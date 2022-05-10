package com.doccomsa.mapper;

import java.util.List;

import com.doccomsa.domain.BoardAttachVO;

public interface BoardAttachMapper {

	public void insert(BoardAttachVO vo);
	
	public List<BoardAttachVO> findByBno(Long bno);
	
	//개별삭제(게시물수정에서 파일삭제)
	public void delete(String uuid);
	
	//게시물번호에 해당하는 삭제
	public void deleteAll(Long bno);
	
	// 하루 전의 파일정보 목록
	public List<BoardAttachVO> getOldFiles();
}
