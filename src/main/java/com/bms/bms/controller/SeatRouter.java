package com.bms.bms.controller.api.seats;

import com.bms.bms.dto.SeatDTO;
import com.bms.bms.router.Router;
import com.bms.bms.service.SeatService;
import com.bms.bms.service.impl.SeatServiceImpl;
import com.bms.bms.utils.HttpRequestParser;
import com.bms.bms.utils.PathParamExtractor;
import com.bms.bms.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeatRouter {
    private final SeatService seatService;
    private static final Logger LOGGER = Logger.getLogger(SeatRouter.class.getName());

    public SeatRouter() {
        try {
            seatService = new SeatServiceImpl();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(Router router) {
        router.get("/theatres/:theatreId/screens/:screenId/seats", this::getSeats);
        router.get("/theatres/:theatreId/screens/:screenId/seats/:seatId", this::getSeatById);
        router.post("/theatres/:theatreId/screens/:screenId/seats", this::createSeat);
        router.put("/theatres/:theatreId/screens/:screenId/seats/:seatId", this::updateSeat);
        router.delete("/theatres/:theatreId/screens/:screenId/seats/:seatId", this::deleteSeat);
    }

    private void getSeats(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/theatres/(\\d+)/screens/(\\d+)/seats", SeatParams.class);
            List<SeatDTO> seats = seatService.getSeatsByScreenId(params.getScreenId());
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Seats found", seats);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getSeats", e);
            handleException(req, resp, e);
        }
    }

    private void getSeatById(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/theatres/(\\d+)/screens/(\\d+)/seats/(\\d+)", SeatParams.class);
            SeatDTO seat = seatService.getSeatById(params.getSeatId());
            if (seat != null) {
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Seat found", seat);
            } else {
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_NOT_FOUND, "Seat not found", null);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getSeatById", e);
            handleException(req, resp, e);
        }
    }

    private void createSeat(HttpServletRequest req, HttpServletResponse resp) {
        try {
            SeatDTO seatDTO = HttpRequestParser.parse(req, SeatDTO.class);
            SeatDTO createdSeat = seatService.createSeat(seatDTO);
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_CREATED, "Seat created", createdSeat);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in createSeat", e);
            handleException(req, resp, e);
        }
    }

    private void updateSeat(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/theatres/(\\d+)/screens/(\\d+)/seats/(\\d+)", SeatParams.class);
            SeatDTO seatDTO = HttpRequestParser.parse(req, SeatDTO.class);
            seatDTO.setId(params.getSeatId());
            SeatDTO updatedSeat = seatService.updateSeat(seatDTO);
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Seat updated", updatedSeat);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in updateSeat", e);
            handleException(req, resp, e);
        }
    }

    private void deleteSeat(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/theatres/(\\d+)/screens/(\\d+)/seats/(\\d+)", SeatParams.class);
            seatService.deleteSeat(params.getSeatId());
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Seat deleted", null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in deleteSeat", e);
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
    public static class SeatParams {
        private Long theatreId;
        private Long screenId;
        private Long seatId;
    }
}