package com.pl.skijumping.client;

import com.pl.skijumping.common.exception.InternalServiceException;

import java.io.IOException;
import java.nio.file.Path;

public interface IHtmlDownloader {
    Path downloadSource(Path filePath, String host) throws IOException, InternalServiceException;
}
