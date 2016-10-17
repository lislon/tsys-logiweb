<%@ tag trimDirectiveWhitespaces="true" %>
<%@tag description="Source of user image" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="userpicSrc" value="${pageContext.request.contextPath}/dist/img/courier.png" />
<sec:authorize access="hasRole('ROLE_MANAGER')">
    <c:set var="userpicSrc" value="${pageContext.request.contextPath}/dist/img/manager.png" />
</sec:authorize>
${userpicSrc}