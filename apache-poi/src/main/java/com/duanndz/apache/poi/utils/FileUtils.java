package com.duanndz.apache.poi.utils;

import java.io.File;

/**
 * Created By duan.nguyen at 5/17/20 8:53 AM
 */
public final class FileUtils {

    private FileUtils() {

    }

    public static File getFile(String filePath) {
        File file = new File(filePath);
        file.deleteOnExit();
        return new File(filePath);
    }
}
