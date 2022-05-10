package com.demo.controller;

import static org.junit.Assert.*;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DataSourceTest {

	
	// DataSource ? DB서버와의 연결
	@Inject
	private DataSource ds;
	
	@Test
	public void testConnection() throws Exception{
		// 커넥션객체생성과 더불어 사용이 끝나면 반납하는 구문
		try(Connection con = ds.getConnection()){
			log.info(con);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
