<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="logiweb" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<logiweb:layout title="List of trucks">

    <jsp:attribute name="body">
        <p class="toolbar">
            <a class="create btn btn-default" href="${s:mvcUrl('TC#create').build()}">Add Truck</a>
        </p>

       <table id="table"
              data-show-refresh="true"
              data-show-columns="true"
              data-search="true"
              data-striped="true"
              data-toolbar=".toolbar">
           <thead>
           <tr>
               <th data-field="name" data-sortable="true">Number</th>
               <th data-field="capacityKg" data-sortable="true">Capacity</th>
               <th data-field="maxDrivers" data-sortable="true">Max drivers</th>
               <th data-field="condition" data-sortable="true" data-cell-style="cellStyle">Condition</th>
               <th data-field="cityName" data-sortable="true">City</th>
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
            logiweb.load('truck/ListModule', function (module) {
                module.initialize();
            });
            var formatter = null;
            logiweb.load('truck/Formatters', function (formatters) {
                formatter = formatters;
            });
        </script>

    </jsp:attribute>

</logiweb:layout>


