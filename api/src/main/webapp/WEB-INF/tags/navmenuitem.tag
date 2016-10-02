<%--
  ~ Copyright (c) 2016.
  ~ Igor Avdeev
  ~ Menu item title, which become active when page is same as href
  --%>
<%@tag description="Navigation menu item" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="url" required="true" type="java.lang.String" description="Relative url from project root starting with /" %>
<%@attribute name="highlight" required="true" type="java.lang.String" description="Menu section to check against url to highlight" %>
<%@attribute name="title" required="true" type="java.lang.String" %>
<%@attribute name="faIcon" required="false" type="java.lang.String" %>

<c:set var="isActive" value="${fn:startsWith(requestScope.origin, highlight)}"/>

<c:choose>
    <c:when test="${isActive}">
        <li class="active">
            <a href="<%= request.getContextPath() %>${url}">
                <i class="fa ${faIcon}"></i> <span>${title}</span>
            </a>
        </li>
    </c:when>
    <c:otherwise>
        <li>
            <a href="<%= request.getContextPath() %>${url}">
                <i class="fa ${faIcon}"></i> <span>${title}</span>
            </a>
        </li>
    </c:otherwise>
</c:choose>