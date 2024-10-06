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

    public static ShowDTO fromMovieDTO(MovieDTO movieDTO) {
        var showDTO = new ShowDTO();
        showDTO.setId(movieDTO.getId());
        showDTO.setName(movieDTO.getName());
        showDTO.setGenre(movieDTO.getGenre());
        showDTO.setDuration(movieDTO.getDuration());
        showDTO.setLanguage(movieDTO.getLanguage());
        showDTO.setReleaseDate(movieDTO.getReleaseDate());
        return showDTO;
    }
}
