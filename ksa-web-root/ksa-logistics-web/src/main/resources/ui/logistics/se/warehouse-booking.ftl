<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<title>订舱通知单</title>
<style type="text/css">
textarea, input { width:95%; }

.company-info div { line-height:1.2; }
.company-saler { border-bottom: 1pt solid black; }
.company-saler div { line-height:1.2; margin-bottom: 5pt; }
div.title { line-height:65pt; text-align: center; font-size:20pt; font-weight:bold; }

td {border: 1pt solid black;}
td.mix-text { vertical-align: top; text-align: center; width:61%; }
td.mix-text label { display:block; text-align:left; padding: 0 10pt 5pt; }
td.mix-text textarea { height: 72pt; }

td.text { width: 13%; text-align: center; }
td.input { width:26%; text-align: center; }
td.input textarea { height: 70pt; }
td.input.widget { text-align:left; padding-left: 5pt; }

tr.text-label td { text-align: center; }
tr.text-label td input { width:90%; }
tr.text-input { height: 75pt; }
tr.text-input td { text-align: center; }
tr.text-input td textarea { height: 70pt; }

tr.inner-table { height:100pt; }
td.inner-table { padding:0; margin:0; height:100pt; }
td.inner-table table { width: 100%; height:100%; border:none; }
td.inner-table table td { border : none; text-align:center;  border-right:1pt solid black; }
</style>
<style type="text/css" media="print">
td.input.widget { padding-left: 0pt !important; }
</style>
<script type="text/javascript">
	<#include "warehouse-booking.js" />
</script>
</head>
<body>
<@s.actionerror />
<@s.actionmessage />
<form method="POST" action="save-se.action">
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
<div class="title">订舱委托单</div>
<table>
<tbody>
	<tr>
		<td rowspan="4" colspan="2" class="mix-text">
			<label>SHIPPER：
				<button class="btn btn-mini btn-warning pull-right" onclick="ksa.bd.replacePartnerAlias('${model.bookingNote.shipper.id!}', '#shipper'); return false;"><i class="icon-edit icon-white"></i> 替换 ...</button>
			</label>
			<textarea id="shipper" rows="4" name="shipper">${model.shipper!}</textarea>
		</td>
		<td class="text"><label>委托日期</label></td>
		<td class="input widget" colspan="2"><input id="created_date" type="text" name="createdDate" value="${model.createdDate!}" /></td>
	</tr>
	<tr>
		<td class="text"><label>委托单号</label></td>
		<td class="input" colspan="2" ><input type="text" name="code" value="${model.code!}" /></td>
	</tr>
	<tr>
		<td class="text"><label>起运港</label></td>
		<td class="input widget" colspan="2"><input id="departure_port" type="text" name="departurePort" value="${model.departurePort!}" /></td>
	</tr>
	<tr>
		<td class="text"><label>目的港</label></td>
		<td class="input widget" colspan="2"><input id="destination_port" type="text" name="destinationPort" value="${model.destinationPort!}" /></td>
	</tr>
	<tr>
		<td rowspan="4" colspan="2" class="mix-text">
			<label>CONSIGNEE：
				<button class="btn btn-mini btn-warning pull-right" onclick="ksa.bd.replacePartnerAlias('${model.bookingNote.consignee.id!}', '#consignee'); return false;"><i class="icon-edit icon-white"></i> 替换 ...</button>
			</label>
			<textarea id="consignee" rows="4" name="consignee">${model.consignee!}</textarea>
		</td>
		<td class="text"><label>转船</label></td>
		<td class="input" colspan="2" ><select id="switchShip" name="switchShip" value="${model.switchShip!}">
				<option value="">　</option>
				<option value="NOT ALLOWED">NOT ALLOWED</option>
			</select>
		<#--<input id="switchShip" type="text" name="switchShip" value="${model.switchShip!}" />-->
		</td>
	</tr>
	<tr>
		<td class="text"><label>分批</label></td>
		<td class="input" colspan="2" ><select id="grouping" name="grouping" value="${model.grouping!}">
				<option value="">　</option>
				<option value="NOT ALLOWED">NOT ALLOWED</option>
			</select>
			<#--<input id="grouping" type="text" name="grouping" value="${model.grouping!}" />-->
		</td>
	</tr>
	<tr>
		<td class="text"><label>运输方式</label></td>
		<td class="input" colspan="2" ><input type="text" name="transportMode" value="${model.transportMode!}" /></td>
	</tr>
	<tr>
		<td class="text"><label>付款方式</label></td>
		<td class="input" colspan="2" >
			<select id="paymentMode" name="paymentMode" value="${model.paymentMode!}">
				<option value="">　</option>
				<option value="FREIGHT PREPAID">FREIGHT PREPAID</option>
				<option value="FREIGHT COLLECT">FREIGHT COLLECT</option>
			</select>
			<#--<input id="paymentMode" type="text" name="paymentMode" value="${model.paymentMode!}" />-->
		</td>
	</tr>
	<tr>
		<td rowspan="2" colspan="2" class="mix-text">
			<label>NOTIFY PARTY：
				<button class="btn btn-mini btn-warning pull-right" onclick="ksa.bd.replacePartnerAlias('${model.bookingNote.notify.id!}', '#notify'); return false;"><i class="icon-edit icon-white"></i> 替换 ...</button>
			</label>
			<textarea id="notify" rows="4" name="notify">${model.notify!}</textarea>
		</td>
		<td class="text"><label>运费</label></td>
		<td class="input" colspan="2" ><input type="text" name="freightCharge" value="${model.freightCharge!}" /></td>
	</tr>
	<tr style="height:75pt;">
		<td class="text"><label>箱量</label></td>
		<td class="input" colspan="2" ><textarea rows="3" name="cargoContainer">${model.cargoContainer!}</textarea></td>
	</tr>
	<tr class="text-label">
		<td>唛头</td>
		<td>货物名称</td>
		<td>件数</td>
		<td>毛重</td>
		<td>体积</td>
	</tr>
	<tr class="text-input">
		<td><textarea rows="4" name="shippingMark">${model.shippingMark!}</textarea></td>
		<td><textarea rows="4" name="cargoName">${model.cargoName!}</textarea></td>
		<td><textarea rows="4" name="cargoQuantity" style="width:90%;">${model.cargoQuantity!}</textarea></td>
		<td><textarea rows="4" name="cargoWeight" style="width:90%;">${model.cargoWeight!}</textarea></td>
		<td><textarea rows="4" name="cargoVolumn" style="width:90%;">${model.cargoVolumn!}</textarea></td>
	</tr>
	<tr class="text-label">
		<td colspan="2">TOTAL：</td>
		<td><input type="text" name="totalQuantity" value="${model.totalQuantity!}" /></td>
		<td><input type="text" name="totalWeight" value="${model.totalWeight!}" /></td>
		<td><input type="text" name="totalVolumn" value="${model.totalVolumn!}" /></td>
	</tr>
	<tr class="inner-table">
		<td colspan="5" class="inner-table"><table><tr>
			<td style="width:6%;"><label>注意<br/>事项</label></td>
			<td style="width:94%; border:none;"><textarea rows="6" style="width:98%;height:95pt;" name="note">${model.note!}</textarea></td>
		</tr></table></td>
	</tr>
</tbody>
</table>
<div class="bottom-bar fixed-bar">
	<button id="dialog_refresh" class="btn btn-warning pull-left"><i class="icon-refresh icon-white"></i> 同步数据</button>
	<button id="dialog_print" class="btn btn-success pull-left"><i class="icon-print icon-white"></i> 打印 ...</button>
	<button id="dialog_save" type="submit" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>
</form>
</body>
</html>