<%@ page pageEncoding='UTF-8' contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%  String path=request.getContextPath();
    String bashPath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>H+ 后台主题UI框架 - Bootstrap Table</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

    <link rel="shortcut icon" href="<%=bashPath%>/img/favicon.ico">
    <link href="<%=bashPath%>/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="<%=bashPath%>/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="<%=bashPath%>/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="<%=bashPath%>/css/animate.min.css" rel="stylesheet">
    <link href="<%=bashPath%>/css/style.min862f.css?v=4.1.0" rel="stylesheet">


</head>

<body class="gray-bg">
<div class="container">
        <div class="">
            <table id="userTable" class="table table-hover table-striped table-bordered">
                <caption>用户列表信息</caption>
                <thead>
                <tr>
                    <th >姓名</th>
                    <th >年龄</th>
                    <th >日期</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list}" var="user">
                        <tr >
                            <td>${user.name}</td>
                            <td>${user.age}</td>
                            <td>${user.birthday}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
</div>
<script src="<%=bashPath%>/js/jquery.min.js?v=2.1.4"></script>
<script src="<%=bashPath%>/js/bootstrap.min.js?v=3.3.6"></script>
<script src="<%=bashPath%>/js/content.min.js?v=1.0.0"></script>
<script src="<%=bashPath%>/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="<%=bashPath%>/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="<%=bashPath%>/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="<%=bashPath%>/js/demo/bootstrap-table-demo.min.js"></script>
<script></script>

</body>
</html>

