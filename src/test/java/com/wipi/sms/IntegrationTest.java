package com.wipi.sms;

import com.wipi.infra.sms.SmsCoolConsumerService;
import com.wipi.support.util.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    SmsCoolConsumerService smsCoolConsumerService;

    @Test
    void sendRealSmsToPhone() {
        String toPhoneNumber = "01094093533";
        String code = MailUtils.generateCode();
        smsCoolConsumerService.sendVerificationCode(toPhoneNumber, code);
    }



}
