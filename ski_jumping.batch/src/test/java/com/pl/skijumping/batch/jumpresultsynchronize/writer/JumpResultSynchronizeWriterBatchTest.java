package com.pl.skijumping.batch.jumpresultsynchronize.writer;

import com.pl.skijumping.batch.BatchApplicationTest;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.JumpResultService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = BatchApplicationTest.class)
public class JumpResultSynchronizeWriterBatchTest {

    @Autowired
    private JumpResultService jumpResultService;

    @Test
    @Transactional
    public void writeWhenNullTest() {
        JumpResultSynchronizeWriterBatch jumpResultSynchronizeWriterBatch = new JumpResultSynchronizeWriterBatch(jumpResultService);
        jumpResultSynchronizeWriterBatch.write(null);
        Assertions.assertThat(jumpResultService.findAll()).isEqualTo(new ArrayList<>());
    }

    @Test
    @Transactional
    public void writeWhenEmptyListTest() {
        JumpResultSynchronizeWriterBatch jumpResultSynchronizeWriterBatch = new JumpResultSynchronizeWriterBatch(jumpResultService);
        jumpResultSynchronizeWriterBatch.write(new ArrayList<>());
        Assertions.assertThat(jumpResultService.findAll()).isEqualTo(new ArrayList<>());
    }

    @Test
    @Transactional
    public void writeTest() {
        JumpResultSynchronizeWriterBatch jumpResultSynchronizeWriterBatch = new JumpResultSynchronizeWriterBatch(jumpResultService);

        List<JumpResultDTO> expectedJumpResultList = Arrays.asList(
                new JumpResultDTO().rank(1).id(1L),
                new JumpResultDTO().rank(2).id(2L),
                new JumpResultDTO().rank(3).id(3L)
        );

        jumpResultSynchronizeWriterBatch.write(Arrays.asList(expectedJumpResultList));

        List<JumpResultDTO> actualJumpResultList = jumpResultService.findAll();
        Assertions.assertThat(actualJumpResultList).hasSize(3);
        Assertions.assertThat(actualJumpResultList).containsAll(expectedJumpResultList);
    }
}