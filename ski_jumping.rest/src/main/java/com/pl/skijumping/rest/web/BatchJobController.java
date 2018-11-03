package com.pl.skijumping.rest.web;

import com.pl.skijumping.batch.dataimportjob.scheduler.DataImportScheduler;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.dto.BatchJobStatisticDTO;
import org.springframework.batch.core.JobExecution;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/admin")
public class BatchJobController {

    private final DataImportScheduler dataImporterStep;

    public BatchJobController(DataImportScheduler dataImporterStep) {
        this.dataImporterStep = dataImporterStep;
    }

    @RequestMapping(path = "dataRaceJobImporter", method = RequestMethod.POST)
    public ResponseEntity<BatchJobStatisticDTO> getCountriesList() throws InternalServiceException {
        JobExecution jobExecution = dataImporterStep.importData();
        BatchJobStatisticDTO batchJobStatisticDTO = StatisticGenerator.generate(jobExecution);
        return new ResponseEntity<>(batchJobStatisticDTO, HttpStatus.OK);
    }
}
