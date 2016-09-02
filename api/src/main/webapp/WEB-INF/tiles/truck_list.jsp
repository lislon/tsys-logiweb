<p class="toolbar">
    <a class="create btn btn-default" href="<%= request.getContextPath() %>/truck/edit.do">Create Item</a>
    <span class="alert"></span>
</p>
<table id="table"
       data-show-refresh="true"
       data-show-columns="true"
       data-search="true"
       data-query-params="queryParams"
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

<div id="modal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>Name</label>
                    <input type="text" class="form-control" name="name" placeholder="Name">
                </div>
                <div class="form-group">
                    <label>Stars</label>
                    <input type="number" class="form-control" name="stargazers_count" placeholder="Stars">
                </div>
                <div class="form-group">
                    <label>Forks</label>
                    <input type="number" class="form-control" name="forks_count" placeholder="Forks">
                </div>
                <div class="form-group">
                    <label>Description</label>
                    <input type="text" class="form-control" name="description" placeholder="Description">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary submit">Submit</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script src="<%= request.getContextPath() %>/js/apps/truck/truck_list.js"></script>
