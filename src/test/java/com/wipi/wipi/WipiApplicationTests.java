package com.wipi.wipi;

import com.wipi.feat.model.dto.ResUserSignupDto;
import com.wipi.feat.service.front.UserFrontService;
import com.wipi.rest.param.UserSignupParam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WipiApplicationTests {

    @Autowired
    UserFrontService userFrontService;

    @Test
    void contextLoads() {
    }

    @Test
    public void signUpTest() {
        // given - 테스트 입력 데이터 설정
        UserSignupParam param = new UserSignupParam();
        param.setUserId("tes12");
        param.setPassword("12345678");
        param.setEmail("tes12@example.com");
        param.setNickName("user123");
        param.setPhoneNumber("01012345678");
        param.setZipAddress("06578");
        param.setMainAddress("서울시 강남구 강남대로 123");
        param.setDetailAddress("101동 202호");
        param.setBirthDt("20000101");
        param.setGender("man"); // 또는 GenderEnum.valueOf("MAN")

        // when - 실제 서비스 메서드 호출
        ResUserSignupDto result = userFrontService.userSignup(param);

        // then - 검증
        System.out.println("회원가입 결과: " + result.getUserId() + ", " + result.getEmail());
        Assertions.assertNotNull(result);
        Assertions.assertEquals("tes12", result.getUserId());
        Assertions.assertEquals("tes12@example.com", result.getEmail());
    }

}
