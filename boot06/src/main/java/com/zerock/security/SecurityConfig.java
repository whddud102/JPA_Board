package com.zerock.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.extern.java.Log;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private ZerockUsersService myUserSerivce;
	
	@Override
	// HttpSecurity를 파라미터로 받는 메소드를 오버라이드 해야함!
	protected void configure(HttpSecurity http) throws Exception {
		log.info("security config.........");
		
		http
			.authorizeRequests()
				.antMatchers("/boards/lists").permitAll()
				.antMatchers("/boards/register").hasAnyRole("BASIC", "MANAGER", "ADMIN");
		
		http.formLogin().loginPage("/login").successHandler(new LoginSuccessHandler());	// from 로그인 제공하겠다는 설정 (기본 경로 : /login)
		http.exceptionHandling().accessDeniedPage("/accessDenied");
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/boards/list").invalidateHttpSession(true);
		
		http.rememberMe()
		.key("jy")
		.userDetailsService(myUserSerivce)		// 내가 만든 userDetailsService를 사용하도록 수정
		.tokenRepository(getJDBCRepository())	// 토큰을 저장할 저장소 선택 (= DB에 토큰을 저장하도록 설정) 
		.tokenValiditySeconds(60 * 60 * 24);	// 토큰 유지시간을 24시간으로 지정
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	// Bcrpyt로 암호화하는 password encoder 구현체를 사용, 빈으로 등록
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		// 인증 매니저가 password Encoder를 사용하도록 지정
		log.info("인증 매니저 구성........");
		auth.userDetailsService(myUserSerivce)		// 내가 만든 사용자 정의 userDetailsService를 사용하도록 설정
			.passwordEncoder(passwordEncoder());	// passowrd encoder를 사용하도록 지정
	}
	
	
	/**
	 * remember-me 토큰을 DB에 저장하기 위한 객체를 반환
	 * @return PersistentTokenRepository 구현체
	 */
	private PersistentTokenRepository getJDBCRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(datasource);	// 접근할 DB의 DataSource 설정
		
		return repo;
		
	}
	
	
}
