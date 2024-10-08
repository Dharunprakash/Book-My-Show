package com.bms.bms.controller.api.theatre.screens;

import com.bms.bms.dto.screen.CreateScreenDTO;
import com.bms.bms.dto.screen.ScreenDTO;
import com.bms.bms.model.Screen;
import com.bms.bms.router.Router;
import com.bms.bms.service.ScreenService;
import com.bms.bms.service.impl.ScreenServiceImpl;
import com.bms.bms.utils.HttpRequestParser;
import com.bms.bms.utils.PathParamExtractor;
import com.bms.bms.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScreenRouter {
    private final ScreenService screenService = new ScreenServiceImpl();
    private static final Logger LOGGER = Logger.getLogger(ScreenRouter.class.getName());

    public void register(Router router) {
        router.get("/:theatreId/screens", this::getScreens);
        router.get("/:theatreId/screens/:screenId", this::getScreenById);
        router.post("/:theatreId/screens", this::createScreen);
        router.put("/:theatreId/screens/:screenId", this::updateScreen);
        router.delete("/:theatreId/screens/:screenId", this::deleteScreen);
    }

    private void getScreens(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)/screens", ScreenParams.class);
            List<ScreenDTO> screens = new ArrayList<>();
            for (var screen : screenService.getScreensByTheatreId(params.getTheatreId())) {
                screens.add(ScreenDTO.fromScreen(screen));
            }

            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Screens found", screens);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getScreens", e);
            handleException(req, resp, e);
        }
    }

    private void getScreenById(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)/screens/(\\d+)", ScreenParams.class);
            ScreenDTO screen = screenService.getScreenById(params.getScreenId());
            if (screen != null) {
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Screen found", screen);
            } else {
                ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_NOT_FOUND, "Screen not found", null);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in getScreenById", e);
            handleException(req, resp, e);
        }
    }

    private void createScreen(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)/screens", ScreenParams.class);
            Screen screen = HttpRequestParser.parse(req, CreateScreenDTO.class).toScreen();
            screen.setTheaterId(params.getTheatreId());
            ScreenDTO createdScreen = screenService.createScreen(screen);
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_CREATED, "Screen created", createdScreen);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in createScreen", e);
            handleException(req, resp, e);
        }
    }

    private void updateScreen(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)/screens/(\\d+)", ScreenParams.class);
            Screen screen = HttpRequestParser.parse(req, CreateScreenDTO.class).toScreen();
            screen.setId(params.getScreenId());
            screen.setTheaterId(params.getTheatreId());
            System.out.println(params);
            System.out.println(screen);
            ScreenDTO updatedScreen = screenService.updateScreen(screen);
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Screen updated", updatedScreen);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in updateScreen", e);
            handleException(req, resp, e);
        }
    }

    private void deleteScreen(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var params = PathParamExtractor.extractPathParams(req.getPathInfo(), "/(\\d+)/screens/(\\d+)", ScreenParams.class);
            screenService.deleteScreen(params.getScreenId());
            ResponseUtil.sendResponse(req, resp, HttpServletResponse.SC_OK, "Screen deleted", null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in deleteScreen", e);
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
    public static class ScreenParams {
        private Long theatreId;
        private Long screenId;
    }
}