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

<c:choose>
    <c:when test="${fn:startsWith(requestScope.origin, highlight)}">
        <li class="active">
            <a href="<%= request.getContextPath() %>${url}">
                    ${title} <span class="sr-only">(current)</span>
            </a>
        </li>
    </c:when>
    <c:otherwise>
        <li>
            <a href="<%= request.getContextPath() %>${url}">
                    ${title}
            </a>
        </li>
    </c:otherwise>
</c:choose>


