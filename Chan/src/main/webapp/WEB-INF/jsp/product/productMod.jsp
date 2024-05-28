<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/header"/>
<body class="text-center">
<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">상품 수정 </h1>
		</div>
	</div>
	
	<div class="container">
		<form id="productModForm" action="/productModAction" method="post" enctype="multipart/form-data">
			<input type="hidden" name="productId" value="${product.id}">	
			<input type="hidden" name="fileNum" value="${product.fileNum}">
			<div class="form-group row">
				<label class="col-sm-2">상품명</label>
				<div class="col-sm-3">
					<input type="text" id="name" name="name" class="form-control" value="${product.name}">
				</div>
			</div>
		
			<div class="form-group row">
				<label class="col-sm-2">가격</label>
				<div class="col-sm-3">
					<input type="text" id="price" name="price" class="form-control" value="${product.price}">
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">상세 정보</label>
				<div class="col-sm-3">
					<textarea id="description" name="description" cols="50" rows="2">${product.description}</textarea>
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">분류</label>
				<div class="col-sm-3">
					<input type="text" id="category" name="category" class="form-control" value="${product.category}">
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">상태</label>
				<div class="col-sm-5">
					<input type="radio" name="status" id="statusNew" value="new" <c:if test="${product.status == 'new'}">checked</c:if> >
					신규 제품
					<input type="radio" name="status" id="statusOld" value="old" <c:if test="${product.status == 'old'}">checked</c:if> >
					중고 제품
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">상품 이미지</label>
				<div class="col-sm-3">
					<c:forEach var="i" begin="0" end="2" step="1">
						<p>
							<input type="file" name="img" class="form-control">
							<c:if test="${not empty fileList[i]}">
								<input type="hidden" name="fileId_${i}" value="${fileList[i].id}">
								 	현재 파일 : <a href="${path}" target='_blank'>${fileList[i].orgFileName}</a>
									삭제 <input type="checkbox" name="delFile_${i}" value="y">
							</c:if>
						</p> 
					</c:forEach>
				</div>
			</div>
			
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10">
					<input type=button class="btn btn-primary" value="수정" onclick="productModChk()">
				</div>
			</div>
		</form>
	</div>	
</body>

<script>
	function delFile(id, count) {
		if(confirm("해당 파일을 정말 삭제 하시겠습니까?\n (삭제된 파일은 다시 복구할 수 없습니다.)")) {
			$.ajax({
				type : 'post',
				url : '/delFile',
				dataType : 'text',
				data : {id : id},
				success : function(res) {
					if(res == "Y") {
						alert("파일이 삭제 되었습니다.");
						$("#file_"+count).remove();
					} else {
						alert(res);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){ 
                    alert("통신 실패.")
                }
			});
		}
	}

	function productModChk() {
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
			$("#productModForm").submit();
		}
	}
</script>

<c:import url="/footer"/>

