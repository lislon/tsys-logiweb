<%@ page import="com.tsystems.javaschool.logiweb.api.helper.UserAlert" %>
<%@ page import="javax.jws.soap.SOAPBinding" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link rel="stylesheet" href="<%= request.getContextPath() %>/vendor/bootstrap/dist/css/bootstrap.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/vendor/bootstrap-table/dist/bootstrap-table.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/vendor/EasyAutocomplete/dist/easy-autocomplete.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/vendor/EasyAutocomplete/dist/easy-autocomplete.themes.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/vendor/bootstrap-select/dist/css/bootstrap-select.css" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style/main.css">
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="<%= request.getContextPath() %>/style/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <title><tiles:getAsString name="title" /></title>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<%= request.getContextPath() %>/vendor/jquery/dist/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<%= request.getContextPath() %>/vendor/bootstrap/dist/js/bootstrap.js"></script>
    <script src="<%= request.getContextPath() %>/vendor/bootstrap-table/dist/bootstrap-table.js"></script>
    <script src="<%= request.getContextPath() %>/vendor/bootstrap-validator/dist/validator.js"></script>
    <script src="<%= request.getContextPath() %>/vendor/EasyAutocomplete/dist/jquery.easy-autocomplete.js"></script>
    <script src="<%= request.getContextPath() %>/vendor/handlebars/handlebars.js"></script>
    <script src="<%= request.getContextPath() %>/vendor/underscore/underscore.js"></script>
    <script src="<%= request.getContextPath() %>/vendor/backbone/backbone.js"></script>
    <script src="<%= request.getContextPath() %>/vendor/jquery-ui/jquery-ui.js"></script>
    <script src="<%= request.getContextPath() %>/vendor/bootstrap-select/dist/js/bootstrap-select.js"></script>

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
            <c:if test="${requestScope.alert != null}">
                <div class="alert alert-${fn:toLowerCase(requestScope.alert.getType())}">
                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                        ${requestScope.alert.getMessage()}
                </div>
            </c:if>

            <c:if test="${sessionScope.alert != null}">
                <div class="alert alert-${fn:toLowerCase(sessionScope.alert.getType())}">
                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                        ${sessionScope.alert.getMessage()}
                </div>
            </c:if>

            <tiles:insertAttribute name="body" />
        </div>

    </div>
</div>
</body>
</html>