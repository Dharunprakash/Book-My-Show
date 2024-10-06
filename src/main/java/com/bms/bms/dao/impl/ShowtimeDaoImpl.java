package com.bms.bms.dao.impl;

import com.bms.bms.dao.ShowtimeDao;
import com.bms.bms.dto.ShowtimeDTO;
import com.bms.bms.model.Showtime;
import com.bms.bms.utils.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ShowtimeDaoImpl implements ShowtimeDao {
    private final Connection connection;
    private final QueryBuilderUtil queryBuilderUtil = new QueryBuilderUtil();

    public ShowtimeDaoImpl() {
        this.connection = DataBaseUtil.getConnection();
    }

    @Override
    public Showtime save(Showtime showtime) {
        try {
            QueryResult queryResult = queryBuilderUtil.createInsertQuery("showtimes", showtime);
            Long id = queryBuilderUtil.executeDynamicQuery(connection, queryResult, Long.class);
            showtime.setId(id);
            return showtime;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Showtime> findById(Long id) {
        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery("showtimes", Showtime.builder().id(id).build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            Showtime showtime = null;
            if (rs.next()) {
                showtime = ResultSetMapper.mapResultSetToObject(rs, Showtime.class);
            }
            return Optional.ofNullable(showtime);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Showtime> findAll() {
        // Implement database logic to find all showtimes
        return null;
    }

    @Override
    public Showtime update(Showtime showtime) {
        try {
            QueryResult queryResult = queryBuilderUtil.createUpdateQuery("showtimes", showtime, "id", showtime.getId());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
            return showtime;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            QueryResult queryResult = queryBuilderUtil.createDeleteQuery("showtimes", Showtime.builder().id(id).build());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ShowtimeDTO> getShowtimesByScreenId(Long screenId) {
        // Implement database logic to fetch showtimes by screen ID
        return null;
    }
}