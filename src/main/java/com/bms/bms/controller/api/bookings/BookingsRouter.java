package com.bms.bms.controller.api.bookings;

import com.bms.bms.dto.BookingDTO;
import com.bms.bms.router.Router;
import com.bms.bms.service.BookingService;
import com.bms.bms.service.impl.BookingServiceImpl;
import com.bms.bms.utils.HttpRequestParser;
import com.bms.bms.utils.PathParamExtractor;
import com.bms.bms.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingsRouter {
    private final BookingService bookingService;
    private static final Logger LOGGER = Logger.getLogger(BookingsRouter.class.getName());

    public BookingsRouter() {
        bookingService = new BookingServiceImpl();
    }

    public void register(Router router) {
        router.get("/", this::getAllBookings);
        router.get("/:id", this::getBookingById);
        router.post("/", this::createBooking);
        router.put("/:id", this::updateBooking);
        router.delete("/:id", this::deleteBooking);
    }

    private void getAllBookings(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<BookingDTO> bookings = bookingService.getAllBookings();
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Bookings found", bookings);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getAllBookings", e);
            handleException(req, resp, e);
        }
    }

    private void getBookingById(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)", BookingParams.class);
            BookingDTO booking = bookingService.getBookingById(params.getId());
            if (booking != null) {
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Booking found", booking);
            } else {
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_NOT_FOUND, "Booking not found", null);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getBookingById", e);
            handleException(req, resp, e);
        }
    }

    private void createBooking(HttpServletRequest req, HttpServletResponse resp) {
        try {
            BookingDTO bookingDTO = HttpRequestParser.parse(req, BookingDTO.class);
            BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_CREATED, "Booking created", createdBooking);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in createBooking", e);
            handleException(req, resp, e);
        }
    }

    private void updateBooking(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)", BookingParams.class);
            BookingDTO bookingDTO = HttpRequestParser.parse(req, BookingDTO.class);
            bookingDTO.setId(params.getId());
            BookingDTO updatedBooking = bookingService.updateBooking(params.getId(), bookingDTO);
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Booking updated", updatedBooking);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in updateBooking", e);
            handleException(req, resp, e);
        }
    }

    private void deleteBooking(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)", BookingParams.class);
            bookingService.deleteBooking(params.getId());
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Booking deleted", null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in deleteBooking", e);
            handleException(req, resp, e);
        }
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        try {
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), null);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error handling exception", ex);
        }
    }

    @Data
    public static class BookingParams {
        private Long id;
    }
}