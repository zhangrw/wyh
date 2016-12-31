<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>转账信息维护</title>
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

<div id="select">
    <div class="select-main">
        <form action="" id="searchForm" method="post" class="well-work bs-adp form-inline">
            <c:if test="${isAdmin}">
                <div class="row">
                    <div  class="col-md-1" style="text-align:right">流水号:</div>
                    <div  class="col-md-2">
                        <input type="text" class="form-control input-sm" name="serialNumber" id="serialNumber" value='' placeholder="流水号"/>
                    </div>
                    <div  class="col-md-1" style="text-align:right">所属单位:</div>
                    <div  class="col-md-2">
                        <input type="text" class="form-control input-sm" name="deptName" id="deptName" value='' placeholder="选择所属部门"/>
                        <input id="deptId" name="deptId" hidden/>
                    </div>

                    <!--
                    <div  class="col-md-1" style="text-align:right">转出银行卡号:</div>
                    <div  class="col-md-2">
                      <input type="text" class="form-control input-sm" name="srcbankNumber" id="srcbankNumber" value='' placeholder="银行卡号"/>
                    </div>
                    <div  class="col-md-1" style="text-align:right">转入银行卡号:</div>
                    <div  class="col-md-2">
                        <input type="text" class="form-control input-sm" name="targetbankNumber" id="targetbankNumber"
                               value='' placeholder="银行卡号"/>
                    </div>
                     -->
                    <div  class="col-md-1" style="text-align:right">转账状态:</div>
                    <div  class="col-md-2">
                        <select class="form-control input-sm" name="state" id="state">
                            <option value=""></option>
                            <option value="1">转账等待中</option>
                            <option value="2">转账失败</option>
                            <option value="3">转账成功</option>
                        </select>
                    </div>
                </div>
            </c:if>
            <div  class="row" style="margin-top:5px;">
                <c:if test="${isAdmin}">
                    <div  class="col-md-1" style="text-align:right">姓名:</div>
                    <div  class="col-md-2">
                        <input type="text" class="form-control input-sm" name="userName" id="userName" value='' placeholder="用户姓名"/>
                    </div>
                    <div  class="col-md-1" style="text-align:right">工号:</div>
                    <div  class="col-md-2">
                        <input type="text" class="form-control input-sm" name="jobNumber" id="jobNumber" value='' placeholder="工号"/>
                    </div>
                    <div  class="col-md-1" style="text-align:right">身份证号:</div>
                    <div  class="col-md-2">
                        <input type="text" class="form-control input-sm" name="idNumber" id="idNumber" value='' placeholder="身份证号"/>
                    </div>
                </c:if>
                <div  class="col-md-3" style="float:right;"><!-- margin-right: 30px;margin-top: 5px -->
                    <c:if test='${isAdmin}'>
                        <button type="button" id="btnQuery" class="btn btn-primary btn-align-right btn-sm">查询</button>
                        <button type="button" id="impExcel" class="btn btn-primary btn-align-right btn-sm">导入</button>
                        <button type="button" id="downExcel" class="btn btn-primary btn-align-right btn-sm">模版下载</button>
                    </c:if>
                    <button type="button" id="expExcel" class="btn btn-primary btn-align-right btn-sm">导出</button>
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

<div id="jqgrid"> <!-- style="overflow: auto" 会导致整个列表浮出 而不是列表的滚动条 -->
    <table id="grid"></table>
    <div id="pager"></div>
</div>


<script type="text/javascript">
    $(function(){
        <%--<c:if test="${isAdmin}" >--%>
        $.getJSON("${ctx}/sys/dept/getallDept",function(data) {
            $('#deptName').autocomplete(data,{
                minChars: 0,
                mustMatch:true,
                width:260,
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
        <%--</c:if>--%>

        var option = {
            url : '${ctx}/trans/getdata',
            datatype : 'json',
            mtype : 'POST',
            colNames : ['流水号','用户名','所属部门','工号','身份证号','转账金额(元)',
                '转出银行卡号',
                //'转出银行名称','转入银行卡号','转入银行名称',
                '转账状态','备注信息'],
            colModel :
                [
                {name : 'serial_number',key:true,index : 'serial_number',width:"200"}, //,hidden : true
                {name : 'user_name',  align:'center' },
                {name : 'dept_name', align:'center' },
                {name : 'job_number', align:'center',width:"200"},
                {name : 'id_number', align:'center',width:"200"},
                {name : 'trans_value', align:'center',width:"200"},
                {name : 'srcbank_number', align:'center' ,width:"200"},
//                {name : 'srcbank_name', align:'center' ,width:"200"},
//                {name : 'targetbank_number', align:'center',width:"200" },
//                {name : 'targetbank_name', align:'left',width:"200"},
                {name : 'state', align:'left',formatter:function (value,o,rowobj) {
                    if( value == '1' ){
                        return "转账等待中";
                    }else if(value == '2'){
                        return "转账失败";
                    }else if(value == '3'){
                        return "转账成功";
                    }else{
                        return "";
                    }
                }},
                {name : 'bz', align:'left',width:"200" }
            ],
            rowNum : 15,
            rowList : [ 15, 30, 50 ],
            height : "100%",
            shrinkToFit:false,// 设置出现水平滚动条
            autowidth : true,
            pager : '#pager',
            sortname : 'serial_number',
            sortorder : "desc",
            altRows:true,
            hidegrid : false,
            viewrecords : true,
            recordpos : 'left',
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
<%--<c:if test="${isAdmin}">--%>
                .navButtonAdd('#pager',{caption:"新增",buttonicon:"ui-icon-plus",onClickButton: function(){toAdd()},position:"last"})
<%--</c:if>--%>
                .navButtonAdd('#pager',{caption:"修改",buttonicon:"ui-icon-pencil",onClickButton: function(){toModify()},position:"last"})
<c:if test="${isAdmin}">
                .navButtonAdd('#pager',{caption:"删除",buttonicon:"ui-icon-trash",onClickButton: function(){toDelete()},position:"last"})
</c:if>
        ;
        // 设置出现水平滚动条
        $("#grid").closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'scroll' });
        //自适应
        jqgridResponsive("grid",false);
    });

    $("#btnQuery").click(function(){
        var myform = $("#searchForm").serializeArray();
        var data = {};
        $.each(myform, function(i, field){
            data[field.name]=null;
            if(field.value && ''!=field.value){
                data[field.name] = field.value;
            }
        });
        var postData = $("#grid").jqGrid("getGridParam", "postData");
        $.extend(postData,data);
        $("#grid").jqGrid("setGridParam", {
            search: true
        }).trigger("reloadGrid", [{page:1}]);
    });

    function toAdd(){
        openDialog("${ctx}/trans/openmodaltransinfo?time="+new Date().getTime());
        return;
    }

    $("#cancel2").click(function(){
        $("#dialog-delete").modal("hide");
    });

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
        $.post("${ctx}/trans/delete",
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
        if( $("#grid").jqGrid().getRowData().length == 1 ){// 只有一行数据的时候
            openDialog("${ctx}/trans/openmodaltransinfo?id="+$("#grid").jqGrid().getRowData()[0].id+"&time="+new Date().getTime());
            return;
        }
        var ids = $("#grid").jqGrid('getGridParam','selarrrow');
        if($.isEmptyObject(ids)||ids.length >1) {
            openError('请选择一条数据,且仅选择一条数据',2000);
            return;
        }
        var oneData = $("#grid").jqGrid('getRowData',ids[0]);
        openDialog("${ctx}/trans/openmodaltransinfo?id="+oneData.id+"&time="+new Date().getTime());
        return;
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
        $("#transInfoForm")[0].action = "${ctx}/trans/save";

        var myform = $("#dialog-confirm").find("form").get(0);
        if(!jQuery(myform).validate().form())
        {
            return ;
        }

        $("#transInfoForm").ajaxSubmit({
            url:"${ctx}/trans/save",
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
        window.open("${ctx}/trans/export?"+encodeURI($("#searchForm").serialize()));
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
            url:"${ctx}/trans/upload",
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
        window.open("${ctx}/basic/downTemplete?name=trans");
    });

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
</script>
</body>
</html>
