<div class="col-sm-3 col-md-2 sidebar">
    <ul class="nav nav-sidebar">
        <li><a href="<%= request.getContextPath() %>/order/list.do">Orders</a></li>
        <%--<li><a href="#">Cargo &amp; order status</a></li>--%>
        <li><a href="<%= request.getContextPath() %>/driver/list.do">Drivers</a></li>
        <li class="active"><a href="<%= request.getContextPath() %>/truck/list.do">Trucks <span class="sr-only">(current)</span></a></li>
    </ul>
</div>