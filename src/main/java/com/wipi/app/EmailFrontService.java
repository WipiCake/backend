package com.wipi.app;

import com.wipi.domain.email.EmailService;
import com.wipi.domain.email.EmailVerification;
import com.wipi.domain.user.UserService;
import com.wipi.inferfaces.model.dto.req.ReissueEmailVerificationCodeParam;
import com.wipi.inferfaces.model.dto.req.ReqSaveEmailVerificationDto;
import com.wipi.inferfaces.model.dto.req.ReqSendEmailDto;
import com.wipi.inferfaces.model.dto.req.ReqVerifyEmailVerificationCode;
import com.wipi.inferfaces.model.param.ProcessEmailVerificationParam;
import com.wipi.inferfaces.model.param.ProcessIssueTempPasswordParam;
import com.wipi.inferfaces.model.param.VerifyEmailVerificationCodeParam;
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
    private final EmailService emailService;

    //이메일 인증코드 발급 프로세스
    public void processEmailVerification(ProcessEmailVerificationParam param) {
        final String reqEmail = param.getToEmail();
        final String reqPurpose = param.getPurpose();
        final String reqVerificationCode = Utils.generateCode6();
        final String reqSubject = MailUtils.getSubjectForVerificationEmail();
        final String reqBody = MailUtils.getBodyForVerificationEmail(reqVerificationCode);

        // TODO 이메일 REDIS 저장
        ReqSaveEmailVerificationDto reqSaveDto= new ReqSaveEmailVerificationDto(
                reqEmail,reqPurpose,reqVerificationCode
        );

        emailService.saveEmailVerification(reqSaveDto);

        // TODO 이메일 전송 rabbitmq 비동기
        ReqSendEmailDto reqSendDto = new ReqSendEmailDto();
            reqSendDto.setToEmail(reqEmail);
            reqSendDto.setCode(reqVerificationCode);
            reqSendDto.setSubject(reqSubject);
            reqSendDto.setBody(reqBody);

        rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_MAIL,RabbitmqConstants.ROUTING_MAIL_SEND, reqSendDto);
    }

    //임시 비밀번호 발급 프로세스
    public void processIssueTempPassword(ProcessIssueTempPasswordParam param) {
        final String reqEmail = param.getToEmail();
        final String tempPassword = Utils.generateTempPassword8();
        final String reqSubject = MailUtils.getSubjectForFindPassword();
        final String reqBody = MailUtils.getBodyForFindPassword(tempPassword);

        // todo 유저 검증
        userService.findByEmail(reqEmail);

        // todo 비밀번호 업데이트
        userService.updatePasswordByEmail(reqEmail, tempPassword);

        // todo 이메일 전송
        ReqSendEmailDto reqSendDto = new ReqSendEmailDto();
            reqSendDto.setToEmail(reqEmail);
            reqSendDto.setCode(tempPassword);
            reqSendDto.setSubject(reqSubject);
            reqSendDto.setBody(reqBody);

        rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_MAIL,RabbitmqConstants.ROUTING_MAIL_SEND, reqSendDto);
    }

    //이메일 인증코드 검증 프로세스
    public void verifyEmailVerificationCode(VerifyEmailVerificationCodeParam param){
        // todo 이메일 인증코드 검증
        emailService.verifyEmailVerificationCode(new ReqVerifyEmailVerificationCode(
                param.getFromEmail(), param.getVerificationCode())
        );

        // todo 검증완료시 인증코드 삭제
        emailService.deleteEmailVerificationByEmail(param.getFromEmail());
    }

    //이메일 재발급 프로세스
    public void resReissueEmailVerificationCode(ReissueEmailVerificationCodeParam param){
        final String reqToEmail = param.getToEmail();

        // todo 발급시간 2분 지났는지 검증
        emailService.canReissueVerificationCode(reqToEmail);

        final String reqVerificationCode = Utils.generateCode6();
        final String reqPurpose = param.getPurpose();
        final String reqSubject = MailUtils.getSubjectForVerificationEmail();
        final String reqBody = MailUtils.getBodyForVerificationEmail(reqVerificationCode);

        // todo 해당 이메일에 해당하는 인증코드 삭제 -> 해당 이메일 없어도 문제없음
        emailService.deleteEmailVerificationByEmail(reqToEmail);

        // todo redis 인증코드 저장
        emailService.saveEmailVerification(new ReqSaveEmailVerificationDto(
           reqToEmail,reqPurpose,reqVerificationCode
        ));

        // TODO 이메일 전송 rabbitmq 비동기
        ReqSendEmailDto reqSendDto = new ReqSendEmailDto();
            reqSendDto.setToEmail(reqToEmail);
            reqSendDto.setCode(reqVerificationCode);
            reqSendDto.setSubject(reqSubject);
            reqSendDto.setBody(reqBody);

        rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_MAIL,RabbitmqConstants.ROUTING_MAIL_SEND, reqSendDto);
    }


}
