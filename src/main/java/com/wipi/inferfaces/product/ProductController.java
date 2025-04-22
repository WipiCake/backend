package com.wipi.inferfaces.product;

import com.wipi.domain.product.ProductService;
import com.wipi.inferfaces.model.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품", description = "상품 등록 및 관리 API")
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "상품 등록",
            description = "신규 상품을 등록합니다. 이미지, 설명, 가격 등의 정보를 포함해야 합니다."
    )
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ApiResponse<Void> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 등록 요청",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequest.Register.class))
            )
            @Valid @RequestBody ProductRequest.Register request
    ) {
        productService.productRegister(request.toCommand());
        return ApiResponse.success();
    }

}
