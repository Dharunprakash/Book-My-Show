package com.bms.bms.dao;

import com.bms.bms.dto.ShowDTO;
import com.bms.bms.dto.TheatreDTO;
import com.bms.bms.model.Theatre;

import java.util.List;

public interface TheatreDao  extends GenericDao<Theatre, Long> {
    List<TheatreDTO> getAllTheatres();
    TheatreDTO getTheatreById(Long id);
    List<ShowDTO> getShowsByTheatreId(Long theatreId);
}