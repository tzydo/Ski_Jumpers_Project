package com.pl.skijumping.rest.web;


import com.pl.skijumping.batchclient.BatchClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/admin")
public class BatchJobController {
    private final BatchClient batchClient;

    public BatchJobController(BatchClient batchClient) {
        this.batchClient = batchClient;
    }

    @PostMapping
    @RequestMapping(path = "dataRaceJobImporter")
    public ResponseEntity runDataRaceJob(@RequestParam(value = "month") Integer month, @RequestParam(value = "year") Integer year) {
        batchClient.runImportDataJob(month, year);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
