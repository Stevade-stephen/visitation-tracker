package com.stevade.visitationtracker.security;

import com.stevade.visitationtracker.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final PasswordEncoder encoder;
	private final UserDetailsService userService;
	private final JwtRequestFilter jwtRequestFilter;

	public SecurityConfiguration(final UserDetailsService userService,
                                 final JwtRequestFilter jwtRequestFilter) {
		this.encoder = new PasswordConfiguration().encoder();
		this.userService = userService;
		this.jwtRequestFilter = jwtRequestFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST,"/login").permitAll()
				.antMatchers(HttpMethod.POST,"/staff").permitAll()
				.antMatchers("/visitor","/visitors","/visitor/**").hasAuthority(String.valueOf(Role.STAFF))
				.antMatchers("/staff/**").hasAuthority(String.valueOf(Role.SUPER_ADMIN))
				.anyRequest().authenticated()
				.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/login");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(encoder);
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
		auth.userDetailsService(userService);
	}


	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(encoder);
	}

}
