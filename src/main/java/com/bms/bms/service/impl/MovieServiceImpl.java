package com.bms.bms.service.impl;

import com.bms.bms.dao.MovieDao;
import com.bms.bms.dao.impl.MovieDaoImpl;
import com.bms.bms.dto.MovieDTO;
import com.bms.bms.dto.ScreeningDTO;
import com.bms.bms.model.Movie;
import com.bms.bms.service.MovieService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MovieServiceImpl implements MovieService {
    private final MovieDao movieDao;

    public MovieServiceImpl() throws SQLException, ClassNotFoundException {
        this.movieDao = new MovieDaoImpl();
    }

    @Override
    public List<MovieDTO> getMovies(String date) {
        return movieDao.getMovies(date);
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        Optional<Movie> movie = movieDao.findById(id);
        return movie.map(MovieDTO::fromMovie).orElse(null);
    }

    @Override
    public List<ScreeningDTO> getMovieScreenings(Long movieId) {
        return movieDao.getMovieScreenings(movieId);
    }

    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        Movie movie = movieDTO.toMovie();
        Movie savedMovie = movieDao.save(movie);
        return MovieDTO.fromMovie(savedMovie);
    }

    @Override
    public MovieDTO updateMovie(MovieDTO movieDTO) {
        Movie movie = movieDTO.toMovie();
        Movie updatedMovie = movieDao.update(movie);
        return MovieDTO.fromMovie(updatedMovie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieDao.delete(id);
    }
}