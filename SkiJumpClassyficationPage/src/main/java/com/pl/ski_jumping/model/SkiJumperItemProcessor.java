package com.pl.ski_jumping.model;


import org.springframework.batch.item.ItemProcessor;

public class SkiJumperItemProcessor implements ItemProcessor<SkiJumper, SkiJumper> {
    @Override
    public SkiJumper process(SkiJumper skiJumper) throws Exception {
        return skiJumper;
    }
}
