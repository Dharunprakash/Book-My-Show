package com.bms.bms.service.impl;

import com.bms.bms.dao.ScreenDao;
import com.bms.bms.dao.impl.ScreenDaoImpl;
import com.bms.bms.dto.ScreenDTO;
import com.bms.bms.model.Screen;
import com.bms.bms.service.ScreenService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScreenServiceImpl implements ScreenService {
    private final ScreenDao screenDao;

    public ScreenServiceImpl() throws SQLException, ClassNotFoundException {
        this.screenDao = new ScreenDaoImpl();
    }

    @Override
    public List<ScreenDTO> getScreensByTheatreId(Long theatreId) {
        return screenDao.getScreensByTheatreId(theatreId);
    }

    @Override
    public ScreenDTO getScreenById(Long screenId) {
        Optional<Screen> screen = screenDao.findById(screenId);
        return screen.map(ScreenDTO::fromScreen).orElse(null);
    }

    @Override
    public ScreenDTO createScreen(ScreenDTO screenDTO) {
        Screen screen = screenDTO.toScreen();
        Screen savedScreen = screenDao.save(screen);
        return ScreenDTO.fromScreen(savedScreen);
    }

    @Override
    public ScreenDTO updateScreen(ScreenDTO screenDTO) {
        Screen screen = screenDTO.toScreen();
        Screen updatedScreen = screenDao.update(screen);
        return ScreenDTO.fromScreen(updatedScreen);
    }

    @Override
    public void deleteScreen(Long screenId) {
        screenDao.delete(screenId);
    }
}