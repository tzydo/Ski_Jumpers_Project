package com.pl.skijumping.batch.util;

import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.util.Optional;

public class FileDelete {
    private final DiagnosticMonitor diagnosticMonitor;
    private final String filePath;
    private final StepContribution stepContribution;

    public FileDelete(DiagnosticMonitor diagnosticMonitor, String filePath, StepContribution stepContribution) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.filePath = filePath;
        this.stepContribution = stepContribution;
    }

    public RepeatStatus delete() throws InternalServiceException {
        if(filePath.isEmpty()) {
            String errorMessage = "Cannot delete file from null filepath";
            stepContribution.setExitStatus(new ExitStatus(errorMessage));
            diagnosticMonitor.logWarn(errorMessage);

            return RepeatStatus.FINISHED;
        }

        Optional<File> file = FileUtil.getFile(filePath);
        if(!file.isPresent()) {
            String errorMessage = "Cannot delete file from empty filepath";
            stepContribution.setExitStatus(new ExitStatus(errorMessage));
            diagnosticMonitor.logWarn("Cannot delete file from empty filepath");
            return RepeatStatus.FINISHED;
        }

        FileUtil.deleteFile(file.get().getPath());
        stepContribution.setExitStatus(ExitStatus.COMPLETED);
        diagnosticMonitor.logInfo("Successfully delete file");
        return RepeatStatus.FINISHED;
    }
}
