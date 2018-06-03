package com.pl.skijumping.batch.datareaderjob.reader;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

@Profile("test")
@RunWith(MockitoJUnitRunner.class)
public class DataReaderBatchTest {

    @Mock
    DiagnosticMonitor diagnosticMonitor;


    @Test
    public void open() throws IOException {
        File file = new ClassPathResource("testSkiJumper.txt").getFile();
        DataReaderBatch dataReaderBatch = new DataReaderBatch(file.getPath(), diagnosticMonitor);
        dataReaderBatch.read();
    }
}