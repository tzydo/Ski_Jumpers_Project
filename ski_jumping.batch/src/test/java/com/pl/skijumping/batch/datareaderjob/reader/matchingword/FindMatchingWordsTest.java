package com.pl.skijumping.batch.datareaderjob.reader.matchingword;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FindMatchingWordsTest {

    @Mock
    private DiagnosticMonitor diagnosticMonitor;

    @Test
    public void getSeasonDataWhenWordsNullTest() {
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        Optional<List<String>> seasonData = findMatchingWords.getSeasonData(null, null, true);
        Assertions.assertThat(seasonData.isPresent()).isFalse();
        Assertions.assertThat(seasonData).isEqualTo(Optional.empty());
    }

    @Test
    public void getSeasonDataWhenRegexpNullTest() {
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        Optional<List<String>> seasonData = findMatchingWords.getSeasonData("test", null, true);
        Assertions.assertThat(seasonData.isPresent()).isFalse();
        Assertions.assertThat(seasonData).isEqualTo(Optional.empty());

        seasonData = findMatchingWords.getSeasonData("test", null, false);
        Assertions.assertThat(seasonData.isPresent()).isFalse();
        Assertions.assertThat(seasonData).isEqualTo(Optional.empty());
    }
}