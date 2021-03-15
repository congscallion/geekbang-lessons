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
<div class="jumbotron">
    <div class="container">
        <h1 class="display-4">「小马哥的 Java 项目实战营」第 0 期</h1>
        <p class="lead">第一阶段单体项目</p>
    </div>
</div>

<div class="m-auto h-auto" style="display: block; width: 500px;">
    <nav>
        <div class="nav nav-tabs" id="nav-tab" role="tablist">
            <a class="nav-link active" id="nav-home-tab" data-toggle="tab" href="#login-div" role="tab"
               aria-controls="nav-home" aria-selected="true">登录</a>
            <a class="nav-link" id="nav-profile-tab" data-toggle="tab" href="#register-div" role="tab"
               aria-controls="nav-profile" aria-selected="false">注册</a>
        </div>
    </nav>
    <div class="tab-content" id="nav-tabContent">

        <!-- login from -->
        <div class="tab-pane fade show active" id="login-div" role="tabpanel" aria-labelledby="nav-home-tab">
            <div class="form-group">
                <label for="loginUserNameInput">User Name</label>
                <input id="loginUserNameInput" class="form-control required" type="text" name="name"
                       autofocus="autofocus" maxlength="20">
            </div>
            <div class="form-group">
                <label for="loginPasswordInput">Password</label>
                <input id="loginPasswordInput" class="form-control required" type="password" name="password"
                       maxlength="8"/>
            </div>
            <div class="alert alert-warning alert-dismissible" id="login-message">
                This alert box could indicate a successful or positive action.
            </div>
            <button type="submit" class="btn btn-primary" id="btn-login">Login</button>
        </div>

        <!-- register from -->
        <div class="tab-pane fade" id="register-div" role="tabpanel" aria-labelledby="nav-profile-tab">
            <div class="form-group">
                <label for="registerName">User Name</label>
                <input id="registerName" class="form-control required" type="text" name="name"
                       autofocus="autofocus" maxlength="20"/>
            </div>
            <div class="form-group">
                <label for="registerPassword">Password</label>
                <input id="registerPassword" class="form-control required" type="password" name="password"
                       maxlength="20"/>
            </div>
            <div class="form-group">
                <label for="registerEmail">Email</label>
                <input id="registerEmail" class="form-control required" type="email" name="email"
                       maxlength="30"/>
            </div>
            <div class="form-group">
                <label for="registerPhone">Phone Number</label>
                <input id="registerPhone" class="form-control required" type="text" name="phoneNumber"
                       maxlength="11"/>
            </div>
            <div class="alert alert-warning alert-dismissible" id="register-message">
            </div>
            <div class="alert alert-success alert-dismissible" id="re-success-message">
            </div>
            <button type="submit" class="btn btn-primary" id="btn-register">Register</button>
        </div>
    </div>
</div>


<%@ include file="/WEB-INF/templates/includes/footer.jsp" %>

<script type="text/javascript">
  $(document).ready(() => {

    $("#login-message").hide();
    $("#register-message").hide();
    $("#re-success-message").hide();
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
              alertError(value);
            } else {
              window.location.href = "/main"
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
              alertError(value);
            } else {
              alertSuccess()
            }
          });
        } else if (response.status == 404) {
          response.data.then(value => {
            alertError(value);
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
    let aa = $("a.active").attr('href');
    if ('#login-div' === aa) {
      $("#login-message").text(error.message);
      $("#login-message").show();
    } else {

      $("#re-success-message").text("");
      $("#re-success-message").hide();
      $("#register-message").text(error.message);
      $("#register-message").show();
    }

  }

  function alertSuccess(message) {
    $("#register-message").text("")
    $("#register-message").hide();
    $("#re-success-message").text("注册成功，你可以使用该账户登录了!");
    $("#re-success-message").show();
  }


</script>