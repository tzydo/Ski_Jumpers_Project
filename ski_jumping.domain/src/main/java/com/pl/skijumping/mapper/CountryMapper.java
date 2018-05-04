package com.pl.skijumping.mapper;

import com.pl.skijumping.dto.CountryDTO;
import com.pl.skijumping.entity.Country;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

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
