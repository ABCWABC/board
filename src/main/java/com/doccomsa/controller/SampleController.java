package com.doccomsa.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doccomsa.domain.SampleVO;
import com.doccomsa.domain.Ticket;

import lombok.extern.log4j.Log4j;

// REST방식 테스트 도구 : PostMan, ARC-Advanced Rest Client 등
// REST방식과 Ajax를 사용하는 uri매핑주소는 @RestController 어노테이션을 사용한다.
// @Controlller + @ResponseBody 2개의 어노테이션이 결합된 의미이다.
@RestController
//@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
	
	
	// produces속성? 메서드에서 처리가능한 요청 컨텐츠타입과 응답컨텐트 타입을 MIME으로 제한하는 의미.  
	// 단순한 텍스트 반환
	@GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
	public String getText() {
		
		// MediaType 클래스 : 스프링에서 MIME규약 목록을 정의한 클래스
		// 인터넷에 전달되는 파일 포맷과 포맷콘덴츠를 위한 식별자.
		log.info("MIME TYPE: " + MediaType.TEXT_PLAIN_VALUE);
		
		return "안녕하세요"; // 스프링의 기본특징은 jsp파일명으로 참조를 하지만, 리턴값의 포맷을 json or xml형태로 클라이언트로 보내고자 함.
	}
	
	// 객체 반환(json or xml형태)
	@GetMapping(value = "/getSample", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	public SampleVO getSample() {
		
		return new SampleVO(112, "스타", "로드");
	}
	
	// 컬렉션 타입(List)의 객체반환
	@GetMapping(value = "/getList" , produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<SampleVO> getList(){
		
		List<SampleVO> list = new ArrayList<SampleVO>();
		
		
		for(int i=1; i<= 10; i++) {
			SampleVO sample = new SampleVO(i, i + "First", i + " Last");
			list.add(sample);
		}
		
		return list;
	}
	
	//컬렉션 타입(Map)의 객체반환
	@GetMapping(value = "/getMap" , produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Map<String, SampleVO> getMap(){
		
		Map<String, SampleVO> map = new HashMap<String, SampleVO>();
		
		map.put("First", new SampleVO(111, "그루트", "주니어"));
		
		
		return map;
	}
	
	// 앞에 메서드는 데이터 그자체 리턴값을 직접사용.
	// ------------------------------------------------------------------------------------------------
	// 밑에 메서드는 ResponseEntity클래스를 사용하여 데이터와 상태코드를 리턴
	
	// ResponseEntity클래스? 서버에서 클라이언트에게 결과를 보낼때, 상태코드를 포함하여, 데이터를 보낼때 사용.
	
	@GetMapping(value = "/check", params = {"height", "weight"})
	public ResponseEntity<SampleVO> check(Double height, Double weight){
		
		SampleVO vo = new SampleVO(000, "" + height, "" + weight);
		
		ResponseEntity<SampleVO> result = null;
		
		if(height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		
		return result;
	}
	
	// @PathVariable어노테이션 : 주소경로에 존재하는 값을 사용하고자 할때 사용
	@GetMapping("/product/{cat}/{pid}")  // REST방식 권장하는 주소형태. /product/1/10          기본주소    /product?cat=1&pid=10
	public String[] getpath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {
		
		return new String[] {"category: " + cat, "productid: " + pid};
	}
	
	@PostMapping("/ticket") // json형태의 문자열을 @RequestBody가 Ticket클래스에 맞게 변환과정 작업을 함.
	public Ticket convert(@RequestBody Ticket ticket) {
		
		log.info("convert.... ticket called  " + ticket);
		
		return ticket;
	}

}
