package com.bms.bms.service.impl;

import com.bms.bms.dao.ScreenDao;
import com.bms.bms.dao.impl.ScreenDaoImpl;
import com.bms.bms.dto.screen.ScreenDTO;
import com.bms.bms.model.Screen;
import com.bms.bms.service.ScreenService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ScreenServiceImpl implements ScreenService {
    private final ScreenDao screenDao;

    public ScreenServiceImpl() {
        this.screenDao = new ScreenDaoImpl();
    }

    @Override
    public List<Screen> getScreensByTheatreId(Long theatreId) {
        return screenDao.getScreensByTheatreId(theatreId);
    }

    @Override
    public ScreenDTO getScreenById(Long screenId) {
        Optional<Screen> screen = screenDao.findById(screenId);
        return screen.map(ScreenDTO::fromScreen).orElse(null);
    }

    @Override
    public ScreenDTO createScreen(Screen screen) {
        Screen savedScreen = screenDao.save(screen);
        return ScreenDTO.fromScreen(savedScreen);
    }

    @Override
    public ScreenDTO updateScreen(Screen screen) {
        Screen updatedScreen = screenDao.update(screen);
        return ScreenDTO.fromScreen(updatedScreen);
    }

    @Override
    public void deleteScreen(Long screenId) {
        screenDao.delete(screenId);
    }
}