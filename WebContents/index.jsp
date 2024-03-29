<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="id" value="${sessionScope.id}" />
<c:set var="name" value="${sessionScope.name}" />

<c:url var="loginUrl" value="/login.jsp" />
<c:url var="logoutUrl" value="/member/logout.do" />
<c:url var="signupUrl" value="/signup.jsp" />
<c:url var="writeBlogUrl" value="/writeBlog.jsp" />
<c:url var="blogListUrl" value="/blog/blogList.do" />
<c:url var="myBlogList" value="/blog/myBlogList.do" />
<c:url var="memberList" value="/member/memberList.do" />

<jsp:include page="header.jsp"></jsp:include>

<!DOCTYPE html>
<html>

<head>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	function good() {
		$.ajax({
			url: "good",
			type: "post",
			async: false,
			success: function()
		});
	}
</script>
    
<meta charset="UTF-8">
<link rel="icon" href="./image/favicon.png" type="image/x-icon">
<title>Main page</title>
</head>

<body>
	<!-- 로그인이 안되어 있을 때 -->
	<c:if test="${empty id}">
		<a href="${loginUrl}">로그인</a>
		<a href="${signupUrl}">회원가입</a><br>
		<a href="${blogListUrl}">블로그 목록</a>
	</c:if>
	
	<!-- 로그인이 되어 있을 때 -->
	<c:if test="${not empty id}">
        <h3>${name}님 환영합니다.<br></h3>
        <a href="${writeBlogUrl}">글쓰기</a>
		<a href="${logoutUrl}">로그아웃</a><br>
		<a href="${blogListUrl}">블로그 목록</a>
		<a href="${myBlogList}">내 블로그 목록</a><br>
		
		<!-- 관리자일 때 -->
		<c:if test="${id == 'admin'}">
			<a href="${memberList}">회원 목록</a>
		</c:if>
	</c:if>
	<br>
	
	<img src="image/daug.png" alt="daug" width="700px"/><br>
	<button onclick="good()">개추</button> <span id="goodCount">0</span>
</body>
</html>
