package com.bms.bms.dao.impl;

import com.bms.bms.dao.MovieDao;
import com.bms.bms.dto.MovieDTO;
import com.bms.bms.dto.ScreeningDTO;
import com.bms.bms.model.Movie;
import com.bms.bms.utils.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDaoImpl implements MovieDao {
    private final Connection connection;
    private final String tableName = "movie";
    private final QueryBuilderUtil queryBuilderUtil = new QueryBuilderUtil();

    public MovieDaoImpl() {
        this.connection = DataBaseUtil.getConnection();
    }

    @Override
    public Movie save(Movie movie) {
        try {
            QueryResult queryResult = queryBuilderUtil.createInsertQuery(tableName, movie);
            Long id = queryBuilderUtil.executeDynamicQuery(connection, queryResult, Long.class);
            movie.setId(id);
            return movie;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Movie> findById(Long id) {
        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery(tableName, Movie.builder().id(id).build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            Movie movie = null;
            if (rs.next()) {
                movie = ResultSetMapper.mapResultSetToObject(rs, Movie.class);
            }
            return Optional.ofNullable(movie);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Movie> findAll() {
        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery(tableName, Movie.builder().build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            List<Movie> movies = new ArrayList<>();
            while (rs.next()) {
                Movie movie = ResultSetMapper.mapResultSetToObject(rs, Movie.class);
                movies.add(movie);
            }
            return movies;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Movie update(Movie movie) {
        try {
            QueryResult queryResult = queryBuilderUtil.createUpdateQuery(tableName, movie, "id", movie.getId());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
            return movie;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            QueryResult queryResult = queryBuilderUtil.createDeleteQuery(tableName, Movie.builder().id(id).build());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MovieDTO> getMovies(String date) {
        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery(tableName, Movie.builder().releaseDate(date).build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            List<MovieDTO> movies = new ArrayList<>();
            while (rs.next()) {
                Movie movie = ResultSetMapper.mapResultSetToObject(rs, Movie.class);
                MovieDTO movieDTO = MovieDTO.fromMovie(movie);
                movies.add(movieDTO);
            }
            return movies;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ScreeningDTO> getMovieScreenings(Long movieId) {

        return null;
    }
}