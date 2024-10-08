package com.bms.bms.controller.api.theatre;

import com.bms.bms.dto.ShowDTO;
import com.bms.bms.router.Router;
import com.bms.bms.service.TheatreService;
import com.bms.bms.dto.TheatreDTO;
import com.bms.bms.service.impl.TheatreServiceImpl;
import com.bms.bms.utils.HttpRequestParser;
import com.bms.bms.utils.PathParamExtractor;
import com.bms.bms.utils.QueryParamExtractor;
import com.bms.bms.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TheatreRouter {
    private final TheatreService theatreService = new TheatreServiceImpl();
    private static final Logger LOGGER = Logger.getLogger(TheatreRouter.class.getName());

    public void register(Router router) {
        router.get("/:id", this::getTheatre);
        router.get("/", this::getAllTheatre);
        router.post("/", this::createTheatre);
        router.put("/:id", this::updateTheatre);
        router.get("/:id/shows", this::getShows);
        router.get("/:id/shows/:showId", this::getShowById);
        router.delete("/:id", this::deleteTheatre);
    }



    private void getTheatre(HttpServletRequest req, HttpServletResponse resp) {
        try {
            System.out.println("TheatreRouter getTheatre");
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)", TheatreParams.class);
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

    @NoArgsConstructor
    @Data
    public static class TheatreQueryParams {
        private String location;
    }
    private void getAllTheatre(HttpServletRequest req, HttpServletResponse resp) {
        try {
            System.out.println("TheatreRouter getAllTheatre "+req.getQueryString());
            TheatreQueryParams qp = QueryParamExtractor.extractQueryParams(req, TheatreQueryParams.class);
            System.out.println("TheatreRouter getAllTheatre "+qp);
            if (qp.getLocation() != null) {
                List<TheatreDTO> theatres = theatreService.getTheatresByLocation(qp.getLocation());
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Theatres found", theatres);
                return;
            }
            List<TheatreDTO> theatres = theatreService.getAllTheatres();

            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Theatres found", theatres);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getAllTheatre", e);
            handleException(req, resp, e);
        }
    }

    private void getShows(HttpServletRequest req, HttpServletResponse resp) {
        try {
            System.out.println("TheatreRouter getShows");
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)/shows", TheatreParams.class);
            List<ShowDTO> theatres = theatreService.getShowsByTheatreId(params.getId());
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Shows found", theatres);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getShows", e);
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
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)", TheatreParams.class);
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
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)", TheatreParams.class);
            theatreService.deleteTheatre(params.getId());
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Theatre deleted", null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in deleteTheatre", e);
            handleException(req, resp, e);
        }
    }
    public static class showtimeParams {
        private Long id;
    }
    private void getShowById(HttpServletRequest req, HttpServletResponse resp) {

        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)/shows/(\\d+)", showtimeParams.class);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getShowById", e);
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