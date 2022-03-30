package com.stevade.visitationtracker.utils;

import com.stevade.visitationtracker.enums.Role;
import com.stevade.visitationtracker.models.Member;
import com.stevade.visitationtracker.repositories.UserRepository;
import com.stevade.visitationtracker.security.PasswordConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class LoadSuperAdmin {

    @Bean
    CommandLineRunner run (UserRepository userRepository){
        return args -> {
            PasswordEncoder encoder = new PasswordConfiguration().encoder();
            String password = encoder.encode("password");
            Member member = new Member(
                    "Admin",
                    "09067543214",
                    "admin@admin.com",
                    password,
                    "edsg",
                    Role.SUPER_ADMIN
            );

            member.setAccountNonLocked(true);
            member.setEnabled(true);
            userRepository.save(member);
            log.info(password);
        };
    }
}
