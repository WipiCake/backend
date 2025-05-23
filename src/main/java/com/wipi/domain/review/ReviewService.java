package com.wipi.domain.review;

import com.wipi.infra.comm.CommFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final CommFileService commFileService;
    private final static String basePath = "/review";

    @Transactional
    public void register(ReviewCommand.Register command){
        reviewRepository.save(Review.of(command.getProductId(),command.getUserId(),
        command.getTitle(), command.getContent(), command.getStarCount()));

        List<Map<String, String>> savedImages = new ArrayList<>();

        try {
            for (MultipartFile file : command.getImages()) {
                Map<String, String> image = commFileService.saveImagesForPath(file, basePath);
                savedImages.add(image);
                reviewImageRepository.save(ReviewImage.of(command.getProductId(),
                image.get("originalFileName"),image.get("savedFileName"),image.get("webPath") ));
            }
            log.info("리뷰 등록 성공");
        } catch (Exception e) {
            for (Map<String, String> image : savedImages) {
                String savedFileName = image.get("savedFileName");
                commFileService.deleteFile(basePath, savedFileName);
            }
            throw new RuntimeException("리뷰 이미지 저장 중 오류 발생", e);
        }
    }


}
