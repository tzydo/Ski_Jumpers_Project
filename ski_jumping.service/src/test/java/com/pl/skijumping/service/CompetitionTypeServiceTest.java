package com.pl.skijumping.service;

import com.pl.skijumping.domain.repository.CompetitionTypeRepository;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.service.mapper.CompetitionTypeMapper;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class CompetitionTypeServiceTest {
    @Autowired
    private CompetitionTypeRepository competitionTypeRepository;
    @Autowired
    private CompetitionTypeMapper competitionTypeMapper;

    @Test
    @Transactional
    public void saveTest() {
        CompetitionTypeDTO competitionTypeDTO = new CompetitionTypeDTO(null, "type", "name");

        CompetitionTypeService competitionTypeService = new CompetitionTypeService(competitionTypeRepository, competitionTypeMapper);
        competitionTypeService.save(competitionTypeDTO);

        List<CompetitionTypeDTO> competitionTypeDTOList = competitionTypeService.findAll();
        Assertions.assertThat(competitionTypeDTOList).isNotEmpty();
        Assertions.assertThat(competitionTypeDTOList).hasSize(1);
        competitionTypeDTO.setId(1l);
        Assertions.assertThat(competitionTypeDTOList.get(0)).isEqualToComparingFieldByFieldRecursively(competitionTypeDTO);
    }

    @Test
    public void saveWhenNullTest() {
        CompetitionTypeService competitionTypeService = new CompetitionTypeService(competitionTypeRepository, competitionTypeMapper);
        CompetitionTypeDTO competitionTypeDTO = competitionTypeService.save(null);

        List<CompetitionTypeDTO> competitionTypeDTOList = competitionTypeService.findAll();
        Assertions.assertThat(competitionTypeDTOList).isEmpty();
        Assertions.assertThat(competitionTypeDTO).isNull();
    }

    @Test
    public void findWhenNotExistTest() {
        CompetitionTypeService competitionTypeService = new CompetitionTypeService(competitionTypeRepository, competitionTypeMapper);
        Optional<CompetitionTypeDTO> competitionTypeDTO = competitionTypeService.find(1l);
        Assertions.assertThat(competitionTypeDTO).isEqualTo(Optional.empty());
    }

    @Test
    @Transactional
    public void findByTypeAndNameTest() {
        CompetitionTypeService competitionTypeService = new CompetitionTypeService(competitionTypeRepository, competitionTypeMapper);
         competitionTypeService.save(new CompetitionTypeDTO(null, "type", "name"));
         competitionTypeService.save(new CompetitionTypeDTO(null, "type2", "name2"));
         competitionTypeService.save(new CompetitionTypeDTO(null, "type3", "name3"));

        Optional<CompetitionTypeDTO> competitionTypeDTO = competitionTypeService.findByTypeAndName("type", "name");
        Assertions.assertThat(competitionTypeDTO.isPresent()).isTrue();
        Assertions.assertThat(competitionTypeDTO.get().getCompetitionType()).isEqualTo("type");
        Assertions.assertThat(competitionTypeDTO.get().getCompetitionName()).isEqualTo("name");
    }

    @Test
    public void findByTypeAndNameWhenNullTest() {
        CompetitionTypeService competitionTypeService = new CompetitionTypeService(competitionTypeRepository, competitionTypeMapper);
        Optional<CompetitionTypeDTO> competitionTypeDTO = competitionTypeService.findByTypeAndName(null, "test");

        Assertions.assertThat(competitionTypeDTO.isPresent()).isFalse();
    }
}