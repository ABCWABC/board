package com.doccomsa.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.doccomsa.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {

	@GetMapping("/uploadForm")
	public void uploadForm() {
		
	}
	
	/*
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile) {
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("----------------------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());
		}
	}
	*/
	
	// "multipartResolver" bean이 multipart/form-data인코딩방식으로 클라이언트로부터 전송되어 온 파일을 MultipartFile[] uploadFile객체가
	// 사용할수 있도록 전달해주는 기능.
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile) {
		
		String uploadFolder = "D:\\Dev\\upload";
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("----------------------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());
			
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile); //파일복사(업로드)
			} catch (IllegalStateException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("uploadAjax");
	}
	
	// @RestController or @Controller + @ResponseBody, ResponseEntity클래스 사용.
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody  // jackson-databind라이브러리에 의하여 json으로 변환되고 @ResponseBody어노테이션이 응답객체의 body영역에 json데이타를 추가하는 기능.
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxAction(MultipartFile[] uploadFile) {
		
		
		// AttachFileDTO클래스? 첨부된 파일정보를 객체에 담아서, 클라이언트에게 보내고자 하는 목적
		
		ResponseEntity<List<AttachFileDTO>> entity = null;

		//업로드된 파일의 정보를 List컬렉션으로 구성하여, 클라이언트로 보내고자 작업
		List<AttachFileDTO> list = new ArrayList<AttachFileDTO>();
		
		String uploadFolder = "D:\\Dev\\upload"; // 
		
		String uploadFolderPath = getFolder(); // "운영체제별 구분자를 이용하여" "2022\01\18"

		//  D:\\Dev\\upload\\2022\\01\\19
		File uploadPath = new File(uploadFolder, uploadFolderPath);  
		
		// uploadPath객체가 관리하는 폴더가 존재유무체크.    예>  "D:\\Dev\\upload" "2022/01/18"
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs(); // 현재폴더명을 기준하여 상위폴더가 존재하지 않으면 모두 생성
		}
		

		for(MultipartFile multipartFile : uploadFile) {
			
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			// 클라이언트에서 보낸 파일명
			String uploadFileName = multipartFile.getOriginalFilename();
			//1)클라이언트 파일명
			attachDTO.setFileName(uploadFileName);
			
			// 중복되지 않는 문자열을 생성
			UUID uuid = UUID.randomUUID();
			
			// 중복되지 않는 문자열을 생성_클라이언트파일명
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			
			
			try {
				// "D:\\Dev\\upload" "2022/01/18" + 유일한 파일명
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile); //파일복사(업로드)
				
				//2)중복되지 않는 고유의 문자열.  2a88f93f-9b56-4791-af27-1ec2a66d59b6
				attachDTO.setUuid(uuid.toString());
				//3)파일이 업로드되는날짜폴더경로.  "2022\01\18"
				
				//uploadFolderPath = uploadFolderPath.replace("\\", "/");
				
				attachDTO.setUploadPath(uploadFolderPath);
				
				//업로드 파일이 이미지파일인지 일반파일인지 체크
				if(checkImageType(saveFile)) {
					attachDTO.setImage(true);
					
					//Thumnail 이미지 생성작업
					
					// 출력스트림객체가 생성됨 - 비워있는 파일생성(0byte)
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					
					//빈파일에 원본이미지 정보를 읽어와서 썸네일이미지 생성작업
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					
					thumbnail.close();
					
					
				}
				
				list.add(attachDTO);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		entity = new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);
		
		return entity;
	}

	// 업로드되는 파일이 이미지파일 여부를 체크
	private boolean checkImageType(File saveFile) {

		try {
			// 업로드 된 파일에 대한 MIME정보를 읽어오는 기능. *.sql 파일확장자에 대한 MIME정보가 null
			String contentType = Files.probeContentType(saveFile.toPath());  // text/html, text/plain 등

			return contentType.startsWith("image"); // MIME정보가 존재하지 않으면, NullPointerException 예외발생
		} catch (Exception e) {  // IOException 예외처리주의 할것.
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	// 날짜를 이용한 업로드 폴더생성
	private String getFolder() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date); // "2022-01-18"
		
		
		// File.separator 운영체제별 파일경로 구분자를 반환한다. 윈도우> c:\temp\..    리눅스> /home/etc/..
		return str.replace("-", File.separator);  
	}
	
	// 파라미터에 유효하지 않은 문자가 존재할 경우에는 에러발생.
	//클라이언트에서 썸네일 이미지 요청에따른 썸네일이미지 리턴메서드
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		
		log.info("fileName: " + fileName);
		
		ResponseEntity<byte[]> entity = null;
		
		
		File file = new File("D:\\Dev\\upload\\" + fileName);
		
		log.info("file: " + file);
		
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath())); // 브라우저에게 보내는 파일에 대한 MIME정보제공
			entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			//entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), HttpStatus.OK);
		 
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return entity;
		
	}
	
	// 다운로드
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName){
		
		ResponseEntity<Resource> entity = null;
		
		Resource resource = new FileSystemResource("D:\\Dev\\upload\\" + fileName);
		
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		
		// 클라이언트가 보낸 파일명
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		
		HttpHeaders headers = new HttpHeaders();
		
		String downloadName = null;
		
		try {
			downloadName = new String(resourceOriginalName.getBytes("UTf-8"), "ISO-8859-1");
			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		entity = new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
		
		return entity;
	}
		
	// 파일삭제
	@PostMapping("/deletedFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		
		ResponseEntity<String> entity = null;
		
		log.info("deleteFile: " + fileName);
		
		File file;
		
		try {
			file = new File("D:\\Dev\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
			
			file.delete(); // 일반파일 또는 썸네일이미지 파일 삭제
			
			// 원본이미지파일 삭제작업
			if(type.equals("image")) {
				
				String orginFileName = file.getAbsolutePath().replace("s_", "");
				
				log.info("orginFileName: " + orginFileName);
				
				file = new File(orginFileName);
				
				file.delete();
			}
	
		}catch(Exception ex) {
			ex.printStackTrace();
			//삭제하고자 하는 파일이 존재하지 않았을 경우 예외발생
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		
		entity = new ResponseEntity<String>("deleted", HttpStatus.OK);
		
		return entity;
		
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
}

