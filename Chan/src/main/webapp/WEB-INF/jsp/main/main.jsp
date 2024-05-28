<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<c:import url="/header"/>

<body>
    <!-- Header-->
    <header class="bg-dark py-5">
        <div class="container px-4 px-lg-5 my-5">
            <div class="text-center text-white">
                <h1 class="display-4 fw-bolder">Shop in style</h1>
                <p class="lead fw-normal text-white-50 mb-0">With this shop hompeage template</p>
            </div>
        </div>
    </header>
    <!-- Section-->
    <section class="py-5">
        <div class="container px-4 px-lg-5 mt-5">
        	<form name="searchForm" method="post" action="/main">
        		<select name="searchType">
        			<option value="" selected>검색</option>
	        		<option value="name" <c:if test="${searchType == 'name'}">selected</c:if> >상품명</option>
	        		<option value="description" <c:if test="${searchType == 'description'}">selected</c:if> >상품상세 내용</option>
	        		<option value="category" <c:if test="${searchType == 'category'}">selected</c:if> >분류</option>
	        	</select>
	        	
	        	<input type="text" name="searchContent" value="${searchContent}">
	        	
	        	<button type="submit">검색</button>
        	</form>

        	<h1>등록된 상품</h1>
            <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
            	<c:forEach var="product" items="${productList}" varStatus="i">
            		<div class="col mb-5">
	                    <div class="card h-100">	                    	
	                    	<c:choose>
	                    		<c:when test="${product.fileYN == 'Y'}">
	                    			<img class="card-img-top" src="${product.fileList[0].filePath}" alt="${product.fileList[0].orgFileName}" width="268px" height="201px"/>	
	                    		</c:when>
	                    		
	                    		<c:otherwise>
	                    			<img class="card-img-top" src="/uploadFile/noImage.jpg" alt="noImage" />
	                    		</c:otherwise>
	                    	</c:choose>
	                        
	                        <div class="card-body p-4">
	                            <div class="text-center">
	                                <h5 class="fw-bolder">${product.name}</h5>
	                                ${product.price}
	                            </div>
	                        </div>
	                        <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
	                            <div class="text-center">
	                            	<button class="btn btn-outline-dark mt-auto" onclick="productDetail('${product.id}')">상품 보러 가기</button>
	                            </div>
	                        </div>
	                    </div>
	                </div>
            	</c:forEach>
            	
            	<c:if test="${fn:length(productList) == 0}">
            		등록된 상품이 없습니다.
            	</c:if>
            	
            	
            </div>
            
            <nav>
				<ul class="pagination" style="text-align:center;">
					<li class="page-item">
						<a class="page-link" href="/main?pageNum=${(endPage - countPage)}&searchType=${searchType}&searchContent=${searchContent}" aria-label="Previous">
			        		<span aria-hidden="true">&laquo;</span>
			        	</a>
					</li>
			
			    	<c:forEach var="i" begin="${startPage}" end="${endPage}">
			        	<li class="page-item"><a class="page-link" href="/main?pageNum=${i-1}&searchType=${searchType}&searchContent=${searchContent}">${i}</a></li>
			    	</c:forEach>
			
			    	<li class="page-item">
			        	<a class="page-link" href="/main?pageNum=${startPage + countPage}&searchType=${searchType}&searchContent=${searchContent}" aria-label="Next">
			            	<span aria-hidden="true">&raquo;</span>
			        	</a>
			    	</li>
				</ul>
			</nav>
        </div>
    </section>
    </body>
    
    <form name="productDetailForm" id="productDetailForm" action="/productDetail" method="post">
    	<input type="hidden" name="id" value="">
    </form>
    
    <script>
    	function productDetail(id) {
    		if(id != null && id != "") {
    			$('#productDetailForm [name="id"]').val(id);
    			$('#productDetailForm').submit();
    		} else {
    			alert("상품이 존재하지 않습니다.");
    		}
    	}
    </script>
    
    <c:import url="/footer"/>