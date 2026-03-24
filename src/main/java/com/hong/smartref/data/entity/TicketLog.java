package com.hong.smartref.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketLogId;

    @ManyToOne
    @JoinColumn(name = "email", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String ticketValue;

    @Column
    private LocalDateTime createdAt;

    @Column
    private String result;

    public static TicketLog create(
            User user,
            String ticketValue,
            String result
    ) {
       TicketLog ticketLog = new TicketLog();
       ticketLog.user = user;
       ticketLog.ticketValue = ticketValue;
       ticketLog.result = result;
       ticketLog.createdAt = LocalDateTime.now();
    return ticketLog;
    }
}
