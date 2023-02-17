package hello.hellospring;

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

    @Autowired
    public SpringBeanConfig(DataSource dataSource, EntityManager entityManager) {
        this.dataSource = dataSource;
        this.entityManager = entityManager;
    }


    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcPureMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
        return new EntityManagerJpaMemberRepository(entityManager);
    }
}
