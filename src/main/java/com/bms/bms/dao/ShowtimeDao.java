package com.bms.bms.dao;

import com.bms.bms.dto.ShowtimeDTO;
import com.bms.bms.model.Showtime;

import java.util.List;

public interface ShowtimeDao extends GenericDao<Showtime, Long> {
    List<ShowtimeDTO> getShowtimesByScreenId(Long screenId);
}