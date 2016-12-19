<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sitemesh"
    uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<link rel="stylesheet" href="${ctx}/static/homepage/css/quality.css" type="text/css" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="dropdown" id="message1">
	<p class="li-text2">查看待办事项：</p>
	<a data-toggle="dropdown" class="dropdown-toggle" href="#" 
		title="您有新的消息"> <i class="fa fa-envelope fa-2x"></i>
	</a>
	<ul 
		class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
		<li name="orin" class="dropdown-header"></li>
		<li name="orin"><a id="sel" onclick="openAll('${ctx}/message?type=1')" href="#" > 列出所有通知<i class="fa fa-arrow-up"></i>
		</a></li>
	</ul>

</div>
<!--紧急消息,必须立即处理-->
<div id='fatal-alter' class="modal fade bs-example-modal-sm">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header" style="height: 40px">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="dialog-confirm-title" style="height: 20px;font-size: 10px">未读通知</h4>
				</div>
				<div class="modal-body blockquote">
				</div>
				<div class="modal-footer">
				</div>
			</div>
		</div>
</div>
<script type="text/javascript">
	var status=false;//初始化之后是否展开消息面板
	$(function() {
		init();
		msgDivWidthAdapt();
	});
	
	function msgDivWidthAdapt(){
		var width=$(window).width();
		if(width<1000){
			$("#message1").css("margin-right",1050-width+"px");
		}
	}
	
	//查看所有的消息 修改dropdown源码 添加
	//if(!$parent.hasClass('openAll')){
    	 // $parent.removeClass('open').trigger('hidden.bs.dropdown', relatedTarget)
      //}else{
    	//  $parent.removeClass('openAll');
     // }
	//'${ctx}/message?type=2'
	function openAll(url){
		$("#message1").addClass("openAll");
		//var url="${ctx}/message?type=1"
		init(url);
		if(url){
			$("#sel").attr("onclick","openAll()");
			$("#sel").html("列出未读通知<i class='fa fa-arrow-up'></i>")
		}else{
			$("#sel").attr("onclick","openAll('${ctx}/message?type=1')");
			$("#sel").html("列出所有通知<i class='fa fa-arrow-down'></i>")
		}
		
	}
	$('#fatal-alter').on('show.bs.modal', function (e) {
	    $(this).find('.modal-dialog').css({
	        'margin-top': function () {
	            var modalHeight = $('#news-alter').find('.modal-dialog').height();
	            return ($(window).height() / 3 - (modalHeight / 2));
	        }
	    });    
	});
	
	//消息框弹出事件  
	$('#message1').on('show.bs.dropdown', function() {
		init();
	});
	$('#message1').on('hide.bs.dropdown', function() {
		init();
	});
	
	var s1,s2;
	function state1() {
		$(".fa-envelope").removeClass("r2");
		$(".fa-envelope").addClass("r1");
		s1=setTimeout(state2, 90);
	}
	function state2() {
		$(".fa-envelope").removeClass("r1");
		$(".fa-envelope").addClass("r2");
		s2=setTimeout(state1, 90);
	}
	function stop(){
		clearTimeout(s1);
		clearTimeout(s2);
		$(".fa-envelope").removeClass("r1");
		$(".fa-envelope").removeClass("r2");
	}
	
	//弹出模态框5
	function openModel(url){
		$.post('${ctx}/'+url,function(result){
			$("#fatal-alter .modal-body" ).html(result.webContent);
			$("#fatal-alter .modal-title" ).html(result.msgtitle);
			$( "#fatal-alter" ).modal({
				backdrop: true
			});
			init();
		},'json');
	}
	
	var firstInitModel=true;//模态框初始化
	//初始化
	function init(url){
		if(!url)
			url="${ctx}/message";
		$.post(url,"",function(result){
			stop();
			delLi();
			if(result.success){
				var list=result.result;
				var obj = eval(list);
				var count=0;
				var items=[];
				var itemf=[];
				var itemh=[];
				var itemm=[];
				var iteml=[];
				//定义优先级 高中低
				var LEVEL={
					L:1,//低(默认)
					M:2,//中
					H:3,//高
					F:4//立即处理
				}
				//定义打开方式
				var POPTYPE={
					NEWWIN:1,//新建窗口
					CURRWIN:2,//当前窗口
					OPENMODEL:3,//弹出模态框
					OTHER:4//其他方式
				}
				var MSGICON={
					CHAT:'fa fa-comment',//评论 2
					BELL:'fa fa-bell',//提醒
					ENVELOPE:'fa fa-envelope-square',//信封
					WARN:'fa fa-exclamation-triangle'//警告 1
				}
				var BADGEICON={
					SUCCESS:'badge-success',
					DANGER:'badge-danger',
					INFO:'badge-info',
					WARN:'badge-warning'
				}
				
				var BIZTYPE={
					WARN:1,//通知与提醒
					CHAT:2//项目点交互
				}
				//
				function addDropdownitem(val){
					switch(val.level){
						case LEVEL.L://低级别
							var item=createLi(val);
							iteml.push(item);
							break;
						case LEVEL.M:
							var item=createLi(val);
							itemm.push(item);
							break;
						case LEVEL.H:
							var item=createLi(val);
							itemh.push(item);
							break;
						case LEVEL.F:
							var item=createLi(val);
							itemf.push(item);
							break;
						default:
							var item=createLi(val);
							iteml.push(item);
							break;
					}	
				}
				
				var modelitems=[];
				var modelitemf=[];
				var modelitemh=[];
				var modelitemm=[];
				var modeliteml=[];
				var showCloseButton=true;//显示关闭按钮
				
				function addModelItem(val){
					switch(val.level){
					case LEVEL.L://低级别
						var item=createModelLi(val);
						modeliteml.push(item);
						break;
					case LEVEL.M:
						var item=createModelLi(val);
						modelitemm.push(item);
						break;
					case LEVEL.H:
						var item=createModelLi(val);
						modelitemh.push(item);
						break;
					case LEVEL.F:
						var item=createModelLi(val);
						modelitemf.push(item);
						showCloseButton=false;//不显示关闭按钮
						break;
					default:
						var item=createLi(val);
						modeliteml.push(item);
						break;
				}	
				}
				//
				function createModelLi(val){
						var urlStr="href='javascript:"
						var url=createUrl(val);
						switch(val.popType) {
						case POPTYPE.NEWWIN:
							urlStr+="window.open(\""+url+"\")'"
							break;
						case POPTYPE.CURRWIN:
							urlStr+="window.location.href=\""+url+"\"'";
							break;
						default:
							urlStr+="'";
						}
						var item="<p><a class='divout' style='color:#004280;' onclick=clickthis(this) "+urlStr; 
						item+=">";
						item+=val.title+"</a></p>";
						return item;
				}
				
					
				//创建li 当前窗口和新增窗口
				function createLi(val){
					var urlStr="href='javascript:"
					var url=createUrl(val);
					switch(val.popType) {
					case POPTYPE.NEWWIN:
						urlStr+="window.open(\""+url+"\")'";
						break;
					case POPTYPE.CURRWIN:
						urlStr+="window.location.href=\""+url+"\"'";
						break;
					case POPTYPE.OTHER:
						urlStr+="openModel(\""+url+"\")'";
						break;
					default:
						urlStr+="'";
					}
					var item="<li><a "+urlStr+">"
					item+="<div class='clearfix'> <span class='pull-left'>";
					item+="<i class=";
					switch(val.biztype) {
					case BIZTYPE.WARN:
						item+="'"+MSGICON.WARN+"'/>";
						item+=val.title;
						item+="</span>"
						item+="<span class='pull-right badge "+BADGEICON.WARN+"'>"+val.count+"</span>"
						break;
					case BIZTYPE.CHAT:
						item+="'"+MSGICON.CHAT+"'/>";
						item+=val.title;
						item+="<span class='pull-right badge "+BADGEICON.SUCCESS+"'>"+val.count+"</span>"
						break;
					}
					item+="</div>";
					item+="</a>";
					item+="</li>";
					return item;
				}
				
				//每次刷新时,删除之前创建的li
				function delLi(){
					$("#message1 ul li").remove("li[name!=orin]");
					
				}
				
				//创建url
				function createUrl(val){
					var url=val.popUrl;
					if(!$.isEmptyObject(val.suffix)){
						url=url+"?";
						$.each(val.suffix,function(k,v){
							url=url+k+"="+v+"&";	
						})
						url=url.substring(0,url.lastIndexOf("&"));
					}
					return url;
				}
				$(obj).each(function(index){
					var val = obj[index];
					switch(val.popType) {
						case POPTYPE.NEWWIN:
							addDropdownitem(val);
							break;
						case POPTYPE.CURRWIN:
							addDropdownitem(val);
							break;
						case POPTYPE.OPENMODEL:
							addModelItem(val);
							break;
						case POPTYPE.OTHER:
							addDropdownitem(val);
							break;
						default:
							break;
					}
				})
				if(iteml.length>0)
					items.push(iteml);
				if(itemm.length>0)
					items.push(itemm);
				if(itemh.length>0)
					items.push(itemh);
				if(itemf.length>0)
					items.push(itemf);
				
				if(modeliteml.length>0)
					modelitems.push(modeliteml);
				if(modelitemm.length>0)
					modelitems.push(modelitemm);
				if(modelitemh.length>0)
					modelitems.push(modelitemh);
				if(modelitemf.length>0)
					modelitems.push(modelitemf);
				//
				if(modelitems.length>0&&firstInitModel){
					$("#fatal-alter").find("h4").text("您有"+modelitems.length+"条未读通知,请及时查看！").css("color","red");
					$("#fatal-alter .modal-body").html(modelitems.join(''));
					if(showCloseButton)
						$("#fatal-alter button").show();
					else
						$("#fatal-alter button").hide();
					$("#fatal-alter .modal-body")
					$( "#fatal-alter" ).modal({
						backdrop: 'static'
					});
				}
				
				$(".dropdown-header").html("<i class='fa fa-exclamation-triangle'></i>"+items.length+"条通知");
				if(items.length>0){//</li>,<li>
					var regS = new RegExp("</li>,","gi");
					$("#message1 ul .dropdown-header").after((items.join('').replace(regS,'</li>')));
					state1();
					$("#message1 .fa-envelope").css("background-color","#ff0000");
					$(".dropdown-toggle").attr("title","您有新的消息");
					if(firstInitModel){
						$('.dropdown-toggle').dropdown('toggle');
					}
				}else{
					stop();
					$("#message1 .fa-envelope").css("background-color","#017534");
					$(".dropdown-toggle").attr("title","消息");
				}
				
			}else{//没有消息或其他异常
				stop();
				$(".dropdown-header").html("<i class='fa fa-exclamation-triangle'></i>"+0+"条通知");
				$("#message1 .fa-envelope").css("background-color","#017534");
			}
			firstInitModel=false;
		},'json');
	}
	
</script>
