<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form method="post">
    <div class="form-group">
        <label for="truck_name">Truck name</label>
        <input type="text" name="name" id="truck_name" placeholder="AB12345" class="form-control"
               value="${requestScope.name}">
    </div>
    <div class="form-group">
        <label>Maximum drivers per trip</label>
        <div class="btn-toolbar" role="toolbar">
            <div class="btn-group" data-toggle="buttons" id="max_drivers">
                <c:forEach var="i" begin="1" end="3">
                    <label class="btn btn-default ${requestScope.maxDrivers == i ? "active" : ""}">
                        <input type="radio" name="maxDrivers" autocomplete="off"
                               value="${i}" ${requestScope.maxDrivers == i ? "checked" : ""}>
                            ${i} Seats
                    </label>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="capacity_ton">Capacity (tons)</label>
        <input type="text" name="capacityKg" id="capacity_ton" class="form-control" placeholder="4"
        value="${requestScope.capacityKg}">
    </div>
    <div class="form-group">
        <label>Condition</label>
        <div class="btn-toolbar" role="toolbar">
            <div class="btn-group" data-toggle="buttons" id="condition">
                <label class="btn btn-default ${requestScope.condition == "OK" ? "active" : ""}">
                    <input type="radio" name="condition" autocomplete="off"
                           value="OK" ${requestScope.condition == "OK" ? "checked" : ""}>Good
                </label>
                <label class="btn btn-default ${requestScope.condition == "BROKEN" ? "active" : ""}">
                    <input type="radio" name="condition" autocomplete="off" v
                           value="BROKEN" ${requestScope.condition == "BROKEN" ? "checked" : ""}>Broken
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="city">Current location</label>
        <input type="text" id="city" name="city" placeholder="Berlin" class="form-control"
               value="${requestScope.cityName}">
        <input type="hidden" id="city_id" name="cityId" value="${requestScope.cityId}">
    </div>
    <hr />
    <div class="form-group text-right">
        <button type="submit" class="btn">Cancel</button>
        <button type="submit" class="btn btn-primary">Save</button>
    </div>
</form>
<script src="<%= request.getContextPath() %>/js/apps/truck/truck_edit.js"></script>