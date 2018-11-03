package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchJobStatisticDTO implements Serializable {

    private String jobName;
    private String exitStatus;
    private String errorMessage;
    private JobStatus jobStatus;

    public BatchJobStatisticDTO errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public BatchJobStatisticDTO jobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
        return this;
    }

    public BatchJobStatisticDTO jobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public BatchJobStatisticDTO exitStatus(String exitStatus) {
        this.exitStatus = exitStatus;
        return this;
    }
}
