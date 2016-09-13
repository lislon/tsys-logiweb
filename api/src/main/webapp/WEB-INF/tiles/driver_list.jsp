<p class="toolbar">
    <a class="create btn btn-default" href="javascript:">Add Driver</a>
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
            <th data-field="id">Id</th>
            <th data-formatter="nameFormatter">Name</th>
            <th data-field="hoursWorked">Hours worked</th>
            <th data-field="status" data-formatter="statusFormatter">Status</th>
            <th data-field="city.name">Current city</th>
            <%--<th data-field="truckName">Current truck</th>--%>
            <th data-field="action"
                data-align="center"
                data-formatter="actionFormatter"
                data-events="actionEvents">Action</th>
        </tr>
    </thead>
</table>

<div class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Modal title</h4>
            </div>
            <div class="modal-body">
                <form method="post" data-toggle="validator">
                    <div class="form-group">
                        <label for="firstName">First name</label>
                        <input type="text" name="firstName" id="firstName" placeholder="First name" class="form-control" required>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <label for="lastName">Last name</label>
                        <input type="text" name="lastName" id="lastName" placeholder="Last name" class="form-control" required>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <label for="hoursWorked">Hours worked last month</label>
                        <input type="number" name="hoursWorked" id="hoursWorked" class="form-control" placeholder="4" data-error="Hours worked must be a number" required>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <label for="status">Status</label>
                        <select class="selectpicker form-control" id="status" name="status">
                            <option value="REST">Rest</option>
                            <option value="DUTY_DRIVE">Duty - Drive</option>
                            <option value="DUTY_REST">Duty - Rest</option>
                        </select>
                        <div class="help-block with-errors"></div>
                    </div>

                    <div class="form-group">
                        <label for="cityName">Current location</label>
                        <input type="text" id="cityName" name="cityName" placeholder="City name" class="form-control"
                               required>
                        <input type="hidden" id="cityId" name="cityId" value="0">
                        <div class="help-block with-errors"></div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary submit">Save changes</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script src="<%= request.getContextPath() %>/js/apps/driver/driver_list.js"></script>
