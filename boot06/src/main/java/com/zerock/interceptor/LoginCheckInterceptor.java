package com.zerock.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.java.Log;

@Log
public class LoginCheckInterceptor extends HandlerInterceptorAdapter{@Override
	
	// 컨트롤러 호출 전에 수행되는 메서드
	// 요청 객체에 dest 속성 값이 존재하면, HttpSession에 dest 값을 저장
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

	log.info("---------- preHandl() -----------");
	
	// 요청 객체에서 dest 파라미터의 값을 가져옴
	String dest = request.getParameter("dest");
	
	
	if(dest != null) {	
		// dest 파라미터 존재 할 경우, 즉 되돌아갈 조회 화면이 있는 경우에는 HttpSession에 dest 값으로 돌아갈 경로를 지정
		request.getSession().setAttribute("dest", dest);
	} else {
		// 돌아갈 게시글 조회 경로가 없는 경우, 게시글 목록 화면으로 이동하도록 설정
		request.getSession().setAttribute("dest", "/boards/list");
	}
	
	return super.preHandle(request, response, handler);
	}
	
}
