package com.bms.bms.dto;

import com.bms.bms.model.Booking;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DetailedBookingDTO extends CreateBookingDTO {
    private TheatreDTO theatre;
    private MovieDTO movie;
    private ScreenDTO screen;
    private ShowtimeDTO showtime;
    private List<SeatDTO> seats;
}
