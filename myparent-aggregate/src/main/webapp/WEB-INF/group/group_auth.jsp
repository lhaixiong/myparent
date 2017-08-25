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
                <caption>权限列表信息</caption>
                <thead>
                <tr class="success">
                    <th><input type="checkbox" id="checkAll" name="checkAll" onchange="checkAll(this)" />所有菜单权限</th>
                    <th >权限名</th>
                </tr>
                </thead>
                <tbody id="groupAll">
                <c:if test="${! empty nodeAuth}">
                    <c:forEach items="${nodeAuth}" var="entry"><%--循环节点集合--%>
                        <c:set var="node" value="${entry.value}"/><%--节点--%>
                        <c:set var="menuList" value="${node.subPermission}"/><%--二级菜单--%>
                        <tr id="nodeTr_${node.id}">
                            <td><input type="checkbox" value="${node.id}" name="checkNode" class="checkNode permissId_${node.id}" onchange="checkNode(this)"/>${node.name}</td>
                            <td>
                                <table class="table table-hover table-striped table-bordered">
                                    <tbody>
                                        <c:forEach items="${menuList}" var="menu"><%--循环二级菜单--%>
                                            <c:set var="btnList" value="${menu.subPermission}"/>
                                            <tr class="menuTr">
                                                <td>
                                                    <input type="checkbox" id="menuParent_${menu.parent}" value="${menu.id}" name="checkMenu" class="checkMenu permissId_${menu.id}" onchange="checkMenu(this)"/>${menu.name}
                                                </td>
                                                <td>
                                                    <c:forEach items="${btnList}" var="btn"><%--循环按钮--%>
                                                        <input type="checkbox" value="${btn.id}" name="checkBtn" class="permissId_${btn.id}" onclick="checkBtn(this)"/>${btn.name}&nbsp;&nbsp;
                                                    </c:forEach>
                                                </td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
        </div>
        <div class="text-center">
            <button class="btn btn-success" onclick="updateGroupAuth(this)">提交</button>
            <button class="btn btn-primary" onclick="closeWindow()">取消</button>
        </div>
</div>
<script src="<%=bashPath%>/js/jquery.min.js?v=2.1.4"></script>
<script src="<%=bashPath%>/js/bootstrap.min.js?v=3.3.6"></script>
<script>
    $(function(){
        initGroupPidSet();
        <c:if test="${! empty requestScope.msg}">
            alert('${msg}');
        </c:if>
    })
    function initGroupPidSet(){
        var pIdArr=<%=request.getAttribute("groupPidSet")%>;//权限id数组Array
        $.each(pIdArr,function(idx,pid){
            $(".permissId_"+pid).first().prop("checked",true);
        });
    }
    function closeWindow(){
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
    }
    function updateGroupAuth(me){
        var pIds='';
        $("#groupAll").find("input:checked").each(function(idx,dom){
            pIds+=$(this).val()+",";
        });
        var gid=<%=request.getAttribute("gid")%>;
        $(me).attr("disabled",true);
        $.ajax({
            type:'post',
            url:'/group/updateGroupAuth',
            data:{
                "gid":gid,
                "pIds":JSON.stringify(pIds)
            },
            dataType:'json',
            success:function(result){
                $(me).attr("disabled",false);
                alert(result.info);
                if(result.code==200){
                    closeWindow();
                }
            }
        })
    }
    function checkBtn(me){
        //不用做事情，oh yeah
    }
    //选择或取消按钮二级菜单
    //逻辑1、选中二级菜单，该菜单下所有子菜单选中
    //2、若果一个及以上二级菜单选中，则它上面的一级菜单也选中
    //3  若果该node下的二级菜单全部取消，则该node也取消
    function checkMenu(me){
        var checkState=$(me).prop("checked");//true or false;
        $(me).parent().next().find("input:checkbox").each(function(idx,dom){
            $(this).prop("checked",checkState);
        });
        var isOneMenuOfNodeChecked=false;
        var nodeId=$(me).attr("id").split("_")[1];
        $("#nodeTr_"+nodeId).find(".checkMenu").each(function(idx,dom){
            checkState=$(this).prop("checked");
            if(checkState){
                isOneMenuOfNodeChecked=true;
            }
        });
        $("#nodeTr_"+nodeId).find(".checkNode").prop("checked",isOneMenuOfNodeChecked);
    }

    //选择或者取消一级菜单
    //逻辑：1、选中一级菜单，该菜单下所有子菜单选中
    //2、级联顶部那个checkbox
    function checkNode(me){
        var checkState=$(me).prop("checked");//true or false;
        $(me).parent().next().find("input:checkbox").each(function(idx,dom){
            $(this).prop("checked",checkState);
        });
        var isAllMenuChecked=true;//注意：这里通过是否全部二级菜单选中便可确认顶部checkbox是否选上，不用通过一级菜单
        $(".checkMenu").each(function(idx,dom){
            checkState=$(this).prop("checked");
            if(!checkState){
                isAllMenuChecked=false;
            }
        })
        if(isAllMenuChecked){
            $("#checkAll").prop("checked",true);
        }else{
            $("#checkAll").prop("checked",false);
        }
    }
    //选择或者取消所有权限
    function checkAll(me){
        var checkState=$(me).prop("checked");//true or false;
        $("input:checkbox").each(function(idx,dom){
            $(this).prop("checked",checkState);
        });
    }
</script>
</body>
</html>

