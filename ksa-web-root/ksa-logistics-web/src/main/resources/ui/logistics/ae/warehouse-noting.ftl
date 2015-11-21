<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<title>进仓通知单</title>
<style type="text/css">
textarea, input { width:95% !important; }

textarea { height: 50pt; }
th, td { border: none; }

tr.title { height:80pt; }
tr.title th { text-align: center; font-size:20pt; font-weight:bold;}

td.text { width: 15%; text-align: left; padding-left: 15pt;  }
td.input { width:35%; text-align: left; }

td.inner-table { padding:0; margin:0; }
td.inner-table table { width: 100%; height:100%; border:none; }
td.inner-table table td { border : none; text-align:center;  }

/*日期居中*/
.inner-table input.combo-text { text-align:center; }
</style>
<script type="text/javascript">
	<#include "warehouse-noting.js" />
</script>
</head>
<body>
<@s.actionerror />
<@s.actionmessage />
<form method="POST" action="save-ae.action">
<input type="hidden" name="id" value="${model.id!}" />
<input id="bn_id" type="hidden" name="bookingNote.id" value="${model.bookingNote.id!}" />
<table>
<thead>
	<tr>
		<th colspan="5" class="noborder"><img alt="公司图标" src="<@s.url value="/rs/images/report-banner.png" />" /></th>
	</tr>
	<tr>
		<th colspan="5" class="noborder">
			<div>代理名称：凯思爱(杭州)物流有限公司</div>
			<div>地址：杭州市文二路391号西湖国际科技大厦B4-915</div>
			<div>TEL：<input style="width:100pt !important" type="text" name="salerTel" value='${model.salerTel!"88473507"}' data-options="validType:'length[0,200]'" />　
					 FAX：<input style="width:100pt !important" type="text" name="salerFax" value='${model.salerFax!"88473508"}' data-options="validType:'length[0,200]'" /></div>
			<div>EMAIL：<input style="width:200pt !important" type="text" name="salerEmail" value='${model.salerEmail!"hangzhou@ksa.co.jp"}' data-options="validType:'length[0,200]'" /></div>
		</th>
	</tr>
	<tr>
		<th colspan="5" class="noborder">
			<div>客户服务代表：</div>
			<input id="saler" type="text" name="saler" value="${model.saler!}" data-options="validType:'length[0,200]'" />
		</th>
	</tr>
	<tr class="title noborder">
		<th colspan="5" class="noborder">进仓通知单</th>
	</tr>
</thead>
<tbody>
	<tr>
		<td colspan="5" class="inner-table"><table><tr>
			<td style="width:6%; vertical-align: top;"><label>TO：</label></td>
			<td style="width:94%; border:none;">
				<textarea rows="3" style="width:98%;" name="to">${model.to!}</textarea></td>
		</tr></table></td>
	</tr>
	<tr>
		<td class="text"><label>进仓编号：</label></td>
		<td class="input"><input type="text" name="code" value="${model.code!}"/></td>
		<td class="text"><label>通知时间：</label></td>
		<td class="input"><input type="text" name="createdDate" class="easyui-datebox" value="${model.createdDate!}" /></td>
	</tr>
	<tr>
		<td class="text"><label>品名：</label></td>
		<td class="input"><input type="text" name="cargoName" value="${model.cargoName!}"/></td>
		<td class="text"><label>委托件数：</label></td>
		<td class="input"><input type="text" name="cargoQuantity" value="${model.cargoQuantity!}"/></td>
	</tr>
	<tr>
		<td class="text"><label>委托毛重：</label></td>
		<td class="input"><input type="text" name="cargoWeight" value="${model.cargoWeight!}"/></td>
		<td class="text"><label>委托体积：</label></td>
		<td class="input"><input type="text" name="cargoVolumn" value="${model.cargoVolumn!}"/></td>
	</tr>
	<tr>
		<td class="text"><label>客户名：</label></td>
		<td class="input"><input id="customer" type="text" name="customer" value="${model.customer!}" data-options="validType:'length[0,200]'" /></td>
		<td class="text"><label>起运港：</label></td>
		<td class="input"><input id="loading_port" type="text" name="loadingPort" value="${model.loadingPort!}" data-options="validType:'length[0,200]'" /></td>
	</tr>
	<tr>
		<td class="text"><label>卸货港：</label></td>
		<td class="input"><input id="discharge_port" type="text" name="dischargePort" value="${model.dischargePort!}" data-options="validType:'length[0,200]'" /></td>
		<td class="text"><label>目的地：</label></td>
		<td class="input"><input id="destination" type="text" name="destination" value="${model.destination!}" data-options="validType:'length[0,200]'" /></td>
	</tr>
	<tr>
		<td class="text"><label>航名航次：</label></td>
		<td class="input"><input id="vessel_voyage" type="text" name="vesselVoyage" value="${model.vesselVoyage!}" data-options="validType:'length[0,200]'" /></td>
		<td class="text"><label>开航日：</label></td>
		<td class="input"><input type="text" name="departureDate" class="easyui-datebox" value="${model.departureDate!}" /></td>
	</tr>
	<tr>
		<td class="text"><label>提单号：</label></td>
		<td class="input"><input type="text" name="mawb" value="${model.mawb!}" /></td>
	</tr>
	<tr></tr>
	<tr>
		<td colspan="4" class="inner-table">
			<table><tr>
				<td style="width:30%; text-align: right;"><label>贵司以上委托之货物请最晚于</label></td>
				<td style="width:40%;"><input type="text" name="entryDate" class="easyui-datebox" value="${model.entryDate!}" /></td>
				<td style="width:30%; text-align: left;"><label>前进仓！</label></td>
			</tr><tr>
				<td style="width:30%; text-align: right;"><label>若委托我司报关，请最晚于</label></td>
				<td style="width:40%;"><input type="text" name="informDate" class="easyui-datebox" value="${model.informDate!}" /></td>
				<td style="width:30%; text-align: left;"><label>前将报送单证送抵我司！</label></td>
			</tr></table>
		</td>
	</tr>
	<tr></tr>
	<tr>
		<td class="text" style="vertical-align: top;"><label>进仓地址：</label></td>
		<td class="input" colspan="3"><textarea rows="4" name="address">${model.address!}</textarea></td>
	</tr>
	<tr></tr>
	<tr>
		<td colspan="4" class="inner-table">
			<table><tr>
				<td style="width:10%;"><label>联系人：</label></td>
				<td style="width:20%;"><input type="text" name="contact" value="${model.contact!}"/></td>
				<td style="width:10%;"><label>电话：</label></td>
				<td style="width:25%;"><input type="text" name="telephone" value="${model.telephone!}"/></td>
				<td style="width:10%;"><label>传真：</label></td>
				<td style="width:25%;"><input type="text" name="fax" value="${model.fax!}"/></td>
			</tr></table>
		</td>
	</tr>
</tbody>
</table>
<div class="bottom-bar fixed-bar">
	<button id="dialog_print" class="btn btn-success pull-left"><i class="icon-print icon-white"></i> 打印 ...</button>
	<div class="btn-group dropup pull-left">
		<button id="dialog_download" class="btn btn-success btn-small" href="#" data-download="word"><i class="icon-download-alt icon-white"></i> 导出</button>
		<button class="btn btn-success btn-small dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></button>
		<ul class="dropdown-menu">
		    <li><a href="#" data-download="word">导出 Word</a></li>
		    <li class="divider"></li>
		    <li><a href="#"  data-download="excel">导出 Excel</a></li>
		</ul>
	</div>
	<button id="dialog_refresh" class="btn btn-warning pull-left"><i class="icon-refresh icon-white"></i> 同步数据</button>
	<button id="dialog_save" type="submit" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>
</form>
</body>
</html>