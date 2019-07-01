package com.pl.skijumping.batch.importplaceevent;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.model.HillType;
import com.pl.skijumping.dto.CountryDTO;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessagePropertiesConst;
import com.pl.skijumping.dto.PlaceDTO;
import com.pl.skijumping.service.CountryService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParsePlaceDataTest {
    private final String fileName = "import_2019_43851.txt";

    @Test
    public void parseTest() throws IOException, InternalServiceException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        CountryService mockCountryService = Mockito.mock(CountryService.class);

        Map<String, Object> property = new HashMap<>();
        property.put(MessagePropertiesConst.COMPETITION_TYPE.getValue(), "HS109");

        CountryDTO countryDTO = new CountryDTO().id(1).name("test").shortName("tt");
        Mockito.when(mockCountryService.findByShortName(Mockito.anyString())).thenReturn(Optional.of(countryDTO));

        MessageDTO messageDTO = new MessageDTO()
                .filePath(Paths.get(new ClassPathResource(fileName).getURI()).toString())
                .properties(property);
        ParsePlaceData parsePlaceData = new ParsePlaceData(diagnosticMonitorMock, mockCountryService);
        PlaceDTO actualPlaceDTO = parsePlaceData.parse(messageDTO);

        PlaceDTO expectedPlaceDTO = new PlaceDTO()
                .country(1)
                .city("Kranj")
                .hillSize(109)
                .hillType(HillType.NORMAL_HILL.getHillType());

        Assertions.assertThat(actualPlaceDTO).isEqualToComparingFieldByFieldRecursively(expectedPlaceDTO);
    }

    @Test
    public void parseWhenCityNotFound() throws IOException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        CountryService mockCountryService = Mockito.mock(CountryService.class);

        Map<String, Object> property = new HashMap<>();
        property.put(MessagePropertiesConst.COMPETITION_TYPE.getValue(), "HS109");

        MessageDTO messageDTO = new MessageDTO()
                .filePath(Paths.get(new ClassPathResource("emptyTest.txt").getURI()).toString())
                .properties(property);
        ParsePlaceData parsePlaceData = new ParsePlaceData(diagnosticMonitorMock, mockCountryService);
        Throwable throwable = Assertions.catchThrowable(() -> parsePlaceData.parse(messageDTO));
        Assertions.assertThat(throwable).isInstanceOf(InternalServiceException.class);
    }

    @Test
    public void parseWhenNotFoundCountryTest() throws IOException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        CountryService mockCountryService = Mockito.mock(CountryService.class);

        Map<String, Object> property = new HashMap<>();
        property.put(MessagePropertiesConst.COMPETITION_TYPE.getValue(), "HS109");

        Mockito.when(mockCountryService.findByShortName(Mockito.anyString())).thenReturn(Optional.empty());

        MessageDTO messageDTO = new MessageDTO()
                .filePath(Paths.get(new ClassPathResource(fileName).getURI()).toString())
                .properties(property);
        ParsePlaceData parsePlaceData = new ParsePlaceData(diagnosticMonitorMock, mockCountryService);
        Throwable throwable = Assertions.catchThrowable(() -> parsePlaceData.parse(messageDTO));
        Assertions.assertThat(throwable).isInstanceOf(InternalServiceException.class);
    }
}