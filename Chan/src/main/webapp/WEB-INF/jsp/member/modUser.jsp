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
			<div id="loginBoxTitle">Join</div>
			<!-- 아이디, 비번, 버튼 박스 -->
			<div id="inputBox">
				
				
				<div class="input-form-box">
					<span>아이디</span>
					${member.id}
				</div>
				
				<div class="input-form-box">
					<span>이름</span>
					<input type="text" id="name" class="form-control" value="${member.name}">
				</div>
				
				<div class="input-form-box">
					<span>나이</span>
					<input type="text" id="age" class="form-control" value="${member.age}">
				</div>
				
				<div class="input-form-box">
					<span>성별</span>
					남 <input type="radio" id="sexM" name="sexRadio" value="M" <c:if test="${member.sex == 'M'}">checked</c:if>>
					여 <input type="radio" id="sexW" name="sexRadio" value="F" <c:if test="${member.sex == 'F'}">checked</c:if>>
				</div>
				
				<div class="input-form-box">
					<span>생년월일 (8자리)</span>
					<input type="text" id="birth" class="form-control" maxlength="8" value="${member.birth}">
				</div>
				
				<div class="input-form-box">
					<span>주소</span>
					<input type="text" id="post" class="form-control" value="${member.post}">
				</div>
	
				<div class="input-form-box">
					<span>전화번호</span>
					<input type="text" id="tel1" class="form-control" maxlength="3" value="${tel1}">
					-
					<input type="text" id="tel2" class="form-control" maxlength="4" value="${tel2}">
					-
					<input type="text" id="tel3" class="form-control" maxlength="4" value="${tel3}">
				</div>
				
				<div class="input-form-box">
					<span>이메일</span>
					<input type="text" id="email1" class="form-control" value="${email1}">
					@
					<input type="text" id="email2" class="form-control" value="${email2}">
				</div>
								
				<div class="button-login-box">
					<button type="button" class="btn btn-primary btn-xs" onclick="modUserChk()" style="width:100%">정보수정</button>
				</div>
			</div>

		</div>
	</div>
	
	<form name="modUserForm" id="modUserForm" action="/modUserChk" method="post">
		<input type="hidden" name="id" value="${member.id}">
		<input type="hidden" name="tel" value="${member.tel}">
		<input type="hidden" name="name" value="">
		<input type="hidden" name="age" value="">
		<input type="hidden" name="sex" value="">
		<input type="hidden" name="birth" value="">
		<input type="hidden" name="post" value="">
		<input type="hidden" name="tel1" value="">
		<input type="hidden" name="tel2" value="">
		<input type="hidden" name="tel3" value="">
		<input type="hidden" name="email1" value="">
		<input type="hidden" name="email2" value="">
	</form>
		
	<script>
	
	function modUserChk() {
		var name = $("#name").val();
		var age = $("#age").val();
		var sex = $('input[name="sexRadio"]:checked').val();
		var birth = $("#birth").val();
		var post = $("#post").val();
		var tel1 = $("#tel1").val();
		var tel2 = $("#tel2").val();
		var tel3 = $("#tel3").val();
		var email1 = $("#email1").val();
		var email2 = $("#email2").val();

		var regExp = /^[0-9]*$/;
		var pwdRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*]).{8,16}$/;
		
		if(name == null || name == '') {
			alert("이름을 입력해 주세요");
			$("#name").focus();
			return false;
		} else if(age == null || age == '') {
			alert("나이를 입력해 주세요");
			$("#age").focus();
			return false;
		} else if(!regExp.test(age)) {
			alert("나이는 숫자만 입력해 주세요");
			$("#age").focus();
			return false;
		} else if(sex == null || sex == '') {
			alert("성별을 선택해 주세요");
			$("#sex").focus();
			return false;
		} else if(birth == null || birth == '') {
			alert("생년월일을 입력해 주세요");
			$("#birth").focus();
			return false;
		} else if(birth.length != 8) {
			alert("생년월일은 8자로 입력해 주세요(ex:20001205)");
			$("#birth").focus();
			return false;
		} else if(!regExp.test(birth)) {
			alert("생년월일은 숫자만 입력해 주세요");
			$("#birth").focus();
			return false;
		} else if(post == null || post == '') {
			alert("주소를 입력해 주세요");
			$("#post").focus();
			return false;
		} else if(tel1 == null || tel1 == '') {
			alert("전화번호를 입력해 주세요");
			$("#tel1").focus();
			return false;
		} else if(tel2 == null || tel2 == '') {
			alert("전화번호를 입력해 주세요");
			$("#tel1").focus();
			return false;
		} else if(tel3 == null || tel3 == '') {
			alert("전화번호를 입력해 주세요");
			$("#tel3").focus();
			return false;
		} else if(!regExp.test(tel1) || !regExp.test(tel2) || !regExp.test(tel3)) {
			alert("전화번호는 숫자만 입력해 주세요");
			$("#tel1").focus();
			return false;
		} else if(email1 == null || email1 == '') {
			alert("이메일을 입력해 주세요");
			$("#email1").focus();
			return false;
		} else if(email2 == null || email2 == '') {
			alert("이메일을 입력해 주세요");
			$("#email2").focus();
			return false;
		} else {
			$('#modUserForm [name="name"]').val(name);
			$('#modUserForm [name="age"]').val(age);
			$('#modUserForm [name="sex"]').val(sex);
			$('#modUserForm [name="birth"]').val(birth);
			$('#modUserForm [name="post"]').val(post);
			$('#modUserForm [name="tel1"]').val(tel1);
			$('#modUserForm [name="tel2"]').val(tel2);
			$('#modUserForm [name="tel3"]').val(tel3);
			$('#modUserForm [name="email1"]').val(email1);
			$('#modUserForm [name="email2"]').val(email2);
			$("#modUserForm").submit();
		}
	}
</script>
</body>

<c:import url="/footer"/>