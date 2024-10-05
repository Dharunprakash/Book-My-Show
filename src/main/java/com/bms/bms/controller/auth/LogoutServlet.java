package com.bms.bms.controller.auth;

import com.bms.bms.utils.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Invalidate the session if exists
        if (req.getSession(false) != null) {
            req.getSession().invalidate();
        }

        // Remove the authentication token cookie
        Cookie tokenCookie = new Cookie("AUTH_TOKEN", null);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0); // Set the cookie to expire immediately

        resp.addCookie(tokenCookie);

        // Send response
        ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Logout successful", null);
    }
}