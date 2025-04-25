package com.wipi.inferfaces.api.controller.product;

import com.wipi.app.product.ProductFacadeService;
import com.wipi.inferfaces.model.ApiResponse;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductFacadeService productFacadeService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Void> register(
            @RequestPart("product") ProductRequest.Register request,
            @RequestPart("thumbNailImage") MultipartFile thumbnail,
            @RequestPart("detailImages") List<MultipartFile> detailImages)
    {
        request.setThumbNailImage(thumbnail);
        request.setDetailImages(detailImages);
        productFacadeService.registerProduct(request.toCriteria());

        return ApiResponse.success();
    }

}
