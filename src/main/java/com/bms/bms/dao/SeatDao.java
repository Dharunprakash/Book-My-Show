package com.bms.bms.dao;

import com.bms.bms.dto.SeatDTO;
import com.bms.bms.model.Seat;

import java.util.List;

public interface SeatDao extends GenericDao<Seat, Long> {
    List<SeatDTO> getSeatsByScreenId(Long screenId);
}