package com.zerock;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zerock.interceptor.LoginCheckInterceptor;

import lombok.extern.java.Log;

@Configuration
@Log
/**
 * 인터셉터를 등록하는 WebMvcConfigurer 구현 클래스 (= MVC 관련 설정 담당) 
 * @author JongYoung
 *
 */
public class InterceptorConfig implements WebMvcConfigurer {@Override
	
	// 내가 만든 LoginCheckInterceptor를 등록
	public void addInterceptors(InterceptorRegistry registry) {
		// 인터셉터는 등록 할 때, 동작을 감시할 URI를 지정해줘야 함
		registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns("/login");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
	
}
