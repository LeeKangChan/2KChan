<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/header"/>
<body class="text-center">
<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">상품 등록 </h1>
		</div>
	</div>
	
	<div class="container">
		<form id="productRegForm" action="/productRegAction" method="post" enctype="multipart/form-data">	
			<div class="form-group row">
				<label class="col-sm-2">상품명</label>
				<div class="col-sm-3">
					<input type="text" id="name" name="name" class="form-control">
				</div>
			</div>
		
			<div class="form-group row">
				<label class="col-sm-2">가격</label>
				<div class="col-sm-3">
					<input type="text" id="price" name="price" class="form-control">
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">상세 정보</label>
				<div class="col-sm-3">
					<textarea id="description" name="description" cols="50" rows="2"></textarea>
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">분류</label>
				<div class="col-sm-3">
					<input type="text" id="category" name="category" class="form-control">
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">상태</label>
				<div class="col-sm-5">
					<input type="radio" name="status" id="statusNew" value="New " >
					신규 제품
					<input type="radio" name="status" id="statusOld" value="Old " >
					중고 제품
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">상품 이미지</label>
				<div class="col-sm-3">
					<input type="file" id="img1" name="img" class="form-control">
					<input type="file" id="img2" name="img" class="form-control">
					<input type="file" id="img3" name="img" class="form-control">
				</div>
			</div>
			
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10">
					<input type=button class="btn btn-primary" value="등록" onclick="productRegChk()">
				</div>
			</div>
		</form>
	</div>	
</body>

<script>
	function productRegChk() {
		var name = $("#name").val();
		var price = $("#price").val();
		var description = $("#description").val();
		var category = $("#category").val();
		var status = $('input[name="status"]:checked').val();
		
		if(name == null || name == '') {
			alert("상품명을 입력해 주세요");
			$("#name").focus();
			return false;
		} else if(price == null || price == '') {
			alert("가격을 입력해 주세요");
			$("#price").focus();
			return false;
		} else if(description == null || description == '') {
			alert("상품 상세정보를 입력해 주세요");
			$("#description").focus();
			return false;
		} else if(status == null || status == '') {
			alert("상품 상태를 선택해 주세요");
			$("#status").focus();
			return false;
		} else {
			$("#productRegForm").submit();
		}
	}
</script>

<c:import url="/footer"/>

