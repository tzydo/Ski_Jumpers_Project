package com.pl.skijumping.batchclient;

import feign.Param;
import feign.RequestLine;

public interface BatchClient {
    @RequestLine(value = "POST /api/v1/runjob?month={month}&year={year}")
    public void runImportDataJob(@Param(value = "month") Integer month, @Param(value = "year") Integer year);
}
