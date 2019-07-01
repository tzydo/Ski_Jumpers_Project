package com.pl.skijumping.batch.importplaceevent;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.entity.Country;
import com.pl.skijumping.domain.entity.DataRaceToPlace;
import com.pl.skijumping.dto.*;
import com.pl.skijumping.service.CountryService;
import com.pl.skijumping.service.DataRaceToPlaceService;
import com.pl.skijumping.service.PlaceService;
import org.junit.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.*;

public class ImportPlaceEventListenerTest {
    private static final String FILE_NAME = "place_obersdorf_43175_2019.txt";

    @Test
    public void importPlaceTest() throws IOException, InternalServiceException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        CountryService countryService = Mockito.mock(CountryService.class);
        PlaceService placeService = Mockito.mock(PlaceService.class);
        DataRaceToPlaceService dataRaceToPlaceService = Mockito.mock(DataRaceToPlaceService.class);
        PlaceDTO placeDTO = new PlaceDTO().id(1).country(1).city("Oberstdorf").hillSize(235).hillType("SKI_FLYING_HILL");

        Mockito.when(countryService.findByShortName(Mockito.anyString())).thenReturn(Optional.of(new CountryDTO().shortName("ger").id(1)));
        Mockito.when(placeService.findByCityAndHillType(Mockito.any())).thenReturn(Optional.of(placeDTO));
        Mockito.when(dataRaceToPlaceService.findByDataRaceToPlace(Mockito.any())).thenReturn(Optional.empty());

        Path originalityPath = Paths.get(new ClassPathResource(FILE_NAME).getURI());
        Path tempFile = Files.createTempFile("testFile", "txt");
        String filePath = Files.copy(originalityPath, tempFile, StandardCopyOption.REPLACE_EXISTING).toString();

        MessageDTO messageDTO = new MessageDTO()
                .filePath(filePath)
                .properties(new HashMap<String, Object>() {{
                    put(MessagePropertiesConst.DARA_RACE_ID.getValue(), 1L);
                    put(MessagePropertiesConst.COMPETITION_TYPE.getValue(), "HS235");
                }});
        ImportPlaceEventListener importPlaceEventListener = new ImportPlaceEventListener(diagnosticMonitorMock, countryService, placeService, dataRaceToPlaceService);
        importPlaceEventListener.importPlace(messageDTO);
        Mockito.verify(placeService, Mockito.times(1)).findByCityAndHillType(placeDTO.id(null));
        Mockito.verify(dataRaceToPlaceService, Mockito.times(1)).save(new DataRaceToPlaceDTO().placeId(1).dataRaceId(1L));
    }

    @Test
    public void importPlaceTestWhenPlaceNotExist() throws IOException, InternalServiceException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        CountryService countryService = Mockito.mock(CountryService.class);
        PlaceService placeService = Mockito.mock(PlaceService.class);
        DataRaceToPlaceService dataRaceToPlaceService = Mockito.mock(DataRaceToPlaceService.class);
        PlaceDTO placeDTO = new PlaceDTO().id(1).country(1).city("Oberstdorf").hillSize(235).hillType("SKI_FLYING_HILL");

        Mockito.when(countryService.findByShortName(Mockito.anyString())).thenReturn(Optional.of(new CountryDTO().shortName("ger").id(1)));
        Mockito.when(placeService.findByCityAndHillType(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(placeService.save(Mockito.any())).thenReturn(placeDTO);
        Mockito.when(dataRaceToPlaceService.findByDataRaceToPlace(Mockito.any())).thenReturn(Optional.empty());

        Path originalityPath = Paths.get(new ClassPathResource(FILE_NAME).getURI());
        Path tempFile = Files.createTempFile("testFile", "txt");
        String filePath = Files.copy(originalityPath, tempFile, StandardCopyOption.REPLACE_EXISTING).toString();

        MessageDTO messageDTO = new MessageDTO()
                .filePath(filePath)
                .properties(new HashMap<String, Object>() {{
                    put(MessagePropertiesConst.DARA_RACE_ID.getValue(), 1L);
                    put(MessagePropertiesConst.COMPETITION_TYPE.getValue(), "HS235");
                }});
        ImportPlaceEventListener importPlaceEventListener = new ImportPlaceEventListener(diagnosticMonitorMock, countryService, placeService, dataRaceToPlaceService);
        importPlaceEventListener.importPlace(messageDTO);
        Mockito.verify(placeService, Mockito.times(1)).findByCityAndHillType(placeDTO.id(null));
        Mockito.verify(placeService, Mockito.times(1)).save(placeDTO.id(null));
        Mockito.verify(dataRaceToPlaceService, Mockito.times(1)).save(new DataRaceToPlaceDTO().placeId(1).dataRaceId(1L));
    }
}