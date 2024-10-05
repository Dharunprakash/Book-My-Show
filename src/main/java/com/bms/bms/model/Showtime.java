package com.bms.bms.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Showtime {
    private Long id;
    private Long screenId;
    private Long movieId;
    private Date startTime;
    private Float price;
    private Integer availableSeats;
    private List<Booking> bookings;
}

