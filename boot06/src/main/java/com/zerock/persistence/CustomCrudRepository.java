package com.zerock.persistence;

import org.springframework.data.repository.CrudRepository;

import com.zerock.domain.WebBoard;

// 엔티티의 Repository 인터페이스를 설계할 떄, 사용자 정의 인터페이스도 상속하도록 설계
public interface CustomCrudRepository extends CustomWebBoard, CrudRepository<WebBoard, Long>{

}
