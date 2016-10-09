<%@ taglib prefix="m"
           tagdir="/WEB-INF/tags/" %>
<%--
  ~ Copyright (c) 2016.
  ~ Igor Avdeev
  --%>

<div class="col-sm-3 col-md-2 sidebar">
    <ul class="nav nav-sidebar">
        <m:navmenuitem url="/orders" highlight="/orders" title="Orders"/>
        <m:navmenuitem url="/drivers" highlight="/drivers" title="Drivers"/>
        <m:navmenuitem url="/trucks" highlight="/trucks" title="Trucks"/>
        <m:navmenuitem url="/assignment/list" highlight="/assignments" title="Assignment"/>
    </ul>
</div>

