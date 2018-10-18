package com.pl.skijumping.service;

import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.service.mapper.CompetitionTypeMapper;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationTest.class)
public class CompetitionTypeServiceTest {

    @Autowired
    private CompetitionTypeService competitionTypeService;

    @Test
    @Transactional
    public void saveTest() {
        CompetitionTypeDTO competitionTypeDTO = competitionTypeService.save(
                new CompetitionTypeDTO(null, "type", null));

        List<CompetitionTypeDTO> competitionTypeDTOList = competitionTypeService.findAll();
        Assertions.assertThat(competitionTypeDTOList).isNotEmpty();
        Assertions.assertThat(competitionTypeDTOList).hasSize(1);
        Assertions.assertThat(competitionTypeDTOList.get(0)).isEqualToComparingFieldByFieldRecursively(competitionTypeDTO);
    }

    @Test
    @Transactional
    public void saveWhenNullTest() {
        CompetitionTypeDTO competitionTypeDTO = competitionTypeService.save(null);

        List<CompetitionTypeDTO> competitionTypeDTOList = competitionTypeService.findAll();
        Assertions.assertThat(competitionTypeDTOList).isEmpty();
        Assertions.assertThat(competitionTypeDTO).isNull();
    }

    @Test
    public void findWhenNotExistTest() {
        Optional<CompetitionTypeDTO> competitionTypeDTO = competitionTypeService.find(-1l);
        Assertions.assertThat(competitionTypeDTO).isEqualTo(Optional.empty());
    }

    @Test
    @Transactional
    public void findByTypeTest() {
        CompetitionTypeDTO competitionTypeDTO = competitionTypeService.save(
                new CompetitionTypeDTO(null, "type", null));
        competitionTypeService.save(new CompetitionTypeDTO(null, "type2", null));
        competitionTypeService.save(new CompetitionTypeDTO(null, "type3", null));

        Optional<CompetitionTypeDTO> actualCompetitionTypeDTO = competitionTypeService.findByType("type");
        Assertions.assertThat(actualCompetitionTypeDTO.isPresent()).isTrue();
        Assertions.assertThat(actualCompetitionTypeDTO.get()).isEqualTo(competitionTypeDTO);

    }

    @Test
    public void findByTypeWhenNullTest() {
        Optional<CompetitionTypeDTO> competitionTypeDTO = competitionTypeService.findByType("test");

        Assertions.assertThat(competitionTypeDTO.isPresent()).isFalse();
    }
}