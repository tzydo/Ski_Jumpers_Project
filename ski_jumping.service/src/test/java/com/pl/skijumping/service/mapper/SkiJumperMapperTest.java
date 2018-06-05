package com.pl.skijumping.domain.mapper;

import com.pl.skijumping.domain.dto.SkiJumperDTO;
import com.pl.skijumping.domain.entity.SkiJumper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class SkiJumperMapperTest {
    private static final int FIRST_SKI_JUMPER_ID = 1;
    private static final String VALUES_ARE_NOT_EQUALS = "values are not equals";
    private static final int SECOND_SKI_JUMPER_ID = 2;
    private static final int THIRD_SKI_JUMPER_ID = 3;

    @Autowired
    private SkiJumperMapper skiJumperMapper;

    @Test
    public void toDTOTest() throws Exception {
        SkiJumper skiJumper = createSkiJumper(FIRST_SKI_JUMPER_ID);
        SkiJumperDTO skiJumperDTO = createSkiJumperDTO(FIRST_SKI_JUMPER_ID);

        assertEquals(VALUES_ARE_NOT_EQUALS, skiJumperDTO, skiJumperMapper.toDTO(skiJumper));
    }

    @Test
    public void toDTOListTest() throws Exception {
        List<SkiJumper> skiJumperList = Arrays.asList(createSkiJumper(FIRST_SKI_JUMPER_ID),
                createSkiJumper(SECOND_SKI_JUMPER_ID),
                createSkiJumper(THIRD_SKI_JUMPER_ID));


        List<SkiJumperDTO> skiJumperDTOList = Arrays.asList(createSkiJumperDTO(FIRST_SKI_JUMPER_ID),
                createSkiJumperDTO(SECOND_SKI_JUMPER_ID),
                createSkiJumperDTO(THIRD_SKI_JUMPER_ID));

        assertEquals(VALUES_ARE_NOT_EQUALS, skiJumperDTOList, skiJumperMapper.toDTO(skiJumperList));
    }

    @Test
    public void fromDTOTest() throws Exception {
        SkiJumperDTO skiJumperDTO = createSkiJumperDTO(FIRST_SKI_JUMPER_ID);
        SkiJumper skiJumper = createSkiJumper(FIRST_SKI_JUMPER_ID);

        assertEquals(VALUES_ARE_NOT_EQUALS, skiJumper, skiJumperMapper.fromDTO(skiJumperDTO));
    }

    @Test
    public void fromDTOListTest() throws Exception {
        List<SkiJumper> skiJumperList = Arrays.asList(createSkiJumper(FIRST_SKI_JUMPER_ID),
                createSkiJumper(SECOND_SKI_JUMPER_ID),
                createSkiJumper(THIRD_SKI_JUMPER_ID));


        List<SkiJumperDTO> skiJumperDTOList = Arrays.asList(createSkiJumperDTO(FIRST_SKI_JUMPER_ID),
                createSkiJumperDTO(SECOND_SKI_JUMPER_ID),
                createSkiJumperDTO(THIRD_SKI_JUMPER_ID));

        assertEquals(VALUES_ARE_NOT_EQUALS, skiJumperList, skiJumperMapper.fromDTO(skiJumperDTOList));
    }

    private SkiJumper createSkiJumper(int value) {
        return SkiJumper.builder()
                .id(value)
                .rank(value + 1)
                .bib(value + 2).build();
    }

    private SkiJumperDTO createSkiJumperDTO(int value) {
        return SkiJumperDTO.builder()
                .id(value)
                .rank(value + 1)
                .bib(value + 2).build();
    }
}