package com.doccomsa.mapper;

import org.apache.ibatis.annotations.Insert;

public interface Sample1Mapper {

	@Insert("insert into tbl_sample1(col1) values (#{data})") // 500byte
	public int insertCol1(String data);
}
