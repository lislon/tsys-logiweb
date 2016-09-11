<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<script id="cargo-item-template" type="text/x-handlebars-template">
    <td>
        <a href="javascript:void(0)"" class="edit ajax-link">{{ name }} - {{ title }} - {{ weight }} kg</a>
    </td>
    <td>
        {{srcCityName}} → {{dstCityName}}
    </td>
    <td>
        <a class="edit" href="javascript:void(0)" title="Edit" rel="noreferrer">
            <i class="glyphicon glyphicon-edit"></i>
        </a>
        <a class="remove" href="javascript:void(0)" title="Remove" rel="noreferrer">
            <i class="glyphicon glyphicon-remove"></i>
        </a>
    </td>
</script>

<script id="edit-cargo-template" type="text/x-handlerabs-template">

    <div id="modal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <form>
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Edit cargo</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="cargoName">Cargo name</label>
                            <input type="text" class="form-control" id="cargoName" placeholder="Cargo id" name="name" value="{{ name }}" required>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="form-group">
                            <label for="cargoTitle">Cargo Title</label>
                            <input type="text" class="form-control" id="cargoTitle" placeholder="Fruits" name="title" value="{{ title }}" required>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="form-group">
                            <label for="cargoWeight">Weight</label>
                            <div class="input-group">
                                <input type="number" class="form-control" id="cargoWeight" placeholder="Title" name="weight" value="{{ weight }}" required>
                                <span class="input-group-addon">kg</span>
                            </div>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="form-group">
                            <label>Departure → Destination</label>
                            <div class="form-inline row">

                                <div class="form-group col-sm-6">
                                    <input type="text" id="srcCityName" name="srcCityName" placeholder="Berlin" class="form-control"
                                           value="{{ srcCityName }}" required style="width: 100%;">
                                    <input type="hidden" id="srcCityId" name="srcCityId" value="{{ srcCityId }}">
                                    <div class="help-block with-errors"></div>
                                    <div style="position: absolute; right: -5px; top: 5px;">→</div>
                                </div>
                                <div class="form-group col-sm-6">
                                    <input type="text" id="dstCityName" name="dstCityName" placeholder="Berlin" class="form-control"
                                           value="{{ dstCityName }}" required style="width: 100%;">
                                    <input type="hidden" id="dstCityId" name="dstCityId" value="{{ dstCityId }}">
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary submit" id="editCargoSubmit">Submit</button>
                    </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</script>

<script id="city-li-template" type="text/x-handlerabs-template">
    <div class="panel-heading route-cities-heading"><div class="route-city-name">{{name}}</div><div class="route-arrow">→</div></div>
</script>

<script id="route-summary-template" type="text/x-handlebars-template">
    <table class="route-summary-table">
        <tr>
            <td>Distance</td>
            <td>{{length}} km</td>
        </tr>
        <tr>
            <td>Duration</td>
            <td>{{duration}} hours</td>
        </tr>
        <tr>
            <td>Required capacity</td>
            <td>{{requiredCapacity}} kg</td>
        </tr>
    </table>
</script>

<script id="truck-select-template" type="text/x-handlebars-template">
    <option data-subtext="{{pluralCount seats 'seat' 'seats'}}">{{ name }}</option>
</script>

<script src="<%= request.getContextPath() %>/js/apps/order/order_edit.js"></script>

<form method="post" data-toggle="validator">
    <h1>New order</h1>
    <h2 class="section-title section-title-cargo">1. Cargo</h2>

    <div class="form-group">
        <p class="toolbar">
            <button type="button" href="javascript:void(0)" class="btn btn-success btn-sm addCargo">Add cargo</button>
            <span class="alert"></span>
        </p>
        <table data-toggle="table" class="table table-hover" id="cargoTable">
            <thead>
            <tr>
                <th>Description</th>
                <th>City</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>

    <div id="route-area">
        <h2 class="section-title section-title-route">2. Route</h2>
        <ol class="list-unstyled route-cities">
        </ol>
        <div class="clearfix"></div>
        <p class="route-summary"></p>
    </div>
    <div>
        <h2 class="section-title section-title-truck">3. Truck</h2>
        <div class="form-group">
            <select class="selectpicker truck-selector">
                <option data-subtext="Heinz">Ketchup</option>
            </select>
        </div>
    </div>
    <div>
        <h2 class="section-title section-title-drivers">4. Drivers<small></small></h2>
        <div class="form-group">
            <select class="selectpicker" multiple data-max-options="2" disabled>
                <option>Mustard</option>
                <option>Ketchup</option>
                <option>Relish</option>
            </select>
        </div>
    </div>

    <hr />
    <div class="form-group text-right">
        <a href="<%= request.getContextPath() %>/order/list.do" type="submit" class="btn">Cancel</a>
        <button type="submit" class="btn btn-primary">Save</button>
    </div>
</form>