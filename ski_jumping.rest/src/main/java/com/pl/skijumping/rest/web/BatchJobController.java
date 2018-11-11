package com.pl.skijumping.rest.web;

import com.pl.skijumping.batch.dataimportjob.scheduler.DataImportScheduler;
import com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.scheduler.FindTournamentYearScheduler;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.dto.BatchJobStatisticDTO;
import org.springframework.batch.core.JobExecution;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/admin")
public class BatchJobController {

    private final DataImportScheduler dataImporterStep;
    private final FindTournamentYearScheduler findTournamentYearScheduler;

    public BatchJobController(DataImportScheduler dataImporterStep,
                              FindTournamentYearScheduler findTournamentYearScheduler) {
        this.dataImporterStep = dataImporterStep;
        this.findTournamentYearScheduler = findTournamentYearScheduler;
    }

    @PostMapping
    @RequestMapping(path = "dataRaceJobImporter")
    public ResponseEntity<BatchJobStatisticDTO> runDataRaceJob() throws InternalServiceException {
        JobExecution jobExecution = dataImporterStep.importData();
        BatchJobStatisticDTO batchJobStatisticDTO = StatisticGenerator.generate(jobExecution);
        return new ResponseEntity<>(batchJobStatisticDTO, HttpStatus.OK);
    }

    @PostMapping
    @RequestMapping(path = "findTournamentYearJob")
    public ResponseEntity<BatchJobStatisticDTO> runFindTournamentYearJob() throws InternalServiceException {
        JobExecution jobExecution = findTournamentYearScheduler.importData();
        BatchJobStatisticDTO batchJobStatisticDTO = StatisticGenerator.generate(jobExecution);
        return new ResponseEntity<>(batchJobStatisticDTO, HttpStatus.OK);
    }
}
