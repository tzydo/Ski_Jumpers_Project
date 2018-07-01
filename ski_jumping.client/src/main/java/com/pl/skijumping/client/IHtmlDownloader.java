package com.pl.skijumping.client;

import com.pl.skijumping.common.exception.InternalServiceException;

import java.io.IOException;

public interface IHtmlDownloader {
    String downloadSource(String filePath, String host) throws IOException, InternalServiceException;
}
