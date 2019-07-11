package com.pl.skijumping.batch.reader;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Profile("test")
@RunWith(MockitoJUnitRunner.class)
public class DataReaderTest {

    @Test
    public void readTest() throws IOException {
        String expectedFileContent = "https://www.fis-ski.com/DB/general/event-details.html?sectorcode=JP&eventid=39207&seasoncode=2017https://www.fis-ski.com/DB/general/event-details.html?sectorcode=JP&eventid=39224&seasoncode=2017https://www.fis-ski.com/DB/general/event-details.html?sectorcode=JP&eventid=39038&seasoncode=2017https://www.fis-ski.com/DB/general/event-details.html?sectorcode=JP&eventid=39225&seasoncode=2017";

        DiagnosticMonitor diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReader dataReader = new DataReader(diagnosticMonitor);
        String readWords = dataReader.read(FileUtil.getPath(FileUtil.getResource(), "import_eventIdImport.txt").toString());
        Assertions.assertThat(readWords).isNotNull();
        Assertions.assertThat(readWords).isNotEmpty();
        Assertions.assertThat(readWords).isEqualTo(expectedFileContent);
    }

    @Test
    public void readWhenNullTest() {
        DiagnosticMonitor diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReader dataReader = new DataReader(diagnosticMonitor);
        String readWords = dataReader.read(null);
        Assertions.assertThat(readWords).isNull();
    }

    @Test
    public void readWhenEmptyTest() {
        DiagnosticMonitor diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReader dataReader = new DataReader(diagnosticMonitor);
        String readWords = dataReader.read("emptyTest");
        Assertions.assertThat(readWords).isNull();
    }
}