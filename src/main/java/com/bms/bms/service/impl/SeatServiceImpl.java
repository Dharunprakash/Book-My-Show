package com.bms.bms.service.impl;

import com.bms.bms.dao.SeatDao;
import com.bms.bms.dao.impl.SeatDaoImpl;
import com.bms.bms.dto.SeatDTO;
import com.bms.bms.model.Seat;
import com.bms.bms.service.SeatService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SeatServiceImpl implements SeatService {
    private final SeatDao seatDao;

    public SeatServiceImpl() throws SQLException, ClassNotFoundException {
        this.seatDao = new SeatDaoImpl();
    }

    @Override
    public List<SeatDTO> getSeatsByScreenId(Long screenId) {
        return seatDao.getSeatsByScreenId(screenId);
    }

    @Override
    public SeatDTO getSeatById(Long seatId) {
        Optional<Seat> seat = seatDao.findById(seatId);
        return seat.map(SeatDTO::fromSeat).orElse(null);
    }

    @Override
    public SeatDTO createSeat(SeatDTO seatDTO) {
        Seat seat = seatDTO.toSeat();
        Seat savedSeat = seatDao.save(seat);
        return SeatDTO.fromSeat(savedSeat);
    }

    @Override
    public SeatDTO updateSeat(SeatDTO seatDTO) {
        Seat seat = seatDTO.toSeat();
        Seat updatedSeat = seatDao.update(seat);
        return SeatDTO.fromSeat(updatedSeat);
    }

    @Override
    public void deleteSeat(Long seatId) {
        seatDao.delete(seatId);
    }
}