package com.bms.bms.service;

import com.bms.bms.dto.ShowtimeDTO;
import com.bms.bms.dto.ShowtimeSeatsDTO;

import java.util.List;

public interface ShowtimeService {
    List<ShowtimeDTO> getShowtimesByScreenId(Long screenId);
   ShowtimeSeatsDTO getShowtimeById(Long id);
    ShowtimeDTO createShowtime(ShowtimeDTO showtimeDTO);
    ShowtimeDTO updateShowtime(ShowtimeDTO showtimeDTO);
    void deleteShowtime(Long id);
    List<ShowtimeDTO> getShowtimesByScreenIdAndDate(Long screenId, String date);
}