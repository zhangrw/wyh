var rootPath = publicPath;
var sURLParms = '';

window.urlParam = '';
function toolTipShow(tooltipId, infoDivId, params, showStyle) {
	// 处理参数
	$('#' + tooltipId).data(params);
	window.urlParam = jQuery.param(params); // jquery参数序列化

	// 显示tooltip
	var bL = document.getElementById(infoDivId);
	var posX = 0;
	var posY = 0;
	if (bL.offsetParent) {
		for ( var posX = 0, posY = 0; bL.offsetParent; bL = bL.offsetParent) {
			posX += bL.offsetLeft;
			posY += bL.offsetTop;
		}
		posX -= 1;
		posY -= 1;
	} else {
		posX = bL.x;
		posY = bL.y;
	}
	
	posX += 53;
	//posY -= 10;
	var rI = document.getElementById(tooltipId);
	if (rI != null) {
		rI.style.position = 'absolute';
		rI.style.left = posX + 'px';
		rI.style.top = posY + 'px';
		rI.style.display = 'block';
	}

	// 控制子项目显示
	if (showStyle) {
		$.each(showStyle, function(i, n) {
			if (n) {
				$($('#' + tooltipId + ' ul li').get(i)).show();
			} else {
				$($('#' + tooltipId + ' ul li').get(i)).hide();
			}
		});
	}
}

//绑定关闭tooltip,被多个infoDiv.
function toolTipClose(tipIds, infoDiv, DISTANCE) {
	DISTANCE = DISTANCE ? DISTANCE : 30;
	infoDiv = infoDiv ? infoDiv : '.infoBlue';
	for ( var i = 0; i < tipIds.length; i++) {
		$(document).off('mouseleave', tipIds[i]).on('mouseleave', tipIds[i],
				function(e) {
					$(this).hide();
				});
	}
	$(document).off('mouseleave', infoDiv).on('mouseleave', infoDiv,
	function(e) {
		var ileft = $(this).offset().left;
		var itop = $(this).offset().top;
		if (e.pageX < ileft || e.pageY < itop || e.pageY > itop + DISTANCE ) {
			for ( var i = 0; i < tipIds.length; i++) {
				$(tipIds[i]).hide();
			}
		}
	});

}

//先关闭再打开
function openNewBlank(url, check, tooltipName){
	 if(tooltipName){
		var rowId = $('#'+tooltipName).data('legId');
		 check = rowId+ check;
	 }
/*	 var win =  window.open(url,check);
     win.close();*/
     window.open(url,check);
}
 
 
//每次都重新打开一个新窗口
function adpNewWin(url){
	var timestamp = new Date().getTime();
	window.open(url, timestamp );
}
 
//定义全局变量来判断页面是否是第一次打开,局限性:中途刷新页面时失效
window.adpWinMap = {};
function adpWin(url){
	 
	 var _win = window.adpWinMap[url];
	 
	 if($.isWindow(_win)&&$(_win).width()){
		 _win.focus();
		 //title闪烁 -_-!
		 var _title = $(_win.document).find('title').html();
		 $(_win.document).find('title').html('点击查看');
		 for(var i = 1 ;i<8;i++){
			 +function(j){
				 window.setTimeout(function(){
					 if(j%2){
						 $(_win.document).find('title').html(_title);
					 }
					 else{
						 $(_win.document).find('title').html('点击查看');
					 }
						 
				 },300*j);
			 }(i);
		 }
	 }else{
		 _win = window.open(url);
		 window.adpWinMap[url]=_win;
	 }
	 
	 //删除已关闭的页面
	 for(var u in window.adpWinMap){
		 var w = window.adpWinMap[u];
		 if($.isWindow(w)&&!$(w).width()){
			 delete window.adpWinMap[u];
		 }
	 }
	 
}


// 打开新的窗口
function openDialog(_url, param , _w, _h) {
	_w = _w ? _w : screen.width;
	_h = _h ? _h : screen.height;
	return window.showModalDialog(_url, param, "dialogWidth:" + _w
			+ "px;dialogHeight:" + _h + "px;center:yes;status:0;help:no;");
}

function openDialogCallBack(_url, param , _w, _h) {
	_w = _w ? _w : screen.width;
	_h = _h ? _h : screen.height;
	var res = window.showModalDialog(_url, param, "dialogWidth:" + _w
			+ "px;dialogHeight:" + _h + "px;center:yes;status:0;help:no;");
	
	$(document).trigger('openDialogCallBack',res );	
	
}


function openStaticWindow(_url, target, _w, _h) {
	_w = _w ? _w : screen.width;
	_h = _h ? _h : screen.height;
	var newwin = null;
	if (typeof (staticWindow) == 'undefined' || staticWindow.closed) { 
		newwin = window.open(_url,	target ,"width="+ _w + ",height="+ _h + ","+ "left="+ (screen.width - _w)/ 2	+ ",top="+ (screen.height - _h)/ 2+ ","
								+ "status=no,help=no,toolbar=no,menubar=no,scrollbars=yes,resizable=no");
		newwin.location.href = _url;
		staticWindow = newwin;
		staticWindow.focus();
	} else { 
		staticWindow.name = target;
		staticWindow.location = _url;
		staticWindow.resizeTo(_w, _h);
		staticWindow.focus();
	}

}

//bootstrap样式的model窗口
function bsModalDialog(text_url , _w, _h, _title, callback, _p,btnShow ){
	
	var dialogHtml ='<div id="tempxx-dialog" class="modal fade" >'
    +'<div class="modal-dialog" style="width:'+_w+'; ">'
    +'<div class="modal-content">'
    +'<div class="modal-header">'
    +'<button type="button" class="close" data-dismiss="modal" aria-hidden="true" >&times;</button>'
    +'<h4 class="modal-title">'+_title+'</h4>'
    +'</div>'
    +'<div class="modal-body" style="height:'+_h+'; overflow-y:auto;overflow-x:hidden;">'
    +'<!-- 放置html -->'
    +'</div>'
    +'</div><!-- /.modal-content -->'
    +'</div><!-- /.modal-dialog -->'
	+'</div>';
	
	var footBtn ='<div class ="breadcrumb" style="text-align:center;margin-top:5px;" >'
		+'<button type="button" class="btn btn-default btn-sm" data-dismiss="modal" >关闭</button>'
        +'<button type="button" class="btn btn-primary btn-sm" id="tempxx-dialog-btn" >确定</button></div>';
	
	//=====构造model对话框=====
    if($('#tempxx-dialog')[0]){ $('#tempxx-dialog').remove(); }
    $('body').append(dialogHtml);
    
    //=====填充内容=====
    if(text_url && text_url.indexOf("/")!=-1){//判断是url还是text
        var timeFlag = new Date().getTime();
        var eventName ='temp-event' + timeFlag ;
        var param= { eventName : eventName  };
        if(_p) $.extend(param , _p );
        $('#tempxx-dialog .modal-body').load( text_url +'?'+$.param(param) );
        
        //========绑定确定事件============
        $(document).bind(eventName, function(e,text ){
        	callback(e,text);
        	$('#tempxx-dialog').modal('hide');
        });
        
    }else{
    	$('#tempxx-dialog .modal-body').html(text_url + footBtn);
    }	
	//=========初始化对话框==========
	$("#tempxx-dialog").modal({
	     backdrop: 'static',
	     show: true
	});

    //========band窗口关闭时销毁dom========  
    $(document).on('hidden.bs.modal', function (e) {
    	callback();
        $('#tempxx-dialog').remove();
    });
    
    //========当确认时销毁dom========  
    $(document).off('click','#tempxx-dialog-btn').on('click','#tempxx-dialog-btn', function(){
    	$('#tempxx-dialog').modal('hide');
    });
    
}

//=============bootstrap确认窗口================
function bs_confirm(_callback,_text){
	_text = _text ? _text:'是否确认删除！';
    bsModalDialog(_text, '40%', '150px' ,'确认', _callback );  
}


window.PUwin;
function popUp(pu_item, h, w ) {
	
	var newwin = null;
	if (typeof (PUwin) == 'undefined' || PUwin.closed) {
		if(w && h){
			newwin = window.open(pu_item , "", "width=" + w	+ ",height=" + h
					+ ",status=no,resizable=yes,scrollbars=yes" );
		}else{
			newwin = window.open(pu_item , "" );
		}
		
		if (newwin != null) {
			if (newwin.opener == null) newwin.opener = self;
			//newwin.location.href = pu_item;
		}
		PUwin = newwin;
		//PUwin.focus();
	} else {		
		PUwin.location = pu_item;
		//PUwin.resizeTo(w, h);
		//PUwin.focus();
	}
}

function MultiDimensionalArray(iRows, iCols) {
	var i;
	var j;
	var a = new Array(iRows);
	for (i = 0; i < iRows; i++) {
		a[i] = new Array(iCols);
		for (j = 0; j < iCols; j++) {
			a[i][j] = "";
		}
	}
	return (a);
}

/**
 * N/A列
 */
function naFormatter(cellValue, options) {
	if (!cellValue) {
		return cellValue='N/A';
	}
	return cellValue;
}

/**
 * zero列
 */
function zeroFormatter(cellValue, options) {
	if (!cellValue) {
		return cellValue=0;
	}
	return cellValue;
}
/**
 * radio 列
 */
var radioFormatter = function(cellValue, options) {
	if (cellValue) {
		return '<input type="radio" name="radio_' + options.gid + '" value="' + cellValue + '"/>';
	}
	return '';
};

/**
 * 工卡条目radio 列
 */
function checkBoxFormatter(cellvalue, options, rowObject) {
	var result = '';
	if (cellvalue) {
		// 5-拒绝 , 10-完成,72-系统智能关闭 
		if (rowObject.status != 5 || rowObject.status != 10 ||rowObject.status != 72 ) {
			result = '<input type="checkbox" name="cb_' + options.gid + '" value="' + cellvalue + '"/>';
		}
	}
	return result;
}
/**
 * 告警等级
 */
var alarmItemPriorityFormatter = function(cellvalue, options, rowObject) {
	var result = '';
	if (cellvalue || 0 == cellvalue) {
		var ls = "";
		switch (cellvalue) {
		case 1:
			ls = "高";
			break;
		case 10:
			ls = "中-高";
			break;
		case 120:
			ls = "中";
			break;
		case 200:
			ls = "中-低";
			break;
		case 1000:
			ls = "低";
			break;
		default:
			ls = "告警";
		}
		result = '<div class= "alarm_Level_'+ cellvalue	+ '" style="border:1px solid #000;margin-left:auto;margin-right:auto;width:16px;height:16px;" title="' + ls + '" ></div>';

	}
	return result;
};


/**
 * 告警等级
 */
var otherAlarmLevelFormatter = function(cellvalue, tailId, legId, ctx) {
	var result = '';
	if (cellvalue || 0 == cellvalue) {
		result = alarmItemPriorityFormatter(cellvalue);
		var url = ctx + '/recordSummary/faultSummary/dialogFaultSummary?tailId=' + tailId
				+ '&legId=' + legId;
		result = '<a href="javascript:{}" onClick="openWindow(\'' + url
				+ '\',event,820)">' + result + '</a>';
	}
	return result;
};

/******************************************************************************* 
 * 性能预警告警等级图片输出 author:chengkun
 * 
 ******************************************************************************/
var permonAlarmLevelFormat = function(rowObject) {
	var alarmType = rowObject.type ? rowObject.type : ' ';
	var grade = rowObject.grade;
	var imageName = '';
	var ls = "";
	if (alarmType.toLowerCase() == 'short') {
		imageName = 'pgc-fld-short-'+ grade;
		ls = "耗油率高于-短期平均耗油率";
	} else if (alarmType.toLowerCase() == 'long') {
		imageName = 'pgc-fld-long-'+ grade ;
		ls = "耗油率高于-长期平均耗油率";
	} else if (alarmType.toLowerCase() == 'sudden') {
		imageName = 'pgc-engine-sudden';
		ls = "突变预警";
	} else if (alarmType.toLowerCase() == 'trend') {
		imageName = 'pgc-engine-trend';
		ls = "趋势预警";
	} else if (alarmType.toLowerCase() == 'exceed') {
		imageName = 'pgc-engine-exceed';
		ls = "超限预警";
	}else {
		imageName = 'pgc-fld-short-1';
		ls = "预警";
	}

	var rStr = '<div class= "img16-border '+ imageName+ '" '
			+' title="'	+ ls + '" ></div>';	
	return rStr;
};

//预警告警====
function fldOrEngbs(values, row ,parent ){
	 var rValue = "0.0000";
	 var returnStr;
	 if (values) {rValue = values.toFixed(4);}	
	 if(parent.type == '1'){ 
		 	returnStr='变化值：' +rValue;		 
		}else { 
		 	returnStr='变化值：'+rValue;			
		}
	 return returnStr;
}

function alarmTypefmt(cellvalue, options, rowObject){
	var result = "";
	if(cellvalue){
		if(cellvalue=='1'){
			result="滑油预警";
		}else if(cellvalue=="2"){
			result="发动机预警";
		}else if(cellvalue=="3"){
			result='APU预警';
		}
	}
	return result;
}
// 格式化日期
var priorityDateFormatter = function(cellvalue, options, rowObject) {
	var udate = "";
	if (cellvalue) {
		var date = new Date(cellvalue);
		udate = DateFormat.format(date, 'MM-dd hh:mm');
	}
	return udate;
};

var priorityFullDateFormatter = function(cellvalue, options, rowObject) {
	var udate = "";
	if (cellvalue) {
		var date = new Date(cellvalue);
		udate = DateFormat.format(date, 'yyyy-MM-dd hh:mm:ss');
	}
	return udate;
};


var yyyyMMddhhmmDateFormatter = function(cellvalue, options, rowObject) {
	var udate = "";
	if (cellvalue) {
		var date = new Date(cellvalue);
		udate = DateFormat.format(date, 'yyyy-MM-dd hh:mm');
	}
	return udate;
};

var mmddFormatter = function(cellvalue, options, rowObject) {
	var udate = '';
	if (cellvalue) {
		var date = new Date(cellvalue);
		udate = DateFormat.format(date, 'MM-dd');
	}
	return udate;
};

var yymmddFormatter = function(cellvalue, options, rowObject) {
	var udate = '';
	if (cellvalue) {
		var date = new Date(cellvalue);
		udate = DateFormat.format(date, 'yyyy-MM-dd');
	}
	return udate;
};

var hhmmssFormatter = function(cellvalue, options, rowObject) {
	var udate = '';
	if (cellvalue) {
		var date = new Date(cellvalue);
		udate = DateFormat.format(date, 'hh:mm:ss');
	}
	return udate;
};

var hhmmFormatter = function(cellvalue, options, rowObject) {
	var etadate = '';
	if (cellvalue) {
		var date = new Date(cellvalue);
		etadate = DateFormat.format(date, 'hh:mm');
	}
	return etadate;
};

var mmFormatter = function(cellvalue, options, rowObject) {
	var etadate = '';
	if (cellvalue) {
		var date = new Date(cellvalue);
		etadate = DateFormat.format(date, 'MM');
	}
	return etadate;
};

var alarmStatusFormatter = function(cellvalue, options, rowObject) {
	var result = '';
	if (typeof (cellvalue) != "undefined") {
		if (cellvalue == 0) {
			if(rowObject&&rowObject.type==15)
				result = "<span class='label alarm_event_status_0'>新预警</span>";
			else
				result = "<span class='label alarm_event_status_0'>新告警</span>";
		} else if (cellvalue == 3) {
			result = "<span class='label alarm_event_status_2'>监控中</span>";
			//result = "<span class='label alarm_event_status_1'>已接受</span>";
		} else if (cellvalue == 5) {
			result = "<span class='label alarm_event_status_4'>已拒绝</span>";
		} else if (cellvalue == 8) {
			result = "<span class='label alarm_event_status_3'>已进入维修流程</span>";
		} else if (cellvalue == 10) {
			result = "<span class='label alarm_event_status_3'>已完成</span>";			
		} else if (cellvalue == 21) {
			result = "<span class='label alarm_event_status_5'>无效</span>";
		} else if (cellvalue == 31) {
//			result = "<span class='label alarm_event_status_6'>MPF已匹配</span>";
			result = "<span class='label alarm_event_status_7'>已加入故障预警</span>";
		} else if (cellvalue == 71) {
			result = "<span class='label alarm_event_status_8'>监控关闭</span>";
		} else if (cellvalue == 72) {
			result = "<span class='label alarm_event_status_9'>抑制关闭</span>";
		} else if (cellvalue == 91) {
			result = "<span class='label alarm_event_status_9'>系统自动过期</span>";
		} /*else if (cellvalue == 10) {
			result = "<span class='label alarm_event_status_10'>FDE已匹配</span>";
		} else if (cellvalue == 11) {
			result = "<span class='label alarm_event_status_11'>已转至AO</span>";
		} else if (cellvalue == 12) {
			result = "<span class='label alarm_event_status_12'>已转至DD</span>";
		}*/
	}
	return result;
};

var permonStautsFormatter = function(cellvalue, options, rowObject) {
	var result = '';
	if (typeof (cellvalue) != "undefined") {
		if (cellvalue == 0) {
			result = "<span class='label alarm_event_status_0'>新预警</span>";
		} else if (cellvalue == 3) {
			result = "<span class='label alarm_event_status_2'>监控中</span>";
		} else if (cellvalue == 5) {
			result = "<span class='label alarm_event_status_4'>已拒绝</span>";
		} else if (cellvalue == 8) {
			result = "<span class='label alarm_event_status_7'>已转为故障警</span>";
		} else if (cellvalue == 10) {
			result = "<span class='label alarm_event_status_3'>已完成</span>";
		} else if (cellvalue == 71) {
			result = "<span class='label alarm_event_status_8'>监控关闭</span>";
		} else if (cellvalue == 72) {
			result = "<span class='label alarm_event_status_5'>系统智能关闭 </span>";
		} else if (cellvalue == 91) {
			result = "<span class='label alarm_event_status_9'>系统自动过期</span>";
		}

	}
	return result;
};

var alarmTypeFormatter = function(cellvalue, options, rowObject) {
	var result = '';
	if (cellvalue) {
		if (10 == cellvalue) {
			result = '<span title="驾驶舱效应">FDE</span>';
		} else if (20 == cellvalue && rowObject.isservicemessage != 1) {
			result = '<span title="维护消息">MMSG</span>';
		} else if (30 == cellvalue && rowObject.isservicemessage != 1) {
			result = '<span title="POSSIBLE-FDE告警">POSSIBLE-FDE</span>';
		}

	}
	return result;
};

function airportFormatter(cellvalue, options, title, ctx) {
	var result = '';
	if (cellvalue && title) {
	result = '<a href="javascript:{}" class="airport_popover" title="'+title+'"><span id="airport-popover-"'+new Date().getTime()+' style="color:blue;">' + cellvalue + '</span></a>';
	}
	return result;
	
}


function legStatusFormatter(cellvalue, options, rowObject, type) {
	var result = '';
	var nb = new Array('', '', '', '', '', '', '', '', '', '');
	if (cellvalue) {
		var isservicemessage = rowObject.isservicemessage;
		if (isservicemessage == 1 && type == 0) {

		} else {
			var bc = new Array();
//			/** 新告警 **/
//			public static final int NEW = 0; 
//			/** 监控 **/
//			public static final int MONITOR = 3; 
//			/** 拒绝 **/
//			public static final int REFUSE = 5; 
//			/** 已进入维修流程 **/
//			public static final int ACCEPT = 8; 
//			/** 完成 **/
//			public static final int COMPLETE = 10; 
//			/** 无效 **/
//			public static final int INVALID = 21; 
//			/** 已加入故障预警 **/
//			public static final int ADD_FAULT_WARNING = 31; 
//			/** 监控关闭 **/
//			public static final int MONITOR_CLOSE = 71; 
//			/** 系统智能关闭 **/
//			public static final int SYS_SMART_CLOSE = 72; 
//			/** 系统自动过期 **/
//			public static final int SYS_AUTO_EXPIRE = 91; 
			
			
			// 新告警
			bc[0] = 'alarm_event_status_0';
			// 已接受
			//bc[1] = 'alarm_event_status_1';
			// 监控中
			bc[3] = 'alarm_event_status_2';
			// 已完成
			bc[10] = 'alarm_event_status_3';
			// 拒绝
			bc[5] = 'alarm_event_status_4';
			// 系统智能关闭
			bc[72] = 'alarm_event_status_5';
			// MPF已匹配
			//bc[6] = 'alarm_event_status_6';
			// 已加入故障预警
			bc[31] = 'alarm_event_status_7';
			// 监控关闭
			bc[71] = 'alarm_event_status_8';
			// 系统自动过期
			bc[91] = 'alarm_event_status_9';
			// 被FDE匹配
			//bc[10] = 'alarm_event_status_10';
			// 已转至AO---到维修流程
			bc[8] = 'alarm_event_status_11';
			// 已转至DD
			//bc[12] = 'alarm_event_status_12';

			for ( var i in cellvalue) {
				var legNo = cellvalue[i].legNo;
				var status = cellvalue[i].legStatus;
				nb[Math.abs(legNo)] = typeof (bc[status]) != 'undefined' ? bc[status]
						: '';
			}
			var tmpTd = '';
			for ( var j = 0; j >= -9; j--) {
				tmpTd += '<td class="' + nb[Math.abs(j)] + '" >' + j + '</td>';
			}
			result = '<table class="table-swf" ><tbody><tr>' + tmpTd
					+ '</tr></tbody></table>';
		}
	}
	return result;
}

/**
 * 自动关闭alert
 */
function createAutoClosingAlert(selector, delay) {
	window.setTimeout(function() {
		$('#' + selector).alert('close');
	}, delay);
}

// 格式化小数值
var priorityDecimalsValueFormatter = function(cellvalue, options, rowObject) {
	var rValue = "0.0000";
	if (cellvalue) {
		rValue = cellvalue.toFixed(4);
	}
	return rValue;
};

// 格式化小数值(两位)
var priorityDecimalsTwoValueFormatter = function(cellvalue, options, rowObject) {
	var rValue = "";
	if (cellvalue) {
		rValue = cellvalue.toFixed(2);
	}
	return rValue;
};

// 操作日志类型
// add by liuwei 2012.11.16
var priorityLogTypeFormatter = function(cellvalue, options, rowObject) {
	var ls = "";
	var _lv = parseInt(cellvalue);
	switch (_lv) {
	case 1:
		ls = "系统日志";
		break;
	case 2:
		ls = "安全日志";
		break;
	case 3:
		ls = "应用日志";
		break;
	case 4:
		ls = "其它日志";
		break;
	default:
		break;
	}
	return ls;
};

// 操作日志等级
// add by liuwei 2012.11.16
var priorityLogLevelFormatter = function(cellvalue, options, rowObject) {
	var ls = "";
	var _lv = parseInt(cellvalue);
	switch (_lv) {
	case 0:
		ls = "信息";
		break;
	case 1:
		ls = "警告";
		break;
	case 2:
		ls = "错误";
		break;
	case 3:
		ls = "错误";
		break;
	case 4:
		ls = "瘫痪";
		break;
	default:
		break;
	}
	return ls;
};

/**
 * 性能预警--告警类别判断 * 
 */
function pgcSystemTypefmt(cellvalue, row ){
	var result = cellvalue ;
	var dtype = row.type ? row.type :' ' ;
	if(dtype.toLowerCase()=='long'){
		result +="-长期异常";
	}else if(dtype.toLowerCase()=="short"){
		result +="-短期异常";
	}else if(dtype.toLowerCase()=="fix"){
		result +="-固定异常";
	}else if(dtype.toLowerCase()=='sudden'){
		result +="-突变预警";
	}else if(dtype.toLowerCase()=="trend"){
		result +="-趋势预警";
	}else if(dtype.toLowerCase()=="exceed"){
		result +="-超限预警";
	}else{
		result +="-预警";
	}
	
	return result;
}



var apuSystemTypefmt = function(cellvalue, options, rowObject ) {
	var result = "";
	// 一级
	if (row.type.toUpperCase() == 'EXCEED') {
		result = "超限-";
	}
	if (row.type.toUpperCase() == "TREND") {
		result = "趋势-";
	}
/*	// 二级
	if (cellvalue.indexOf('GLA') != -1) {
		result += "APU转速";
	}
	if (cellvalue.indexOf("IGV") != -1) {
		result += "进口导向叶片位置";
	}
	if (cellvalue.indexOf("WB") != -1) {
		result += "APU引气流量";
	}
	if (cellvalue.indexOf("SCV") != -1) {
		result += "喘振控制阀位置";
	}
	if (cellvalue.indexOf("LOT") != -1) {
		result += "APU滑油低温";
	}
	if (cellvalue.indexOf("HOT") != -1) {
		result += "APU滑油高温";
	}
	if (cellvalue.indexOf("STA") != -1) {
		result += "APU启动时间";
	}
	if (cellvalue.indexOf("EGTP") != -1) {
		result += "APU排气温度峰值";
	}
	if (cellvalue.indexOf("NPA") != -1) {
		result += "排气温度峰值时APU转速";
	}else{
		result += cellvalue;
	}
	if (cellvalue.indexOf("_1") != -1) {
		result += "_1";
	} else if (cellvalue.indexOf("_2") != -1) {
		result += "_2";
	}*/
	result += cellvalue;
	return result;
};

//bootstrap常规工具条
var noramlProgress = function(curValue, maxValue, styleclass, showtext ) {
	if(!styleclass) styleclass = '';
	var cur_persent = Math.ceil(curValue * 100 / maxValue) ;
	var prgress ='<div class="progress" style="white-space:normal; margin-bottom:0px;" >'
        +' <div class="progress-bar '+styleclass+'" aria-valuenow="'+cur_persent+'" style="width:'+cur_persent+'%;" aria-valuemin="0" aria-valuemax="100" >'
        +'</div>';
        if(showtext){
            prgress +=' <div style="float:right;">'+curValue+'</div>';
        }else{
        	prgress +=' <span class="sr-only">'+curValue+'</span>';
        }
        prgress +='</div>';

	return prgress;

};

//bootstrap正负向工具条
var plusMinusProgress = function(curValue, maxValue ) {
	
	var curLength = Math.abs(curValue);
	var maxlength = maxValue*2;
	var blank = (curValue>=0)?maxValue:(maxValue + curValue);
	var blank_persent = blank * 100 / maxlength ;
	
	var cur_persent = curLength * 100 / maxlength ;
	//当前条颜色
	var cur_class =(curValue>=0) ?'progress-bar-success':'progress-bar-danger';
	var prgress ='<div title="'+maxValue+'"  class="progress" style="white-space:normal; margin-bottom:0px;" >'
      +' <div title="'+(-maxValue)+'" class="progress-bar " style="width:'+blank_persent+'%;background-color: #F5F5F5;" ></div>'
      +' <div title="'+curValue+'" class="progress-bar '+cur_class+'" style="width:'+cur_persent+'%;" ></div>'      
      +'</div>';

	return prgress;

};


//高级工具条
var drawProgress = function(width, height, color, datas) {
	var min = datas.min;
	var max = datas.max;
	var avg = datas.avg;
	var cur = datas.cur;
	var cur_persent = (cur - min) * 100 / (max - min);
	var avg_persent = (avg - min) * 100 / (max - min);

	var prgress = '<div class="progressck" style="height:'+ height+ 'px; background-color: #fff; border:2px solid #000; width:'
			+ width	+ 'px;" title="min:'+ min+ ' max:'+ max	+ ' avg:'+ avg	+ ' cur:'	+ cur	+ '" >'
			+ '<div style="height:'	+ height+ 'px; float:left; background-color:'+ color+ '; width:'+ cur_persent+ '%;"  ></div>'
			+ '<div style="height:'	+ height+ 'px; width:2px;  background-color:#000; position:relative;  left:'+ avg_persent + '%;"  ></div>' 
			+ '</div>';

	return prgress;

};

//
// <script type="text/javascript">
// document.write(drawProgress(200,20,'#FAA732',{min:1, max:70, cur:20,
// avg:50}));
// document.write(drawVertical(20,200,'#FAA732',{min:1, max:70, cur:20,
// avg:50}));
// </script>

var drawVertical = function(width, height, color, datas) {
	var min = datas.min;
	var max = datas.max;
	var avg = datas.avg;
	var cur = datas.cur;
	var cur_persent = (cur - min) * 100 / (max - min);
	var avg_persent = (avg - min) * 100 / (max - min);

	var vertical = '<div class="verticalck"  style="width:'
			+ width
			+ 'px; height:'
			+ height
			+ 'px; background-color: #fff; border: 2px solid #000;" title="min:'
			+ min
			+ ' max:'
			+ max
			+ ' avg:'
			+ avg
			+ ' cur:'
			+ cur
			+ '" >'
			+ '<div	style="width:'
			+ width
			+ 'px; height: 2px; background-color: #000; float: left; position: relative; top:'
			+ (100 - avg_persent) + '%;z-index:1"></div>'
			+ '<div	style="width:' + width + 'px; background-color:' + color
			+ '; position: relative; height:' + cur_persent + '%; top:'
			+ (100 - cur_persent) + '%;"></div>' + '</div>';

	return vertical;

};

// 航班条
var flightBar = function(beginTime, endTime, flightInfos, url, css) {
	var barLength = endTime - beginTime;
	var totleWidthPersent = 0;
	var result = '<div style="height:20px; border:1px solid #ccc; background-color:#fff; text-align:center;"  >';
	var inner = '';
	for ( var i in flightInfos) {
		if (!flightInfos[i].beginTime)
			continue;
		if (!flightInfos[i].endTime)
			continue;

		var begin = flightInfos[i].beginTime;
		var end = flightInfos[i].endTime;
		var tailNo = flightInfos[i].tailNo;
		var flightNumber = flightInfos[i].flightNumber;
		var priority = flightInfos[i].priority;

		if (begin < beginTime)
			begin = beginTime;
		if (end > endTime)
			end = endTime;

		var startDot = begin - beginTime;
		var curStartPersent = startDot * 100 / barLength - totleWidthPersent;

		var widthPersent = (end - begin) * 100 / barLength;

		inner += '<div class="alarm_Level_'
				+ priority
				+ '" '
				+ 'onclick="window.location=\''
				+ url
				+ '?tailNo='
				+ tailNo
				+ '&flightNumber='
				+ flightNumber
				+ '\'" '
				+ 'style="cursor: pointer;text-align:center; border-left:2px solid #aaa; border-right:2px solid #aaa; height:18px; width:'
				+ widthPersent + '%; float:left;  position:relative;  left:'
				+ curStartPersent + '%;"  >'

				+ flightInfos[i].flightNumber

				+ '</div>';
		if ((totleWidthPersent + widthPersent) <= 1) {
			totleWidthPersent += widthPersent;
		}
	}
	if (!inner)
		inner = '此时间段内无航班！';
	result += inner + '</div>';

	return result;

};

/**
		showMessage({
			message:".....要显示的信息",
			isSuccess:false,
			callback:function(){
				//window.close();
			}
		});
 * 
 * 
 * @param message
 *            要提示的信息
 * @param isSuccess
 *            是正确信息（true）还是错误信息(false)
 */
function showMessage(messageOrOptions, _isSuccess) {
	var defaultOpts = {
		//自动关闭
		autoClose:true,
		//非单例，每次执行都会新建
		singleton:false,
		//具体信息，可以是html
		message:null,
		//决定消息框的样式
		isSuccess:false,
		//窗体关闭回调
		callback:null,
		//信息的分类,该Class会加在infoDiv的class上
		//当为singleton，创建infoDiv会加入messageClass判断
		messageClass:''
	}
	var containerId = 'info-container-id';
	var $container = $("#"+containerId);
	var $singletonInfo;
	$container.length||($container=$('<div id="'+containerId+'"    ></div>').appendTo('body'));
	

	if(typeof messageOrOptions ==='object'){
		$.extend(defaultOpts,messageOrOptions);
	}else{
		defaultOpts.message = messageOrOptions;
		defaultOpts.isSuccess = _isSuccess;
	}
	
	if (!defaultOpts.message) {
		defaultOpts.message = defaultOpts.isSuccess === true ? '成功，但没有提示信息！' : '失败，但没有提示信息！'
	}
	//创建infoDiv,一条消息一个
	if(defaultOpts.singleton){
		
		$singletonInfo = $container.find(".info-singleton"+(defaultOpts.messageClass?"."+defaultOpts.messageClass:""));
		$singletonInfo.length||($singletonInfo=$('<div class="info-unit info-singleton '+defaultOpts.messageClass+'">'+
				'<button class="close close-info"  type="button">×</button>'+
				'<strong>提示信息</strong>'+
			'</div>').appendTo($container));
		$singletonInfo.on('click','.close-info',{unit:$singletonInfo},_close_info);
		enrichInfo($singletonInfo);
	}else{
		var $notSingletonInfo = $('<div class="info-unit info-singleton-not  '+defaultOpts.messageClass+'">'+
				'<button class="close close-info"   type="button">×</button>'+
				'<strong>提示信息</strong>'+
			'</div>').appendTo($container);
		$notSingletonInfo.on('click','.close-info',{unit:$notSingletonInfo,isSingleton:false},_close_info);
		enrichInfo($notSingletonInfo);
	}
	
	//为消息DOM添加具体信息，样式，事件等
	function enrichInfo($info){
		var $strong = $("strong", $info);
		$strong.html(defaultOpts.message);

		if (defaultOpts.isSuccess === true) {
			$info.addClass('info-success').removeClass('info-danger');
		} else {
			$info.addClass('info-danger').removeClass('info-success');
		}
		$info.show();
		
		//显示的时间计算
		var mess_len = defaultOpts.message.length;
		var times = 400*mess_len;
		
		if(defaultOpts.autoClose===true)
		$info.slideDown(1000, function() {
			/*// 计算展示高度
			var strongHeight = $strong.height();
			var ht = strongHeight > 50 ? strongHeight : 50;*/
			
			// 拉起
			window.setTimeout(function(){
				$info.slideUp(1000,function(){
					$info.find('.close:first').click();
				});
			},times)

		});
	}
	//
	function _close_info(event){
		if(typeof messageOrOptions.callback === 'function'){
			messageOrOptions.callback();
		}
		
		if(event.data.isSingleton===false){
			event.data.unit.remove();
		}else{
			event.data.unit.hide();
		}
	}
	

}
/**
 * 显示成功信息,自动关闭
 * @param message
 * @param callback
 */
function bs_info(message,callback){
	showMessage({
		message:message,
		isSuccess:true,
		callback:callback
	});
}
/**
 * 显示错误信息,自动关闭
 * @param message
 * @param callback
 */
function bs_error(message,callback){
	showMessage({
		message:message,
		isSuccess:false,
		callback:callback
	});
}
/**
 * 显示成功信息,钉在某处，不会自动关闭
 * @param message
 * @param callback
 */
function infoPin(message,callback){
	showMessage({
		message:message,
		isSuccess:true,
		callback:callback,
		autoClose:false
		
	});
}
/**
 * 显示错误信息,钉在某处，不会自动关闭
 * @param message
 * @param callback
 */
function errorPin(message,callback){
	showMessage({
		message:message,
		isSuccess:false,
		callback:callback,
		autoClose:false
		
	});
}





//数组去重
function distinctArray(inArray){
	var dic = {};
	$.each( inArray , function(i, field){
		 dic[field] = i;
		});
	var outArray=[];
	$.each( dic , function(n, v){
		outArray.push(n);
		});
	return outArray;
}


//禁用文本选择
function disableSelection(target){
	if (typeof target.onselectstart!="undefined") //IE route
		target.onselectstart=function(){return false;}
	else if (typeof target.style.MozUserSelect!="undefined") //Firefox route
		target.style.MozUserSelect="none";
	else //All other route (ie: Opera)
		target.onmousedown=function(){return false;}
	target.style.cursor = "default";
	}

function showAlertCk(parentId, text, flag, callback, delayTime){
	var flagclass= 'alert-danger';
	if(flag) flagclass= 'alert-success';
	text= text ? text : 'alert';
	delayTime = delayTime ? delayTime: 2000;
	
	var message = '<div  class="alert '+flagclass+'" ><strong>'+text+'</strong></div>';
	$(parentId).append(message);
	
	window.setTimeout(function(){
	$(parentId).html(''); 
	//提供给外包的callback方法
	$.isFunction(callback)&& callback.call();
	}, delayTime);
}

//checkbox多选的树结构
function checkboxAuto(_url, _targetId ,_option ){
	   var setting = {
	            async : {
	                enable : true, type : "get",
	                url :  _url
	            },
	            check: {
					enable: true,
					chkStyle: "checkbox",
					autoCheckTrigger: true,
					chkboxType: { "Y": "ps", "N": "ps" }	            	           
	            },
	            view: {
	                dblClickExpand: false
	            },
	            data: {
	                simpleData: {
	                    enable: true,
	                    idKey : "id",
	                    pIdKey : "parentId"
	                },
	                key : {
	                    name : "name"
	                }
	            },
	            callback: {
	                onCheck: onCheck,
	                onAsyncSuccess: onAsyncSuccess
	            }
	    };

	   $.extend(true,setting , _option)
	   
    	var idFlag =new Date().getTime() ;
	   	var d_divId = idFlag+'div';
		var d_cleanId = idFlag+'clean';
		var d_checkallId = idFlag+'checkall';
		var d_treeId = idFlag+'tree';
		
    	var _divId = '#'+idFlag+'div';
    	var _cleanId = '#'+idFlag+'clean';
    	var _checkallId = '#'+idFlag+'checkall';
    	var _treeId = '#'+idFlag+'tree';
    	
    	var _html ='<div id="'+ d_divId +'" class="tree-select" style="height:250px;">'
			+'&nbsp;<span id="'+ d_cleanId +'" style="cursor:pointer;">清空</span>'
			+'&nbsp;&nbsp;<span id="'+ d_checkallId +'" style="cursor:pointer;">全选</span>'
    		+'<div id="'+ d_treeId +'" class="ztree" style="height:220px; overflow:auto;"></div>'
    		+'</div>';
    	
    	$("body").append(_html);
    	$.fn.zTree.init($(_treeId), setting);	    
    	
    	//默认选中
    	function onAsyncSuccess(e, treeId, treeNode){
    		var default_input = $(_targetId).val();
    	    if(default_input){
    	    	var treeObj = $.fn.zTree.getZTreeObj(d_treeId);
    	    	var nodes = treeObj.transformToArray(treeObj.getNodes());//获取所有节点
    	        var TEXT =setting.data.key.name;
    	    	$.each(nodes , function(i,field){
    	    		var input_v = field[TEXT];
    	    		if(default_input.indexOf(input_v) !=-1){
    	    			treeObj.checkNode(field, true, true, true);
    	    		}
    	    	});
    	    }
    	}
	    
	    //check事件
	    function onCheck(e, treeId, treeNode) {
	        var zTree = $.fn.zTree.getZTreeObj(d_treeId);
	        var nodes = zTree.getCheckedNodes(true);
	        var v = "", id="";
	        var TEXT =setting.data.key.name;
	        for (var i=0, l=nodes.length; i<l; i++) {
	        	var temp =nodes[i];  
	        	var isParent = temp.isParent;
	        	if(!isParent){
		            v += temp[TEXT] + ",";
		            }	        	
	            //id += nodes[i].ID + ",";
	        }
	        if (v.length > 0 ) v = v.substring(0, v.length-1);
	        $(_targetId).val(v);
	    }
	    
	    $(_targetId).click(function(){
	        var cityObj = $(_targetId);
	        var cityOffset = $(_targetId).offset();
	        $(_divId).css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	        $("body").bind("mousedown", onBodyDown);
	    });
	    
	    function hideMenu() {
	        $(_divId).fadeOut("fast");
	        $("body").unbind("mousedown", onBodyDown);
	    }
	    
	    function onBodyDown(event) {
	        if (!(event.target.id == "fleetCode" || event.target.id == _divId || $(event.target).parents(_divId).length>0)) {
	            hideMenu();
	        }
	    }
	    
   	    //====清除=======
	    $(_cleanId).click(function(){
	        var zTree = $.fn.zTree.getZTreeObj(d_treeId);
	        zTree.checkAllNodes(false);
	        $(_targetId).val('');
	    });
	    
   	    //====全选======
	    $(_checkallId).click(function(){
	        var zTree = $.fn.zTree.getZTreeObj(d_treeId);
	        zTree.checkAllNodes(true);
	    	var nodes = zTree.transformToArray(zTree.getNodes());//获取所有节点
	        var v = "", id="";
	        var TEXT =setting.data.key.name;
	        for (var i=0, l=nodes.length; i<l; i++) {
	        	var temp =nodes[i];  
	            v += temp[TEXT] + ",";
	        }
	        if (v.length > 0 ) v = v.substring(0, v.length-1);
	        $(_targetId).val(v);
	    });	    
	    
}


//popover多选/单选=====================
function selectPopover(_option){
	//默认值======
	var option = {title:'泡芙选择', multiFlag:true };
	$.extend(_option, option);
	//内置domId===
	var idFlag =new Date().getTime() ;
	var _popoverId ='selectPopover_'+idFlag;
	var _searchId ='search_'+idFlag;
	var _selectTable ='selectTable_'+idFlag;
	var _inputType = _option.multiFlag ? 'checkbox':'hidden';
	
	var _html='<div id="'+_popoverId+'" class="aiport-select popover fade bottom in">'
	+'<div class="arrow" style="left:250px;"></div>'
	+'<div class="breadcrumb" style="margin-bottom:0px;"><label class="active">'+_option.title+'</label></div>'
	
	+' <div class="input-group" style="width:253px;">'
	+'  <input id="'+_searchId+'" class="form-control input-sm"  placeholder="搜索:条件"/>'
	+'  <span class="input-group-addon input-sm"> '
	+'  <i class="glyphicon glyphicon-search"></i>'
	+'  </span>'
	+'</div>'
	+'<div style="height:200px;overflow:auto;background-color:#F5F5F5;">'
	+'<table id="'+_selectTable+'" style="font-size:12px;margin-left:5px;cursor:pointer;"></table>'
	+'</div></div>';
	
	
	//初始化=====
	$("body").append(_html);
	var allData='';
	function strutsTable(allData){
    	var htmlStrDep = '';
        $.each(allData , function(i,field){
        	htmlStrDep += '<tr value="'+field.code3+'"><td><input type="'+_inputType+'" value="'+field.code3+'">'+field.code3+'-'+field.longCnname+'</td></tr>';
        });        
        $('#'+_selectTable).html(htmlStrDep);
	}
	
    $.ajax({
        url: _option.url,
        type:"post",
        dataType:"json",
        success:function(result,status){
        	allData =result;
        	strutsTable(allData);
        }
        
    });	
	
    //popover搜索框=======
    $(document).on('keyup','#'+_searchId, function(){
        var value = $(this).val();
        var containStr = '';
        if($.trim(value)){
            $.each(allData ,function(i,field){
                if(field.code3.indexOf($.trim(value.toUpperCase())) != -1){
             	   var _count = field.code3.indexOf(value.toUpperCase());
                   var _length = value.length;
                   containStr += '<tr value="'+field.code+'"><td><input type="'+_inputType+'" value="'+field.code3+'">'+field.code3.substring(0,_count)+'<b style="color:red">'+value.toUpperCase()+'</b>'+field.code3.substring(_count+_length)+'-'+field.longCnname+'</td></tr>';
                } 
            });
            $('#'+_selectTable).html(containStr);
       }else{
    	  strutsTable(allData);
       }
    });
    
	//点击出发机场,popover显示===========
	 $(_option.targetId).click(function(){
		 var ileft = $(_option.targetId).offset().left-230;
		 var itop = $(_option.targetId).offset().top+30;  
	     $('#'+_popoverId).css('top',itop ).css('left', ileft );	    
	     $('#'+_popoverId).toggle();
	 });
	 
	 //选择数据===多选====
	 var checkflag = '#'+_selectTable+' input:checkbox';
	 $(document).on('click', checkflag , function(event){
	   	 var checkedflag = '#'+_selectTable+' input:checkbox:checked';	   	 
	        var resultStr =[];
	        $(checkedflag).each(function(i){
	        	resultStr.push($(this).val());
	        });
	        $(_option.inputId).val(resultStr.toString());
	        //阻止冒泡===
	        event.stopPropagation();
	 });	 

	 //选择数据===单选====
	 var TRflag = '#'+_selectTable+' tr';
	 $(document).on('click', TRflag , function(){
	     if(!_option.multiFlag){
	    	 var result= $(this).find('input').val();
		      $(_option.inputId).val(result);
	     }
	 });
}

//查看备注 模块id pk1...主键标识  callback:回调函数,如刷新当前jqgrid
function openmodelRemak(moduleid,pk1,callback,pk2,pk3){
//function bsModalDialog(text_url , _w, _h, _title, callback, _p,btnShow )
	var _p={},text_url=base_url+"/remark/openmodalRemarkList",_title="备注",_w="50%",_h="400px",
	btnShow=false;
	_p['moduleId']=moduleid;
	if(pk1!="")_p['pk1']=pk1;
	if(pk2!="")_p['pk2']=pk2;
	if(pk3!="")_p['pk3']=pk3;
	bsModalDialog(text_url , _w, _h, _title, callback, _p,btnShow);
}



