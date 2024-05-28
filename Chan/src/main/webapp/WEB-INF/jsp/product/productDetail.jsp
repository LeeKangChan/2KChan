<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="/header"/>
<body class="text-center">
<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">상품 상세</h1>
		</div>
	</div>
	
	<div class="container">
		<form id="productRegForm" action="/productRegAction" method="post" enctype="multipart/form-data">
			<div class="form-group row">
				<label class="col-sm-2">등록자</label>
				<div class="col-sm-3">
					${product.regName}
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">등록일</label>
				<div class="col-sm-3">
					${product.regDate}
				</div>
			</div>
		
			<div class="form-group row">
				<label class="col-sm-2">상품명</label>
				<div class="col-sm-3">
					${product.name}
				</div>
			</div>
		
			<div class="form-group row">
				<label class="col-sm-2">가격</label>
				<div class="col-sm-3">
					${product.price}
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">상세 정보</label>
				<div class="col-sm-3">
					${product.description}
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">분류</label>
				<div class="col-sm-3">
					${product.category}
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">상태</label>
				<div class="col-sm-3">				
					<c:choose>
						<c:when test="${product.status == 'new'}">
							신규 제품
						</c:when>
						
						<c:when test="${product.status == 'old'}">
							중고 제품
						</c:when>
					</c:choose>
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">상품 이미지</label>
				<div class="col-sm-3">					
					<c:if test="${product.fileYN == 'Y'}">
						<c:forEach var="file" items="${fileList}" varStatus="i">
							<img class="card-img-top" src="${file.filePath}" alt="${file.orgFileName}" />
						</c:forEach>
					</c:if>
				</div>
			</div>
			
			
			<div class="container">
				<div class="row">
					<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
						<tbody>
							<tr>
								<td align="left" bgcolor="beige">댓글</td>
							</tr>
							
							<tr>
								<c:forEach var="reply" items="${replyList}" varStatus="i">
									<div class="container">
										<div class="row">
											<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
												<tbody>
													<tr>						
														<td align="left">
															${reply.regName} <%-- (${reply.regId}) --%> <c:if test="${product.regId == reply.regId}">(작성자)</c:if> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${reply.regDate}
														</td>		
														<td colspan="2"></td>
														<td align="right" id="button_${reply.id}">
															<c:if test="${not empty member && reply.regId == member.id}">
																<a type="button" onclick="replyMod('${reply.id}')" class="btn-primary">수정</a>
																<a type="button" onclick="replyDel('${reply.id}')" class="btn-primary">삭제</a>	
															</c:if>	
														</td>
													</tr>
													
													<tr>
														<td colspan="5" align="left" id="content_${reply.id}">
															${reply.content}
														</td>
														
														<td colspan="5" align="left" id="modInput_${reply.id}" style="display:none;">
															<p>
																<input type="text" style="height:100px;" class="form-control" placeholder="상대방을 존중하는 댓글을 남깁시다." value="${reply.content}" id="contentMod_${reply.id}">
															</p>
														</td>
														
														<td id="modButton_${reply.id}" style="display:none;">
															<br><br>
															<input type="button" class="btn-primary pull" onclick="modProductReply('${reply.id}')" value="댓글 수정">
															<input type="button" class="btn-primary pull" onclick="cancelModReply('${reply.id}')" value="수정 취소">
														</td>
													</tr>
												</tbody>
											</table>			
										</div>
									</div>
								</c:forEach>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			
			<c:if test="${not empty member}">
				<div class="container">
					<div class="form-group">
						<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
							<tr>
								<td style="border-bottom:none;" valign="middle">
									<br>
									<br>
									${member.name} (${member.id})
								</td>
								
								<td>
									<input type="text" style="height:100px;" class="form-control" placeholder="상대방을 존중하는 댓글을 남깁시다." id="content">
								</td>
								
								<td>
									<br><br>
									<input type="button" class="btn-primary pull" onclick="regProductReply()" value="댓글 작성">
								</td>
							</tr>
						</table>
					</div>
				</div>
			</c:if>
			
			
			<c:if test="${product.regId == member.id}">
				<div class="form-group row">
					<div class="col-sm-offset-2 col-sm-10">
						<input type=button class="btn btn-primary" value="수정" onclick="modProduct()">
						<input type=button class="btn btn-primary" value="삭제" onclick="delProduct()">
					</div>
				</div>
			</c:if>

		</form>
	</div>
</body>

<form name="modProductForm" id="modProductForm" method="post" action="/productMod">
	<input type="hidden" name="productId" value="${product.id}">
	<input type="hidden" name="memberId" value="${member.id}">
</form>

<form name="delProductForm" id="delProductForm" method="post" action="/productDel">
	<input type="hidden" name="productId" value="${product.id}">
	<input type="hidden" name="memberId" value="${member.id}">
</form>

<form name="regProductReplyForm" id="regProductReplyForm" method="post" action="/regProductReply">
	<input type="hidden" name="productId" value="${product.id}">
	<input type="hidden" name="content" value="">
</form>

<form name="modProductReplyForm" id="modProductReplyForm" method="post" action="/modProductReply">
	<input type="hidden" name="productId" value="${product.id}">
	<input type="hidden" name="memberId" value="${member.id}">
	<input type="hidden" name="replyId" value="">
	<input type="hidden" name="content" value="">
</form>

<form name="delProductReplyForm" id="delProductReplyForm" method="post" action="/delProductReply">
	<input type="hidden" name="productId" value="${product.id}">
	<input type="hidden" name="memberId" value="${member.id}">
	<input type="hidden" name="replyId" value="">
</form>

<script>
	function modProduct() {
		
		$("#modProductForm").submit();
	}
	
	function delProduct() {
		if(confirm("정말 삭제 하시겠습니까?")) {
			$("#delProductForm").submit();
		}
		
	}

	function regProductReply() {
		var content = $("#content").val();
		
		if(content == null || content == "") {
			alert("댓글 내용을 입력해 주세요");
			$("#content").focus();
		} else {
			$('#regProductReplyForm [name="content"]').val(content);
			$("#regProductReplyForm").submit();
		}
	}
	
	function replyMod(id) {
		if(id == null || id == "") {
			alert("댓글 수정 오류! 담당자 문의 바랍니다.");
			return false;
		}
		
		$("#content_"+id).hide();
		$("#button_"+id).hide();
		
		$("#modInput_"+id).show();
		$("#modButton_"+id).show();
	}
	
	function modProductReply(id) {
		if(id == null || id == "") {
			alert("댓글 수정 오류! 담당자 문의 바랍니다.");
			return false;
		}
		
		var content = $("#contentMod_"+id).val();
		
		if(content == null || content == "") {
			alert("댓글 내용을 입력해 주세요");
			$("#contentMod_"+id).focus();
		} else {
			$('#modProductReplyForm [name="content"]').val(content);
			$('#modProductReplyForm [name="replyId"]').val(id);
			$("#modProductReplyForm").submit();
		}
	}
	
	function cancelModReply(id) {
		if(id == null || id == "") {
			alert("댓글 수정 취소 오류! 담당자 문의 바랍니다.");
			return false;
		}
		
		$("#content_"+id).show();
		$("#button_"+id).show();
		
		$("#modInput_"+id).hide();
		$("#modButton_"+id).hide();
	}
	
	function replyDel(id) {
		if(id == null || id == "") {
			alert("댓글 삭제 오류! 담당자 문의 바랍니다.");
			return false;
		}
		
		if(confirm("정말 삭제하시겠습니까?")) {
			$('#delProductReplyForm [name="replyId"]').val(id);
			$("#delProductReplyForm").submit();
		}
	}

</script>

<c:import url="/footer"/>

