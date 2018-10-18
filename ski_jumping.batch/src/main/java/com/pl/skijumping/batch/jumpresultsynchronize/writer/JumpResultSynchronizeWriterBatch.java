package com.pl.skijumping.batch.jumpresultsynchronize.writer;

import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.JumpResultService;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class JumpResultSynchronizeWriterBatch implements ItemWriter<List<JumpResultDTO>> {
    private final JumpResultService jumpResultService;

    public JumpResultSynchronizeWriterBatch(JumpResultService jumpResultService) {
        this.jumpResultService = jumpResultService;
    }

    @Override
    public void write(List<? extends List<JumpResultDTO>> jumpResultDTOList) {
        if(jumpResultDTOList == null ||jumpResultDTOList.isEmpty()) {
            return;
        }
        List<JumpResultDTO>resultList = jumpResultDTOList.get(0);
        if (resultList.isEmpty()) {
            return;
        }
        resultList.forEach(jumpResultService::update);
    }
}
