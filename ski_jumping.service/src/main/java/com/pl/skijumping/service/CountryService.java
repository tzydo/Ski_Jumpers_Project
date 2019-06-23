package com.pl.skijumping.service;

import com.pl.skijumping.domain.repository.CountryRepository;
import com.pl.skijumping.dto.CountryDTO;
import com.pl.skijumping.service.mapper.CountryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional
    public CountryDTO save(CountryDTO countryDTO) {
        if(countryDTO == null) {
            return null;
        }

        return countryMapper.toDTO(countryRepository.save(countryMapper.fromDTO(countryDTO)));
    }

    public List<CountryDTO> findAll() {
        return countryMapper.toDTO(countryRepository.findAll());
    }

    public Optional<CountryDTO> findByShortName(String shortName) {
        if (shortName == null || shortName.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(countryMapper.toDTO(countryRepository.findCountriesByShortName(shortName)));
    }
}
