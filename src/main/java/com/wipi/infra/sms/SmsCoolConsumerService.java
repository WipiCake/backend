package com.wipi.infra.sms;

import com.wipi.config.RabbitmqConfig;
import com.wipi.inferfaces.model.dto.req.ReqSmsCoolSendDto;
import com.wipi.support.constants.RabbitmqConstants;
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
@Profile("consumer")
public class SmsCoolConsumerService {

    private final DefaultMessageService messageService;

    @RabbitListener(queues = RabbitmqConstants.QUEUE_SMS_COOL, concurrency = "1")
    public void sendVerificationCode(ReqSmsCoolSendDto dto) {
        log.info("📨 [SMS Consumer] 메시지 수신: {}", dto);

        try {
            final String from = SmsUtils.fromNumber;
            final String verificationCode = dto.getVerificationCode();
            final String body = SmsUtils.getContentForVerificationCode(verificationCode);
            final String toPhoneNumber = dto.getToPhoneNumber();

            log.debug("📨 [SMS 준비] from={}, to={}, body={}", from, toPhoneNumber, body);

            Message message = new Message();
            message.setFrom(from);
            message.setTo(toPhoneNumber);
            message.setText(body);

            this.messageService.sendOne(new SingleMessageSendingRequest(message));

            log.info("✅ [SMS 전송 성공] to={}, code={}", toPhoneNumber, verificationCode);

        } catch (Exception e) {
            log.error("❌ [SMS 전송 실패] dto={}, 에러={}", dto, e.getMessage(), e);
        }
    }
}
