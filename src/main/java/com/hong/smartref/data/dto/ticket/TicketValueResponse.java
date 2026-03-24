package com.hong.smartref.data.dto.ticket;


import com.hong.smartref.data.enumerate.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketValueResponse {
    private String ticketValue;
}
