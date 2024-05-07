<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:import url="/header"/>
<div class="container"> 
	<div class="mem-mypage">
		<dl>
	    	<dt>아이디</dt>
	        <dd>${member.id}</dd>
	    </dl>
		<dl>
	    	<dt>이름</dt>
	        <dd>${member.name}</dd>
	    </dl>
	    <dl>
	    	<dt>생년월일</dt>
	        <dd>${member.birth}</dd>
	    </dl>
	    <dl>
	    	<dt>나이</dt>
	        <dd>${member.age}</dd>
	    </dl>
	    <dl>
	    	<dt>휴대번호</dt>
	        <dd>${member.tel}</dd>
	    </dl>
	    <dl>
	    	<dt>성별</dt>
	        <dd>
	        	<c:choose>
	        		<c:when test="${member.sex == 'M'}">
	        			남
	        		</c:when>
	        		
	        		<c:when test="${member.sex == 'W'}">
	        			여
	        		</c:when>
	        	</c:choose>
	        </dd>
	    </dl>
		<dl>
	    	<dt>회원가입일</dt>
	        <dd>${member.reg_date}</dd>
	    </dl>
	    
		<button class="btn btn-outline-dark" onclick="location.href='/modUser'">
			정보수정
		</button>
		
		<button class="btn btn-outline-dark" onclick="location.href='/modPwd'">
			비밀번호 수정
		</button>
		
		<button class="btn btn-outline-dark" onclick="location.href='/delUserChk'">
			회원 탈퇴
		</button>
	</div>
</div>

<c:import url="/footer"/>