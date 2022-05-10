package com.doccomsa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doccomsa.domain.Criteria;
import com.doccomsa.domain.ReplyVO;

// 인터페이스는 객체(bean)를 생성할수가 없다. 내부적으로 인터페이스를 참조하는 프록시객체(MapperProxy)가 생성된다.
public interface ReplyMapper {

	// mapper xml파일의 insert,update, delete문이 작동되면  테이블에 작업한 행수인 리턴값이 1이 반환된다.
	public int insert(ReplyVO vo);
	
	// 댓글 글번호에 대한 댓글데이타 정보조회
	public ReplyVO read(Long rno);
	
	public int update(ReplyVO reply);
	
	public int delete(Long rno);
	
	public void deleteAll(Long bno);
	
	// mapper인터페이스 메서드에서 파라미터가 2개 이상일 경우에는
	// 1)클래스를 구성(객체)  2)Map컬렉션 이용 3)@Param 어노테이션을 이용
	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);
	
	public int getCountByBno(Long bno);

	
	
	
	
}
