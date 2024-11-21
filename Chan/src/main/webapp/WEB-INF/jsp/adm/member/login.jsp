<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/adChan/header"/>

<body class="text-center">

	<!--  html 전체 영역을 지정하는 container -->
	<div id="container">
		<!--  login 폼 영역을 : loginBox -->
		<div id="loginBox">

			<!-- 로그인 페이지 타이틀 -->
			<div id="loginBoxTitle">관리자 로그인</div>
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
					<button type="button" class="btn btn-primary btn-xs" onclick="loginChk()" style="width:100%">로그인</button>
				</div>
			</div>

		</div>
	</div>
	
	<form name="loginForm" id="loginForm" action="/adChn/loginChk" method="post">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="pwd" value="">
	</form>

	<script>

    $("#inputForm").keypress(function (e) {
        if (e.keyCode === 13) {
        	loginChk();
        }
    });

	function loginChk() {
		var id = $("#id").val();
		var pwd = $("#pwd").val();
		
		if(id == null || id == '') {
			alert("아이디를 입력해 주세요");
			$("#id").focus();
		} else if(pwd == null || pwd == '') {
			alert("비밀번호를 입력해 주세요");
			$("pwd").focus();
		} else {
			$('#loginForm [name="id"]').val(id);
			$('#loginForm [name="pwd"]').val(pwd);
			$("#loginForm").submit();
		}
	}
</script>
</body>
<c:import url="/adChan/footer"/>