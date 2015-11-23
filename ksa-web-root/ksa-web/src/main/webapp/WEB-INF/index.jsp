<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/shiro-tags" prefix="shiro" %><%-- 自定义扩展后的 Shiro 标签 --%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>首页</title>
<script type="text/javascript">
	$(function(){
	    var portalHeight = 290;
	    
	    $("#index_portal").parent().height( $(window).height() - 50 );
	    var $portal = $("#index_portal").portal();
//<shiro:hasPermission name="bookingnote:edit:view">	    
	    /* 自定义 Portal1 : 我填写的托单 */
	    $portal.portal('add', {  
	        columnIndex: 0,
	        panel: $('<div></div>').appendTo('body').panel({  
		        title: '我负责的业务',  height:portalHeight,
		        href:'portal/logistics/default.action'
		    })  
	    });
//</shiro:hasPermission>
//<shiro:hasPermission name="bookingnote:edit:view">
	    /* 自定义 Portal2 : 退单提醒 */
	    $portal.portal('add', {  
	        columnIndex: 1,
	        panel: $('<div></div>').appendTo('body').panel({  
		        title: '退单提醒',  height:portalHeight,
		        href:'portal/logistics/return-notify.action'
		    })  
	    });
//</shiro:hasPermission>	
//<shiro:hasPermission name="statistics:profit">
	    /* 自定义 Portal4 : 当月业务利润表 */
	    var now = new Date();
	    var year = now.getFullYear();
	    var month = now.getMonth() + 1;
	    var firstDay =  year + "-" + month + "-1";
	    var lastDay = year + "-" + month + "-" + getLastDay( year, month );
	    var src = 'dialog/chart/profit/default.action?CREATED_DATE='+firstDay+'&CREATED_DATE='+lastDay+'&title=' + year + '年' + month + '月业务利润图&chart=MSColumn2D&group=type';
	    $portal.portal('add', {  
	        columnIndex: 0,
	        panel: $('<div></div>').appendTo('body').panel({  
		        title: year + '年' + month + '月业务利润图',  height:portalHeight,
		        href:encodeURI( src )
		    })  
	    });
//</shiro:hasPermission>
//<shiro:hasPermission name="finance:account1">
	    /* 自定义 Portal3 : 账单管理 */
	    $portal.portal('add', {  
	        columnIndex: 1,
	        panel: $('<div></div>').appendTo('body').panel({  
		        title: '近期账单',  height:portalHeight,
		        href:'portal/finance/account/default.action'
		    })  
	    });
//</shiro:hasPermission>  

		function getLastDay(year,month)  {  
		 var new_year = year;  
		 var new_month = month++;
		 if(month>12) {  
			  new_month -=12; 
			  new_year++;  
		 }  
		 var new_date = new Date(new_year,new_month,1); 
		 return (new Date(new_date.getTime()-1000*60*60*24)).getDate();
		}
	});
</script>
</head>
<body>
<div id="index_portal">
	<div id="portal1" style="width:50%"></div>  
    <div id="portal2" style="width:50%"></div>  
</div>
</body>
</html>
