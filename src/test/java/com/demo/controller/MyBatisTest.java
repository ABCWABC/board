package com.demo.controller;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class MyBatisTest {

	
	@Inject
	private SqlSessionFactory sqlFactory;
	
	@Test
	public void testFactory() {
		log.info(sqlFactory);
		
	}
	
	@Ignore // 클래스 전체테스트시 스킵하고자 할때
	@Test
	public void testSession() throws Exception{
		try(SqlSession session = sqlFactory.openSession()){
			log.info(session);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
