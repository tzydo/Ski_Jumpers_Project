package com.pl.skijumping.batch.importplaceevent;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.batch.reader.DataReader;
import com.pl.skijumping.batch.util.BasicDataParser;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.CountryDTO;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessageProperties;
import com.pl.skijumping.dto.PlaceDTO;
import com.pl.skijumping.service.CountryService;

import java.util.Optional;

class ParsePlaceData {

    private final DiagnosticMonitor diagnosticMonitor;
    private final CountryService countryService;

    ParsePlaceData(DiagnosticMonitor diagnosticMonitor,
                          CountryService countryService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.countryService = countryService;
    }

    PlaceDTO parse(MessageDTO messageDTO) throws InternalServiceException {
        String competitionType = (String) messageDTO.getProperties().get(MessageProperties.COMPETITION_TYPE.getValue());

        DataReader dataReader = new DataReader(diagnosticMonitor);
        String readSource = dataReader.read(messageDTO.getFilePath());

        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String city = matchingWords.getRaceDataCity(readSource);

        if (city == null) {
            String errorMessage = String.format("Not found city data in file: %s", messageDTO.getFilePath());
            diagnosticMonitor.logError(errorMessage, getClass());
            throw new InternalServiceException(errorMessage);
        }

        Optional<CountryDTO> countryDTO = countryService.findByShortName(getShortCountryName(city));

        String hillSize = competitionType.toUpperCase().split("HS")[1];
        Integer size = BasicDataParser.toInt(hillSize);
        return new PlaceDTO()
                .hillType(size != null ? HillSizeUtil.checkHillType(size).getHillType() : null)
                .city(getCity(city))
                .country(countryDTO.map(CountryDTO::getId).orElse(null));
    }

    private String getCity(String word) {
        return word.split("\\(")[0].replace(" ", "");
    }

    private String getShortCountryName(String city) {
        return city.split("\\(")[1].split("\\)")[0];
    }
}
