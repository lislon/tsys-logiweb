<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="logiweb" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<logiweb:layout title="List of drivers">

    <jsp:attribute name="body">
        <p class="toolbar">
            <a class="create btn btn-default" href="${s:mvcUrl('DC#create').build()}">Add Driver</a>
            <%--<span class="alert" id="table-alert"></span>--%>
        </p>

        <table id="table"
               data-show-refresh="true"
               data-show-columns="true"
               data-search="true"
               data-striped="true"
               data-toolbar=".toolbar">
            <thead>
            <tr>
                <th data-field="personalCode" data-sortable="true">Personal code</th>
                <th data-field="firstName" data-formatter="formatter.driverName" data-sortable="true">Name</th>
                <th data-field="hoursWorked" data-sortable="true">Hours worked</th>
                <th data-field="status" data-formatter="formatter.statusFormatter" data-sortable="true"
                    data-cell-style="cellStyle">
                    Status
                </th>
                <th data-field="city.name" data-sortable="true">Current city</th>
                    <%--<th data-field="truckName">Current truck</th>--%>
                <th data-field="action"
                    data-align="center"
                    data-formatter="formatter.actionFormatter"
                    <%--data-="app.actionFormatter"--%>
                    data-events="actionEvents">Action
                </th>
            </tr>
            </thead>
        </table>

        <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        ...
                    </div>
                    <div class="modal-body">
                        ...
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <a class="btn btn-danger btn-ok">Delete</a>
                    </div>
                </div>
            </div>
        </div>

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


