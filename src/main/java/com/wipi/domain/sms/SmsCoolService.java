package com.wipi.domain.sms;

import com.wipi.support.util.SmsUtils;
import com.wipi.support.util.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsCoolService {

    private final SmsCoolRepository smsCoolRepository;

    @Transactional
    public void saveSmsVerification(String reqToNumber, String reqVerificationCode, String reqPurpose) {
        final String reqId = Utils.generate32CharCode();
        final LocalDateTime reqNow = LocalDateTime.now();
        final LocalDateTime reqExpiration = reqNow.plusMinutes(SmsUtils.expirationMinute);

        SmsCool smsCool = new SmsCool();
            smsCool.setId(reqId);
            smsCool.setPhoneNumber(reqToNumber);
            smsCool.setVerificationCode(reqVerificationCode);
            smsCool.setCreateAt(reqNow);
            smsCool.setExpirationTime(reqExpiration);
            smsCool.setPurpose(reqPurpose);

        smsCoolRepository.save(smsCool);
    }


    @Transactional
    public void verifySmsCoolVerificationCode(String reqPhoneNumber, String reqVerificationCode) {
        SmsCool resSmsCool =  smsCoolRepository.findByPhoneNumberAndVerificationCode(reqPhoneNumber,reqVerificationCode).orElseThrow(
                () -> new RuntimeException("휴대폰 인증에 실패하였습니다.")
        );

        if (resSmsCool.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("인증코드의 기한이 만료되어 휴대폰 인증에 실패하였습니다.");
        }

        smsCoolRepository.deleteAllByPhoneNumber(reqPhoneNumber);
    }


    @Transactional
    public void canReissueSmsCool(String reqPhoneNumber, String reqPurpose) {
        Optional<List<SmsCool>> optionalSmsList = smsCoolRepository.findByPhoneNumberAndPurpose(reqPhoneNumber, reqPurpose);

        if (optionalSmsList.isPresent()) {
            List<SmsCool> resSmsCoolList = optionalSmsList.get();

            log.info("찾은 SmsCool 데이터: {}", Utils.toJson(resSmsCoolList));

            SmsCool latest = resSmsCoolList.stream()
                    .max(Comparator.comparing(SmsCool::getCreateAt))
                    .orElse(null);

            if (latest != null && latest.getCreateAt().plusMinutes(2).isAfter(LocalDateTime.now())) {
                log.warn("2분 이내 재발급 요청 탐지 - phoneNumber: {}", reqPhoneNumber);
                throw new RuntimeException("2분 이내에 재발급이 불가능합니다.");
            }

            log.info("2분 경과 - 기존 인증코드 삭제 시작: phoneNumber={}", reqPhoneNumber);
            smsCoolRepository.deleteAllByPhoneNumber(reqPhoneNumber);
        } else {
            log.info("발견된 SmsCool 데이터 없음 - phoneNumber: {}", reqPhoneNumber);
        }
    }





}
