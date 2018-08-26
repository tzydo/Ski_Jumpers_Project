package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.CompetitionName;
import com.pl.skijumping.dto.CompetitionNameDTO;
import com.pl.skijumping.service.ApplicationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class CompetitionNameMapperTest {
    @Autowired
    private CompetitionNameMapper competitionNameMapper;

    @Test
    public void fromDTOTest() {
        CompetitionNameDTO competitionNameDTO = new CompetitionNameDTO().id(1l).name("name");
        CompetitionName expectedCompetitionName = new CompetitionName().id(1l).name("name");

        Assertions.assertThat(competitionNameMapper.fromDTO(competitionNameDTO))
                .isEqualToComparingFieldByFieldRecursively(expectedCompetitionName);
    }

    @Test
    public void fromDTOListTest() {
        List<CompetitionName> expectedCompetitionNameList = Arrays.asList(
                new CompetitionName().name("name").id(1l),
                new CompetitionName().name("name2").id(2l),
                new CompetitionName().name("name3").id(3l)
        );

        List<CompetitionNameDTO> competitionNameDTOList = Arrays.asList(
                new CompetitionNameDTO().name("name").id(1l),
                new CompetitionNameDTO().name("name2").id(2l),
                new CompetitionNameDTO().name("name3").id(3l)
        );

        Assertions.assertThat(competitionNameMapper.fromDTO(competitionNameDTOList)).containsAll(expectedCompetitionNameList);
    }

    @Test
    public void toDTOTest() {
        CompetitionNameDTO expectedCompetitionNameDTO = new CompetitionNameDTO().name("name").id(1l);
        CompetitionName competitionName = new CompetitionName().id(1l).name("name");

        Assertions.assertThat(competitionNameMapper.toDTO(competitionName))
                .isEqualToComparingFieldByFieldRecursively(expectedCompetitionNameDTO);
    }

    @Test
    public void toDTOListTest() {
        List<CompetitionName> competitionNameList = Arrays.asList(
                new CompetitionName().name("name").id(1l),
                new CompetitionName().name("name2").id(2l),
                new CompetitionName().name("name3").id(3l)
        );

        List<CompetitionNameDTO> expectedCompetitionNameDTOList = Arrays.asList(
                new CompetitionNameDTO().name("name").id(1l),
                new CompetitionNameDTO().name("name2").id(2l),
                new CompetitionNameDTO().name("name3").id(3l)
        );

        Assertions.assertThat(competitionNameMapper.toDTO(competitionNameList)).containsAll(expectedCompetitionNameDTOList);
    }
}