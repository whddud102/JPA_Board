package com.zerock.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "tbl_members")
@EqualsAndHashCode(of = "uid")
@ToString
public class Member {
	
	@Id
	@Column(length = 250)
	private String uid;
	
	private String upw;
	
	private String uname;
	
	@CreationTimestamp
	private LocalDateTime regdate;
	
	@UpdateTimestamp
	private LocalDateTime updatedate;
	
	// 항상 회원 정보와 그에 해당하는 회원 권한 정보를 함께 로딩하도록 즉시 로딩 설정
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "members")
	private List<MemberRole> roles;
	
}
