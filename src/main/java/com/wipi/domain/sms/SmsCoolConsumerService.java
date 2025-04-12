package com.wipi.domain.sms;

import com.wipi.support.util.SmsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class SmsCoolConsumerService {

    private final DefaultMessageService messageService;

    public void sendVerificationCode(String toPhoneNumber, String code) {
        final String from = SmsUtils.fromNumber;
        final String body = SmsUtils.getContentForVerificationCode(code);

        Message message = new Message();
        message.setFrom(from);
        message.setTo(toPhoneNumber);
        message.setText(body);

        try {
            this.messageService.sendOne(new SingleMessageSendingRequest(message));
        } catch (Exception e) {
            log.error("문자 전송 중 오류 발생 : {}", e.getMessage());
        }
    }
}
