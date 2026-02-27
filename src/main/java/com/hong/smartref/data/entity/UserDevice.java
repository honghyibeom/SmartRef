package com.hong.smartref.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDevice {
    @Id
    @GeneratedValue
    private Long id;

    private String fcmToken;

    private String deviceType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
