package com.bms.bms.service;

import com.bms.bms.dto.BookingDTO;
import com.bms.bms.dto.CreateBookingDTO;
import com.bms.bms.model.Booking;

import java.util.List;

public interface BookingService {
    List<BookingDTO> getAllBookings();
    BookingDTO getBookingById(Long id);
    CreateBookingDTO createBooking(CreateBookingDTO bookingDTO);
    BookingDTO updateBooking(Long id, BookingDTO bookingDTO);
    void deleteBooking(Long id);
    List<BookingDTO> getAllBookingsByUserId(Long userId);
}