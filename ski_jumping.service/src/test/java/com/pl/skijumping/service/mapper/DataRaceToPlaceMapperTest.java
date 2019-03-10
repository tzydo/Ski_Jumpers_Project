package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.domain.entity.DataRaceToPlace;
import com.pl.skijumping.domain.entity.Place;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.domain.repository.PlaceRepository;
import com.pl.skijumping.dto.DataRaceToPlaceDTO;
import com.pl.skijumping.service.ApplicationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class DataRaceToPlaceMapperTest {

    @MockBean
    private DataRaceRepository dataRaceRepository;
    @MockBean
    private PlaceRepository placeRepository;
    @Autowired
    private DataRaceToPlaceMapper dataRaceToPlaceMapper;

    @Test
    public void testMappingToDTO() {
        DataRace dataRace = new DataRace().id(1L);
        Place place = new Place().id(1);
        DataRaceToPlace dataRaceToPlace = new DataRaceToPlace().place(place).dataRace(dataRace).id(1L);
        DataRaceToPlaceDTO expectedDataRaceToPlaceDTO = new DataRaceToPlaceDTO().dataRaceId(dataRace.getId()).placeId(place.getId()).id(1L);
        Assertions.assertThat(dataRaceToPlaceMapper.toDTO(dataRaceToPlace)).isEqualToComparingFieldByField(expectedDataRaceToPlaceDTO);
    }

    @Test
    public void testMappingFromDTO() {
        DataRace dataRace = new DataRace().id(1L);
        Place place = new Place().id(1);

        Mockito.when(dataRaceRepository.findOne(1L)).thenReturn(dataRace);
        Mockito.when(placeRepository.findOne(1)).thenReturn(place);

        DataRaceToPlace expectedDataRaceToPlace = new DataRaceToPlace().place(place).dataRace(dataRace);
        DataRaceToPlaceDTO dataRaceToPlaceDTO = new DataRaceToPlaceDTO().dataRaceId(dataRace.getId()).placeId(place.getId());
        Assertions.assertThat(dataRaceToPlaceMapper.fromDTO(dataRaceToPlaceDTO)).isEqualToComparingFieldByField(expectedDataRaceToPlace);
    }
}