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
                        <th scope="col">Name</th>
                        <th scope="col">Email</th>
                        <th scope="col">PhoneNumber</th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <th scope="row">${user.id}</th>
                            <td>${user.name}</td>
                            <td>${user.email}</td>
                            <td>${user.phoneNumber}</td>
                            <td>
                                <div class="btn-group" role="group" aria-label="Basic example">
                                    <button type="button" class="btn btn-info" data-toggle="modal"
                                            data-target="#commonModal" data-user-action="viewUser"
                                            data-user-id="${user.id}">View
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
<%@ include file="/WEB-INF/templates/components/viewUserTable.jsp" %>
<%@ include file="/WEB-INF/templates/includes/footer.jsp" %>
<script src="${pageContext.request.contextPath}/assets/js/user/user.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
