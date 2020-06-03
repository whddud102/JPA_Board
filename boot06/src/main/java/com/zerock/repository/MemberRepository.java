package com.zerock.repository;

import org.springframework.data.repository.CrudRepository;

import com.zerock.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String>{

}
