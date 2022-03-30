package com.stevade.visitationtracker.repositories;

import com.stevade.visitationtracker.models.BlackListedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlackListedTokenRepository extends JpaRepository<BlackListedToken, Long> {
    Optional<BlackListedToken> findByToken(String token);
}
