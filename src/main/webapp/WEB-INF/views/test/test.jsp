<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/26
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title>测试</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="${ctx}/static/bootstrap-3.3.5/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="${ctx}/static/bootstrap-3.3.5/css/bootstrap-theme.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script type="text/javascript" src="${ctx}/static/jquery/jquery-1.12.3.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="${ctx}/static/bootstrap-3.3.5/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/datepicker/bootstrap-datepicker.js"></script>
</head>
<body>

<div class="container">
    <div  id='datetimepicker1' width="100%">
        <script type="text/javascript">
            $(function () {
                $('#datetimepicker1').datepicker(
                        {
                            weekStart :1
                        }
                ).on("changeMonth",function(e){
                    alert();
                });


            });
        </script>
    </div>
</div>

<script type="text/javascript">


</script>

</body>
</html>
