<p class="toolbar">
    <a class="create btn btn-default" href="<%= request.getContextPath() %>/driver/edit.do">Add Driver</a>
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
        <th data-field="name">Name</th>
        <th data-field="uid">Id</th>
        <th data-field="hoursWorked">Hours worked</th>
        <th data-field="status">Status</th>
        <th data-field="cityName">Current city</th>
        <th data-field="truckName">Current truck</th>
        <th data-field="action"
            data-align="center"
            data-formatter="actionFormatter"
            data-events="actionEvents">Action</th>
    </tr>
    </thead>
</table>

<script src="<%= request.getContextPath() %>/js/apps/driver/driver_list.js"></script>
