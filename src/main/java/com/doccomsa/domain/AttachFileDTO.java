package com.doccomsa.domain;

import lombok.Data;

// 파일업로드 연습용
@Data
public class AttachFileDTO {

	private String uuid; // 중복되지 않은 문자열
	private String uploadPath; // 날짜를 이용한 업로드 폴더명
	private String fileName; // 클라이언트에서 보낸 파일명
	private boolean image; // 이미지 파일구분.   true면 FileType컬럼에는 1, false면 FileType컬럼에는 0값으로 저장된다.
}
