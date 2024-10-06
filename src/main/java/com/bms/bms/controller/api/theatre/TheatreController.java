package com.bms.bms.controller.api.theatre;

import com.bms.bms.controller.api.theatre.screens.ScreenRouter;
import com.bms.bms.controller.api.theatre.screens.seats.SeatRouter;
import com.bms.bms.controller.api.theatre.screens.showtimes.ShowtimeRouter;
import com.bms.bms.router.Router;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/theatres/*")
public class TheatreController extends HttpServlet {

    private Router router;

    public void init() throws ServletException {
        router = new Router("/bms_war_exploded/api/theatres");
        new TheatreRouter().register(router);
        new ScreenRouter().register(router);
        new ShowtimeRouter().register(router);
        new SeatRouter().register(router);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("TheatreController doGet");
        router.handle(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        router.handle(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        router.handle(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        router.handle(req, resp);
    }
}