package com.zerock.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zerock.domain.Member;
import com.zerock.repository.MemberRepository;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/member/")
public class MemberController {
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberRepository repo;
	
	// join.html로 이동
	@GetMapping("/join")
	public void join() {}
	
	@PostMapping("/join")
	public String joinPost(@ModelAttribute("member") Member member) {
		log.info("---- 회원 가입 요청 ------");
		log.info("Member : " + member);

		log.info("비밀번호를 암호화 합니다......");
		String encryptPw = passwordEncoder.encode(member.getUpw());
		log.info("암호화한 비밀번호 : " + encryptPw);
		
		member.setUpw(encryptPw);		// 암호화 된 비밀번호로 설정
		
		repo.save(member);				// DB에 저장
		
		return "/member/joinResult";
	};
	
}
