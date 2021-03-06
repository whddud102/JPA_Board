package com.zerock.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@ToString(exclude = "replies")
@Entity
@Table(name = "tbl_webboards")
@EqualsAndHashCode(of = "bno")
public class WebBoard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// MySQL에 키 생성 위임
	private Long bno;
	private String title;
	
	private String writer;
	
	private String content;
	
	
	// 엔티티의 생성, 수정 시 자동으로 TimeStamp 값을 기록해주는 어노테이션
	@CreationTimestamp	
	private Timestamp regdate;
	@UpdateTimestamp
	private Timestamp updatedate;
	
	@OneToMany(mappedBy = "board")
	private List<WebReply> replies;
}
