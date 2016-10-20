<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="logiweb" tagdir="/WEB-INF/tags/" %>

<%--@elvariable id="order" type="com.tsystems.javaschool.logiweb.service.dto.DriverAssignmentDTO"--%>

<logiweb:layout title="Order #${order.orderId}">

    <jsp:attribute name="body">
        <div class="row">
            <!-- Left col -->
            <div class="col-md-4">
                <!-- ROUTE INFO LIST -->
                <div class="box box-info">
                    <div class="box-header with-border">
                        <i class="fa fa-info"></i>

                        <h3 class="box-title">Route Information</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <p class="lead"><c:out value="${order.srcCityName}" /> -> <c:out value="${order.dstCityName}" /></p>
                        <dl class="dl-horizontal">
                            <dt>Distance</dt>
                            <dd><c:out value="${order.routeDistance}" /> km</dd>
                            <dt>Aprox. duration</dt>
                            <dd><c:out value="${order.routeDuration}" /> h</dd>
                        </dl>
                    </div>
                    <!-- /.box-body -->
                </div>
            </div>
            <!--/.col-md-6 -->

            <!-- Middle col -->
            <div class="col-md-4">
                <!-- Truck info -->
                <div class="box box-info">
                    <div class="box-header with-border">
                        <i class="fa fa-truck"></i>

                        <h3 class="box-title">Truck Information</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <dl class="dl-horizontal">
                            <dt>License plate</dt>
                            <dd><c:out value="${order.truck.name}" /></dd>
                            <dt>Drivers seats</dt>
                            <dd><c:out value="${order.truck.maxDrivers}" /></dd>
                            <dt>Capacity</dt>
                            <dd><c:out value="${order.truck.capacityKg}" /> kg</dd>
                        </dl>
                    </div>
                    <!-- /.box-body -->
                </div>
            </div>
            <!--/.col-md-6 -->

            <!-- right col -->
            <div class="col-md-4">
                <!-- USERS LIST -->
                <div class="box box-info">
                    <div class="box-header with-border">
                        <i class="fa fa-users"></i>

                        <h3 class="box-title">Co-drivers</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body no-padding">
                        <ul class="users-list clearfix">
                            <c:forEach items="${order.coDrivers}" var="driver">
                                <li>
                                    <img src="${pageContext.request.contextPath}/dist/img/courier.png"  alt="<c:out value="${driver.firstName}" /> <c:out value="${driver.lastName}" />">
                                    <a class="users-list-name" href="#" data-ss1476753338="1"><c:out value="${driver.firstName}" /> <c:out value="${driver.lastName}" /></a>
                                </li>
                            </c:forEach>
                        </ul>
                        <!-- /.users-list -->
                    </div>
                    <!-- /.box-body -->
                </div>
                <!--/.box -->
            </div>
            <!--/.col-md-6 -->
        </div>
        <!--/.row -->

        <h2>
            Route
        </h2>

        <ul class="timeline">

            <c:forEach items="${order.cityXOperationXWaypoints}" var="wpCity">


                <!-- timeline time label -->
                <li class="time-label">
                    <span class="bg-red">
                        <c:out value="${wpCity.key}" />
                    </span>
                </li>
                <!-- /.timeline-label -->

                <c:forEach items="${wpCity.value}" var="wpOperation">
                    <c:set var="icon" value="${wpOperation.key == 'LOAD' ? 'fa-download' : 'fa-upload'}" />
                    <c:set var="title" value="${wpOperation.key == 'LOAD' ? 'Pickup cargo' : 'Unload'}" />
                    <!-- timeline item -->
                    <li>
                        <!-- timeline icon -->
                        <i class="fa ${icon} bg-blue"></i>
                        <div class="timeline-item">
                            <span class="time"><i class="fa fa-clock-o"></i></span>
                            <%--<span class="label label-success">done</span>--%>
                            <h3 class="timeline-header"><c:out value="${title}" /> at <c:out value="${wpCity.key}" /></h3>

                            <div class="timeline-body">
                                <ul>
                                    <c:forEach items="${wpOperation.value}" var="wpCargo">
                                        <li><c:out value="${wpCargo.cargoName}" /> <small><c:out value="${wpCargo.cargoTitle}" /></small></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </li>
                </c:forEach>

            <!-- END timeline item -->
            </c:forEach>

            <li>
                <i class="fa fa-stop-circle-o bg-red"></i>
            </li>
        </ul>
    </jsp:attribute>

    <jsp:attribute name="script">
        <script>
            logiweb.load('driver/ListModule', function (module) {
                module.initialize();
            });
            var formatter = null;
            logiweb.load('driver/Formatters', function (formatters) {
                formatter = formatters;
            });
        </script>

    </jsp:attribute>

</logiweb:layout>


