package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.CompetitionType;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.ApplicationTest;
import com.pl.skijumping.service.DataRaceService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class CompetitionTypeMapperTest {

    @Autowired
    private CompetitionTypeMapper competitionTypeMapper;
    @Autowired
    private DataRaceService dataRaceService;
    @Autowired
    private DataRaceMapper dataRaceMapper;

    @Test
    @Transactional
    public void fromDTOTest() {
        DataRaceDTO dataRaceDTO = dataRaceService.save(new DataRaceDTO().date(LocalDate.now()).city("city").raceId(1L).shortCountryName("short"));

        CompetitionTypeDTO competitionTypeDTO = new CompetitionTypeDTO();
        competitionTypeDTO.setType("type");
        competitionTypeDTO.setId(1L);
        competitionTypeDTO.setDataRaceId(Arrays.asList(dataRaceDTO.getId()));

        CompetitionType expectedCompetitionType = new CompetitionType();
        expectedCompetitionType.setType("type");
        expectedCompetitionType.setId(1L);
        expectedCompetitionType.setDataRaceList(Arrays.asList(dataRaceMapper.fromDTO(dataRaceDTO)));

        Assertions.assertThat(competitionTypeMapper.fromDTO(competitionTypeDTO))
                .isEqualToComparingFieldByFieldRecursively(expectedCompetitionType);
    }

    @Test
    public void fromDTOListTest() {
        List<CompetitionType> expectedCompetitionTypeList = Arrays.asList(
                new CompetitionType().type("type").id(1L),
                new CompetitionType().type("type2").id(2L),
                new CompetitionType().type("type3").id(3L)
        );

        List<CompetitionTypeDTO> competitionTypeDTOList = Arrays.asList(
                new CompetitionTypeDTO().type("type").id(1L),
                new CompetitionTypeDTO().type("type2").id(2L),
                new CompetitionTypeDTO().type("type3").id(3L)
        );

        Assertions.assertThat(competitionTypeMapper.fromDTO(competitionTypeDTOList)).containsAll(expectedCompetitionTypeList);
    }

    @Test
    public void toDTOTest() {
        CompetitionTypeDTO expectedCompetitionTypeDTO = new CompetitionTypeDTO();
        expectedCompetitionTypeDTO.setType("type");
        expectedCompetitionTypeDTO.setId(1L);

        CompetitionType competitionType = new CompetitionType();
        competitionType.setType("type");
        competitionType.setId(1L);

        Assertions.assertThat(competitionTypeMapper.toDTO(competitionType))
                .isEqualToComparingFieldByFieldRecursively(expectedCompetitionTypeDTO);
    }

    @Test
    public void toDTOListTest() {
        List<CompetitionType> competitionTypeList = Arrays.asList(
                new CompetitionType().type("type").id(1L),
                new CompetitionType().type("type2").id(2L),
                new CompetitionType().type("type3").id(3L)
        );

        List<CompetitionTypeDTO> expectedCompetitionTypeDTOList = Arrays.asList(
                new CompetitionTypeDTO().type("type").id(1L),
                new CompetitionTypeDTO().type("type2").id(2L),
                new CompetitionTypeDTO().type("type3").id(3L)
        );

        Assertions.assertThat(competitionTypeMapper.toDTO(competitionTypeList)).containsAll(expectedCompetitionTypeDTOList);
    }
}