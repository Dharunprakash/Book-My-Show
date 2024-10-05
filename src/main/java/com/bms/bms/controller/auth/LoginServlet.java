package com.bms.bms.controller.auth;

import com.bms.bms.dto.SessionUserDTO;
import com.bms.bms.model.User;
import com.bms.bms.service.AuthService;
import com.bms.bms.utils.HttpRequestParser;
import com.bms.bms.utils.ResponseUtil;
import com.bms.bms.utils.TokenUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

    private final AuthService authService;

    public LoginServlet() throws SQLException, ClassNotFoundException {
        this.authService = new AuthService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = HttpRequestParser.parse(req, User.class);

        Optional<User> userOpt = authService.loginUser(user);

        if (userOpt.isEmpty()) {
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials", null);
            return;
        }
        var sessionUser = SessionUserDTO.fromUser(userOpt.get());

        // Generate token
        String token = TokenUtil.generateToken(sessionUser.getId().toString());

        // Set token as cookie
        Cookie tokenCookie = new Cookie("AUTH_TOKEN", token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(30 * 60); // 30 minutes expiration

        resp.addCookie(tokenCookie);
        ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Login successful", sessionUser);
    }
}