package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.writer;

import com.pl.skijumping.batch.BatchApplicationTest;
import com.pl.skijumping.domain.dto.TournamentYearDTO;
import com.pl.skijumping.service.TournamentYearService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    @Transactional
    public void writeTest() {
        List<String> matchingWords = Arrays.asList("1234", "3211", "1222", "1222");

        FindTournamentYearWriter findTournamentYearWriter = new FindTournamentYearWriter(tournamentYearService);
        findTournamentYearWriter.write(Collections.singletonList(matchingWords));

        Optional<List<TournamentYearDTO>> tournamentYearDTOS = tournamentYearService.findAll();
        Assertions.assertThat(tournamentYearDTOS.isPresent()).isTrue();
        Assertions.assertThat(tournamentYearDTOS.get()).isNotEmpty();
        Assertions.assertThat(tournamentYearDTOS.get()).hasSize(3);

        List<String> actualTournamentYearList = tournamentYearDTOS.get().stream()
                .map(e -> e.getYear())
                .collect(Collectors.toList());

        Assertions.assertThat(actualTournamentYearList).containsAll(matchingWords);
    }

    @Test
    public void writeNullTest() {
        FindTournamentYearWriter findTournamentYearWriter = new FindTournamentYearWriter(tournamentYearService);
        findTournamentYearWriter.write(null);
        Optional<List<TournamentYearDTO>> tournamentYearDTOS = tournamentYearService.findAll();
        Assertions.assertThat(tournamentYearDTOS.isPresent()).isTrue();
        Assertions.assertThat(tournamentYearDTOS.get()).isEmpty();
    }

    @Test
    public void writeEmptyListTest() {
        FindTournamentYearWriter findTournamentYearWriter = new FindTournamentYearWriter(tournamentYearService);
        findTournamentYearWriter.write(new ArrayList<>());
        Optional<List<TournamentYearDTO>> tournamentYearDTOS = tournamentYearService.findAll();
        Assertions.assertThat(tournamentYearDTOS.isPresent()).isTrue();
        Assertions.assertThat(tournamentYearDTOS.get()).isEmpty();
    }
}