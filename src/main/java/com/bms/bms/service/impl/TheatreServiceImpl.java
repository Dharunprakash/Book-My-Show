package com.bms.bms.service.impl;

import com.bms.bms.dao.TheatreDao;
import com.bms.bms.dao.impl.TheatreDaoImpl;
import com.bms.bms.dto.ShowDTO;
import com.bms.bms.dto.TheatreDTO;
import com.bms.bms.model.Theatre;
import com.bms.bms.service.TheatreService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TheatreServiceImpl implements TheatreService {

    private final TheatreDao theatreDao = new TheatreDaoImpl();

    @Override
    public List<TheatreDTO> getAllTheatres() {
        List<Theatre> theatres = theatreDao.findAll();
        return theatres.stream()
                .map(TheatreDTO::fromTheatre)
                .collect(Collectors.toList());
    }

    @Override
    public TheatreDTO getTheatreById(Long id) {
        Optional<Theatre> theatre = theatreDao.findById(id);
        return theatre.map(TheatreDTO::fromTheatre).orElse(null);
    }

    @Override
    public List<ShowDTO> getShowsByTheatreId(Long theatreId) {
        return theatreDao.getShowsByTheatreId(theatreId);
    }

    @Override
    public TheatreDTO createTheatre(TheatreDTO theatreDTO) {
        Theatre theatre = theatreDTO.toTheatre();
        Theatre savedTheatre = theatreDao.save(theatre);
        return TheatreDTO.fromTheatre(savedTheatre);
    }

    @Override
    public TheatreDTO updateTheatre(Long id, TheatreDTO theatreDTO) {
        Theatre theatre = theatreDTO.toTheatre();
        theatre.setId(id);
        Theatre updatedTheatre = theatreDao.update(theatre);
        return TheatreDTO.fromTheatre(updatedTheatre);
    }

    @Override
    public void deleteTheatre(Long id) {
        theatreDao.delete(id);
    }

    @Override
    public List<TheatreDTO> getTheatresByLocation(String location) {
        List<Theatre> theatres = theatreDao.getTheatresByLocation(location);
        return theatres.stream()
                .map(TheatreDTO::fromTheatre)
                .collect(Collectors.toList());
    }
}