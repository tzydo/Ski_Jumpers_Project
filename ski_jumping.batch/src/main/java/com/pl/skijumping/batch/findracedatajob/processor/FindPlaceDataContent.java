package com.pl.skijumping.batch.findracedatajob.processor;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.batch.reader.DataReader;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.CountryDTO;
import com.pl.skijumping.dto.PlaceDTO;
import com.pl.skijumping.service.CountryService;

import java.nio.file.Path;

class FindPlaceDataContent {

    private final DiagnosticMonitor diagnosticMonitor;
    private final CountryService countryService;

    FindPlaceDataContent(DiagnosticMonitor diagnosticMonitor, CountryService countryService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.countryService = countryService;
    }

    PlaceDTO getPlaceData(Path filePath) throws InternalServiceException {
        if (filePath == null || !filePath.toFile().exists()) {
            diagnosticMonitor.logWarn("Cannot read data from not existing file: " + filePath);
            return null;
        }

        String fileContent = getFileContent(filePath);
        if (fileContent == null || fileContent.isEmpty()) {
            diagnosticMonitor.logError("Cannot read words from file: " + filePath, getClass());
            return null;
        }

        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String word = matchingWords.getRaceDataCity(fileContent);

        if (word == null) {
            String errorMessage = String.format("Not found city data in file: %s", filePath);
            diagnosticMonitor.logError(errorMessage, getClass());
            throw new InternalServiceException(errorMessage);
        }

        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setCity(getCity(word));
        CountryDTO countryDTO = countryService.findByShortName(getShortCountryName(word));

        if (countryDTO != null) {
            placeDTO.setCountry(countryDTO.getId());
        }
        return placeDTO;
    }

    private String getCity(String word) {
        return word.split("\\(")[0].replace(" ", "");
    }

    private String getShortCountryName(String city) {
        return city.split("\\(")[1].split("\\)")[0];
    }

    private String getFileContent(Path filePath) {
        DataReader dataReader = new DataReader(diagnosticMonitor);
        return dataReader.read(filePath.toString());
    }
}
