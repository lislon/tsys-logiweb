<%@ page import="com.tsystems.javaschool.logiweb.api.listener.MySessionListener" %>
<%--
  ~ Copyright (c) 2016.
  ~ Igor Avdeev
  --%>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="offcanvas" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<%= request.getContextPath() %>/order/list">Java School #18 - Logiweb</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Dashboard</a></li>
                <li><a href="#">Dashboard 2</a></li>
            </ul>
            <p class="navbar-text navbar-right">
                <%= MySessionListener.getActiveSessions() %> Session(s)
            </p>
        </div>
    </div>
</nav>