<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="logiweb" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="title" value="${model != null ? 'Edit driver' : 'New driver'}" />;

<logiweb:layout title="${title}">


    <jsp:attribute name="body">
        <div class="box box-primary">
            <div class="box-body">
                <form:form modelAttribute="model" method="post" id="primary-form">

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

                    <jsp:include page="../common/elem/form_text_row.jsp">
                        <jsp:param name="name" value="firstName"/>
                        <jsp:param name="title" value="First name"/>
                    </jsp:include>

                    <jsp:include page="../common/elem/form_text_row.jsp">
                        <jsp:param name="name" value="lastName"/>
                        <jsp:param name="title" value="Last name"/>
                    </jsp:include>

                    <jsp:include page="../common/elem/form_text_row.jsp">
                        <jsp:param name="name" value="personalCode"/>
                        <jsp:param name="title" value="Personal code"/>
                    </jsp:include>

                    <c:set var="path" value="status" />
                    <spring:bind path="${path}">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="${path}">Status</label>
                            <form:select path="${path}" id="${path}" cssClass="form-control">
                                <form:option value="REST" label="Rest"/>
                                <form:option value="DUTY_DRIVE" label="Duty - Drive"/>
                                <form:option value="DUTY_REST" label="Duty - Rest"/>
                            </form:select>

                            <form:errors path="${path}" class="help-block with-errors"/>
                        </div>
                    </spring:bind>

                    <c:set var="path" value="cityName" />
                    <spring:bind path="${path}">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="${path}">Current location</label>
                            <form:input path="${path}" type="text" class="form-control" id="${path}"
                                        placeholder="Current location"/>
                            <form:errors path="${path}" class="help-block with-errors"/>

                            <form:input path="cityId" type="hidden" id="cityId"/>
                        </div>
                    </spring:bind>


                    <!-- Submit -->
                    <div class="box-footer">
                        <button class="btn btn-primary" type="submit">
                            <c:out value="${title}"/>
                        </button>
                    </div>

                </form:form>
            </div>
            <!-- /.box-body -->
        </div>
        <!-- /.box -->
    </jsp:attribute>

    <jsp:attribute name="script">
        <script>
            $(function () {
                logiweb.load('driver/EditModule', function (module) {
                    module.initialize();
                });
            });
        </script>
    </jsp:attribute>

</logiweb:layout>

