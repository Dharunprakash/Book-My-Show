package com.bms.bms.dto;

import com.bms.bms.dto.screen.ScreenDTO;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailedBookingDTO extends CreateBookingDTO {
    private TheatreDTO theatre;
    private MovieDTO movie;
    private ScreenDTO screen;
    private ShowtimeDTO showtime;
    private List<SeatDTO> seats;
}
