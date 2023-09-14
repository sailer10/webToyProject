package com.mysite.PP;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/***
 * 
 * @author tpdlf
 * @summary 스프링 시큐리티를 위한 클래스. @EnableWebSecurity 애너테이션을 사용하면 내부적으로
 *          SpringSecurityFilterChain이 동작하여 URL 필터가 적용된다.
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/**")).permitAll().and().csrf()
				.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")).and().headers()
				.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
				.and().formLogin().loginPage("/user/login").defaultSuccessUrl("/").and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/")
				.invalidateHttpSession(true);
		return http.build();
	}

	// 비밀번호 저장시 사용하기 위해 bean 으로 지정. userService 에서 사용함
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 스프링 시큐리티의 인증을 담당함
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}