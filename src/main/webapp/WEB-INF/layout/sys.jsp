<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <title><sitemesh:title /></title>
    <!-- 设置根路径 -->
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <%@ include file="/WEB-INF/common/common-js-style.jsp"%>
    <%@ include file="/WEB-INF/common/table-js-style.jsp"%>
    <%@ include file="/WEB-INF/common/less-common-js-style.jsp"%>
    <%@ include file="/WEB-INF/common/tree-js-style.jsp"%>
    <%@ include file="/WEB-INF/common/custom-js-style.jsp"%>
    <%@ include file="/WEB-INF/common/backtop.jsp"%>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />

    <sitemesh:head />
    <style>
        /*--select 样式--*/
        #select{
            border-radius: 4px;
            background-color: #f5f5f5;
            border: 1px solid #cccccc;
            color: #404040;
        }
        #selectWrap{
            margin: 15px;
            border-radius: 4px;
            background-color: #f5f5f5;
            border: 1px solid #cccccc;
            color: #404040;
            margin-bottom: 6px;
        }
        .select-main{
            margin: 2px;
            height: auto;
            overflow: hidden;
            font-size: 14px;
            background-color: #ffffff;
            padding: 10px;
        }
        .select-main ol li{
            float: left;
            margin-right: 15px;
        }
        .select-main input[type=checkbox],.select-main input{
            float: left;
        }
        .select-main label{
            float: left;
            margin-left: 3px;
            margin-right: 4px;
            font-weight: normal;

        }
    </style>
</head>
<body>
<div id="screenWidth" class="container">

    <div  id="fixTopDiv" class="bs-navbar-fixed-top" >
        <div class="topPic topPicportal">
        </div>
        <!-- navbar begin -->
        <div class="navbar navbar-default">
            <%--<div class="navbar-header">--%>
                <%--<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">--%>
                    <%--<span class="icon-bar"></span>--%>
                    <%--<span class="icon-bar"></span>--%>
                    <%--<span class="icon-bar"></span>--%>
                <%--</button>--%>
            <%--</div>--%>


    <div id="menuParent" class="collapse navbar-collapse navbar-ex1-collapse" style="position: static;font-weight:bold">

            <ul class="nav navbar-nav pull-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="glyphicon glyphicon-user"></i>
                        <shiro:principal property="name" />
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="javascript:{}" onclick="goTo('${ctx}/login/logout');">退出系统</a></li>
                    </ul>
                </li>
            </ul>



        <ul class="nav navbar-nav" id = "backindex">
            <li id="systemManage_nav"><a href="javascript:{}" onclick="goTo('${ctx}/index')"><i class="glyphicon glyphicon-home" title="工作台"></i></a></li>
                        <%--<li class="dropdown">--%>
                                <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"--%>
                                   <%--aria-expanded="false">系统管理 <span class="caret"></span></a>--%>
                                <%--<ul class="dropdown-menu">--%>
                                    <%--<shiro:hasPermission name="user:add">--%>
                                        <%--<li id="user_nav"><a href="javascript:{}" onclick="goTo('${ctx}/sys/user');">用户管理</a></li>--%>
                                    <%--</shiro:hasPermission>--%>
                                    <%--<shiro:hasPermission name="role:add">--%>
                                        <%--<li id="role_nav"><a href="javascript:{}" onclick="goTo('${ctx}/sys/role');">角色管理</a></li>--%>
                                    <%--</shiro:hasPermission>--%>
                                    <%--<shiro:hasPermission name="permission:add">--%>
                                        <%--<li id="permissions_nav"><a href="javascript:{}" onclick="goTo('${ctx}/sys/permission');">权限管理</a></li>--%>
                                    <%--</shiro:hasPermission>--%>
                                <%--</ul>--%>
                            <%--</li>--%>

                        <%--<li><a href="javascript:{}">测试一级菜单</a></li>--%>

                        <%--<li class="dropdown">--%>
                            <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"--%>
                               <%--aria-expanded="false">测试多级菜单 <span class="caret"></span></a>--%>
                            <%--<ul class="dropdown-menu">--%>
                                <%--<li><a href="#" >测试</a></li>--%>
                                <%--<li class="dropdown-submenu">--%>
                                    <%--<a href="#" >二级菜单 <span ></span></a>--%>
                                    <%--<ul class="dropdown-menu">--%>
                                        <%--<li><a href="javascript:{}">三级菜单</a></li>--%>

                                        <%--<li class="dropdown-submenu"><a href="javascript:{}">三级菜单</a>--%>
                                            <%--<ul class="dropdown-menu">--%>
                                                <%--<li >--%>
                                                    <%--<a href="#">四级菜单</a>--%>
                                                <%--</li>--%>
                                                <%--<li >--%>
                                                    <%--<a href="#">四级应该足够了</a>--%>
                                                <%--</li>--%>

                                            <%--</ul>--%>
                                        <%--</li>--%>
                                    <%--</ul>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</li>--%>
                </ul>

            </div>
        </div>

    </div>

    <!-- navbar end -->
    <div id="clearFixTop" style="min-height: 580px">
        <sitemesh:body />
    </div>
    <footer class="footer" style="text-align: center;clear:both;">
        Copyright © 2016-2017.  公司运维.  All rights reserved
    </footer>
</div>
</body>
<script type="text/javascript">

    var menu = $("#systemManage_nav");
    var menuParent = $("#menuParent");
    $(document).ready(function(){
        var menuArr = new Array();
        $.getJSON("${ctx}/sys/menu/menutree",{},function(data){
            for( var i = 0 ; i < data.length ; i++ ){
                menuArr = menuArr.concat(listNodes(data[i]));
            }
            $(menuArr).each(function(i , node){
                if( node.type == 1 ){ // 菜单组
                    var newMenuHtml = "<ul class='nav navbar-nav'><li class='dropdown'><a href='#' class='dropdown-toggle'" +
                            "  data-toggle='dropdown' role='button' " +
                            " aria-haspopup='true' aria-expanded='false'>"+node.name+" <span class='caret'></span></a> "+
                            "<ul class='dropdown-menu' id = '"+node.id+"'></ul></li></ul>";
                    if( node.parentId == '0' ){ // 第一层菜单  根div内插入元素
                        menuParent.append(newMenuHtml);
                    }else{ // 不是第一层  需要查找父级菜单插入元素
                        menuParent.find(node.parentId).append(newMenuHtml);
                    }
                }else{ // 菜单项
                    var menuHtml ="<li id='"+node.id+"'><a href='javascript:{}' onclick=\"goTo('${ctx}"+node.url+"');\">"+node.name+"</a></li>";
                    menuParent.find("ul#"+node.parentId).append(menuHtml);
                }
            });
        });

    });

// 遍历菜单树节点
    function listNodes( node ){
        var nodeArr = new Array();
        nodeArr.push(node);
        if( node.children.length == 0 ){
            return nodeArr;
        }else{
            for(var i = 0 ; i < node.children.length ; i++ ){
                var tempNode = node.children[i];
                if( tempNode.children.length == 0  ){
                    nodeArr.push(tempNode);
                }else{
                    return nodeArr.concat(listNodes(tempNode));
                }
            }
        }
        return nodeArr;
    }


</script>
</html>