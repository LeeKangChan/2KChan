<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
			<div id="loginBoxTitle">비밀번호 찾기</div>
			<!-- 아이디, 비번, 버튼 박스 -->
			<div id="inputBox">				
				<div class="input-form-box">
					<span>새로운 비밀번호</span>
					<input type="password" id="pwd" class="form-control">
				</div>
				
				<div class="input-form-box">
					<span>비밀번호 확인</span>
					<input type="password" id="pwdChk" class="form-control">
				</div>
								
				<div class="button-login-box">
					<button type="button" class="btn btn-primary btn-xs" onclick="pwdChk()" style="width:100%">비밀번호 변경</button>
				</div>
			</div>

		</div>
	</div>
	
	<form name="changePwdForm" id="changePwdForm" action="/changePwd" method="post">
		<input type="hidden" name="id" value="${id}">
		<input type="hidden" name="pwd" value="">
		<input type="hidden" name="pwdChk" value="">
	</form>
		
	<script>
	
	function pwdChk() {
		var pwd = $("#pwd").val();
		var pwdChk = $("#pwdChk").val();

		var pwdRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*]).{8,16}$/;
		
		if(pwd == null || pwd == '') {
			alert("비밀번호를 입력해 주세요");
			$("#pwd").focus();
			return false;
		} else if(!pwdRegex.test(pwd)) {
			alert("비밀번호는 영문, 숫자, 특수문자를 포함하여 8~16자로 입력해 주세요");
			$("#pwd").focus();
			return false;
		} else if(pwdChk == null || pwdChk == '') {
			alert("비밀번호 확인을 입력해 주세요");
			$("#pwd").focus();
			return false;
		} else if(pwd != pwdChk) {
			alert("비밀번호가 서로 다릅니다. 다시 확인해 주세요");
			$("pwd").focus();
			return false;
		} else {
			$('#changePwdForm [name="pwd"]').val(pwd);
			$('#changePwdForm [name="pwdChk"]').val(pwdChk);
			$("#changePwdForm").submit();
		}
	}
</script>
</body>
<c:import url="/footer"/>
