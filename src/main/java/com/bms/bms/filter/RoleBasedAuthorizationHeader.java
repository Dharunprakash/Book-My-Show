package com.bms.bms.filter;

import com.bms.bms.utils.TokenUtil;
import io.jsonwebtoken.Claims;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebFilter("/api/*")
public class RoleBasedAuthorizationHeader implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        try {
            Claims claims = (Claims) httpRequest.getAttribute("claims");

            // Extract roles from the token
            String userIdRole = claims.getSubject();
            String role = userIdRole.split("-")[1];
            List<String> roles = List.of(role.split(","));

            // Check if the user has the required role
            if (isAccessAllowed(httpRequest, roles)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Insufficient permissions");
            }
        } catch (Exception e) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAccessAllowed(HttpServletRequest request, List<String> roles) {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        if (roles.contains("ADMIN")) {
            return true; // Admins can access all routes
        }

        if (roles.contains("USER")) {
            if ("GET".equalsIgnoreCase(method)) {
                return true; // Users can access all GET routes
            }
            else return "POST".equalsIgnoreCase(method) && "/api/bookings".equals(uri); // Users can access POST /api/bookings
        }

        return false; // Access denied for other cases
    }
}