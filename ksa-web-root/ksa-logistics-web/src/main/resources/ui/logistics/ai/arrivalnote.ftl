<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<title>到货通知单</title>
<style type="text/css">
tr { height: 15pt; }
textarea, input { width:95% !important; }
th, td { border: none; }
th { border-top: 1pt solid black; border-bottom: 1pt solid black; text-align:center; }
tr.no-top-border th { border-top: none; }
td.text, span.text { font-weight:bold; }

div.title { height:24pt; text-align: center; font-size:20pt; font-weight:bold; font-style: italic; }

table.date-no tr { height: 15pt; }
table.date-no td.text { text-align:right;width:75%; padding-right:5pt; }

</style>
<script type="text/javascript">
	<#include "arrivalnote.js" />
</script>
</head>
<body>
<@s.actionerror />
<@s.actionmessage />
<form method="POST" action="save-ai.action">
<input type="hidden" name="id" value="${model.id!}" />
<input id="bn_id" type="hidden" name="bookingNote.id" value="${model.bookingNote.id!}" />

<div><img alt="公司图标" style="height:36pt;" src="<@s.url value="/rs/images/report-banner-arrival.png" />" /><div>
<div class="title">ARRIVAL NOTICE  /  DEBIT NOTE</div>
<table class="date-no">
	<tr><td class="text">DATE.</td><td><input type="text" name="date" class="easyui-datebox" value="${model.date!}" /></td></tr>
	<tr><td class="text">NO.</td><td><input type="text" name="code" value="${model.code!}" /></td></tr>
</table>
<div>
<span class="text">MESSERS：</span>
<button class="btn btn-mini btn-warning" onclick="ksa.bd.replacePartnerAlias('${model.bookingNote.consignee.id!}', '#consignee'); return false;"><i class="icon-edit icon-white"></i> 替换 ...</button>
</div>
<textarea id="consignee" style="margin-left:5pt;height:45pt;" rows="4" name="consignee">${model.consignee!}</textarea>
<div>
<span class="text">SHIPPER NAME:</span>
<button class="btn btn-mini btn-warning" onclick="ksa.bd.replacePartnerAlias('${model.bookingNote.shipper.id!}', '#shipper'); return false;"><i class="icon-edit icon-white"></i> 替换 ...</button>
</div>
<textarea id="shipper" style="margin-left:5pt;height:30pt;" rows="4" name="shipper">${model.shipper!}</textarea>
<table class="vessel">
	<tr>
		<td class="text">VESSEL：</td>
		<td><input type="text" name="vessel" value="${model.vessel!}" /></td>
		<td class="text">VOY NO.</td>
		<td><input type="text" name="voyage" value="${model.voyage!}" /></td>
		<td class="text">ETA：</td>
		<td><input type="text" name="eta" value="${model.eta!}" /></td>
		<td class="text"></td>
	</tr>
</table>
<table>
	<tr>
		<td class="text">M B/L NO.</td>
		<td><input type="text" name="mawb" value="${model.mawb!}" /></td>
		<td class="text">CONTAINER NO.</td>
		<td><input type="text" name="container" value="${model.container!}" /></td>
		<td class="text" style="text-align:center;">CY</td>
	</tr>
	<tr>
		<td class="text">H B/L NO.</td>
		<td><input type="text" name="hawb" value="${model.hawb!}" /></td>
		<td class="text">SEAL NO.</td>
		<td><input type="text" name="seal" value="${model.seal!}" /></td>
		<td rowspan="4">
			<textarea id="shipper" style="margin-left:5pt; height:45pt;" rows="3" name="cy">${model.cy!}</textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="width:200pt;" class="text">PORT OF LOADING:</td>
		<td class="text" style="width:125pt;">PORT OF DISCHARGE:</td>
		<td><input type="text" name="dischargePort" value="${model.dischargePort!}" /></td>
	</tr>
	<tr>
		<td colspan="2"  class="text"><input type="text" name="loadingPort" value="${model.loadingPort!}" /></td>
		<td class="text" style="width:125pt;">PLACE OF DELIVERY: </td>
		<td><input type="text" name="deliverPlace" value="${model.deliverPlace!}" /></td>
	</tr>
</table>
<table>
	<tr>
		<td style="width:20pt;">&nbsp;</td>
		<th>MARKS</th>
		<th>DESCRIPTION</th>
		<th>P'KG</th>
		<th>KGS./M3</th>
	</tr>
	<tr  style="height:60pt">
		<td>&nbsp;</td>
		<td><textarea id="cargoMark" style=" height:60pt" rows="4" name="cargoMark">${model.cargoMark!}</textarea></td>
		<td style="width:186pt;">SHIPPER'S LOAD, COUNT & SEAL <br/> 
				(<input type="text" style="width:50pt !important;" name="count" value="${model.count!}" />)CONTAINER S.T.C. <br/>
				<textarea id="cargoDescription" style=" height:30pt" rows="2" name="cargoDescription">${model.cargoDescription!}</textarea> </td>
		<td style="width:90pt;">
			<textarea id="cargo" rows="2" style=" height:30pt" name="cargo">${model.cargo!}</textarea>
			<textarea id="pkg" rows="2" style=" height:30pt" name="pkg">${model.pkg!}</textarea> </td>
		<td>
			<input type="text" style="width:30pt !important;" name="cargoWeight" value="${model.cargoWeight!}" /> KGS<br/>
			<input type="text" style="width:30pt !important;" name="cargoVolumn" value="${model.cargoVolumn!}" /> M3
		</td>
	</tr>
	<tr class="no-top-border">
		<td>&nbsp;</td>
		<th></th>
		<th style="text-align:left;">"FREIGHT:<input type="text" style="width:60pt !important;" name="freight" value="${model.freight!}" />"</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
	</tr>
	<tr class="no-top-border">
		<td>&nbsp;</td>
		<td colspan="4">
			<span class="text">CHARGE:</span><input type="text" style="width:80pt !important;" name="charge" value="${model.charge!}" />
			<span class="text">EX. CHANGE RATE:</span><input type="text" style="width:80pt !important;" name="rate" value="${model.rate!}" /></td>
	</tr>
</table>
<table>
	<tr>
		<td style="width:45pt;">&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td style="text-align:right;">単価</td>
		<td style="width:30pt;">&nbsp;</td>
		<td style="text-align:right;">数量</td>
		<td style="width:30pt;">&nbsp;</td>
		<td style="text-align:right;">合計</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>海上運賃</td>
		<td style="text-align:right;">US$</td>
		<td style="text-align:right;">0.00</td>
		<td style="text-align:center;">ｘ</td>
		<td style="text-align:right;">1</td>
		<td style="text-align:center;">=</td>
		<td style="text-align:right;">0</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>FAF</td>
		<td style="text-align:right;">&nbsp;</td>
		<td style="text-align:right;">￥28,800</td>
		<td style="text-align:center;">ｘ</td>
		<td style="text-align:right;">2</td>
		<td style="text-align:center;">=</td>
		<td style="text-align:right;">￥57,600</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>YAS</td>
		<td style="text-align:right;">&nbsp;</td>
		<td style="text-align:right;">￥3,600</td>
		<td style="text-align:center;">ｘ</td>
		<td style="text-align:right;">2</td>
		<td style="text-align:center;">=</td>
		<td style="text-align:right;">￥7,200</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>EBS</td>
		<td style="text-align:right;">&nbsp;</td>
		<td style="text-align:right;">￥7,200</td>
		<td style="text-align:center;">ｘ</td>
		<td style="text-align:right;">2</td>
		<td style="text-align:center;">=</td>
		<td style="text-align:right;">￥14,400</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>D/O FEE</td>
		<td style="text-align:right;">&nbsp;</td>
		<td style="text-align:right;">￥2,500</td>
		<td style="text-align:center;">ｘ</td>
		<td style="text-align:right;">1</td>
		<td style="text-align:center;">=</td>
		<td style="text-align:right;">￥2,500</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>CY CHARGE</td>
		<td style="text-align:right;">&nbsp;</td>
		<td style="text-align:right;">￥24,200</td>
		<td style="text-align:center;">ｘ</td>
		<td style="text-align:right;">2</td>
		<td style="text-align:center;">=</td>
		<td style="text-align:right;">￥48,400</td>
	</tr>
	<tr class="no-top-border">
		<td>&nbsp;</td>
		<th style="text-align:left;">DOC FEE</th>
		<th style="text-align:right;">&nbsp;</th>
		<th style="text-align:right;">￥2,000</th>
		<th style="text-align:center;">ｘ</th>
		<th style="text-align:right;">1</th>
		<th style="text-align:center;">=</th>
		<th style="text-align:right;">￥2,00</th>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td style="text-align:right;">￥132,100</td>
	</tr>
</table>
<table>
	<tr>
		<td style="width:15pt;">&nbsp;</td>
		<td style="vertical-align: top; font-weight:bold;">振込先：</td>
		<td style="vertical-align: top; font-weight:bold;">
			三菱東京UFJ銀行 西院支店<br/>
			当座　番号 9000777<br/>
			（株）KSAインターナショナル
		</td>
		<td>&nbsp;</td>
		<td>
			諸費用は弊社へお振込ください。<br/>
			振込明細のFAXを頂けますとD/O切替書類を<br/>
			折り返しFAXさせていただきます。<br/>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td style="vertical-align: top; font-weight:bold;">住所：</td>
		<td style="vertical-align: top; font-weight:bold;">
			〒604-8824<br/>
			京都市中京区壬生高樋町13番地<br/>
			TEL:075-821-2702<br/>
			FAX:075-821-2703
		</td>
		<td>&nbsp;</td>
		<td>
			*小切手の持込は受け付けておりません。<br/><br/><br/><span style="font-weight:bold;">担当：加藤</span>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td style="vertical-align: top;">CY：</td>
		<td>
			OHI B/A NO.1-2CY<br/>
			CODE:1FD01<br/>
			TEL:03-3790-8063<br/>
			FAX:03-3790-5243
		</td>
		<td>&nbsp;</td>
		<td style="vertical-align: top;">
			D/O切替場所：LESS
		</td>
	</tr>
</table>

<div class="bottom-bar fixed-bar">
	<button id="dialog_refresh" class="btn btn-warning pull-left"><i class="icon-refresh icon-white"></i> 同步数据</button>
	<button id="dialog_print" class="btn btn-success pull-left"><i class="icon-print icon-white"></i> 打印 ...</button>
	<button id="dialog_download" class="btn btn-success btn-small pull-left" data-download="excel"><i class="icon-download-alt icon-white"></i> 导出</button>
	<button id="dialog_save" type="submit" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>
</form>
</body>
</html>
