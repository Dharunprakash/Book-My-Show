package com.bms.bms.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Screen {
    private Long id;
    private String name;
    private Long theaterId;
    private List<Seat> seats; // List of seats associated with the screen
    private List<Showtime> showtimes; // List of showtimes associated with the screen
}
