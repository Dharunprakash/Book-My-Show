package com.bms.bms.model;

import com.bms.bms.annotations.JoinMappingId;
import com.bms.bms.annotations.RelationField;
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
    @JoinMappingId("movie_id")
    private Long id;
    @JoinMappingId("movie_name")
    private String name;
    private String language;
    private String genre;
    private String duration;
    private String releaseDate;
    private Float rating;
    @RelationField("showtimes")
    private List<Showtime> showtimes;
}

