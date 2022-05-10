<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/bower_components/ckeditor/ckeditor.js"></script>
<script>
	$(document).ready(function(){
		
		let ckeditor_config = {
			resize_enabled : false,
			enterMode : CKEDITOR.ENTER_BR,
			shiftEnterMode : CKEDITOR.ENTER_P,
			toolbarCanCollapse : true,
			removePlugins : "elementspath",
			
			filebrowserUploadUrl : "/editor/imageUpload"  // /editor/imageUpload. 이미지 업로드시 업로드탭 보기
				
		};
		
		CKEDITOR.replace('ckeditor', ckeditor_config);
		
		// 4.8.0 (Standard)
		// alert(CKEDITOR.version);  
		
	});
</script>
</head>
<body>
<h3>CKEditor 적용</h3>
<form>
	<textarea name="ckeditor" rows="10" cols="30"></textarea><br>
	<input type="submit" value="등록">
</form>
</body>
</html>