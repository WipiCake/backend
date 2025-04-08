package com.wipi.wipi;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipi.controller.UserController;
import com.wipi.feat.model.dto.ResUserSignupDto;
import com.wipi.feat.service.front.UserFrontService;
import com.wipi.rest.param.UserSignupParam;
import com.wipi.rest.result.RestUserSignup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class IntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserFrontService userFrontService;

    @Test
    @DisplayName("회원가입 성공")
    void signupSuccess() throws Exception {
        // given
        UserSignupParam param = new UserSignupParam(
                "jaemin123",
                "jaemin@example.com",
                "password123",
                "재민",
                "01012345678",
                "06578",
                "서울시 서초구 강남대로 123",
                "101동 202호",
                "20011212",
                "man"
        );

        ResUserSignupDto fakeDto = new ResUserSignupDto(
                "jaemin123",
                "jaemin@example.com"
        );

        Mockito.when(userFrontService.userSignup(any(UserSignupParam.class)))
                .thenReturn(fakeDto);

        // when & then
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(param)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value("상품 조회에 성공하였습니다."))
                .andExpect(jsonPath("$.data.userId").value("jaemin123"))
                .andExpect(jsonPath("$.data.email").value("jaemin@example.com"));
    }
}
