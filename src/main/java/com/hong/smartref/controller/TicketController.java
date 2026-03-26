package com.hong.smartref.controller;

import com.hong.smartref.config.security.UserDetailsImpl;
import com.hong.smartref.data.dto.ApiResponse;
import com.hong.smartref.data.dto.ticket.TicketValueRequest;
import com.hong.smartref.data.dto.ticket.TicketValueResponse;
import com.hong.smartref.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ticket")
public class TicketController {
    private final TicketService ticketService;

    @Operation(summary = "티켓 발급 요청", description = "티켓 발급")
    @GetMapping("/request")
    public ResponseEntity<ApiResponse<TicketValueResponse>> getTicket(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                ApiResponse.success("티켓 발급 성공", ticketService.createTicket(userDetails))
        );
    }

    @Operation(summary = "티켓 사용 가능 여부", description = "티켓 사용 가능 여부")
    @PostMapping("/validate-and-lock")
    public ResponseEntity<ApiResponse<Void>> validateTicket(@RequestBody TicketValueRequest ticketValueRequest) {
        ticketService.validateTicket(ticketValueRequest);
        return ResponseEntity.ok(
                ApiResponse.success("티켓 검증 성공")
        );
    }

    @Operation(summary = "티켓 사용 완료후 로그 저장", description = "티켓 사용 완료")
    @PostMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateTicket (@RequestBody TicketValueRequest request) {
        ticketService.completeTicket(request);
        return ResponseEntity.ok(
                ApiResponse.success("티켓 사용 완료")
        );
    }


}
