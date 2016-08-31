<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="truck" class="com.tsystems.javaschool.logiweb.dao.entities.Truck" scope="request" />

<form method="post">

    <div class="alert alert-warning" role="alert">
        Ops! Check you truck name!
    </div>

    <div class="form-group">
        <label for="truck_name">Truck name</label>
        <input type="text" name="name" id="truck_name" placeholder="AB12345" class="form-control"
               value="${truck.name}">
    </div>
    <div class="form-group">
        <label>Maximum drivers per trip</label>
        <div class="btn-toolbar" role="toolbar">
            <div class="btn-group" data-toggle="buttons" id="max_drivers">
                <c:forEach var="i" begin="1" end="3">
                    <label class="btn btn-default ${truck.maxDrivers == i ? "active" : ""}">
                        <input type="radio" name="max_drivers" autocomplete="off" ${truck.maxDrivers == i ? "checked" : ""}>
                            ${i} Seat
                    </label>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="capacity_ton">Capacity (tons)</label>
        <input type="text" name="capacity_ton" id="capacity_ton" class="form-control" placeholder="4"
        value="${truck.capacityTon}">
    </div>
    <div class="form-group">
        <label>Condition</label>
        <div class="btn-toolbar" role="toolbar">
            <div class="btn-group" data-toggle="buttons" id="condition">
                <label class="btn btn-default ${truck.condition == "OK" ? "active" : ""}">
                    <input type="radio" name="condition" autocomplete="off" ${truck.condition == "OK" ? "checked" : ""}>Good
                </label>
                <label class="btn btn-default ${truck.condition == "BROKEN" ? "active" : ""}">
                    <input type="radio" name="condition" autocomplete="off" ${truck.condition == "BROKEN" ? "checked" : ""}>Broken
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="city">Current location</label>
        <input type="text" id="city" name="city" placeholder="Berlin" class="form-control" value="${truck.city.name}">
        <input type="hidden" id="city_id" name="city_id" value="${truck.city.id}">
    </div>
    <hr />
    <div class="form-group text-right">
        <button type="submit" class="btn">Cancel</button>
        <button type="submit" class="btn btn-primary">Save</button>
    </div>
</form>
<script src="<%= request.getContextPath() %>/js/apps/truck/truck_edit.js"></script>