package com.bms.bms.service;

import com.bms.bms.dto.ShowDTO;
import com.bms.bms.dto.TheatreDTO;
import java.util.List;

public interface TheatreService {
    List<TheatreDTO> getAllTheatres();
    TheatreDTO getTheatreById(Long id);
    TheatreDTO createTheatre(TheatreDTO theatreDTO);
    TheatreDTO updateTheatre(Long id, TheatreDTO theatreDTO);
    void deleteTheatre(Long id);
    List<ShowDTO> getShowsByTheatreId(Long theatreId);
}