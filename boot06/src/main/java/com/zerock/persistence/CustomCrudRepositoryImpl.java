package com.zerock.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.zerock.domain.QWebBoard;
import com.zerock.domain.QWebReply;
import com.zerock.domain.WebBoard;

import lombok.extern.java.Log;

/**
 * 엔티티의 Repository를 구현하는 클래스를 생성
 * - 주의사항 -
 * 1. 구현 클래스의 이름은 반드시 'Repository 이름 + Impl'
 * 2. 부모 클래스로 QuerydslRepositorySupport를 지정
 * 3. 반드시 생성자 구현
 * 4. 사용자 정의 인터페이스를 구현할 것!
 * @author JongYoung
 *
 */
@Log
public class CustomCrudRepositoryImpl extends QuerydslRepositorySupport implements CustomWebBoard{
	
	public CustomCrudRepositoryImpl() {
		super(WebBoard.class);
	}

	// 사용자 정의 인터페이스의 메서드
	// 동적으로 JPQL을 생성하여 쿼리문을 수행할 것
	@Override
	public Page<Object[]> getCustomPage(String type, String keyword, Pageable page) {
		log.info("===============================");
		log.info("검색 조건 : " + type );
		log.info("검색 키워드 : " + keyword);
		log.info("Page : " + page);
		log.info("================================");
		
		// Querydsl을 이용해서 동적으로 JPQL을 생성하려면 Q도메인을 이용해야 함
		QWebBoard board = QWebBoard.webBoard;
		QWebReply reply = QWebReply.webReply;
		
		// Q 도메인 클래스로 부터 동적으로 생성할 JPQL 객체를 얻어 온다.
		JPQLQuery<WebBoard> query = from(board);
		
		// query에 실제로 쿼리를 지정하여 JPQL<Tuple> 객체를 얻어온다
		// 쿼리에 select문 지정
		JPQLQuery<Tuple> tuple = query.select(board.bno, board.title, reply.count(),  board.writer, board.regdate);
		
		// 댓글 엔티티와 left outer 조인 수행
		tuple.leftJoin(reply);
		// left outer 조인 시에, on 조건 추가 ( board.bno == r.board.bno)
		tuple.on(board.bno.eq(reply.board.bno));
		// 인덱스를 이용하여 정렬하기 위해 bno > 0 조건 추가
		tuple.where(board.bno.gt(0L));
		
		// 검색 조건 처리
		if(type != null) {
			switch (type.toLowerCase()) {
			case "t":
				tuple.where(board.title.like("%" + keyword + "%"));
				break;
			case "c":
				tuple.where(board.content.like("%" + keyword + "%"));
				break;
			case "w":
				tuple.where(board.writer.like("%" + keyword + "%"));
				break;
			}
		}
		
		
		// bno 값으로 그룹핑
		tuple.groupBy(board.bno);
		
		// order by bno desc 조건 추가
		tuple.orderBy(board.bno.desc());
		
		// 페이지 정보로부터 offset 설정
		tuple.offset(page.getOffset());
				
		// 페이지 정보로부터 limit(=페이지 당 데이터 수) 설정
		tuple.limit(page.getPageSize());
		
		// 실제로 DB에서 조건에 맞는 데이터를 fetch 해 옴
		List<Tuple> list = tuple.fetch();
		
		List<Object[]> resultList = new ArrayList<Object[]>();
		
		list.forEach(t -> {
			// 결과 tuple을 object[]로 바꿔서 결과 리스트에 저장
			// 여러 칼럼의 값을 가져올 때는 objet[]을 반환 함
			resultList.add(t.toArray());
		});
		
		// 전체 데이터 개수를 가져 옴
		long total = tuple.fetchCount();
		
		// PageIml 객체를 이용해서 resultList를 page<Object[]> 로 변환
		return new PageImpl<Object[]>(resultList, page, total);
	}
}
