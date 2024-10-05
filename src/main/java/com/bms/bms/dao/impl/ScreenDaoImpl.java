package com.bms.bms.dao.impl;

import com.bms.bms.dao.ScreenDao;
import com.bms.bms.dto.ScreenDTO;
import com.bms.bms.model.Screen;
import com.bms.bms.utils.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ScreenDaoImpl implements ScreenDao {
    private final Connection connection;
    private final QueryBuilderUtil queryBuilderUtil = new QueryBuilderUtil();

    public ScreenDaoImpl() {
        this.connection = DataBaseUtil.getConnection();
    }

    @Override
    public Screen save(Screen screen) {
        try {
            QueryResult queryResult = queryBuilderUtil.createInsertQuery("screens", screen);
            Long id = queryBuilderUtil.executeDynamicQuery(connection, queryResult, Long.class);
            screen.setId(id);
            return screen;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Screen> findById(Long id) {
        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery("screens", Screen.builder().id(id).build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            Screen screen = null;
            if (rs.next()) {
                screen = ResultSetMapper.mapResultSetToObject(rs, Screen.class);
            }
            return Optional.ofNullable(screen);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Screen> findAll() {
        // Implement database logic to find all screens
        return null;
    }

    @Override
    public Screen update(Screen screen) {
        try {
            QueryResult queryResult = queryBuilderUtil.createUpdateQuery("screens", screen, "id", screen.getId());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
            return screen;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            QueryResult queryResult = queryBuilderUtil.createDeleteQuery("screens", Screen.builder().id(id).build());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ScreenDTO> getScreensByTheatreId(Long theatreId) {
        // Implement database logic to fetch screens by theatre ID
        return null;
    }
}