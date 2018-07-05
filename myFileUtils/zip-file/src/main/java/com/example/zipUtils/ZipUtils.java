package com.example.zipUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    private static final int READ_BUFFER_SIZE = 1024;

    public static void zipFile(String sourceFile, String targetZipFile, boolean deleteSource) throws IOException {
        File fileToZip = new File(sourceFile);
        try(FileOutputStream fos = new FileOutputStream(targetZipFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);
            final byte[] bytes = new byte[READ_BUFFER_SIZE];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            // since we have using we do not need to close
//            zipOut.close();
//            fis.close();
//            fos.close();
        }
        if (deleteSource)
            fileToZip.delete();
    }

    public static void zipMultiFiles(List<String> srcFiles, String targetZipFile, boolean deleteSource) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(targetZipFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            for (String srcFile : srcFiles) {
                File fileToZip = new File(srcFile);
                try(FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry);

                    byte[] bytes = new byte[READ_BUFFER_SIZE];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                }
                if (deleteSource)
                    fileToZip.delete();
            }
            // since we have using we do not need to close
//            zipOut.close();
//            fos.close();
        }
    }

    public static void zipDirectory(String sourceDirectory, String targetZipFile, boolean deleteSource) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(targetZipFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            File fileToZip = new File(sourceDirectory);

            zipFileInDir(fileToZip, fileToZip.getName(), zipOut, deleteSource);
        }
    }

    private static void zipFileInDir(File fileToZip, String fileName, ZipOutputStream zipOut, boolean deleteSource) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            File[] children = fileToZip.listFiles();
            if (children.length == 0) {
                ZipEntry zipEntry = new ZipEntry(fileName + File.separator);
                zipOut.putNextEntry(zipEntry);
            }
            else for (File childFile : children) {
                zipFileInDir(childFile, fileName + File.separator + childFile.getName(), zipOut, deleteSource);
            }
            if (deleteSource)
                fileToZip.delete();
            return;
        }
        try(FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[READ_BUFFER_SIZE];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
        if (deleteSource)
            fileToZip.delete();
    }

    public static void unzipFile(String sourceZipFile, String targetDirectory, boolean deleteSource) throws IOException {
        byte[] buffer = new byte[READ_BUFFER_SIZE];
        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(sourceZipFile))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    new File(targetDirectory + File.separator + fileName).mkdirs();
                    zipEntry = zis.getNextEntry();
                    continue;
                }
                File newFile = new File(targetDirectory + File.separator + fileName);
                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
        if (deleteSource) {
            File fileToZip = new File(sourceZipFile);
            fileToZip.delete();
        }
    }
}
