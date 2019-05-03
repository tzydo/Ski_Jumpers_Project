package com.pl.skijumping.service;

import com.pl.skijumping.domain.repository.CountryRepository;
import com.pl.skijumping.dto.CountryDTO;
import com.pl.skijumping.service.mapper.CountryMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService {
    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;

    public CountryService(CountryMapper countryMapper,
                          CountryRepository countryRepository) {
        this.countryMapper = countryMapper;
        this.countryRepository = countryRepository;
    }

    public Optional<CountryDTO> findByShortName(String shortName) {
        if (shortName == null || shortName.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(countryMapper.toDTO(countryRepository.findCountriesByShortName(shortName)));
    }
}
