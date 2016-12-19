function checkDateTime(dateFromInput,dateToInput, successBack, errorBack ){
	var flag = true;
    
    if($(dateFromInput).val() && $(dateToInput).val()){
        var fromDate =  DateFormat.parse($(dateFromInput).val(), 'yyyy-MM-dd'); 
        var endDate = DateFormat.parse($(dateToInput).val(), 'yyyy-MM-dd');
        
       /* if (fromDate.valueOf() > new Date().valueOf()){
        	bs_error('起始时间不能晚于当前时间！');
            flag=false;
        }
        if (endDate.valueOf() > new Date().valueOf()){
        	bs_error('结束时间不能晚于当前时间！');
            flag=false;
        }
        if(fromDate.valueOf() > endDate.valueOf()){
        	bs_error('起始时间不能大于结束时间！');
            flag=false;
        } */  
       
    }else if($(dateFromInput).val() && !$(dateToInput).val()){
        /*var fromDate =  DateFormat.parse($(dateFromInput).val(), 'yyyy-MM-dd'); 
         if (fromDate.valueOf() > new Date().valueOf()){
        	 bs_error('起始时间不能晚于当前时间！');
                flag=false;
            }*/
    }else if(!$(dateFromInput).val() && $(dateToInput).val()){
       /*   var endDate = DateFormat.parse($(dateToInput).val(), 'yyyy-MM-dd');
          if (endDate.valueOf() > new Date().valueOf()){
        	  bs_error('结束时间不能晚于当前时间！');
                flag=false;
            }*/
    }
    if(!flag){
       if(errorBack) errorBack.call();
    }else {
       if(successBack) successBack.call();
    }  
    
	return flag;
}


function initDatePicker(
			wrapF, dateFromInput, 
			wrapT, dateToInput, 
			successBack, errorBack,
			datefrom , dateto){		
		if(datefrom && ''!=datefrom){
			$(wrapF).attr('data-date',datefrom);
			$(dateFromInput).val(datefrom);
		}else{
			var fday=new Date();
			fday.setMonth(fday.getMonth()-1);
			var fdate = DateFormat.format(fday, 'yyyy-MM-dd');
			$(wrapF).attr('data-date', fdate);
			$(dateFromInput).val(fdate);
		}

		$(wrapF).datepicker({
			format : 'yyyy-mm-dd',
			weekStart : 1
		}).on('changeDate', function(ev){	
			checkDateTime(dateFromInput,dateToInput,successBack, errorBack );		
		});
		
		if(dateto && ''!=dateto){
			$(wrapT).attr('data-date',dateto);
			$(dateToInput).val(dateto);
		}else{
			var today = DateFormat.format(new Date(), 'yyyy-MM-dd');
			$(wrapT).attr('data-date',today);
			$(dateToInput).val(today);
		}

		$(wrapT).datepicker({
			format : 'yyyy-mm-dd',
			weekStart : 1
		}).on('changeDate', function(ev){
            checkDateTime(dateFromInput,dateToInput,successBack, errorBack );
		});
}
