package com.wipi.infra.sms;

import com.wipi.inferfaces.model.dto.req.ReqSmsCoolSendDto;
import com.wipi.support.util.SmsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
@Profile("/consumer")
public class SmsCoolConsumerService {

    private final DefaultMessageService messageService;

    @RabbitListener(queues = "queue.sms.send", concurrency = "1")
    public void sendVerificationCode(ReqSmsCoolSendDto dto) {
        try {
            final String from = SmsUtils.fromNumber;
            final String verificationCode = dto.getVerificationCode();
            final String body = SmsUtils.getContentForVerificationCode(verificationCode);
            final String toPhoneNumber = dto.getToPhoneNumber();

            Message message = new Message();
            message.setFrom(from);
            message.setTo(toPhoneNumber);
            message.setText(body);
            this.messageService.sendOne(new SingleMessageSendingRequest(message));

        } catch (Exception e) {
            log.error("문자 전송 중 오류 발생 : {}", e.getMessage());
        }
    }
}
