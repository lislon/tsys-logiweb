<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="logiweb" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="${model != null ? 'Edit truck' : 'New truck'}" />;

<logiweb:layout title="${title}">


    <jsp:attribute name="body">
        <div class="box box-primary">
            <div class="box-body">
                <form:form modelAttribute="model" method="post" id="primary-form">

                    <jsp:include page="../common/elem/form_text_row.jsp">
                        <jsp:param name="name" value="name"/>
                        <jsp:param name="title" value="Truck identifier"/>
                    </jsp:include>


                    <c:set var="path" value="maxDrivers" />
                    <spring:bind path="${path}">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="${path}">
                                Status
                            </label>
                            <form:select path="${path}" cssClass="form-control" id="${path}">
                                <form:option value="1" label="1 seat"/>
                                <form:option value="2" label="2 seats"/>
                                <form:option value="3" label="3 seats"/>
                            </form:select>

                            <form:errors path="${path}" class="help-block with-errors"/>
                        </div>
                    </spring:bind>


                    <c:set var="path" value="condition" />
                    <spring:bind path="${path}">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="${path}">
                                Condition
                            </label>
                            <form:select path="${path}" cssClass="form-control" id="${path}">
                                <form:option value="OK" label="Operating"/>
                                <form:option value="BROKEN" label="Broken"/>
                            </form:select>

                            <form:errors path="${path}" class="help-block with-errors"/>
                        </div>
                    </spring:bind>


                    <c:set var="path" value="capacityKg" />
                    <spring:bind path="${path}">
                        <div class="form-group ${path}-error-container ${status.error ? 'has-error' : ''}">
                            <label for="${path}">
                                Max capacity
                            </label>
                            <div class="input-group">
                                <span class="input-group-addon">kg</span>
                                <form:input path="${path}" type="text" class="form-control" id="${path}" data-parsley-errors-container=".${path}-error-container" />
                            </div>
                            <form:errors path="${path}" class="help-block with-errors"/>
                        </div>
                    </spring:bind>


                    <c:set var="path" value="cityName" />
                    <spring:bind path="${path}">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="${path}">
                                Current location
                            </label>
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
                logiweb.load('truck/EditModule', function (module) {
                    module.initialize();
                });
            });
        </script>
    </jsp:attribute>

</logiweb:layout>