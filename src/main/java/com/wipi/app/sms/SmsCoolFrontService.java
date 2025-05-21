package com.wipi.app.sms;

import com.wipi.domain.jwt.JwtService;
import com.wipi.domain.sms.SmsCoolService;
import com.wipi.domain.user.User;
import com.wipi.domain.user.UserService;
import com.wipi.model.dto.req.ReqSmsCoolSendDto;
import com.wipi.model.dto.res.ResIssueJwtDto;
import com.wipi.model.param.VerifyAuthBySmsCoolParam;
import com.wipi.model.param.VerifyFindIdBySmsCoolParam;
import com.wipi.model.param.VerifyResetPwByCoolSmsParam;
import com.wipi.model.param.ProcessSendSmsCoolParam;
import com.wipi.support.constants.RabbitmqConstants;
import com.wipi.support.util.Utils;
import com.wipi.support.util.ValidUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsCoolFrontService {
    private final SmsCoolService smsCoolService;
    private final RabbitTemplate rabbitTemplate;
    private final UserService userService;
    private final JwtService jwtService;

    public void sendSmsCoolProcess(ProcessSendSmsCoolParam param) {
        ValidUtils.validVerifyPurpose(param.getPurpose(), List.of("FIND-PW", "FIND-ID", "AUTH", "TEST"));

        final String reqToPhoneNumber = param.getToPhoneNumber();
        final String reqPurpose = param.getPurpose();
        final String reqVerificationCode = Utils.generateCode6();

        smsCoolService.canReissueSmsCool(reqToPhoneNumber, reqPurpose);

        // todo SMS 인증정보 저장
        smsCoolService.saveSmsVerification(reqToPhoneNumber,reqVerificationCode,reqPurpose);
        ReqSmsCoolSendDto reqSmsCoolSendDto = new ReqSmsCoolSendDto();
            reqSmsCoolSendDto.setToPhoneNumber(reqToPhoneNumber);
            reqSmsCoolSendDto.setVerificationCode(reqVerificationCode);

        // todo SMS 전송
        rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_SMS_COOL,RabbitmqConstants.ROUTING_SMS_SEND, reqSmsCoolSendDto);
    }

    //휴대폰 인증코드 검증, 아이디 찾기
    public String verifyFindId(VerifyFindIdBySmsCoolParam param){
        ValidUtils.validVerifyPurpose(param.getPurpose(), List.of("FIND-ID","TEST"));

        final String reqPhoneNumber = param.getPhoneNumber();
        final String reqVerificationCode = param.getVerificationCode();

        smsCoolService.verifySmsCoolVerificationCode(reqPhoneNumber,reqVerificationCode);
        return userService.findUserByPhoneNumber(reqPhoneNumber).getUserId();
    }


    //휴대폰 인증코드 검증, 비밀번호 찾기
    public Map<String,Object> verifyResetPw(VerifyResetPwByCoolSmsParam param){
        ValidUtils.validVerifyPurpose(param.getPurpose(), List.of("FIND-PW","TEST"));

        final String reqPhoneNumber = param.getPhoneNumber();
        final String reqVerificationCode = param.getVerificationCode();

        smsCoolService.verifySmsCoolVerificationCode(reqPhoneNumber,reqVerificationCode);

        User user = userService.findUserByPhoneNumber(param.getPhoneNumber());
        ResIssueJwtDto resIssueJwtDto = jwtService.issueJwtAuth(user.getUserId(),user.getRole());
        log.info("resIssueJwtDto:{}", Utils.toJson(resIssueJwtDto));

        return Map.of(
                "accessToken", resIssueJwtDto.getAccessToken(),
                "refreshCookie", resIssueJwtDto.getCookie()
        );
    }

    public void verifyAuth(VerifyAuthBySmsCoolParam param) {
        ValidUtils.validVerifyPurpose(param.getPurpose(), List.of("AUTH","TEST"));
        final String reqPhoneNumber = param.getPhoneNumber();
        final String reqVerificationCode = param.getVerificationCode();

        smsCoolService.verifySmsCoolVerificationCode(reqPhoneNumber,reqVerificationCode);
    }

}
