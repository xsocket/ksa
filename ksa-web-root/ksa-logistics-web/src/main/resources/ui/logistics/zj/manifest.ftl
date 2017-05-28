<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<title>Manifest</title>
<style type="text/css">
.company-info div { line-height:1.2; }
.company-saler div { line-height:1.2; margin-bottom: 5pt; }
div.title { line-height:65pt; text-align: center; font-size:20pt; font-weight:bold; }

table.no-border td { border: none; }
table.no-border td.text { width: 16%; text-align: right; padding-right: 5pt;  }
table.no-border td.input { width:16%; text-align: center; }
table.no-border td.input input { border-bottom: 1pt solid black; }

table.cargo { margin: 25pt 0; }
table.cargo td { text-align:center; }
table.cargo td textarea { width:90%; height:150pt; }

table.footer td.text { width: 18%; text-align: right; padding-right: 5pt; }
table.footer td.input { width: 32%; text-align: center; }
</style>
<script type="text/javascript">
	<#include "manifest.js" />
</script>
</head>
<body>
<@s.actionerror />
<@s.actionmessage />
<form method="POST" style="overflow:hidden;" action="save-zj.action">
<input type="hidden" name="id" value="${model.id!}" />
<input type="hidden" name="bookingNote.id" value="${model.bookingNote.id!}" />
<div class="company-logo"><img alt="公司图标" src="<@s.url value="/rs/images/report-banner.png" />" /></div>
<div class="company-info">
	<div>代理名称：凯思爱(杭州)物流有限公司</div>
	<div>地址：杭州市文二路391号西湖国际科技大厦B4-915</div>
	<div>TEL：<input style="width:100pt !important" type="text" name="salerTel" value='${model.salerTel!"88473507"}' data-options="validType:'length[0,200]'" />　
			 FAX：<input style="width:100pt !important" type="text" name="salerFax" value='${model.salerFax!"88473508"}' data-options="validType:'length[0,200]'" /></div>
	<div>EMAIL：<input style="width:200pt !important" type="text" name="salerEmail" value='${model.salerEmail!"hangzhou@ksa.co.jp"}' data-options="validType:'length[0,200]'" /></div>
</div>
<div class="company-saler">
	<div>客户服务代表：</div>
	<input id="saler" type="text" name="saler" value="${model.saler!}" data-options="validType:'length[0,200]'" />
</div>
<div class="title">AIR CARGO MANIFEST</div>
<table class="no-border">
<tbody>
	<tr>
		<td class="text"><label>CONSIGNED TO：</label></td>
		<td class="input"><input type="text" name="agent" value="${model.agent!}" /></td>
		<td class="text"><label>MAWB NO：</label></td>
		<td class="input"><input id="code" type="text" name="code" value="${model.code!}" /></td>
		<td class="text"><label>DEPARTURE：</label></td>
		<td class="input"><input id="loading_port" type="text" name="loadingPort" value="${model.loadingPort!}"/></td>
	</tr>
	<tr>
		<td class="text"><label>DESTINATION：</label></td>
		<td class="input"><input id="destination_port" type="text" name="destinationPort" value="${model.destinationPort!}"/></td>
		<td class="text"><label>FLIGHT NO：</label></td>
		<td class="input"><input type="text" name="flightDate" value="${model.flightDate!}" /></td>
		<td class="text"></td>
		<td class="input"></td>
	</tr>
</tbody>
</table>
<table class="cargo">
<tbody>
	<tr>
		<td class="head" style="width:13%;">HAWB NO.</td>
		<td class="head" style="width:9%;">PCS</td>
		<td class="head" style="width:9%;">KGS</td>
		<td class="head" style="width:20%;">COMMODITY</td>
		<td class="head" style="width:9%;">CC/PP</td>
		<td class="head" style="width:20%;">NAME OF SHIPPER
			<button class="btn btn-mini btn-warning pull-right" onclick="ksa.bd.replacePartnerAlias('${model.bookingNote.shipper.id!}', '#shipper'); return false;"><i class="icon-edit icon-white"></i> 替换 ...</button></td>
		<td class="head" style="width:20%;">NAME OF CONSIGNEE
			<button class="btn btn-mini btn-warning pull-right" onclick="ksa.bd.replacePartnerAlias('${model.bookingNote.consignee.id!}', '#consignee'); return false;"><i class="icon-edit icon-white"></i> 替换 ...</button></td>
	</tr>
	<tr>
		<td class="text"><textarea rows="9" name="hawb">${model.hawb!}</textarea></td>
		<td class="text"><textarea rows="9" name="totalPackages">${model.totalPackages!}</textarea></td>
		<td class="text"><textarea rows="9" name="cargoWeight">${model.cargoWeight!}</textarea></td>
		<td class="text"><textarea rows="9" name="cargoName">${model.cargoName!}</textarea></td>
		<td class="text"><textarea rows="9" name="re">${model.re!}</textarea></td>
		<td class="text"><textarea rows="9" name="shipper" id="shipper">${model.shipper!}</textarea></td>
		<td class="text"><textarea rows="9" name="consignee" id="consignee">${model.consignee!}</textarea></td>
	</tr>
</tbody>
</table>
<#-- v3.4.2采用新版本后废弃
<table class="no-border footer">
<tbody>
	<tr>
		<td class="text"><label>TOTAL NO. OF HAWB：</label></td>
		<td class="input"><input type="text" name="totalHawb" value="${model.totalHawb!}" /></td>
		<td class="text"><label>TOTAL OF PACKAGS：</label></td>
		<td class="input"><input type="text" name="totalPackages" value="${model.totalPackages!}" /></td>
	</tr>
</tbody>
</table>
 -->
<div class="bottom-bar fixed-bar">
	<button id="dialog_refresh" class="btn btn-warning pull-left"><i class="icon-refresh icon-white"></i> 同步数据</button>
	<button id="dialog_print" class="btn btn-success pull-left"><i class="icon-print icon-white"></i> 打印 ...</button>
	<button id="dialog_save" type="submit" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>
</form>
</body>
</html>