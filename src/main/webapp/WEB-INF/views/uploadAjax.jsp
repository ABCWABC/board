<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.uploadResult {
		width: 100%;
		background-color: gray;
	}
	
	.uploadResult ul {
		display: flex;
		flex-flow: row;
		justify-content: center;
		align-items: center;
	}
	
	.uploadResult ul li {
		list-style: none;
		padding: 10px;
	}
	
	.uploadResult ul li img {
		width: 100px;
	}
</style>
<style>
	.bigPictureWrapper {
	  position: absolute;
	  display: none;
	  justify-content: center;
	  align-items: center;
	  top:0%;
	  width:100%;
	  height:100%;
	  background-color: gray; 
	  z-index: 100;
	}
	
	.bigPicture {
	  position: relative;
	  display:flex;
	  justify-content: center;
	  align-items: center;
	}
</style>
</head>
<body>
<h1>Upload with Ajax</h1>
<div class="bigPictureWrapper">
	<div class="bigPicture"></div>
</div>

<div class="uploadDiv">
	<input type="file" name="uploadFile" multiple>
</div>

<!-- 업로드된 파일정보를 사용하여, 업로드 파일목록출력-->
<div class="uploadResult">
	<ul></ul>
</div>

<button id="uploadBtn">Upload</button>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>

	let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	let maxSize = 5 * 1024 * 1024; //5mb
	
	function checkExtension(fileName, fileSize){

		if(fileSize >= maxSize){
			alert("파일 사이즈 초과");
			return false;
		}

		if(regex.test(fileName)){
			alert("해당 종류의 파일은 업로드 불가.");
			return false;
		}

		return true;

	}

	//파일업로드 태그부분의 div태그참조
	let cloneObj = $(".uploadDiv").clone();
	/*
	<div class="uploadDiv">
	<input type="file" name="uploadFile" multiple>
	</div>	
	*/

	// 업로드 된 파일정보를 참고하여, 출력하는 작업
	let uploadResult = $(".uploadResult ul");

	// 파라미터 uploadResultArr : 업로드된 파일정보
	function showUploadedFile(uploadResultArr){
		let str = "";

		$(uploadResultArr).each(function(i, obj){
			
			if(!obj.image){

				let fileCalPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);

				str += "<li><div><a href='/download?fileName="+ fileCalPath + "'><img src='/resources/img/attach.png'>" + obj.fileName + "</a>" +
					"<span style='cursor:pointer;' data-file=\'" + fileCalPath + "\' data-type='file'> x </span></div></li>";
			}else{

				let fileCalPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
				let originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;

				originPath = originPath.replace(new RegExp(/\\/g), "/");

				console.log(fileCalPath);
				

				// 썸네일이미지 출력(스프링에 썸네일이미지 주소요청)
				str += "<li><a href=\"javascript:showImage('" + originPath + "')\"><img src='/display?fileName=" + fileCalPath + "'></a>" + 
					"<span style='cursor:pointer;' data-file=\'" + fileCalPath + "\' data-type='image'> x </span></li>";
			}
			
			
		});

		uploadResult.append(str);

	}

	function showImage(fileCalPath){

		$(".bigPictureWrapper").css("display", "flex").show();

		$(".bigPicture")
			.html("<img src='/display?fileName="+fileCalPath+"'>")
			.animate({width:'100%', height:'100%'}, 1000);



	}
	



	$(document).ready(function(){

		$("#uploadBtn").on("click", function(){

			let formData = new FormData(); // <form>태그에 해당하는 의미.
			// 배열의 특징을 갖는 변수
			let inputFile = $("input[name='uploadFile']"); //선택자는 복수의 의미.

			let files = inputFile[0].files;

			//console.log(files);

			for(let i=0; i<files.length; i++){
				
				// 파일크기와 확장자를 제한
				if(!checkExtension(files[i].name, files[i].size)) {
					return false;
				}
												
				formData.append("uploadFile", files[i]);
			}


			// ajax전송작업
			$.ajax({
				url: "/uploadAjaxAction",
				processData: false,  // processData: true(기본값)이고 기능은 key:value값을 Query String으로 변환해서 보내는 기능. 주소?id=doccomsa&name=홍길동
				contentType: false, //  contentType: true(기본값)이고. "application/x-www-form-urlencoded;charset=UTF-8"  "multipart/form-data" 인코딩을 사용하여 전송.
				data:formData, // 첨부된 파일을 포함하고 있는 객체
				type:"post",
				dataType: "json", // 리턴값의 타입
				success: function(result){
					// result : 업로드된 파일정보를 List<AttachFileDTO>컬렉션이 json포맷으로  제공
					console.log(result);
					// 업로드파일정보를 이용하여, 파일목록으로 출력하는 작업
					showUploadedFile(result);

					// 업로드시 선택한 파일정보를 지우기 위하여, 자신의 div태그를 복제하여 사용.
					$(".uploadDiv").html(cloneObj.html());

				}
			});
		});

		// 파일삭제 (x) 클릭시 동작
		$(".uploadResult").on("click", "span", function(){
			let targetFile = $(this).data("file"); // <span data-file='값'>
			let type = $(this).data("type"); // <span data-type='값'>

			//console.log(targetFile);
			//console.log(type);

			$.ajax({
				url: '/deletedFile',
				data: {fileName: targetFile, type: type},
				dataType: 'text',
				type: 'POST',
				success: function(result) {
					alert(result);
				}
			});
		});
	});

</script>
</body>
</html>