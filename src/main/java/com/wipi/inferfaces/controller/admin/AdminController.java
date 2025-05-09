package com.wipi.inferfaces.controller.admin;


import com.wipi.app.admin.AdminFrontService;
import com.wipi.model.dto.res.ResGetJwtInfoAll;
import com.wipi.model.rest.RestResponse;
import com.wipi.model.rest.RestResponseEntity;
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
    public ResponseEntity<RestResponse<List<ResGetJwtInfoAll>>> getJwtInfoAll(){
        List<ResGetJwtInfoAll> resDto = adminFrontService.getJwtInfoAll();

        return RestResponseEntity.ok("전체 조회에 성공하였습니다.", resDto);
    }

}
