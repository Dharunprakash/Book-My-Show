package com.bms.bms.dto;

import com.bms.bms.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO{
    private Long id;
    private String name;
    private String language;
    private String genre;
    private String duration;
    private String releaseDate;
    private Float rating;

    public Movie toMovie(){
        return Movie.builder()
                .id(id)
                .name(name)
                .language(language)
                .genre(genre)
                .duration(duration)
                .releaseDate(releaseDate)
                .rating(rating)
                .build();
    }

    public static MovieDTO fromMovie(Movie movie){
        return MovieDTO.builder()
                .id(movie.getId())
                .name(movie.getName())
                .language(movie.getLanguage())
                .genre(movie.getGenre())
                .duration(movie.getDuration())
                .releaseDate(movie.getReleaseDate())
                .rating(movie.getRating())
                .build();
    }
}
