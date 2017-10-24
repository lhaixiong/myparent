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
    <title>操作日志</title>

    <link rel="shortcut icon" href="<%=bashPath%>/img/favicon.ico">
    <link href="<%=bashPath%>/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="<%=bashPath%>/js/plugins/layer/skin/layer.css" rel="stylesheet">
    <link href="<%=bashPath%>/js/plugins/My97DatePicker/skin/WdatePicker.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="container" style="width: auto;">
    <div class="row">
        <form id="searchForm" action="/operatelog/list" method="post" class="form-inline" role="form">
            <div class="form-group">
                <input type="text" class="form-control" id="datemin" name="datemin" onfocus="WdatePicker({lang:'auto',dateFmt:'yyyy-MM-dd'})" id="datemin" name="datemin" value="${datemin}" placeholder='开始日期'  style="width:120px;">
                -
                <input type="text" class="form-control" id="datemax" name="datemax" onfocus="WdatePicker({lang:'auto',dateFmt:'yyyy-MM-dd'})" id="datemax" name="datemax" value="${datemax}" placeholder='结束日期'  style="width:120px;">

            </div>
            <div class="form-group">
                <label for="creator" class="col-sm-3 control-label">创建人:</label>
                <div class="col-sm-6">
                    <select class="form-control" id="creator" name="creater" value="${creater}" placeholder="创建人">
                        <c:forEach items="${allUsers}" var="item">
                            <option value="${item.id}" <c:if test="${item.id==creator}">selected="selected" </c:if> >${item.account}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <input type="text" class="form-control" id="keyword" name="keyword" value="${keyword}" placeholder="输入路径或名字">
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-primary" style="height: 34px;" onclick="searchForm()">
                    <span class="glyphicon glyphicon-search">搜索</span>
                </button>
            </div>
        </form>
    </div>
    <div class="row table-responsive">
        <c:if test="${!empty pageBean}">
            <div style="float: right;">共有记录 ${pageBean.count} 条</div>
        </c:if>
        <table class="table table-hover table-striped table-bordered">
            <caption>操作日志列表信息</caption>
            <thead>
            <tr class="success">
                <th >日志ID</th>
                <th >日期</th>
                <th >操作用户</th>
                <th >访问路径</th>
                <th >名称</th>
                <%--<th >参数</th>--%>
            </tr>
            </thead>
            <tbody>
            <c:if test="${! empty pageBean && !empty pageBean.result}">
                <c:forEach items="${pageBean.result}" var="item">
                    <tr>
                        <td>${item.id}</td>
                        <td>${item.date}</td>
                        <td>${item.creater}</td>
                        <td>${item.accessPath}</td>
                        <td>${item.accessName}</td>
                        <%--<td>${item.params}</td>--%>
                    </tr>
                </c:forEach>
            </c:if>

            </tbody>
        </table>
    </div>
    <c:if test="${! empty pageBean && !empty pageBean.result && pageBean.count>0}" >
        <div class="admin-table-page">
            <div id="page" class="page"></div>
        </div>
    </c:if>
</div>
<script src="<%=bashPath%>/js/jquery.min.js?v=2.1.4"></script>
<script src="<%=bashPath%>/js/bootstrap.min.js?v=3.3.6"></script>
<script src="<%=bashPath%>/js/plugins/My97DatePicker/WdatePicker.js?now=$dateUtils.getNow()"></script>
<script src="<%=bashPath%>/js/plugins/layer/layer.min.js"></script>
<script>
    $(function(){
        <c:if test="${! empty requestScope.msg}">
        alert('${msg}');
        </c:if>
    })

</script>
</body>
</html>

