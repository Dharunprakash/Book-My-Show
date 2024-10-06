package com.bms.bms.controller.api.movies;

import com.bms.bms.dto.MovieDTO;
import com.bms.bms.dto.ScreeningDTO;
import com.bms.bms.router.Router;
import com.bms.bms.service.MovieService;
import com.bms.bms.service.impl.MovieServiceImpl;
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

public class MovieRouter {
    private final MovieService movieService;
    private static final Logger LOGGER = Logger.getLogger(MovieRouter.class.getName());

    public MovieRouter() {
        try {
            movieService = new MovieServiceImpl();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(Router router) {
        router.get("/", this::getMovies);
        router.get("/:movieId", this::getMovieById);
        router.get("/:movieId/screenings", this::getMovieScreenings);
        router.post("/", this::createMovie);
        router.put("/:movieId", this::updateMovie);
        router.delete("/:movieId", this::deleteMovie);
    }

    private void getMovies(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String date = req.getParameter("date");
            List<MovieDTO> movies = movieService.getMovies(date);
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Movies found", movies);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getMovies", e);
            handleException(req, resp, e);
        }
    }

    private void getMovieById(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)", MovieParams.class);
            MovieDTO movie = movieService.getMovieById(params.getId());
            if (movie != null) {
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Movie found", movie);
            } else {
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_NOT_FOUND, "Movie not found", null);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getMovieById", e);
            handleException(req, resp, e);
        }
    }

    private void getMovieScreenings(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)/screenings", MovieParams.class);
            List<ScreeningDTO> screenings = movieService.getMovieScreenings(params.getId());
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Screenings found", screenings);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getMovieScreenings", e);
            handleException(req, resp, e);
        }
    }

    private void createMovie(HttpServletRequest req, HttpServletResponse resp) {
        try {
            MovieDTO movieDTO = HttpRequestParser.parse(req, MovieDTO.class);
            MovieDTO createdMovie = movieService.createMovie(movieDTO);
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_CREATED, "Movie created", createdMovie);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in createMovie", e);
            handleException(req, resp, e);
        }
    }

    private void updateMovie(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)", MovieParams.class);
            MovieDTO movieDTO = HttpRequestParser.parse(req, MovieDTO.class);
            movieDTO.setId(params.getId());
            MovieDTO updatedMovie = movieService.updateMovie(movieDTO);
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Movie updated", updatedMovie);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in updateMovie", e);
            handleException(req, resp, e);
        }
    }

    private void deleteMovie(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)", MovieParams.class);
            movieService.deleteMovie(params.getId());
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Movie deleted", null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in deleteMovie", e);
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
    public static class MovieParams {
        private Long id;
    }
}