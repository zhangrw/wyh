<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<style type="text/css">
#d-top {
	right: 10px; bottom: 10px; float: right; position: fixed; z-index: 10;
}
#d-top img {
	width: 42px; filter: alpha(opacity=30); opacity: 0.3; -moz-opacity: 0.3; -khtml-opacity: 0.3;
}
#d-top a:hover img {
	filter: alpha(opacity=100); opacity: 1; -moz-opacity: 1; -khtml-opacity: 1;
}
</style>
<div id="d-top" hidden>
<a id="d-top-a" href="#" title="回到顶部">
<img src="${ctx}/static/banshion/images/icon/top.png" alt="TOP" /></a>
</div>
<script type="text/javascript">
    $(function(){
        var d_top=$('#d-top');
        window.onscroll=function(){
            var scrTop=(document.body.scrollTop||document.documentElement.scrollTop);
            if(scrTop>200){
                d_top.show();
            }else{
                d_top.hide();
            }
        }
        $('#d-top-a').click(function(){
            scrollTo(100,0);
            this.blur();
            return false;
        });
    });
</script> 

    