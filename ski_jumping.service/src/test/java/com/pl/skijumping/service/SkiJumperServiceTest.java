package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.SkiJumper;
import com.pl.skijumping.domain.repository.SkiJumperRepository;
import com.pl.skijumping.dto.SkiJumperDTO;
import com.pl.skijumping.service.mapper.SkiJumperMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationTest.class)
public class SkiJumperServiceTest {

    private static final int BIB = 1;
    private static final String NATIONALITY = "nationality";
    private static final String SURNAME = "surname";
    private static final String NAME = "name";

    @Autowired
    private SkiJumperRepository skiJumperRepository;

    @Autowired
    private SkiJumperMapper skiJumperMapper;

    @Test
    @Transactional
    public void saveTest() {
        SkiJumperDTO skiJumperDTO = getSkiJumperDTO(BIB, NAME, NATIONALITY);
        SkiJumperService skiJumperService = new SkiJumperService(skiJumperRepository, skiJumperMapper);
        SkiJumperDTO savedSkiJumperDTO = skiJumperService.save(skiJumperDTO);

        skiJumperDTO.setId(savedSkiJumperDTO.getId());
        assertThat(savedSkiJumperDTO).isEqualToComparingFieldByField(skiJumperDTO);
        SkiJumper skiJumperFromDB = skiJumperRepository.getOne(savedSkiJumperDTO.getId());
        assertThat(savedSkiJumperDTO).isEqualToComparingFieldByField(skiJumperMapper.toDTO(skiJumperFromDB));
    }

    private SkiJumperDTO getSkiJumperDTO(int rank, String name, String nationality) {
        return new SkiJumperDTO()
                .fisCode(rank)
                .name(name)
                .nationality(nationality);
    }
}