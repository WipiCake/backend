package com.wipi.inferfaces.api.controller.product;

import com.wipi.app.product.ProductFacadeService;
import com.wipi.domain.product.ProductService;
import com.wipi.inferfaces.model.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/product")
@Tag(name = "상품 api", description = "상품 등록 및 조회 기능")
public class ProductController {

    private final ProductFacadeService productFacadeService;
    private final ProductService productService;

    @Operation(
            summary = "상품 등록",
            description = "Multipart/form-data 로 전달된 정보를 받아 새 상품을 등록"
    )
    @ApiResponse(responseCode = "200", description = "상품 등록 성공")
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse<Void> register(
            @Parameter(description = "상품 정보", required = true)
            @ModelAttribute ProductRequest.Register request,
            @Parameter(
                    description = "썸네일 이미지 파일",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                            schema = @Schema(type = "string", format = "binary")
                    )
            )
            @RequestPart("thumbNailImage") MultipartFile thumbnail,
            @Parameter(
                    description = "상세 이미지 파일 목록",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(type = "string", format = "binary")
                            )
                    )
            )
            @RequestPart("detailImages") List<MultipartFile> detailImages)
    {
        request.setThumbNailImage(thumbnail);
        request.setDetailImages(detailImages);
        productFacadeService.registerProduct(request.toCriteria());
        return APIResponse.success();
    }

    @Operation(
            summary = "판매 중 상품 조회",
            description = "현재 판매 중인 상품들의 목록을 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProductResponse.GetSelling.class)
            )
    )
    // @GetMapping("/getSelling")
    @GetMapping(
            path = "/getSelling",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public APIResponse<List<ProductResponse.GetSelling>> getSelling() {
        List<ProductResponse.GetSelling> list = productService.getAllSellingList()
                .stream()
                .map(ProductResponse.GetSelling::fromGetSellingInfo)
                .toList();

        return APIResponse.success(list);
    }

}
