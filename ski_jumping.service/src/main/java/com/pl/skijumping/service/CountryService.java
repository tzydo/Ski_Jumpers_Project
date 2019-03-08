package com.pl.skijumping.service;

import com.pl.skijumping.domain.repository.CountryRepository;
import com.pl.skijumping.service.mapper.CountryMapper;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;

    public CountryService(CountryMapper countryMapper,
                          CountryRepository countryRepository) {
        this.countryMapper = countryMapper;
        this.countryRepository = countryRepository;
    }
}
