package com.bms.bms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingSeat {
    private Long id;
    private Long bookingId;
    private Long seatId;
}
