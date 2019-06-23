package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.Place;
import com.pl.skijumping.domain.model.HillType;
import com.pl.skijumping.domain.repository.PlaceRepository;
import com.pl.skijumping.dto.PlaceDTO;
import com.pl.skijumping.service.mapper.PlaceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    public PlaceService(PlaceRepository placeRepository, PlaceMapper placeMapper) {
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
    }

    @Transactional
    public PlaceDTO save(PlaceDTO placeDTO) {
        Place place = placeRepository.save(placeMapper.fromDTO(placeDTO));
        return placeMapper.toDTO(place);
    }

    @Transactional
    public Optional<PlaceDTO> findByCityAndHillType(PlaceDTO placeDTO) {
        Place place = placeRepository.findByCityAndAndHillType(placeDTO.getCity(), HillType.valueOf(placeDTO.getHillType()));
        if(place == null) {
            return Optional.empty();
        }

        return Optional.of(placeMapper.toDTO(place));
    }
}
