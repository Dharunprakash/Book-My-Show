package com.bms.bms.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningDTO extends TheatreDTO {
    private List<ScreenShowtimeDTO> showtimeDetails;

    public static ScreeningDTO fromTheatreDTO(TheatreDTO theatreDTO) {
        var screeningDTO = new ScreeningDTO();
        screeningDTO.setId(theatreDTO.getId());
        screeningDTO.setName(theatreDTO.getName());
        screeningDTO.setLocation(theatreDTO.getLocation());
        screeningDTO.setAddress(theatreDTO.getAddress());
        return screeningDTO;
    }
}
