package com.pl.skijumping.batch.util;

import com.pl.skijumping.common.util.FileUtil;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class FileScanner {
    private FileScanner() {
//
    }

    public static Set<Path> getPathList(String pattern, Path directory) {
        List<Path> pathList = FileUtil.getPathList(directory);
        return pathList.stream()
                .filter(Objects::nonNull)
                .filter(path -> path.getFileName().toString().contains(pattern))
                .collect(Collectors.toSet());
    }
}
