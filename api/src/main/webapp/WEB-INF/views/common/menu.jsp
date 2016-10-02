<%@ taglib prefix="m" tagdir="/WEB-INF/tags/" %>

<!-- Sidebar Menu -->
<ul class="sidebar-menu">
    <li class="header">Menu</li>
    <!-- Optionally, you can add icons to the links -->

    <m:navmenuitem url="/orders" highlight="/orders" title="Orders" faIcon="fa-list"/>
    <m:navmenuitem url="/drivers" highlight="/drivers" title="Drivers" faIcon="fa-user"/>
    <m:navmenuitem url="/trucks" highlight="/trucks" title="Trucks" faIcon="fa-truck"/>
    <m:navmenuitem url="/assignment/list" highlight="/assignments" title="Assignment" faIcon="fa-table"/>
</ul>
