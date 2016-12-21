<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>基本信息维护</title>



    <style>
        <!--
        .fileload {
            border-radius: 3px;
            font-size: 12px;
            height: 30px;
            line-height: 1.5;
            padding: 5px 10px;
            width: 81%;
            background-color: #fff;
            background-image: none;
            border: 1px solid #ccc;
            box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
            color: #555;

        }
        -->
    </style>
</head>
<body>
<div id='dialog-confirm' class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">基本信息</h4>
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
<div id="select">
    <div class="select-main">
        <form action="" id="searchForm" method="post" class="well-work bs-adp form-inline">
            <div  class="row">

                <div  class="leftLable col-md-1" style="text-align:right">患者姓名:</div>
                <div  class="col-md-2">
                    <input type="text" class="form-control input-sm" name="patientName" id="patientName" value='' placeholder="患者姓名"/>
                </div>

                <div  class="rightLable col-md-1" style="text-align:right">是否糖尿病:</div>
                <div  class="col-md-2">
                    <select class="form-control" id="isdiabetes" name="isdiabetes">
                        <option value="">请选择</option>
                        <option value="1">是</option>
                        <option value="2">否</option>
                    </select>
                </div>

                <div  class="col-md-3" style="float:right;margin-right: 30px;margin-top: 5px">
                    <button type="button" id="btnQuery" class="btn btn-primary btn-align-right btn-sm">查询</button>
                    <button type="button" id="impExcel" class="btn btn-primary btn-align-right btn-sm">导入</button>
                    <button type="button" id="expExcel" class="btn btn-primary btn-align-right btn-sm">导出</button>
                    <button type="button" id="downExcel" class="btn btn-primary btn-align-right btn-sm">模版下载</button>
                </div>
            </div>
        </form>
    </div>
</div>
<div style="height: 15px;"></div>

<div id="alert" class="alert alert-danger" hidden>
    <strong>Warning!</strong>
</div>
<div id="message" class="alert alert-success" hidden>
    <button data-dismiss="alert" class="close">&times;</button>
    <span id="messageSpanId"></span>
</div>
<div id="jqgrid">
    <table id="grid"></table>
    <div id="pager"></div>
</div>

<div id='dialog-uplaod' class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">基本信息</h4>
            </div>
            <div class="modal-body">
                <p>

                <div id="alertForUpload" class="alert alert-danger" hidden>
                    <strong>Warning!</strong>
                </div>
                <form id="uploadForm" method="POST" enctype="multipart/form-data" class="form-horizontal">

                    <div class="form-group">
                        <label class="col-lg-3 col-md-1  control-label" for="impexcel"><span style="color: red">*</span>选择文件:</label>
                        <div class="col-lg-7 col-md-8">
                            <input type="text" name="fileName" id="puf" class="fileload" placeholder="请点击浏览选择文件"/>
                            <input type="button" value="浏览..." style="height:30px;" onclick="javascript:$('#file').click();" />
                            <input type="file" name="file" accept=".xls" id="file" style="display:none" onchange="javascript:$('#puf').val($('#file').val());" />
                        </div>
                    </div>
                    <div class="form-group" id="loading" hidden>
                        <label class="col-lg-5 col-md-1  control-label" for="impexcel">&nbsp;</label>
                        <div class="col-lg-7 col-md-5">
                            <img src="${ctx }/static/bootstrap/file-input/img/loading-sm.gif" width="32" height="32" style="margin-right:8px;float:left;vertical-align:top;"/>
                        </div>
                    </div>
                </form>

                </p>
            </div>
            <div class="modal-footer">
                <button type="button" id ='cancel_upload' class="btn btn-default btn-sm" tabindex="1001">取消</button>
                <button type="button" id ='do_save_upload' class="btn btn-primary btn-sm" tabindex="1000">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
    $(function(){

        var option = {
            url : '${ctx}/basic/getdata',
            datatype : 'json',
            mtype : 'POST',
            colNames : [ '','登录名','用户名','工号','性别','身份证号','银行卡号','部门ID','所属部门','备注信息'],
            colModel : [ {name : 'id',index : 'id',hidden : true},
                {name : 'loginName', index : 'loginName', align:'center' },
                {name : 'name', index : 'name', align:'center' },
                {name : 'jobNumber', index : 'jobNumber', align:'center' },
                {name : 'sex', align:'center',formatter : function(cellvalue, option, rowObjects){
                    if (cellvalue == 1) {
                        return "男";
                    }else{
                        return "女";
                    }
                }
                },
                {name : 'idNumber', align:'center' },
                {name : 'bankNumber', align:'center' },
                {name : 'deptId', align:'left',hidden : true },
                {name : 'deptName', align:'center' },
                {name : 'bz', align:'left' }
            ],
            rowNum : 15,
            rowList : [ 15, 30, 50 ],
            height : "100%",
            autowidth : true,
            pager : '#pager',
            sortname : 'id',
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
        <%--<shiro:hasPermission name="user:add">--%>
                .navButtonAdd('#pager',{caption:"新增",buttonicon:"ui-icon-plus",onClickButton: function(){toAdd()},position:"last"})
                <%--</shiro:hasPermission>--%>
                <%--<shiro:hasPermission name="user:update">--%>
                .navButtonAdd('#pager',{caption:"修改",buttonicon:"ui-icon-pencil",onClickButton: function(){toModify()},position:"last"})
                <%--</shiro:hasPermission>--%>
                <%--<shiro:hasPermission name="user:del">--%>
                .navButtonAdd('#pager',{caption:"删除",buttonicon:"ui-icon-trash",onClickButton: function(){toDelete()},position:"last"})
                <%--</shiro:hasPermission>--%>
                <%--<shiro:hasPermission name="user:resetpassword">--%>
                <%--.navButtonAdd('#pager',{caption:"重置密码",buttonicon:"ui-icon-disk",onClickButton: function(){toReSetPwd()},position:"last"})--%>
        <%--</shiro:hasPermission>--%>
//                .navButtonAdd('#pager',{caption:"数据权限",buttonicon:"ui-icon-disk",onClickButton: function(){toDataLimit()},position:"last"})
        ;
        //自适应
        jqgridResponsive("grid",false);
    });

    function toAdd(){
        openDialog("${ctx}/basic/openmodaluserinfo?time="+new Date().getTime());
        return;
    }
    function toDelete(){
        var ids = $("#grid").jqGrid('getGridParam','selarrrow');
        if($.isEmptyObject(ids)) {
            openError('请选择要删除的数据',2000);
            return;
        }

        $("#do_delete").attr("disabled",false);
        $("#dialog-delete").modal({
            keyboard: false
        });
    }

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
            if(str!=''){
                str +=','+_ids[i];
            }else{
                str +=_ids[i];
            }
        }
        //开始执行删除动作
        $.post("${ctx}/basic/delete",
                {ids :str },
                function(data){
                    $("#grid").trigger("reloadGrid", {page:1 });
                    $('#dialog-delete').modal('hide');
                    var message = "删除失败！";
                    if(data.success){
                        message = "删除成功！";
                    }else{
                        message = data.msg+"!";
                    }
                    showResult(data.success,message);
                },
                "json");
    });

    function toModify(){
//        $("#grid").jqGrid().getRowData()[0].id
        if( $("#grid").jqGrid().getRowData().length == 1 ){// 只有一行数据的时候
            openDialog("${ctx}/basic/openmodaluserinfo?id="+$("#grid").jqGrid().getRowData()[0].id+"&time="+new Date().getTime());
            return;
        }
        var ids = $("#grid").jqGrid('getGridParam','selarrrow');
        if($.isEmptyObject(ids)||ids.length >1) {
            openError('请选择一条数据,且仅选择一条数据',2000);
            return;
        }
        var oneData = $("#grid").jqGrid('getRowData',ids[0]);
        openDialog("${ctx}/basic/openmodaluserinfo?id="+oneData.id+"&time="+new Date().getTime());
    }

    //弹出对话框
    function openDialog(url,data){
        $( "#dialog-confirm" ).modal({
            backdrop: 'static'
        });
        $( "#do_save").attr("disabled",false);
        //使用此方法防止js缓存不加载
        $("#dialog-confirm p" ).load(url);
    }

    function openError(message,delay){
        $('#alert').show().find('strong').text(message);
        window.setTimeout(function() {
            $('#alert').slideUp("slow");
            //window.top.location.reload();
        }, delay);
    }

    $("#cancel").click(function(){
        $( "#dialog-confirm" ).modal("hide");
    });


    $("#do_save").click(function(){
        $("#shirouser")[0].action = "${ctx}/basic/save";

        var myform = $("#dialog-confirm").find("form").get(0);
        if(!jQuery(myform).validate().form())
        {
            return ;
        }

        $("#shirouser").ajaxSubmit({
            url:"${ctx}/basic/save",
            type:"POST",
            dataType:"json",
            success:function(data){
                $("#messageSpanId").text(data.msg);
                if(data.success){
                    $('#dialog-confirm').modal('hide');
                    $("#grid").setGridParam({postData:{page: 1}}).trigger("reloadGrid");
                    $("#message").addClass('alert-success').slideToggle(1000);
                    window.setTimeout(function() {
                        $('#message').slideToggle(1000);
                    }, 2000);
                }else{
                    $("#loading").hide();
                    openError(data.msg,2000,$("#alertForUpload"));
                    $("#do_save").attr("disabled",false);
                }
            }
        });
    });

    $("#expExcel").click(function(){
        window.open("${ctx}/basic/export")
    });
    $("#impExcel").click(function(){
        $( "#dialog-uplaod" ).modal({
            backdrop: 'static'
        });
        $( "#do_save_upload").attr("disabled",false);
    });

    $("#cancel_upload").click(function(){
        $( "#dialog-uplaod" ).modal("hide");
    });

    $("#do_save_upload").click(function(){
        $( "#do_save_upload").attr("disabled",true);
        $("#uploadForm").ajaxSubmit({
            url:"${ctx}/basic/upload",
            type:"POST",
//            dataType:"json",
            success:function(data){
                if(data.success){
                    $('#dialog-uplaod').modal('hide');
                    $("#grid").setGridParam({postData:{page: 1}}).trigger("reloadGrid");
                }else{
                    $("#loading").hide();
                    openError(data.msg,2000,$("#alertForUpload"));
                    $("#do_save").attr("disabled",false);
                }
            }
        });
    });


    $("#downExcel").click(function(){
        window.open("${ctx}/basic/downTemplete?name=user");
    });

</script>
</body>
</html>
