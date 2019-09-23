package com.streambuf.subtitle.user.repository;

import com.streambuf.subtitle.user.model.po.InvitationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface InvitationCodeRepo extends JpaRepository<InvitationCode, Long> {
    Optional<InvitationCode> findByCodeAndStatus(String code, Integer status);
}
