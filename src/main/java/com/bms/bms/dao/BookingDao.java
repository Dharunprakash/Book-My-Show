package com.bms.bms.dao;

import com.bms.bms.dto.BookingDTO;
import com.bms.bms.dto.CreateBookingDTO;

import java.util.List;
import java.util.Optional;

public interface BookingDao {
    CreateBookingDTO save(CreateBookingDTO booking);
    Optional<BookingDTO> findById(Long id);
    List<BookingDTO> findAll();
    BookingDTO update(BookingDTO booking);
    void delete(Long id);
}