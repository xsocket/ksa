<!DOCTYPE html>
<html lang="zh">
<head>
<title>利润统计图</title>
</head>
<body>
<div id="chart_container" class="chart-container">
</div>
<script type="text/javascript">
	$.ajax({
        url: "<@s.url namespace="/data/chart/profit" action="${chart!'Column2D'}" />",
        data: "${queryString!}",
        success: function( xml ) {
            try {
                var chart = new FusionCharts( APPLICATION_CONTEXTPATH + "/rs/charts/${chart!"MSColumn2D"}.swf?ChartNoDataText=暂时没有任何数据可以显示", "ChartId", $(window).width() - 10, $(window).height() - 12, "0", "0");
                chart.addParam( "wmode", "Opaque" );
			    chart.setDataXML( xml );		   
			    chart.render("chart_container");
            } catch (e) { console.log(e); }
        }
    }); 
</script>
</body>
</html>