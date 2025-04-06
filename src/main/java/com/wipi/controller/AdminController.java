package com.wipi.controller;

import com.wipi.model.param.AccessLogGetAllParam;
import com.wipi.model.result.RestResult;
import com.wipi.service.front.LogFrontService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final LogFrontService logFrontService;

    @Operation(summary = "AccessLog 페이징", description = "Admin URL 테스트 입니다. 기능적인 요소는 아닙니다.")
    @PostMapping("/accessLog/paging")
    public RestResult getAccessLogs(@RequestBody AccessLogGetAllParam param){
        return logFrontService.getAccessLogs(param);
    }





}
