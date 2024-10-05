package com.bms.bms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Long id;
    private String name;
    private String language;
    private String genre;
    private String duration;
    private String releaseDate;
    private Float rating;
    private List<Showtime> showtimes;
}

