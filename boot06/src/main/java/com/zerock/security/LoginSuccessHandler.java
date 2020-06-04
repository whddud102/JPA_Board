package com.zerock.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import lombok.extern.java.Log;

/**
 * 로그인 성공 후, 기존에 있던 경로로 이동 시켜주는 역할을 담당
 * @author JongYoung
 *
 */
@Log
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{@Override
	
	// 로그인 후, 이동할 URI를 결정
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		
		log.info("--------- determineTargetURL ----------------");
		
		// 세션에 저장해두었던 dest 값을 가져옴
		Object dest = request.getSession().getAttribute("dest");
		
		String nextURL = null;
		
		if(dest != null) {
			// 세션에서 사용이 끝난 dest 속성을 제거
			request.getSession().removeAttribute("dest");
			nextURL = (String)dest;
		} else {
			// 기존 방식으로 동작
			nextURL = super.determineTargetUrl(request, response); 
		}
		
		log.info(" ---- 로그인 후 이동할 경로 : " + nextURL + " ------------------");
		
		return nextURL; 
	}
	
}
