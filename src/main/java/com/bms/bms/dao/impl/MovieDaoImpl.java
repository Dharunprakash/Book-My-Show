package com.bms.bms.dao.impl;

import com.bms.bms.dao.MovieDao;
import com.bms.bms.dto.*;
import com.bms.bms.dto.screen.ScreenDTO;
import com.bms.bms.model.Movie;
import com.bms.bms.model.Theatre;
import com.bms.bms.utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


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
        try {
            String sql = "SELECT t.id as theater_id," +
                    "       t.name as theatre_name," +
                    "       t.location," +
                    "       t.address," +
                    "       s.id as screen_id," +
                    "       s.name as screen_name," +
                    "       st.id as showtime_id," +
                    "       st.start_time," +
                    "       st.show_date," +
                    "       st.price " +
                    "FROM theater t " +
                    "JOIN screen s ON t.id = s.theater_id " +
                    "JOIN showtime st ON s.id = st.screen_id " +
                    "JOIN movie m ON st.movie_id = m.id " +
                    "WHERE m.id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, movieId);
            ResultSet rs = preparedStatement.executeQuery();

            List<ScreeningDTO> screenings = new ArrayList<>();
            Map<Long, ScreeningDTO> theatreIdMap = new HashMap<>();

            while (rs.next()) {
                TheatreDTO theatreDTO = ResultSetMapper.mapResultSetToObject(rs, TheatreDTO.class, true);
                ScreenDTO screenDTO = ResultSetMapper.mapResultSetToObject(rs, ScreenDTO.class, true);
                ShowtimeDTO showtimeDTO = ResultSetMapper.mapResultSetToObject(rs, ShowtimeDTO.class, true);

                ScreenShowtimeDTO screenShowtimeDTO = ScreenShowtimeDTO.fromShowtimeDTO(showtimeDTO);
                screenShowtimeDTO.setScreen(screenDTO);

                if (!theatreIdMap.containsKey(theatreDTO.getId())) {
                    ScreeningDTO screeningDTO = ScreeningDTO.fromTheatreDTO(theatreDTO);
                    screeningDTO.setShowtimeDetails(new ArrayList<>());
                    screenings.add(screeningDTO);
                    theatreIdMap.put(theatreDTO.getId(), screeningDTO);
                }

                theatreIdMap.get(theatreDTO.getId()).getShowtimeDetails().add(screenShowtimeDTO);
            }

            return screenings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}