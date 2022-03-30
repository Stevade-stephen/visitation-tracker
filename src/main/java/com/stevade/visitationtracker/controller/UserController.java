package com.stevade.visitationtracker.controller;

import com.stevade.visitationtracker.dtos.LoginDto;
import com.stevade.visitationtracker.repositories.UserRepository;
import com.stevade.visitationtracker.security.JwtTokenProvider;
import com.stevade.visitationtracker.security.UserServiceImpl;
import com.stevade.visitationtracker.services.BlackListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BlackListService blackListService;

    public UserController(AuthenticationManager authenticationManager, UserServiceImpl userService, JwtTokenProvider jwtTokenProvider, BlackListService blackListService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.blackListService = blackListService;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) throws Exception {
        log.info("Attempting to login");
        try {
            log.info(loginDto.getEmail());
            log.info(loginDto.getPassword());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userService
                .loadUserByUsername(loginDto.getEmail());

        final String jwt = jwtTokenProvider.generateToken(userDetails);

        return new ResponseEntity<>(jwt, HttpStatus.OK);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {

        final String token = httpServletRequest.getHeader("Authorization");

        blackListService.blackListToken(token);

        return new ResponseEntity<>("Logout Successful", HttpStatus.OK);

    }
}
