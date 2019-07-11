package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.SkiJumper;
import com.pl.skijumping.domain.model.Gender;
import com.pl.skijumping.domain.model.MaritalStatus;
import com.pl.skijumping.dto.SkiJumperDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class SkiJumperMapperTest {

    @Test
    public void fromDTOMappingTest() {
        SkiJumperDTO skiJumperDTO = new SkiJumperDTO()
                .id(1L)
                .fisCode(3333)
                .birthday(LocalDate.of(2019, 1, 1))
                .name("testName")
                .nationality("Nationality")
                .team("team")
                .gender(Gender.FEMALE.name())
                .martialStatus(MaritalStatus.IN_RELATIONSHIP.name());

        SkiJumper expectedSkiJumper = new SkiJumper()
                .id(1L)
                .fisCode(3333)
                .birthday(LocalDate.of(2019, 1, 1))
                .name("testName")
                .nationality("Nationality")
                .team("team")
                .gender(Gender.FEMALE)
                .martialStatus(MaritalStatus.IN_RELATIONSHIP);

        SkiJumper actualSkiJumper = SkiJumperMapper.SKI_JUMPER_MAPPER.fromDTO(skiJumperDTO);
        Assertions.assertThat(actualSkiJumper).isEqualToComparingFieldByFieldRecursively(expectedSkiJumper);
    }

    @Test
    public void fromObjectMappingTest() {
        SkiJumper skiJumper = new SkiJumper()
                .id(1L)
                .fisCode(3333)
                .birthday(LocalDate.of(2019, 1, 1))
                .name("testName")
                .nationality("Nationality")
                .team("team")
                .gender(Gender.FEMALE)
                .martialStatus(MaritalStatus.IN_RELATIONSHIP);

        SkiJumperDTO expectedSkiJumperDTO = new SkiJumperDTO()
                .id(1L)
                .fisCode(3333)
                .birthday(LocalDate.of(2019, 1, 1))
                .name("testName")
                .nationality("Nationality")
                .team("team")
                .gender(Gender.FEMALE.name())
                .martialStatus(MaritalStatus.IN_RELATIONSHIP.name());

        SkiJumperDTO actualSkiJumperDTO = SkiJumperMapper.SKI_JUMPER_MAPPER.toDTO(skiJumper);
        Assertions.assertThat(actualSkiJumperDTO).isEqualToComparingFieldByFieldRecursively(expectedSkiJumperDTO);
    }
}