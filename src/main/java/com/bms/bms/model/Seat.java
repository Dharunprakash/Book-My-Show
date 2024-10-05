package com.bms.bms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    private Long id;
    private Long screenId;
    private Integer seatNumber;
    private List<BookingSeats> bookedSeats;
}
