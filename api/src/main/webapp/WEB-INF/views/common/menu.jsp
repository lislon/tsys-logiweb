<%@ taglib prefix="m" tagdir="/WEB-INF/tags/" %>

<!-- Sidebar Menu -->
<ul class="sidebar-menu">
    <li class="header">Menu</li>
    <!-- Optionally, you can add icons to the links -->
    <sec:authorize access="hasRole('ROLE_MANAGER')">
        <m:navmenuitem url="/orders" highlight="/orders" title="Orders" faIcon="fa-list"/>
        <m:navmenuitem url="/drivers" highlight="/drivers" title="Drivers" faIcon="fa-user"/>
        <m:navmenuitem url="/trucks" highlight="/trucks" title="Trucks" faIcon="fa-truck"/>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_DRIVER')">
        <m:navmenuitem url="/myorder" highlight="/myorder" title="My order" faIcon="fa-truck"/>
    </sec:authorize>

</ul>
