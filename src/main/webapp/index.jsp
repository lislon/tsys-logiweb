<html>
<head>
    <link rel="stylesheet" href="style/main.css">
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp" />
    <div class="wrapper">
        <jsp:include page="sidebar.jsp" />
        <div class="content">
            <h2>Trucks</h2>
            <table>
                <tr>
                    <th>Number</th>
                    <th>Duty hours</th>
                    <th>Capacity</th>
                    <th>Condition</th>
                    <th>Last city</th>
                    <th>Action</th>
                </tr>
                <tr>
                    <td>FH 121251</td>
                    <td>8</td>
                    <td>5 ton</td>
                    <td>&#10003;</td>
                    <td>Hamburg</td>
                    <td>Delete | Edit</td>
                </tr>
                <tr>
                    <td>ME 121251</td>
                    <td>5</td>
                    <td>5 ton</td>
                    <td>&#10007;</td>
                    <td>Hamburg</td>
                    <td>Delete | Edit</td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>
