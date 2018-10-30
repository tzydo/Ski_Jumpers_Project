package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.JumpResult;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.ApplicationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class JumpResultMapperTest {

    @Autowired
    private JumpResultMapper jumpResultMapper;

    @Test
    @Transactional
    public void fromDTOTest() {
        JumpResultDTO jumpResultDTO = new JumpResultDTO()
                .rank(1)
                .firstJump(100)
                .pointsForFirstJump(100)
                .secondJump(200)
                .pointsForSecondJump(200)
                .totalPoints(300);

        JumpResult expectedJumpResult = new JumpResult()
                .rank(1)
                .firstJump(100)
                .pointsForFirstJump(100)
                .secondJump(200)
                .pointsForSecondJump(200)
                .totalPoints(300);

        Assertions.assertThat(jumpResultMapper.fromDTO(jumpResultDTO))
                .isEqualToComparingFieldByFieldRecursively(expectedJumpResult);
    }

    @Test
    @Transactional
    public void toDTOTest() {
        JumpResult jumpResult = new JumpResult()
                .rank(1)
                .firstJump(100)
                .pointsForFirstJump(100)
                .secondJump(200)
                .pointsForSecondJump(200)
                .totalPoints(300);

        JumpResultDTO expectedJumpResultDTO = new JumpResultDTO()
                .rank(1)
                .firstJump(100)
                .pointsForFirstJump(100)
                .secondJump(200)
                .pointsForSecondJump(200)
                .totalPoints(300);

        Assertions.assertThat(jumpResultMapper.toDTO(jumpResult))
                .isEqualToComparingFieldByFieldRecursively(expectedJumpResultDTO);
    }
}