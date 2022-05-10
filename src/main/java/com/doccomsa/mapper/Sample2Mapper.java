package com.doccomsa.mapper;

import org.apache.ibatis.annotations.Insert;

public interface Sample2Mapper {

	@Insert("insert into tbl_sample2(col2) values(#{data})")  // 50byte
	public int insertCol2(String data);
}
