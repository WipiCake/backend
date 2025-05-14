package com.wipi.infra.comm;

import com.wipi.support.properties.ImagesPathProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommFileService {

    private final ImagesPathProperties imagesPathProperties;

    public String loadImage(String savedFileName, String basePath) {
        try {
            String uploadDir = imagesPathProperties.getPath().replace("file:", "") +imagesPathProperties.getSrc() + basePath;
            File file = new File(uploadDir, savedFileName);

            if (!file.exists()) {
                throw new RuntimeException("이미지를 찾을 수 없습니다: " + savedFileName);
            }

            String urlPath = basePath + "/" + savedFileName;
            return urlPath.replace("\\", "/");
        } catch (Exception e) {
            throw new RuntimeException("이미지 로드 중 오류 발생: " + savedFileName, e);
        }
    }

    public Map<String, String> saveImagesForPath(MultipartFile file, String basePath) {
        try {
            String uploadDir = imagesPathProperties.getPath().replace("file:", "") + basePath;
            String uuid = UUID.randomUUID().toString();
            String originalFileName = file.getOriginalFilename();
            String savedFileName = uuid + "_" + originalFileName;
            String savedFullPath = uploadDir + File.separator + savedFileName;

            File dest = new File(savedFullPath);


            dest.getParentFile().mkdirs();
            file.transferTo(dest);
            String webPath = imagesPathProperties.getSrc().replace("/**", "") + basePath + "/" + savedFileName;

            return Map.of(
                    "originalFileName", originalFileName,
                    "savedFileName", savedFileName,
                    "webPath", webPath
            );
        } catch (Exception e) {
            throw new RuntimeException("이미지 저장 중 오류가 발생했습니다.", e);
        }
    }


}
