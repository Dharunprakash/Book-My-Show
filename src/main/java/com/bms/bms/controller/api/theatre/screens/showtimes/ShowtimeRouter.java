package com.bms.bms.controller.api.theatre.screens.showtimes;

import com.bms.bms.dto.ShowtimeDTO;
import com.bms.bms.dto.ShowtimeSeatsDTO;
import com.bms.bms.router.Router;
import com.bms.bms.service.ShowtimeService;
import com.bms.bms.service.impl.ShowtimeServiceImpl;
import com.bms.bms.utils.HttpRequestParser;
import com.bms.bms.utils.PathParamExtractor;
import com.bms.bms.utils.QueryParamExtractor;
import com.bms.bms.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowtimeRouter {
    private final ShowtimeService showtimeService;
    private static final Logger LOGGER = Logger.getLogger(ShowtimeRouter.class.getName());

    public ShowtimeRouter() {
        try {
            showtimeService = new ShowtimeServiceImpl();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(Router router) {
        router.get("/:theatreId/screens/:screenId/showtimes", this::getShowtimes);
       router.get("/:theatreId/screens/:screenId/showtimes/:id", this::getShowtimesById);
        router.post("/:theatreId/screens/:screenId/showtimes", this::createShowtime);
        router.put("/:theatreId/screens/:screenId/showtimes/:id", this::updateShowtime);
        router.delete("/:theatreId/screens/:screenId/showtimes/:id", this::deleteShowtime);
    }

    @NoArgsConstructor
    @Data
    public static class ShowtimeDateQueryParams {
        private String date;
    }

    private void getShowtimes(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)/screens/(\\d+)/showtimes", ShowtimeParams.class);
            System.out.println("paramas:"+params);

            ShowtimeDateQueryParams qp = QueryParamExtractor.extractQueryParams(req, ShowtimeDateQueryParams.class);
            if (qp.getDate() != null) {
                System.out.println("ShowtimeRouter getShowtimes " + qp.getDate());
                List<ShowtimeDTO> showtimes = showtimeService.getShowtimesByScreenIdAndDate(params.getScreenId(), qp.getDate());
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Showtimes found", showtimes);
                return;
            }
            List<ShowtimeDTO> showtimes = showtimeService.getShowtimesByScreenId(params.getScreenId());
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Showtimes found", showtimes);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getShowtimes", e);
            handleException(req, resp, e);
        }
    }

    private void getShowtimesById(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)/screens/(\\d+)/showtimes/(\\d+)", ShowtimeParams.class);
            ShowtimeSeatsDTO showtimeSeats = showtimeService.getShowtimeById(params.getId());
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Showtime found", showtimeSeats);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getShowtimesById", e);
            handleException(req, resp, e);
        }
    }

    private void createShowtime(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ShowtimeDTO showtimeDTO = HttpRequestParser.parse(req, ShowtimeDTO.class);
            ShowtimeDTO createdShowtime = showtimeService.createShowtime(showtimeDTO);
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_CREATED, "Showtime created", createdShowtime);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in createShowtime", e);
            handleException(req, resp, e);
        }
    }

    private void updateShowtime(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)/screens/(\\d+)/showtimes/(\\d+)", ShowtimeParams.class);
            ShowtimeDTO showtimeDTO = HttpRequestParser.parse(req, ShowtimeDTO.class);
            showtimeDTO.setId(params.getId());
            ShowtimeDTO updatedShowtime = showtimeService.updateShowtime(showtimeDTO);
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Showtime updated", updatedShowtime);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in updateShowtime", e);
            handleException(req, resp, e);
        }
    }

    private void deleteShowtime(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)/screens/(\\d+)/showtimes/(\\d+)", ShowtimeParams.class);
            showtimeService.deleteShowtime(params.getId());
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Showtime deleted", null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in deleteShowtime", e);
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
    public static class ShowtimeParams {
        private Long theatreId;
        private Long screenId;
        private Long id;
    }
}