<%@ page pageEncoding='UTF-8' contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%  String path=request.getContextPath();
    String bashPath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>

    <link rel="shortcut icon" href="<%=bashPath%>/img/favicon.ico">
    <link href="<%=bashPath%>/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="container" style="width: auto;">
        <div class="row table-responsive">
            <table class="table table-hover table-striped table-bordered">
                <caption>用户列表信息</caption>
                <thead>
                <tr class="success">
                    <th >用户账号</th>
                    <th >昵称</th>
                    <th >状态</th>
                    <th >所在组</th>
                    <th >创建日期</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list}" var="item">
                        <tr >
                            <td>${item.account}</td>
                            <td>${item.nickname}</td>
                            <td>
                                <c:if test="${item.status==1}">可用</c:if>
                                <c:if test="${item.status==0}">不可用</c:if>
                            </td>
                            <td>${groupName}</td>
                            <td>${item.createTime}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
</div>
<script src="<%=bashPath%>/js/jquery.min.js?v=2.1.4"></script>
<script src="<%=bashPath%>/js/bootstrap.min.js?v=3.3.6"></script>
<script>
    $(function(){
        <c:if test="${! empty requestScope.msg}">
            alert('${msg}');
        </c:if>
    })
</script>
</body>
</html>

