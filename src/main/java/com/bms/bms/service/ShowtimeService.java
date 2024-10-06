package com.bms.bms.service;

import com.bms.bms.dto.ShowtimeDTO;

import java.util.List;

public interface ShowtimeService {
    List<ShowtimeDTO> getShowtimesByScreenId(Long screenId);
    ShowtimeDTO getShowtimeById(Long id);
    ShowtimeDTO createShowtime(ShowtimeDTO showtimeDTO);
    ShowtimeDTO updateShowtime(ShowtimeDTO showtimeDTO);
    void deleteShowtime(Long id);
}