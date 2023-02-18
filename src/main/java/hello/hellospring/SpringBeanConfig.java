package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.EntityManagerJpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringBeanConfig {

    private final DataSource dataSource;
    private final EntityManager entityManager;
    private final MemberRepository memberRepository;

    @Autowired
    public SpringBeanConfig(DataSource dataSource, EntityManager entityManager, MemberRepository memberRepository) {
        this.dataSource = dataSource;
        this.entityManager = entityManager;
        this.memberRepository = memberRepository;
    }


    @Bean
    public MemberService memberService() {
//        return new MemberService(memberRepository()); // 임시DB, 순수JDBC, JDBCTemplate, EntityManager
        return new MemberService(memberRepository); // 인터페이스 주입 SpringDataJpaMemberRepository가 구현하면서 Spring Data JPA의 기능을 사용할수 있게 된다.
    }

    /**
     * 임시DB, 순수JDBC, JDBCTemplate, EntityManager 주입
     * @return
     */
    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcPureMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
        return new EntityManagerJpaMemberRepository(entityManager);

    }

    @Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }
}
