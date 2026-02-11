package com.hong.smartref.data.entity;

import com.hong.smartref.data.enumerate.DeviceType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PushToken {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(unique = true, nullable = false)
    private String deviceToken;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    private LocalDateTime lastUsedAt;
}
