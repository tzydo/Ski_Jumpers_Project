package com.pl.skijumping.batch.findracedatajob.processor;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.CountryDTO;
import com.pl.skijumping.dto.PlaceDTO;
import com.pl.skijumping.service.CountryService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FindPlaceDataContentTest {

    private static final String FILE_PATCH = "event_2018_43167.txt";

    @Test
    public void getPlaceDataTest() throws IOException, InternalServiceException {
        CountryService mockCountryService = Mockito.mock(CountryService.class);
        Mockito.when(mockCountryService.findByShortName(Mockito.anyString())).thenReturn(new CountryDTO().id(1));

        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindPlaceDataContent findPlaceDataContent = new FindPlaceDataContent(diagnosticMonitorMock, mockCountryService);
        Path pathToFile = FileUtil.getPath(FileUtil.getResource(), FILE_PATCH);
        PlaceDTO placeData = findPlaceDataContent.getPlaceData(pathToFile);

        PlaceDTO expectedPlaceDTO = new PlaceDTO().city("Oberstdorf").country(1);
        Assertions.assertThat(placeData).isEqualToComparingFieldByField(expectedPlaceDTO);
    }

    @Test
    public void getPlaceDataTestWhenNullFileTest() throws InternalServiceException {
        CountryService mockCountryService = Mockito.mock(CountryService.class);
        Mockito.when(mockCountryService.findByShortName(Mockito.anyString())).thenReturn(new CountryDTO().id(1));
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindPlaceDataContent findPlaceDataContent = new FindPlaceDataContent(diagnosticMonitorMock, mockCountryService);
        PlaceDTO placeData = findPlaceDataContent.getPlaceData(null);
        Assertions.assertThat(placeData).isNull();
    }

    @Test
    public void getPlaceDataTestWhenNotFoundCityTest() throws IOException {
        Path filePath = Files.write(Files.createTempFile("test", ".txt").toAbsolutePath(), "test test".getBytes());
        CountryService mockCountryService = Mockito.mock(CountryService.class);
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindPlaceDataContent findPlaceDataContent = new FindPlaceDataContent(diagnosticMonitorMock, mockCountryService);
        Throwable throwable = Assertions.catchThrowable(() -> findPlaceDataContent.getPlaceData(filePath));
        filePath.toFile().delete();

        Assertions.assertThat(throwable).isInstanceOf(InternalServiceException.class);
    }
}