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
    <title>aa</title>

    <link rel="shortcut icon" href="<%=bashPath%>/img/favicon.ico">
    <link href="<%=bashPath%>/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="container" style="width: auto;">
    <div class="row">
        <c:if test="${!empty item}">
            <form id="detailForm" action="/group/saveOrUpdate" method="post" class="form-horizontal" role="form">
                <input type="hidden" name="id" value="${item.id}">
                <input type="hidden" name="opt" value="${requestScope.opt}">
                <div class="form-group">
                    <label for="name" class="col-sm-3 control-label">名称:</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="name" name="name" value="${item.name}" placeholder="请输入组名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="status" class="col-sm-3 control-label">状态:</label>
                    <div class="col-sm-6">
                        <select class="form-control" id="status" name="status" value="${item.status}" placeholder="请输入组状态">
                            <option value="1" <c:if test="${item.status==1}">selected="selected" </c:if> >可用</option>
                            <option value="0" <c:if test="${item.status==0}">selected="selected" </c:if>>不可用</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="type" class="col-sm-3 control-label">类型:</label>
                    <div class="col-sm-6">
                        <select class="form-control" id="type" name="type" value="${item.type}" placeholder="请输入组类型">
                            <option value="0" <c:if test="${item.type==0}">selected="selected" </c:if>>普通组</option>
                            <option value="1" <c:if test="${item.type==1}">selected="selected" </c:if> >超级组</option>
                        </select>
                    </div>
                </div>
                <div style="text-align: center;">
                    <button type="button" class="btn btn-primary" onclick="submitForm(this)">提交</button>
                    <button type="button" class="btn btn-default" onclick="closeWindow()">取消</button>
                </div>
            </form>
        </c:if>
    </div>
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
    function submitForm(me){
        var arr=$("#detailForm").serializeArray();
        var data={};
        for(var i=0;i<arr.length;i++){
            data[arr[i].name]=arr[i].value;
        }
        $(me).attr("disabled",true);
        $.ajax({
            type:"POST",
            url:"/group/saveOrUpdate",
            data:data,
            dataType:"json",
            success:function(result){
                $(me).attr("disabled",false);
                alert(result.info);
                if(200==result.code){
                    closeWindow();
                }
            }
        })
    }
    function closeWindow(){
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
    }
</script>
</body>
</html>

