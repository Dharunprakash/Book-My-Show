package com.bms.bms.dto;

import com.bms.bms.dto.screen.ScreenDTO;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScreenShowtimeDTO extends ShowtimeDTO {
    private ScreenDTO screen;

    public static ScreenShowtimeDTO fromShowtimeDTO(ShowtimeDTO showtimeDTO) {
        var screenShowtimeDTO = new ScreenShowtimeDTO();
        screenShowtimeDTO.setId(showtimeDTO.getId());
        screenShowtimeDTO.setPrice(showtimeDTO.getPrice());
        screenShowtimeDTO.setScreenId(showtimeDTO.getScreenId());
        screenShowtimeDTO.setStartTime(showtimeDTO.getStartTime());
        return screenShowtimeDTO;
    }
}
