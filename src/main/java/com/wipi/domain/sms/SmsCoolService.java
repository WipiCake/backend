package com.wipi.domain.sms;

import com.wipi.support.util.SmsUtils;
import com.wipi.support.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SmsCoolService {

    private final SmsCoolRepository smsCoolRepository;

    public void saveSmsVerification(String reqToNumber, String reqVerificationCode) {
        final String reqId = Utils.generate32CharCode();
        final LocalDateTime reqNow = LocalDateTime.now();
        final LocalDateTime reqExpiration = reqNow.plusMinutes(SmsUtils.expirationMinute);

        SmsCool smsCool = new SmsCool();
            smsCool.setId(reqId);
            smsCool.setPhoneNumber(reqToNumber);
            smsCool.setVerificationCode(reqVerificationCode);
            smsCool.setCreateAt(reqNow);
            smsCool.setExpirationTime(reqExpiration);

        smsCoolRepository.save(smsCool);
    }


    public void verifySmsCoolVerificationCode(String reqPhoneNumber, String reqVerificationCode) {
        SmsCool resSmsCool =  smsCoolRepository.findByPhoneNumberAndVerificationCode(reqPhoneNumber,reqVerificationCode).orElseThrow(
                () -> new RuntimeException("휴대폰 인증에 실패하였습니다.")
        );

        if (resSmsCool.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("휴대폰 인증에 실패하였습니다.");
        }

        smsCoolRepository.deleteByPhoneNumber(reqPhoneNumber);
    }


}
