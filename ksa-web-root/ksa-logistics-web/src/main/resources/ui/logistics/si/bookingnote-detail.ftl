<#-- 判断状态的静态类 -->
<#assign state = model.state?c />
<#assign stateClass = "@com.ksa.model.logistics.BookingNoteState" />
<#if stack.findValue("${stateClass}@isReturned(${state})")>
	<#assign isReturned = true />
<#else>	
	<#assign isReturned = false />
</#if>
<input id="node_id" type="hidden" name="id" value="${model.id!}" />
<input type="hidden" name="type" value="SI" />
<input type="hidden" name="serialNumber" value="${model.serialNumber?c}" />
<div id="content-container">
	<@s.actionerror />
	<@s.actionmessage />
<#-- 基础信息 -->
<div class="form-inline">
	<div class="control-group">
		<label class="control-label">编号：</label>
		<div class="controls">
	    	<input type="text" name="code" readonly="readonly" value="${model.code!}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">客户：</label>
		<div class="controls">
	    	<input id="customer" type="text" name="customer.id" value="${model.customer.id!}" 
	    		data-options="required:true,validType:'script[\'validateCustomer()\', \'客户不存在\']'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">发票编号：</label>
		<div class="controls">
	    	<input type="text" name="invoiceNumber" value="${model.invoiceNumber!}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">接单日期：</label>
		<div class="controls">
	    	<input type="text" name="createdDate" class="easyui-datebox" value="${model.createdDate?date}" />
		</div>
	</div>	
	<br/><#-- 第一行 -->
	<div class="control-group">
		<label class="control-label">类型：</label>
		<div class="controls" style="margin-left:65px">
	    	<input type="text" readonly="readonly" value="海运进口" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">销售担当：</label>
		<div class="controls" style="margin-left:65px">
	    	<input id="saler" type="text" name="saler.id" value="${model.saler.id!}" 
	    		data-options="required:true,validType:'script[\'validateSaler()\', \'销售人员不存在\']'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">代理：</label>
		<div class="controls" style="margin-left:65px">
	    	<input id="agent" type="text" name="agent.id" value="${model.agent.id!}" data-options="validType:'length[0,200]'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">操作人员：</label>
		<div class="controls" style="margin-left:65px">
	    	<input type="text" readonly="readonly" name="creator.name" value="${model.creator.name!}" />
			<input type="hidden" name="creator.id" value="${model.creator.id!}" />
		</div>
	</div>
	<#-- end : control-group -->
</div>


<fieldset class="form-inline">
	<legend>报关、退单相关信息</legend>
	<img title="海运进口" alt="海运进口" src="<@s.url value="/rs/images/type/SI.png" />" class="img-polaroid logistics-logo" />
	<div class="control-group">
		<label class="control-label">报关单号：</label>
		<div class="controls">
			<input type="text" name="customsCode" value="${model.customsCode!}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">报关日期：</label>
		<div class="controls">
	    	<input type="text" name="customsDate" class="easyui-datebox" <#if model.customsDate??>value="${model.customsDate?date}"</#if> />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">报关行：</label>
		<div class="controls" style="margin-left:65px">
	    	<input id="chb" type="text" name="customsBroker.id" value="${model.customsBroker.id!}" data-options="validType:'length[0,200]'" />
		</div>
	</div>
	<br/><#-- 换行 -->
	<div class="control-group">
		<label class="control-label">退单号：</label>
		<div class="controls">
			<input id="returnCode" type="text" name="returnCode" value="${model.returnCode!}" <#if isReturned>readonly="readonly"</#if> />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">收单日期：</label>
		<div class="controls">
	    	<input id="returnDate" type="text" name="returnDate" class="easyui-datebox" <#if model.returnDate??>value="${model.returnDate?date}"</#if> <#if isReturned>readonly="readonly"</#if> />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">寄送日期：</label>
		<div class="controls">
	    	<input id="returnDate" type="text" name="returnDate2" class="easyui-datebox" <#if model.returnDate2??>value="${model.returnDate2?date}"</#if> <#if isReturned>readonly="readonly"</#if> />
		</div>
	</div>
	<br/>
	<div class="control-group">
		<label class="control-label">退单快件号：</label>
		<div class="controls">
			<input type="text" name="returnExpressCode" value="${model.returnExpressCode!}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><#if isReturned><b style="color:#51A351;">已退单</b><#else><b style="color:#FAA732;">未退单：</b></#if></label>
		<div class="controls">
	    	<#if !isReturned>
			<@shiro.hasPermission name="bookingnote:edit">
				<button id="dialog_save_returned" type="submit" class="btn btn-success"><i class="icon-ok icon-white"></i> 完成退单</button>
			</@shiro.hasPermission>
			</#if>
		</div>
	</div>
	<br/><#-- 换行 -->
	<div class="control-group">
		<label class="control-label">税单号：</label>
		<div class="controls">
			<input type="text" name="taxCode" value="${model.taxCode!}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">收单日期：</label>
		<div class="controls">
	    	<input type="text" name="taxDate1" class="easyui-datebox" <#if model.taxDate1??>value="${model.taxDate1?date}"</#if> />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">寄送日期：</label>
		<div class="controls">
	    	<input type="text" name="taxDate2" class="easyui-datebox" <#if model.taxDate2??>value="${model.taxDate2?date}"</#if> />
		</div>
	</div>
	<br/>
	<div class="control-group">
		<label class="control-label">税单快件号：</label>
		<div class="controls">
			<input type="text" name="taxExpressCode" value="${model.taxExpressCode!}" />
		</div>
	</div>
</fieldset>

<fieldset class="form-inline">
<legend>车队信息</legend>
	<div class="control-group">
		<label class="control-label">车队：</label>
		<div class="controls" style="margin-left:65px">
	    	<input id="team" type="text" name="vehicleTeam.id" value="${model.vehicleTeam.id!}" data-options="validType:'length[0,200]'"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">车型：</label>
		<div class="controls">
			<input id="vehicle_type" type="text" name="vehicleType" value="${model.vehicleType!}" data-options="validType:'length[0,200]'"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">车牌号：</label>
		<div class="controls">
	    	<input type="text" name="vehicleNumber" value="${model.vehicleNumber!}" />
		</div>
	</div>
</fieldset>

<#-- 货物信息 -->
<fieldset class="form-inline">
<legend style="margin-bottom:10px;">货物信息</legend>
<div style="float:left;margin:10px 10px 0 0">	
	<div class="control-group">
		<label class="control-label">品名：</label>
		<div class="controls">
			<textarea name="cargoName" rows="2"
				class="easyui-validatebox input-xxlarge" data-options="required:true,validType:'length[0,2000]'">${model.cargoName!}</textarea>
		</div>
	</div> <br/>
	<div class="control-group">
		<label class="control-label">英文品名：</label>
		<div class="controls">
			<textarea name="cargoNameEng" rows="3"
				class="easyui-validatebox input-xxlarge" data-options="required:false,validType:'length[0,1000]'">${model.cargoNameEng!}</textarea>
		</div>
	</div> <br/>
	<div class="control-group">
		<label class="control-label">价格：</label>
		<div class="controls">
	    	<input type="text" name="cargoPrice" value="${model.cargoPrice!}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">HScode：</label>
		<div class="controls">
	    	<input type="text" name="hsCode" value="${model.hsCode!}" />
		</div>
	</div> <br/>
	<div class="control-group">
		<label class="control-label">申报要素：</label>
		<div class="controls">
			<textarea name="keyContent" rows="3" class="input-xxlarge">${model.keyContent!}</textarea>
		</div>
	</div> <br/>
	<div class="control-group">
		<label class="control-label">唛头：</label>
		<div class="controls">
			<textarea name="shippingMark" rows="2" class="input-xxlarge">${model.shippingMark!}</textarea>
		</div>
	</div> <br/>
	<div class="control-group">
		<label class="control-label">箱号封号：</label>
		<div class="controls">
			<textarea name="cargoDescription" rows="2"
				class="easyui-validatebox input-xxlarge" data-options="validType:'length[0,2000]'">${model.cargoDescription!}</textarea>
		</div>
	</div> <br/>
	<div class="control-group">
		<label class="control-label">体积：</label>
		<div class="controls input-append" style="margin-left:0;">
	    	<input type="text" name="volumn" style="text-align:right;" <#if model.volumn??>value="${model.volumn?c}"</#if>
	    		class="easyui-numberbox input-small" data-options="min:0,precision:3" />
	    	<span class="add-on">m<sup>3</sup></span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">毛重：</label>
		<div class="controls input-append" style="margin-left:0;">
	    	<input type="text" name="weight" style="text-align:right;" <#if model.weight??>value="${model.weight?c}"</#if>
	    		class="easyui-numberbox input-small" data-options="min:0,precision:3" />
		<span class="add-on">kg</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">数量：</label>
		<div class="controls input-append" style="margin-left:0;">
	    	<input type="text" name="quantity" style="text-align:right;" <#if model.quantity??>value="${model.quantity?c}"</#if>
	    		class="input-small" data-options="min:0" />
	    		<span class="add-on" style="padding:0;height:28;border:none;">
	    			<input id="units" type="text" name="unit" value="${model.unit!}"
	    				data-options="width:86,border:false" />
	    		</span>
		</div>
	</div>
	<br/><#-- 换行 -->
	
	<div class="control-group">
		<label class="control-label">数量&nbsp;&nbsp;&nbsp;<br />英文描述：</label>
		<div class="controls">
	    	<textarea name="quantityDescription" rows="2" class="input-xxlarge">${model.quantityDescription!}</textarea>
		</div>
	</div>
	<br/><#-- 换行 -->
	
	<div class="control-group">
		<label class="control-label">备注：</label>
		<div class="controls">
	    	<textarea name="cargoNote" rows="2" class="input-xxlarge">${model.cargoNote!}</textarea>
		</div>
	</div>
</div>	<#-- end 货物属性 -->
<div style="float:left; width:300px;margin-top:10px;">
	<table id="cargo_grid">
		<#list model.cargos as cargo>
		    <tr><td>${cargo.id!}</td><td>${cargo.name!}</td><td>${cargo.category!}</td><td>${cargo.type!}</td><td>${cargo.amount!}</td></tr>
		</#list>
	</table>
	<div class="control-group" style="margin-top:5px;">
		<label class="control-label">箱类箱型：</label>
		<div class="controls input-append" style="margin-left:0;">
	    	<input id="cargo_container" type="text" name="cargoContainer" value="${model.cargoContainer!}" />
	    	<span class="add-on" style="padding:0;border:none;">
    			<button id="cargo_refresh" class="btn" style="height:30px;"><i class="icon-refresh"></i> 刷新</button>
    		</span>
		</div>
	</div>
</div>	<#-- end 货物列表 -->

</fieldset>

<fieldset class="form-inline">
<legend>提单信息</legend>
	<div class="control-group">
		<label class="control-label">主提单号：</label>
		<div class="controls">
	    	<input type="text" name="mawb" value="${model.mawb!}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">副提单号：</label>
		<div class="controls">
	    	<input type="text" name="hawb" value="${model.hawb!}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">开票抬头：</label>
		<div class="controls">
	    	<input type="text" name="title" value="${model.title!}" />
		</div>
	</div>
	<br/><#-- 换行 -->
	<div class="control-group">
		<label class="control-label">发货人：</label>
		<div class="controls">
	    	<input id="shipper" type="text" name="shipper.id" value="${model.shipper.id!}" data-options="validType:'length[0,200]'"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">收货人：</label>
		<div class="controls">
	    	<input id="consignee" type="text" name="consignee.id" value="${model.consignee.id!}" data-options="validType:'length[0,200]'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">通知人：</label>
		<div class="controls">
	    	<input id="notify" type="text" name="notify.id" value="${model.notify.id!}" data-options="validType:'length[0,200]'" />
		</div>
	</div>
</fieldset>

<#-- 航线信息 -->
<fieldset class="form-inline">
	<legend>航线港口信息</legend>
	<div class="control-group">
		<label class="control-label">航线：</label>
		<div class="controls">
	    	<input id="route_sea" type="text" name="route" value="${model.route!}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">船名：</label>
		<div class="controls">
			<input type="text" name="routeName" value="${model.routeName!}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">航次：</label>
		<div class="controls">
	    	<input type="text" name="routeCode" value="${model.routeCode!}" />
		</div>
	</div>
	<br/><#-- 换行 -->
	
	<div class="control-group">
		<label class="control-label">起运港：</label>
		<div class="controls">
	    	<input id="departure_port" type="text" name="departurePort" value="${model.departurePort!}" 
    			data-options="validType:'length[0,200]'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">装货港：</label>
		<div class="controls">
	    	<input id="loading_port" type="text" name="loadingPort" value="${model.loadingPort!}" data-options="validType:'length[0,200]'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">离港日：</label>
		<div class="controls">
	    	<input type="text" name="departureDate" <#if model.departureDate??>value="${model.departureDate?date}"</#if> class="easyui-datebox" />
		</div>
	</div>
	<br/><#-- 换行 -->
	
	<div class="control-group">
		<label class="control-label">目的港：</label>
		<div class="controls">
	    	<input id="destination_port" type="text" name="destinationPort" value="${model.destinationPort!}" 
    			data-options="validType:'length[0,200]'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">卸货港：</label>
		<div class="controls">
	    	<input id="discharge_port" type="text" name="dischargePort" value="${model.dischargePort!}" data-options="validType:'length[0,200]'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">到港日：</label>
		<div class="controls">
	    	<input type="text" name="destinationDate" <#if model.destinationDate??>value="${model.destinationDate?date}"</#if> class="easyui-datebox" />
		</div>
	</div>
	<br/><#-- 换行 -->
	
	<div class="control-group">
		<label class="control-label">出发地：</label>
		<div class="controls">
	    	<input id="departure" type="text" name="departure" value="${model.departure!}" data-options="validType:'length[0,200]'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">目的地：</label>
		<div class="controls">
	    	<input id="destination" type="text" name="destination" value="${model.destination!}" data-options="validType:'length[0,200]'" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">送货日：</label>
		<div class="controls">
	    	<input type="text" name="deliverDate" <#if model.deliverDate??>value="${model.deliverDate?date}"</#if> class="easyui-datebox" />
		</div>
	</div>
	<br/><#-- 换行 -->
	<div class="control-group">
		<label class="control-label">承运人：</label>
		<div class="controls">
	    	<input id="carrier" type="text" name="carrier.id" value="${model.carrier.id!}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">船代：</label>
		<div class="controls">
	    	<input id="shipping_agent" type="text" name="shippingAgent.id" value="${model.shippingAgent.id!}" />
		</div>
	</div>
</fieldset>
</div><#-- end : easyui-panel -->
<div class="bottom-bar fixed-bar">
<@shiro.hasPermission name="finance:charge">
	<button id="btn_charge" class="btn btn-success pull-left">查看费用信息</button>
</@shiro.hasPermission>
<@shiro.hasPermission name="bookingnote:edit">
	<button id="dialog_save" type="submit" class="btn btn-primary"><i class="icon-ok icon-white"></i> 保存</button>
</@shiro.hasPermission>
	<button id="dialog_close" class="btn btn-danger"><i class="icon-off icon-white"></i> 关闭</button>
</div>