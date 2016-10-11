<%--
  ~ Copyright (c) 2016.
  ~ Igor Avdeev
  --%>

<p class="toolbar">
    <a class="create btn btn-default" href="<%= request.getContextPath() %>/truck/edit">Create Item</a>
    <span class="alert" id="table-alert"></span>
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
            data-formatter="actionFormatter"
            data-events="actionEvents">Action</th>
    </tr>
    </thead>
</table>

<script src="<%= request.getContextPath() %>/resources/js/apps/truck/truck_list.js"></script>
