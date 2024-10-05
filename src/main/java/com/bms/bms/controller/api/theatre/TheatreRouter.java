package com.bms.bms.controller.api.theatre;

import com.bms.bms.router.Router;
import com.bms.bms.service.TheatreService;
import com.bms.bms.dto.TheatreDTO;
import com.bms.bms.utils.HttpRequestParser;
import com.bms.bms.utils.PathParamExtractor;
import com.bms.bms.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TheatreRouter {
    private final TheatreService theatreService;
    private static final Logger LOGGER = Logger.getLogger(TheatreRouter.class.getName());

    public TheatreRouter(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    public void register(Router router) {
        router.get("/theatre/:id", this::getTheatre);
        router.get("/theatre", this::getAllTheatre);
        router.post("/theatre", this::createTheatre);
        router.put("/theatre/:id", this::updateTheatre);
        router.delete("/theatre/:id", this::deleteTheatre);
    }

    private void getTheatre(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/theatre/(\\d+)", TheatreParams.class);
            TheatreDTO theatre = theatreService.getTheatreById(params.getId());
            if (theatre != null) {
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Theatre found", theatre);
            } else {
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_NOT_FOUND, "Theatre not found", null);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getTheatre", e);
            handleException(req, resp, e);
        }
    }

    private void getAllTheatre(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<TheatreDTO> theatres = theatreService.getAllTheatres();
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Theatres found", theatres);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getAllTheatre", e);
            handleException(req, resp, e);
        }
    }

    private void createTheatre(HttpServletRequest req, HttpServletResponse resp) {
        try {
            TheatreDTO theatreDTO = HttpRequestParser.parse(req, TheatreDTO.class);
            TheatreDTO createdTheatre = theatreService.createTheatre(theatreDTO);
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_CREATED, "Theatre created", createdTheatre);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in createTheatre", e);
            handleException(req, resp, e);
        }
    }

    private void updateTheatre(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/theatre/(\\d+)", TheatreParams.class);
            TheatreDTO theatreDTO = HttpRequestParser.parse(req, TheatreDTO.class);
            theatreDTO.setId(params.getId());
            TheatreDTO updatedTheatre = theatreService.updateTheatre(params.getId(), theatreDTO);
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Theatre updated", updatedTheatre);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in updateTheatre", e);
            handleException(req, resp, e);
        }
    }

    private void deleteTheatre(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/theatre/(\\d+)", TheatreParams.class);
            theatreService.deleteTheatre(params.getId());
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Theatre deleted", null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in deleteTheatre", e);
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
    public static class TheatreParams {
        private Long id;
    }
}