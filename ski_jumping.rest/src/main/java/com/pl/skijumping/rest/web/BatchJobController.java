package com.pl.skijumping.rest.web;

import com.pl.skijumping.batch.dataimportjob.scheduler.DataImportScheduler;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.dto.BatchJobStatisticDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/admin")
public class BatchJobController {

    private final DataImportScheduler dataImporterStep;

    public BatchJobController(DataImportScheduler dataImporterStep) {
        this.dataImporterStep = dataImporterStep;
    }

    @PostMapping
    @RequestMapping(path = "dataRaceJobImporter")
    public ResponseEntity<BatchJobStatisticDTO> runDataRaceJob(@RequestParam(value = "month") Integer month, @RequestParam(value = "year") Integer year) throws InternalServiceException {
        dataImporterStep.addJobParameter("date", String.format("%s-%02d-01", year, month));
        dataImporterStep.importData();
        return new ResponseEntity<>(HttpStatus.OK);
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
