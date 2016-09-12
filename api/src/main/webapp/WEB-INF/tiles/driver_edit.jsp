<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form method="post" data-toggle="validator">
    <div class="form-group">
        <label for="firstName">First name</label>
        <input type="text" name="firstName" id="firstName" placeholder="First name" class="form-control"
               required
               value="${requestScope.firstName}">
        <div class="help-block with-errors"></div>
    </div>
    <div class="form-group">
        <label for="lastName">Last name</label>
        <input type="text" name="lastName" id="lastName" placeholder="Last name" class="form-control"
               required
               value="${requestScope.lastName}">
        <div class="help-block with-errors"></div>
    </div>
    <div class="form-group">
        <label for="hoursWorked">Hours worked last month</label>
        <input type="number" name="hoursWorked" id="hoursWorked" class="form-control" placeholder="4"
               data-error="Hours worked must be a number" required
               value="${requestScope.hoursWorked}">
        <div class="help-block with-errors"></div>
    </div>
    <div class="form-group">
        <label for="city">Current location</label>
        <input type="text" id="city" name="city" placeholder="City name" class="form-control"
               value="${requestScope.cityName}" required>
        <input type="hidden" id="cityId" name="cityId" value="${requestScope.cityId}">
        <div class="help-block with-errors"></div>
    </div>
    <hr />
    <div class="form-group text-right">
        <a href="<%= request.getContextPath() %>/driver/list.do" type="submit" class="btn">Cancel</a>
        <button type="submit" class="btn btn-primary">Save</button>
    </div>
</form>
<script src="<%= request.getContextPath() %>/js/apps/driver/driver_edit.js"></script>