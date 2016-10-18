<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="logiweb" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<logiweb:layout title="List of orders">

    <jsp:attribute name="body">
        <p class="toolbar">
            <a class="create btn btn-default" href="${s:mvcUrl('OC#create').build()}">New order</a>
        </p>

        <table id="table"
               data-show-refresh="true"
               data-show-columns="true"
               data-sortable="true"
               data-search="true"
               data-toolbar=".toolbar">
            <thead>
            <tr>
                <th data-field="id" data-sortable="true">Order Id</th>
                <th data-field="truckName" data-formatter="formatter.truckFormatter"  data-sortable="true">Truck</th>
                <th data-field="cityStartName" data-sortable="true">Departure city</th>
                <th data-field="cityEndName" data-sortable="true">Arrival city</th>
                <th data-field="routeLength" data-formatter="formatter.routeLengthFormatter" data-sortable="true">Distance</th>
                <th data-field="maxPayload" data-formatter="formatter.payloadFormatter" data-sortable="true">Max payload</th>
                <th data-field="dateCreated" data-formatter="formatter.dateFormatter" data-sortable="true">Date</th>
                <th data-field="status" data-formatter="formatter.statusFormatter" data-sortable="true">Status</th>
                <th data-field="action"
                    data-align="center"
                    data-formatter="formatter.actionFormatter"
                    data-events="actionEvents">Action</th>
            </tr>
            </thead>
        </table>

    </jsp:attribute>


    <jsp:attribute name="script">

        <script>
            var formatter = null;
            logiweb.load('order/Formatters', function (formatters) {
                formatter = formatters;
            });
            logiweb.load('order/OrderListController', function (module) {
                module.initialize();
            });
        </script>

    </jsp:attribute>

</logiweb:layout>

