<%@ page isErrorPage="true" %>
<p class="bg-warning">
    <%= exception %>
</p>
<pre>
    <% exception.printStackTrace(response.getWriter()); %>
</pre>