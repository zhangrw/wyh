<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" /> 

<script type="text/javascript" src="${ctx}/static/highcharts-stock/4.0.1-2.0.1/js/highstock.js"></script>
<script type="text/javascript">	
	//定义全局的变量
	var webPath = "${ctx}";
</script> 
<script type="text/javascript" src="${ctx}/static/highcharts-stock/4.0.1-2.0.1/js/highcharts-3d.js"></script>
<script type="text/javascript" src="${ctx}/static/highcharts-stock/4.0.1-2.0.1/js/highcharts-more.js"></script>
<script type="text/javascript" src="${ctx}/static/highcharts-stock/4.0.1-2.0.1/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="${ctx}/static/highcharts-stock/4.0.1-2.0.1/js/modules/drilldown.js"></script>
<script type="text/javascript" src="${ctx}/static/highcharts-stock/chart-plugins.js"></script>   