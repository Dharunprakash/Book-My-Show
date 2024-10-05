package com.bms.bms.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO extends MovieDTO {
    private List<ScreenShowtimeDTO> showtimeDetails;
}
