package com.doccomsa.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.doccomsa.domain.BoardAttachVO;
import com.doccomsa.mapper.BoardAttachMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Component  // bean생성이름? fileCheckTask
public class FileCheckTask {

	@Setter(onMethod_ = {@Autowired})
	private BoardAttachMapper attachMapper;
	
	// 초(0~59) 분(0~59) 시(0~23) 일(1~31) 월(1~12) 요일(0~6) 년도(생략가능)
	/*
	  * : 모든값(매시,매일, 매주 의미)
	  ? : 특정값이 아닌 어떤값이든 상관없음
	  - : 범위를 지정
	  , : 여러값을 지정
	  / : 증분값, 즉 초기값과증가치를 설정할 경우
	  L : 지정할수 있는 범위의 마지막 값 표시
	  W : 가장 가까운 평일(weekday)을 설정할 때
	  # : N번 째 특정 요일을 설정할 때
	  
	  cron정규표현식
	  - 매 10분마다 : 0 0/10 * * * *
	  - 매 3시간마다 : 0 0 0/3 * * *
	  - 2022년도 매일 13시 20분마다 : 0 20 13 * * * 2022
	  - 매일 9시~19시 사이에 10분 간격으로 : 0 0/10 9-19 * * *
	  - 매일 9시, 19시만 10분 간격으로 : 0 0/10 9,19 * * *
	  - 매월10일 20시 30분에 : 0 30 20 25 * *
	  - 매주 월, 토요일 10시~19시 사이 10분마다 : 0 10 10-19 ? * MON,SAT
	  - 매월 마지막날 15시30분 : 0 30 15 L * *
	 */
	// 매일 새벽2시에 동작하는 설정
	@Scheduled(cron = "* * 2 * * *")  
	public void checkFiles() throws Exception{
		
		/*
		log.warn("File Check Task run.......");
		log.warn("==========================");
		*/
		
		log.warn(new Date());
		
		
		/*Stream API 사용*/
		
		// 1)하루 이전의 tbl_attach테이블의 파일정보(s_ 썸네일 이미지 정보는 포함되어 있지않다.)
		List<BoardAttachVO> fileList = attachMapper.getOldFiles();
		
		// Path클래스? java.io.File클래스의 업그레이드 된 버전.        java.io패키지, java.nio패키지
		
		// 2)하루 이전날짜폴더에 파일정보(s_ 썸네일 이미지 정보는 포함되어 있지않다.)
		List<Path> fileListPaths = fileList.stream()
				.map(vo -> Paths.get("D:\\Dev\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName()))
				.collect(Collectors.toList());
		
		// 3)fileListPaths컬렉션에 썸네일이미지 추가정보.(fileList컬렉션 fileType이 true(1)인것에 한하여 _s 썸네일이름을 구성하여 추가)
		fileList.stream().filter(vo -> vo.isFileType() == true) // 이미지파일정보 추출
			.map(vo -> Paths.get("D:\\Dev\\upload", vo.getUploadPath(), vo.getUuid() + "s_" + vo.getFileName()))
			.forEach(p -> fileListPaths.add(p));
				
		
		
		// for문 전환. 위의 3번구문과 동일한 작업이다.
		/*
		for(int i=0; i<fileList.size(); i++) {
			BoardAttachVO vo = fileList.get(i); 
			
			if(vo.isFileType() == true) {
				Path fPath = Paths.get("D:\\Dev\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName());
				fileListPaths.add(fPath);
			}
		}
		*/
		
		
		log.warn("=============================================================");
		
		fileListPaths.forEach(p -> log.warn(p));
		
		// fileListPaths 컬렉션 : tbl_attach테이블의 하루 이전날짜 파일정보. 이미지에 해당하는 썸네일정보추가
		
		//4)쓰레기 파일들을 삭제
		
		// "2022\01\24"날짜폴더에 대한 정보
		File targetDir = Paths.get("D:\\Dev\\upload", getFolerYesterDay()).toFile();
		
		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
		
		log.warn("===================================================================");
		for(File file : removeFiles) {
			log.warn(file.getAbsolutePath());
			
			file.delete();
		}
		
		
	}
	
	
	
	// 하루 이전 날짜의 정보를 문자열로 구하기.  예> "2022\01\24"
	private String getFolerYesterDay() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		
		// 운영체제에 맞는 구분자사용
		return str.replace("-", File.separator);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
