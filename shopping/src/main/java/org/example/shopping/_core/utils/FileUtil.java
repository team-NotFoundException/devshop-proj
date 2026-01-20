package org.example.shopping._core.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUtil {

    private static String imagesDir;

    @Value("${file.upload.path}")
    public void setImagesDir(String value) {
        imagesDir = value;
    }


        public static String saveFile(MultipartFile file) throws IOException {
            return saveFile(file, imagesDir);
        }

        public static String saveFile(MultipartFile file, String uploadDir) throws IOException {
            if (file == null || file.isEmpty()){
                return null;
            }

            Path uploadPath = Paths.get(imagesDir);

            if (!Files.exists(uploadPath)) {

                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            if(originalFilename == null || originalFilename.isEmpty()) {
                throw new IOException("파일명이 없습니다.");
            }

            String uuid = UUID.randomUUID().toString();

            String savedFileName = uuid + "_" + originalFilename;

            Path filePath = uploadPath.resolve(savedFileName);

            Files.copy(file.getInputStream(), filePath);

            return savedFileName;
        }

        public static boolean isImageFile(MultipartFile file) {
            if (file == null || file.isEmpty()) {
                return false;
            }

            String contentType = file.getContentType();

            return contentType != null && contentType.startsWith("image/");
        }

        public static void deleteFile(String filename) throws IOException {
            deleteFile(filename, imagesDir);
        }

        public static void deleteFile(String filename, String uploadDir) throws IOException {
            if(filename == null || filename.isEmpty()) {
                return;
            }

            Path filePath = Paths.get(uploadDir, filename);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        }
}
