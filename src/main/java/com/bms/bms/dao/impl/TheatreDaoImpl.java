package com.bms.bms.dao.impl;

import com.bms.bms.dao.TheatreDao;
import com.bms.bms.dto.*;
import com.bms.bms.dto.screen.ScreenDTO;
import com.bms.bms.model.Movie;
import com.bms.bms.model.Theatre;
import com.bms.bms.utils.*;

import java.sql.*;
import java.util.*;

public class TheatreDaoImpl implements TheatreDao {
    private final Connection connection;
    private final String tableName = "theater";
    private final QueryBuilderUtil queryBuilderUtil = new QueryBuilderUtil();

    public TheatreDaoImpl() {
        this.connection = DataBaseUtil.getConnection();
    }

    @Override
    public Theatre save(Theatre theatre) {
        try {
            QueryResult queryResult = queryBuilderUtil.createInsertQuery(tableName, theatre);
            Long id = queryBuilderUtil.executeDynamicQuery(connection, queryResult, Long.class);
            theatre.setId(id);
            return theatre;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Theatre> findById(Long id) {
        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery(tableName, Theatre.builder().id(id).build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            Theatre theatre = null;
            if (rs.next()) {
                theatre = ResultSetMapper.mapResultSetToObject(rs, Theatre.class);
            }
            return Optional.ofNullable(theatre);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Theatre> findAll() {
        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery(tableName, Theatre.builder().build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            List<Theatre> theatres = new ArrayList<>();
            while (rs.next()) {
                Theatre theatre = ResultSetMapper.mapResultSetToObject(rs, Theatre.class);
                theatres.add(theatre);
            }
            return theatres;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ShowDTO> getShowsByTheatreId(Long theatreId) {
        try {
            String sql = "SELECT s.id as screen_id," +
                    "    st.id as showtime_id," +
                    "    m.id as movie_id," +
                    "    s.theater_id," +
                    "    m.name as movie_name," +
                    "    s.name as screen_name," +
                    "    st.start_time," +
                    "    st.show_date," +
                    "    st.price," +
                    "    m.duration," +
                    "    m.genre," +
                    "    m.language," +
                    "    m.rating," +
                    "    m.release_date " +
             "FROM screen s " +
             "JOIN showtime st ON s.id = st.screen_id " +
             "JOIN movie m ON st.movie_id = m.id " +
             "WHERE s.theater_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, theatreId);
            ResultSet rs = preparedStatement.executeQuery();
            List<ShowDTO> shows = new ArrayList<>();
            boolean first = true;
            List<ScreenShowtimeDTO> screenShowtimeDTOs = new ArrayList<>();
            Map<Long, ShowDTO> movieIdMap = new HashMap<>();
            while (rs.next()) {
                Movie movie = ResultSetMapper.mapResultSetToObject(rs, Movie.class, true);
                MovieDTO movieDTO = MovieDTO.fromMovie(movie);
                ScreenDTO screenDTO = ResultSetMapper.mapResultSetToObject(rs, ScreenDTO.class, true);
                ShowtimeDTO showtimeDTO = ResultSetMapper.mapResultSetToObject(rs, ShowtimeDTO.class, true);
                System.out.println("Movie: "+movieDTO);
                System.out.println("Screen: "+screenDTO);
                System.out.println("Showtime: "+showtimeDTO);
                ScreenShowtimeDTO screenShowtimeDTO = ScreenShowtimeDTO.fromShowtimeDTO(showtimeDTO);
                screenShowtimeDTO.setScreen(screenDTO);
                if (!movieIdMap.containsKey(movieDTO.getId())) {
                    System.out.println("First time "+movieDTO);
                    ShowDTO showDTO = ShowDTO.fromMovieDTO(movieDTO);
                    showDTO.setShowtimeDetails(new ArrayList<>());
                    shows.add(showDTO);
                    movieIdMap.put(movieDTO.getId(), showDTO);
                }
                System.out.println(movieIdMap);
                System.out.println(movieIdMap.get(movieDTO.getId()));
                movieIdMap.get(movieDTO.getId()).getShowtimeDetails().add(screenShowtimeDTO);
            }
            return shows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Theatre update(Theatre theatre) {
        try {
            QueryResult queryResult = queryBuilderUtil.createUpdateQuery(tableName, theatre, "id", theatre.getId());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
            return theatre;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            QueryResult queryResult = queryBuilderUtil.createDeleteQuery(tableName, Theatre.builder().id(id).build());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TheatreDTO> getAllTheatres() {
        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery(tableName, Theatre.builder().build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            List<TheatreDTO> theatres = new ArrayList<>();
            while (rs.next()) {
                Theatre theatre = ResultSetMapper.mapResultSetToObject(rs, Theatre.class);
                TheatreDTO theatreDTO = TheatreDTO.from(theatre);
                theatres.add(theatreDTO);
            }
            return theatres;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TheatreDTO getTheatreById(Long id) {
        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery(tableName, Theatre.builder().id(id).build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            Theatre theatre = null;
            if (rs.next()) {
                theatre = ResultSetMapper.mapResultSetToObject(rs, Theatre.class);
            }
            return TheatreDTO.from(theatre);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Theatre> getTheatresByLocation(String location) {
        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery(tableName, Theatre.builder().location(location).build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            List<Theatre> theatres = new ArrayList<>();
            while (rs.next()) {
                Theatre theatre = ResultSetMapper.mapResultSetToObject(rs, Theatre.class);
                theatres.add(theatre);
            }
            return theatres;
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}