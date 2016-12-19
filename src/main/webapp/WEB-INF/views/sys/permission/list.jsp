<%--
  Created by IntelliJ IDEA.
  User: zhang.rw
  Date: 16-4-20
  Time: 下午10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>

    <title>权限管理</title>
    <style type="text/css">
        .ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
    </style>
</head>
<body>

<div class="row">
    <div class="col-lg-3 col-md-3">
        <ul id="permissionTree" class="ztree well"  style="height:498px; overflow:auto;"></ul>
    </div>

    <div class="col-lg-6 col-md-6">
        <div id="message" class="alert alert-success" hidden>
            <button data-dismiss="alert" class="close">&times;</button>
            <span id="messageSpanId"></span>
        </div>

        <div id="form_div" hidden>
            <form id="permissionForm">
                <fieldset>
                    <div class="form-group">
                        <label for="parent"><span style="color:red">*</span>父节点：</label>
                        <input type="text" class="form-control input-sm" id="parent" name="parent" readonly="readonly">
                        <input type="hidden" id="parentId" name="parentId">
                    </div>
                    <div class="form-group">
                        <label for="name"><span style="color:red">*</span>名称：</label>
                        <input type="text" class="form-control input-sm" id="name" name="name" placeholder="名称" required>
                    </div>
                    <div class="form-group">
                        <label for="code"><span style="color:red">*</span>权限码：</label>
                        <input type="text" class="form-control input-sm" id="code" name="code" placeholder="权限码" required>
                    </div>
                    <div class="form-group">
                        <label for="url">URL：</label>
                        <input type="text" id="url" class="form-control input-sm" name="url" placeholder="URL">
                    </div>
                    <div class="form-group">
                        <label for="type">资源类型：</label>
                        <select id="type" name="type" name="type" class="form-control input-sm">
                            <option value="0" selected="selected">请选择</option>
                            <option value="1">系统</option>
                            <option value="2">模块</option>
                            <option value="3">功能</option>
                            <%--<option value="4">资源</option>--%>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="remark">权限描述：</label>
                        <textarea rows="3" name="remark" id="remark" class='form-control' placeholder="权限描述"></textarea>
                    </div>

                    <%--<input type="hidden" id="grade" name="Grade">--%>
                    <input type="hidden" id="id">
                    <%--<input type="hidden" id="t_id">--%>
                    <button type="button" id="btn_cancel" class="btn btn-default btn-sm">取消</button>
                    <button type="submit" id="btn_submit" class="btn btn-primary btn-sm">添加</button>
                </fieldset>
            </form>
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

    $(function(){
        $.getJSON('${ctx}/sys/permission/getparents',function(data) {
            $('#parent').autocomplete(data,{
                mustMatch:true,
                minChars:0,
                width:'200px',
                // 下拉列表显示的字段 ，如果多个用表格格式化，如果单个 直接  return item.COUNTRY_NAME; 即可
                formatItem: function(item,i, max) {

                    if( item.code == null || item.code == "underfined" ){
                        return '<table><tr><td width="120px" ><div style="width:110px;">'+item.name+'</div></td><td width="40px"></td></tr></table>';
                    }

                    return '<table><tr><td width="120px" ><div style="width:110px;">'+item.name+'</div></td><td width="40px">' + item.code + '</td></tr></table>';
                },

                // 指定 与 输入文字匹配的字段名
                formatMatch: function(item,i, max) {
                    return item.code;
                },
                // 选中 某条记录在输入框里 显示的数据字段
                formatResult: function(item) {
                    return item.name;
                }
            });

            //选中 某条记录 触发的事件
            $('#parent').result(function(event, item){
                if(item){
                    if(item.name == $("#name").val() || item.id == $("#id").val() ){
                        $("#permissionForm :button").attr("disabled",true);
//                        $("#btn_submit").disabled = false;
                        showResult(false,"不能选择自身节点作为父节点！");
                    }else{
                        $("#parent").val(item.name);
                        $("#parentId").val(item.id);
                        $("#permissionForm :button").attr("disabled",false);
//                        $("#btn_submit").disabled = false;
                    }

                }else{
                    $("#parent").text("");
                }

            });

        });
    });


    var setting = {
        async: {
            enable: true,
            type:"post",
            url:"${ctx}/sys/permission/getallpermission"
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"
            }
        } ,
        callback: {
            beforeDrag: beforeDrag,
            onAsyncSuccess: onAsyncSuccess,
            onRemove: onRemove
//            ,onClick : editOnClickReadOnly  //单击事件到效果不好 会影响其他按钮操作
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
        <shiro:hasPermission name="permission:add">
        if ($("#addBtn_"+treeNode.id).length>0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.id
                + "' title='添加子节点' onfocus='this.blur();'></span>";
        sObj.append(addStr);
        var btn = $("#addBtn_"+treeNode.id);
        if (btn) btn.bind("click", function(){
            var zTree = $.fn.zTree.getZTreeObj("permissionTree");
            var newNode = {id:(100 + newCount), pId:treeNode.id, name:"新节点" + (newCount++),
//                grade:(treeNode.grade+1),
                tId:treeId+'_new'+(newCount++)};
            add(treeId, newNode,treeNode);
        });
        if(treeNode.type=='3'){
            $("#addBtn_"+treeNode.id).hide();
        }
        </shiro:hasPermission>
        <shiro:hasPermission name="permission:update">
        var editStr = "<span class='button edit' id='editBtn_" + treeNode.id	+ "' title='修改' onfocus='this.blur();'></span>";
        sObj.append(editStr);
        var ebtn = $("#editBtn_"+treeNode.id);
        if(ebtn) ebtn.bind('click',function(e){
            var zTree = $.fn.zTree.getZTreeObj(treeId);
            zTree.selectNode(treeNode);
            editOnClick(e,treeId,treeNode);
        });
        </shiro:hasPermission>
        <shiro:hasPermission name="permission:del">
//        if(treeNode.grade!=0){
        if(treeNode.id!="0"){
            var removeStr = "<span class='button remove' id='removeBtn_" + treeNode.id	+ "' title='删除' onfocus='this.blur();'></span>";
            sObj.append(removeStr);
            var ebtn = $("#removeBtn_"+treeNode.id);
            if(ebtn) ebtn.bind('click',function(e){
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                zTree.selectNode(treeNode);
                onRemove(e, treeId, treeNode);
            });
        }
        </shiro:hasPermission>
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
        var node = treeObj.getNodeByParam("id","0", null); // 展开根节点
        treeObj.expandNode(node,true,false,true);
    }

    function beforeDrag(treeId, treeNodes) {
        return false;
    }

    function showRemoveBtn(treeId, treeNode) {
     if(treeNode.grade==1){
     return false;
     }
     return true;
     }

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
        $.post("${ctx}/sys/permission/rename",
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
        $.post("${ctx}/sys/permission/delete",
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
        $("#permissionForm :button").attr("disabled",false);
        $.post("${ctx}/sys/permission/update",
                { "id": $("#id").val(),
                    'name':$("#name").val(),
                    'code':$("#code").val(),
                    'permissionType':$("#permissionType").val(),
                    'url':$("#url").val(),
                    'type':parseInt($("#type").val()),
                    "parentId":$("#parentId").val(),
                    'remark':$("#remark").val()},
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

    function editOnClickReadOnly(event, treeId, treeNode) {
        editOnClick(event, treeId, treeNode);
        $("#btn_submit").hide();
    }

    function editOnClick(event, treeId, treeNode){
        $("#btn_submit").show();
        $("#form_div").show();
        var validator = $("#permissionForm").validate();
        validator.resetForm();
        $("#btn_submit").text('更新');
        $("#name").val(treeNode.name);
        $("#code").val(treeNode.code);
        $("#url").val(treeNode.url);
        $("#parentId").val(treeNode.parentId);
        if(treeNode.type != '0') {
            $('#type')[0].selectedIndex = parseInt(treeNode.type);
            $("#type").val(treeNode.type);
//        }
            $("#permissionType").val(treeNode.permissionType);
            var treeObj = $.fn.zTree.getZTreeObj(treeId);
            var node = treeObj.getNodeByParam("id", treeNode.parentId, null);
            if (node) {
                $("#parent").val(node.name);
            }
            $("#id").val(treeNode.id);
            $("#remark").val(treeNode.remark);

            if( treeNode.type == '2' ){ // 菜单可以随意调整父节点
                $("#parent")[0].readOnly = false;
                $("#parent")[0].disabled = false;

            }else{
                $("#parent")[0].readOnly = true;
                $("#parent")[0].disabled = true;
            }

//        var tId = $("#t_id").val();
//        if(''!=tId){
//            var treeObj = $.fn.zTree.getZTreeObj(treeId);
//            var node = treeObj.getNodeByTId(tId);
//            treeObj.removeNode(node);
//            $("#t_id").val('');
//        }
        }
    }

    $(function(){
        $("#permissions_nav").addClass('active');

        $("#permissionForm").validate();

        $.fn.zTree.init($("#permissionTree"), setting);

        $('#confirmModal .btn-confirm').click(remove);

        $('#confirmModal .btn-cancel').click(removeForm);
    });

    $("#permissionForm").validate({
        rules:{
            name:{
                required:true,
                maxlength:20,
                minlength:2
            },
            code:{
                required:true,
                maxlength:30,
                minlength:4
            },
            url:{
                maxlength:100,
                minlength:1
            },
            remark:{
                maxlength:200,
                minlength:4
            }
        },
        messages:{
            name:{
                required:'名称不能为空',
                maxlength:$.format('不能超过{0}个字符'),
                minlength:$.format('不能少于{0}个字符')
            },
            code:{
                required:'权限码不能为空',
                maxlength:$.format('不能超过{0}个字符'),
                minlength:$.format('不能少于{0}个字符')
            },
            url:{
                maxlength:$.format('不能超过{0}个字符'),
                minlength:$.format('不能少于{0}个字符')
            },remark:{
                maxlength:$.format('不能超过{0}个字符'),
                minlength:$.format('不能少于{0}个字符')
            }

        },
        submitHandler: function(form) {
            $("#permissionForm :button").attr("disabled",true);
            var btn = $("#btn_submit");

                $.post("${ctx}/sys/permission/checknameexists",
                        { "id":$("#id").val(),
                            "name":$("#name").val(),
                            "code":$("#code").val()
                        },
                        function(data){
                            if(data){
                                message = "名称或权限码已经存在，请重新输入！";
                                showResult(false,message);
                                $("#permissionForm :button").attr("disabled",false);
                            }else{
                                if('更新'==btn.text()){
                                    onUpdate();
                                }else{
                                    var options = {
                                        success:showResponse,
                                        url:'${ctx}/sys/permission/add',
                                        type:'post'
                                    };
                                    $(form).ajaxSubmit(options);
                                }
                            }
                        },
                        "json");

        }
    });

    function showResponse(responseText, statusText, xhr, $form)  {
        $("#permissionForm :button").attr("disabled",false);
        if(responseText){
            removeForm();
            $("#messageSpanId").text('权限添加成功！');
            $("#message").addClass('alert-success').removeClass('alert-danger').slideToggle(1000);
        }else{
            $("#messageSpanId").text('权限添加失败！');
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

        var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
        treeObj.reAsyncChildNodes(null, "refresh");
    }

    function add(treeId,treeNode,pNode){
        var formstatus = $('#form_div').is(':hidden');
        if($(!formstatus && "#btn_submit").text()=='添加'){
            showResult(false,'请勿重复添加！');
            return;
        }
        $("#form_div").show();
        var validator = $("#permissionForm").validate();
        validator.resetForm();
        $("#parent").val(pNode.name);
        $("#btn_submit").text('添加');
        $("#name").val('');
        $("#code").val('');
        $("#url").val('');
        $("#remark").val('');
//        $("#permissionType").val(pNode.permissionType);
        $("#parentId").val(treeNode.pId);
//        $("#grade").val(treeNode.grade);
        $("#id").val('');
        $("#t_id").val(treeNode.tId);
        $("#permissionForm").attr('action','${ctx}/sys/permission/add').attr('method','POST');
    }

    $("#btn_cancel").click(function(){
        removeForm();
    });

    function removeForm(){
        $("#permissionForm").resetForm();
        $("#parentId").val('');
//        $("#grade").val('');
        $('#id').val('');
        $("#form_div").hide();
        $("#permissionForm :button").attr("disabled",false);
        var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
        treeObj.reAsyncChildNodes(null, "refresh");
    }
</script>
</body>
</html>
