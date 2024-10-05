package com.bms.bms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingSeats {
    private Long id;
    private Long bookingId;
    private Long seatId;
}
