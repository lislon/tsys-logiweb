<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="Add driver"></c:set>
<c:set var="buttonText" value="Create driver"></c:set>

<%--<c:choose>--%>
    <%--<c:when test="${formAction == 'new'}">--%>
        <%--<c:set var="title" value="Add driver"></c:set>--%>
        <%--<c:set var="buttonText" value="Create driver"></c:set>--%>
    <%--</c:when>--%>
    <%--<c:when test="${formAction == 'edit'}">--%>
        <%--<c:set var="title" value="Edit driver"></c:set>--%>
        <%--<c:set var="buttonText" value="Edit driver"></c:set>--%>
    <%--</c:when>--%>
<%--</c:choose>--%>

<form:form modelAttribute="driverModel" method="post" cssClass="form-horizontal">

    <!-- Form Name -->
    <legend>${title}</legend>

        <%--Error message --%>
    <c:if test="${not empty error}">
        <div class="form-group">
            <div class="col-md-4">
            </div>
            <div class="col-md-4 alert alert-warning">
                <strong>Warning!</strong> ${error}
                <form:errors path="*"/>
            </div>
        </div>
    </c:if>

    <form:hidden path="id" />

    <jsp:include page="../common/elem/form_text_row.jsp">
        <jsp:param name="name" value="firstName" />
        <jsp:param name="title" value="First name" />
    </jsp:include>

    <jsp:include page="../common/elem/form_text_row.jsp">
        <jsp:param name="name" value="lastName" />
        <jsp:param name="title" value="Last name" />
    </jsp:include>

    <jsp:include page="../common/elem/form_text_row.jsp">
        <jsp:param name="name" value="personalCode" />
        <jsp:param name="title" value="Personal code" />
    </jsp:include>

    <div class="form-group">
        <label for="status">Status</label>
        <form:select path="status" cssClass="selectpicker form-control" id="status">
            <form:option value="REST" label="Rest"/>
            <form:option value="DUTY_DRIVE" label="Duty - Drive"/>
            <form:option value="DUTY_REST" label="Duty - Rest"/>
        </form:select>

        <form:errors path="status" class="help-block with-errors" />
    </div>

    <div class="form-group">
        <label for="cityName">Current location</label>
        <form:input path="cityName" type="text" class="form-control" id="cityName" placeholder="Current location" />
        <form:errors path="cityId" class="help-block with-errors" />

        <input type="hidden" id="cityId" name="cityId">
    </div>



    <%--<spring:bind path="firstName">--%>

        <%--<div class="form-group ${status.error ? 'has-error' : ''}">--%>
            <%--<label for="firstName">First name</label>--%>
            <%--<form:input path="firstName" type="text" class="form-control" id="firstName" placeholder="First name" />--%>
            <%--&lt;%&ndash;<input type="text" name="firstName" id="firstName" placeholder="First name" class="form-control" required>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="help-block with-errors"></div>&ndash;%&gt;--%>
            <%--<form:errors path="firstName" class="help-block with-errors" />--%>
        <%--</div>--%>

    <%--</spring:bind>--%>

    <%--<spring:bind path="name">--%>
        <%--<div class="form-group ${status.error ? 'has-error' : ''}">--%>
            <%--<label class="col-md-4 control-label">Name</label>--%>
            <%--<div class="col-md-4">--%>
                <%--<form:input path="name" type="text" class="form-control input-md"--%>
                            <%--id="name" placeholder="Name" />--%>
                <%--<form:errors path="name" class="control-label has-error" />--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</spring:bind>--%>

    <%--<spring:bind path="surname">--%>
        <%--<div class="form-group ${status.error ? 'has-error' : ''}">--%>
            <%--<label class="col-md-4 control-label">Surname</label>--%>
            <%--<div class="col-md-4">--%>
                <%--<form:input path="surname" type="text" class="form-control input-md"--%>
                            <%--id="surname" placeholder="Surname" />--%>
                <%--<form:errors path="surname" class="control-label" />--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</spring:bind>--%>

    <%--<spring:bind path="currentCityId">--%>
        <%--<div class="form-group ${status.error ? 'has-error' : ''}">--%>
            <%--<label class="col-md-4 control-label">City</label>--%>
            <%--<div class="col-md-4">--%>
                <%--<form:select path="currentCityId" class="form-control">--%>
                    <%--<form:options items="${cities}" itemLabel="name"/>--%>
                <%--</form:select>--%>
                <%--<form:errors path="currentCityId" class="control-label" />--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</spring:bind>--%>

    <!-- Submit -->
    <div class="form-group">
        <label class="col-md-4 control-label"></label>
        <div class="col-md-4">
            <button class="btn btn-primary" type="submit">${buttonText}</button>
        </div>
    </div>
</form:form>