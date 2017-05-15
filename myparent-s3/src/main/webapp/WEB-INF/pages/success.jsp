<%@ page pageEncoding='UTF-8' contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page isELIgnored="false" %>--%>
<html>
<head>
    <title></title>
    <%--<link rel="stylesheet" type="text/css" href="abc.css"/>--%>
</head>
<body>
    <h1>success</h1>
    request user:${requestScope.user}  <br/>
    session user:${sessionScope.user}  <br/>
    names:${requestScope.names}  <br/>
    time:${requestScope.time}  <br/>
</body>
</html>
