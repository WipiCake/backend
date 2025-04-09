package com.wipi.app;

import com.wipi.domain.user.UserService;
import com.wipi.inferfaces.model.dto.req.ReqSaveEmailVerificationDto;
import com.wipi.inferfaces.model.dto.req.ReqSendEmailDto;
import com.wipi.inferfaces.model.param.ProcessEmailVerification;
import com.wipi.inferfaces.model.param.processIssueTempPassword;
import com.wipi.infra.email.EmailRedisRepository;
import com.wipi.support.constants.RabbitmqConstants;
import com.wipi.support.util.MailUtils;
import com.wipi.support.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailFrontService {

    private final RabbitTemplate rabbitTemplate;
    private final UserService userService;
    private final EmailRedisRepository emailRedisRepository;

    // TODO 이메일 인증코드 발급 프로세스
    public void processEmailVerification(ProcessEmailVerification param) {
        final int expirationTime = 10;
        final String verified = "false";
        final String id = Utils.generate32CharCode();
        final String code = MailUtils.generateCode();

        // TODO 이메일 REDIS 저장
        ReqSaveEmailVerificationDto reqSaveDto= new ReqSaveEmailVerificationDto();
            reqSaveDto.setToEmail(param.getToEmail());
            reqSaveDto.setPurpose(param.getPurpose());
            reqSaveDto.setExpirationTime(expirationTime);
            reqSaveDto.setVerified(verified);
            reqSaveDto.setId(id);
            reqSaveDto.setCode(code);

        rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_MAIL,RabbitmqConstants.ROUTING_MAIL_SAVE, reqSaveDto);

        // TODO 이메일 전송
        ReqSendEmailDto reqSendDto = new ReqSendEmailDto();
            reqSendDto.setToEmail(param.getToEmail());
            reqSendDto.setCode(code);
            reqSendDto.setSubject(MailUtils.getSubjectForVerificationEmail());
            reqSendDto.setBody(MailUtils.getBodyForVerificationEmail(code));

        rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_MAIL,RabbitmqConstants.ROUTING_MAIL_SEND, reqSendDto);

    }

    // TODO 임시 비밀번호 발급 프로세스
    public void processIssueTempPassword(processIssueTempPassword param) {
        final int expirationTime = 10;
        final String verified = "false";
        final String id = Utils.generate32CharCode();
        final String tempPassword = MailUtils.generateTempPassword();

        userService.updatePasswordByEmail(param.getToEmail(), tempPassword);

        ReqSaveEmailVerificationDto reqSaveDto = new ReqSaveEmailVerificationDto();
            reqSaveDto.setToEmail(param.getToEmail());
            reqSaveDto.setPurpose(param.getPurpose());
            reqSaveDto.setExpirationTime(expirationTime);
            reqSaveDto.setVerified(verified);
            reqSaveDto.setId(id);
            reqSaveDto.setCode(tempPassword);

        rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_MAIL,RabbitmqConstants.ROUTING_MAIL_SAVE ,reqSaveDto);

        // TODO 이메일 전송
        ReqSendEmailDto reqSendDto = new ReqSendEmailDto();
        reqSendDto.setToEmail(param.getToEmail());
        reqSendDto.setCode(tempPassword);
        reqSendDto.setSubject(MailUtils.getSubjectForFindPassword());
        reqSendDto.setBody(MailUtils.getBodyForFindPassword(tempPassword));

        rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_MAIL,RabbitmqConstants.ROUTING_MAIL_SEND, reqSendDto);
    }

    // TODO 이메일 인증코드 재전송
    public void reissueVerificationCode(processIssueTempPassword param) {
        final int expirationTime = 10;
        final String verified = "false";
        final String id = Utils.generate32CharCode();
        final String tempPassword = MailUtils.generateTempPassword();




        ReqSaveEmailVerificationDto reqSaveDto = new ReqSaveEmailVerificationDto();
        reqSaveDto.setToEmail(param.getToEmail());
        reqSaveDto.setPurpose(param.getPurpose());
        reqSaveDto.setExpirationTime(expirationTime);
        reqSaveDto.setVerified(verified);
        reqSaveDto.setId(id);
        reqSaveDto.setCode(tempPassword);

        rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_MAIL,RabbitmqConstants.ROUTING_MAIL_SAVE ,reqSaveDto);

        // TODO 이메일 전송
        ReqSendEmailDto reqSendDto = new ReqSendEmailDto();
        reqSendDto.setToEmail(param.getToEmail());
        reqSendDto.setCode(tempPassword);
        reqSendDto.setSubject(MailUtils.getSubjectForFindPassword());
        reqSendDto.setBody(MailUtils.getBodyForFindPassword(tempPassword));

        rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_MAIL,RabbitmqConstants.ROUTING_MAIL_SEND, reqSendDto);
    }





}
