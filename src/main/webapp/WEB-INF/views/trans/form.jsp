<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>转账信息</title>
</head>
<body>

<form id="transInfoForm" class="form-horizontal" action="${ctx}/trans/save">
<c:if test="${'update'.equals(operate)}">
    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="serialNumber"><span style="color: red">*</span>流水号：</label>
        <div class="col-lg-8 col-md-8">
            <input type="text" id="serialNumber"  name="serialNumber" class="form-control input-sm" value="${transinfo.get("serial_number")}" placeholder="流水号" required>
        </div>
    </div>
</c:if>
    <div class="form-group">
    <label class="col-lg-3 col-md-3  control-label" for="userName"><span style="color: red">*</span>用户名：</label>
        <div class="col-lg-5 col-md-5">
            <input  id="userId" name="userId" value="${transinfo.get("user_id")}" hidden/>
            <input type="text" id="userName"  name="userName" class="form-control input-sm" value="${transinfo.get("user_name")}" placeholder="用户名" required>
        </div>
    </div>
    <div class="form-group">
    <label class="col-lg-3 col-md-3  control-label" for="deptName">所属部门：</label>
        <div class="col-lg-5 col-md-5">
            <input  id="deptId" name="deptId" value="${transinfo.get("dept_id")}" hidden/>
            <input type="text" id="deptName"  name="deptName" class="form-control input-sm" value="${transinfo.get("dept_name")}" placeholder="所属部门" required>
        </div>
    </div>
    <div class="form-group">
    <label class="col-lg-3 col-md-3  control-label" for="jobNumber"><span style="color: red">*</span>工号：</label>
        <div class="col-lg-8 col-md-8">
            <input type="text" id="jobNumber"  name="jobNumber" class="form-control input-sm" value="${transinfo.get("job_number")}" placeholder="工号" required>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="idNumber"><span style="color: red">*</span>身份证号：</label>
        <div class="col-lg-8 col-md-8">
            <input type="text" id="idNumber"  name="idNumber" class="form-control input-sm" value="${transinfo.get("id_number")}" placeholder="身份证号" required>
        </div>
    </div>
    <div class="form-group">
    <label class="col-lg-3 col-md-3  control-label" for="srcbankNumber"><span style="color: red">*</span>转出银行卡号：</label>
        <div class="col-lg-8 col-md-8">
            <input type="number" id="srcbankNumber"  name="srcbankNumber" class="form-control input-sm" value="${transinfo.get("srcbank_number")}" placeholder="转出银行卡号" required>
        </div>
    </div>
    <%--<div class="form-group">--%>
    <%--<label class="col-lg-3 col-md-3  control-label" for="srcbankName"><span style="color: red">*</span>转出银行名称：</label>--%>
        <%--<div class="col-lg-6 col-md-6">--%>
            <%--<input type="text" id="srcbankName"  name="srcbankName" class="form-control input-sm" value="${transinfo.get("srcbank_name")}" placeholder="转出银行名称" required>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div class="form-group">
        <label class="col-lg-3 col-md-3  control-label" for="transValue"><span style="color: red">*</span>转账金额(元)：</label>
        <div class="col-lg-6 col-md-6">
            <input type="number" id="transValue"  name="transValue"
                   <%--onkeyup="this.value=this.value.replace(/\D/g,'')"--%>
                   <%--onafterpaste="this.value=this.value.replace(/\D/g,'')"--%>
                   class="form-control input-sm" value="${transinfo.get("trans_value")}" placeholder="转账金额" required>
        </div>
    </div>
    <%--<div class="form-group">--%>
    <%--<label class="col-lg-3 col-md-3  control-label" for="targetbankNumber"><span style="color: red">*</span>转入银行卡号：</label>--%>
        <%--<div class="col-lg-8 col-md-8">--%>
            <%--<input type="number" id="targetbankNumber"  name="targetbankNumber" class="form-control input-sm" value="${transinfo.get("targetbank_number")}" placeholder="转入银行卡号" required>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div class="form-group">--%>
    <%--<label class="col-lg-3 col-md-3  control-label" for="targetbankName"><span style="color: red">*</span>转入银行名称：</label>--%>
        <%--<div class="col-lg-6 col-md-6">--%>
            <%--<input type="text" id="targetbankName"  name="targetbankName" class="form-control input-sm" value="${transinfo.get("targetbank_name")}" placeholder="转入银行名称" required>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div class="form-group">
    <label class="col-lg-3 col-md-3  control-label" for="bz">备注信息：</label>
        <div class="col-lg-9 col-md-9 ">
            <textarea  id="bz" style="width: 95%" name="bz">${transinfo.get("bz")}</textarea>
        </div>
    </div>
</form>
<script type="text/javascript">

    $(function(){
            $.getJSON("${ctx}/sys/dept/getallDept",function(data) {
                $('#deptName').autocomplete(data,{
                    minChars: 0,
                    mustMatch:true,
                    width:260,
                    max:10, //最多一次展示10条记录
                    // 下拉列表显示的字段 ，如果多个用表格格式化，如果单个 直接  return item.COUNTRY_NAME; 即可
                    formatItem: function(item,i, max) {
                        return '<table><tr><td width="180px;">' + item.name + '</td></tr></table>';//<td width="80px;">' + item.id + '</td>
                    },
                    // 指定 与 输入文字匹配的字段名
                    formatMatch: function(item,i, max) {
                        return item.id+item.name;
                    },
                    // 选中 某条记录在输入框里 显示的数据字段
                    formatResult: function(item) {
                        return item.name;
                    }
                });
                //选中 某条记录 触发的事件
                $('#deptName').result(function(event, item){
                    if(item){
                        if(item.id != $("#deptId").val()){
                            $("#deptId").val(item.id);
                        }
                    }else{
                        $("#deptId").val("");
                    }
                });
            });

            $.getJSON("${ctx}/basic/getalluser",function(data) {
                $('#userName').autocomplete(data,{
                    minChars: 0,
                    mustMatch:true,
                    width:260,
                    max:10, //最多一次展示10条记录
                    matchContains: true,
                    // 下拉列表显示的字段 ，如果多个用表格格式化，如果单个 直接  return item.COUNTRY_NAME; 即可
                    formatItem: function(item,i, max) {
                        return '<table><tr><td width="100px;">' + item.name + '</td><td width="180px;">' + item.idNumber + '</td></tr></table>';//<td width="80px;">' + item.id + '</td>
                    },
                    // 指定 与 输入文字匹配的字段名
                    formatMatch: function(item,i, max) {
                        return item.idNumber+item.name;
                    },
                    // 选中 某条记录在输入框里 显示的数据字段
                    formatResult: function(item) {
                        return item.name;
                    }
                });
                //选中 某条记录 触发的事件
                $('#userName').result(function(event, item){
                    if(item){
                        if(item.id != $("#userId").val()){
                            $("#userId").val(item.id);
                            $("#deptId").val(item.deptId);
                            $("#deptName").val(item.deptName);
                            $("#jobNumber").val(item.jobNumber);
                            $("#idNumber").val(item.idNumber);
                            $("#srcbankNumber").val(item.bankNumber);
                        }
                    }else{
                        $("#userId").val("");
                        $("#deptId").val("");
                        $("#deptName").val("");
                        $("#jobNumber").val("");
                        $("#idNumber").val("");
                        $("#srcbankNumber").val("");
                    }
                });
            });
    });
    $("#transInfoForm").validate({
        rules: {
            userName:{
                required:true
            },
            jobNumber:{
                required:true,
                maxlength:25,
                minlength:4
            },
            idNumber:{
                maxlength:25,
                minlength:12
            },
            srcbankNumber:{
                maxlength:32,
                minlength:12
            },
            targetbankNumber:{
                maxlength:32,
                minlength:12
            },
            srcbankName:{
                maxlength:100,
                minlength:2
            },
            targetbankName:{
                maxlength:100,
                minlength:2
            },
            bz:{
                maxlength:500,
                minlength:4
            }
        },
        messages:{
            name:{
                required:'用户名不能为空'
            },
            jobNumber:{
                required:'工号不能为空',
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
            },
            srcbankNumber:{
                maxlength:'不能超过{0}个字符',
                minlength:'不能少于{0}个字符'
            },
            targetbankNumber:{
                maxlength:'不能超过{0}个字符',
                minlength:'不能少于{0}个字符'
            },
            srcbankName:{
                maxlength:'不能超过{0}个字符',
                minlength:'不能少于{0}个字符'
            },
            targetbankName:{
                maxlength:'不能超过{0}个字符',
                minlength:'不能少于{0}个字符'
            }
        }
    });
</script>
</body>
</html>
