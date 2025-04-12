package com.wipi.infra.email;

import com.wipi.inferfaces.model.dto.req.ReqSendEmailDto;
import com.wipi.support.constants.RabbitmqConstants;
import com.wipi.support.util.MailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("consumer")
public class EmailConsumerService {

    private final JavaMailSender mailSender;

    @RabbitListener(queues = RabbitmqConstants.QUEUE_MAIL_SEND, concurrency = "1")
    public void sendMail(ReqSendEmailDto DTO) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(DTO.getToEmail());
            message.setSubject(DTO.getSubject());
            message.setText(DTO.getBody());
            message.setFrom(MailUtils.setFrom);
            mailSender.send(message);
            log.info("Email sent to : {}\n, code : {}",DTO.getToEmail(),DTO.getCode());

        }catch (Exception e) {
            log.error("ERROR Send Mail : {}",e.getMessage());
        }
    }

}
