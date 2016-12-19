<%--
  Created by IntelliJ IDEA.
  User: zhang.rw
  Date: 16-4-21
  Time: 下午10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>

    <title>角色管理</title>
</head>
<body>

<form class="well bs-bdcor form-inline" id="searchForm">
    角色名：
    <input type="text" id="s_roleName" name="s_roleName" class="form-control input-sm"/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <button type="button" id="btnQuery" class="btn btn-primary btn-align-right btn-sm">查询</button>
</form>

<div id="alert" class="alert alert-danger" hidden>
    <strong>Oh snap!</strong>
</div>

<div id="message" class="alert alert-success" hidden>
    <button data-dismiss="alert" class="close">&times;</button>
    <span id="messageSpanId"></span>
</div>


<div id="jqgrid">
    <table id="grid"></table>
    <div id="pager"></div>
</div>

<div id='dialog-confirm' class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">角色信息</h4>
            </div>
            <div class="modal-body">
                <p>加载中……</p>
            </div>
            <div class="modal-footer">
                <button type="button" id ='cancel' class="btn btn-default btn-sm" tabindex="1001">取消</button>
                <button type="button" id ='do_save' class="btn btn-primary btn-sm" tabindex="1000">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id='dialog-delete' class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">删除</h4>
            </div>
            <div class="modal-body">
                <p>是否确定删除？</p>
            </div>
            <div class="modal-footer">
                <button type="button" id ='cancel2' class="btn btn-default btn-sm" tabindex="1001">取消</button>
                <button type="button" id ='do_delete' class="btn btn-primary btn-sm" tabindex="1000">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<!--  
    <div id='dialog-tailInfo' class="modal fade">
     <div class="modal-dialog">
	      <div class="modal-content">
	    <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true" >&times;</button>
	    <h4>角色关联机尾信息</h4>
	    </div>
	    <div class="modal-body">
	    <p>    </p>
	    </div>
	    <div class="modal-footer">
	      <button id ='cancel3' type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
	      <button id ='do_save_tail' type="button" class="btn btn-sm btn-primary">确定</button>
	    </div> 
       </div>
	    </div>
	</div>

<div id="tip_role" class="adp_roll" >
<ul class="dropdown-menu" style="display:block;min-width:120px; ">
<%--<shiro:hasPermission name="sys:role-tail">--%>
  <%--<li>--%>
  <%--<a href="javascript:{}" onClick="queryTail();">--%>
  <%--<span class="glyphicon glyphicon-random"></span>	--%>
  <%--<span>关联机尾</span>	--%>
  <%--</a>	--%>
  <%--</li>--%>
<%--</shiro:hasPermission>--%>
</ul>
</div>
-->
<script type="text/javascript">
    $(function(){
        $("#tip_role").mouseleave( function(){ $(this).hide(); });
    });

    //绑定关闭tooltip
    toolTipClose(["#tip_role"], '.btn-link'  );

</script>

<script type="text/javascript">

    $(function(){
        $("#role_nav").addClass('active');
        var option = {
            url : '${ctx}/sys/role/getList',
            datatype : 'json',
            mtype : 'POST',
            colNames : [ '','角色名','角色描述'],
            colModel : [ {name : 'id',index : 'id',hidden : true},
                {name : 'rolename',
                    index : 'rolename',
                    align:'left'
                    //,
                    //formatter:function(cellvalue, options, rowObject){
                    //	var id = rowObject.id; 
                    //	var result= '';
                    //	var infoDivId= 'info_'+id;
                    //	var toolTipId='tip_role';
                    //result = "<button id=\""+infoDivId+"\" onMouseOver=\"toolTipShow('"+toolTipId+"', '"+ infoDivId														
                    //+"', {roleId:'"+id
                    //  +"'} ,"
                    // +"[1]);\" "
                    //+" class=\"btn btn-link\" style=\"color:blue\">"+rowObject.name+"</button>";			
                    //return result;		
                    //}
                },
                {name : 'description', index : 'description', align:'left'}],
            rowNum : 15,
            rowList : [ 15,20 ],
            height : "100%",
            autowidth : true,
            pager : '#pager',
            sortname : 'r.rolename',
            altRows:true,
            hidegrid : false,
            viewrecords : true,
            recordpos : 'left',
            sortorder : "desc",
            emptyrecords : "没有可显示记录",
            loadonce : false,
            multiselect:true,
            loadComplete : function() {},
            jsonReader : {
                root : "rows",
                page : "page",
                total : "total",
                records : "records",
                repeatitems : false,
                cell : "cell",
                id : "id"
            }
        };
        $("#grid").jqGrid(option);
        $("#grid").jqGrid('navGrid', '#pager', {edit : false, add : false, del : false, search : false,	position : 'right'})
                <shiro:hasPermission name="role:add">
                .navButtonAdd('#pager',{caption:"新增",buttonicon:"ui-icon-plus",onClickButton: function(){toAdd()},position:"last"})
                </shiro:hasPermission>
                <shiro:hasPermission name="role:update">
                .navButtonAdd('#pager',{caption:"修改",buttonicon:"ui-icon-pencil",onClickButton: function(){toModify()},position:"last"})
                </shiro:hasPermission>
                <shiro:hasPermission name="role:del">
                .navButtonAdd('#pager',{caption:"删除",buttonicon:"ui-icon-trash",onClickButton: function(){toDelete()},position:"last"});
        </shiro:hasPermission>

        //自适应
        jqgridResponsive("grid",false);


        //取消按钮操作
        $('#cancel').click(function(){
            $('#dialog-confirm').modal('hide');
        });

        $('#cancel2').click(function(){
            $('#dialog-delete').modal('hide');
        });

        //新增或修改操作
        $('#do_save').click(function(){
            var myform = $("#dialog-confirm").find("form").get(0);
            if(!jQuery(myform).validate().form()){ return ;}
            $("#do_save").attr("disabled",true);
            var myform = $("#dialog-confirm").find("form").serializeArray();
            var data = {};
            $.each(myform, function(i, field){
                data[field.name] = field.value;
            });

            var mypIds = "";
            var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
            var nodes = treeObj.getCheckedNodes(true);
            $.each(nodes, function(i, n){
                if( n.getParentNode() != null  ){ // 避免根节点入库
                    mypIds+=n.id+",";
                }
            });
            if('' != mypIds){
                mypIds = mypIds.substring(0,mypIds.length-1);
                data.pIds = mypIds;
            }
            if(data.id ==""){addDate(data);}
            else{updateDate(data);}
        });


        //删除一条记录操作
        $('#do_delete').click(function(){
            var _ids = $("#grid").jqGrid('getGridParam','selarrrow');
            if($.isEmptyObject(_ids)) {
                openError('请选择一条数据',2000);
                return;
            }

            $("#do_delete").attr("disabled",true);
            var str='';
            for(var i=0; i<_ids.length; i++ ){
                if(str!=''){str +=','+_ids[i];
                }else{str +=_ids[i];}
            }
            //开始执行删除动作
            $.post("${ctx}/sys/role/delete",
                    {ids :str },
                    function(data){
                        $("#grid").trigger("reloadGrid", {page:1 });
                        $('#dialog-delete').modal('hide');
                        var message = "删除失败！";
                        if(data){
                            message = "删除成功！";
                        }
                        showResult(data,message);
                    },
                    "json");
        });

        $("#btnQuery").click(multipleSearch);



        //保存机队关联机尾操作
        //$('#do_save_tail').click(function(){
        //	var str='';		
        //	var $options = $('#dialog-tailInfo #using option');
        //	var roleId = $('#roleId').val();
        //	$options.each(function(){ 	     	 
        //		if(str!=''){str +=','+ $(this).val();}
        //		else{	str += $(this).val(); }	
        //     });
        //	
        //	 $.ajax({
        //			url:"${ctx}/sys/role/saveRoleTail",
        //			type: "post",
        ////			dataType: 'json',	
        //			data : {tailIds:str , roleId:roleId},
        //			success:function(messages,status){	
        //				
        //				if(messages==true){
        //								
        //				$('#dialog-tailInfo').modal('hide');		
        //					
        //				showResult(messages,"关联机尾操作成功!");
        //				}
        //				
        //		  		}
        //		});


        //});
    });


    //弹出新增对话框			
    function toAdd(){
        var timebak = new Date().getTime();
        openDialog("${ctx}/sys/role/openmodalroleinput?time="+timebak);
    }

    //弹出修改对话框
    function toModify(){
        var ids = $("#grid").jqGrid('getGridParam','selarrrow');
        if($.isEmptyObject(ids)||ids.length >1) {
            openError('请选择一条数据,且仅选择一条数据',2000);
            return;
        }
        var oneData = $("#grid").jqGrid('getRowData',ids[0]);
        openDialog("${ctx}/sys/role/openmodalroleinput?id="+oneData.id+"&timestamp="+(new Date()).valueOf());
    }

    //弹出删除对话框
    function toDelete(){
        var ids = $("#grid").jqGrid('getGridParam','selarrrow');
        if($.isEmptyObject(ids)) {
            openError('请选择要删除的数据',2000);
            return;
        }
        var str='';
        for(var i=0; i<ids.length; i++ ){
            if(str!=''){str +=','+ids[i];
            }else{str +=ids[i];}
        }

        $.post("${ctx}/sys/role/getUserRoleCount",{ids :str},function(data){
            if(data>0){
                openError('该角色下已关联'+data+"条用户记录,请解除关系再执行删除操作",3000);
                return;
            }
            $("#do_delete").attr("disabled",false);
            $( "#dialog-delete").modal({
                keyboard: false
            });
        },"json");

    }

    //弹出对话框
    function openDialog(url,data){
        $( "#dialog-confirm" ).modal({
            backdrop: 'static'
        });
        //使用此方法防止js缓存不加载
        $("#do_save").attr("disabled",false);
        $("#dialog-confirm p" ).load(url);
    }

    //新增数据
    function addDate(data){
        //新增时注释id值
        //data['id'] = null;
        $.post("${ctx}/sys/role/save",data,function(result){
            $("#grid").trigger("reloadGrid", {page:1 });
            $('#dialog-confirm').modal('hide');
            var message = "新增失败！";
            if(result){
                message ='新增成功!';
            }
            showResult(result,message);
        },'json');
    }

    //修改数据
    function updateDate(data){
        $.post("${ctx}/sys/role/update",data,function(result){
            $("#grid").trigger("reloadGrid", {page:1 });
            $('#dialog-confirm').modal('hide');
            var message = "更新失败！";
            if(result){
                message = "更新成功！";
            }
            showResult(result,message);
        },'json');
    }


    function openError(message,delay){
        $('#alert').show().find('strong').text(message);
        window.setTimeout(function() {
            $('#alert').slideUp("slow");
            //window.top.location.reload();
        }, delay);
    }

    function showResult(result,message,delay){
        $("#messageSpanId").text(message);

        if (!delay || typeof(delay)=="undefined" || typeof(delay)!='number'){
            delay = 2000;
        }
        if(result){
            $("#message").addClass('alert-success').removeClass('alert-danger').slideToggle(1000);
        }else{
            $("#message").addClass('alert-danger').removeClass('alert-success').slideToggle(1000);
        }
        window.setTimeout(function() {
            $('#message').slideToggle(1000);
        }, delay);
    }

    //查询动作
    var multipleSearch = function() {
        var postData = $("#grid").jqGrid("getGridParam", "postData");
        var mydata = {};
        var roleName =$("#s_roleName").val();
        if(roleName && ''!=roleName){
            mydata.name = roleName;
        }else{
            delete postData.name;
        }
        $.extend(postData,mydata);
        $("#grid").jqGrid("setGridParam", {
            search: true
        }).trigger("reloadGrid", [{page:1}]);
    };

    function hideError(){
        $('#alert').hide();
    }

    $(document).click(function(){
        hideError();
    });



    function queryTail(){

        var id= $("#tip_role").data("roleId");

        $(document).data("roleId",'');

        $( "#dialog-tailInfo" ).modal({
            backdrop: 'static'
        });
        var timebak = new Date().getTime();
        //使用此方法防止js缓存不加载
        $("#dialog-tailInfo p" ).load("${ctx}/sys/role/openmodalTailDetail?id="+id+"&time="+timebak);

    }
</script>
</body>
</html>