<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="logiweb" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="title" value="${bootstrapJson != null ? 'Edit order' : 'New order'}" />;

<logiweb:layout title="${title}">


    <jsp:attribute name="body">
        <div class="box box-primary">
            <div class="box-body">
                <form>

                    <h2 class="section-title section-title-cargo">1. Cargo</h2>

                    <div class="form-group">
                        <p class="toolbar">
                            <button type="button" class="btn btn-success btn-sm addCargo">Add cargo</button>
                        </p>
                        <table class="table table-hover" id="cargoTable">
                            <thead>
                            <tr>
                                <th>Description</th>
                                <th data-align="center">City</th>
                                <th data-align="center">Action</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>

                    <div id="route-area">
                        <h2 class="section-title section-title-route">2. Route</h2>
                        <ol class="list-unstyled route-cities">
                        </ol>
                        <div class="clearfix"></div>
                        <p class="route-summary"></p>
                    </div>
                    <div>
                        <h2 class="section-title section-title-truck">3. Truck</h2>
                        <div class="form-group">
                            <select class="truck-selector" name="truck" id="truck-selector">
                            </select>
                        </div>
                    </div>
                    <div>
                        <h2 class="section-title section-title-drivers">4. Drivers</h2>
                        <div class="form-group">
                            <select class="driver-selector" multiple disabled name="drivers" id="driver-selector">
                            </select>
                        </div>
                    </div>


                    <!-- Submit -->
                    <div class="box-footer">
                        <button class="btn btn-primary" type="button" id="submit">
                            <c:out value="${title}"/>
                        </button>
                    </div>

                </form>
            </div>
            <!-- /.box-body -->
        </div>
        <!-- /.box -->
    </jsp:attribute>

    <jsp:attribute name="script">

        <script src="${pageContext.request.contextPath}/dist/vendor/underscore/underscore-min.js"></script>
        <script src="${pageContext.request.contextPath}/dist/vendor/backbone/backbone-min.js"></script>
        <script>
            <c:if test="${bootstrapJson != null}">
                window.bootstrap = ${bootstrapJson};
            </c:if>
            $(function () {
                logiweb.load('order/OrderEditController', function (OrderEditController) {
                    let controller = new OrderEditController();
                    controller.start();
                });
            });
        </script>
    </jsp:attribute>

</logiweb:layout>