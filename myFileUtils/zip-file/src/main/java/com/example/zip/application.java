package com.example.zip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class application {
    public static void main(String[] args) throws IOException {
        // zip single file with deletion
        File sourceFile0 = new File("src/test/resources");
        File sourceFile1 = new File(sourceFile0.getAbsolutePath() + File.separator+ "test1.txt");
        File sourceFile2 = new File(sourceFile0.getAbsolutePath() + File.separator+ "test2.txt");
        zipUtils.zipFile(sourceFile1.getAbsolutePath(), "c:/temp/sabiSingle.zip",false);
        // zip multi files with deletion
        List<String> list = new ArrayList<String>();
        list.add(sourceFile1.getAbsolutePath());
        list.add(sourceFile2.getAbsolutePath());
        zipUtils.zipMultiFiles(list, "c:/temp/sabiMultiFiles.zip",false);
        // zip directory with deletion
        zipUtils.zipDirectory(sourceFile0.getAbsolutePath(), "c:/temp/sabiDirectory.zip",false);
        // unzip all zips created without deleting them
        zipUtils.unzipFile("c:/temp/sabiSingle.zip", "c:/temp", false);
        zipUtils.unzipFile("c:/temp/sabiMultiFiles.zip", "c:/temp", false);
        zipUtils.unzipFile("c:/temp/sabiDirectory.zip", "c:/temp", false);
    }
}
