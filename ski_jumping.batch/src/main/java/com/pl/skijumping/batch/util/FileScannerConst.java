package com.pl.skijumping.batch.util;

public class FileScannerConst {
    private FileScannerConst() {
//
    }

    public static final String FILE_DATA_IMPORT = "import_";
    public static final String FILE_EVENT_ID = "event_";
    public static final String FILE_JUMP_RESULT = "jump_result_";
    public static final String FILE_SKI_JUMPER = "ski_jumper_";
    private static final String FILE_EXTENSION = ".txt";

    public static String prepareFileName(String prefix, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append(fileName);
        stringBuilder.append(FILE_EXTENSION);

        return stringBuilder.toString();
    }
}
