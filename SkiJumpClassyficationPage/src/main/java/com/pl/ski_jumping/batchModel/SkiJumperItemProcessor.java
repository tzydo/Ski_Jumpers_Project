package com.pl.ski_jumping.batchModel;

import com.pl.ski_jumping.model.SkiJumper;

import org.springframework.batch.item.ItemProcessor;

public class SkiJumperItemProcessor implements ItemProcessor<SkiJumper, SkiJumper> {
    @Override
    public SkiJumper process(SkiJumper skiJumper) throws Exception {
        return skiJumper;
    }
}
