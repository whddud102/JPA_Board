package com.zerock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zerock.repository.MemberRepository;

import lombok.extern.java.Log;

@Service
public class ZerockUsersService implements UserDetailsService{
	
	// MemberRepository와의 연동
	@Autowired
	private MemberRepository repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// 인증에 사용할 userDetails 객체를 만들어서 반환
		return repo.findById(username).filter(m -> m != null)
				.map(m -> new ZerockSecurityUser(m)).get();
	}

}
