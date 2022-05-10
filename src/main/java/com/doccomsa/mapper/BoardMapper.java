package com.doccomsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doccomsa.domain.BoardVO;
import com.doccomsa.domain.Criteria;

// mapper interface를 기반으로 한 프록시 클래스 생성
public interface BoardMapper {

	//3)
	public void insert(BoardVO board); // 사용 안함.
	
	public Integer insertSelectKey(BoardVO board); // 글번호 bno 필드값이 null상태.
	
	public List<BoardVO> getList();
	
	// 검색기능이 포함된 필드(type, keyword)가 사용하게 됨.
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	// 검색기능 포함
	public int getTotalCount(Criteria cri);
	
	
	public BoardVO get(Long bno);
	
	public int update(BoardVO board);
	
	public int delete(Long bno);
	
	// 댓글추가 또는 삭제시 댓글개수 변경작업
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}
