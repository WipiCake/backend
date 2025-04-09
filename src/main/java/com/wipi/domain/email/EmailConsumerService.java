package com.wipi.domain.email;

import com.wipi.inferfaces.model.dto.req.ReqSaveEmailVerificationDto;
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
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("consumer")
public class EmailConsumerService {

    private final EmailRepository emailRepository;
    private final JavaMailSender mailSender;

    @RabbitListener(queues = RabbitmqConstants.QUEUE_MAIL_SAVE, concurrency = "1")
    public void saveEmailVerification(ReqSaveEmailVerificationDto DTO){
        try {
            EmailVerification emailVerification = new EmailVerification();
            emailVerification.setId(DTO.getId());
            emailVerification.setEmail(DTO.getToEmail());
            emailVerification.setVerificationCode(DTO.getCode());
            emailVerification.setPurpose(DTO.getPurpose());
            emailVerification.setVerified(DTO.getVerified());
            emailVerification.setCreateAt(LocalDateTime.now());
            emailVerification.setExpirationTime(LocalDateTime.now().plusMinutes(DTO.getExpirationTime()));

            emailRepository.save(emailVerification);
        }catch (Exception e){
            log.error("Error Save Mail : {}",e.getMessage());
        }

    }

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
