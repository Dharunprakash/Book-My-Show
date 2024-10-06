package com.bms.bms.service.impl;

import com.bms.bms.dao.BookingDao;
import com.bms.bms.dao.impl.BookingDaoImpl;
import com.bms.bms.dto.BookingDTO;
import com.bms.bms.dto.CreateBookingDTO;
import com.bms.bms.service.BookingService;

import java.util.List;
import java.util.Optional;

public class BookingServiceImpl implements BookingService {
    private final BookingDao bookingDao;

    public BookingServiceImpl() {
        this.bookingDao = new BookingDaoImpl();
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingDao.findAll();
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        Optional<BookingDTO> bookingDTO = bookingDao.findById(id);
        return bookingDTO.orElse(null);
    }

    @Override
    public CreateBookingDTO createBooking(CreateBookingDTO bookingDTO) {
        return bookingDao.save(bookingDTO);
    }

    @Override
    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        return bookingDao.update(bookingDTO);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingDao.delete(id);
    }
}