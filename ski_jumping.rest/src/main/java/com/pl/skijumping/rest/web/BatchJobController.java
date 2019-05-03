package com.pl.skijumping.rest.web;

import com.pl.skijumping.batch.dataimportjob.scheduler.DataImportScheduler;
import com.pl.skijumping.batch.eventimportjob.EventIdImporterScheduler;
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
    private final EventIdImporterScheduler eventIdImporterScheduler;

    public BatchJobController(DataImportScheduler dataImporterStep,
                              EventIdImporterScheduler eventIdImporterScheduler) {
        this.dataImporterStep = dataImporterStep;
        this.eventIdImporterScheduler = eventIdImporterScheduler;
    }

    @PostMapping
    @RequestMapping(path = "dataRaceJobImporter")
    public ResponseEntity<BatchJobStatisticDTO> runDataRaceJob() throws InternalServiceException {
//        JobExecution jobExecution = dataImporterStep.parse();
//        BatchJobStatisticDTO batchJobStatisticDTO = StatisticGenerator.generate(jobExecution);
//        return new ResponseEntity<>(batchJobStatisticDTO, HttpStatus.OK);
        return null;
    }

    @PostMapping
    @RequestMapping(path = "importEventJob")
    public ResponseEntity<BatchJobStatisticDTO> runImportEventJob() throws InternalServiceException {
//        JobExecution jobExecution = eventIdImporterScheduler.importEvent();
//        BatchJobStatisticDTO batchJobStatisticDTO = StatisticGenerator.generate(jobExecution);
//        return new ResponseEntity<>(batchJobStatisticDTO, HttpStatus.OK);
        return null;
    }
}
