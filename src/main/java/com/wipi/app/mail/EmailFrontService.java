package com.wipi.app.mail;

import com.wipi.domain.email.EmailService;
import com.wipi.domain.jwt.JwtService;
import com.wipi.domain.user.User;
import com.wipi.domain.user.UserService;
import com.wipi.model.dto.res.ResIssueJwtDto;
import com.wipi.model.param.VerifyFindIdByEmailParam;
import com.wipi.model.dto.req.ReqSaveEmailVerificationDto;
import com.wipi.model.dto.req.ReqSendEmailDto;
import com.wipi.model.dto.req.ReqVerifyEmailVerificationCode;
import com.wipi.model.param.ProcessEmailVerificationParam;
import com.wipi.model.param.VerifyRestPwByEmailParam;
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
import java.util.Map;

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
        final String reqPurpose = param.getPurpose();
        emailService.canReissueVerificationCode(reqEmail, reqPurpose);

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
    public Map<String,Object> verifyResetPw(VerifyRestPwByEmailParam param){
        ValidUtils.validVerifyPurpose(param.getPurpose(), List.of("FIND-PW","TEST"));

        emailService.verifyEmailVerificationCode(new ReqVerifyEmailVerificationCode(
                param.getFromEmail(), param.getVerificationCode())
        );
        User user = userService.findByEmail(param.getFromEmail());

        ResIssueJwtDto resIssueJwtDto = jwtService.issueJwtAuth(user.getUserId(),user.getRole());
        log.info("resIssueJwtDto:{}", resIssueJwtDto);
        return Map.of(
                "accessToken", resIssueJwtDto.getAccessToken(),
                "refreshCookie", resIssueJwtDto.getCookie()
        );
    }


}
