package com.pl.skijumping.batch.datareaderjob.reader;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@Profile("test")
public class DataReaderBatchTest {

    @Test
    public void open() throws IOException {
        File file = new ClassPathResource("testSkiJumper.txt").getFile();
        DataReaderBatch dataReaderBatch = new DataReaderBatch(file.getPath());
        dataReaderBatch.read();
    }
}