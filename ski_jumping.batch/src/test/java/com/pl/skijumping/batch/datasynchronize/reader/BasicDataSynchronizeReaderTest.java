//package com.pl.skijumping.batch.datasynchronize.reader;
//
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.List;
//
//@RunWith(MockitoJUnitRunner.class)
//public class BasicDataSynchronizeReaderTest {
//
//    @Mock
//    private DiagnosticMonitor diagnosticMonitor;
//    private String directoryPath = "testTmp";
//
//    @Test
//    public void synchronizeTest() {
//        BasicDataSynchronizeReader basicDataSynchronize = new BasicDataSynchronizeReader(directoryPath, diagnosticMonitor);
//        List<String> wordsList = basicDataSynchronize.synchronize();
//        Assertions.assertThat(wordsList).isNotEmpty();
//        Assertions.assertThat(wordsList).hasSize(368);
//    }
//
//    @Test
//    public void synchronizeWhenNullTest() {
//        BasicDataSynchronizeReader basicDataSynchronize = new BasicDataSynchronizeReader(null, diagnosticMonitor);
//        List<String> wordsList = basicDataSynchronize.synchronize();
//        Assertions.assertThat(wordsList).isEmpty();
//    }
//
//    @Test
//    public void synchronizeWhenEmptyFileTest() {
//        BasicDataSynchronizeReader basicDataSynchronize = new BasicDataSynchronizeReader("empty", diagnosticMonitor);
//        List<String> wordsList = basicDataSynchronize.synchronize();
//        Assertions.assertThat(wordsList).isEmpty();
//    }
//
//    @Test
//    public void synchronizeWhenDoesNotExistFileTest() {
//        BasicDataSynchronizeReader basicDataSynchronize = new BasicDataSynchronizeReader("notExist", diagnosticMonitor);
//        List<String> wordsList = basicDataSynchronize.synchronize();
//        Assertions.assertThat(wordsList).isEmpty();
//    }
//}