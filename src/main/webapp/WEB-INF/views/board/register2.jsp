<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <%@include file="/WEB-INF/views/include/header_info.jsp" %>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <!-- Main Header -->
  <%@include file="/WEB-INF/views/include/header.jsp" %>
  <!-- Left side column. contains the logo and sidebar -->
  <%@include file="/WEB-INF/views/include/left_menu.jsp" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>Page Header<small>Optional description</small></h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i>Level</a></li>
        <li class="active">Here</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

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
     
      <!-- 글쓰기 폼 | BoardVO클래스의 필드명을 참고 -->
      <div class="row">
       <div class="col-md-12">   
        <div class="box box-primary">
         <div class="box-header with-border">
           <h3 class="box-title">Quick Example</h3>
         </div>
         <!-- /.box-header -->
         <!-- form start -->
         <form role="form" action="/board/register" method="post">
          <div class="box-body">
           <div class="form-group">
            <label for="title">Title</label>
            <input type="text" class="form-control" id="title" name="title">
           </div>
           <div class="form-group">
             <label for="content">Text area</label>
             <textarea class="form-control" rows="3" id="content" name="content"></textarea>
           </div>
           <div class="form-group">
             <label for="writer">Writer</label>
             <input type="text" class="form-control" id="writer" name="writer">
           </div>
          </div>
          <!-- /.box-body -->
          <div class="box-footer">
            <button type="submit" class="btn btn-primary">Submit</button>
          </div>
         </form>
        </div>
       </div>
      </div>
	
  	  <!-- 파일업로드및 파일목록 출력위치-->
	  <div class="row">
	   <div class="col-md-12">
		<div class="box box-primary">
		 <div class="box-header with-border">
		  <h3 class="box-title">File Attach</h3>
		 </div>
		 <div class="box-body">
		  <div class="form-group uploadDiv">
		   <input type="file" name="uploadFile" multiple>
		  </div>
		  <div class="uploadResult">
           <ul></ul>
		  </div>
		 </div>
	    </div>
	   </div>
	  </div>
	
     </section>
     <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer (기타 footer태그밑에 소스포함)-->
    <%@include file="/WEB-INF/views/include/footer.jsp" %>
   </div>
   <!-- ./wrapper -->

   <!-- REQUIRED JS SCRIPTS -->
   <%@include file="/WEB-INF/views/include/plugin_js.jsp" %>
   
<script src="/bower_components/ditor/ckeditor.js"></script>

<script>

   $(document).ready(function(){
	
	let ckeditor_config = {
		resize_enabled : false,
		enterMode : CKEDITOR.ENTER_BR,
		shiftEnterMode : CKEDITOR.ENTER_P,
		toolbarCanCollapse : true,
		removePlugins : "elementspath",
		
		filebrowserUploadUrl : "/editor/imageUpload"
			
	};
	
	CKEDITOR.replace('content', ckeditor_config);
	
   });
   
</script>

<script>

  $(document).ready(function(){

    let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
    let maxSize = 5 * 1024 * 1024;

    function checkExtension(fileName, fileSize){

      if(fileSize >= maxSize){
        alert("파일 크기초과");
        return false;
      }

      if(regex.test(fileName)){
        alert("해당 종류의 파일은 업로드 할 수 없습니다.");
        return false;
      }
      return true;
    }

    function showUploadResult(uploadResultArr){

      if(!uploadResultArr || uploadResultArr.length == 0){return;}

      let uploadUL = $(".uploadResult ul");

      let str = "";

      $(uploadResultArr).each(function(i, obj) {

        if(obj.image){
          //이미지파일출력
          let fileCalPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
          str += "<li data-path='" + obj.uploadPath + "'";
          str += " data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName +"' data-type='" + obj.image + "'>";
          str += "<div>";
          str += "<span>" + obj.fileName + "</span>";
          str += "<button type='button' data-file='" + fileCalPath + "' ";
          str += "data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
          str += "<img src='/display?fileName=" + fileCalPath + "'>";
          str += "</div>";
          str += "</li>";



        }else{
          //일반파일출력
          let fileCalPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
          let fileLink = fileCalPath.replace(new RegExp(/\\/g), "/");
          
          str += "<li data-path='" + obj.uploadPath + "'";
          str += " data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName +"' data-type='" + obj.image + "'>";
          str += "<div>";
          str += "<span>" + obj.fileName + "</span>";
          str += "<button type='button' data-file='" + fileCalPath + "' ";
          str += "data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
          str += "<img src='/resources/img/attach.png'>";
          str += "</div>";
          str += "</li>";
        }
      });

      console.log(str);

      uploadUL.append(str);

    }



    // <form role="form"
    let formObj = $("form[role='form']");

    // <button type="submit" class="btn btn-primary">Submit</button>
    $("button[type='submit']").on("click", function(e){
      e.preventDefault(); // 전송기능 취소

      console.log("submit click");

      // <input type="hidden" name="" value="파일정보">

      let str = "";

      // 첨부된 파일목록의 태그를 통하여 정보를 참조
      $(".uploadResult ul li").each(function(i, obj){
        let jobj = $(obj);

        console.dir(jobj); // jobj객체의 속성정보를 출력. 요소를 JSON형태의 트리구조로 보여준다.
        console.log("------------------");
        console.log(jobj.data("filename"));

        // 첨부파일 테이블에 저장하기위한 파일정보를 <input>태그로 동적작업을 한다.
        str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
        str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
        str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
        str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.data("type") + "'>";
      });

      console.log(str);

      formObj.append(str);
      formObj.submit();

    });

    // 삭제(X)  버튼 클릭시 동작
    $(".uploadResult").on("click", "button", function(){

      console.log("delete file");

      let targetFile = $(this).data("file");
      let type = $(this).data("type");

      // 현재 선택자 기준에서 조상요소의 탐색. .parent() : 부모(단일), .parents(): 조상들(여러개), .parentsUntil(선택자) : 선택자 이전까지의 요소들, .closet(선택자) : 상위요소중 선택자에 해당하는 첫번째 요소(단일)
      let targetli = $(this).closest("li");


      $.ajax({
        url: '/deletedFile',
        data: {fileName: targetFile, type:type},  // 서버에 데이타 보내는 형태.1)javascript object문법 2) object-> json변환
        dataType: 'text',
        type: 'POST',
        success: function(result){
          alert(result);

          targetli.remove(); // 파일정보를 나타내는 li태그 화면에서 제거됨
        }
      });
    });
  });

</script>
</body>
</html>
