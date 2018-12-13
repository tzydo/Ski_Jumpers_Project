//package com.pl.skijumping.batch.datasynchronize;
//
//import com.pl.skijumping.client.IHtmlDownloader;
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import com.pl.skijumping.service.TournamentYearService;
//import org.springframework.batch.core.ExitStatus;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Value;
//
//public class BasicDataSynchronizeTasklet implements Tasklet {
//    private final String hostWithYear;
//    private final String directory;
//    private final Boolean loadAllData;
//    private final Integer numberOfPreviousYear;
//    private final TournamentYearService tournamentYearService;
//    private final DiagnosticMonitor diagnosticMonitor;
//    private final IHtmlDownloader htmlDownloader;
//
//    public BasicDataSynchronizeTasklet(@Value("${skijumping.settings.hostWithFilterForYear}") String hostWithYear,
//                                       @Value("${skijumping.settings.directory}") String directory,
//                                       @Value("${skijumping.settings.loadAllData") Boolean loadAllData,
//                                       @Value("${skijumping.settings.numberOfPreviousYear") Integer numberOfPreviousYear,
//                                       TournamentYearService tournamentYearService,
//                                       IHtmlDownloader htmlDownloader,
//                                       DiagnosticMonitor diagnosticMonitor) {
//        this.tournamentYearService = tournamentYearService;
//        this.hostWithYear = hostWithYear;
//        this.directory = directory;
//        this.loadAllData = loadAllData;
//        this.numberOfPreviousYear = numberOfPreviousYear;
//        this.htmlDownloader = htmlDownloader;
//        this.diagnosticMonitor = diagnosticMonitor;
//    }
//
//    @Override
//    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
//        BasicDataDownloader basicDataDownloader = new BasicDataDownloader(
//                tournamentYearService, hostWithYear, directory, htmlDownloader, diagnosticMonitor, loadAllData, numberOfPreviousYear);
//        ExitStatus exitStatus = basicDataDownloader.download();
//        stepContribution.setExitStatus(exitStatus);
//        return RepeatStatus.FINISHED;
//    }
//}
