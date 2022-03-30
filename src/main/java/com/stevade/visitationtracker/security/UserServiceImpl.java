package com.stevade.visitationtracker.security;

import com.stevade.visitationtracker.models.Member;
import com.stevade.visitationtracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> user = userRepository.findUserByEmail(email);
            user.map(securedUser -> {
                    securedUser.getAuthorities();
                    return securedUser;
            });
            throw new UsernameNotFoundException("User not found");
    }
}
