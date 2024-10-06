package com.bms.bms.filter.validator;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.bms.bms.dto.CreateBookingDTO;
import com.bms.bms.utils.CachedBodyHttpServletRequest;
import com.bms.bms.utils.HttpRequestParser;
import com.bms.bms.utils.ResponseUtil;

import java.io.IOException;

@WebFilter(urlPatterns = {"/api/booking"})
public class BookingFilter implements Filter {

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
            if (createDTO != null && isValidCreateBooking(createDTO)) {
                chain.doFilter(request, response);
            } else {
                ResponseUtil.sendResponse(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid booking data", null);
            }
        } catch (Exception e) {
            ResponseUtil.sendResponse(request, response, HttpServletResponse.SC_BAD_REQUEST, "Invalid booking data", null);
        }
    }

    private boolean isValidCreateBooking(CreateBookingDTO dto) {
        return dto.getSeatIds() != null && dto.getUserId() != null & dto.getShowtimeId() != null;
    }
}