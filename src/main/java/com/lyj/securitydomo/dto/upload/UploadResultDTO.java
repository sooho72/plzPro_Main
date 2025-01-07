package com.lyj.securitydomo.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {

    private String uuid;
    private String fileName;
    private int pno;

    /**
     * 썸네일 이미지 링크를 반환하는 메서드
     * @return 썸네일 이미지 링크
     */
    public String getThumbnailLink() {
        return (uuid != null && fileName != null)
                ? "/view/s_" + uuid + "_" + fileName  // 썸네일 파일명으로 생성
                : getRandomImage(); // 썸네일 이미지가 없으면 랜덤 이미지 반환
    }

    /**
     * 원본 이미지 링크를 반환하는 메서드
     * @return 원본 이미지 링크
     */
    public String getOriginalLink() {
        return (uuid != null && fileName != null)
                ? "/view/" + uuid + "_" + fileName // 원본 파일명으로 생성
                : getRandomImage();
    }

    /**
     * 기본 랜덤 이미지를 반환하는 메서드
     * 업로드된 이미지가 없는 경우 호출됩니다.
     *
     * @return 기본 랜덤 이미지 링크
     */
    public static String getRandomImage() {
        String[] defaultImages = {
                "https://dummyimage.com/450x300/dee2e6/6c757d.jpg",
                "https://dummyimage.com/450x300/ced4da/495057.jpg",
                "https://dummyimage.com/450x300/e9ecef/adb5bd.jpg"
        };
        Random random = new Random();
        return defaultImages[random.nextInt(defaultImages.length)];
    }
}