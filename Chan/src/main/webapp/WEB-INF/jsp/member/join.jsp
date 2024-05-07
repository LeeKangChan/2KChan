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
					<input type="text" id="id" class="form-control">
					<button type="button" class="btn btn-primary btn-xs" onclick="idChk()" style="width:100%">아이디 확인</button>
					<input type="hidden" id="idChk">
				</div>
				
				<p id="msg" style="font-size:15px; color:red;"></p>
				
				<div class="input-form-box">
					<span>비밀번호</span>
					<input type="password" id="pwd" class="form-control">
				</div>
				
				<div class="input-form-box">
					<span>비밀번호 확인</span>
					<input type="password" id="pwdChk" class="form-control">
				</div>
				
				<div class="input-form-box">
					<span>이름</span>
					<input type="text" id="name" class="form-control">
				</div>
				
				<div class="input-form-box">
					<span>나이</span>
					<input type="text" id="age" class="form-control">
				</div>
				
				<div class="input-form-box">
					<span>성별</span>
					남 <input type="radio" id="sexM" name="sexRadio" value="M">
					여 <input type="radio" id="sexW" name="sexRadio" value="F">
				</div>
				
				<div class="input-form-box">
					<span>생년월일 (8자리)</span>
					<input type="text" id="birth" class="form-control" maxlength="8">
				</div>
				
				<div class="input-form-box">
					<span>주소</span>
					<input type="text" id="post" class="form-control">
				</div>
	
				<div class="input-form-box">
					<span>전화번호</span>
					<input type="text" id="tel1" class="form-control" maxlength="3">
					-
					<input type="text" id="tel2" class="form-control" maxlength="4">
					-
					<input type="text" id="tel3" class="form-control" maxlength="4">
				</div>
				
				<div class="input-form-box">
					<span>이메일</span>
					<input type="text" id="email1" class="form-control">
					@
					<input type="text" id="email2" class="form-control">
				</div>
								
				<div class="button-login-box">
					<button type="button" class="btn btn-primary btn-xs" onclick="joinChk()" style="width:100%">회원가입</button>
				</div>
			</div>

		</div>
	</div>
	
	<form name="joinForm" id="joinForm" action="/joinChk" method="post">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="pwd" value="">
		<input type="hidden" name="pwdChk" value="">
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
	function idChk() {
		var id = $("#id").val();
		
		var regExp = /^[a-z]+[a-z0-9]{6,20}$/g;
		
		if(!regExp.test(id)) {
			$("#msg").text("아이디는 영문, 숫자를 포함하여 6~20자로 작성해 주세요.");
		} else {
			$.ajax({
				type : 'post',
				url : '/idChk',
				dataType : 'text',
				data : {id : id},
				success : function(res) {
					if(res == "Y") {
						$("#msg").text("사용 가능한 아이디 입니다.");
						$("#idChk").val("Y");
					} else {
						$("#msg").text(res);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){ 
                    alert("통신 실패.")
                }
			});
		}
	}
	
	function joinChk() {
		var id = $("#id").val();
		var idChk = $("#idChk").val();
		var pwd = $("#pwd").val();
		var pwdChk = $("#pwdChk").val();
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
		
		if(id == null || id == '') {
			alert("아이디를 입력해 주세요");
			$("#id").focus();
			return false;
		} else if(idChk == null || idChk == '') {
			alert("아이디확인을 해주세요");
			$("#idChk").focus();
			return false;
		} else if(pwd == null || pwd == '') {
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
		} else if(name == null || name == '') {
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
			$('#joinForm [name="id"]').val(id);
			$('#joinForm [name="pwd"]').val(pwd);
			$('#joinForm [name="pwdChk"]').val(pwdChk);
			$('#joinForm [name="name"]').val(name);
			$('#joinForm [name="age"]').val(age);
			$('#joinForm [name="sex"]').val(sex);
			$('#joinForm [name="birth"]').val(birth);
			$('#joinForm [name="post"]').val(post);
			$('#joinForm [name="tel1"]').val(tel1);
			$('#joinForm [name="tel2"]').val(tel2);
			$('#joinForm [name="tel3"]').val(tel3);
			$('#joinForm [name="email1"]').val(email1);
			$('#joinForm [name="email2"]').val(email2);
			$("#joinForm").submit();
		}
	}
</script>
</body>

<c:import url="/footer"/>