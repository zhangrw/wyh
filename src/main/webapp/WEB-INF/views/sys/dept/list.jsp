<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>

    <title>部门管理</title>
    <style type="text/css">
        .ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
    </style>
</head>
<body>

<div class="row">
    <div class="col-lg-3 col-md-3">
        <ul id="menuTree" class="ztree well"  style="height:498px; overflow:auto;"></ul>
    </div>

    <div class="col-lg-6 col-md-6">
        <div id="message" class="alert alert-success" hidden>
            <button data-dismiss="alert" class="close">&times;</button>
            <span id="messageSpanId"></span>
        </div>

        <div id="form_div" hidden>
            <form id="menuForm">
                <fieldset>
                    <div class="form-group">
                        <label for="parentId"><span style="color:red">*</span>上级部门：</label>
                        <%--<input type="text" class="form-control input-sm" id="parentname" name="parentname"><!--  readonly="readonly" -->--%>                <select  class="form-control input-sm" id="parentId" name="parentId">
                            <c:forEach items="${deptlist}" var="m" >
                                <option value='${m.getId()}'>${m.getName()}</option>
                            </c:forEach>
                        </select>

                    </div>

                    <div class="form-group" id="deptname">
                        <label><span style="color: red">*</span>部门名称：</label>
                        <input type="text" class="form-control input-sm" name="name" id="name" placeholder="输入部门名称"/>
                    </div>

                    <div class="form-group">
                        <label for="description">部门描述：</label>
                        <textarea name="description" id="description" class='form-control input-sm' placeholder="部门描述"></textarea>
                    </div>
                    <%--<input type="hidden" id="parentId" name="parentId">--%>
                    <input type="hidden" id="node_id" name="id">
                    <input type="hidden" id="deptOrder" name="deptOrder">

                    <input type="hidden" id="t_id">
                    <button type="button" id="btn_cancel" class="btn btn-default btn-sm">取消</button>
                    <button type="submit" id="btn_submit" class="btn btn-primary btn-sm">添加</button>
                </fieldset>
            </form>
        </div>

        <div id="alert" class="alert alert-danger" hidden>
            <strong>Oh snap!</strong>
        </div>
        <div id="grid_div" hidden>
            <div id="jqgrid">
                <table id="grid"></table>
            </div>
            <button type="button" id="btn_up_1" class="btn btn-default btn-sm">向上</button>
            <button type="button" id="btn_down_1" class="btn btn-default btn-sm">向下</button>
            <button type="button" id="btn_cancel_1" class="btn btn-default btn-sm">返回</button>
            <input type="hidden" id="id" name="id">
        </div>
    </div>
</div>


<div id='confirmModal' class="modal fade">
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
                <input type="hidden" id="del_id">
                <button type="button" data-dismiss="modal" class="btn btn-default btn-sm btn-cancel" tabindex="1001">取消</button>
                <button type="button" class="btn btn-primary btn-sm btn-confirm" tabindex="1000">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
    var orderRn = "";
    var option = {
        url : '${ctx}/sys/dept/getDeptbyid',
        datatype : 'json',
        mtype : 'POST',
        colNames : [ '','部门名','', '序号'],
        colModel : [ {name : 'id',index : 'id',hidden : true},
            {name : 'name', index : 'name', align:'left',sortable : false},
            {name : 'permission', index : 'permission',hidden:true },
            {name : 'deptOrder', index : 'menuOrder', align : 'center',sortable : false}],
//        {name : 'description', index : 'description', align:'center'},
//         {name : 'permission', index : 'permission'},
//         {name : 'url', index : 'url'}],
        rowNum : 15,
        rowList : [ 15, 30, 50 ],
        height : "100%",
        autowidth : true,
        //pager : '#pager',
        sortname : 'menuOrder',
        rownumbers:true,
        altRows:true,
        hidegrid : false,
        viewrecords : true,
        recordpos : 'left',
        sortorder : "desc",
        emptyrecords : "没有可显示记录",
        loadonce : false,
//        loadonce : true ,
//        multiselect:true,
        loadComplete : function() {
            var rowData = $("#grid").jqGrid('getRowData',1);
            if(!rowData.id){
                $("#grid_div").hide();
            }
            $("#grid").jqGrid("setSelection",orderRn);
            orderRn = "";
        },
        jsonReader : {
            id : "menurn"
        }
    };
    $("#grid").jqGrid(option);
    //$( pGridId ).closest(".ui-jqgrid-bdiv").css({ 'overflow-y' : 'scroll' });
    //需要保持水平滚动条，则：
    //$( pGridId ).closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'scroll' });

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
        } ,
        callback: {
            beforeDrag: beforeDrag,
            //beforeDrop: beforeDrop,
            onAsyncSuccess: onAsyncSuccess,
            onRemove: onRemove,
            onClick: zTreeOnClick
        },
        view:{
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            expandSpeed: "slow",
            selectedMulti: false
        },
        edit: {
            showRemoveBtn : false,
            showRenameBtn:false,
            enable: true
        }
    };

    var newCount = 1;
    function addHoverDom(treeId, treeNode) {

        var sObj = $("#" + treeNode.tId + "_span");

        <%--<shiro:hasPermission name="menumgt:add">--%>
        if ($("#addBtn_"+treeNode.id).length>0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.id
                + "' title='添加子节点' onfocus='this.blur();'></span>";

        sObj.append(addStr);
        var btn = $("#addBtn_"+treeNode.id);
        if (btn) btn.bind("click", function(){
            var zTree = $.fn.zTree.getZTreeObj("menuTree");
            var newNode = {id:(100 + newCount), parent:treeNode.id, name:"新节点" + (newCount++),tId:treeId+'_new'+(newCount++)};
            add(treeId, newNode,treeNode);
        });
//        if(treeNode.type=="2"){$("#addBtn_"+treeNode.id).hide();}
        <%--</shiro:hasPermission>--%>
        <%--<shiro:hasPermission name="menumgt:update">--%>
        var editStr = "<span class='button edit' id='editBtn_" + treeNode.id	+ "' title='修改' onfocus='this.blur();'></span>";
        sObj.append(editStr);
        var ebtn = $("#editBtn_"+treeNode.id);
        if(ebtn) ebtn.bind('click',function(e){
            var zTree = $.fn.zTree.getZTreeObj(treeId);
            zTree.selectNode(treeNode);
            editOnClick(e,treeId,treeNode);
        });
        <%--</shiro:hasPermission>--%>
        <%--<shiro:hasPermission name="menumgt:delete">--%>
        if(treeNode.parent!=0){
            var removeStr = "<span class='button remove' id='removeBtn_" + treeNode.id	+ "' title='删除' onfocus='this.blur();'></span>";
            sObj.append(removeStr);
            var ebtn = $("#removeBtn_"+treeNode.id);
            if(ebtn) ebtn.bind('click',function(e){
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                zTree.selectNode(treeNode);
                onRemove(e, treeId, treeNode);
            });
        }
        <%--</shiro:hasPermission>--%>
    };
    function removeHoverDom(treeId, treeNode) {
        var abtn = $("#addBtn_"+treeNode.id);
        if(abtn) abtn.unbind().remove();
        var ebtn = $("#editBtn_"+treeNode.id);
        if(ebtn) ebtn.unbind().remove();
        var rbtn = $("#removeBtn_"+treeNode.id);
        if(rbtn) rbtn.unbind().remove();
    };


    function onAsyncSuccess(event, treeId, treeNode, msg) {

        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        treeObj.expandAll(true);
    }

    function beforeDrag(treeId, treeNodes) {
        return false;
    }

    function zTreeOnClick(event, treeId, treeNode) {
        if($(event.target).html().length==0) return;

        $("#form_div").hide();
        var id = treeNode.id;
        $("#grid").setGridParam({postData:{id: id}}).trigger("reloadGrid");
        $("#id").val(id);
        $("#grid_div").show();
    }
    function upOrDown(moveType){
        var menurn = $("#grid").jqGrid('getGridParam','selrow');
        if($.isEmptyObject(menurn)) {
            openError('请选择一条数据',2000);
            return;
        }
        var currentRowData = $("#grid").jqGrid('getRowData',menurn);

        if(moveType=="up"&&menurn!=1){
            var upRowData = $("#grid").jqGrid('getRowData',menurn-1);
            updateMenuOrder(currentRowData.id,menurn-1,upRowData.id,menurn);
        }
        if(moveType=="down"){
            var downRowData = $("#grid").jqGrid('getRowData',parseInt(menurn)+1);
            if(!downRowData.id) return;
            updateMenuOrder(currentRowData.id,parseInt(menurn)+1,downRowData.id,menurn);
        }

    }
    function updateMenuOrder(currentid,currentorder,updownid,updownorder){
        $.post("${ctx}/sys/dept/updateDeptOrder",
                {menuOrderParam : currentid+':'+currentorder+';'+updownid+':'+updownorder},
                function(data){
                    if(data.success){
                        orderRn = currentorder;
                        $("#grid").setGridParam({postData:{id: $("#id").val()}}).trigger("reloadGrid");
                        var treeObj = $.fn.zTree.getZTreeObj("menuTree");
                        treeObj.reAsyncChildNodes(null, "refresh");
                        //$("#grid").jqGrid("setSelection",currentorder);
                    }
                },
                "json");
    }
    function openError(message,delay){
        $('#alert').show().find('strong').text(message);
        window.setTimeout(function() {
            $('#alert').slideUp("slow");
            //window.top.location.reload();
        }, delay);
    }
    //	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
    //		if(targetNode == null || (moveType != "inner" && !targetNode.parentTId)){
    //			return false;
    //		}
    //		if(moveType=='prev'){
    //			//updateMenuOrder();
    //			return true;
    //		}
    //		if(moveType=='next'){
    //
    //			return true;
    //		}
    //		return false;
    //	}

    /**
     * 重命名结束
     * @param e
     * @param treeId
     * @param treeNode
     */
    function onRename(e, treeId, treeNode,isCancel) {
        if(isCancel){
            return ;
        }
        $.post("${ctx}/sys/dept/rename",
                { "id": treeNode.id,"newName":treeNode.name},
                function(data){
                    var message = "重命名失败！";
                    if(data){
                        message = "重命名成功！";

                    }
                    showResult(data,message);
                },
                "json");
    }
    /**
     * 删除结束
     * @param e
     * @param treeId
     * @param treeNode
     */
    function onRemove(e, treeId, treeNode) {
        $("#del_id").val(treeNode.id);

        $("#confirmModal").modal({ keyboard: false,backdrop:true});
    }

    function remove(){
        $('#confirmModal').modal('hide');
        var id = $("#del_id").val();
        $.post("${ctx}/sys/dept/delete",
                { "id": id},
                function(data){
                    var message = "删除失败！";
                    if(data){
                        message = "删除成功！";
                    }
                    showResult(data,message);
                },
                "json");
    }

    function onUpdate(){
        $("#menuForm :button").attr("disabled",false);
        $.post("${ctx}/sys/dept/update",
                $("#menuForm").serialize(),
                function(data){
                    var message = "更新失败！";
                    if(data){
                        message = "更新成功！";
                        $("#form_div").hide();
                    }
                    showResult(data,message);
                },
                "json");
    }

    function editOnClick(event, treeId, treeNode) {
        $("#grid_div").hide();
        $("#form_div").show();
        var validator = $("#menuForm").validate();
        validator.resetForm();
        $("#btn_submit").text('更新');
        $("#name").val(treeNode.name);
        $("#deptOrder").val(treeNode.deptOrder);
        $("#description").val(treeNode.description);
        $("#parentId").val(treeNode.parentId);
//        $("#parentId").find("option[selected='selected']").removeAttr("selected");
//        $("#parentId").find("option[value='"+treeNode.parentId+"']").attr("selected","selected");

        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        var node =  treeObj.getNodeByParam("id", treeNode.parentId, null);
        if(node){
            $("#parentname").val(node.name);
//            $("#parentId").val(treeNode.parentId);

        }
        $("#id").val(treeNode.id);
        $("#node_id").val(treeNode.id);
        var tId = $("#t_id").val();
        if(''!=tId){
            var treeObj = $.fn.zTree.getZTreeObj(treeId);
            var node = treeObj.getNodeByTId(tId);
            treeObj.removeNode(node);
            $("#t_id").val('');
        }
    }

    $(function(){
        $("#menu_nav").addClass('active');
        $("#menuForm").validate();
        var treeObj = $.fn.zTree.init($("#menuTree"), setting);
        $('#confirmModal .btn-confirm').click(remove);
        $('#confirmModal .btn-cancel').click(removeForm);
        $("#selectResource2").hide();
        treeObj.expandAll(true);
    });

    $("#menuForm").validate({
        rules:{
            type:{
                required:true
            },
            menuname2:{
                required:true
            },
            url:{
                maxlength:100,
                minlength:1
            },
            description:{
                maxlength:200,
                minlength:4
            }
        },
        messages:{
            type:{
                required:'请选择类型'
            },
            menuname2:{
                required:'请输入资源'
            },
            url:{
                maxlength:$.format('不能超过{0}个字符'),
                minlength:$.format('不能少于{0}个字符')
            },description:{
                maxlength:$.format('不能超过{0}个字符'),
                minlength:$.format('不能少于{0}个字符')
            }

        },
        submitHandler: function(form) {
            $("#menuForm :button").attr("disabled",true);
            var btn = $("#btn_submit");
            if('更新'==btn.text()){
                $.post("${ctx}/sys/dept/checknameexists",
                        $("#menuForm").serialize(),
                        function(data){
                            if(data){
                                var message;
                                message = "部门名称已经存在，请重新输入！";

                                showResult(false,message);
                                $("#menuForm :button").attr("disabled",false);
                            }else{
                                onUpdate();
                            }
                        },
                        "json");

            }else{
                $.post("${ctx}/sys/dept/checknameexists",
                        { "parentId":$("#parent").val(),"name":$("#menuname2").val()==''?$("#menutext").val():$("#menuname2").val()},
                        function(data){
                            if(data){
                                var message;
                                message = "部门名称已经存在，请重新输入！";

                                showResult(false,message);
                                $("#menuForm :button").attr("disabled",false);
                            }else{
                                var options = {
                                    success:showResponse,
                                    url:'${ctx}/sys/dept/add',
                                    type:'post'
                                };
                                $(form).ajaxSubmit(options);
                            }
                        },
                        "json");

            }
        }
    });

    function showResponse(responseText, statusText, xhr, $form)  {
        $("#menuForm :button").attr("disabled",false);
        if(responseText){
            removeForm();
            $("#messageSpanId").text('部门添加成功！');
            $("#message").addClass('alert-success').removeClass('alert-danger').slideToggle(1000);
        }else{
            $("#messageSpanId").text('部门添加失败！');
            $("#message").addClass('alert-danger').removeClass('alert-success').slideToggle(1000);
        }
        window.setTimeout(function() {
            $('#message').slideToggle(1000);
        }, 2000);
    }

    function showResult(result,message){
        $("#messageSpanId").text(message);
        if(result){
            $("#message").addClass('alert-success').removeClass('alert-danger').slideToggle(1000);
        }else{
            $("#message").addClass('alert-danger').removeClass('alert-success').slideToggle(1000);
        }
        window.setTimeout(function() {
            $('#message').slideToggle(1000);
        }, 2000);

        var treeObj = $.fn.zTree.getZTreeObj("menuTree");
        treeObj.reAsyncChildNodes(null, "refresh");
        treeObj.expandAll(true);
    }

    function add(treeId,treeNode,pNode){
        $("#grid_div").hide();
        var formstatus = $('#form_div').is(':hidden');
        //if($(!formstatus && "#btn_submit").text()=='添加'){
        //	showResult(false,'请勿重复添加！');
        //	return;
        //}
        $("#form_div").show();
        var validator = $("#menuForm").validate();
        validator.resetForm();
        $("#parentname").val(pNode.name);
        $("#btn_submit").text('添加');
        $("#name").val('');
        $("#permission").val('');
        $("#url").val('');
        $("#description").val('');
        $("#parentId").val(treeNode.parent);
        $("#id").val('');
        $("#t_id").val(treeNode.tId);
        //$("#menuOrder").val((treeNode.tId).replace(/[^0-9]/ig,""));

        $.post("${ctx}/sys/dept/getordercount",{parentId:pNode.id},function(result){
            if(result.success){
                $("#menuOrder").val(result.order + 1);
            }
        },'json');

        $("#menuForm").attr('action','${ctx}/sys/dept/add').attr('method','POST');
//        changeProperty();
    }

    $("#btn_cancel").click(function(){
        removeForm();
    });

    $("#btn_cancel_1").click(function(){
        $("#grid_div").hide();
    });

    $("#btn_up_1").click(function(){
        upOrDown("up");
    });
    $("#btn_down_1").click(function(){
        upOrDown("down");
    })

    function removeForm(){
        $("#menuForm").resetForm();
        $("#parent").val('');
        $('#id').val('');
        $("#form_div").hide();
        $("#menuForm :button").attr("disabled",false);
        var treeObj = $.fn.zTree.getZTreeObj("menuTree");
        treeObj.reAsyncChildNodes(null, "refresh");
    }

    $(function(){

        var setting2 = {
            async: {
                enable: true,
                type:"post",
                url:"${ctx}/sys/permission/getallpermission",
            },
            view: {
                showIcon: true
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId"
                },
                key:{
                    url:"_url"
                }
            } ,
            callback: {
                onClick:zTreeOnClickForMenu
            }
        };

        function zTreeOnClickForMenu(event, treeId, treeNode){
//            if(treeNode.type > 1){
                $("#name").val(treeNode.name);
                $("#menutext").val(treeNode.name)
                $("#permission").val(treeNode.id);
                if($("#type").val()=="1"){
                    $("#permission").val("lookatmenu");
                }
                $("#code").val(treeNode.code);
                $("#url").val(treeNode.url);
                $("#description").val(treeNode.remark);
                $("#menuZtree").toggle();
//            }
        }

//        $.fn.zTree.init($("#menu_ztree"), setting2);
          $.fn.zTree.init($("#menu_ztree"), setting);


        $("#menutext").click(function(e){
            $("#menuZtree").css('width',$(this).outerWidth());
            $("#menuZtree").toggle();
        });

    });

    function changeProperty(){
        if($("#type").val()=='1'){
            $("#selectResource").hide();
            $("#selectPermission").hide();
            $("#selectResource2").show();
            $("#selectTarget").hide();
        }else{
            $("#selectResource").show();
            $("#selectPermission").hide();
            $("#selectResource2").hide();
            $("#selectTarget").show();
        }
    }
</script>
</body>
</html>