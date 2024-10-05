package com.bms.bms.controller.auth;

import com.bms.bms.dto.SessionUserDTO;
import com.bms.bms.service.AuthService;
import com.bms.bms.utils.ResponseUtil;
import com.bms.bms.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/auth/current-user")
public class CurrentUserServlet extends HttpServlet {
    private final AuthService authService = new AuthService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("AUTH_TOKEN".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        if (token == null) {
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_UNAUTHORIZED, "No authenticated user", null);
            return;
        }

        try {
            Claims claims = TokenUtil.validateToken(token);
            String userIdRole = claims.getSubject();
            String userId = userIdRole.split("-")[0];
            var user = authService.getUserById(Long.parseLong(userId));
            if (user.isEmpty()) {
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_UNAUTHORIZED, "User not found", null);
                return;
            }
            var sessionUser = SessionUserDTO.fromUser(user.get());
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "User found", sessionUser);
        } catch (Exception e) {
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token", null);
        }
    }
}