package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * <p>H2 데이터베이스 테스트 클래스.</p>
 * <p>데이터베이스는 기본적으로 Transaction이라는 개념이 있다.</p>
 * <p>DB데이터를 Insert 쿼리를 한다음 사실 Commit을 해야만 DB에 반영되고</p>
 * <p>AutoCommit일경우 자동으로 반영된다.</p>
 */
@SpringBootTest //스프링 컨테이너와 함께 테스트를 진행한다.
@Transactional //테스트 클래스에 선언한뒤 테스트 시작 전 transaction을 실행하고 테스트 완료 후 항상 RollBack한다.
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
//    @Commit
    void 회원가입() {
        //given - 준비
        Member member = new Member();
        member.setName("spring");
        
        //when - 실행
        Long saveId = memberService.join(member);
        
        //then - 검증
        Member findMember = memberService.findOne(saveId).get();

        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
/*        try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
        //then

    }
}