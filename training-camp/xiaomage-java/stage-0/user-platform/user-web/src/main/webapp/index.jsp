<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${pageContext.request.contextPath}/assets/images/favicon.ico">
    <title>${pageName} - Java Web Application with Embedded Tomcat</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/vendor/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">Java Web Application</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
    </nav>
</header>
<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <h1 class="display-4">「小马哥的 Java 项目实战营」第 0 期</h1>
        <p class="lead">第一阶段单体项目</p>
    </div>
</div>

<div class="container">

    <div class="alert alert-danger" role="alert"></div>
    <div class="alert alert-success" role="alert"></div>
    <div class="form row jumbotron align-content-center " id="login-div">
        <div class="align-content-center">
            <div class="form-group">
                <h3 class="form-title">Login to your account</h3>
            </div>
            <div class="form-group">
                <i class="fa fa-user fa-lg"></i>
                <input class="form-control required" type="text" placeholder="Username" name="name"
                       autofocus="autofocus" maxlength="20"/>
            </div>
            <div class="form-group">
                <i class="fa fa-lock fa-lg"></i>
                <input class="form-control required" type="password" placeholder="Password" name="password"
                       maxlength="8"/>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary" id="btn-login">Login</button>
            </div>
        </div>
    </div>


    <div class="form row jumbotron align-content-center" id="register-div">
        <div class="align-content-center">
            <div class="form-group">
                <h3 class="form-title">Create your account</h3>
            </div>
            <div class="form-group">
                <i class="fa fa-user fa-lg"></i>
                <input class="form-control required" type="text" placeholder="Username" name="name"
                       autofocus="autofocus" maxlength="20"/>
            </div>
            <div class="form-group">
                <i class="fa fa-lock fa-lg"></i>
                <input class="form-control required" type="password" placeholder="Password" name="password"
                       maxlength="20"/>
            </div>
            <div class="form-group">
                <i class="fa fa-lock fa-lg"></i>
                <input class="form-control required" type="email" placeholder="Email" name="email"
                       maxlength="30"/>
            </div>
            <div class="form-group">
                <i class="fa fa-lock fa-lg"></i>
                <input class="form-control required" type="text" placeholder="Phone Number" name="phoneNumber"
                       maxlength="11"/>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary" id="btn-register">Register</button>
            </div>
        </div>
    </div>
</div>


<%@ include file="/WEB-INF/templates/includes/footer.jsp" %>

<script type="text/javascript">
  $(document).ready(() => {
    $('.alert-success').hide();
    $('.alert-danger').hide();
    $("#login-div #btn-login").click(function () {
      let name = $("#login-div input[name='name']").val();
      let password = $("#login-div input[name='password']").val();

      const formData = new URLSearchParams();
      formData.append('name', name);
      formData.append('password', password);

      fetch('users/login', {
        method: 'post',
        body: formData,
      })
      .then(response => ({status: response.status, data: response.json()}))
      .then(response => {
        if (response.status == 200) {
          response.data.then(value => {
            if (value.status == 500) {
              alertError(value.message);
            } else {
              window.location.href="/main"
              // TODO go to main page
            }
          });
        } else if (response.status == 404) {
          response.data.then(value => {
            alertError(value)
          });
        } else {
          alertError("An unknown error occurred. Please contact Administrator.")
        }
      })
      .catch(error => {
        console.log('Request failed', error);
        alertError("An unknown error occurred. Please contact Administrator.")
      });

      return;

    });

    $("#btn-register").click(function () {

      let name = $("#register-div input[name='name']").val();
      let password = $("#register-div input[name='password']").val();
      let email = $("#register-div input[name='email']").val();
      let phoneNumber = $("#register-div input[name='phoneNumber']").val();

      const formData = new URLSearchParams();
      formData.append('name', name);
      formData.append('password', password);
      formData.append('email', email);
      formData.append('phoneNumber', phoneNumber);

      fetch('users/register', {
        method: 'post',
        body: formData,
      })
      .then(response => ({status: response.status, data: response.json()}))
      .then(response => {
        if (response.status == 200) {
          response.data.then(value => {
            if (value.status == 500) {
              alertError(value.message);
            } else {
              alertSuccess()
            }
          });
        } else if (response.status == 404) {
          response.data.then(value => {
            alertError(value)
          });
        } else {
          alertError("An unknown error occurred. Please contact Administrator.")
        }
      })
      .catch(error => {
        console.log('Request failed', error);
        alertError("An unknown error occurred. Please contact Administrator.")
      });

      return;

    });

  });

  function alertError(error) {
    $('.alert-success').hide();
    $('.alert-success').text("");

    $('.alert-danger').show();
    $('.alert-danger').text(error);
  }

  function alertSuccess(message) {
    $('.alert-danger').hide();
    $('.alert-danger').text("");

    $('.alert-success').show();
    $('.alert-success').text(message || "执行成功!");
  }

  function formatData(data) {
    const result = Object.entries(data).map(([key, value]) => `${key}=${value}`).join('&');
    return result;
  }

</script>