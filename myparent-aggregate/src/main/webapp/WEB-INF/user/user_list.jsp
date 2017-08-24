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
    <link href="<%=bashPath%>/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="<%=bashPath%>/js/plugins/layer/skin/layer.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="container" style="width: auto;">
        <div class="row">
            <form id="searchForm" action="/user/list" method="post" class="form-inline" role="form">
                <div class="form-group">
                    <input type="text" class="form-control" id="account" name="account" value="${requestScope.account}" placeholder="请输入用户名称">
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-primary" style="height: 34px;" onclick="searchForm()">
                        <span class="glyphicon glyphicon-search">搜索</span>
                    </button>
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-success" style="height: 34px;" onclick="addItem()">
                        <span class="glyphicon glyphicon-plus">添加</span>
                    </button>
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-danger" style="height: 34px;" onclick="batDelItem()">
                        <span class="glyphicon glyphicon-remove">批量删除</span>
                    </button>
                </div>
            </form>
        </div>
        <div class="row table-responsive">
            <table class="table table-hover table-striped table-bordered">
                <caption>用户列表信息</caption>
                <thead>
                <tr class="success">
                    <th><input type="checkbox" id="checkAll" name="checkAll" /></th>
                    <th >用户账号</th>
                    <th >昵称</th>
                    <th >状态</th>
                    <th >所在组</th>
                    <th >创建日期</th>
                    <th >操作</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list}" var="item">
                        <tr >
                            <td><input type="checkbox" name="checkItem" class="itemId" value="${item.id}"/></td>
                            <td>${item.account}</td>
                            <td>${item.nickname}</td>
                            <td>
                                <c:if test="${item.status==1}">可用</c:if>
                                <c:if test="${item.status==0}">不可用</c:if>
                            </td>
                            <td>
                                <c:if test="${item.groupId==1}">超级组</c:if>
                                <c:if test="${item.groupId!=1}">普通组</c:if>
                            </td>
                            <td>${item.createTime}</td>
                            <td>
                                <button type="button" class="btn btn-primary" onclick="editItem(this)">
                                    <span class="glyphicon glyphicon-edit">修改</span>
                                </button>
                                <button type="button" class="btn btn-danger" onclick="delItem(this)">
                                    <span class="glyphicon glyphicon-remove">删除</span>
                                </button>
                                <button type="button" class="btn btn-primary" onclick="toUserAuth(this)">
                                    <span class="glyphicon glyphicon-edit">编辑用户权限</span>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
</div>
<script src="<%=bashPath%>/js/jquery.min.js?v=2.1.4"></script>
<script src="<%=bashPath%>/js/bootstrap.min.js?v=3.3.6"></script>
<script src="<%=bashPath%>/js/plugins/iCheck/icheck.min.js"></script>
<script>
    var layer=parent.layer;
    $(function(){
        initIcheck();
        <c:if test="${! empty requestScope.msg}">
            alert('${msg}');
        </c:if>
    })
    function toUserAuth(me){
        var userId=$(me).parent().parent().children().find(".itemId").first().val();
        layer.open({
            type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层)
            title: '编辑用户权限',
            content: '${bashPath}/user/toUserAuth?userId='+userId,
            area: ['900px', '600px'],
            maxmin: true,
            end: function(index) {
                //location.href = location.href;
            },
            full: function(elem) {
                var win = window.top === window.self ? window : parent.window;
                $(win).on('resize', function() {
                    var $this = $(this);
                    elem.width($this.width()).height($this.height()).css({
                        top: 0,
                        left: 0
                    });
                    //elem.children('div.layui-layer-content').height($this.height() - 95);
                });
            }
        });
    }
    function batDelItem(){
        var ids=[];
        $(".table tbody input:checked").each(function(idx,dom){
            ids.push($(this).val());
        });
        if(ids.length<1){
            layer.alert("请选择至少一条记录!");
            return;
        }
        layer.confirm("确定删除么？",function(index){
            $.ajax({
                type:"POST",
                url:'${bashPath}/user/batDelete',
                data:{
                    "ids":JSON.stringify(ids)
                },
                dataType:"json",
                success:function(result){
                    layer.alert(result.info);
                    location.reload();
                }
            });
            layer.close(index);
        });
    }
    function delItem(me){
        var userId=$(me).parent().parent().children().find(".itemId").first().val();
        layer.confirm('确定删除该记录么？', function(index){
            $.ajax({
                type:"POST",
                url:'${bashPath}/user/delete?opt=2&id='+userId,
                dataType:"json",
                success:function(result){
                    layer.alert(result.info);
                    location.reload();
                }
            });
            layer.close(index);
        });

    }
    function editItem(me){
        var id=$(me).parent().parent().children().find(".itemId").first().val();
        layer.open({
            type:2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层)
            title:"修改",
            area: ['800px', '450px'],
            maxmin: true, //开启最大化最小化按钮
            content: '${bashPath}/user/detail?opt=3&id='+id,
            end: function(index) {
                location.href = location.href;
            }
        })
    }
    function addItem(){
        layer.open({
            type:2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层)
            title:"新增",
            area: ['800px', '450px'],
            maxmin: true, //开启最大化最小化按钮
            content: '${bashPath}/user/detail?opt=1',
//            yes: function(index, layero){
//                //do something//例如提交校验等等
//                layer.close(index); //如果设定了yes回调，需进行手工关闭
//            },
            end: function(index) {
                location.href = location.href;
            }
        })
    }
    function searchForm(){
        document.getElementById("searchForm").submit();
    }
    function initIcheck(){
        $("input").iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'icheckbox_square-green',
            increaseArea: '20%'
        })

        $('#checkAll').on('ifChanged', function(event) {
            $('.table tbody tr td').find('input').iCheck(event.currentTarget.checked ? 'check' : 'uncheck');
        });
    }

</script>
</body>
</html>

