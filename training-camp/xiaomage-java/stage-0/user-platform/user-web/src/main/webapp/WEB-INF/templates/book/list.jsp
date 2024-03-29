<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/templates/includes/header.jsp" %>
<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <h1 class="display-4">「小马哥的 Java 项目实战营」第 0 期</h1>
        <p class="lead">第一阶段单体项目</p>
    </div>
</div>
<main>
    <div class="container">
        <div class="row">
            <div class="col">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Title</th>
                        <th scope="col">Edition</th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="book" items="${books}">
                        <tr>
                            <th scope="row">${book.id}</th>
                            <td>${book.title}</td>
                            <td>${book.edition}</td>
                            <td>
                                <div class="btn-group" role="group" aria-label="Basic example">
                                    <button type="button" class="btn btn-info" data-toggle="modal"
                                            data-target="#commonModal" data-book-action="viewBook"
                                            data-book-id="${book.id}">View
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>
<%@ include file="/WEB-INF/templates/components/commonModal.jsp" %>
<%@ include file="/WEB-INF/templates/components/viewBookTable.jsp" %>
<%@ include file="/WEB-INF/templates/includes/footer.jsp" %>
<script src="${pageContext.request.contextPath}/assets/js/book/book.js"></script>