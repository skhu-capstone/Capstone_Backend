package com.skhu.skhucapstone.auth.repository;

import com.skhu.skhucapstone.auth.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    Optional<EmailVerification> findByEmail(String email);

    boolean existsByEmail(String email); // 재발송 요청 시에 기존 거가 있는지 확인

    void deleteByEmail(String email); // 재발송 시 인증번호 삭제
}
