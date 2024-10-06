package com.bms.bms.service;

import com.bms.bms.dto.screen.ScreenDTO;
import com.bms.bms.model.Screen;

import java.util.List;

public interface ScreenService {
    List<Screen> getScreensByTheatreId(Long theatreId);
    ScreenDTO getScreenById(Long screenId);
    ScreenDTO createScreen(Screen screen);
    ScreenDTO updateScreen(Screen screen);
    void deleteScreen(Long screenId);
}