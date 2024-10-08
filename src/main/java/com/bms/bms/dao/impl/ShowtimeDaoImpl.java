package com.bms.bms.dao.impl;

import com.bms.bms.dao.ShowtimeDao;
import com.bms.bms.dto.ShowtimeDTO;
import com.bms.bms.dto.ShowtimeSeatsDTO;
import com.bms.bms.model.Showtime;
import com.bms.bms.utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        // showtheshowtime only the showtime start time dadte and time greatr than or equal to current date and time

        try {
            QueryResult queryResult = queryBuilderUtil.createSelectQuery("showtime", Showtime.builder().screenId(screenId).build());
            ResultSet rs = queryBuilderUtil.executeDynamicSelectQuery(connection, queryResult);

            List<ShowtimeDTO> showtimes = new ArrayList<>();

            while (rs.next()) {
                Showtime showtime = ResultSetMapper.mapResultSetToObject(rs, Showtime.class);
                ShowtimeDTO showtimeDTO = ShowtimeDTO.fromShowtime(showtime);
                showtimes.add(showtimeDTO);
            }

            return showtimes;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public List<ShowtimeDTO> getShowtimesByScreenIdAndDate(Long screenId, String date) {
        try {
            String sql = "SELECT * FROM showtime WHERE screen_id = ? AND DATE(start_time) = ?";
            System.out.println("ShowtimeDaoImpl getShowtimesByScreenIdAndDate "+sql);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, screenId);
            preparedStatement.setString(2, date);
            ResultSet rs = preparedStatement.executeQuery();

            List<ShowtimeDTO> showtimes = new ArrayList<>();

            while (rs.next()) {
                Showtime showtime = ResultSetMapper.mapResultSetToObject(rs, Showtime.class);
                ShowtimeDTO showtimeDTO = ShowtimeDTO.fromShowtime(showtime);
                showtimes.add(showtimeDTO);
            }

            return showtimes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ShowtimeSeatsDTO getShowtimeById(Long id) {
        String sql = "SELECT " +
                "    s.seat_number, " +
                "    CASE " +
                "        WHEN t.seat_id IS NOT NULL THEN 'booked' " +
                "        ELSE 'available' " +
                "    END AS is_available " +
                "FROM " +
                "    showtime st " +
                "JOIN " +
                "    seat s ON st.screen_id = s.screen_id AND st.id = ? " +
                "LEFT JOIN " +
                "    ( " +
                "        SELECT " +
                "            bs.seat_id " +
                "        FROM " +
                "            booking_seats bs " +
                "        JOIN " +
                "            booking b ON b.id = bs.booking_id " +
                "        WHERE " +
                "            b.showtime_id = ? " +
                "    ) AS t ON t.seat_id = s.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, id);
            ResultSet rs = preparedStatement.executeQuery();

            List<ShowtimeSeatsDTO.SeatDetail> seatDetails = new ArrayList<>();

            while (rs.next()) {
                int seatNumber = rs.getInt("seat_number");
                boolean isAvailable = "available".equals(rs.getString("is_available"));

                ShowtimeSeatsDTO.SeatDetail seatDetail = ShowtimeSeatsDTO.SeatDetail.builder()
                        .seatNumber(seatNumber)
                        .isAvailable(isAvailable)
                        .build();

                seatDetails.add(seatDetail);
            }

            return ShowtimeSeatsDTO.builder()
                    .seats(seatDetails)
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}