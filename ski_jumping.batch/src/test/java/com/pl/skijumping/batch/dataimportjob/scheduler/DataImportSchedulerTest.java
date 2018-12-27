package com.pl.skijumping.batch.dataimportjob.scheduler;

import com.pl.skijumping.batch.BatchApplicationTest;
import com.pl.skijumping.batch.dataimportjob.DataImporterUtil;
import com.pl.skijumping.batch.util.FileScannerConst;
import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static com.pl.skijumping.batch.dataimportjob.configuration.DataImporterConfiguration.DATA_IMPORT_JOB_NAME;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest(classes = BatchApplicationTest.class)
public class DataImportSchedulerTest {
    private static final String DIRECTORY = "tmp";
    @Autowired
    @Qualifier(DATA_IMPORT_JOB_NAME)
    private Job job;
    @Autowired
    private JobLauncher jobLauncherTestUtils;
    @Autowired
    private DiagnosticMonitor diagnosticMonitor;
    @Value("skijumping.settings.host")
    String host;
    @MockBean
    private HtmlDownloader htmlDownloader;

    @Before
    public void setup() throws IOException, InternalServiceException {
        Mockito.when(htmlDownloader.downloadSource(Mockito.any(), Mockito.anyString())).thenReturn(null);
    }

    @After
    public void tearDown() throws IOException {
        Path directoryPath = Paths.get(FileUtil.getResource(), DIRECTORY);
        List<File> fileList = FileUtil.getFiles(directoryPath);
        fileList.forEach(File::delete);
        directoryPath.toFile().delete();
    }

    @Test
    public void importDataTest() throws Exception {
        DataImportScheduler dataImportScheduler = new DataImportScheduler(jobLauncherTestUtils, job, true, diagnosticMonitor);
        ExitStatus exitStatus = dataImportScheduler.importData().getExitStatus();
        Assertions.assertThat(exitStatus).isEqualTo(ExitStatus.COMPLETED);

        Path pathToDirectory = FileUtil.getPath(FileUtil.getResource(), DIRECTORY);
        Assertions.assertThat(FileUtil.getFiles(pathToDirectory)).isNotEmpty().hasSize(1);
    }

    @Test
    public void importDataWhenExistTest() throws Exception {
        Path directoryPath = FileUtil.createDirectory(FileUtil.getResourcePath(), DIRECTORY);
        List<String> hosts = DataImporterUtil.generateSeasonCodeByPreviousMonths(LocalDate.now().getYear(), LocalDate.now().getMonthValue(),2, host);
        hosts.forEach(host -> FileUtil.createFile(directoryPath, FileScannerConst.FILE_DATA_IMPORT + DataImporterUtil.getFileNameFromHost(host)));

        DataImportScheduler dataImportScheduler = new DataImportScheduler(jobLauncherTestUtils, job, true, diagnosticMonitor);
        ExitStatus exitStatus = dataImportScheduler.importData().getExitStatus();
        Assertions.assertThat(exitStatus).isEqualTo(ExitStatus.COMPLETED);

        Path pathToDirectory = FileUtil.getPath(FileUtil.getResource(), DIRECTORY);
        Assertions.assertThat(FileUtil.getFiles(pathToDirectory)).isNotEmpty().hasSize(2);
    }

    @Test
    public void importDataWhenEnableFalseTest() throws InternalServiceException {
        DataImportScheduler dataImportScheduler = new DataImportScheduler(jobLauncherTestUtils, job, false, diagnosticMonitor);
        JobExecution jobExecution = dataImportScheduler.importData();
        Assertions.assertThat(jobExecution).isNull();
    }
}