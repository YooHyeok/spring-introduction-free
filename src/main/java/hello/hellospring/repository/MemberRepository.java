package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id); // Optional : java 8에 들어있는 기능중 하나. null값 반환시 null을 처리하는 방법
    Optional<Member> findByName(String name);
    List<Member> findAll();

}
