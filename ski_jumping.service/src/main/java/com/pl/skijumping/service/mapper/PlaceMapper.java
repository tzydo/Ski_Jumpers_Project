package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.Place;
import com.pl.skijumping.domain.repository.CountryRepository;
import com.pl.skijumping.dto.PlaceDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PlaceMapper {

    @Autowired
    CountryRepository countryRepository;

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "country.id", target = "country")
    })
    public abstract PlaceDTO toDTO(Place place);

    public abstract List<PlaceDTO> toDTO(List<Place> placeList);

    @InheritInverseConfiguration
    public abstract Place fromDTO(PlaceDTO placeDTO);

    public abstract List<Place> fromDTO(List<PlaceDTO> placeDTOList);

    @AfterMapping
    public Place setCountry(@MappingTarget Place place, PlaceDTO placeDTO) {
        place.setCountry(countryRepository.findOne(placeDTO.getCountry()));
        return place;
    }
}
