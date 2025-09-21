// 代码生成时间: 2025-09-22 03:25:30
package com.example.filedecompression;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedOutputStream;
import java.io.OutputStream;

@Component
public class FileDecompressionUtility {

    @Value("{decompression.directory}")
    private String decompressionDirectory; // Configuration property for decompression directory

    private final ResourceLoader resourceLoader;

    public FileDecompressionUtility(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Decompresses a zip file to a specified directory.
     * 
     * @param zipFile The zip file to decompress.
     * @param targetDirectory The directory to decompress the file to.
     * @throws IOException If an I/O error occurs.
     */
    public void decompressZipFile(MultipartFile zipFile, String targetDirectory) throws IOException {
        if (zipFile == null || zipFile.isEmpty()) {
            throw new IllegalArgumentException("Zip file must not be null or empty.");
        }

        // Ensure the target directory exists
        Files.createDirectories(Paths.get(targetDirectory));

        // Decompress the zip file
        try (ZipFile zip = new ZipFile(zipFile.getInputStream())) {
            zip.stream().forEach(zipEntry -> {
                Path targetPath = Paths.get(targetDirectory, zipEntry.getName());
                try {
                    if (zipEntry.isDirectory()) {
                        Files.createDirectories(targetPath);
                    } else {
                        Files.copy(zip.getFile(), targetPath);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error decompressing file: " + zipEntry.getName(), e);
                }
            });
        }
    }

    /**
     * Compresses files in a directory into a zip file.
     * 
     * @param directoryPath The directory path to compress.
     * @return The compressed zip file as a byte array.
     * @throws IOException If an I/O error occurs.
     */
    public byte[] compressDirectoryToZip(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        try (ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(decompressionDirectory + "/compressed.zip")));) {
            Files.walk(path).forEach(filePath -> {
                try {
                    if (Files.isRegularFile(filePath)) {
                        Path relativePath = path.relativize(filePath);
                        ZipEntry zipEntry = new ZipEntry(relativePath.toString());
                        zipOut.putNextEntry(zipEntry);
                        Files.copy(filePath, zipOut);
                        zipOut.closeEntry();
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error compressing file: " + filePath.toString(), e);
                }
            });
        }
        return Files.readAllBytes(Paths.get(decompressionDirectory + "/compressed.zip"));
    }
}
