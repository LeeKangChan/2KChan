<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript" src="//code.jquery.com/jquery-3.4.0.min.js"></script>
<title>로그인</title>


<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

<!-- 나의 스타일 추가 -->
<link rel="stylesheet" href="css/login.css?v=1234">

</head>
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
					<button type="button" class="btn btn-primary btn-xs" onclick="idChk()" style="width:100%">아이디확인</button>
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
					<input type="number" id="age" class="form-control">
				</div>
				
				<div class="input-form-box">
					<span>성별</span>
					남 <input type="radio" id="sex" value="M">
					여 <input type="radio" id="sex" value="F">
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
					<input type="number" id="tel1" class="form-control" maxlength="3">
					-
					<input type="number" id="tel2" class="form-control" maxlength="4">
					-
					<input type="number" id="tel3" class="form-control" maxlength="4">
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

	<!-- Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
		crossorigin="anonymous"></script>
		
	<script>
	function idChk() {
		var id = $("#id").val();
		
		var regExp = /^[a-z]+[a-z0-9]{5,19}$/g;
		
		if(!regExp.test(id)) {
			$("#msg").text("아이디는 영문, 숫자를 포함하여 8~16자로 작성해 주세요.");
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
						$("#msg").text("중복된 아이디 입니다.");
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
		var pwd = $("#pwd").val();
		var pwdChk = $("#pwdChk").val();
		var name = $("#name").val();
		var age = $("#age").val();
		var sex = $("#sex").val();
		var birth = $("#birth").val();
		var post = $("#post").val();
		var tel1 = $("#tel1").val();
		var tel2 = $("#tel2").val();
		var tel3 = $("#tel3").val();
		var email1 = $("#email1").val();
		var email2 = $("#email2").val();
		
		
		if(id == null || id == '') {
			alert("아이디를 입력해 주세요");
			$("#id").focus();
			return false;
		} else if(pwd == null || pwd == '') {
			alert("비밀번호를 입력해 주세요");
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
</html>

