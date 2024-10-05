package com.bms.bms.dao.impl;

import com.bms.bms.dao.TheatreDao;
import com.bms.bms.dto.TheatreDTO;
import com.bms.bms.model.Theatre;
import com.bms.bms.utils.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TheatreDaoImpl implements TheatreDao {
    private final Connection connection;
    private final String tableName = "theatre";
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
}