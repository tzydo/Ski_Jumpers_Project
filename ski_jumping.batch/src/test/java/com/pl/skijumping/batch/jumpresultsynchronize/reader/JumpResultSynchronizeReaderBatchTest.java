package com.pl.skijumping.batch.jumpresultsynchronize.reader;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.common.util.Pair;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.service.DataRaceService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class JumpResultSynchronizeReaderBatchTest {

    @Mock
    private DataRaceService dataRaceService;
    @Mock
    private HtmlDownloader htmlDownloader;
    private DiagnosticMonitor diagnosticMonitor;

    @Test
    public void openTestWhenRaceDataExistThenCreatePairOfRaceDataIdAndHost() throws NoSuchFieldException, IllegalAccessException {
        String host = "testHost";
        diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        Mockito.when(dataRaceService.getRaceDataIds()).thenReturn(Arrays.asList(1L, 2L, 3L));

        JumpResultSynchronizeReaderBatch jumpResultSynchronizeReaderBatch = new JumpResultSynchronizeReaderBatch(dataRaceService, diagnosticMonitor, htmlDownloader, host);

        jumpResultSynchronizeReaderBatch.open(null);


        Field iterator = JumpResultSynchronizeReaderBatch.class.getDeclaredField("dataRaceIdAndHostIterator");
        iterator.setAccessible(true);
        Iterator<Pair<Long, String>> jumpResultReaderIterator =
                (Iterator<Pair<Long, String>>) iterator.get(jumpResultSynchronizeReaderBatch);

        List<Pair<Long, String>> jumpResultIteratorList = new ArrayList<>();
        jumpResultReaderIterator.forEachRemaining(jumpResultIteratorList::add);

        Assertions.assertThat(jumpResultIteratorList.isEmpty()).isFalse();
        Assertions.assertThat(jumpResultIteratorList).hasSize(3);

        List<Pair<Long, String>> expectedJumpResultIterator = Arrays.asList(
                new Pair<>(1L, host + "1"),
                new Pair<>(2L, host + "2"),
                new Pair<>(3L, host + "3")
        );

        Assertions.assertThat(jumpResultIteratorList).containsAll(expectedJumpResultIterator);
    }

    @Test
    public void openTestWhenRaceDataDoesNotExistThenReturnNull() throws NoSuchFieldException, IllegalAccessException {
        String host = "testHost";
        diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        Mockito.when(dataRaceService.getRaceDataIds()).thenReturn(new ArrayList<>());

        JumpResultSynchronizeReaderBatch jumpResultSynchronizeReaderBatch = new JumpResultSynchronizeReaderBatch(dataRaceService, diagnosticMonitor, htmlDownloader, host);

        jumpResultSynchronizeReaderBatch.open(null);

        Field iterator = JumpResultSynchronizeReaderBatch.class.getDeclaredField("dataRaceIdAndHostIterator");
        iterator.setAccessible(true);
        Iterator<Pair<Long, String>> jumpResultReaderIterator =
                (Iterator<Pair<Long, String>>) iterator.get(jumpResultSynchronizeReaderBatch);

        Assertions.assertThat(jumpResultReaderIterator).isNull();
    }

    @Test
    public void readTestWhenHasRaceDataIdPairsThenReturnIdAndPageSource() throws Exception {
        String host = "testHost";
        diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        Mockito.when(dataRaceService.getRaceDataIds()).thenReturn(Arrays.asList(1L, 2L, 3L));

        Mockito.when(htmlDownloader.downloadToString("testHost1")).thenReturn("first");
        Mockito.when(htmlDownloader.downloadToString("testHost2")).thenReturn("second");
        Mockito.when(htmlDownloader.downloadToString("testHost3")).thenReturn("third");


        JumpResultSynchronizeReaderBatch jumpResultSynchronizeReaderBatch = new JumpResultSynchronizeReaderBatch(dataRaceService, diagnosticMonitor, htmlDownloader, host);

        jumpResultSynchronizeReaderBatch.open(null);

        List<Pair<Long, String>> expectedPairs = Arrays.asList(
                new Pair<>(1L, "first"),
                new Pair<>(2L, "second"),
                new Pair<>(3L, "third")
        );

        Assertions.assertThat(expectedPairs).contains(jumpResultSynchronizeReaderBatch.read());
        Assertions.assertThat(expectedPairs).contains(jumpResultSynchronizeReaderBatch.read());
        Assertions.assertThat(expectedPairs).contains(jumpResultSynchronizeReaderBatch.read());
        Assertions.assertThat(jumpResultSynchronizeReaderBatch.read()).isNull();
    }

    @Test
    public void readTestWhenNull() throws Exception {
        JumpResultSynchronizeReaderBatch jumpResultSynchronizeReaderBatch = new JumpResultSynchronizeReaderBatch(dataRaceService, diagnosticMonitor, htmlDownloader, null);
        Assertions.assertThat(jumpResultSynchronizeReaderBatch.read()).isNull();
    }
}