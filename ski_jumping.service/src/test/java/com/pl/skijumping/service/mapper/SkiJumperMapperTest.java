package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.SkiJumper;
import com.pl.skijumping.dto.SkiJumperDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class SkiJumperMapperTest {
    private static final Long FIRST_SKI_JUMPER_ID = 1L;
    private static final String VALUES_ARE_NOT_EQUALS = "values are not equals";
    private static final Long SECOND_SKI_JUMPER_ID = 2L;
    private static final Long THIRD_SKI_JUMPER_ID = 3L;

    @Autowired
    private SkiJumperMapper skiJumperMapper;

    @Test
    public void toDTOTest() {
        SkiJumper skiJumper = createSkiJumper(FIRST_SKI_JUMPER_ID);
        SkiJumperDTO skiJumperDTO = createSkiJumperDTO(FIRST_SKI_JUMPER_ID);

        assertEquals(VALUES_ARE_NOT_EQUALS, skiJumperDTO, skiJumperMapper.toDTO(skiJumper));
    }

    @Test
    public void toDTOListTest() {
        List<SkiJumper> skiJumperList = Arrays.asList(createSkiJumper(FIRST_SKI_JUMPER_ID),
                createSkiJumper(SECOND_SKI_JUMPER_ID),
                createSkiJumper(THIRD_SKI_JUMPER_ID));


        List<SkiJumperDTO> skiJumperDTOList = Arrays.asList(createSkiJumperDTO(FIRST_SKI_JUMPER_ID),
                createSkiJumperDTO(SECOND_SKI_JUMPER_ID),
                createSkiJumperDTO(THIRD_SKI_JUMPER_ID));

        assertEquals(VALUES_ARE_NOT_EQUALS, skiJumperDTOList, skiJumperMapper.toDTO(skiJumperList));
    }

    @Test
    public void fromDTOTest() {
        SkiJumperDTO skiJumperDTO = createSkiJumperDTO(FIRST_SKI_JUMPER_ID);
        SkiJumper skiJumper = createSkiJumper(FIRST_SKI_JUMPER_ID);

        assertEquals(VALUES_ARE_NOT_EQUALS, skiJumper, skiJumperMapper.fromDTO(skiJumperDTO));
    }

    @Test
    public void fromDTOListTest() {
        List<SkiJumper> skiJumperList = Arrays.asList(createSkiJumper(FIRST_SKI_JUMPER_ID),
                createSkiJumper(SECOND_SKI_JUMPER_ID),
                createSkiJumper(THIRD_SKI_JUMPER_ID));


        List<SkiJumperDTO> skiJumperDTOList = Arrays.asList(createSkiJumperDTO(FIRST_SKI_JUMPER_ID),
                createSkiJumperDTO(SECOND_SKI_JUMPER_ID),
                createSkiJumperDTO(THIRD_SKI_JUMPER_ID));

        assertEquals(VALUES_ARE_NOT_EQUALS, skiJumperList, skiJumperMapper.fromDTO(skiJumperDTOList));
    }

    private SkiJumper createSkiJumper(Long value) {
        return new SkiJumper()
                .id(value)
                .fisCode(value.intValue() + 1)
                .bib(value.intValue() + 2);
    }

    private SkiJumperDTO createSkiJumperDTO(Long value) {
        return new SkiJumperDTO()
                .id(value)
                .fisCode(value.intValue() + 1)
                .bib(value.intValue() + 2);
    }
}