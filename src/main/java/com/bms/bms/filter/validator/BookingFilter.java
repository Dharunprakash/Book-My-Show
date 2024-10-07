package com.bms.bms.filter.validator;

import com.bms.bms.utils.validator.Validator;
import com.bms.bms.utils.validator.ValidatorBuilder;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.bms.bms.dto.CreateBookingDTO;
import com.bms.bms.utils.CachedBodyHttpServletRequest;
import com.bms.bms.utils.HttpRequestParser;
import com.bms.bms.utils.ResponseUtil;

import java.io.IOException;
import java.util.List;

@WebFilter("/api/bookings")
public class BookingFilter implements Filter {
    Validator<Long> userIdValidator = new ValidatorBuilder<Long>()
            .addNotNull("User ID cannot be null")
            .build();
    Validator<Long> showtimeIdValidator = new ValidatorBuilder<Long>()
            .addNotNull("Showtime ID cannot be null")
            .build();
    Validator<List<Long>> seatIdsValidator = new ValidatorBuilder<List<Long>>()
            .addNotNull("Seat IDs cannot be null")
            .addSize(1, 10, "Seat IDs must be between 1 and 10")
            .build();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String method = httpRequest.getMethod();

        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(httpRequest);
        System.out.println("HEY I'm A filter: BookingFilter");
        if ("POST".equals(method)) {
            validateCreateBookingRequest(cachedRequest, httpResponse, chain);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void validateCreateBookingRequest(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        try {
            CreateBookingDTO createDTO = HttpRequestParser.parse(request, CreateBookingDTO.class);
            System.out.println("isValidating1");
            if (createDTO != null && isValidCreateBooking(createDTO)) {
                System.out.println("isValidating");
                chain.doFilter(request, response);
            } else {
                ResponseUtil.sendResponse(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid booking data", null);
            }
        } catch (Exception e) {
            ResponseUtil.sendResponse(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid booking data", null);
        }
    }

    private boolean isValidCreateBooking(CreateBookingDTO dto) {

        try {
            userIdValidator.validate(dto.getUserId());
            showtimeIdValidator.validate(dto.getShowtimeId());
            seatIdsValidator.validate(dto.getSeatIds());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}