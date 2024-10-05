package com.bms.bms.dao;

import com.bms.bms.dto.MovieDTO;
import com.bms.bms.dto.ScreeningDTO;
import com.bms.bms.model.Movie;

import java.util.List;

public interface MovieDao extends GenericDao<Movie, Long> {
    List<MovieDTO> getMovies(String date);
    List<ScreeningDTO> getMovieScreenings(Long movieId);
}