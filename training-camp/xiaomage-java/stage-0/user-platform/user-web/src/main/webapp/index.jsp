<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/templates/includes/header.jsp">
    <jsp:param value="Home" name="pageName"/>
</jsp:include>
<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <h1 class="display-4">「小马哥的 Java 项目实战营」第 0 期</h1>
        <p class="lead">第一阶段单体项目</p>
        <a type="button" class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/books" alt="Books">Books</a>
        <a type="button" class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/users" alt="Users">Users</a>
    </div>
</div>
<%@ include file="/WEB-INF/templates/includes/footer.jsp" %>