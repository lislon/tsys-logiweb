<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  ~ Copyright (c) 2016.
  ~ Igor Avdeev
  --%>

<form class="form-inline" id="search-driver">
    <div class="form-group form-group-lg">
        <%--<label for="exampleInputAmount" class="label-lg">Personal driver number:</label>--%>
        <input type="text" class="form-control" id="personalNumber" placeholder="Personal driver number"
               value="${sessionScope.personalNumber}">
        <button type="submit" class="btn btn-primary btn-lg" data-toggle="modal" id="show-orders">Show orders</button>
    </div>
</form>

<span class="alert" id="table-alert"></span>
<table id="table"
       data-show-refresh="true"
       data-show-columns="true"
       data-search="true"
       data-query-params="queryParams"
       data-striped="true"
       data-toolbar=".toolbar">
    <thead>
        <tr>
            <th data-field="orderId" data-sortable="true">Order</th>
            <th data-field="truckName" data-sortable="true">Truck</th>
            <th data-field="drivers" data-sortable="true" data-formatter="driversFormatter">Drivers</th>
            <th data-field="routeDescription" data-sortable="true">Route</th>
            <th data-field="status" data-sortable="true">Status</th>
        </tr>
    </thead>
</table>

<div class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <form data-toggle="validator">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Modal title</h4>
                </div>
                <div class="modal-body">
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

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary submit">Save changes</button>
                </div>
            </div><!-- /.modal-content -->
        </form>
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->



<script src="<%= request.getContextPath() %>/resources/js/apps/assignments/assignment_list.js"></script>
