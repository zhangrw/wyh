<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login Page</title>
    <%@ include file="/WEB-INF/common/common-js-style.jsp"%>

<link rel="stylesheet" type="text/css" href="${ctx}/static/portal/login/styles.css">

</head>
<body>

<h1>login page</h1>





<%--<form id="" action="${pageContext.request.contextPath}/login" method="post">--%>
    <%--<label>User Name</label><input tyep="text" name="userName" maxLength="40" />--%>
    <%--<label>Password</label> <input type="password"  name="password" />--%>
    <%--<input type="submit" value="login" />--%>
<%--</form>--%>
<%--&lt;%&ndash;用于输入后台返回的验证错误信息 &ndash;%&gt;--%>
<%--<P><c:out value="${message }" /></P>--%>


<div class="wrapper">
    <div class="container">
        <h1>Welcome</h1>
        <form class="form" action="${ctx}/login" method="post">
            <input id="name" name="userName" type="text" placeholder="用户名">
            <input id="pswd" name="password" type="password" placeholder="密&nbsp&nbsp&nbsp&nbsp码">
            <button type="submit" id="login-button">登 &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp 录</button></br>
            <button id="changePwd" style="margin-top:5px">
                <%--<a href="changePwd.jsp">忘记密码</a>--%>
                    忘记密码
            </button>
            <%--用于输入后台返回的验证错误信息 --%>
            <P><c:out value="${message }" /></P>
        </form>
    </div>

</div>
</body>
</html>