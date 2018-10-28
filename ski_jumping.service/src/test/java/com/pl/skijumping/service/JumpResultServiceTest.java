package com.pl.skijumping.service;

import com.pl.skijumping.dto.JumpResultDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationTest.class)
public class JumpResultServiceTest {

    @Autowired
    private JumpResultService jumpResultService;

    @Test
    @Transactional
    public void testFindBy() {
        Assertions.assertThat(jumpResultService.findAll()).isEmpty();
        JumpResultDTO jumpResultDTO = jumpResultService.save(new JumpResultDTO().rank(1).totalPoints(20.0));
        jumpResultService.save(new JumpResultDTO().rank(1).totalPoints(20.0).pointsForSecondJump(10.0));
        jumpResultService.save(new JumpResultDTO().rank(2).totalPoints(30.0).pointsForSecondJump(10.0));

        Optional<JumpResultDTO> foundJumpResult = jumpResultService.findBy(jumpResultDTO);
        Assertions.assertThat(foundJumpResult.isPresent()).isTrue();
        Assertions.assertThat(foundJumpResult.get()).isEqualToComparingFieldByFieldRecursively(jumpResultDTO);
    }
}