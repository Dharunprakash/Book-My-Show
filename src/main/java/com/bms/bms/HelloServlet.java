package com.bms.bms;

import java.io.*;
import java.sql.Connection;

import com.bms.bms.utils.DataBaseUtil;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;



    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


    }

}