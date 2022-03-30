package com.stevade.visitationtracker.security;

import com.stevade.visitationtracker.services.BlackListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;
    private final BlackListService blackListService;

    @Autowired
    public JwtRequestFilter(JwtTokenProvider jwtTokenProvider, UserServiceImpl userService, BlackListService blackListService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.blackListService = blackListService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("Attempting to login in filter");
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            log.info("Checking ");
            jwtToken = authorizationHeader.substring(7);
            username = jwtTokenProvider.getUsernameFromToken(jwtToken);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.loadUserByUsername(username);
            log.info("Inside here");

            if (blackListService.getToken(authorizationHeader) == null && Boolean.TRUE.equals(jwtTokenProvider.validateToken(jwtToken, userDetails))) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        log.info("After passing to next chain");
    }
}
