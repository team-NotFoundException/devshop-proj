package org.example.shopping._core.fileupload;

import org.example.shopping._core.utils.FileUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class ImageUploadController {

    /**
     * Toast UI Editor 이미지 업로드
     * 파라미터명 주의: 'image' 또는 'file' 둘 다 처리
     */
    @PostMapping("/editor-image")
    public ResponseEntity<?> uploadEditorImage(
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        System.out.println("=== 이미지 업로드 API 호출됨 ===");

        // image 또는 file 파라미터 중 하나 사용
        MultipartFile uploadFile = (image != null) ? image : file;

        if (uploadFile == null || uploadFile.isEmpty()) {
            System.out.println("업로드된 파일이 없음");
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "파일이 없습니다."));
        }

        System.out.println("파일명: " + uploadFile.getOriginalFilename());
        System.out.println("파일 크기: " + uploadFile.getSize() + " bytes");
        System.out.println("파일 타입: " + uploadFile.getContentType());

        try {
            // 1. 이미지 파일 검증
            if (!FileUtil.isImageFile(uploadFile)) {
                System.out.println("이미지 파일이 아님");
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "이미지 파일만 업로드 가능합니다."));
            }

            // 2. 파일 저장
            String savedFileName = FileUtil.saveFile(uploadFile);
            System.out.println("저장된 파일명: " + savedFileName);

            if (savedFileName == null) {
                System.out.println("파일 저장 실패 (null 반환)");
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "파일 저장에 실패했습니다."));
            }

            // 3. 응답 데이터 생성
            Map<String, String> response = new HashMap<>();
            response.put("fileName", savedFileName);
            response.put("url", "/images/" + savedFileName);

            System.out.println("응답 데이터: " + response);
            System.out.println("=== 업로드 성공 ===");

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            System.err.println("파일 저장 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "파일 저장 중 오류: " + e.getMessage()));
        }
    }
}