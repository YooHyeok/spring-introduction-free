package hello.hellospring.repository;


import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * findById나 Save등 단건을 처리하는 기능은 제공하지만
 * PK기반이 아닌 나머지는 JPQL을 활용하여 작성해야한다.
 */
public class EntityManagerJpaMemberRepository implements MemberRepository {

    private final EntityManager em;// JPA는 EntityManager에의해 모든것이 동작된다.
    public EntityManagerJpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); //persist는 영속하다 영구적으로 저장하겠다는 뜻
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
//        return Optional.ofNullable(em.find(Member.class,id)); // 한줄로 처리
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member); //Optional로 반환하기
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> resultList = em.createQuery("select m from Member m where m.name=:name", Member.class)
                .setParameter("name", name).getResultList();
        Optional<Member> result = resultList.stream().findAny();
        return result;
//        return em.createQuery("select m from Member m where m.name=:name", Member.class)
//                .setParameter("name", name)
//                .getResultList().stream().findAny(); //한줄로 처리

    }

    @Override
    public List<Member> findAll() {
//        return em.createQuery("select m from Member m", Member.class).getResultList(); //한줄로처리
        TypedQuery<Member> tq = em.createQuery("select m from Member m", Member.class); //select와 from사이의 alias는 엔티티 자체를 조회한다는 의미.
        // TypedQuery : JPQL을 실행시키기 위해 만드는 쿼리 객체 제너릭 타입을 명확히 지정할 수 없으면 Query객체를 사용한다.
        List<Member> resultList = tq.getResultList();
        return resultList;
    }
}
