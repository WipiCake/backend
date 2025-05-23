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

    @Transactional
    public void update(ReviewCommand.Update command) {
        reviewRepository.save(Review.of(
                command.getReviewId(), command.getProductId(), command.getUserId(),
                command.getTitle(), command.getContent(), command.getStarCount()
        ));

        List<ReviewImage> existingImages = reviewImageRepository.findAllByReviewId(command.getReviewId());
        reviewImageRepository.deleteAllByReviewId(command.getReviewId());

        List<Map<String, String>> savedImages = new ArrayList<>();
        try {
            for (MultipartFile file : command.getImages()) {
                Map<String, String> image = commFileService.saveImagesForPath(file, basePath);
                savedImages.add(image);
                reviewImageRepository.save(ReviewImage.of(
                        command.getReviewId(),
                        image.get("originalFileName"),
                        image.get("savedFileName"),
                        image.get("webPath")
                ));
            }
        } catch (Exception e) {
            for (Map<String, String> image : savedImages) {
                commFileService.deleteFile(basePath, image.get("savedFileName"));
            }
            throw new RuntimeException("리뷰 이미지 수정 중 오류 발생", e);
        }

        try {
            for (ReviewImage image : existingImages) {
                commFileService.deleteFile(basePath, image.getSavedFileName());
            }
        } catch (Exception ex) {
            log.warn("기존 리뷰 이미지 파일 삭제 중 오류 발생", ex);
        }

        log.info("리뷰 수정 성공");
    }


    public List<ReviewResult.GetAll> getAll(Long productId){
        List<Review> reviewList = reviewRepository.findAllByProductId(productId);
        List<ReviewResult.GetAll> resultList = new ArrayList<>();

        for(Review review : reviewList){
            List<ReviewImage> imageList = reviewImageRepository.findAllByReviewId(review.getReviewId());
            List<String> resourceList = new ArrayList<>();

            for(ReviewImage image : imageList){
                String imageResource = commFileService.loadImage(image.getSavedFileName(), basePath);
                resourceList.add(imageResource);
            }
            ReviewResult.GetAll result = ReviewResult.GetAll.of(review.getReviewId(),review.getUserId(),review.getTitle(),
            review.getContent(),review.getStarCount(),resourceList);
            resultList.add(result);
        }

        return resultList;
    }


}
