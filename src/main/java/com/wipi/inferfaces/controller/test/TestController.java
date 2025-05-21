package com.wipi.inferfaces.controller.test;

import com.wipi.model.rest.APIResponse;
import com.wipi.model.param.ProcessSendSmsCoolParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(name = "test", description = "테스트 API")
public class TestController {

    @PostMapping("/v3")
    public APIResponse<String> test(@Valid @RequestBody ProcessSendSmsCoolParam param) {
        return APIResponse.success();
    }

}
