package com.pl.skijumping.service;

import com.pl.skijumping.dto.CountryDTO;
import com.pl.skijumping.mapper.CountryMapper;
import com.pl.skijumping.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    public Optional<List<CountryDTO>> getCountries() {
        List<CountryDTO> countryDTOList = countryMapper.toDTO(countryRepository.findAll());
        if (countryDTOList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(countryDTOList);
    }

    public Optional<List<CountryDTO>> getCountriesByPattern(String pattern) {
        List<CountryDTO> countryDTOList = countryMapper.toDTO(countryRepository.findAllByName(pattern));
        if(countryDTOList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(countryDTOList);
    }
}
