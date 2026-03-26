package com.hong.smartref.service;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.ticket.TicketValueRequest;
import com.hong.smartref.data.dto.ticket.TicketValueResponse;
import com.hong.smartref.data.entity.TicketLog;
import com.hong.smartref.data.entity.User;
import com.hong.smartref.data.enumerate.TicketStatus;
import com.hong.smartref.exception.CustomException;
import com.hong.smartref.exception.ErrorCode;
import com.hong.smartref.repository.TicketLogRepository;
import com.hong.smartref.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String PREFIX = "ticket:";
    private final TicketLogRepository ticketLogRepository;


    // 🔥 Lua Script (validate + 상태 변경 원자 처리)
    private static final String VALIDATE_SCRIPT = """
        local status = redis.call('GET', KEYS[1])
        if not status then return 0 end
        if status ~= 'issue' then return 0 end
        
        local ttl = redis.call('TTL', KEYS[1])
        
        redis.call('SET', KEYS[1], 'inProgress')
        
        if ttl > 0 then
            redis.call('EXPIRE', KEYS[1], ttl)
        end
        
        return 1
        """;

    private static final String LUA_CREATE_SCRIPT = """
            local userKey = KEYS[1]
            local ticketKey = KEYS[2]
    
            local code = ARGV[1]
            local ttl = tonumber(ARGV[2])
            local maxCount = tonumber(ARGV[3])
    
            if not ttl or not maxCount then
                return -99
            end
    
            local size = redis.call('SCARD', userKey)
            if not size then size = 0 end
    
            if size >= maxCount then
                return 0
            end
    
            redis.call('SET', ticketKey, 'issue', 'EX', ttl)
            redis.call('SADD', userKey, code)
    
            local currentTtl = redis.call('TTL', userKey)
            if currentTtl and currentTtl < 0 then
                redis.call('EXPIRE', userKey, ttl)
            end
    
            return 1
        """;
    private final UserRepository userRepository;

    /**
     * 1️⃣ 티켓 생성
     */
    public TicketValueResponse createTicket(UserDetailsImpl userDetails) {

        String code = UUID.randomUUID().toString();

        String email = userDetails.getEmail().toLowerCase();
        String userKey = "user:" + email + ":tickets";
        String ticketKey = "ticket:" + code;

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(LUA_CREATE_SCRIPT);
        script.setResultType(Long.class);

        Long result = redisTemplate.execute(
                script,
                List.of(userKey, ticketKey),
                code,
                String.valueOf(600), // TTL 10분
                String.valueOf(3)    // 최대 3개 제한
        );

        if (result == null || result == 0) {
            throw new CustomException(ErrorCode.TO_MANY_TICKET);
        }

        return TicketValueResponse.builder().ticketValue(code).build();
    }

    /**
     * 2️⃣ 티켓 검증 (issue → inProgress)
     */
    public void validateTicket(TicketValueRequest request) {
        String key = PREFIX + request.getTicketValue();

        String email = request.getEmail().toLowerCase();
        String userKey = "user:" + email + ":tickets";

        Boolean isMember = redisTemplate.opsForSet().isMember(userKey, request.getTicketValue());

        if (Boolean.FALSE.equals(isMember)) {
            throw new CustomException(ErrorCode.NOT_VALIDATE_USER);
        }

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(VALIDATE_SCRIPT);
        script.setResultType(Long.class);

        Long result = redisTemplate.execute(script, Collections.singletonList(key));

        if (result == null || result != 1) {
            throw new CustomException(ErrorCode.NOT_VALIDATE_TICKET);
        }
    }

    /**
     * 3️⃣ 완료 처리 (SUCCESS / FAILED)
     */
    @Transactional
    public void completeTicket(TicketValueRequest request) {

        String code = request.getTicketValue();
        TicketStatus status = request.getStatus();

        String key = PREFIX + code;
        String email = request.getEmail().toLowerCase();
        String userKey = "user:" + email + ":tickets";

        Object current = redisTemplate.opsForValue().get(key);
        if (current == null) {
            throw new CustomException(ErrorCode.NOT_EXIST_TICKET);
        }

        if (status != TicketStatus.success && status != TicketStatus.failed) {
            throw new CustomException(ErrorCode.INVALID_STATUS);
        }

        // 1️⃣ Redis 상태 변경
        redisTemplate.opsForValue().set(
                key,
                status.getValue(), // 🔥 중요 (문자열로)
                5,
                TimeUnit.MINUTES
        );

        // 2️⃣ Set에서 제거 (핵심)
        redisTemplate.opsForSet().remove(userKey, code);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        // 3️⃣ 로그 저장 (추천)
        TicketLog ticketLog = TicketLog.create(user, code, status.getValue());
        ticketLogRepository.save(ticketLog);
    }

}
