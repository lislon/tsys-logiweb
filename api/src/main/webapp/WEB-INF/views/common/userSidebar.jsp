
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags/" %>

<!-- Sidebar user panel (optional) -->
<div class="user-panel">
    <div class="pull-left image">
        <c:set var="userpicSrc" value="${pageContext.request.contextPath}/dist/img/courier.png" />
        <sec:authorize access="hasRole('ROLE_MANAGER')">
            <c:set var="userpicSrc" value="${pageContext.request.contextPath}/dist/img/manager.svg" />
        </sec:authorize>
        <img src="<m:userPicSrc />" height="160" width="160" class="img-circle" alt="User Image">
    </div>
    <div class="pull-left info">
        <p><c:out value="${sessionScope.userTitle}" /></p>
        <!-- Status -->
        <a href="#"><i class="fa fa-circle text-success"></i> <c:out value="${sessionScope.userStatus}" /></a>
    </div>
</div>

<%--<sec:authorize access="hasRole('ROLE_DRIVER')">--%>
    <%--<ul class="sidebar-menu">--%>
        <%--<li class="header">Hours worked</li>--%>
        <%--<li class="text-center">--%>
            <%--<div class="pad">--%>
                <%--<input type="text" value="75" class="driver-hours-chart"--%>
                            <%--data-thickness="0.2"--%>
                            <%--min="0"--%>
                            <%--max="176"--%>
                            <%--data-skin="tron"--%>
                            <%--data-width="60"--%>
                            <%--data-height="60"--%>
                            <%--data-readOnly="true">--%>
            <%--</div>--%>
        <%--</li>--%>
    <%--</ul>--%>
 <%--</sec:authorize>--%>

