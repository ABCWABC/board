package com.doccomsa.domain;

import lombok.Data;

@Data
public class BoardAttachVO {

	// 첨부파일 테이블 : uuid, uploadpath, filename, filetype, bno
	
	private String uuid;
	private String uploadPath;
	private String fileName;
	private boolean fileType;  // filetype 컬럼의 데이타타입 char(1). 값은 '1' or '0'
	
	private Long bno;
}
