package com.example.zipUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Application {
    public static void main(String[] args) throws IOException {
        // zip single file with deletion
        File sourceFile0 = new File("zip-file/src/test/resources");
        File sourceFile1 = new File(sourceFile0.getAbsolutePath() + File.separator+ "test1.txt");
        File sourceFile2 = new File(sourceFile0.getAbsolutePath() + File.separator+ "test2.txt");
        ZipUtils.zipFile(sourceFile1.getAbsolutePath(), "c:/temp/sabiSingle.zip",false);
        // zip multi files with deletion
        List<String> list = new ArrayList<String>();
        list.add(sourceFile1.getAbsolutePath());
        list.add(sourceFile2.getAbsolutePath());
        ZipUtils.zipMultiFiles(list, "c:/temp/sabiMultiFiles.zip",false);
        // zip directory with deletion
        ZipUtils.zipDirectory(sourceFile0.getAbsolutePath(), "c:/temp/sabiDirectory.zip",false);
        // unzip all zips created without deleting them
        ZipUtils.unzipFile("c:/temp/sabiSingle.zip", "c:/temp", false);
        ZipUtils.unzipFile("c:/temp/sabiMultiFiles.zip", "c:/temp", false);
        ZipUtils.unzipFile("c:/temp/sabiDirectory.zip", "c:/temp", false);
    }
}
