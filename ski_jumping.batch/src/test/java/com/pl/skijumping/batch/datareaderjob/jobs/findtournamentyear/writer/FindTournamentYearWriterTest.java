package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.writer;

import com.pl.skijumping.batch.BatchApplicationTest;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.TournamentYearDTO;
import com.pl.skijumping.service.TournamentYearService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = BatchApplicationTest.class)
public class FindTournamentYearWriterTest {

    @Autowired
    private TournamentYearService tournamentYearService;

    @Mock
    private DiagnosticMonitor diagnosticMonitor;

    @Test
    @Transactional
    public void writeTest() {
        List<String> matchingWords = Arrays.asList("1234", "3211", "1222", "1222");

        FindTournamentYearWriter findTournamentYearWriter = new FindTournamentYearWriter(tournamentYearService, diagnosticMonitor);
        findTournamentYearWriter.write(Collections.singletonList(matchingWords));

        List<TournamentYearDTO> tournamentYearDTOS = tournamentYearService.findAll();
        Assertions.assertThat(tournamentYearDTOS.isEmpty()).isFalse();
        Assertions.assertThat(tournamentYearDTOS).isNotEmpty();
        Assertions.assertThat(tournamentYearDTOS).hasSize(3);

        List<String> actualTournamentYearList = tournamentYearDTOS.stream()
                .map(TournamentYearDTO::getYear)
                .collect(Collectors.toList());

        Assertions.assertThat(actualTournamentYearList).containsAll(matchingWords);
    }

    @Test
    public void writeNullTest() {
        FindTournamentYearWriter findTournamentYearWriter = new FindTournamentYearWriter(tournamentYearService, diagnosticMonitor);
        findTournamentYearWriter.write(null);
        List<TournamentYearDTO> tournamentYearDTOS = tournamentYearService.findAll();
        Assertions.assertThat(tournamentYearDTOS).isEmpty();
    }

    @Test
    public void writeEmptyListTest() {
        FindTournamentYearWriter findTournamentYearWriter = new FindTournamentYearWriter(tournamentYearService, diagnosticMonitor);
        findTournamentYearWriter.write(new ArrayList<>());
        List<TournamentYearDTO> tournamentYearDTOS = tournamentYearService.findAll();
        Assertions.assertThat(tournamentYearDTOS).isEmpty();
    }
}