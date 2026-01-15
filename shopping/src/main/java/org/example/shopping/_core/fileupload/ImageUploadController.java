package org.example.shopping._core.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.example.shopping._core.utils.FileUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/upload")
public class ImageUploadController {

    /**
     * Toast UI Editor 이미지 업로드
     */
    @PostMapping("/editor-image")
    public ResponseEntity<?> uploadEditorImage(
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        log.info("Starting editor image upload process");

        // image 또는 file 파라미터 중 하나 사용
        MultipartFile uploadFile = (image != null) ? image : file;

        if (uploadFile == null || uploadFile.isEmpty()) {
            log.warn("Upload failed: No file found in request parameters 'image' or 'file'");
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "파일이 없습니다."));
        }

        // 파일 정보 로깅 (Debug 레벨 활용)
        log.debug("Received file - Name: {}, Size: {} bytes, Type: {}",
                uploadFile.getOriginalFilename(), uploadFile.getSize(), uploadFile.getContentType());

        try {
            // 1. 이미지 파일 검증
            if (!FileUtil.isImageFile(uploadFile)) {
                log.warn("Upload failed: File [{}] is not a valid image type", uploadFile.getOriginalFilename());
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "이미지 파일만 업로드 가능합니다."));
            }

            // 2. 파일 저장
            String savedFileName = FileUtil.saveFile(uploadFile);

            if (savedFileName == null) {
                log.error("Upload failed: FileUtil.saveFile returned null for file [{}]", uploadFile.getOriginalFilename());
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "파일 저장에 실패했습니다."));
            }

            // 3. 응답 데이터 생성
            Map<String, String> response = new HashMap<>();
            response.put("fileName", savedFileName);
            response.put("url", "/images/" + savedFileName);

            log.info("Upload successful: Saved as [{}]", savedFileName);
            log.debug("Response data sent: {}", response);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            log.error("Critical error during file storage for [{}]: {}", uploadFile.getOriginalFilename(), e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "파일 저장 중 오류: " + e.getMessage()));
        }
    }
}