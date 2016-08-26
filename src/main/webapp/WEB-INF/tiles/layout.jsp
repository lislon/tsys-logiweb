<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%--
  ~ Copyright (c) 2016.
  ~ Igor Avdeev
  --%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link rel="stylesheet" href="style/bootstrap.min.css">
    <link rel="stylesheet" href="style/bootstrap-table.css">
    <link rel="stylesheet" href="style/main.css">
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="style/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <title><tiles:getAsString name="title" /></title>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery-1.12.3.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-table.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="js/ie10-viewport-bug-workaround.js"></script>
</head>
<body>

<tiles:insertAttribute name="header" />
<div class="container-fluid">
    <div class="row">
        <tiles:insertAttribute name="menu" />

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header"><tiles:getAsString name="title" /></h1>
            <tiles:insertAttribute name="body" />
        </div>

    </div>
</div>
</body>
</html>