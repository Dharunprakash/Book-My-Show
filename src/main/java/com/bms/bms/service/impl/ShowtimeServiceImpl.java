package com.bms.bms.service.impl;

import com.bms.bms.dao.ShowtimeDao;
import com.bms.bms.dao.impl.ShowtimeDaoImpl;
import com.bms.bms.dto.ShowtimeDTO;
import com.bms.bms.dto.ShowtimeSeatsDTO;
import com.bms.bms.model.Showtime;
import com.bms.bms.service.ShowtimeService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ShowtimeServiceImpl implements ShowtimeService {
    private final ShowtimeDao showtimeDao;

    public ShowtimeServiceImpl() throws SQLException, ClassNotFoundException {
        this.showtimeDao = new ShowtimeDaoImpl();
    }

    @Override
    public List<ShowtimeDTO> getShowtimesByScreenId(Long screenId) {
        return showtimeDao.getShowtimesByScreenId(screenId);
    }

    @Override
    public ShowtimeSeatsDTO getShowtimeById(Long id) {

        return showtimeDao.getShowtimeById(id);

    }

    @Override
    public ShowtimeDTO createShowtime(ShowtimeDTO showtimeDTO) {
        Showtime showtime = showtimeDTO.toShowtime();
        Showtime savedShowtime = showtimeDao.save(showtime);
        return ShowtimeDTO.fromShowtime(savedShowtime);
    }

    @Override
    public ShowtimeDTO updateShowtime(ShowtimeDTO showtimeDTO) {
        Showtime showtime = showtimeDTO.toShowtime();
        Showtime updatedShowtime = showtimeDao.update(showtime);
        return ShowtimeDTO.fromShowtime(updatedShowtime);
    }

    @Override
    public void deleteShowtime(Long id) {
        showtimeDao.delete(id);
    }

    @Override
    public List<ShowtimeDTO> getShowtimesByScreenIdAndDate(Long screenId, String date) {
        return showtimeDao.getShowtimesByScreenIdAndDate(screenId, date);
    }
}