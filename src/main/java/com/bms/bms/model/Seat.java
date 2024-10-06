package com.bms.bms.model;

import com.bms.bms.annotations.RelationField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    private Long id;
    private Long screenId;
    private Integer seatNumber;
    @RelationField("bookings")
    private List<Booking> bookings;
}
