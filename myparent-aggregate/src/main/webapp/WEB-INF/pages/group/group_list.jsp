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
    <title>H+ 后台主题UI框架 - Bootstrap Table</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

    <link rel="shortcut icon" href="<%=bashPath%>/img/favicon.ico">
    <link href="<%=bashPath%>/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="<%=bashPath%>/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="<%=bashPath%>/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="<%=bashPath%>/css/animate.min.css" rel="stylesheet">
    <link href="<%=bashPath%>/css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <link href="<%=bashPath%>/css/plugins/iCheck/custom.css" rel="stylesheet">


</head>

<body class="gray-bg">
<div class="container" style="width: auto;">
        <div class="row">
            <form id="searchForm" action="/group/list" method="post" class="form-inline" role="form">
                <div class="form-group">
                    <input type="text" class="form-control" id="name" name="name" value="${requestScope.name}" placeholder="请输入组名称">
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-primary" style="height: 34px;" onclick="searchForm()">
                        <span class="glyphicon glyphicon-search">搜索</span>
                    </button>
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-success" style="height: 34px;" onclick="addItem()">
                        <span class="glyphicon glyphicon-plus">添加组</span>
                    </button>
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-danger" style="height: 34px;" onclick="deleteItem()">
                        <span class="glyphicon glyphicon-remove">删除组</span>
                    </button>
                </div>
            </form>
        </div>
        <div class="row table-responsive">
            <table class="table table-hover table-striped table-bordered">
                <caption>用户组列表信息</caption>
                <thead>
                <tr class="success">
                    <th><input type="checkbox" id="checkAll" name="checkAll" /></th>
                    <th >组名</th>
                    <th >类型</th>
                    <th >状态</th>
                    <th >创建者</th>
                    <th >创建日期</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list}" var="item">
                        <tr >
                            <td><input type="checkbox" name="checkItem" /></td>
                            <td>${item.name}</td>
                            <td>${item.type}</td>
                            <td>${item.status}</td>
                            <td>${item.creater}</td>
                            <td>${item.createTime}</td>
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
<script src="<%=bashPath%>/js/plugins/iCheck/icheck.min.js"></script>
<script>
    $(function(){
        initIcheck();
    })
    function searchForm(){
        document.getElementById("searchForm").submit();
    }
    function initIcheck(){
        $("input").iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'icheckbox_square-green',
            increaseArea: '20%'
        })
        $('.table tbody tr').on('click', function(event) {
            var curTr = $(this);
            var curInput = $(curTr).children('td').eq(0).find('input');
            $(curInput).on('ifChecked', function(e) {
                $(curTr).css('background-color', '#EEEEEE');
            });
            $(curInput).on('ifUnchecked', function(e) {
                $(curTr).removeAttr('style');
            });
            $(curInput).iCheck('toggle');
        }).find('input').each(function() {
            $(this).on('ifChecked', function(e) {
                $(this).parents('tr').css('background-color', '#EEEEEE');
            });
            $(this).on('ifUnchecked', function(e) {
                $(this).parents('tr').removeAttr('style');
            });
        });

        $('#checkAll').on('ifChanged', function(event) {
            $('.table tbody tr td').find('input').iCheck(event.currentTarget.checked ? 'check' : 'uncheck');
        });
    }

</script>

</body>
</html>

