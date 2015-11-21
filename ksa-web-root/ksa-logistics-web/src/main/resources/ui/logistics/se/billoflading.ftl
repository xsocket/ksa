<!DOCTYPE html>
<html lang="zh">
<head>
<title>提单确认通知书</title>
<style type="text/css">
th, td { width:25%; }
textarea { height:75pt; }
tr.higher-text {height:140pt;}
tr.higher-text textarea {height:135pt; height:155pt\9;}

/* head */
#title { font-size: 20pt; letter-spacing: 5pt; }
div.header-label { text-align:left; }
div.header-label label { width:60pt; text-align:right; display: inline-block; *display: inline; }
div.header-label input { width:150pt; }
div.header-label span { padding:0; }

/* tabel begin */
td.mix-text { vertical-align: top; text-align: center; width:58%; }
td.mix-text label, td.mix-input label { display:block; padding-left: 5pt; text-align:left; }
	/* checkbox */
div.checkbox { width: 60pt; font-size:14pt; cursor: pointer; vertical-align:bottom; display: inline-block; *display: inline; }
div.checkbox img { width:22pt; height:22pt;  vertical-align:middle; }
	
tr.no-padding { height: 40pt; }
tr.no-padding td.mix-input label { padding-left: 0; }
tr.no-padding td.mix-input input { margin-left: 5pt; }

td.text { width: 18%; text-align: center; }
td.text.space { letter-spacing: 5pt; }
td.input { width:24%; text-align: center; }

/* inner table */
td.inner-table { padding:0; margin:0; }
td.inner-table table { width: 100%; height:100%; border:none; }
td.inner-table table td { border : none; text-align:center;  border-right:1pt solid black; }
td.inner-table table td textarea {width:95%;}
</style>
<script type="text/javascript">
	var CHECKBOX_CHECK_SRC = "<@s.url value="/rs/images/checkbox-check.png" />";
	var CHECKBOX_SRC = "<@s.url value="/rs/images/checkbox.png" />";
	<#include "billoflading.js" />
</script>
</head>
<body>
<@s.actionerror />
<@s.actionmessage />
<form method="POST" action="save-se.action">
<input type="hidden" name="id" value="${model.id!}" />
<input id="bn_id" type="hidden" name="bookingNote.id" value="${model.bookingNote.id!}" />

<table>
	<tr>
		<th id="title" colspan="2" class="no-border">提单确认通知书</th>
		<th colspan="2"class="no-border">
				<div class="header-label"><label>TO： </label>
					<input type="text" name="to" value="${model.to!}"/></div>
				<div class="header-label"><label>发件时间：</label>
					<input type="text" style="width:150px;" name="publishDate" class="easyui-datebox" value="${model.publishDate!}" />
				</div>
		</th>
	</tr>
</table>
<table style="border:1pt solid black;">
<tbody>
	<tr>
		<td rowspan="4" colspan="2" class="mix-text">
			<label>发 货 人 &nbsp; Shipper
				<button class="btn btn-mini btn-warning pull-right" onclick="ksa.bd.replacePartnerAlias('${model.bookingNote.shipper.id!}', '#shipper'); return false;"><i class="icon-edit icon-white"></i> 替换 ...</button>
			</label>
			<textarea id="shipper" name="shipper" rows="3">${model.shipper!}</textarea>
		</td>
		<td class="text"><label>B/L NO. 提单号</label></td>
		<td class="input"><input type="text" name="code" value="${model.code!}"/></td>
	</tr>
	<tr>
		<td class="text"><label>选择提单是否电放</label></td>
		<td class="input">
			<input id="deliver_type" type="hidden" name="deliverType" value="${model.deliverType!}" />
			<div class="checkbox"><img alt="电放" src="<#if model.deliverType?? && model.deliverType =="电放"><@s.url value="/rs/images/checkbox-check.png" /><#else><@s.url value="/rs/images/checkbox.png" /></#if>" />电放</div>
			<div class="checkbox"><img alt="正本" src="<#if model.deliverType?? && model.deliverType =="正本"><@s.url value="/rs/images/checkbox-check.png" /><#else><@s.url value="/rs/images/checkbox.png" /></#if>" />正本</div>
		</td>
	</tr>
	<tr>
		<td class="text space"><label>客户编号</label></td>
		<td class="input"><input type="text" name="customerCode" value="${model.customerCode!}"/></td>
	</tr>
	<tr>
		<td class="text space"><label>我司编号</label></td>
		<td class="input"><input type="text" name="selfCode" value="${model.selfCode!}"/></td>
	</tr>
	<tr>
		<td rowspan="4" colspan="2" class="mix-text">
			<label>收 货 人 &nbsp; Consignee
				<button class="btn btn-mini btn-warning pull-right" onclick="ksa.bd.replacePartnerAlias('${model.bookingNote.consignee.id!}', '#consignee'); return false;"><i class="icon-edit icon-white"></i> 替换 ...</button>
			</label>
			<textarea id="consignee" name="consignee" rows="4">${model.consignee!}</textarea>
		</td>
		<td class="text space" ><label>发件人</label></td>
		<td class="input"><input type="text" name="creator" value="${model.creator!}" /></td>
	</tr>
	<tr>
		<td class="text space" ><label>提单类型</label></td>
		<td class="input"><input type="text" name="billType" value="${model.billType!}" /></td>
	</tr>
	<tr >
		<td rowspan="4" colspan="2" class="mix-text">
			<label>备注 &nbsp; Note</label>
			<textarea rows="4" name="note">${model.note!}</textarea>
		</td>
	</tr>
	<tr></tr>
	<tr>
		<td rowspan="4" colspan="2" class="mix-text" style="width:45%;">
			<label>通 知 人 &nbsp; Notify Party
				<button class="btn btn-mini btn-warning pull-right" onclick="ksa.bd.replacePartnerAlias('${model.bookingNote.notify.id!}', '#notify'); return false;"><i class="icon-edit icon-white"></i> 替换 ...</button>
			</label>
			<textarea id="notify" rows="4" name="notify">${model.notify!}</textarea>
		</td>
	</tr>
	<tr></tr>
	<tr >
		<td rowspan="4" colspan="2" class="mix-text" style="width:45%;">
			<label>海外代理
				<button class="btn btn-mini btn-warning pull-right" onclick="ksa.bd.replacePartnerAlias('', '#agent'); return false;"><i class="icon-edit icon-white"></i> 替换 ...</button></label>
			<textarea id="agent" rows="5" style="height:85pt;" name="agent">${model.agent!}</textarea>
		</td>
	</tr>
	<tr></tr>
	<tr class="no-padding">
		<td class="mix-input"><label>船名航次 Vessel Voyage</label>
			<input type="text" name="vesselVoyage"  value="${model.vesselVoyage!}"/></td>
		<td class="mix-input"><label>起运港 Port of Lading</label>
			<input id="loading_port" type="text" name="loadingPort" value="${model.loadingPort!}"/></td>
	</tr>
	<tr class="no-padding">
		<td class="mix-input"><label>卸货港 Port of Discharge</label>
			<input id="discharge_port" type="text" name="dischargePort" value="${model.dischargePort!}"/></td>
		<td class="mix-input"><label>目的港 Final Destination</label>
			<input id="destination_port" type="text" name="destinationPort" value="${model.destinationPort!}"/></td>
	</tr>
	<tr>
		<td colspan="4" class="inner-table">
			<table><tr style="border-bottom:1pt solid black;">
				<td style="width:28%;"><label>标记及号码<br/>Mark & Numbers</label></td>
				<td style="width:10%;"><label>件&nbsp;&nbsp;数<br/>Pkgs</label></td>
				<td style="width:35%;"><label>中英文货名Description of Goods<br/>(In Chinese & English)</label></td>
				<td style="width:10%;"><label>毛 重 <br/>G.W(Kgs)</label></td>
				<td style="width:17%; border:none;"><label>体积(立方米)<br/>Dimensions</label></td>
			</tr><tr class="higher-text" style="border:none;">
				<td style="width:28%; border:none;"><textarea name="cargoMark">${model.cargoMark!}</textarea></td>
				<td style="width:10%; border:none;"><textarea name="cargoQuantity">${model.cargoQuantity!}</textarea></td>
				<td style="width:35%; border:none;"><textarea name="cargoName">${model.cargoName!}</textarea></td>
				<td style="width:10%; border:none;"><textarea name="cargoWeight">${model.cargoWeight!}</textarea></td>
				<td style="width:17%; border:none;"><textarea name="cargoVolumn">${model.cargoVolumn!}</textarea></td>
			</tr>
			</table>
			<table style="border:none;"><tr style="border:none;height:15pt;">
				<td style="width:65%; text-align:right;border:none;"><b><#if model.bookingNote.subType?? && model.bookingNote.subType == 'FCL'>SHIPPER'S LOAD, COUNT & SEAL<br/>SAID TO CONTAIN</#if></b></td>
				<td style="width:35%; text-align:left;border:none;padding-left:30pt;"><#if model.bookingNote.subType?? && model.bookingNote.subType == 'FCL'>CY TO CY<#else>CFS TO CFS</#if></td>
			</tr><tr style="border:none;height:15pt;">
				<td style="width:65%; border:none;"><input style="color:blue;font-weight:bold;text-align:right;" type="text" name="cargoQuantityDescription" value="${model.cargoQuantityDescription!}"/></td>
				<td style="width:35%; text-align:left;padding-left:30pt;border:none;">
				    <select id="payMode" class="easyui-combobox" name="payMode" data-options="editable:false">
					    <option value="FREIGHT PREPAID" <#if model.payMode?? && model.payMode == 'FREIGHT PREPAID'>selected="true"</#if>>FREIGHT PREPAID</option>
					    <option value="FREIGHT COLLECT" <#if model.payMode?? && model.payMode == 'FREIGHT COLLECT'>selected="true"</#if>>FREIGHT COLLECT</option>
				    </select>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr style="height:50pt;">
		<td colspan="4" class="inner-table">
			<table><tr style="height:50pt;">
				<td style="width:15%;"><label>箱号封号</label></td>
				<td style="width:85%;border:none;">
					<textarea style="width:98%;height:45pt;" name="cargoDescription">${model.cargoDescription!}</textarea></td>
			</tr></table>
		</td>
	</tr>
	<tr style="height:20pt; vertical-align: top;">
		<td colspan="4" style="text-align:center;">TEL : 0571-88473507 &nbsp; FAX : 0571-88473508</td>
	</tr>
	<tr style="height:30pt; vertical-align:bottom;">
		<td colspan="4" style="text-align:center;font-size:16pt;">凯思爱（杭州）物流有限公司</td>
	</tr>
</tbody>
</table>
<div class="bottom-bar fixed-bar">
	<button id="dialog_refresh" class="btn btn-warning pull-left"><i class="icon-refresh icon-white"></i> 同步数据</button>
	<button id="dialog_print" class="btn btn-success pull-left"><i class="icon-print icon-white"></i> 打印 ...</button>
	<#-- 
	<div class="btn-group dropup pull-left">
		<button id="dialog_download" class="btn btn-success btn-small" href="#" data-download="word"><i class="icon-download-alt icon-white"></i> 导出</button>
		<button class="btn btn-success btn-small dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></button>
		<ul class="dropdown-menu">
		    <li><a href="#" data-download="word">导出 Word</a></li>
		    <li class="divider"></li>
		    <li><a href="#"  data-download="excel">导出 Excel</a></li>
		</ul>
	</div>
	-->
	<div class="btn-group dropup pull-left">
		<button id="dialog_download" class="btn btn-success btn-small" href="#" data-download="excel"><i class="icon-download-alt icon-white"></i> 导出</button>
		<button class="btn btn-success btn-small dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></button>
		<ul class="dropdown-menu">
			<li><a href="#" data-download="excel" data-filename="提单确认通知书">导出提单确认通知书</a></li>
		    <li class="divider"></li>
		    <li><a href="#" data-download="original-excel" data-filename="提单原件">导出提单原件（ORIGINAL）</a></li>
		    <li class="divider"></li>
		    <li><a href="#"  data-download="copy-excel" data-filename="提单副本">导出提单副本（COPY）</a></li>
		</ul>
	</div>
	<#-- <button id="dialog_download" class="btn btn-success pull-left" data-download="excel"><i class="icon-download-alt icon-white"></i> 导出</button> -->
	<button id="dialog_save" type="submit" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>
</form>
</body>
</html>