package com.bms.bms.controller.api.bookings;

import com.bms.bms.router.Router;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/bookings/*")
public class BookingsController extends HttpServlet {

    private Router router;
    @Override
    public void init(){
        router = new Router("/bms_war_exploded/api/bookings");
        new BookingsRouter().register(router);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, IOException {
        System.out.println("In doGet");
        router.handle(req, resp);
    }

    @Override
    public  void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, IOException {
        router.handle(req, resp);
    }

    @Override
    public  void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, IOException {
        router.handle(req, resp);
    }

    @Override
    public  void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, IOException {
        router.handle(req, resp);
    }



}
