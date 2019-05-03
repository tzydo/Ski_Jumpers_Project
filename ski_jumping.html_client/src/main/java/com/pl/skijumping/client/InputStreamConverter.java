package com.pl.skijumping.client;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

class InputStreamConverter {
    private InputStreamConverter() {}

    public static String convert(InputStream inputStream, Charset charset) throws IOException {
        return IOUtils.toString(inputStream, charset);
    }
}
