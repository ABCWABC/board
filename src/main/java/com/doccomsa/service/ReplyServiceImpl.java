package com.doccomsa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doccomsa.domain.Criteria;
import com.doccomsa.domain.ReplyPageDTO;
import com.doccomsa.domain.ReplyVO;
import com.doccomsa.mapper.BoardMapper;
import com.doccomsa.mapper.ReplyMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService {

	private ReplyMapper mapper;
	
	private BoardMapper boardMapper;

	//댓글등록 - 진행중 오류나면 전체 롤백함
	@Transactional
	@Override
	public int register(ReplyVO vo) {
		boardMapper.updateReplyCnt(vo.getBno(), 1);                                      //특정 bno에 댓글수 +1 update하기 > (댓글등록(+1) , 댓글삭제(-1))
		return mapper.insert(vo);                                                        //ReplyVO값 insert하고 1반환
	}

	//특정 bno의 특정 페이지의 ReplyVO 가져오기
	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		return mapper.getListWithPaging(cri, bno);
	}

	//특정 bno의 ReplyVO(댓글개수, 댓글목록) 가져오기
	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
		return new ReplyPageDTO(mapper.getCountByBno(bno), mapper.getListWithPaging(cri, bno));     //특정 bno의 rno수 가져오기, 특정 bno의 특정 페이지의 ReplyVO 가져오기
	}

	//특정 rno의 댓글내용, 수정일 update하기
	@Override
	public int modifyReply(ReplyVO vo) {
		return mapper.update(vo);
	}

	
	//댓글삭제 - 진행중 오류나면 전체 롤백함
	@Transactional
	@Override
	public int deleteReply(Long bno, Long rno) {
		boardMapper.updateReplyCnt(bno, -1);                        //특정 bno에 댓글수 -1 update하기 > (댓글등록(+1) , 댓글삭제(-1))
		return mapper.delete(rno);                                  //ReplyVO값 delete하고 1반환
	}
}
