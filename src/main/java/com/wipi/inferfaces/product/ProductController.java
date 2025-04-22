package com.wipi.inferfaces.product;

import com.wipi.domain.product.ProductInfo;
import com.wipi.domain.product.ProductService;
import com.wipi.inferfaces.model.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Tag(name = "상품", description = "상품 등록 및 관리 API")
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Void> register(
            @RequestPart("request") @Valid ProductRequest.Register request,
            @RequestPart("images") List<MultipartFile> images
    ) {
        productService.productRegister(request, images);
        return ApiResponse.success();
    }

    @GetMapping("/getAll")
    public ApiResponse<List<ProductInfo.ListSelling>> getAllSellingProducts() {
        List<ProductInfo.ListSelling> result = productService.getAllSelling();
        return ApiResponse.success(result);
    }

}
