package hello.hellospring.domain;

import javax.persistence.*;

/**
 * JPA는 인터페이스만 제공되고 구현체로 Hibernate, EclipseLink등 여러개가 있다.
 * 그중 Java는 Hibernate를 쓴다.
 * ORM - Object Relational Mapping - 객체 관계 테이블 매핑
 */
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 id를 자동으로 증가시켜 생성해주는것을 Identity전략이라고 한다.
    private Long id; //System이 정하는 아이디(데이터구분값 예를들어 시퀀스 등의 자동증가값)
    @Column(name="name") //생략가능하다.
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
