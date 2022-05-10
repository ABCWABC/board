package com.doccomsa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doccomsa.domain.BoardAttachVO;
import com.doccomsa.domain.BoardVO;
import com.doccomsa.domain.Criteria;
import com.doccomsa.mapper.BoardAttachMapper;
import com.doccomsa.mapper.BoardMapper;
import com.doccomsa.mapper.ReplyMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class BoardServiceImpl implements BoardService {

	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper replyMapper;
	
	//BoardVO 와 BoardAttachVO 값 insert하기 - 진행중 오류나면 전체 롤백함
	@Transactional
	@Override
	public void register(BoardVO board) {
		
		mapper.insertSelectKey(board);                                                 //BoardVO값 insert하기
		
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {       //아래 getAttachList()메소드 수행 - findByBno : 특정 bno의 BoardAttachVO값 가져오기
			return;
		}
		board.getAttachList().forEach(attach -> {                                      //각 BoardAttachVO값에 해당 BoardVO의 bno값 set하고 BoardAttachVO값 insert하기 
			attach.setBno(board.getBno());                                             //(이때, 첨부파일테이블 bno. 첨부된 파일이 없으면 NullPointerException 예외발생)
			attachMapper.insert(attach);
		});
	}

	//전체 bno의 BoardVO 가져오기
	@Override
	public List<BoardVO> getList() {
		return mapper.getList();
	}

	//특정 bno의 BoardVO 가져오기
	@Override
	public BoardVO get(Long bno) {
		return mapper.get(bno);
	}

	//특정 bno의 게시물수정 - 진행중 오류나면 전체 롤백함
	@Transactional
	@Override
	public void modify(BoardVO board) {
		
		attachMapper.deleteAll(board.getBno());                                                      //특정 bno의 BoardAttachVO값 delete하기
		
		boolean modifyResult = mapper.update(board) == 1;                                            //특정 bno의 BoardVO값 update완료되면 true값 저장 
		
		if(modifyResult && board.getAttachList() != null && board.getAttachList().size() >= 0) {     //아래 getAttachList()메소드 수행 - findByBno : 특정 bno의 BoardAttachVO값 가져오기
			
			board.getAttachList().forEach(attach -> {                                                //각 BoardAttachVO값에 해당 BoardVO의 bno값 set하고 BoardAttachVO값 insert하기
				attach.setBno(board.getBno());                                                       //(이때, 첨부파일테이블 bno. 첨부된 파일이 없으면 NullPointerException 예외발생)
				attachMapper.insert(attach);
			});
		}
	}

	//특정 bno의 게시물삭제 - 진행중 오류나면 전체 롤백함
	@Transactional
	@Override
	public boolean delete(Long bno) {
		
		replyMapper.deleteAll(bno);                                        //특정 bno의 ReplyVO값 delete하기
		attachMapper.deleteAll(bno);                                       //특정 bno의 BoardAttachVO값 delete하기
		
		return mapper.delete(bno) == 1;                                    //특정 bno의 BoardVO값 delete완료되면 true값 반환
	}

	//검색후 특정 페이지의 BoardVO 가져오기
	@Override
	public List<BoardVO> getListWithPaging(Criteria cri) {
		return mapper.getListWithPaging(cri);
	}

	//검색후 전체 게시글수 가져오기
	@Override
	public int getTotalCount(Criteria cri) {
		return mapper.getTotalCount(cri);
	}

	//특정 bno의 BoardAttachVO값 가져오기
	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		return attachMapper.findByBno(bno);
	}

	//특정 bno의 BoardAttachVO값 삭제하기
	@Override
	public void removeAttach(Long bno) {
		attachMapper.deleteAll(bno);
	}

}
