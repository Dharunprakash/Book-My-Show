package com.bms.bms.service.impl;

import com.bms.bms.dao.TheatreDao;
import com.bms.bms.dto.TheatreDTO;
import com.bms.bms.model.Theatre;
import com.bms.bms.service.TheatreService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TheatreServiceImpl implements TheatreService {

    private final TheatreDao theatreDao;

    public TheatreServiceImpl(TheatreDao theatreDao) {
        this.theatreDao = theatreDao;
    }

    @Override
    public List<TheatreDTO> getAllTheatres() {
        List<Theatre> theatres = theatreDao.findAll();
        return theatres.stream()
                .map(TheatreDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public TheatreDTO getTheatreById(Long id) {
        Optional<Theatre> theatre = theatreDao.findById(id);
        return theatre.map(TheatreDTO::from).orElse(null);
    }

    @Override
    public TheatreDTO createTheatre(TheatreDTO theatreDTO) {
        Theatre theatre = theatreDTO.toTheatre();
        Theatre savedTheatre = theatreDao.save(theatre);
        return TheatreDTO.from(savedTheatre);
    }

    @Override
    public TheatreDTO updateTheatre(Long id, TheatreDTO theatreDTO) {
        Theatre theatre = theatreDTO.toTheatre();
        theatre.setId(id);
        Theatre updatedTheatre = theatreDao.update(theatre);
        return TheatreDTO.from(updatedTheatre);
    }

    @Override
    public void deleteTheatre(Long id) {
        theatreDao.delete(id);
    }
}