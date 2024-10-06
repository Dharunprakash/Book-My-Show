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
}
