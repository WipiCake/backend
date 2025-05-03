package com.wipi.app.mail;

import com.wipi.domain.email.EmailService;
import com.wipi.domain.jwt.JwtService;
import com.wipi.domain.user.User;
import com.wipi.domain.user.UserService;
import com.wipi.inferfaces.model.dto.res.ResIssueJwtDto;
import com.wipi.inferfaces.model.param.VerifyFindIdByEmailParam;
import com.wipi.inferfaces.model.dto.req.ReqSaveEmailVerificationDto;
import com.wipi.inferfaces.model.dto.req.ReqSendEmailDto;
import com.wipi.inferfaces.model.dto.req.ReqVerifyEmailVerificationCode;
import com.wipi.inferfaces.model.param.ProcessEmailVerificationParam;
import com.wipi.inferfaces.model.param.VerifyRestPwByEmailParam;
import com.wipi.support.constants.RabbitmqConstants;
import com.wipi.support.util.MailUtils;
import com.wipi.support.util.Utils;
import com.wipi.support.util.ValidUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailFrontService {

    private final RabbitTemplate rabbitTemplate;
    private final UserService userService;
    private final EmailService emailService;
    private final JwtService jwtService;

    //이메일 인증코드 발급 프로세스
    @Transactional
    public void processEmailVerification(ProcessEmailVerificationParam param) {
        ValidUtils.validVerifyPurpose(param.getPurpose(), List.of("FIND-PW", "FIND-ID", "AUTH", "TEST"));

        final String reqEmail = param.getToEmail();
        emailService.canReissueVerificationCode(reqEmail);

        final String reqVerificationCode = Utils.generateCode6();
        final String reqSubject = MailUtils.getSubjectForVerificationEmail();
        final String reqBody = MailUtils.getBodyForVerificationEmail(reqVerificationCode);

        ReqSaveEmailVerificationDto reqSaveDto= new ReqSaveEmailVerificationDto(
                reqEmail,param.getPurpose(),reqVerificationCode
        );

        emailService.saveEmailVerification(reqSaveDto);

        ReqSendEmailDto reqSendDto = new ReqSendEmailDto();
            reqSendDto.setToEmail(reqEmail);
            reqSendDto.setCode(reqVerificationCode);
            reqSendDto.setSubject(reqSubject);
            reqSendDto.setBody(reqBody);

        rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_MAIL,RabbitmqConstants.ROUTING_MAIL_SEND, reqSendDto);
    }


    //이메일 인증코드 검증, 아이디 찾기
    public String verifyFindId(VerifyFindIdByEmailParam param){
       ValidUtils.validVerifyPurpose(param.getPurpose(), List.of("FIND-ID","TEST"));

       emailService.verifyEmailVerificationCode(new ReqVerifyEmailVerificationCode(
                param.getFromEmail(), param.getVerificationCode()
        ));

        return userService.findByEmail(param.getFromEmail()).getUserId();
    }

    //이메일 인증코드 검증, 비밀번호 찾기
    public void verifyResetPw(VerifyRestPwByEmailParam param){
        ValidUtils.validVerifyPurpose(param.getPurpose(), List.of("FIND-PW","TEST"));

        emailService.verifyEmailVerificationCode(new ReqVerifyEmailVerificationCode(
                param.getFromEmail(), param.getVerificationCode())
        );
        User user = userService.findByEmail(param.getFromEmail());

        ResIssueJwtDto resIssueJwtDto = jwtService.issueJwtAuth(user.getUserId(),user.getRole());
        log.info("resIssueJwtDto:{}", resIssueJwtDto);
    }


}
