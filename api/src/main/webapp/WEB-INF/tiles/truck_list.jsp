<p class="toolbar">
    <a class="create btn btn-default" href="<%= request.getContextPath() %>/truck/edit.do">Create Item</a>
    <span class="alert" id="table-alert"></span>
</p>
<table id="table"
       data-show-refresh="true"
       data-show-columns="true"
       data-search="true"
       data-toolbar=".toolbar">
    <thead>
    <tr>
        <th data-field="name">Number</th>
        <th data-field="capacityKg">Capacity</th>
        <th data-field="maxDrivers">Max drivers</th>
        <th data-field="condition">Condition</th>
        <th data-field="cityName">City</th>
        <th data-field="action"
            data-align="center"
            data-formatter="actionFormatter"
            data-events="actionEvents">Action</th>
    </tr>
    </thead>
</table>

<script src="<%= request.getContextPath() %>/js/apps/truck/truck_list.js"></script>
