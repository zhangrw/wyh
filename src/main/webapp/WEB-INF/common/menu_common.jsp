<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 16-5-5
  Time: 上午10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

</head>

<body>
<script type="text/javascript">
    $(document).ready(function(){

        $.getJSON(
                '<c:url value="/sys/menu/menuTree" />',
                {
                    param : "sanic"
                },
                function(data) {

                    var bars = new Array();
                    var downItem = new Array();
                    var menu = $("#dynamicMenu");

                    //此处返回的data已经是json对象   循环 记录 构建 二级菜单
                    //以下其他操作同第一种情况
                    $.each(
                            data,
                            function(idx, item) {
                                var i = 0;
                                var parentId = item['PARENT'] ? item['PARENT']
                                        : item['parent'];
                                mu_name = item['TEXT'] ? item['TEXT']
                                        : item['text'];
                                mu_id = item['ID'] ? item['ID']
                                        : item['id'];
                                mu_target = item['TARGET'] ? item['TARGET']
                                        : item['target'];

                                //如果是模块    则显示到横排菜单中
                                if (parentId == 1) {
                                    i++;
                                    this.data = new Object();
                                    var li = $(" <li></li>");
                                    var div= "<div class='p_qtitle'>"+mu_name+"</div>";
                                    var dl="<dl id='menu_"+mu_id+"'></dl>"
                                    li.append($(div));
                                    li.append($(dl));
                                    menu.append(li);
                                    return true;
                                }

                                menu_it = new Object();
                                var menuItem = "<dd id='menu_item"  + mu_id +  "' ><a href='javascript:{}' onclick='goTo(\"${ctx}"
                                        + mu_target
                                        + "\");'>"
                                        + mu_name
                                        + "</a></dd>";

                                menu_it["li"] = menuItem;
                                menu_it["pid"] = parentId;

                                downItem.push(menu_it);

                            });

                    $.each(downItem,
                            function(idx, item) {
                                var pid = item.pid;
                                findStr = "#menu_"
                                        + pid;
                                var e_li = $(item.li);
                                menu.find(findStr)
                                        .append(e_li);
                            });
                });

    });
</script>
<div id="brwrap">
    <!--head-->
    <div id="login">
    </div>
    <!--head end-->
    <div id="mainContent">
        <div class="portal_quality">
            <ul id="dynamicMenu">

            </ul>
        </div>
    </div>

</div>
</body>
</html>

