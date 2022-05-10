package com.doccomsa.service;

import java.util.List;

import com.doccomsa.domain.Criteria;
import com.doccomsa.domain.ReplyPageDTO;
import com.doccomsa.domain.ReplyVO;

public interface ReplyService {

	public int register(ReplyVO vo);
	
	public List<ReplyVO> getList(Criteria cri, Long bno);
	
	public ReplyPageDTO getListPage(Criteria cri, Long bno);
	
	public int modifyReply(ReplyVO vo);
	
	public int deleteReply(Long bno, Long rno);
}
