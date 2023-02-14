package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepositoryTest {

//    MemberRepository repository = new MemoryMemberRepository();
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 클래스 선언된 메서드가 끝날때마다 한번씩 실행됨
    public void afterEach() { //테스트 종료 후 메모리 DB 저장된 데이터 삭제
        repository.clearStore();
    }
    @Test
    public void save() {
        Member member = new Member();
        member.setName("Spring");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();
//        System.out.println("result = " + (result == member));
//        Assertions.assertEquals(result,member); // Expected 예상값 , Actual 실제값
//        Assertions.assertEquals(result,null);
//        동등 '비교할 예상값'을 1번째인자 '비교대상인 실제값' 2번째인자 - 값이 일치하면 테스트성공 일치하지않으면 failed와함께 Expected(예측기대)값과 actual(비교대상)값을 출력
//        Assertions.assertThat(member).isEqualTo(result);
        assertThat(member).isEqualTo(result); //Alt + Enter로 Assertions를 Static으로 import
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("Spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        Member result = repository.findByName("Spring1").get();
        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("Spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);

    }
}
