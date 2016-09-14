<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form method="post" data-toggle="validator">
    <div class="form-group">
        <label for="truck_name">Truck name</label>
        <input type="text" name="name" id="truck_name" placeholder="AB12345" class="form-control"
               data-error="Truck name must consist of two latin letters + 5 digits" pattern="^[A-Z]{2}[0-9]{5}$" required
               value="${requestScope.name}">
        <div class="help-block with-errors"></div>
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
        <label for="capacity_ton">Capacity (kg)</label>
        <input type="number" name="capacityKg" id="capacity_ton" class="form-control" placeholder="4000"
               data-error="Capacity must be a number" required
               value="${requestScope.capacityKg}">
        <div class="help-block with-errors"></div>
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
                    <input type="radio" name="condition" autocomplete="off"
                           value="BROKEN" ${requestScope.condition == "BROKEN" ? "checked" : ""}>Broken
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="city">Current location</label>
        <input type="text" id="city" name="city" placeholder="City..." class="form-control"
               value="${requestScope.cityName}" required>
        <input type="hidden" id="city_id" name="cityId" value="${requestScope.cityId}">
        <div class="help-block with-errors"></div>
    </div>
    <hr />
    <div class="form-group text-right">
        <a href="<%= request.getContextPath() %>/truck/list.do" type="submit" class="btn">Cancel</a>
        <button type="submit" class="btn btn-primary">Save</button>
    </div>
</form>
<script src="<%= request.getContextPath() %>/js/apps/truck/truck_edit.js"></script>