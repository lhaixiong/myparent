<%@ page pageEncoding='UTF-8' contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title></title>
    <%--<link rel="stylesheet" type="text/css" href="abc.css"/>--%>
</head>
<body>
    password：<fmt:message key="i18n.password"/>
    <a href="/emp/i18n">to i18n</a>
    <a href="/emp/i18n?locale=zh_CH">中文</a>
    <a href="/emp/i18n?locale=en_US">英文</a>
</body>
</html>
