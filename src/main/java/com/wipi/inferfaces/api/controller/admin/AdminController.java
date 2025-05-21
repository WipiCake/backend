package com.wipi.inferfaces.api.controller.admin;

import com.wipi.app.admin.AdminFrontService;
import com.wipi.inferfaces.model.dto.res.ResGetJwtInfoAll;
import com.wipi.inferfaces.model.rest.RestResponse;
import com.wipi.inferfaces.model.rest.RestResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "관리자 API", description = "관리자 권한 API")
public class AdminController {

    private final AdminFrontService adminFrontService;

    @Operation(
            summary = "JWT 조회",
            description = "Redis에 저장된 jwt 정보 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공"
    )
    @GetMapping("/getJwtInfoAll")
    public ResponseEntity<RestResponse<List<ResGetJwtInfoAll>>> getJwtInfoAll() {
        List<ResGetJwtInfoAll> resDto = adminFrontService.getJwtInfoAll();
        return RestResponseEntity.ok("전체 조회에 성공하였습니다.", resDto);
    }

}
