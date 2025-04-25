package com.wipi.e2e.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("상품 등록 API가 정상적으로 동작한다")
    void registerProduct() throws Exception {
        String productJson = """
            {
              "name": "테스트 상품",
              "price": 10000,
              "description": "테스트 상품 설명",
              "type": "TEST_TYPE",
              "sellStatus": "SELLING",
              "quantity": 100
            }
            """;

        MockMultipartFile productPart = new MockMultipartFile(
                "product",
                "product.json",
                "application/json",
                productJson.getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile thumbNailImage = new MockMultipartFile(
                "thumbNailImage",
                "thumbnail.jpg",
                "image/jpeg",
                "썸네일 이미지 데이터".getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile detailImage1 = new MockMultipartFile(
                "detailImages",
                "detail1.jpg",
                "image/jpeg",
                "디테일 이미지1 데이터".getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile detailImage2 = new MockMultipartFile(
                "detailImages",
                "detail2.jpg",
                "image/jpeg",
                "디테일 이미지2 데이터".getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/product/register")
                        .file(productPart)
                        .file(thumbNailImage)
                        .file(detailImage1)
                        .file(detailImage2)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk());
    }
}
