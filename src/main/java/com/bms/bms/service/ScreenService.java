package com.bms.bms.service;

import com.bms.bms.dto.ScreenDTO;

import java.util.List;

public interface ScreenService {
    List<ScreenDTO> getScreensByTheatreId(Long theatreId);
    ScreenDTO getScreenById(Long screenId);
    ScreenDTO createScreen(ScreenDTO screenDTO);
    ScreenDTO updateScreen(ScreenDTO screenDTO);
    void deleteScreen(Long screenId);
}