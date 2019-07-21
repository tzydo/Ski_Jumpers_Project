package com.pl.skijumping.batch.controller;

import com.pl.skijumping.batch.dataimportjob.scheduler.DataImportScheduler;
import com.pl.skijumping.common.exception.InternalServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class BatchController {
    private final DataImportScheduler dataImporterStep;

    public BatchController(DataImportScheduler dataImporterStep) {
        this.dataImporterStep = dataImporterStep;
    }

    @PostMapping(path = "dataImportJob")
    public void runImportDataJob(@RequestParam(value = "month") Integer month, @RequestParam(value = "year") Integer year) throws InternalServiceException {
        dataImporterStep.addJobParameter("date", String.format("%s-%02d-01", year, month));
        dataImporterStep.importData();
    }
}
