package com.bms.bms.dao;

import com.bms.bms.dto.BookingDTO;

import java.util.List;
import java.util.Optional;

public interface BookingDao {
    BookingDTO save(BookingDTO booking);
    Optional<BookingDTO> findById(Long id);
    List<BookingDTO> findAll();
    BookingDTO update(BookingDTO booking);
    void delete(Long id);
}