package com.bms.bms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Booking {
    private Long id;
    private Long userId;
    private Long showtimeId;
    private List<BookingSeat> bookedSeats;
}
