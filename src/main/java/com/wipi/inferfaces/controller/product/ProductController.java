package com.wipi.inferfaces.controller.product;

import com.wipi.app.product.ProductFacadeService;
import com.wipi.domain.product.ProductInfo;
import com.wipi.domain.product.ProductService;
import com.wipi.model.rest.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/product")
@Tag(name = "product", description = "상품 관련 API")
public class ProductController {

    private final ProductFacadeService productFacadeService;
    private final ProductService productService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse<Void> register(
            @Valid ProductRequest.Register request,
            @RequestPart("thumbNailImage") MultipartFile thumbnail,
            @RequestPart("detailImages") List<MultipartFile> detailImages
    ) {
        request.setThumbNailImage(thumbnail);
        request.setDetailImages(detailImages);
        productFacadeService.registerProduct(request.toCriteria());
        return APIResponse.success();
    }

    @Operation(
            summary = "메인페이지 전체 상품 조회",
            description = """
            전체 판매중인 상품 목록을 조회합니다.
            
            - 각 상품 객체에는 이미지 파일명이 포함되어 있으며, 이를 다음과 같이 조합해 이미지 URL을 구성할 수 있습니다:
            
              `http://104.197.93.245:8080/{thumbnail}`
            
            - 예시:
              `http://104.197.93.245:8080/img/product/123a1bbd-caa7-45d7-b826-47c93dd37812_Cake.jfif`
            
            이 URL은 `<img src="...">`로 사용 가능하며, 메인페이지 이미지 노출에 활용됩니다.
            """
                )
    @GetMapping("/getSelling")
    public APIResponse<List<ProductResponse.GetSelling>> getSelling() {
        List<ProductResponse.GetSelling> list = productService.getAllSellingList()
                .stream()
                .map(ProductResponse.GetSelling::fromGetSellingInfo)
                .toList();

        return APIResponse.success(list);
    }

    @Operation(
            summary = "상품 상세 조회",
            description = """
                상품 상세 정보를 조회합니다.
                - 이미지 URL 예시:  
                  `https://wipi-backend-dpe6cxe4b4dya4g9.koreacentral-01.azurewebsites.net/img/product/123a1bbd-caa7-45d7-b826-47c93dd37812_Cake.jfif`
                
                이 URL을 `<img src="..." />`와 같이 HTML에 사용하면 이미지가 정상적으로 표시됩니다.
                """
    )
    @GetMapping("/getDetail/{productId}")
    public APIResponse<ProductInfo.ProductDetail> getDetail(@PathVariable Long productId) {
        ProductInfo.ProductDetail data = productService.getProductDetail(productId);
        return APIResponse.success(data);
    }

}
