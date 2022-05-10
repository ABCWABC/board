package com.doccomsa.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/editor/*")
public class EditorController {
	
	
	@GetMapping("/write")
	public void write() {
		
	}
	
	@PostMapping("/imageUpload")
	public void imageUpload(HttpServletRequest request, HttpServletResponse response,@RequestParam MultipartFile upload) {
		
		/*
		 CKEditor 파일업로드 1)파일업로드 작업 2) 업로드된 파일정보를 브라우저에게 보내야 한다. 
		  
		 */
		
		
		// 클라이언트로부터 전송되어 온 파일을 업로드폴더에 복사(생성)작업
		OutputStream out = null;
		
		// 업로드된 파일정보를 브라우저에게 보내는 작업
		PrintWriter printWriter = null;
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		try {
			String fileName = upload.getOriginalFilename();
			byte[] bytes = upload.getBytes();
			// 클라이언트에서 전송해 온 파일명을 포함하여, 실제 업로드되는 경로생성
			String uploadPath = request.getSession().getServletContext().getRealPath("/resources/upload/") + fileName;
			
			log.info(uploadPath);
			
			out = new FileOutputStream(new File(uploadPath)); // 0byte의 빈 파일생성
			
			// 파일에 내용이 채워짐.
			out.write(bytes);
			out.flush();
			
			/*======================================================================*/
			
			
			String callback = request.getParameter("CKEditorFuncNum");
			
			log.info(callback);
			
			printWriter = response.getWriter();
			
			// <resources mapping="/upload/**" location="/resources/upload/" />
			String fileUrl = "/upload/" + fileName;
			
			printWriter.println("<script>window.parent.CKEDITOR.tools.callFunction("
								+ callback
								+ ",'"
								+ fileUrl
								+ "','이미지를 업로드 하였습니다.'"
								+ ")</script>");
			printWriter.flush();
			
		
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
			if(out != null) out.close();
			if(printWriter != null) printWriter.close();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
		}
		
		//return;
		
	}

}
