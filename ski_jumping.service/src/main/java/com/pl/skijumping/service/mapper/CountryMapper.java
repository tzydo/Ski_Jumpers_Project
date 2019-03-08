package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.Country;
import com.pl.skijumping.dto.CountryDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryMapper COUNTRY_MAPPER = Mappers.getMapper(CountryMapper.class);

    @Mapping(source = "id", target = "id")
    CountryDTO toDTO(Country country);
    List<CountryDTO> toDTO(List<Country> country);

    @InheritInverseConfiguration
    Country fromDTO(CountryDTO countryDTO);
    List<Country> fromDTO(List<CountryDTO> countryDTO);
}