<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<!-- css, js 파일포함 -->
<!-- 절대경로  /WEB-INF/views/include/header_info.jsp -->
<%@include file="/WEB-INF/views/include/header_info.jsp" %>
<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
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
      <h1>
        Page Header
        <small>Optional description</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
        <li class="active">Here</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

      <!--------------------------
        | 글쓰기 폼 | BoardVO클래스의 필드명을 참고.
        -------------------------->
     <div class="row">
      <div class="col-md-12">   
       <div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">Board Read Page</h3>
            </div>
            <!-- /.box-header -->
            
              <div class="box-body">
                <div class="form-group">
                  <label for="bno">Bno</label>
                  <input type="text" class="form-control" id="bno" name="bno" value="${board.bno }" readonly="readonly">
                </div>
                <div class="form-group">
                  <label for="title">Title</label>
                  <input type="text" class="form-control" id="title" name="title" value="${board.title }" readonly="readonly">
                </div>
                <div class="form-group">
                  <label for="content">Text area</label>
                  <%--<textarea class="form-control" rows="3" id="content" name="content" readonly="readonly">${board.content }</textarea> --%>
                  <div>${ board.content}</div>
                </div>
                <div class="form-group">
                  <label for="writer">Writer</label>
                  <input type="text" class="form-control" id="writer" name="writer" value="${board.writer }" readonly="readonly">
                </div>
              </div>
              <!-- /.box-body -->

              <div class="box-footer">
                <button id="btnModify" type="button" data-bno="${board.bno }" class="btn btn-primary">Modify</button>
                <button id="btnList" type="button" class="btn btn-info">List</button>


                <form id="operForm" action="/board/modify" method="get">
                  <input type="hidden" id="bno" name="bno" value='<c:out value="${ board.bno}" />'>
                  <input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum }" />'>
                  <input type="hidden" name="amount" value='<c:out value="${cri.amount }" />'>
                  <input type="hidden" name="type" value='<c:out value="${cri.type }" />'>
                  <input type="hidden" name="keyword" value='<c:out value="${cri.keyword }" />'>
                  
                </form>

              </div>
          
          </div>
        </div>
       
        
	</div>
	
  <!--원본이미지 출력-->
  <div class='bigPictureWrapper'>
    <div class='bigPicture'>
    </div>
  </div>


<style>
.uploadResult {
  width:100%;
  background-color: gray;
}
.uploadResult ul{
  display:flex;
  flex-flow: row;
  justify-content: center;
  align-items: center;
}
.uploadResult ul li {
  list-style: none;
  padding: 10px;
  align-content: center;
  text-align: center;
}
.uploadResult ul li img{
  width: 100px;
}
.uploadResult ul li span {
  color:white;
}
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
  background:rgba(255,255,255,0.5);
}
.bigPicture {
  position: relative;
  display:flex;
  justify-content: center;
  align-items: center;
}

.bigPicture img {
  width:600px;
}

</style>
	
 <!-- 첨부파일목록 출력부분-->
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header with-border">
					<h3 class="box-title">Files</h3>
				</div>
				<div class="box-body">
					<div class="uploadResult">
            			<ul></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
  
  <!-- 댓글목록 출력부분 -->
  <div class="row">
    <div class="col-md-12">
      <button type="button" id="btnReplyAdd" class="btn btn-primary">Reply Write</button>      
    </div>
  </div>
  <!--댓글 출력-->
  <div class="row">
    <div class="col-md-12">
      <div id="replyList">
        
      </div>
    </div>
  </div>
  <!--댓글 페이징 출력-->
  <div class="row" id="replyPaging">
        
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

<!-- REQUIRED JS SCRIPTS(with jquery) -->
<%@include file="/WEB-INF/views/include/plugin_js.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<script id="replyTemplate" type="text/x-handlebars-template">
  {{#each .}}
  <div class="modal-body">
      <div class="form-group">
        <label for="replyer" class="col-form-label rno"><b>No. <span class="reply-rno">{{rno}}</span></b></label>
        <input type="text" class="form-control" name="replyer" readonly value="{{replyer}}">
      </div>
      <div class="form-group">
        <label for="reply" class="col-form-label regdate">{{prettifyDate replydate}}</label>
        <textarea class="form-control" name="reply" readonly>{{reply}}</textarea>
      </div>
      <div class="form-group">
        <button type="button" class="btn btn-link btn-reply-modify">Modify</button>
        <button type="button" class="btn btn-link btn-reply-delete">Delete</button>
      </div>
  </div>
  
  {{/each}}
</script>

<script>
  // Handlebars 사용자정의 Helper : 댓글 작성일 밀리세컨드 데이타를 날짜포맷으로 변환하는 작업(2022/01/11)
  Handlebars.registerHelper("prettifyDate", function(timeValue){
    const date = new Date(timeValue);

    return date.getFullYear() + "/" + date.getMonth() + 1 + "/" + date.getDate();

  });



  //1)댓글목록출력함수
  // replyArr : 댓글데이타를 받을 파라미터 target : 댓글목록이 표시될 위치, templateObj : 템플릿을 참조하는 변수
  let printData = function (replyArr, target, templateObj) {

    let template = Handlebars.compile(templateObj.html());
    let html = template(replyArr); // 댓글 템플릿소스와 데이터가 바인딩되어 결합된 소스
    target.empty();
    target.append(html);
  }


  //2)댓글페이징기능함수
  // pageMaker : 페이징정보, target : 페이징이 출력될 위치.
  let printPaging = function(pageMaker, target){

    let pageInfoStr = "<div class='col-md-12'><div class='dataTables_paginate paging_simple_numbers' id='example2_paginate'><ul class='pagination'>";

    if(pageMaker.prev){
      pageInfoStr += "<li class='paginate_button previous ";
			pageInfoStr += "id='example2_previous'><a href='" + (pageMaker.startPage - 1) + "'";
			pageInfoStr += " aria-controls='example2' data-dt-idx='0' tabindex='0'>Previous</a></li>";
    }

    for(let i=pageMaker.startPage; i<= pageMaker.endPage; i++){
      
      let strClass = (pageMaker.cri.pageNum == i) ? 'active' : '';
      pageInfoStr += "<li class='paginate_button " + strClass + "'><a href='" + i + "'>" + i + "</a></li>";
    }

    if(pageMaker.next){
      pageInfoStr += "<li class='paginate_button next ";
			pageInfoStr += "id='example2_next'><a href='" + (pageMaker.endPage + 1) + "'";
			pageInfoStr += " aria-controls='example2' data-dt-idx='0' tabindex='0'>Next</a></li>";
    }

    pageInfoStr += "</ul></div></div>";

    target.html(pageInfoStr);
  }


  //현재 게시물 번호
  let bno = <c:out value="${ board.bno}" />;
  let replyPage = 1;
    
  //현재 게시물번호에 해당하는 댓글목록 요청주소
  let url = "/replies/pages/" + bno + "/" + replyPage + ".json";
  getPage(url);

  function getPage(url){
    // 스프링에서 json포맷의 댓글목록과 페이징정보를 받아오는 구문
    $.getJSON(url, function(data){

        // data -> data.list, data.pageMaker

        // 댓글목록 출력
        printData(data.list, $("#replyList"), $("#replyTemplate"));
        // 댓글페이징출력
        printPaging(data.pageMaker, $("#replyPaging"));
    });
  }


</script>



<script>

  $(document).ready(function(){

    let operForm = $("#operForm");

    //수정 버튼 클릭시 동작
    $("#btnModify").on("click", function(){
      operForm.submit();
    });

    //목록 버튼 클릭시 동작
    $("#btnList").on("click", function(){
      operForm.find("#bno").remove();
      operForm.attr("action", "/board/list");
      operForm.submit();
    });
    
    //현재 게시물 번호
    //let bno = <c:out value="${ board.bno}" />;
    
    //현재 게시물번호에 해당하는 댓글목록 요청주소
    //let url = "/replies/pages/" + bno + "/1.json";
    
    //alert(url);
    
    // 현재게시물을 참조하는 댓글데이타 요청구문작성
    // jquery에서 제공하는 ajax기능의 메서드이용.
    /*
    $.getJSON(url, function(data){
        
        
        console.log("댓글총개수: " + data.replyCnt);
        console.log("댓글출력개수: " + data.list.length);
        console.log("댓글번호: " + data.list[0].rno);
        console.log("댓글내용: " + data.list[0].reply);
        console.log("댓글작성자: " + data.list[0].replyer);

        let date = new Date(data.list[0].replydate);
        console.log("댓글등록일: " + date);

        let trStr = "";

        for(let i=0; i<data.list.length; i++){
          trStr += "<tr>";
          trStr += "<td>" + data.list[i].rno + "</td>";
          trStr += "<td>" + data.list[i].reply + "</td>";
          trStr += "<td>" + data.list[i].replyer + "</td>";
          trStr += "<td>" + date + "</td>";
          trStr += "</tr>";

          
        }

        $("#replyTable").append(trStr);

        
        
        
  });
  */

  // 댓글 페이지번호 클릭. 선택자가 동적인 태그로 작업된 경우에는 직접 이벤트를 추가할수 없다.(중요)
  /*
  $("li.paginate_button a").on("click", function(e){
    e.preventDefault(); // <a>태그의 링크기능 취소
    console.log("댓글 페이지번호 클릭");
  });
  */

  //페이지 번호 클릭시.
  $("#replyPaging").on("click", "li.paginate_button a", function(e){
    e.preventDefault();
    console.log("댓글 페이지번호 클릭");

    replyPage = $(this).attr("href");
    let url = "/replies/pages/" + bno + "/" + replyPage + ".json";
    getPage(url);
  });
  
  //댓글쓰기 대화상자
  $("#btnReplyAdd").on("click", function(){
    
    // 댓글 대화상자 필드 초기화
    $("#replyer").val("");
    $("#reply").val("");

    $("button#btnReplyWrite").show();
    $("button#btnReplyModify").hide();


    $("h5#exampleModalLabel").html("Reply Add");
    $("#exampleModal").modal('show');
  });
  
  
  
  //댓글저장버튼
  $("#btnReplyWrite").on("click", function(){

    $("h5#exampleModalLabel").html("Reply Add");

    // 선택자 ID속성이 중복되지 않게 사용한다.
    
    let replyer = $("#replyer").val();  
    let reply = $("#reply").val();

    

    let str = JSON.stringify({bno:${board.bno}, replyer:replyer, reply:reply});

    console.log(str);


    //return;

    $.ajax({
      type: 'post',
      url : '/replies/new',
      headers: {
                "Content-Type" : "application/json","X-HTTP-Method-Override" : "POST"
              },
      dataType: 'text',
      data: JSON.stringify({bno:${board.bno}, replyer:replyer, reply:reply}),
      success: function(result){
        if(result == "success"){
          alert("댓글 데이타 삽입성공");

           // 댓글 대화상자 필드 초기화
        $("#replyer").val("");
        $("#reply").val("");


          $("#exampleModal").modal('hide');

          replyPage = 1;
    
          //현재 게시물번호에 해당하는 댓글목록 요청주소
          url = "/replies/pages/" + bno + "/" + replyPage + ".json";
          getPage(url);

        }
      }
    });

  });

  // 댓글목록에서 수정버튼 클릭시
  /*
  $("button.btn-reply-modify").on("click", function(){
      console.log("댓글수정버튼");
  });
  */

  $("#replyList").on("click", "button.btn-reply-modify", function(){
    //console.log("댓글수정버튼");

    let replyer = $(this).parents("div.modal-body").find("input[name='replyer']").val();
    let rno = $(this).parents("div.modal-body").find("span.reply-rno").text();
    let replydate = $(this).parents("div.modal-body").find("label.regdate").text();
    let reply = $(this).parents("div.modal-body").find("textarea[name='reply']").val();

    console.log("replyer: " + replyer);
    console.log("rno: " + rno);
    console.log("replydate: " + replydate);
    console.log("reply: " + reply);

    // 수정 모달대화상자에 댓글내용을 화면에 보여준다.
    $("h5#exampleModalLabel").html("Reply Modify - ");
    $("h5#exampleModalLabel").append("&nbsp;&nbsp;No. " + rno);
    
    $("#replyer").val(replyer);
    $("#reply").val(reply);
    $("#replybno").val(rno);

    $("button#btnReplyWrite").hide();
    $("button#btnReplyModify").show();

    
    $("#exampleModal").modal('show');
  });

  //댓글 수정대화상자에서 수정버튼 클릭시
  $("button#btnReplyModify").on("click", function(){
    //console.log("댓글 수정대화상자에서 수정버튼 클릭시");
    // ajax -> spring
    
    $.ajax({
      type: 'put',
      url: '/replies/modify/' + $("#replybno").val(),
      headers: {
        "Content-Type" : "application/json", "X-HTTP-Method-Override" : "PUT"
      },
      dataType: 'text',
      data: JSON.stringify({reply: $("#reply").val()}),
      success: function(result){
        if(result == "success"){
          alert("댓글 수정됨");

        $("#replyer").val("");
        $("#reply").val("");


        $("#exampleModal").modal('hide');

        //현재 게시물번호에 해당하는 댓글목록 요청주소
        url = "/replies/pages/" + bno + "/" + replyPage + ".json";
        getPage(url);
        }
      }
    });
    

  });

  //댓글목록에서 삭제버튼 클릭
  $("#replyList").on("click", "button.btn-reply-delete", function(){

    let rno = $(this).parents("div.modal-body").find("span.reply-rno").text();

    if(!confirm("댓글 " + rno + "번을 삭제하겠습니까?")) { return;}

    $.ajax({
      type: 'delete',
      url: '/replies/delete/' + ${ board.bno} + '/' + rno,
      headers: {
        "Content-Type" : "application/json", "X-HTTP-Method-Override" : "DELETE"
      },
      dataType:'text',
      success: function(result){
        if(result == "success"){
          alert("댓글삭제됨");

          $("#exampleModal").modal('hide');

          //현재 게시물번호에 해당하는 댓글목록 요청주소
          url = "/replies/pages/" + bno + "/" + replyPage + ".json";
          getPage(url);
        }
      }
    });
  });


});



//첨부파일 목록 가져오기
$(document).ready(function(){

  let bno = '${ board.bno}'; //현재 게시물 번호

  $.getJSON("/board/getAttachList", {bno: bno}, function(arr){

	//1) 
	console.log(arr); // BoardAttachVO클래스
    
    showUploadResult(arr);
  });
  
  
//첨부된 파일정보를 이용하여, 화면에 파일목록을 출력하는 작업
  function showUploadResult(uploadResultArr){

    if(!uploadResultArr || uploadResultArr.length == 0){return;}

    let uploadUL = $(".uploadResult ul");

    let str = "";

    $(uploadResultArr).each(function(i, obj) {

      if(obj.fileType){
        //이미지파일출력
        let fileCalPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
        str += "<li data-path='" + obj.uploadPath + "'";
        str += " data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName +"' data-type='" + obj.fileType + "'>";
        str += "<div>";
        str += "<span>" + obj.fileName + "</span>";
        str += "<img src='/display?fileName=" + fileCalPath + "'>";
        str += "</div>";
        str += "</li>";



      }else{
        //일반파일출력
        let fileCalPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
        let fileLink = fileCalPath.replace(new RegExp(/\\/g), "/");
        
        str += "<li data-path='" + obj.uploadPath + "'";
        str += " data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName +"' data-type='" + obj.fileType + "'>";
        str += "<div>";
        str += "<span>" + obj.fileName + "</span>";
        str += "<img src='/resources/img/attach.png'>";
        str += "</div>";
        str += "</li>";
      }
    });
	//2)
    console.log(str);

    uploadUL.append(str);

  }
  

  // 게시물조회페이지(get.jsp)에서 첨부파일목록의 클릭시 1)이미지 : 원본이미지 출력 2)일반파일 : 다운로드
  $(".uploadResult").on("click", "li", function(e){

    console.log("veiw image");

    let liObj = $(this);

    let path = encodeURIComponent(liObj.data("path") + "/" + liObj.data("uuid") + "_" + liObj.data("filename"));

    if(liObj.data("type")){
      //원본이미지 보여주기
      showImage(path.replace(new RegExp(/\\/g), "/"));
    }else{
      //일반파일 : 다운로드
      self.location = "/download?fileName="+path;
    }

  });

  function showImage(fileCalPath){
    alert(fileCalPath);

    $(".bigPictureWrapper").css("display", "flex").show();
    $(".bigPicture")
      .html("<img src='/display?fileName=" + fileCalPath + "'>")
      .animate({width: '100%', height: '100%'}, 1000);

  }

  $(".bigPictureWrapper").on("click", function(){
    $(".bigPicture").animate({width: '0%', height: '0%'}, 1000);

    setTimeout(function(){
      $(".bigPictureWrapper").hide();
    }, 1000);
  });

});

</script>

<!-- Modal Dialog-->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Reply Add</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="replyer" class="col-form-label">replyer:</label>
            <input type="text" class="form-control" id="replyer" name="replyer">
            <input type="hidden" class="form-control" id="replybno" name="replyerbno">
          </div>
          <div class="form-group">
            <label for="reply" class="col-form-label">reply:</label>
            <textarea class="form-control" id="reply" name="reply"></textarea>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="btnReplyWrite">write</button>
        <button type="button" class="btn btn-primary" id="btnReplyModify">modify</button>
      </div>
    </div>
  </div>
</div>
<!-- Modal Dialog-->
</body>
</html>
