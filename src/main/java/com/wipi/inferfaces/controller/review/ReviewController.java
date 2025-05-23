package com.wipi.inferfaces.controller.review;

import com.wipi.domain.review.ReviewCommand;
import com.wipi.domain.review.ReviewService;
import com.wipi.domain.user.User;
import com.wipi.inferfaces.resolver.LoginUsers;
import com.wipi.model.rest.APIResponse;
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

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse<Void> register(@Valid ReviewRequest.Register request, @LoginUsers User user,
                                      @RequestPart("images") List<MultipartFile> images ){
        reviewService.register(ReviewCommand.Register.of(request.getProductId(),
        user.getUserId(), request.getTitle(),request.getContent(),request.getStarCount(),images));
        return APIResponse.success();
    }



}
