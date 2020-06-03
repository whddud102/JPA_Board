package com.zerock.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.zerock.domain.Member;
import com.zerock.domain.MemberRole;

import lombok.Getter;

/**
 * Member 객체의 데이터를 UserDetails 구현체인 User 클래스로 포장해서 반환해줄 클래스
 * @author JongYoung
 *
 */
@Getter
public class ZerockSecurityUser  extends User{
	
	private static final String ROLE_PREFIX = "ROLE_";
	
	// 인증에 사용할 유저 객체
	private Member member;
	
	/**
	 * Member 객체의 데이터를 User 타입의 객체로 포장해서 반환
	 * @param member 객체를 전달받음
	 * @return user 객체로 포장하여 반환
	 */
	public ZerockSecurityUser(Member member){
		super(member.getUid(), member.getUpw(), makeGrantedAuthority(member.getRoles()));
		this.member = member;
	}

	/**
	 * 유저가 가진 모든 권한을 GrantedAuthority 리스트로 만들어 반환
	 * @param roles 유저가 가진 모든 권한 리스트
	 * @return GrantedAuthority 리스트
	 */
	private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		
		roles.forEach(role -> {
			list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName()));
		});
		
		return  list;
	}
}
