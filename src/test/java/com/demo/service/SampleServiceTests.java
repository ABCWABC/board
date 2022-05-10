package com.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.doccomsa.service.SampleService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


// 테스트클래스의 목적 : SampleServiceImpl클래스가 bean으로 등록되어 사용이 가능한지 여부를 확인한다.

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class SampleServiceTests {

	@Setter(onMethod_ = @Autowired)  // onMethod_ : 특히하게 jdk1.8에서는 _ (underline)이사용됨
	private SampleService service;
	
	@Test
	public void testClass() {
		log.info(service); // Object클래스의 toString()메서드를 상속받아서 그대로 사용하고 있다.
		log.info(service.getClass().getName()); // service객체의 실제 클래스명 full name으로 확인
	}
	
	@Test
	public void testAdd() throws Exception{
		
		log.info(service.doAdd("123", "456"));
	}
	
	@Test
	public void testAddError() throws Exception{
		
		log.info(service.doAdd("123", "ABC"));
	}
	

}
