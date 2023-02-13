package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();// store는 데이터가 임시저장될 공간.
    // Key는 id의 반환타입 값은 Member의 객체이므로 Member
    // 실무에서는 동시성 문제를 야기하므로 공유되는 변수일때는 concurrentHashMap을 써야하지만 예제이므로 HashMap을 사용한다.
    private static long sequence = 0L; //실무에서는 AtomicLong 등을 사용해야하지만 단순히 long으로 선언
    @Override
    public Member save(Member member) {
        member.setId(++sequence); //id값 증가
        store.put(member.getId(), member);// 'store'에 증가된 id와 member객체 저장 - 건바이건으로 (id : member) 형태로 저장됨
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //Optional로 감싸면 null이어도 반환이된다. - null을 예측해서 씌우거나, null오류 발생시 처리.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))// filter함수를통해 member객체에 있는 name과 저장하려는 name이 일치하는지
                .findAny();
        //findAny는 하나라도 찾는 메소드 (루프가 돌면서 name이 있는지 확인하고 있으면 그 값을 Optional에 감싸서 반환 없으면 null을 Optional에 감싸서 반환한다.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //store에 있는 value는 member이다.
    }
}
