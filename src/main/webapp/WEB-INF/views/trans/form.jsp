
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>转账信息</title>
</head>
<body>

<form id="transInfoForm" class="form-horizontal" action="${ctx}/trans/save">

    <div class="form-group">
        <input  id="id" name="id" value="${transinfo.get("user_id")}" hidden/>
        <label class="col-lg-3 col-md-3  control-label" for="loginName"><span style="color: red">*</span>登录名：</label>
        <div class="col-lg-4 col-md-4">
            <input type="text" id="loginName"  style="width: 150px" name="loginName" class="form-control input-sm" value="${transinfo.get("user_name")}" placeholder="登录名" required>
        </div>
    </div>




</form>

</body>
</html>
