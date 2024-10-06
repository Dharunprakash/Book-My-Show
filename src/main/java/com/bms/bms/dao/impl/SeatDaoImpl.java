package com.bms.bms.dao.impl;

import com.bms.bms.dao.SeatDao;
import com.bms.bms.dto.SeatDTO;
import com.bms.bms.model.Seat;
import com.bms.bms.utils.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeatDaoImpl implements SeatDao {
    private final Connection connection;
    private final String tableName = "seat";
    private final QueryBuilderUtil queryBuilderUtil = new QueryBuilderUtil();

    public SeatDaoImpl() {
        this.connection = DataBaseUtil.getConnection();
    }

    @Override
    public Seat save(Seat seat) {
        try {
            QueryResult queryResult = queryBuilderUtil.createInsertQuery(tableName, seat);
            Long id = queryBuilderUtil.executeDynamicQuery(connection, queryResult, Long.class);
            seat.setId(id);
            return seat;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Seat> findById(Long id) {
        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery(tableName, Seat.builder().id(id).build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            Seat seat = null;
            if (rs.next()) {
                seat = ResultSetMapper.mapResultSetToObject(rs, Seat.class);
            }
            return Optional.ofNullable(seat);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Seat> findAll() {
        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery(tableName, Seat.builder().build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            List<Seat> seats = new ArrayList<>();
            while (rs.next()) {
                Seat seat = ResultSetMapper.mapResultSetToObject(rs, Seat.class);
                seats.add(seat);
            }
            return seats;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Seat update(Seat seat) {
        try {
            QueryResult queryResult = queryBuilderUtil.createUpdateQuery(tableName, seat, "id", seat.getId());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
            return seat;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            QueryResult queryResult = queryBuilderUtil.createDeleteQuery(tableName, Seat.builder().id(id).build());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeatDTO> getSeatsByScreenId(Long screenId) {
        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery(tableName, Seat.builder().screenId(screenId).build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);
            List<SeatDTO> seats = new ArrayList<>();
            while (rs.next()) {
                Seat seat = ResultSetMapper.mapResultSetToObject(rs, Seat.class);
                SeatDTO seatDTO = SeatDTO.fromSeat(seat);
                seats.add(seatDTO);
            }
            return seats;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}