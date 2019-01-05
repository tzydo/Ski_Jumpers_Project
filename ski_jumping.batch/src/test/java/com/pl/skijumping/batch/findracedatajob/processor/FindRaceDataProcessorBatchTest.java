//package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor;
//
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import com.pl.skijumping.dto.DataRaceDTO;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(MockitoJUnitRunner.class)
//public class FindRaceDataProcessorBatchTest {
//    @Mock
//    private DiagnosticMonitor diagnosticMonitor;
//
//    @Test
//    public void processWhenNullTest() throws Exception {
//        FindRaceDataProcessorBatch findRaceDataProcessorBatch = new FindRaceDataProcessorBatch(diagnosticMonitor);
//        List<DataRaceDTO> dataRaceDTO = findRaceDataProcessorBatch.process(null);
//        Assertions.assertThat(dataRaceDTO).isEqualTo(null);
//    }
//
//    @Test
//    public void processTest() throws Exception {
//        FindRaceDataProcessorBatch findRaceDataProcessorBatch = new FindRaceDataProcessorBatch(diagnosticMonitor);
//        List<DataRaceDTO> dataRaceDTO = findRaceDataProcessorBatch.process("testWord");
//        Assertions.assertThat(dataRaceDTO).isEqualTo(new ArrayList<>());
//    }
//}