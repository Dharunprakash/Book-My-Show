package com.bms.bms.dao.impl;

import com.bms.bms.dao.BookingDao;
import com.bms.bms.dto.BookingDTO;
import com.bms.bms.dto.CreateBookingDTO;
import com.bms.bms.model.Booking;
import com.bms.bms.utils.DataBaseUtil;
import com.bms.bms.utils.QueryBuilderUtil;
import com.bms.bms.utils.QueryResult;
import com.bms.bms.utils.ResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingDaoImpl implements BookingDao {
    private final Connection connection;
    private final String tableName = "booking";
    private final QueryBuilderUtil queryBuilderUtil = new QueryBuilderUtil();

    public BookingDaoImpl() {
        this.connection = DataBaseUtil.getConnection();
    }

    @Override
    public CreateBookingDTO save(CreateBookingDTO booking) {
        try {
            QueryResult queryResult = queryBuilderUtil.createInsertQuery(tableName, booking);
            Long bookingId = queryBuilderUtil.executeDynamicQuery(connection, queryResult, Long.class);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into booking_seats(booking_id, seat_id) values (?, ?)");
            for (Long seatId : booking.getSeatIds()) {
                preparedStatement.setLong(1, bookingId);
                preparedStatement.setLong(2, seatId);
                preparedStatement.addBatch();
            }
            System.out.println(bookingId);
            preparedStatement.executeBatch();
            return booking;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<BookingDTO> findById(Long id) {
        try {
            String query = "select\n" +
                    "    b.id,\n" +
                    "    b.user_id,\n" +
                    "    t.name as theatre_name,\n" +
                    "    b.showtime_id,\n" +
                    "    st.start_time as show_time,\n" +
                    "    st.price,\n" +
                    "    s.id as screen_id,\n" +
                    "    s.name as screen_name,\n" +
                    "    m.id as movie_id,\n" +
                    "    m.name as movie_name,\n" +
                    "    se.seat_number\n" +
                    "from\n" +
                    "    booking b\n" +
                    "join\n" +
                    "    showtime st on b.showtime_id = st.id\n" +
                    "join\n" +
                    "    booking_seats bs on b.id = bs.booking_id\n" +
                    "join\n" +
                    "    screen s on st.screen_id = s.id\n" +
                    "join\n" +
                    "    seat se on bs.seat_id = se.id\n" +
                    "join\n" +
                    "    movie m on st.movie_id = m.id\n" +
                    "join\n" +
                    "    theater t on s.theater_id = t.id\n" +
                    "where\n" +
                    "    b.id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            BookingDTO booking = null;
            List<Integer> seatNumbers = new ArrayList<>();
            if (rs.next()) {
                booking = ResultSetMapper.mapResultSetToObject(rs, BookingDTO.class);
                seatNumbers.add(rs.getInt("seat_number"));
            }
            while (rs.next()) {
                seatNumbers.add(rs.getInt("seat_number"));
            }
            if (booking != null) {
                booking.setBookedSeats(seatNumbers);
            }
            return Optional.ofNullable(booking);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BookingDTO> findAll() {
        try {
            String query = "select\n" +
                    "    b.id,\n" +
                    "    b.user_id,\n" +
                    "    t.name as theatre_name,\n" +
                    "    b.showtime_id,\n" +
                    "    st.start_time as show_time,\n" +
                    "    st.price,\n" +
                    "    s.id as screen_id,\n" +
                    "    s.name as screen_name,\n" +
                    "    m.id as movie_id,\n" +
                    "    m.name as movie_name,\n" +
                    "    se.seat_number\n" +
                    "from\n" +
                    "    booking b\n" +
                    "join\n" +
                    "    showtime st on b.showtime_id = st.id\n" +
                    "join\n" +
                    "    booking_seats bs on b.id = bs.booking_id\n" +
                    "join\n" +
                    "    screen s on st.screen_id = s.id\n" +
                    "join\n" +
                    "    seat se on bs.seat_id = se.id\n" +
                    "join\n" +
                    "    movie m on st.movie_id = m.id\n" +
                    "join\n" +
                    "    theater t on s.theater_id = t.id;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            List<BookingDTO> bookings = new ArrayList<>();
            BookingDTO currentBooking = null;
            List<Integer> seatNumbers = new ArrayList<>();
            Long currentBookingId = null;

            while (rs.next()) {
                Long bookingId = rs.getLong("id");
                if (currentBookingId == null || !currentBookingId.equals(bookingId)) {
                    if (currentBooking != null) {
                        currentBooking.setBookedSeats(seatNumbers);
                        bookings.add(currentBooking);
                    }
                    currentBooking = ResultSetMapper.mapResultSetToObject(rs, BookingDTO.class);
                    seatNumbers = new ArrayList<>();
                    currentBookingId = bookingId;
                }
                seatNumbers.add(rs.getInt("seat_number"));
            }

            if (currentBooking != null) {
                currentBooking.setBookedSeats(seatNumbers);
                bookings.add(currentBooking);
            }

            return bookings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<BookingDTO> findAllByUserId(Long userId) {
        try {
            String query = "select\n" +
                    "    b.id,\n" +
                    "    b.user_id,\n" +
                    "    t.name as theatre_name,\n" +
                    "    b.showtime_id,\n" +
                    "    st.start_time as show_time,\n" +
                    "    st.price,\n" +
                    "    s.id as screen_id,\n" +
                    "    s.name as screen_name,\n" +
                    "    m.id as movie_id,\n" +
                    "    m.name as movie_name,\n" +
                    "    se.seat_number\n" +
                    "from\n" +
                    "    booking b\n" +
                    "join\n" +
                    "    showtime st on b.showtime_id = st.id\n" +
                    "join\n" +
                    "    booking_seats bs on b.id = bs.booking_id\n" +
                    "join\n" +
                    "    screen s on st.screen_id = s.id\n" +
                    "join\n" +
                    "    seat se on bs.seat_id = se.id\n" +
                    "join\n" +
                    "    movie m on st.movie_id = m.id\n" +
                    "join\n" +
                    "    theater t on s.theater_id = t.id\n" +
                    "where\n" +
                    "    b.user_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            List<BookingDTO> bookings = new ArrayList<>();
            BookingDTO currentBooking = null;
            List<Integer> seatNumbers = new ArrayList<>();
            Long currentBookingId = null;

            while (rs.next()) {
                Long bookingId = rs.getLong("id");
                if (currentBookingId == null || !currentBookingId.equals(bookingId)) {
                    if (currentBooking != null) {
                        currentBooking.setBookedSeats(seatNumbers);
                        bookings.add(currentBooking);
                    }
                    currentBooking = ResultSetMapper.mapResultSetToObject(rs, BookingDTO.class);
                    seatNumbers = new ArrayList<>();
                    currentBookingId = bookingId;
                }
                seatNumbers.add(rs.getInt("seat_number"));
            }

            if (currentBooking != null) {
                currentBooking.setBookedSeats(seatNumbers);
                bookings.add(currentBooking);
            }

            return bookings;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BookingDTO update(BookingDTO booking) {
        try {
            QueryResult queryResult = queryBuilderUtil.createUpdateQuery(tableName, booking, "id", booking.getId());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
            return booking;
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            QueryResult queryResult = queryBuilderUtil.createDeleteQuery(tableName, Booking.builder().id(id).build());
            queryBuilderUtil.executeDynamicQuery(connection, queryResult);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}