<form class="form-inline">
    <div class="form-group form-group-lg">
        <%--<label for="exampleInputAmount" class="label-lg">Personal driver number:</label>--%>
        <input type="text" class="form-control" id="exampleInputAmount" placeholder="Personal driver number">
        <button type="submit" class="btn btn-primary btn-lg" data-toggle="modal">Show orders</button>
    </div>
</form>

<span class="alert" id="table-alert"></span>
<table id="table"
       data-show-refresh="true"
       data-show-columns="true"
       data-search="true"
       data-striped="true"
       data-toolbar=".toolbar">
    <thead>
        <tr>
            <th data-field="orderId" data-sortable="true">Order</th>
            <th data-field="truckName" data-sortable="true">Truck</th>
            <th data-field="drivers" data-sortable="true">Drivers</th>
            <th data-field="route" data-sortable="true">Route</th>
            <th data-field="status" data-sortable="true">Status</th>
        </tr>
    </thead>
</table>


<div class="row">
    <div class="col-md-4">
        <h3>Order</h3>
        <p>12312</p>
    </div>
    <div class="col-md-4">
        <h3>Drivers</h3>
        <ul>
            <li>AB1231 - Boris</li>
            <li>AB1232 - Lisa</li>
        </ul>
    </div>
    <div class="col-md-4">
        <h3>Truck</h3>
        <p>EM132312</p>
    </div>
</div>


<h2>Route</h2>

<table class="table table-striped">
    <thead>
        <tr>
            <th>No</th>
            <th>City</th>
            <th>Cargo</th>
            <th>Operation</th>
        </tr>
    </thead>
    <tr>
        <td>1</td>
        <td>Berlin</td>
        <td>Cargo 1</td>
        <td>Load</td>
    </tr>
    <tr>
        <td>2</td>
        <td>Moscow</td>
        <td>Cargo 1</td>
        <td>Unload</td>
    </tr>
</table>

<script src="<%= request.getContextPath() %>/js/apps/assignments/assignment_list.js"></script>
