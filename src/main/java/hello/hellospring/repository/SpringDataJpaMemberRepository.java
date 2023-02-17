package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * MemberRepository의 추상메소드를 다시 추상메소드로 오버라이딩하면서 해당 메소드를 JpaRepository를 통해 SpringDataJPA로 구현
 */
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long> , MemberRepository{

    //JPQL : select m from Member m where m.name=:name
    @Override
    Optional<Member> findByName(String name);
//    Optional<Member> findByMemberMNoAndName(Long id, String name);
}
