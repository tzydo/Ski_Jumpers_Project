package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.Country;
import com.pl.skijumping.dto.CountryDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    @Mapping(target = "id", source = "id")
    CountryDTO toDTO(Country country);

    List<CountryDTO> toDTO(List<Country> countryList);

    @InheritInverseConfiguration
    Country fromDTO(CountryDTO countryDTO);

    List<Country> fromDTO(List<CountryDTO> countryDTOList);
}
