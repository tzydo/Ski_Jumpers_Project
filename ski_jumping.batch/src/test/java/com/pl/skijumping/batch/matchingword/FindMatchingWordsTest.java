//package com.pl.skijumping.batch.matchingword;
//
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.List;
//import java.util.Optional;
//
//@RunWith(MockitoJUnitRunner.class)
//public class FindMatchingWordsTest {
//
//    @Mock
//    private DiagnosticMonitor diagnosticMonitor;
//
//    @Test
//    public void getSeasonDataWhenWordsNullTest() {
//        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
//        Optional<List<String>> seasonData = findMatchingWords.getMatchingWords(null, null, true);
//        Assertions.assertThat(seasonData.isPresent()).isFalse();
//        Assertions.assertThat(seasonData).isEqualTo(Optional.empty());
//    }
//
//    @Test
//    public void getSeasonDataWhenRegexpNullTest() {
//        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
//        Optional<List<String>> seasonData = findMatchingWords.getMatchingWords("test", null, true);
//        Assertions.assertThat(seasonData.isPresent()).isFalse();
//        Assertions.assertThat(seasonData).isEqualTo(Optional.empty());
//
//        seasonData = findMatchingWords.getMatchingWords("test", null, false);
//        Assertions.assertThat(seasonData.isPresent()).isFalse();
//        Assertions.assertThat(seasonData).isEqualTo(Optional.empty());
//    }
//}