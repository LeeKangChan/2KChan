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
			<div id="loginBoxTitle">아이디 찾기</div>
			
			${name}님의 아이디는 <h4 style="color:red">${id}</h4> 입니다

			<div id="inputBox">				
				<div class="button-login-box">
					<button type="button" class="btn btn-primary btn-xs" onclick="loginChk()" style="width:100%">로그인</button>
				</div>
				
				<div class="button-login-box">
					<button type="button" class="btn btn-primary btn-xs" onclick="location.href='/findPwd'" style="width: 100%;">비밀번호 찾기</button>
				</div>
			</div>
		</div>
	</div>
	
	<form name="loginForm" id="loginForm" action="/loginChk" method="post">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="pwd" value="">
		<input type="hidden" name="url" valye="${url}">
	</form>
		
	<script>
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
<c:import url="/footer"/>