<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<logiweb:layout title="Current assigment">

    <jsp:attribute name="body">
        <h1>
            Active assignment
            <small>#007612</small>
        </h1>

        <ul class="timeline">

            <!-- timeline time label -->
            <li class="time-label">
        <span class="bg-red">
            10 Feb. 2014
        </span>
            </li>
            <!-- /.timeline-label -->

            <!-- timeline item -->
            <li>
                <!-- timeline icon -->
                <i class="fa fa-envelope bg-blue"></i>
                <div class="timeline-item">
                    <span class="time"><i class="fa fa-clock-o"></i> 12:05</span>

                    <h3 class="timeline-header"><a href="#">Support Team</a> ...</h3>

                    <div class="timeline-body">
                        ...
                        Content goes here
                    </div>

                    <div class="timeline-footer">
                        <a class="btn btn-primary btn-xs">...</a>
                    </div>
                </div>
            </li>
            <!-- END timeline item -->


        </ul>



    </jsp:attribute>


    <jsp:attribute name="script">


        <script>
            logiweb.load('driver/ListModule', function (module) {
                module.initialize();
            });
            var formatter = null;
            logiweb.load('driver/Formatters', function (formatters) {
                formatter = formatters;
            });
        </script>

    </jsp:attribute>

</logiweb:layout>


