package com.doccomsa.service;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

//@Log4j
@Service
public class SampleServiceImpl implements SampleService {

	@Override
	public Integer doAdd(String str1, String str2) throws Exception {
		
		// long start = System.currentTimeMillis();
		
		
		/*
		 * 부가기능에 해당하는아래구문을 LogAdvice.java로 분리시킴.
		log.info("doAdd called...");
		log.info(str1);
		log.info(str2);
		*/
		
		Integer result = Integer.parseInt(str1) + Integer.parseInt(str2);
		
		// long end = System.currentTimeMillis();
		
		// end - start
		
		return result; // 핵심기능
	}

}
