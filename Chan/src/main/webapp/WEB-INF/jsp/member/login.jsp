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

			<!-- 로그인 페이지 타이틀 -->
			<div id="loginBoxTitle">Login</div>
			<!-- 아이디, 비번, 버튼 박스 -->
			<div id="inputBox">
				
				
				<div class="input-form-box">
					<span>아이디 </span><input type="text" id="id" class="form-control">
				</div>
				
				<div class="input-form-box">
					<span>비밀번호 </span><input type="password" id="pwd" class="form-control">
				</div>
				
				<div class="button-login-box">
					<button type="button" class="btn btn-primary btn-xs" onclick="loginChk()" style="width:100%">로그인</button>
				</div>
				
				<div class="button-login-box">
					<button type="button" class="btn btn-primary btn-xs" onclick="location.href='/join'" style="width: 100%">회원가입</button>
				</div>
				
				<div class="button-login-box">
					<button type="button" class="btn btn-primary btn-xs"
						style="width: 100%">아이디 찾기</button>
				</div>
				
				<div class="button-login-box">
					<button type="button" class="btn btn-primary btn-xs"
						style="width: 100%;">비밀번호 찾기</button>
				</div>
			</div>

		</div>
	</div>
	
	<form name="loginForm" id="loginForm" action="/loginChk" method="post">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="pwd" value="">
		<input type="hidden" name="url" valye="${url}">
	</form>

	<!-- Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
		crossorigin="anonymous"></script>
		
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
</html>

