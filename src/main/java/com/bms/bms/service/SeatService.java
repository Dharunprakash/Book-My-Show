package com.bms.bms.service;

import com.bms.bms.dto.SeatDTO;

import java.util.List;

public interface SeatService {
    List<SeatDTO> getSeatsByScreenId(Long screenId);
    SeatDTO getSeatById(Long seatId);
    SeatDTO createSeat(SeatDTO seatDTO);
    SeatDTO updateSeat(SeatDTO seatDTO);
    void deleteSeat(Long seatId);
}