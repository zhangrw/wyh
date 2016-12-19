
/******************************************************DIALOG START*********************************/


/**
 * 打开一个模态子窗口，并阻塞父窗口。
 * @param arg 父页面向子页面传递参数，在子页面中获取：var dialogArgs = window.dialogArguments
 * 
 * @returns 子窗口想向父窗口传参：在子窗口，window.returnValue={};
 */
function showInputDialog(url, width, height,arg) {
	var style = "status:no;scroll:yes;help:no;resizable:no;center:yes";
	style += ";dialogWidth:" + (width?width:1250) + "px;dialogHeight:" + (height?height:600) + "px";
	var args = {
		"document" : document,
		"dialog" : "dialog",
		"self" : self
	};
	if(arg){
		args.arg=arg;
	}
	return showModalDialog(url, args, style);
}

/******************************************************DIALOG END*********************************/
/******************************************************validation START********************************************/

/**
 * 对jquery validation插件封装。请在ready事件内调用。封装函数会提供一些默认配置，但options可以覆盖。
 * AJAX提交表单对jquery.form.js有依赖，错误信息的显示对jquery.ui的tooltip有依赖。
 * @param formId --- 
 *  
 *@param  valiVars --- validation插件的options参数。
 * 如： { rules :{ ..... }, messages:{ .... }, ...... } 
 * 
 *@param ajaxVars --- 不需要ajax提交，不需要填写。 如果需要ajax提交，请加入ajax的options参数,
 * 不需要你提供data项。 如：
 *  { dataType:'text', success:function(res){
 *  }, error:function(){
 *  } ...... }
 */
function smartValidate(formId, valiVars, ajaxVars) {

	if (!valiVars)
		valiVars = {};
	var defaultOptions ={  
			errorPlacement : _errorPlacement,
			success : function(label, element) {
				//success方法定以后，才会默认将错误消息定义为空。作用和attr('title','')一样.设置success后，正确时也会调用errorPlacement
			}
			/*showErrors: function(errorMap, errorList) {    
			 * //每一次的验证后都要通过showErrors：，然后使用this.defaultShowErrors()调用errorPlacement和success方法

				var successEles =this.validElements(); 
				//验证是否为success元素.validElements()方法逻辑为：不在错误元素（errorList）内的即为success	
				alert(successEles.length)
				if(successEles.length==1){      //当有一个正确元素时：有可能是验证单独一个元素，有可能是submit
					alert(this.pending[successEles.attr('name')])
					if (!this.pending[successEles.attr('name')]) // 若为pending则不添加
					this.successList.push(successEles); //将此元素推入successList中
				}
				this.defaultShowErrors();
				
			},*/
			/*
			ignoreTitle:true, //validation插件默认可以从title中获得错误消息，若为false且option中不定义messages，错误消息不会改变				
			*/
	};
	
	var options = $.extend(defaultOptions, valiVars);
	if (ajaxVars) {
		//ajaxSubmit ---common设置
		if(!ajaxVars.type){
			ajaxVars.type='post';
		}
		if(!ajaxVars.complete){
			ajaxVars.complete=function(){
				//hideMast();
			};
		}
		
		if(!ajaxVars.error){
			ajaxVars.error=function(){
				alert("ajax请求异常，请确定网络稳定！");
			};
		}
		
		
		
		options.submitHandler = function(fm) {
			var url = $(fm).attr('action');
			ajaxVars.url=url;
			$(fm).ajaxSubmit(ajaxVars);
			return false;
		};
	}
	var $form = $("#" + formId);
	if($form.length>0){
		$form.validate(options);
	}else{
		$(function(){
			$form = $("#" + formId);
			if($form.length>0){
				$form.validate(options);
			}else{
				alert("在注册验证时无法找到FORM,id:"+formId);
			}
		});
	}
	
	

}
/**
 * 验证效果函数
 * @param label
 * @param element
 */
function _errorPlacement (label, element) {
	var errorInfo =  label.text();
	var eleName  = element.attr('name');
	if(errorInfo){//有错误
		var errorInfoData = element.data("errorInfo");//错误信息
		element.data("customErrorFlag",1)//是否错误 
		if(errorInfoData != errorInfo){
			element.data("errorInfo",errorInfo);
			var toolTipHanleEle = element;//触发 浮出tooltip的元素
			if(element.is(":checkbox,:radio")){
				//向上寻找父级，两次无法确定唯一父亲，则为第一个元素
				var tagN = element.prop("tagName");
				toolTipHanleEle=$(tagN+"[name='"+eleName+"']").parent();
				if(toolTipHanleEle.length>1){
					toolTipHanleEle = toolTipHanleEle.parent();
					if(toolTipHanleEle.length>1){
						toolTipHanleEle = element
					}
				}
				toolTipHanleEle.addClass("radioAndCheckboxError");
			}
			//show error!!!
			toolTipHanleEle.tooltip({
				items: "*",//toolTipHanleEle 中哪些显示errorInfo（默认显示title）
				content:errorInfo,
				position : {
					my : "left top",
					at : "left bottom"
				},
				/* open : function(event, ui) {
					ui.tooltip.animate({
						top : ui.tooltip.position().top + 10
					}, "fast");
				}, */
				tooltipClass:'tooltipClassForValid'
			});
		}
	} else {//无错误
		if(element.data("customErrorFlag")==1){
			var toolTipHanleEle = element;
			if(element.is(":checkbox,:radio")){
				if(!element.is(".radioAndCheckboxError")){
					toolTipHanleEle = element.parents(".radioAndCheckboxError");
				}
			}
			toolTipHanleEle.tooltip('destroy');
			toolTipHanleEle.removeClass("radioAndCheckboxError");
		}
		element.data("errorInfo","");
		element.data("customErrorFlag",0)
		
	}
}


/**
 * 
 * @param formId
 * @param rls  新的验证规则
 * @param mes  验证错误信息，可以不输入
 */
/*function changeValiRules(formId,rls,mes){
	
	var vali = $.data(document.getElementById(formId),'validator');
	
	if(vali){
		//给无需验证的元素添加永远为真的验证规则，以便去除验证效果。
		//foreverTrue 方法定义在includeHead.jsp中。
		var originalRules = vali.settings.rules;
		for(var i in originalRules){
			if(!rls[i]){//rls中没有的
				rls[i]={
					//!!!!!!!!!!
					//foreverTrue:true	
					
				};					
			}
		}
		//改变验证规则。
		vali.settings.rules=rls;
		//改变验证错误信息。
		if(mes){
			vali.settings.messages=mes;
		}
		
	}else{
		//alert("在改变验证规则时，validator对象不存在！");
	}
}*/

/**
 * 给出formId和要验证元素的name,对元素进行手动验证
 */
function validateEle(formId,name){
	var vali = $.data(document.getElementById(formId),'validator');
	if(vali){
		return vali.element( "[name='"+name+"']:first" );
	}else{
		alert("在手动验证规则时，validator对象不存在！");
	}
} 
/**
 *  form执行validate方法后，要手动验证form内的元素
 *  有至少一个验证错误，返回false；其他情况都返回true.
 *  用法： $('selector').validateEle();
 */
$.fn.validateEle = function(){
	if(this.length){
		var fm = this.parents('form')[0];
		var vali = $.data(fm,'validator');
		var res  = true;
		if(vali){
			
				this.each(function(){
					vali.element(this)||(res=false);
				});
			
			
		}else{
			alert("在手动验证规则时，validator对象不存在！");
		}
		return res;
	}else{
		return true;
	}
}



/******************************************************validation END*********************************************/
/******************************************************datepicker START*********************************************/


//兼容之前的datepicker
/**
 * $('div.date').datepicker();
 */
$.fn.datepicker=function(opt){
	var r = this.smartTimepicker();
	//去除focus,并添加验证
	$(r).find('input').off('focus').on('focusout',function(){
		var v = $(this).val();
		if(v&&!CheckDateTime(v)){
			bs_error('日期格式错误');
			$(this).val('');
		}
	});
	return r;
}

/**
*    例如 ：$('div.date').smartTimepicker({});
*    
*/
$.fn.smartTimepicker=function(opt){
	var defaultOptions ={
			//两者配合使用
			//format:'yyyy-mm-dd hh:ii',
			//minView:'0',//分
			format:'yyyy-mm-dd',
			minView:'2',//天
			
			weekStart:1,
			autoclose:true,
			language:'zh-CN',
			todayBtn:true,
			pickerPosition:'bottom-left'
			
			,pickerReferer :'span'
			
	};
	if(opt){
		$.extend(defaultOptions,opt);
	}
	return this.datetimepicker(defaultOptions);
}


/*
 * 
 * //
		if (showOn === "smartIcon") { // pop-up date picker when button clicked
			var inptNext = $(input).next();
			if(inptNext.is('span.add-on')){
				inst.trigger = inptNext;
			}else{
				inst.trigger =$('<span class="add-on"><i class="glyphicon glyphicon-calendar"></i></span>');
			}
			
			//console.log(inst);
			
			//console.log(input);
			input["after"](inst.trigger);
			
			inst.trigger.click(function() {
				if ($.datepicker._datepickerShowing && $.datepicker._lastInput === input[0]) {
					$.datepicker._hideDatepicker();
				} else if ($.datepicker._datepickerShowing && $.datepicker._lastInput !== input[0]) {
					$.datepicker._hideDatepicker();
					$.datepicker._showDatepicker(input[0]);
				} else {
					$.datepicker._showDatepicker(input[0]);
				}
				return false;
			});
		}
		
 * 
 * 
 * //jquery ui 的日期插件
$.fn.smartDatepicker=function(opt){
	var defaultOptions={
		dateFormat:'yy-mm-dd',
		changeMonth: true ,
		//changeYear: true ,
		showOn:'smartIcon',//jquery datepicker 触发的icon（修改源码）
		showButtonPanel: true,
		currentText: "今天"
	}
	opt=opt||{};
	$.extend(defaultOptions,opt);
	this.datepicker(defaultOptions);

}*/

//| 日期时间有效性检查 
//| 格式为：YYYY-MM-DD HH:MM:SS 
function CheckDateTime(str){ 
    //var reg=/^(\d+)-(\d{1,2})-(\d{1,2})(\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
	if(!str||typeof str !== 'string')
		return false;
    var reg=/^(\d+)-(\d{1,2})-(\d{1,2})$/; 
    var r=str.match(reg); 
    if(r==null) return false; 
    r[2]=r[2]-1; 
    var d= new Date(r[1],r[2],r[3]); 
    if(d.getFullYear()!=r[1]) return false; 
    if(d.getMonth()!=r[2]) return false; 
    if(d.getDate()!=r[3]) return false; 
   // if(d.getHours()!=r[4]) return false; 
    //if(d.getMinutes()!=r[5]) return false; 
    //if(d.getSeconds()!=r[6]) return false; 
    return true; 
}

/******************************************************datepicker END*********************************************/


/******************************************************timeline*********************************************/
/**
 * 数据格式：
 * 		var tl_data = [{
			title:'报警',
			datetime:'2013-11-05 14:14:14',
			user:'three.zhang',
			content:'内容·····',
			remark:'说明',
			url:'http://www.baidu.com'
		},{
			title:'报警',
			datetime:'2013-11-05 14:14:14',
			user:'three.zhang',
			content:'内容·····',
			remark:'说明',
			url:''
		}
		
		];
 */
;(function($){
	$.fn.extend({
		adpTimeline : function(data,options){
			
			if(!this.length||!data||!data.length){
				
				return null;
			}
			this._options ={
					
			};
			
			$.extend(this._options,options);
			var _container = $('<div class="timeline-container">'+
								'<div class="timeline" >'+
									'<div class="timeline-node-start"></div>'+
									'<div class="timeline-node-end"></div>'+
								'</div>'+
							+'</div>');
			var _height = 500;//高度
			var len = data.length;
			var eventNodes = '';
			for(var i = 0; i<len ;i++ ){
				var top = 10+(_height-20)*(i/len)-i*len;
				
				eventNodes +='<div data-index ="'+i+'" style="top:'+top+'px" class="timeline-node-event" ></div>';
			}
			
			$(eventNodes).appendTo(_container.find('div.timeline'));
			
			_container.appendTo($(this[0]));

			
			_container.find('.timeline-node-event').each(function(){
				
				var _this = $(this);
				var index = _this.attr('data-index');
				var detail = data[index];
				//要显示的内容
				
				var content='<label>时间：</label>'+detail.datetime+'</br>'
						    +'<label>相关人：</label>'+detail.user+'</br>';
				var sub_content="";
				if(detail.content){
					var toggle_html = '<a data-index ="'+index+'"  class="btn timeline-content-show" href="javascript:void(0);"  >详情</a>';
					content += '<label>内容：</label>'+toggle_html+'</br>';
					sub_content = detail.content;
				}
				if(detail.remark){
					content +='<label>说明：</label>'+detail.remark+'</br>';
				}
				if(detail.url){
					content +='<label>当前状态：</label>'
					var urlArr = detail.url.split(',');
					for(var i in urlArr){
						url = urlArr[i];
						content +='<a class="btn" onclick="adpNewWin(\''+url+'\');" href="javascript:void(0);"  >查看</a>';
					}
					
				}
					
				var $popover = $(this).popover({
					animation:true,
					html:true,
					placement:index%2==0?'left':'right',
					title:detail.title,
					content:content
					
				})
				if(sub_content){
					$popover.on('shown.bs.popover', function () {
						
							$(this).next().find('.timeline-content-show').popover({
								animation:true,
								html:true,
								placement:index%2==0?'left':'right',
								title:"详情",
								content:sub_content
								
							});
						
							
					});;
				}
				
				window.setTimeout(function(){
					_this.popover("show");

					
				},600*index);
				
			})
		}
	});
})(jQuery);



/******************************************************timeline END*********************************************/



/******************************************************jquery uploadify and file upload*********************************************/
/**
 * 
 * requestUrl-----处理文件上传的controllerA
 * rData----------controllerA额外需要的数据
 * initFile-------加载已经上传的文件
 * 
 *文件大小默认20M,最多十个文件 
 * 参考文档：
 * https://github.com/blueimp/jQuery-File-Upload/wiki/API
 * http://www.uploadify.com/documentation/
 */
;+function($){
	
	$.fn.extend({
		adpFileUpload : function(contextPath,requestUrl,rData,initFile){
			if(!contextPath){
				alert("adpFileUpload :contextPath must have");
				return ;
			}
			if(!$(this)[0]){
				alert("adpFileUpload :container must have");
				return ;
			}
			var $container = $($(this)[0]);
			fileUplaod($container,contextPath,requestUrl,rData);
			
			
			
			
			//jquery file upload 插件
			function fileUplaod($container,contextPath,requestUrl,rData){
				$container.load(contextPath+'/redirecttofileUpload',function(){
					$(function () {
						var opts = {
						    /* 	url:"${ctx}/postFile",
					    	type:'post', */
					        dataType: 'json'
					        //autoUpload:true,
					        // filesContainer: $('table tbody.files'),
					        /** done: function (e, data) {
					             $.each(data.result.files, function (index, file) {
					                $('<p/>').text(file.name).appendTo(document.body);
					            }); 
					            
					           
					            console.log(e);
					            console.log(data);
					        },
					        fail:function(e,data){
					        	
					        	console.log(e);
					        	console.log(data);
					        }
					        ,formData:function (form) {
					            return form.serializeArray();
					        } */
					        /********Validation options**********/
					        //,acceptFileTypes:/(\.|\/)(gif|jpe?g|png)$/i
					        ,maxFileSize :20*1024*1024 // 20 MB
					        ,minFileSize:1
					        ,maxNumberOfFiles:10

					        //The maxNumberOfFiles option depends on the getNumberOfFiles option, 
					        //which is defined by the UI and AngularJS implementations.

					    };
						if(rData&& typeof rData ==='object'){
							opts.formData = rData;
						}
						$container.find('form.fileupload')
						.attr('action',requestUrl)
						.fileupload(opts);
					});
					
					if(initFile&&window.tmpl){
						var templFunc = window.tmpl("template-download");
						if(templFunc){
							if(typeof initFile ==='string'){
								$.ajax({
									url:initFile,
									type:'get',
									dataType:'json',
									cache:false,
									success:function(files){
										initUploadedFiles(templFunc,files)
									},
									error:function(){
										
									}
								});
							}else if($.isArray(initFile)){
								initUploadedFiles(templFunc,initFile);
							}
						}
						
					}
					
			        
			        
				});
				
			}//--function fileUplaod
			
			//判断使用哪个插件
			function isIE(){
				
			}
			// uploadify 插件
			function uploadify($container,contextPath){
				var _width = $container.width();
				var body_w = $('body').width();
				//判断width是否符合要求，不符合延迟100毫秒。最多延迟两次
				
				if(body_w-_width>30){
					$('<iframe id="iframe-upload"    frameborder="0"  ></iframe>').appendTo($container);
					$("#iframe-upload").width(_width).attr("src",contextPath+'/dialogfileUpload');
				}else{
					window.setTimeout(function(){
						_width = $container.width();
						if(body_w-_width>30){
							$('<iframe id="iframe-upload"    frameborder="0"  ></iframe>').appendTo($container);
							$("#iframe-upload").width(_width).attr("src",contextPath+'/dialogfileUpload2');
						}else{
							window.setTimeout(function(){
								_width = $container.width();
								$('<iframe id="iframe-upload"    frameborder="0"  ></iframe>').appendTo($container);
								$("#iframe-upload").width(_width).prop("src",contextPath+'/dialogfileUpload2');
								
							},100);
						}
					},100);
				}
			}//--function uploadify
			
			//初始化已经上传的files
			function initUploadedFiles(templFunc,files){
				var result = templFunc({
		            'files': files,
		            formatFileSize: function (bytes) {
		                if (typeof bytes !== 'number') {
		                    return '';
		                }
		                if (bytes >= 1000000000) {
		                    return (bytes / 1000000000).toFixed(2) + ' GB';
		                }
		                if (bytes >= 1000000) {
		                    return (bytes / 1000000).toFixed(2) + ' MB';
		                }
		                return (bytes / 1000).toFixed(2) + ' KB';
		            }
		        });
				
				if(result){
					$(result).addClass('in').appendTo("tbody.files");
				}
			}
		}
	});
}(jQuery);



/******************************************************jquery uploadify and file upload  END*********************************************/









/*******************************************************工具 START**********************************************/

$.fn.smartGetAllAircraft=function(ctx, callbackFunction,opts){
	
	var $input = this;
	var $input_w = $input.outerWidth();
	$input_w = $input_w <40 ? 150 :$input_w ; //防止初始化是"hidden",不能计算width
	var _width  = $input_w;
	$.ajax({
			url:ctx+"/asset/aircrafttail/findAllTailByRolesOrderName",
			type: "post",
			dataType: 'json',				  			
			success:function(json){
				//销毁旧autocomplate
				$input.unautocomplete();
				//首先清空cache
				$input.flushCache();
				//加载cache
				$input.autocomplete(json.tailList	, {
		            max:1000,     //列表里的条目数
		            minChars: 0,    //自动完成激活之前填入的最小字符
		            width: _width,     //提示的宽度，溢出隐藏
		            scrollHeight: 300,   //提示的高度，溢出显示滚动条
		            matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
		            autoFill: false,    //自动填充
			        formatItem: function(row, i, max) {//显示div里面的格式  
			            return row.aircraftNo;
			        },
			        //输入框中输入文字后，下拉div是否显示：是将输入的内容与formatMatch返回的结果进行比较
			        formatMatch: function(row, i, max) { 
			    		return row.aircraftNo;
			  		},
			    	formatResult: function(row) {//最后在在输入框显示的值 
			          return row.aircraftNo;
			       }
			  }).result(function(event, row, formatted) {
			  		callbackFunction&&callbackFunction(event, row, formatted);
			  });		
		  },
		  error:function(){
			 // alert("网络异常，smartGetAllAircraft ERROR!");
		  }
	});
};
//根据执管 单位
$.fn.getAircraftByOfficeId = function(ctx, officeId, callbackFunction,opts){
	var $input = this;
	var $input_w = $input.outerWidth();
	$input_w = $input_w <40 ? 150 :$input_w ; //防止初始化是"hidden",不能计算width	
	var _width  = $input_w;
	
	$.ajax({
			url:ctx+"/asset/aircrafttail/findTailByOfficeId",
			type: "post",
			dataType: 'json',
			data:{officeId : officeId },
			success:function(json){
				//销毁旧autocomplate
				$input.unautocomplete();
				//首先清空cache
				$input.flushCache();
				//加载cache
				$input.autocomplete(json.tailList	, {
		            max:1000,     //列表里的条目数
		            minChars: 0,    //自动完成激活之前填入的最小字符
		            width: _width,     //提示的宽度，溢出隐藏
		            scrollHeight: 300,   //提示的高度，溢出显示滚动条
		            matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
		            autoFill: false,    //自动填充
			        formatItem: function(row, i, max) {//显示div里面的格式  
			            return row.aircraftNo;
			        },
			        //输入框中输入文字后，下拉div是否显示：是将输入的内容与formatMatch返回的结果进行比较
			        formatMatch: function(row, i, max) { 
			    		return row.aircraftNo;
			  		},
			    	formatResult: function(row) {//最后在在输入框显示的值 
			          return row.aircraftNo;
			       }
			  }).result(function(event, row, formatted) {
			  		callbackFunction&&callbackFunction(event, row, formatted);
			  });		
		  },
		  error:function(){
			 // alert("网络异常，smartGetAllAircraft ERROR!");
		  }
	});
};

//根据执管单位和机型
$.fn.getAircraftByOfficeIdAndTypeId = function(ctx, officeId, aircraftTypeId, callbackFunction,opts){
	var $input = this;
	var $input_w = $input.outerWidth();
	$input_w = $input_w <40 ? 150 :$input_w ; //防止初始化是"hidden",不能计算width	
	var _width  = $input_w;
	
	$.ajax({
			url:ctx+"/asset/aircrafttail/findTailByOfficeAndTypeId",
			type: "post",
			dataType: 'json',
			data:{officeId : officeId, aircraftTypeId: aircraftTypeId },
			success:function(json){
				//销毁旧autocomplate
				$input.unautocomplete();
				//首先清空cache
				$input.flushCache();
				//加载cache
				$input.autocomplete(json.tailList	, {
		            max:1000,     //列表里的条目数
		            minChars: 0,    //自动完成激活之前填入的最小字符
		            width: _width,     //提示的宽度，溢出隐藏
		            scrollHeight: 300,   //提示的高度，溢出显示滚动条
		            matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
		            autoFill: false,    //自动填充
			        formatItem: function(row, i, max) {//显示div里面的格式  
			            return row.aircraftNo;
			        },
			        //输入框中输入文字后，下拉div是否显示：是将输入的内容与formatMatch返回的结果进行比较
			        formatMatch: function(row, i, max) { 
			    		return row.aircraftNo;
			  		},
			    	formatResult: function(row) {//最后在在输入框显示的值 
			          return row.aircraftNo;
			       }
			  }).result(function(event, row, formatted) {
			  		callbackFunction&&callbackFunction(event, row, formatted);
			  });		
		  },
		  error:function(){
			 // alert("网络异常，smartGetAllAircraft ERROR!");
		  }
	});
};

function adplog(logs){
	if(console&&console.log){
		console.log(logs);
		/*for(var i in logs){
			console.log(i+":"+logs[i])
		}*/
	}else{
		alert(logs)
	}
}

//根据机队查找机尾
$.fn.getAircraftByFleetId = function(ctx, fleetId, callbackFunction,opts){
	var $input = this;
	var $input_w = $input.outerWidth();
	$input_w = $input_w <40 ? 150 :$input_w ; //防止初始化是"hidden",不能计算width	
	var _width  = $input_w;
	
	$.ajax({
			url:ctx+"/asset/aircrafttail/findTailByFleetId",
			type: "post",
			dataType: 'json',
			data:{fleetId : fleetId},
			success:function(json){
				//销毁旧autocomplate
				$input.unautocomplete();
				//首先清空cache
				$input.flushCache();
				//加载cache
				$input.autocomplete(json.tailList	, {
		            max:1000,     //列表里的条目数
		            minChars: 0,    //自动完成激活之前填入的最小字符
		            width: _width,     //提示的宽度，溢出隐藏
		            scrollHeight: 300,   //提示的高度，溢出显示滚动条
		            matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
		            autoFill: false,    //自动填充
			        formatItem: function(row, i, max) {//显示div里面的格式  
			            return row.aircraftNo;
			        },
			        //输入框中输入文字后，下拉div是否显示：是将输入的内容与formatMatch返回的结果进行比较
			        formatMatch: function(row, i, max) { 
			    		return row.aircraftNo;
			  		},
			    	formatResult: function(row) {//最后在在输入框显示的值 
			          return row.aircraftNo;
			       }
			  }).result(function(event, row, formatted) {
			  		callbackFunction&&callbackFunction(event, row, formatted);
			  });		
		  },
		  error:function(){
			 // alert("网络异常，smartGetAllAircraft ERROR!");
		  }
	});
};




//根据机型查找机尾
$.fn.getAircraftByTypeId = function(ctx, typeId, callbackFunction,opts){
	var $input = this;
	var $input_w = $input.outerWidth();
	$input_w = $input_w <40 ? 150 :$input_w ; //防止初始化是"hidden",不能计算width	
	var _width  = $input_w;
	
	$.ajax({
			url:ctx+"/asset/aircrafttail/findTailByTypeId",
			type: "post",
			dataType: 'json',
			data:{typeId : typeId},
			success:function(json){
				//销毁旧autocomplate
				$input.unautocomplete();
				//首先清空cache
				$input.flushCache();
				//加载cache
				$input.autocomplete(json.tailList	, {
		            max:1000,     //列表里的条目数
		            minChars: 0,    //自动完成激活之前填入的最小字符
		            width: _width,     //提示的宽度，溢出隐藏
		            scrollHeight: 300,   //提示的高度，溢出显示滚动条
		            matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
		            autoFill: false,    //自动填充
			        formatItem: function(row, i, max) {//显示div里面的格式  
			            return row.aircraftNo;
			        },
			        //输入框中输入文字后，下拉div是否显示：是将输入的内容与formatMatch返回的结果进行比较
			        formatMatch: function(row, i, max) { 
			    		return row.aircraftNo;
			  		},
			    	formatResult: function(row) {//最后在在输入框显示的值 
			          return row.aircraftNo;
			       }
			  }).result(function(event, row, formatted) {
			  		callbackFunction&&callbackFunction(event, row, formatted);
			  });		
		  },
		  error:function(){
			 // alert("网络异常，smartGetAllAircraft ERROR!");
		  }
	});
};



//通用的 autocomplete
$.fn.commonAutoComplete = function(purl , callbackFunction, opts, param){
	var $input = this;
	var $input_w = $input.outerWidth();
	$input_w = $input_w <40 ? 150 :$input_w ; //防止初始化是"hidden",不能计算width	
	var _width  = $input_w;
	var text = opts.text;
	var sub_param  = param ? param :{};
	$.ajax({
			url: purl +"?now="+ new Date().getTime() ,
			type: "get",
			dataType: 'json',
			data: sub_param ,
			success:function(json){
				//销毁旧autocomplate
				$input.unautocomplete();
				//首先清空cache
				$input.flushCache();
				//加载cache
				$input.autocomplete(json , {
		            max:1000,     //列表里的条目数
		            minChars: 0,    //自动完成激活之前填入的最小字符
		            width: _width,     //提示的宽度，溢出隐藏
		            scrollHeight: 300,   //提示的高度，溢出显示滚动条
		            matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
		            autoFill: false,    //自动填充
			        formatItem: function(row, i, max) {//显示div里面的格式  
			            return row[text];
			        },
			        formatMatch: function(row, i, max) { 
			    		return row[text];
			  		},
			    	formatResult: function(row) {
			          return row[text];
			       }
			  }).result(function(event, row, formatted) {
			  		callbackFunction&&callbackFunction(event, row, formatted);
			  });		
		  },
		  error:function(){	  }
	});
};


/**
 * 弹出一个非阻塞式确认框（目前是bootstrap实现框体）。
 * 
 * @param optsOrFunc 如果是函数，就是点击确定的回调函数。
 * 返回 modal--DOM
 */
function adpConfirm (optsOrFunc){
	var defaultOpts = {
		title:'请选择',
		content:'是否继续？',
		button_func:{
			'取消':null,
	        '确定':null
	    },
	    button_class:{
	    	'取消':'btn-default',
	        '确定':'btn-primary'
	    }
		
	}
	if(typeof optsOrFunc === 'function'){
		defaultOpts.button_func['确定'] = optsOrFunc;
	}
	if(typeof optsOrFunc === 'object'){
		$.extend(true,defaultOpts,optsOrFunc);
	}
	var modal_id = 'adpConfirmModal'; 
	var button_html='';
	for(var i in defaultOpts.button_func){
		var buttonClass = defaultOpts.button_class[i]||'btn-primary';
		button_html+='<a class="btn btn-sm '+buttonClass
					+'" href="javascript:void(0)" onclick="$(\'#'+modal_id+'\').adpConfirmButtonFunc(\''+i+'\');"; data-dismiss="modal" aria-hidden="true">'+i+'</a>'
	}
	var $modalDialog = $('#'+modal_id);
	if($modalDialog.length){
		$modalDialog.data('button_func',defaultOpts.button_func);
		$modalDialog.find('div.modal-header .modal-title').html(defaultOpts.title);
		$modalDialog.find('div.modal-body p').html(defaultOpts.content);
		$modalDialog.find('div.modal-footer').html(button_html);
	}else{
		var _html = 
			'<div id="'+modal_id+'" class="modal fade">'+
				'<div class="modal-dialog">'+
					'<div class="modal-content">'+
						'<div class="modal-header">'+
							'<button type="button" class="close" data-dismiss="modal"'+
								'aria-hidden="true">&times;</button>'+
							'<h4 class="modal-title">'+defaultOpts.title+'</h4>'+
						'</div>'+
						'<div class="modal-body"><p>'+defaultOpts.content+
						'</p></div>'+
						'<div class="modal-footer">'+button_html+
						'</div>'+
					'</div>'+
				'</div>'+
			'</div>';
		$modalDialog = $(_html).data('button_func',defaultOpts.button_func).appendTo('body');
	}
	
	$modalDialog.modal({
		backdrop : 'static',
		keyboard : true
	});
	return $modalDialog;
}

/**
 * adpConfirm的辅助函数
 */
+function($){
	$.fn.extend({
		adpConfirmButtonFunc:function(buttonName){
			var button_func =  $(this).data("button_func");
			var func = button_func[buttonName];
			if(typeof func ==='function'){
				func();
			}
		}
	});
}(jQuery);




/**
 * 发布任务
 * @param taskName
 */
function publishTask (taskName){
	
	setCookie(taskName,new Date().getTime(),2000);
	
	
	function setCookie(c_name, value, expireMms){
　　　　var exdate=new Date();
　　　　exdate.setTime(exdate.getTime() + expireMms);
	   var ck = c_name+ "=" + escape(value) + ((expireMms==null) ? "" : ";expires="+exdate.toGMTString());
	   ck+=";path=/"
　　　　document.cookie = ck;
	}
}
/**
 * 接受发布的相应的任务
 * @param taskName
 * @param func
 */
function acceptTask (taskName,func){

	var taskNameVal;
	window.setInterval(function(){
		var _taskNameVal = getCookie(taskName);
		if(_taskNameVal){
			if(taskNameVal){
				if(_taskNameVal!=taskNameVal){
					$.isFunction(func)&&func();
				}
			}else{
				$.isFunction(func)&&func();
			}
		}
		
		
		taskNameVal = _taskNameVal;
	},1000);
	
	
	
	function getCookie(c_name){
　　　　if (document.cookie.length>0){　　//先查询cookie是否为空，为空就return ""
　　　　　　c_start=document.cookie.indexOf(c_name + "=");　　//通过String对象的indexOf()来检查这个cookie是否存在，不存在就为 -1　　
　　　　　　if (c_start!=-1){ 
　　　　　　　　c_start=c_start + c_name.length+1;　　//最后这个+1其实就是表示"="号啦，这样就获取到了cookie值的开始位置
　　　　　　　　c_end=document.cookie.indexOf(";",c_start);　　//
　　　　　　　　if (c_end==-1) 
			 	c_end=document.cookie.length;　　
　　　　　　　　return decodeURIComponent(document.cookie.substring(c_start,c_end));　　//通过substring()得到了值
　　　　　　} 
　　　　}
　　　　return "";
	}

}

/**
 * 提供一个遮盖层，遮盖dom，并带有等待效果
 */
function showModalWaiting (opts){
	var _info="数据处理...";
	if('string' === typeof opts){
		_info = opts;
	}
	//var _this = $(this[0]);
	var _this = $("body");
	
	if(_this.is(".modal-waiting-container")){
		
	}else{
		_this.addClass('modal-waiting-container');
		_this.append('<div class="modal-waiting-info">'+_info+'</div><div class="modal-waiting"></div>');
	}
	return _this.find(".modal-waiting,.modal-waiting-info").removeClass("hidden");
}
function hideModalWaiting (opts){
	//var _this = $(this[0]);
	var _this = $("body");
	return _this.find(".modal-waiting,.modal-waiting-info").addClass("hidden");
}


//向上滑动关闭窗口
/*$(function(){
	var downEvent = null;
	document.onmousedown = function(e){
		
		if(e&&e.button === 2){//右键
			
			downEvent = e;
		
		}
		
	}
	document.onmouseup = function(e){
		if(e&&e.button === 2){//右键
			
			//没有右键按下
			if(!downEvent)
				return ;
			
			var downX = downEvent.clientX;
			var downY = downEvent.clientY;
			var upX = e.clientX;
			var upY = e.clientY;
			
			
			//向上滑动高度
			var slideH = downY-upY;
			//向上滑动宽度
			var slideW = Math.abs(upX-downX);
			//向上滑动高度150
			if(slideH<150)
				return ;
			//向上滑动角度大于45°
			if(slideW>slideH)
				return ;
			e.preventDefault();
			//关闭窗口
			window.close();
			
			
			downEvent = null;
			return false;
		}
		
	}
});*/

var SmartInfoBox = null ;
$(function(){
	SmartInfoBox = {
			show:_show,
			hide:_hide,
			open:_open,
			close:_close,
			getDom:_getDom,
			closeCallback:null,
			openCallback:null
	};
	
	var _box_html = 
		'<div class="smart-info-box" >'+
			'<div class="sinfobox-body " style="">'+
				'<div class="panel panel-danger " style="width:558px;margin:0;height:358px;">'+
					'<div class="panel-heading">'+
						'<h3 class="panel-title"></h3>'+
					'</div>'+
					'<div class="panel-body" style="overflow:auto;height:317px;padding:0;" >'+
					'</div>'+
				'</div>'+
			'</div>'+
			'<div class="sinfobox-switch  btn" title="打开"> '+
				'<span  class="glyphicon glyphicon-chevron-right" ></span>'+
				'<strong class="sinfobox-switch-word">开</strong>'+
			'</div>'+
		'</div>';
	var $box = $(_box_html).appendTo('body');
	//事件
	$(".sinfobox-switch",$box).on('click',switchClickFunc);
	
	
	function _show(opts){
		var _opts = {
			title:'标题',
			content:'内容'
		};
		_opts = $.extend(_opts,opts);
		if(_opts.title!=null||_opts.title!=undefined)
		$box.find('.panel-heading h3').html(_opts.title);
		if(_opts.content!=null||_opts.content!=undefined)
		$box.find('.panel-body').html(_opts.content);
		return $box.show();
	}
	function _hide(){
		return $box.hide();
	}
	function _open(){
		var _this = $box.find('.sinfobox-switch');
		_this.off('click',switchClickFunc);
		_this.attr('title','关闭');
		_this.find('.sinfobox-switch-word').text('关');
		_this.toggleClass("sinfobox-switch-opened").find(".glyphicon").toggleClass("glyphicon-chevron-right glyphicon-chevron-left");
		if('function' === typeof SmartInfoBox.openCallback){
			SmartInfoBox.openCallback();
		}
		$('div.sinfobox-body').animate({
			 width: '560px'
			 }, 1200, function() {
				 _this.on('click',switchClickFunc);
		});
	}
	function _close(){
		var _this = $box.find('.sinfobox-switch');
		_this.off('click',switchClickFunc);
		_this.attr('title','打开');
		_this.find('.sinfobox-switch-word').text('开');
		_this.toggleClass("sinfobox-switch-opened").find(".glyphicon").toggleClass("glyphicon-chevron-right glyphicon-chevron-left");
		if('function' === typeof SmartInfoBox.closeCallback){
			SmartInfoBox.closeCallback();
		}
		 $('div.sinfobox-body').animate({
			 width: '0px'
			 }, 1200, function() {
				 _this.on('click',switchClickFunc);
		});
	}
	function _getDom(){
		return $box;
	}
	function switchClickFunc(){
		var _this = $(this);
		var isOpened = _this.is(".sinfobox-switch-opened");
		if(isOpened){
			_close();
		}else{
			_open();
		}
	}
	
	
	
});

/*******************************************************工具 END**********************************************/