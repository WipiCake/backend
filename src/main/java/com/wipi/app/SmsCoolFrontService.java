package com.wipi.app;

import com.wipi.domain.sms.SmsCoolService;
import com.wipi.inferfaces.model.dto.req.ReqSmsCoolSendDto;
import com.wipi.inferfaces.model.dto.req.VerifySmsCoolVerificationCodeParam;
import com.wipi.inferfaces.model.param.ProcessSendSmsCoolParam;
import com.wipi.support.constants.RabbitmqConstants;
import com.wipi.support.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsCoolFrontService {
    private final SmsCoolService smsCoolService;
    private final RabbitTemplate rabbitTemplate;

    public void sendSmsCoolProcess(ProcessSendSmsCoolParam param) {
        final String reqToPhoneNumber = param.getToPhoneNumber();
        final String reqVerificationCode = Utils.generate32CharCode();

        // todo SMS 인증정보 저장
        smsCoolService.saveSmsVerification(reqToPhoneNumber,reqVerificationCode);
        ReqSmsCoolSendDto reqSmsCoolSendDto = new ReqSmsCoolSendDto();
            reqSmsCoolSendDto.setToPhoneNumber(reqToPhoneNumber);
            reqSmsCoolSendDto.setVerificationCode(reqVerificationCode);

        // todo SMS 전송
        rabbitTemplate.convertAndSend(RabbitmqConstants.EXCHANGE_SMS_COOL,RabbitmqConstants.ROUTING_SMS_SEND, reqSmsCoolSendDto);
    }

    //휴대폰 인증코드 검증 프로세스
    public void verifySmsCoolVerificationCode(VerifySmsCoolVerificationCodeParam param){
        final String reqPhoneNumber = param.getPhoneNumber();
        final String reqVerificationCode = param.getVerificationCode();

        // todo SMS 인증코드 검증 -> 검증 되면 삭제
        smsCoolService.verifySmsCoolVerificationCode(reqPhoneNumber,reqVerificationCode);
    }



}
