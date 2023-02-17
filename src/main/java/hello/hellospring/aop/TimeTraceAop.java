package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // Aop로 사용할것임을 명시하는 어노테이션
@Component // 특별한 기능은 주로 @Bean으로 등록하는 경우가 더 많다.
public class TimeTraceAop {
    
    @Around("execution(* hello.hellospring..*(..))") //적용할 타게팅 어노테이션 키지 하위의 모든것에 적용
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());
        try {
            Object proceed = joinPoint.proceed();
            return proceed;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
