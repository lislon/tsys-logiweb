<%@ page import="com.tsystems.javaschool.logiweb.api.helper.UserAlert" %>
<%@ page import="javax.jws.soap.SOAPBinding" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link rel="stylesheet" href="<%= request.getContextPath() %>/vendor/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/vendor/bootstrap-table/dist/bootstrap-table.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/vendor/EasyAutocomplete/dist/easy-autocomplete.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/vendor/EasyAutocomplete/dist/easy-autocomplete.themes.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style/main.css">
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="<%= request.getContextPath() %>/style/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <title><tiles:getAsString name="title" /></title>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<%= request.getContextPath() %>/vendor/jquery/dist/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<%= request.getContextPath() %>/vendor/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="<%= request.getContextPath() %>/vendor/bootstrap-table/dist/bootstrap-table.min.js"></script>
    <script src="<%= request.getContextPath() %>/vendor/EasyAutocomplete/dist/jquery.easy-autocomplete.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="<%= request.getContextPath() %>/js/ie10-viewport-bug-workaround.js"></script>
    <script>
        var CONTEXT_PATH = 'http://' + location.host + "<%= request.getContextPath() %>";
    </script>
</head>
<body>
<%--<%!--%>
    <%--session.get().getAttribute(UserAlert.ATTR_NAME);--%>
<%--%>--%>
<tiles:insertAttribute name="header" />
<div class="container-fluid">
    <div class="row">
        <tiles:insertAttribute name="menu" />
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header"><tiles:getAsString name="title" /></h1>
            <%--<c:if test="${sessionScope.get().getAttribute(UserAlert.ATTR_NAME) != null}">--%>
                <%--<div class="alert alert-<%= session.getAttribute(UserAlert.ATTR_NAME).getType() %>">--%>
                    <%--<%= session.getAttribute(UserAlert.ATTR_NAME) %>--%>
                <%--</div>--%>
            <%--</c:if>--%>
            <tiles:insertAttribute name="body" />
        </div>

    </div>
</div>
</body>
</html>