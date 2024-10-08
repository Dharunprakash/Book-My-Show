package com.bms.bms.service;

import com.bms.bms.dto.MovieDTO;
import com.bms.bms.dto.ScreeningDTO;

import java.util.List;

public interface MovieService {
    List<MovieDTO> getMovies(String date);
    MovieDTO getMovieById(Long id);
    List<ScreeningDTO> getMovieScreenings(Long movieId);
    MovieDTO createMovie(MovieDTO movieDTO);
    MovieDTO updateMovie(MovieDTO movieDTO);
    void deleteMovie(Long id);
    List<MovieDTO> findAll();
}