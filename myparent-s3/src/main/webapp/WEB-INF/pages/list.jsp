<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding='UTF-8' contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%--<link rel="stylesheet" type="text/css" href="abc.css"/>--%>
    <script type="text/javascript" src="/scripts/jquery-1.9.1.min.js"></script>
    <script  >
        $(function(){
                $(".delete").click(function(){
                    var href=$(this).attr("href");
                    $("form").attr("action",href).submit();
                    return false;
                });
        });
    </script>
</head>
<body>
<form action="" method="post">
    <input type="hidden" name="_method" value="DELETE"/>
</form>
    <c:if test="${empty requestScope.emps}">没有任何员工信息</c:if>
    <c:if test="${!empty requestScope.emps}">
        <table border="1" cellpadding="10" cellspacing="0" align="center">
            <thead>
                <tr>
                    <th align="center">id</th>
                    <th align="center">lastName</th>
                    <th align="center">email</th>
                    <th align="center">gender</th>
                    <th align="center">department</th>
                    <th align="center">edit</th>
                    <th align="center">delete</th>
                </tr>
            </thead>
            <c:forEach items="${requestScope.emps}" var="emp">
                <tr>
                    <td align="center">${emp.id}</td>
                    <td align="center">${emp.lastName}</td>
                    <td align="center">${emp.email}</td>
                    <td align="center">${emp.gender==0?'female':'male'}</td>
                    <td align="center">${emp.department.departmentName}</td>
                    <td align="center"><a href="/emp/content?id=${emp.id}">edit</a></td>
                    <td align="center"><a class="delete" href="/emp/delete/${emp.id}">delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <br/>
    <a href="/emp/content">add</a><br/>
</body>
</html>
