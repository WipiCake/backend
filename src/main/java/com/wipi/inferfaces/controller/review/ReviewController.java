package com.wipi.inferfaces.controller.review;

import com.wipi.domain.review.ReviewCommand;
import com.wipi.domain.review.ReviewResult;
import com.wipi.domain.review.ReviewService;
import com.wipi.domain.user.User;
import com.wipi.inferfaces.resolver.LoginUsers;
import com.wipi.model.rest.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/review")
@Tag(name = "review", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록", description = "form-data 형식으로 리뷰를 등록합니다.")
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse<Void> register(
            @ModelAttribute @Valid ReviewRequest.Register request,
            @Parameter(hidden = true)  @LoginUsers User user,
            @RequestPart("images") List<MultipartFile> images
    ) {
        reviewService.register(ReviewCommand.Register.of(
                request.getProductId(),
                user.getUserId(),
                request.getTitle(),
                request.getContent(),
                request.getStarCount(),
                images
        ));
        return APIResponse.success();
    }

    @Operation(summary = "요청 상품 리뷰 전체 조회")
    @GetMapping(value = "/getAll/{productId}")
    public APIResponse<List<ReviewResult.GetAll>> getAll(@PathVariable Long productId) {
        List<ReviewResult.GetAll> list = reviewService.getAll(productId);
        return APIResponse.success(list);
    }



}
