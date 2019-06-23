package com.pl.skijumping.batch.importplaceevent;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.batch.reader.DataReader;
import com.pl.skijumping.batch.util.BasicDataParser;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.CountryDTO;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessagePropertiesConst;
import com.pl.skijumping.dto.PlaceDTO;
import com.pl.skijumping.service.CountryService;

class ParsePlaceData {

    private static final String HILL_SIZE_CONST = "HS";
    private final DiagnosticMonitor diagnosticMonitor;
    private final CountryService countryService;

    ParsePlaceData(DiagnosticMonitor diagnosticMonitor,
                          CountryService countryService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.countryService = countryService;
    }

    PlaceDTO parse(MessageDTO messageDTO) throws InternalServiceException {
        String competitionType = messageDTO.getProperties().getStringValue(MessagePropertiesConst.COMPETITION_TYPE.getValue());

        DataReader dataReader = new DataReader(diagnosticMonitor);
        String readSource = dataReader.read(messageDTO.getFilePath());

        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String city = matchingWords.getRaceDataCity(readSource);

        if (city == null) {
            String errorMessage = String.format("Not found city data in file: %s", messageDTO.getFilePath());
            diagnosticMonitor.logError(errorMessage, getClass());
            throw new InternalServiceException(errorMessage);
        }

        CountryDTO countryDTO = countryService.findByShortName(getShortCountryName(city))
                .orElseThrow(() -> new InternalServiceException(String.format("Not found country for pattern: %s", city)));

        String hillSize = competitionType.toUpperCase().split(HILL_SIZE_CONST)[1];
        Integer size = BasicDataParser.toInt(hillSize);
        return new PlaceDTO()
                .hillType(size != null ? HillSizeUtil.checkHillType(size).getHillType() : null)
                .hillSize(size)
                .city(getCity(city))
                .country(countryDTO.getId());
    }

    private String getCity(String word) {
        return word.split("\\(")[0].replace(" ", "");
    }

    private String getShortCountryName(String city) {
        return city.split("\\(")[1].split("\\)")[0];
    }
}
