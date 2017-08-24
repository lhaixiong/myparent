<%@ page pageEncoding='UTF-8' contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%  String path=request.getContextPath();
    String bashPath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--360浏览器优先以webkit内核解析-->
    <title>权限信息</title>
    <link rel="shortcut icon" href="favicon.ico"> <link href="<%=bashPath%>/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="<%=bashPath%>/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="<%=bashPath%>/css/animate.min.css" rel="stylesheet">
    <link href="<%=bashPath%>/css/style.min862f.css?v=4.1.0" rel="stylesheet">


</head>

<body class="gray-bg">
    <div class="container">
        <c:forEach items="${menuMap}" var="entry">
            <c:set var="node" value="${entry.value}"/>
            <c:set var="menuList" value="${node.subPermission}"/>
            <div class="">
                <table id="" class="table table-hover table-striped table-bordered">
                    <thead>
                    <tr>
                      ${node.name}(${node.id})
                    </tr>
                    <tr>
                        <th>菜单</th>
                        <th>按钮</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${menuList}" var="menu">
                        <c:set var="btnList" value="${menu.subPermission}"/>
                        <tr>
                            <td>${menu.name}(${menu.id})</td>
                            <td>
                                <c:forEach items="${btnList}" var="btn">
                                    ${btn.name}(${btn.id})&nbsp;&nbsp;&nbsp;
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:forEach>
    </div>
    <script src="<%=bashPath%>/js/jquery.min.js?v=2.1.4"></script>
    <script src="<%=bashPath%>/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="<%=bashPath%>/js/content.min.js?v=1.0.0"></script>
    <script src="<%=bashPath%>/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="<%=bashPath%>/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script src="<%=bashPath%>/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="<%=bashPath%>/js/demo/bootstrap-table-demo.min.js"></script>
</body>
</html>
