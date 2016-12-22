<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>基本信息</title>
</head>
<body>
<form id="shirouser" class="form-horizontal" action="${ctx}/basic/save">
    <div class="form-group">
        <input  id="id" name="id"value="${shirouser.id}" hidden/>
        <label class="col-lg-3 col-md-3  control-label" for="loginName"><span style="color: red">*</span>登录名：</label>
        <div class="col-lg-4 col-md-4">
            <input type="text" id="loginName"  style="width: 150px" name="loginName" class="form-control input-sm" value="${shirouser.loginName}" placeholder="登录名" required>
        </div>
        <c:if test="${not empty shirouser.id}">
        <div class="col-lg-5 col-md-5">
                <input type="checkbox" name="checkbox" onclick="changepwd()" title="勾选开启编辑密码功能"/> 编辑登录密码
        </div>
        </c:if>
    </div>
    <div id="cgepwd"
            <c:if test="${not empty shirouser.id}">
            style="display: none"
            </c:if>
    >
        <div class="form-group">
            <label class="col-lg-3 col-md-3  control-label" for="password"><span
                    style="color: red">*</span>登录密码：</label>
            <div class="col-lg-5 col-md-5">
                <input type="password" id="password" name="password" class="form-control input-sm" placeholder="登录密码" required onkeydown="return noSpace(event)">
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 col-md-3  control-label" for="password_again"><span style="color: red">*</span>确认密码：</label>
            <div class="col-lg-5 col-md-5">
                <input type="password" id="password_again" name="password_again" class="form-control input-sm" placeholder="确认密码" required onkeydown="return noSpace(event)">
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="name"><span style="color: red">*</span>用户名：</label>
        <div class="col-lg-5 col-md-5 ">
            <input type="text" id="name" style="width: 150px" name="name" value="${shirouser.name }" class="form-control input-sm" placeholder="输入用户名称" required>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="sex"><span style="color: red">*</span>性别：</label>
        <div class="col-lg-5 col-md-5 ">
            <select id="sex" name="sex" class="col-lg-5 col-md-5 form-control input-sm">
                <option value="1" <c:if test="${shirouser.sex == 1}">selected</c:if> >男</option>
                <option value="2" <c:if test="${shirouser.sex == 2}">selected</c:if> >女</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="jobNumber"><span style="color: red">*</span>工号：</label>
        <div class="col-lg-6 col-md-6 ">
            <input type="text" id="jobNumber" name="jobNumber" value="${shirouser.jobNumber}" class="form-control input-sm" placeholder="输入工号" required>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="idNumber">身份证号：</label>
        <div class="col-lg-6 col-md-6 ">
            <input type="text" id="idNumber" name="idNumber" value="${shirouser.idNumber}" class="form-control input-sm" placeholder="输入身份证号" required>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="bankNumber">银行卡号：</label>
        <div class="col-lg-6 col-md-6 ">
            <input type="text" id="bankNumber" name="bankNumber" value="${shirouser.bankNumber}" class="form-control input-sm" placeholder="输入银行卡号" required>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="deptName">所属部门：</label>
        <div class="col-lg-6 col-md-6 ">
            <input type="text" id="deptName" name="deptName" value="${shirouser.deptName}" class="form-control input-sm" placeholder="选择所属部门" required>
            <div id="deptZtree" class="tree-select">
                <div id="menu_ztree" class="ztree" style="height:190px;overflow-y:scroll;" ></div>
            </div>
            <input id="deptId" name="deptId" value="${shirouser.deptId}" hidden/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="bz">备注信息：</label>
        <div class="col-lg-9 col-md-9 ">
            <textarea  id="bz" style="width: 95%" name="bz">${shirouser.bz}</textarea>
        </div>
    </div>

</form>

<script type="text/javascript">
    //密码和确认密码输入框不能输入空格
    function noSpace(e){
        var keynum;
        var keychar;
        var numcheck;
        if(window.event) // IE
        {
            keynum = e.keyCode
        }
        else if(e.which) // Netscape/Firefox/Opera
        {
            keynum = e.which
        }
        keychar = String.fromCharCode(keynum);
        numcheck = /\s/;
        return !numcheck.test(keychar);
    }


    $("#shirouser").validate({
        rules: {
            name:{
                required:true
            },
            <%--<c:if test="${empty shirouser.id}">--%>
            loginName:{
                required:true,
                maxlength:30,
                minlength:2,
                remote:{
                    type:"POST",
                    url:'${ctx}/sys/user/checkloginnameExists',
                    dataType:'json',
                    data:{
                        "loginname":function(){
                            return $("#loginName").val();
                        },
                        "id": function(){
                            return $("#id").val();
                        }
                    },
                    dataFilter: function(data) {
                        data= eval("("+data+")");
                        if(data){
                            return !data.result;
                        }
                        return false;
                    }
                }
            },
            <%--</c:if>--%>
            jobNumber:{
                required:true,
                maxlength:25,
                minlength:4
            },
            bankNumber:{
                maxlength:25,
                minlength:12
            },
            idNumber:{
                maxlength:25,
                minlength:12
            },
            bz:{
                maxlength:200,
                minlength:4
            },
            password:{
                required:true,
                maxlength:30,
                minlength:6
            },
            password_again:{
                required:true,
                maxlength:30,
                minlength:6,
                equalTo: "#password"
            }
        },
        messages:{
            name:{
                required:'用户名不能为空'
            },
            <%--<c:if test="${empty shirouser.id}">--%>
            loginName:{
                required:'登录名不能为空',
                maxlength:'不能超过{0}个字符',
                minlength:'不能少于{0}个字符',
                remote:'登录名已存在'
            },

            <%--</c:if>--%>
            jobNumber:{
                required:'工号不能为空',
                maxlength:'不能超过{0}个字符',
                minlength:'不能少于{0}个字符'
            },
            bankNumber:{
                maxlength:'不能超过{0}个字符',
                minlength:'不能少于{0}个字符'
            },
            idNumber:{
                maxlength:'不能超过{0}个字符',
                minlength:'不能少于{0}个字符'
            },
            bz:{
                maxlength:'不能超过{0}个字符',
                minlength:'不能少于{0}个字符'
            }
            ,
            password:{
                required:'登录密码不能为空',
                maxlength:$.format('不能超过{0}个字符'),
                minlength:$.format('不能少于{0}个字符')
            },
            password_again:{
                required:'确认密码不能为空',
                maxlength:$.format('不能超过{0}个字符'),
                minlength:$.format('不能少于{0}个字符'),
                equalTo: "两次密码输入不一致"
            }
        }
    });

    function changepwd(){
        if( $("input[name=checkbox]")[0].checked ){
            $("#cgepwd")[0].style.display="block"

            // 动态设置js validate的校验规则
            $("#password").rules("add",
                    {
                        required:true,
                        maxlength:30,
                        minlength:6
                    });
            $("#password_again").rules("add",
                    {
                        required:true,
                        maxlength:30,
                        minlength:6,
                        equalTo: "#password"
                    });
        }else{
            $("#cgepwd")[0].style.display="none"
            // 动态设置js validate的校验规则
            $("#password").rules("remove");
            $("#password_again").rules("remove");
        }
    }


    $("#deptName").click(function(e){
        $("#deptZtree").css('width',$(this).outerWidth());
        $("#deptZtree").toggle();
    });

    var setting = {
        async: {
            enable: true,
            type:"post",
            url:"${ctx}/sys/dept/getallDept"
        },
        data: {
            key:{
                name: "name"
            },
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId",
                rootPId: 0
            }
        }
        ,callback: {
            onAsyncSuccess: onAsyncSuccess,
            onClick:clickNode
        }
    };
    function onAsyncSuccess(event, treeId, treeNode, msg) {
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        treeObj.expandAll(true);
    }
    function clickNode(event, treeId, treeNode){
            $("#deptName").val(treeNode.name);
            $("#deptId").val(treeNode.id);
            $("#deptZtree").toggle();
    }
    $.fn.zTree.init($("#menu_ztree"), setting);
</script>
</body>
</html>
