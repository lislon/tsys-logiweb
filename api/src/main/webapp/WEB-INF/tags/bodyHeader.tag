<%--
  ~ Header of content snip
  --%>
<%@tag description="Body header" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="title" required="true" type="java.lang.String" %>

<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        ${title}
        <%--<small>Optional description</small>--%>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
        <li class="active">Here</li>
    </ol>
</section>