package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.TournamentYear;
import com.pl.skijumping.domain.repository.TournamentYearRepository;
import com.pl.skijumping.dto.TournamentYearDTO;
import com.pl.skijumping.service.mapper.TournamentYearMapper;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationTest.class)
public class TournamentYearServiceTest {

    @Autowired
    private TournamentYearMapper tournamentYearMapper;
    @Autowired
    private TournamentYearRepository tournamentYearRepository;

    @Test
    public void findAllByTopTest() {
        int limit = 2;
        TournamentYear firstTournamentYear = tournamentYearRepository.save(new TournamentYear().year("2019"));
        TournamentYear secondTournamentYear = tournamentYearRepository.save(new TournamentYear().year("2018"));
        tournamentYearRepository.save(new TournamentYear().year("2017"));
        tournamentYearRepository.save(new TournamentYear().year("2016"));

        TournamentYearService tournamentYearService = new TournamentYearService(tournamentYearRepository, tournamentYearMapper);

        List<TournamentYearDTO> actualTournamentYear = tournamentYearService.findAllByTop(limit);
        Assertions.assertThat(actualTournamentYear).isNotEmpty();
        Assertions.assertThat(actualTournamentYear).isNotEqualTo(new ArrayList<>());
        Assertions.assertThat(actualTournamentYear).isEqualTo(tournamentYearMapper.toDTO(Arrays.asList(firstTournamentYear, secondTournamentYear)));
    }
}