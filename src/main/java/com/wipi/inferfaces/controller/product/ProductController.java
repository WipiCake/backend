package com.wipi.inferfaces.controller.product;

import com.wipi.app.product.ProductFacadeService;
import com.wipi.domain.product.ProductService;
import com.wipi.model.rest.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "product", description = "상품 관련 API")
public class ProductController {

    private final ProductFacadeService productFacadeService;
    private final ProductService productService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse<Void> register(
            ProductRequest.Register request,
            @RequestPart("thumbNailImage") MultipartFile thumbnail,
            @RequestPart("detailImages") List<MultipartFile> detailImages
    ) {
        request.setThumbNailImage(thumbnail);
        request.setDetailImages(detailImages);
        productFacadeService.registerProduct(request.toCriteria());
        return APIResponse.success();
    }

    @Operation(summary = "메인페이지 전체 상품 조회")
    @GetMapping("/getSelling")
    public APIResponse<List<ProductResponse.GetSelling>> getSelling() {
        List<ProductResponse.GetSelling> list = productService.getAllSellingList()
                .stream()
                .map(ProductResponse.GetSelling::fromGetSellingInfo)
                .toList();

        return APIResponse.success(list);
    }

}
