package com.doccomsa.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {
	
	// rno, bno, reply, replyer, replydate, updatedate
	private Long rno;  // 참조형으로 사용권장.
	private Long bno;  
	
	private String reply;
	private String replyer;
	private Date replydate;
	private Date updatedate;
}
