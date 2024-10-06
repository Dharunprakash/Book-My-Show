package com.bms.bms.model;

import com.bms.bms.annotations.RelationField;
import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Screen {
    private Long id;
    private String name;
    private Long theaterId;
    @RelationField("seats")
    private List<Seat> seats; // List of seats associated with the screen
    @RelationField("showtimes")
    private List<Showtime> showtimes; // List of showtimes associated with the screen
}
