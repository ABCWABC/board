package com.doccomsa.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
@Aspect // 메서드에서 분리된 부가기능에 해당하는 클래스에 선언하는 어노테이션
@Component  // 클래스가 1)컨트롤러 기능(@Controller) 2)서비스(@Service) 3)DAO-DB연동 :@Repository (Mapper인터페이스와 비슷@Mapper) 에 해당하지 않은 일반적인 클래스를 bean으로 생성할 때 사용하는 어노테이션 
public class LogAdvice {
	
	// @Before : Advice target메서드가 호출되기 전에 Advice기능을 수행.
	// execution(실행) : 접근제한자와 특정클래스의 메서드를 지정
	// AspectJ표현식 : "execution(* com.doccomsa.service.SampleService*.*(..))"
	// com.doccomsa.service패키지의 SampleService로 시작하는 파라미터의 개수에 상관없는 클래스의 모든메서드(*)가 호출될때 그 이전에 동작하는 메서드
	@Before("execution(* com.doccomsa.service.SampleService*.*(..))")
	public void logBefore() {
		
		log.info("==================================");
	}
	
	@Before("execution(* com.doccomsa.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		
		log.info("str1: " + str1);
		log.info("str2: " + str2);
	}
	
	// @AfterThrowing : target메서드가 동작될때 예외발생이 되면 호출되어지는 메서드
	@AfterThrowing(pointcut = "execution(* com.doccomsa.service.SampleService*.*(..))", throwing = "exception")
	public void logException(Exception exception) {
		
		log.info("Exception....!!!!");
		log.info("exception: " + exception);
	}
	
	// @Around : target메서드의 동작이전과 이후에 호출되어지는 메서드
	// ProceedingJoinPoint : @Around와 같이 결합해서 파라미터 또는 예외등을 처리한다.
	// 리턴타입이 void가 아니다. 리턴타입은 target메서드의 실행결과를 직접반환하는 형태를 사용
	// @Before 보다 먼저동작되는 특징이 있다.
	@Around("execution(* com.doccomsa.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		
		long start = System.currentTimeMillis();
		
		log.info("Target: " + pjp.getTarget()); // target메서드의 객체
		log.info("Param: " + Arrays.toString(pjp.getArgs())); //target메서듸 파라미터 값
		
		Object result = null;
		
		try {
			result = pjp.proceed(); // target메서드의 코드 실행됨.
		}catch(Throwable e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		log.info("TIME: " + (end - start));
		
		return result;
	}
}
