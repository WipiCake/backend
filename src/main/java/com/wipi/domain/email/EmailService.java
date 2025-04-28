package com.wipi.domain.email;

import com.wipi.inferfaces.model.dto.req.ReqSaveEmailVerificationDto;
import com.wipi.inferfaces.model.dto.req.ReqVerifyEmailVerificationCode;
import com.wipi.support.util.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;

    @Transactional
    public EmailVerification verifyEmailVerificationCode(ReqVerifyEmailVerificationCode DTO) {
        EmailVerification resEmailVerification = emailRepository.findByEmailAndVerificationCode(
                DTO.getEmail(), DTO.getVerificationCode()).orElseThrow(() -> new RuntimeException("이메일 인증에 실패하였습니다."));


        log.info("verifyEmailVerificationCode : {}",Utils.toJson(resEmailVerification));
        if (LocalDateTime.now().isAfter(resEmailVerification.getExpirationTime())) {
            throw new RuntimeException("인증 코드가 만료되었습니다.");
        }

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

    public void deleteEmailVerificationByEmail(String email) {
        emailRepository.deleteByEmail(email);
    }

    @Transactional
    public void canReissueVerificationCode(String toEmail) {
        EmailVerification resEmailVerification = emailRepository.findByEmail(toEmail)
                .orElse(null);

        log.info("resEmailVerification : {}", Utils.toJson(resEmailVerification));

        if (resEmailVerification != null) {
            final LocalDateTime createdAt = resEmailVerification.getCreateAt();

            if (createdAt.plusMinutes(2).isAfter(LocalDateTime.now())) {
                log.info("2분 이내에는 재발급이 불가능합니다.");
                throw new RuntimeException("2분 이내에는 재발급이 불가능합니다.");
            }

            emailRepository.deleteAllByEmail(toEmail);
            log.info("Email 정상적으로 삭제 : {}",Utils.toJson(emailRepository.findByEmail(toEmail)
                    .orElse(null)));
        }
    }



}
