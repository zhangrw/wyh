<%--
  Created by IntelliJ IDEA.
  User: zhang.rw
  Date: 16-4-19
  Time: 上午10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<form id="user_form" class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="loginname"><span style="color: red">*</span>登录名：</label>
        <div class="col-lg-5 col-md-5">
            <input type="text" id="loginname"  style="width: 150px" name="loginname" class="form-control input-sm" value="${user.loginname }" placeholder="登录名" required>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="username"><span style="color: red">*</span>用户名：</label>
        <div class="col-lg-5 col-md-5 ">
            <input type="text" id="lccname1" style="width: 150px" name="username" value="${user.username }" class="form-control input-sm" placeholder="可输入用户名称简码" required>
            <span id="lccCode3" ></span>
        </div>
    </div>

    <c:if test="${empty user}">
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
    </c:if>

    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="email">邮箱：</label>
        <div class="col-lg-5 col-md-5">
            <input type="text" id="email" name="email" class="form-control input-sm" placeholder="邮箱" value="${user.email}" >
        </div>
    </div>
    <%--<div class="form-group">--%>
        <%--<label class="col-lg-3 col-md-3  control-label" for="status"><span style="color: red">*</span>状态：</label>--%>
        <%--<div class="col-lg-5 col-md-5">--%>
            <%--<select name="status" class="form-control input-sm" id="status">--%>
                <%--<option value="1">有效</option>--%>
                <%--<option value="0">无效</option>--%>
            <%--</select>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div class="form-group">--%>
        <%--<label class="col-lg-3 col-md-3  control-label" for="remark">用户描述：</label>--%>
        <%--<div class="col-lg-5 col-md-5">--%>
            <%--<textarea rows="3" id="remark" class="form-control" name="remark" placeholder="用户描述">${user.remark}</textarea>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="roles">角色：</label>
        <div class="col-lg-5 col-md-5">

            <c:forEach items="${roles}" var="role">
                <div class="checkbox-inline" style="margin-left:10px;">
                    <input name="roleId" type="checkbox" value="${role.id}"> ${role.rolename}(${role.description})
                </div>
            </c:forEach>
        </div>
    </div>
    <input type="hidden" name="id" id="id" value="${user.id}">
</form>

<script>
         <c:choose>
        <c:when test="${empty user}">
        $("#loginname").focus();
        </c:when>
        <c:otherwise>
        $("#lccname").focus();
        </c:otherwise>
        </c:choose>
        $("#user_form").validate({
            rules: {
                lccname:{
                    required:true
                },
                <c:if test="${empty user}">
                loginname:{
                    required:true,
                    maxlength:30,
                    minlength:2,
                    remote:{
                        type:"POST",
                        url:'${ctx}/user/checkloginnameExists',
                        dataType:'json',
                        data:{
                            loginname:function(){
                                var l_n = $("#loginname").val();
                                return l_n;
                            }
                        },
                        dataFilter: function(data) {
                            data= eval("("+data+")");
                            if(data){
                                if(!data.result){
                                    return true;
                                }
                                return false;
                            }
                            return false;
                        }
                    }
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
                },
                </c:if>
                //organization:{
                //	required:true
                //},
                email:{
                    email:true,
                    maxlength:30,
                    minlength:4
                },
                status:{
                    required:true
                },
                remark:{
                    maxlength:200,
                    minlength:4
                }
            },
            messages:{
                lccname:{
                    required:'用户名不能为空'
                },
                <c:if test="${empty user}">
                loginname:{
                    required:'登录名不能为空',
                    maxlength:'不能超过{0}个字符',
                    minlength:'不能少于{0}个字符',
                    remote:'登录名已存在'
                },
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
                },
                </c:if>
                //organization:{
                //	required:'请选择组织机构'
                //},
                email:{
                    maxlength:$.format('不能超过{0}个字符'),
                    minlength:$.format('不能少于{0}个字符')
                },
                status:{
                    required:'请选择用户状态'
                },
                remark:{
                    maxlength:'不能超过{0}个字符',
                    minlength:'不能少于{0}个字符'
                }
            }
        });
        <c:if test="${not empty user}">
        $("#loginname").val('${user.loginname}');
//        $("#loginname").attr('readonly',true);

        <%--$("#status").val('${user.status}');--%>
        <c:if test="${not empty ownRoles}">
        var ownRoles = [
            <c:forEach items="${ownRoles}" var="role" varStatus="status">
            {id:'${role.id}'}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ];

        for(var i=0;i<ownRoles.length;i++){
            $('#user_form [name =roleId]').each(function(j){
                var cv = this.value;
                if(ownRoles[i].id==cv){
                    this.checked = true;
                }
            });
        }
        </c:if>
        </c:if>

//    });

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

//    function changeProper(){
//        var code = $("#lccname").val();
//        var codeArr = code.split(";");
//        $("#name").val($("#lccname").find("option:selected").text());
//        $("#userCode").val(codeArr[0]);
//        $("#projectId").val(codeArr[1]);
//        $("#lccCode").val(codeArr[2]);
//    }
</script>
