package com.doccomsa.mapper;

import org.apache.ibatis.annotations.Select;

public interface TestMapper {

	
	@Select("select sysdate from dual") // TestMapper.xml파일에서 작업과 같은 의미.
	public String getTime();
}
