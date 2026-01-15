package org.example.shopping._core.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtil {

    @Value("${file.upload.path}")
    private static String imagesDir;


        public static final String IMAGES_DIR = imagesDir; // 외부에 폴더 생성

        public static String saveFile(MultipartFile file) throws IOException {
            return saveFile(file, IMAGES_DIR);
        }

        public static String saveFile(MultipartFile file, String uploadDir) throws IOException {
            // 1. 유효성 검사
            if (file == null || file.isEmpty()){
                return null; // 파일이 없으면 null (왜? 선택사항이므로 에러 아님)
            }

            // 2. 업로드 디렉토리 생성
            Path uploadPath = Paths.get(IMAGES_DIR);

            if (!Files.exists(uploadPath)) {

                Files.createDirectories(uploadPath);
            }

            // 3. 원본 파일명 가져오기
            String originalFilename = file.getOriginalFilename();
            if(originalFilename == null || originalFilename.isEmpty()) {
                throw new IOException("파일명이 없습니다.");
            }

            // 4. UUID 사용한 고유한 파일명 생성
            String uuid = UUID.randomUUID().toString();
            String savedFileName = uuid + "_" + originalFilename;

            // 5. 파일을 디스크(물리적 저장 장치)에 저장
            Path filePath = uploadPath.resolve(savedFileName);

            // 실제 파일 생성
            Files.copy(file.getInputStream(), filePath);

            return savedFileName;
        }

        // 유효성 검사 기능
        public static boolean isImageFile(MultipartFile file) {
            // 파일 이미지가 없으면 이미지가 아님
            if (file == null || file.isEmpty()) {
                return false;
            }

            // Content-Type 가져오기
            String contentType = file.getContentType();

            // Content-Type 이 image/ 로 시작하는지 확인
            return contentType != null && contentType.startsWith("image/");
        }

        // 이미지 삭제 처리
        public static void deleteFile(String filename) throws IOException {
            deleteFile(filename, IMAGES_DIR);
        }

        public static void deleteFile(String filename, String uploadDir) throws IOException {
            // 방어적 코드 (파일 이름이 없다면 삭제할 것이 없음)
            if(filename == null || filename.isEmpty()) {
                return;
            }

            // 삭제할 파일의 전체 경로를 생성
            Path filePath = Paths.get(uploadDir, filename);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

            // 파일이 없으면 그냥 종료
        }
}
