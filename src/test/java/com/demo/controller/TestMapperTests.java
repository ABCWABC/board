package com.demo.controller;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.doccomsa.mapper.TestMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class TestMapperTests {

	@Setter(onMethod_ = @Autowired)
	private TestMapper mapper;
	
	@Test
	public void getTime() {
		
		log.info(mapper.getTime());
		
	}

}
