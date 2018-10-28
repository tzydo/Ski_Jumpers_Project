package com.pl.skijumping.batch.jumpresultsynchronize.writer.creator;

import com.pl.skijumping.dto.JumpResultDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class JumpResultCreatorTest {

    @Test
    public void testCreateJumpResult() {
        JumpResultDTO expectedJumpResultDTO = new JumpResultDTO()
                .rank(1)
                .firstJump(99.0)
                .pointsForFirstJump(126.3)
                .secondJump(102.5)
                .pointsForSecondJump(124.1)
                .totalPoints(250.4);

        JumpResultDTO actualJumpResult = JumpResultCreator.createJumpResult(Arrays.asList("1", "46", "6198", "1996", "JPN", "99.0", "126.3", "102.5", "124.1", "250.4"));
        Assertions.assertThat(actualJumpResult).isEqualToComparingFieldByFieldRecursively(expectedJumpResultDTO);
    }

    @Test
    public void testCreateJumpResultWhenNull() {
        JumpResultDTO actualJumpResult = JumpResultCreator.createJumpResult(null);
        Assertions.assertThat(actualJumpResult).isNull();

        actualJumpResult = JumpResultCreator.createJumpResult(new ArrayList<>());
        Assertions.assertThat(actualJumpResult).isNull();
    }
}