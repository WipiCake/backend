package com.wipi.inferfaces.api.controller.test;

import com.wipi.inferfaces.model.APIResponse;
import com.wipi.inferfaces.model.param.ProcessSendSmsCoolParam;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/v1")
    public APIResponse<String> test(@Valid @RequestBody ProcessSendSmsCoolParam param) {
        return APIResponse.success();
    }

}
