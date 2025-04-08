package com.wipi.inferfaces.controller;


import com.wipi.app.AdminFrontService;
import com.wipi.inferfaces.dto.ResGetJwtInfoAll;
import com.wipi.inferfaces.rest.RestResponse;
import com.wipi.inferfaces.rest.RestResponseEntity;
import com.wipi.support.swagger.RestGetJwtInfoAllWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(description = "관리자 권한 API", name = "관리자 API")
public class AdminController {

    private final AdminFrontService adminFrontService;

    @GetMapping("/getJwtInfoAll")
    @Operation(summary = "Jwt 전체조회", description = "Redeis에 존재하는 Access, Refresh 토큰을 전체 조회한다. ")
    @ApiResponse(
            responseCode = "200",
            description = "성공 시 [status,message,data] 형식으로 반환",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RestGetJwtInfoAllWrapper.class)
            )
    )
    public ResponseEntity<RestResponse<List<ResGetJwtInfoAll>>> getJwtInfoAll(){
        List<ResGetJwtInfoAll> resDto = adminFrontService.getJwtInfoAll();

        return RestResponseEntity.ok("전체 조회에 성공하였습니다.", resDto);
    }

}
