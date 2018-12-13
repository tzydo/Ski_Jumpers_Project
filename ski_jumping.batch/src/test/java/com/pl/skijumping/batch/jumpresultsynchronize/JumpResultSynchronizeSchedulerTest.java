//package com.pl.skijumping.batch.jumpresultsynchronize;
//
//import com.pl.skijumping.batch.BatchApplicationTest;
//import com.pl.skijumping.batch.SetupUtilTests;
//import com.pl.skijumping.batch.jumpresultsynchronize.configuration.JumpResultSynchronize;
//import com.pl.skijumping.client.HtmlDownloader;
//import com.pl.skijumping.common.exception.InternalServiceException;
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import com.pl.skijumping.dto.DataRaceDTO;
//import com.pl.skijumping.dto.JumpResultDTO;
//import com.pl.skijumping.dto.SkiJumperDTO;
//import com.pl.skijumping.service.DataRaceService;
//import com.pl.skijumping.service.JumpResultService;
//import com.pl.skijumping.service.SkiJumperService;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.batch.core.ExitStatus;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles(profiles = "test")
//@SpringBootTest(classes = BatchApplicationTest.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//public class JumpResultSynchronizeSchedulerTest {
//
//    @Autowired
//    @Qualifier(JumpResultSynchronize.JUMP_RESULT_SYNCHRONIZE_JOB_NAME)
//    private Job job;
//    @Autowired
//    private JobLauncher jobLauncherTestUtils;
//    @Autowired
//    @Value("${skijumping.settings.skiJumpingResultsHost}")
//    String host;
//    @Autowired
//    private JumpResultService jumpResultService;
//    @Autowired
//    private SkiJumperService skiJumperService;
//    @Autowired
//    private DataRaceService dataRaceService;
//
//    @Mock
//    private DiagnosticMonitor diagnosticMonitor;
//    @Autowired
//    private HtmlDownloader htmlDownloader;
//
//    private final String words =
//            "</thead>" +
//                    "<tr>" +
//                    "<td class='i0' align='right'>&nbsp;1</td>" +
//                    "<td class='i0' align='right'>&nbsp;28</td>" +
//                    "<td class='i0' align='right'>&nbsp;4646</td>" +
//                    "<td class='i0'>" +
//                    "<a href=\"https://data.fis-ski.com/dynamic/athlete-biography.html?sector=JP&amp;competitorid=75837&amp;type=result\">TEPES Jurij</a>&nbsp;" +
//                    "</td>" +
//                    "<td class='i0' align='center'>1989&nbsp;</td>" +
//                    "<td class='i0' align='center'>SLO&nbsp;</td>" +
//                    "<td class='i0' align='right'>&nbsp;230.5</td>" +
//                    "<td class='i0' align='right'>&nbsp;208.9</td>" +
//                    "<td class='i0' align='right'>&nbsp;244.0</td>" +
//                    "<td class='i0' align='right'>&nbsp;227.5</td>" +
//                    "<td class='i0' align='right'>&nbsp;436.4</td>" +
//                    "</tr>" +
//                    "<!--<tr class=\"tr-sep\"><td colspan=\"12\"></td></tr>-->" +
//                    "<tr>" +
//                    "<td class='i1' align='right'>&nbsp;2</td>" +
//                    "<td class='i1' align='right'>&nbsp;30</td>" +
//                    "<td class='i1' align='right'>&nbsp;5658</td>" +
//                    "<td class='i1'>" +
//                    "<a href=\"https://data.fis-ski.com/dynamic/athlete-biography.html?sector=JP&amp;competitorid=131309&amp;type=result\">PREVC Peter</a>&nbsp;" +
//                    "</td>" +
//                    "<td class='i1' align='center'>1992&nbsp;</td>" +
//                    "<td class='i1' align='center'>SLO&nbsp;</td>" +
//                    "<td class='i1' align='right'>&nbsp;236.5</td>" +
//                    "<td class='i1' align='right'>&nbsp;216.1</td>" +
//                    "<td class='i1' align='right'>&nbsp;233.5</td>" +
//                    "<td class='i1' align='right'>&nbsp;217.5</td>" +
//                    "<td class='i1' align='right'>&nbsp;433.6</td>" +
//                    "</tr>" +
//                    "<!--<tr class=\"tr-sep\"><td colspan=\"12\"></td></tr>-->" +
//                    "<tr>" +
//                    "<td class='i0' align='right'>&nbsp;3</td>" +
//                    "<td class='i0' align='right'>&nbsp;25</td>" +
//                    "<td class='i0' align='right'>&nbsp;5987</td>" +
//                    "<td class='i0'>" +
//                    "<a href=\"https://data.fis-ski.com/dynamic/athlete-biography.html?sector=JP&amp;competitorid=142646&amp;type=result\">VELTA Rune</a>&nbsp;" +
//                    "</td>" +
//                    "<td class='i0' align='center'>1989&nbsp;</td>" +
//                    "<td class='i0' align='center'>NOR&nbsp;</td>" +
//                    "<td class='i0' align='right'>&nbsp;230.5</td>" +
//                    "<td class='i0' align='right'>&nbsp;210.3</td>" +
//                    "<td class='i0' align='right'>&nbsp;226.5</td>" +
//                    "<td class='i0' align='right'>&nbsp;207.9</td>" +
//                    "<td class='i0' align='right'>&nbsp;418.2</td>" +
//                    "</tr></tbody>test";
//
//    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
//    public void synchronizeDataThenCreateNewJumpResultAndSkiJumperTest() throws InternalServiceException, IOException {
//        Long raceDataId = 1L;
//        dataRaceService.save(new DataRaceDTO().date(LocalDate.now()).city("krk").shortCountryName("name").raceId(raceDataId));
//
//        diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
//        Mockito.when(htmlDownloader.downloadToString(host + raceDataId)).thenReturn(words);
//
//        JumpResultSynchronizeScheduler jumpResultSynchronizeScheduler =
//                new JumpResultSynchronizeScheduler(jobLauncherTestUtils, job, true, diagnosticMonitor);
//
//        JobExecution jobExecution = jumpResultSynchronizeScheduler.synchronizeData();
//        Assertions.assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
//
//        List<JumpResultDTO> actualJumpResultListDTO = jumpResultService.findAll();
//        Assertions.assertThat(actualJumpResultListDTO).isNotEmpty();
//        Assertions.assertThat(actualJumpResultListDTO).hasSize(3);
//
//        List<SkiJumperDTO> skiJumperDTOS = skiJumperService.findAll();
//        Assertions.assertThat(skiJumperDTOS).hasSize(3);
//
//        List<DataRaceDTO> dataRaceDTOS = dataRaceService.findAll();
//        Assertions.assertThat(dataRaceDTOS).hasSize(1);
//    }
//
//    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
//    public void synchronizeDataWhenExistTest() throws InternalServiceException, IOException {
//        //setup
//        Long raceDataId = 1L;
//        DataRaceDTO dataRaceDTO = dataRaceService.save(
//                new DataRaceDTO().date(LocalDate.now()).city("krk").shortCountryName("name").raceId(raceDataId));
//        SkiJumperDTO firstSkiJumperDTO = skiJumperService.save(
//                new SkiJumperDTO().bib(28).fisCode(4646).name("Jurij Tepes"));
//        SkiJumperDTO secondSkiJumperDTO = skiJumperService.save(
//                new SkiJumperDTO().bib(30).fisCode(5658).name("Peter Prevc"));
//        SkiJumperDTO thirdSkiJumperDTO = skiJumperService.save(
//                new SkiJumperDTO().bib(25).fisCode(5987).name("Rune Velta"));
//
//        JumpResultDTO firstExpectedJumpResultDTO = new JumpResultDTO()
//                .id(1L)
//                .rank(1)
//                .firstJump(230.5)
//                .pointsForFirstJump(208.9)
//                .secondJump(244.0)
//                .pointsForSecondJump(227.5)
//                .totalPoints(436.4);
//
//        JumpResultDTO secondExpectedJumpResultDTO = new JumpResultDTO()
//                .id(2L)
//                .rank(2)
//                .firstJump(236.5)
//                .pointsForFirstJump(216.1)
//                .secondJump(233.5)
//                .pointsForSecondJump(217.5)
//                .totalPoints(433.6);
//
//        JumpResultDTO thirdExpectedJumpResultDTO = new JumpResultDTO()
//                .id(3L)
//                .rank(3)
//                .firstJump(230.5)
//                .pointsForFirstJump(210.3)
//                .secondJump(226.5)
//                .pointsForSecondJump(207.9)
//                .totalPoints(418.2);
//        firstExpectedJumpResultDTO = jumpResultService.save(firstExpectedJumpResultDTO);
//        secondExpectedJumpResultDTO = jumpResultService.save(secondExpectedJumpResultDTO);
//        thirdExpectedJumpResultDTO = jumpResultService.save(thirdExpectedJumpResultDTO);
//
//        diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
//        Mockito.when(htmlDownloader.downloadToString(host + raceDataId)).thenReturn(words);
//
//
//        JumpResultSynchronizeScheduler jumpResultSynchronizeScheduler =
//                new JumpResultSynchronizeScheduler(jobLauncherTestUtils, job, true, diagnosticMonitor);
//
//        jumpResultSynchronizeScheduler.synchronizeData();
//
//        List<JumpResultDTO> actualJumpResultListDTO = jumpResultService.findAll();
//        Assertions.assertThat(actualJumpResultListDTO).isNotEmpty();
//        Assertions.assertThat(actualJumpResultListDTO).hasSize(3);
//
//        Assertions.assertThat(actualJumpResultListDTO).containsAll(Arrays.asList(firstExpectedJumpResultDTO, secondExpectedJumpResultDTO, thirdExpectedJumpResultDTO));
//
//        List<SkiJumperDTO> skiJumperDTOS = skiJumperService.findAll();
//        Assertions.assertThat(skiJumperDTOS).hasSize(3);
//    }
//
//}