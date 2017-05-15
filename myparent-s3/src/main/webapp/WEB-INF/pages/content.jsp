<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page pageEncoding='UTF-8' contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html>
<head>
    <title></title>
    <%--<link rel="stylesheet" type="text/css" href="abc.css"/>--%>
    <script type="text/javascript" src="/scripts/jquery-1.9.1.min.js"></script>
    <script  >
        $(function(){

        });
    </script>
</head>
<body>
<%--格式：lastName-email-gender-department.id 例如 ttt-ttt@qq.com-0-1001 --%>
<form action="/emp/testNormalMappingParam" method="post">
    lastName:<input type="text" name="lastName" value="${employee.lastName}"/><br/>
    email:<input type="text" name="email" value="${employee.email}"/><br/>
    gender:<input type="text" name="gender" value="${employee.gender}"/><br/>
    birthday:<input type="text" name="birthday" value="${employee.birthday}"/><br/>
    department.id:<input type="text" name="departmentId" value="${employee.department.id}"/><br/>
    <input type="submit" value="testNormalMappingParam"/>
</form>
    <%--格式：lastName-email-gender-department.id 例如 ttt-ttt@qq.com-0-1001 --%>
    <form action="/emp/testConverionServiceConvertor" method="post">
        <%--lastName:<input type="text" name="lastName" value="tt"/><br/>--%>
        <%--email:<input type="text" name="email" value="tt@qq.com"/><br/>--%>
        <%--gender:<input type="text" name="gender" value="0"/><br/>--%>
        <%--department.id:<input type="text" name="id" value="1001"/><br/>--%>
            <input  type="text" name="employee" value="ttt-ttt@qq.com-0-1001"/>
        <input type="submit" value="testConverionServiceConvertor"/>
    </form>




    <% Map<Integer,String> map=new HashMap<Integer,String>();
            map.put(0,"female");
            map.put(1,"male");
            request.setAttribute("genders",map);
    %>
    <form:form action="/emp/save" method="post" modelAttribute="employee">
        <%--新增--%>
        <c:if test="${employee.id==null}">lastName:<form:input path="lastName" /><br/></c:if>
        <%--修改--%>
        <c:if test="${employee.id!=null}">
                <form:input path="id"/>
                <input type="hidden" name="_method" value="PUT">
        </c:if>

        email:<form:input path="email" /><br/>
        gender:<form:radiobuttons path="gender" items="${requestScope.genders}"/> <br/>
        birthday:<form:input path="birthday"/>
        department:<form:select path="department.id" items="${departments}" itemLabel="departmentName" itemValue="id"/> <br/>
        <input type="submit" value="testSpringFormTag"/>
    </form:form>
</body>
</html>


