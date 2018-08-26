package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.CompetitionType;
import com.pl.skijumping.dto.CompetitionTypeDTO;
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
public class CompetitionTypeMapperTest {

    @Autowired
    private CompetitionTypeMapper competitionTypeMapper;

    @Test
    public void fromDTOTest() {
        CompetitionTypeDTO competitionTypeDTO = new CompetitionTypeDTO();
        competitionTypeDTO.setType("type");
        competitionTypeDTO.setId(1l);

        CompetitionType expectedCompetitionType = new CompetitionType();
        expectedCompetitionType.setType("type");
        expectedCompetitionType.setId(1l);

        Assertions.assertThat(competitionTypeMapper.fromDTO(competitionTypeDTO))
                .isEqualToComparingFieldByFieldRecursively(expectedCompetitionType);
    }

    @Test
    public void fromDTOListTest() {
        List<CompetitionType> expectedCompetitionTypeList = Arrays.asList(
                new CompetitionType().type("type").id(1l),
                new CompetitionType().type("type2").id(2l),
                new CompetitionType().type("type3").id(3l)
        );

        List<CompetitionTypeDTO> competitionTypeDTOList = Arrays.asList(
                new CompetitionTypeDTO().type("type").id(1l),
                new CompetitionTypeDTO().type("type2").id(2l),
                new CompetitionTypeDTO().type("type3").id(3l)
        );

        Assertions.assertThat(competitionTypeMapper.fromDTO(competitionTypeDTOList)).containsAll(expectedCompetitionTypeList);
    }

    @Test
    public void toDTOTest() {
        CompetitionTypeDTO expectedCompetitionTypeDTO = new CompetitionTypeDTO();
        expectedCompetitionTypeDTO.setType("type");
        expectedCompetitionTypeDTO.setId(1l);

        CompetitionType competitionType = new CompetitionType();
        competitionType.setType("type");
        competitionType.setId(1l);

        Assertions.assertThat(competitionTypeMapper.toDTO(competitionType))
                .isEqualToComparingFieldByFieldRecursively(expectedCompetitionTypeDTO);
    }

    @Test
    public void toDTOListTest() {
        List<CompetitionType> competitionTypeList = Arrays.asList(
                new CompetitionType().type("type").id(1l),
                new CompetitionType().type("type2").id(2l),
                new CompetitionType().type("type3").id(3l)
        );

        List<CompetitionTypeDTO> expectedCompetitionTypeDTOList = Arrays.asList(
                new CompetitionTypeDTO().type("type").id(1l),
                new CompetitionTypeDTO().type("type2").id(2l),
                new CompetitionTypeDTO().type("type3").id(3l)
        );

        Assertions.assertThat(competitionTypeMapper.toDTO(competitionTypeList)).containsAll(expectedCompetitionTypeDTOList);
    }
}