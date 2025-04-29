package com.wipi.inferfaces.api.controller.product;

import com.wipi.app.product.ProductFacadeService;
import com.wipi.domain.product.ProductService;
import com.wipi.inferfaces.model.APIResponse;
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
    private final ProductService productService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse<Void> register(
            @ModelAttribute ProductRequest.Register request,
            @RequestPart("thumbNailImage") MultipartFile thumbnail,
            @RequestPart("detailImages") List<MultipartFile> detailImages)
    {
        request.setThumbNailImage(thumbnail);
        request.setDetailImages(detailImages);
        productFacadeService.registerProduct(request.toCriteria());
        return APIResponse.success();
    }

    @GetMapping("/getSelling")
    public APIResponse<List<ProductResponse.GetSelling>> getSelling() {
        List<ProductResponse.GetSelling> list = productService.getAllSellingList()
                .stream()
                .map(ProductResponse.GetSelling::fromGetSellingInfo)
                .toList();

        return APIResponse.success(list);
    }

}
