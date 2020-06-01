package com.zerock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.zerock.persistence.CustomCrudRepository;

import lombok.extern.java.Log;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit		// 테스트 결과를 DB에 반영
public class CustomRepositoryTests {
	
	@Autowired
	private CustomCrudRepository repo;
	
	
	@Test
	public void test1() {
		// 1페이지, 10개 데이터 가져 오도록 설정
		Pageable pageable = PageRequest.of(0, 10, Direction.DESC, "bno");
		
		String type = "w";	// 검색 조건 : "작성자 검색"
		String keyword = "user09"; // 검색어 :  "10"
		
		Page<Object[]> result = repo.getCustomPage(type, keyword, pageable);
		
		log.info(result.toString());
		log.info("전체 페이지 수 : " + result.getTotalPages());
		log.info("전체 데이터 수 : " + result.getTotalElements());
		
		result.getContent().forEach(arr -> {
			log.info(Arrays.toString(arr));
		});
	}
}
