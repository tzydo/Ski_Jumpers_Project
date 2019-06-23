package com.pl.skijumping.batch.importdataraceevent.writer;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.batch.importdataraceevent.reader.FindRaceData;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.DataRaceService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.*;

public class FindRaceDataWriterTest {

    @Test
    public void writeWhenExistInDBTest() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        DataRaceService mockDataRaceService = Mockito.mock(DataRaceService.class);
        CompetitionTypeService mockCompetitionTypeService = Mockito.mock(CompetitionTypeService.class);
        DataRaceDTO expectedDataRaceDTO = new DataRaceDTO().id(99L);
        Mockito.when(mockDataRaceService.findByRaceId(1L)).thenReturn(Optional.of(expectedDataRaceDTO));

        FindRaceDataWriter findRaceData = new FindRaceDataWriter(mockCompetitionTypeService, mockDataRaceService, diagnosticMonitorMock);
        Assertions.assertThat(findRaceData.write(new DataRaceDTO().raceId(1L))).isEqualToComparingFieldByFieldRecursively(expectedDataRaceDTO);
    }

    @Test
    public void writeWhenNotExistInDBTest() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        DataRaceService mockDataRaceService = Mockito.mock(DataRaceService.class);
        Mockito.when(mockDataRaceService.findByRaceId(1L)).thenReturn(Optional.empty());
        CompetitionTypeService mockCompetitionTypeService = Mockito.mock(CompetitionTypeService.class);
        Mockito.when(mockCompetitionTypeService.findByType("test")).thenReturn(Optional.of(new CompetitionTypeDTO()));

        DataRaceDTO expectedDataRaceDTO = new DataRaceDTO().id(99L).competitionType("test");
        Mockito.when(mockDataRaceService.findByRaceId(1L)).thenReturn(Optional.of(expectedDataRaceDTO));

        FindRaceDataWriter findRaceData = new FindRaceDataWriter(mockCompetitionTypeService, mockDataRaceService, diagnosticMonitorMock);
        Assertions.assertThat(findRaceData.write(new DataRaceDTO().raceId(1L))).isEqualToComparingFieldByFieldRecursively(expectedDataRaceDTO);
    }
}