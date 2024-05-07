<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:import url="/header"/>

<body class="text-center">

	<!--  html 전체 영역을 지정하는 container -->
	<div id="container">
		<!--  login 폼 영역을 : loginBox -->
		<div id="loginBox">

			<!-- 로그인 페이지 타이틀 -->
			<h3>본인확인을 위해 다시 로그인 해주세요</h3>
			<!-- 아이디, 비번, 버튼 박스 -->
			<div id="inputBox">
				
				<form id="inputForm" name="inputForm">
					<div class="input-form-box">
						<span>아이디 </span><input type="text" id="id" class="form-control">
					</div>
					
					<div class="input-form-box">
						<span>비밀번호 </span><input type="password" id="pwd" class="form-control">
					</div>
				</form>
				
				<div class="button-login-box">
					<button type="button" class="btn btn-primary btn-xs" onclick="myPageChk()" style="width:100%">로그인</button>
				</div>
			</div>

		</div>
	</div>
	
	<form name="myPageChkForm" id="myPageChkForm" action="/myPageChk1" method="post">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="pwd" value="">
	</form>

	<script>

    $("#inputForm").keypress(function (e) {
        if (e.keyCode === 13) {
        	myPageChk();
        }
    });

	function myPageChk() {
		var id = $("#id").val();
		var pwd = $("#pwd").val();
		
		if(id == null || id == '') {
			alert("아이디를 입력해 주세요");
			$("#id").focus();
		} else if(pwd == null || pwd == '') {
			alert("비밀번호를 입력해 주세요");
			$("pwd").focus();
		} else {
			$('#myPageChkForm [name="id"]').val(id);
			$('#myPageChkForm [name="pwd"]').val(pwd);
			$("#myPageChkForm").submit();
		}
	}
</script>
</body>

<c:import url="/footer"/>