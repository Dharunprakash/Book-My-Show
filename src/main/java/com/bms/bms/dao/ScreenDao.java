package com.bms.bms.dao;

import com.bms.bms.model.Screen;

import java.util.List;

public interface ScreenDao extends GenericDao<Screen, Long> {
    List<Screen> getScreensByTheatreId(Long theatreId);
}