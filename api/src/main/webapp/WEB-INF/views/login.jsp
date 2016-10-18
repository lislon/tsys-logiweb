<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="logiweb" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Logiweb | Log in</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/vendor/bootstrap/css/bootstrap.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/vendor/admin-lte/css/AdminLTE.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/vendor/admin-lte/css/skins/skin-blue.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/vendor/icheck/skins/square/blue.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a href=${pageContext.request.contextPath}><b>Logiweb</b> (Yet Another)</a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body"><link rel="stylesheet" href="${pageContext.request.contextPath}/dist/vendor/bootstrap/css/bootstrap.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/vendor/easy-autocomplete/easy-autocomplete.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/vendor/easy-autocomplete/easy-autocomplete.themes.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/vendor/bootstrap-table/bootstrap-table.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/vendor/admin-lte/css/AdminLTE.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/vendor/admin-lte/css/skins/skin-blue.min.css">
        <p class="login-box-msg">Sign in to start your session</p>

        <form method="post">
            <div class="form-group has-feedback ${param.error == true ? "has-error" : ""}">
                <input type="email" class="form-control" placeholder="Email" name="username" value="admin@test.ru" >
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback ${param.error == true ? "has-error" : ""}">
                <input type="password" class="form-control" placeholder="Password" name="password" value="admin@test.ru">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                <c:if test="${param.error == true}">
                    <span class="help-block">
                        Invalid mail and password.
                    </span>
                </c:if>
            </div>
            <div class="row">
                <%--<div class="col-xs-8">--%>
                    <%--<div class="checkbox icheck">--%>
                        <%--<label>--%>
                            <%--<input type="checkbox"> Remember Me--%>
                        <%--</label>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <!-- /.col -->
                <div class="col-xs-4">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
                </div>
                <!-- /.col -->
            </div>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        </form>

        <%--<a href="#">I forgot my password</a><br>--%>

    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<script src="${pageContext.request.contextPath}/dist/vendor/jquery/jquery.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${pageContext.request.contextPath}/dist/vendor/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="${pageContext.request.contextPath}/dist/vendor/icheck/icheck.min.js"></script>
<script>
    $(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });
</script>
</body>
</html>
