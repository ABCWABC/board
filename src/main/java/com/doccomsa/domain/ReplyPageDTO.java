package com.doccomsa.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data  // getter, setter, toString, equals 등
@AllArgsConstructor // 모든 필드를 이용하여 생성자메서드
public class ReplyPageDTO {

	private int replyCnt; //댓글 개수
	private List<ReplyVO> list; // 댓글 목록
	
	/*
	public ReplyPageDTO(int replyCnt, List<ReplyVO> list) {
		super();
		this.replyCnt = replyCnt;
		this.list = list;
	}
	*/
	 
}
