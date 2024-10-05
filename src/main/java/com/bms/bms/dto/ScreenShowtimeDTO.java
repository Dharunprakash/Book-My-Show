package com.bms.bms.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScreenShowtimeDTO extends ShowtimeDTO {
    private ScreenDTO screen;
}
