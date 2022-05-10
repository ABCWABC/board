package com.demo.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.log4j.Log4j;

@Log4j
@WebAppConfiguration // 컨트롤러 주소테스트시 웹환경 성격이 존재해야 함
@RunWith(SpringJUnit4ClassRunner.class) // applicationContext객체
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class TestControllerTests {

	
	@Inject
	private WebApplicationContext wac; // applicationContext객체 주입
	
	private MockMvc mockMvc;  // 톰캣서버가 시작해서 서블릿컨테이너 환경을 제공한다.  톰캣서버를 구동하지않고 서블릿컨테이너 기능을 MockMvc객체가 그 기능을 제공
	
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void testDoA() throws Exception{
		log.info("/test/doA");
		mockMvc.perform(MockMvcRequestBuilders.get("/test/doA"))   // perform() ? 컨트롤러의 주소요청을 담당.
				.andDo(print());// 요청과응답 전체메시지 확인하기.
				
	}

}
