<%--
  ~ Copyright (c) 2016.
  ~ Igor Avdeev
  --%>

<p class="toolbar">
    <a class="create btn btn-default" href="<%= request.getContextPath() %>/order/edit">Create Item</a>
    <span class="alert" id="table-alert"></span>
</p>
<table id="table"
       data-show-refresh="true"
       data-show-columns="true"
       data-search="true"
       data-query-params="queryParams"
       data-toolbar=".toolbar">
    <thead>
    <tr>
        <th data-field="id">Order Id</th>
        <th data-field="truckName" data-formatter="truckFormatter">Truck</th>
        <th data-field="cityStartName">Departure city</th>
        <th data-field="cityEndName">Arrival city</th>
        <th data-field="routeLength" data-formatter="routeLengthFormatter">Route lenght</th>
        <th data-field="maxPayload" data-formatter="payloadFormatter">Max payload</th>
        <th data-field="dateCreated" data-formatter="dateFormatter">Date</th>
        <th data-field="status">Status</th>
        <th data-field="action"
            data-align="center"
            data-formatter="actionFormatter"
            data-events="actionEvents">Action</th>
    </tr>
    </thead>
</table>

<script src="<%= request.getContextPath() %>/resources/js/apps/order/order_list.js"></script>
