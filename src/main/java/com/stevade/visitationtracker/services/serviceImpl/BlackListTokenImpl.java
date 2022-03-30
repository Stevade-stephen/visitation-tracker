package com.stevade.visitationtracker.services.serviceImpl;

import com.stevade.visitationtracker.models.BlackListedToken;
import com.stevade.visitationtracker.repositories.BlackListedTokenRepository;
import com.stevade.visitationtracker.services.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BlackListTokenImpl implements BlackListService {

    private final BlackListedTokenRepository blackListedTokenRepository;

    @Override
    public BlackListedToken blackListToken(String token) {

        BlackListedToken blackListedToken = new BlackListedToken();
        blackListedToken.setToken(token);

        return blackListedTokenRepository.save(blackListedToken);
    }

    @Override
    public BlackListedToken getToken(String token) {
        return blackListedTokenRepository.findByToken(token).orElse(null);
    }
}
