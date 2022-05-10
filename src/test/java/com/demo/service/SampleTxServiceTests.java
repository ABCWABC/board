package com.demo.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.doccomsa.service.SampleTxService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class SampleTxServiceTests {

	@Setter(onMethod_ = {@Autowired})
	private SampleTxService service;
	
	@Test
	public void testLong() {
		
		// 60byte
		String str = "012345678901234567890123456789012345678901234567890123456789";
		
		service.addData(str);
	}
}
