<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Bind is required for errors for errors -->
<spring:bind path="${param.name}">
    <div class="form-group ${status.error ? 'has-error' : ''}">
        <label for="${param.name}">${param.title}</label>
        <form:input path="${param.name}" type="text" class="form-control" id="${param.name}" placeholder="${param.title}" />
        <form:errors path="${param.name}" class="help-block" />
    </div>
</spring:bind>