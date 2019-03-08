package com.pl.skijumping.service;

import com.pl.skijumping.domain.repository.PlaceRepository;
import com.pl.skijumping.service.mapper.PlaceMapper;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    public PlaceService(PlaceRepository placeRepository, PlaceMapper placeMapper) {
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
    }
}
