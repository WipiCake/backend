package com.wipi.domain.email;

import com.wipi.model.dto.req.ReqSaveEmailVerificationDto;
import com.wipi.model.dto.req.ReqVerifyEmailVerificationCode;
import com.wipi.infra.sms.SmsCoolRedisRepository;
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
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;
    private final SmsCoolRedisRepository smsCoolRedisRepository;

    @Transactional
    public EmailVerification verifyEmailVerificationCode(ReqVerifyEmailVerificationCode DTO) {
        EmailVerification resEmailVerification = emailRepository.findByEmailAndVerificationCode(
                DTO.getEmail(), DTO.getVerificationCode()).orElseThrow(() -> new RuntimeException("이메일 인증에 실패하였습니다."));


        log.info("verifyEmailVerificationCode : {}",Utils.toJson(resEmailVerification));
        if (LocalDateTime.now().isAfter(resEmailVerification.getExpirationTime())) {
            throw new RuntimeException("인증 코드가 만료되었습니다.");
        }

        emailRepository.deleteAllByEmail(DTO.getEmail());

        return resEmailVerification;
    }

    @Transactional
    public EmailVerification saveEmailVerification(ReqSaveEmailVerificationDto DTO) {
        final String reqToEmail = DTO.getToEmail();
        final String reqPurpose = DTO.getPurpose();
        final String reqVerificationCode = DTO.getVerificationCode();
        final String reqId = Utils.generate32CharCode();
        final LocalDateTime reqNow = LocalDateTime.now();
        final LocalDateTime reqExpiration = reqNow.plusMinutes(10);

        EmailVerification emailVerification = new EmailVerification();
            emailVerification.setId(reqId);
            emailVerification.setEmail(reqToEmail);
            emailVerification.setVerificationCode(reqVerificationCode);
            emailVerification.setPurpose(reqPurpose);
            emailVerification.setCreateAt(reqNow);
            emailVerification.setExpirationTime(reqExpiration);

        log.info("Save email verification : {}", Utils.toJson(emailVerification));
        return emailRepository.save(emailVerification);
    }

    @Transactional
    public void canReissueVerificationCode(String toEmail, String purpose) {
        Optional<List<EmailVerification>> resEmailVerification = emailRepository.findByEmailAndPurpose(toEmail, purpose);

        if(resEmailVerification.isPresent()){
            List<EmailVerification> resEmailVerificationList = resEmailVerification.get();

            log.info("resEmailVerification : {}", Utils.toJson(resEmailVerificationList));
            EmailVerification latest = resEmailVerificationList.stream()
                    .max(Comparator.comparing(EmailVerification::getCreateAt))
                    .orElse(null);

            if(latest != null && latest.getCreateAt().plusMinutes(2).isAfter(LocalDateTime.now())){
                log.warn("2분 이내에는 재발급이 불가능합니다.");
                throw new RuntimeException("2분 이내에 재발급이 불가능합니다.");
            }

            emailRepository.deleteAllByEmail(toEmail);
            log.info("Email 정상적으로 삭제 : {}",Utils.toJson(emailRepository.findByEmail(toEmail)
                    .orElse(null)));
        }

    }



}
