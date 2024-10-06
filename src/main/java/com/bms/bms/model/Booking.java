package com.bms.bms.model;

import com.bms.bms.annotations.RelationField;
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
    @RelationField("bookedSeats")
    private List<Seat> bookedSeats;
}