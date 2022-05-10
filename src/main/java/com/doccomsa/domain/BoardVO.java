package com.doccomsa.domain;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;


//스프링에서는 테이블구조를 갖는 클래스를 생성시 getter, setter, toString() 메서드를 정의해야 한다.
//스프링, mybatis에서 필드를 접근시 getter, setter메서드를 이용하여 접근한다.
@Data  
public class BoardVO {

	// 테이블의 컬럼명참조 : bno, title, content, writer, regdate, updateddate
	// 컬럼명과 필드명을 동일하게 한다.(권장)   물론, 다르게 할수도 있다.
	private Long bno;
	private String title;
	private String content;
	private String writer;  // 테스트
	
	// java.sql 패키지 선택안함. java.util 선택
	private Date regdate;  // 2022-01-05
	private Date updatedDate;  // 2022-01-05
	
	private int replycnt;
	
	
	//추가. 첨부파일 정보.   <input type="hidden" name="attachList[0].uuid">
	// attachList[0] 이부분이 BoardAttachVO클래스에 해당하는 의미이다.
	private List<BoardAttachVO> attachList; 
	
	/*
	public Long getBno() {
		return bno;
	}
	public void setBno(Long bno) {
		this.bno = bno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	// 로그에 확인하기위한 용도(객체의 필드값을 확인목적)
	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", title=" + title + ", content=" + content + ", writer=" + writer + ", regdate="
				+ regdate + ", updatedDate=" + updatedDate + "]";
	}
	
	*/
}
