<?xml version="1.0" encoding="UTF-8"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet" xmlns:o="urn:schemas-microsoft-com:office:office"
	xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:html="http://www.w3.org/TR/REC-html40">
	<DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
		<Version>14</Version>
	</DocumentProperties>
	<OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
		<RemovePersonalInformation />
	</OfficeDocumentSettings>
	<ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
		<AcceptLabelsInFormulas />
		<ProtectStructure>False</ProtectStructure>
		<ProtectWindows>False</ProtectWindows>
	</ExcelWorkbook>
	<Styles>
		<Style ss:ID="Default" ss:Name="Normal">
			<Alignment ss:Vertical="Center" />
			<Borders />
			<Font ss:FontName="宋体" x:CharSet="134" ss:Size="12" />
			<Interior />
			<NumberFormat />
			<Protection />
		</Style>
		<!-- 结算单编号样式 -->
		<Style ss:ID="bill-number">
		</Style>
		<!-- 结算单标题样式 -->
		<Style ss:ID="bill-title">
			<Alignment ss:Horizontal="Center" ss:Vertical="Top" ss:WrapText="1" />
			<Font ss:FontName="宋体" x:CharSet="134" ss:Size="20" ss:Bold="1" />
		</Style>
		<Style ss:ID="bill-customer">
			<Alignment ss:Vertical="Bottom" />
		</Style>
		<Style ss:ID="bill-title-address">
			<Alignment ss:Vertical="Bottom" ss:WrapText="1" />
		</Style>
		<Style ss:ID="th">
		   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
		   <Borders>
			   <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
		       <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
		       <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
		       <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
		   </Borders>
		   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="12" ss:Color="#3366FF"/>
		   <Interior ss:Color="#FFFF99" ss:Pattern="Solid"/>
		</Style>
		<Style ss:ID="td">
			<Borders>
		    	<Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
		    	<Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
		    	<Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
		    	<Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
			</Borders>
			<NumberFormat ss:Format="0.00_ "/>
		</Style>
		<Style ss:ID="td-total">
			<Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
			<Borders>
		    	<Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
		    	<Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
		    	<Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
		    	<Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
			</Borders>
		</Style>
		<Style ss:ID="td-summary">
			<Borders>
				<Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
		    	<Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
		    	<Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
		    	<Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
			</Borders>
			<Interior ss:Color="#FFCC99" ss:Pattern="Solid"/>
			<NumberFormat ss:Format="0.00_ "/>
		</Style>
		<Style ss:ID="td-summary-rate">
			<Borders>
				<Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
		    	<Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
		    	<Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
		    	<Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
			</Borders>
			<Interior ss:Color="#FFCC99" ss:Pattern="Solid"/>
			<NumberFormat ss:Format="0.0000_ "/>
		</Style>
		<Style ss:ID="td-total-sum">
			<NumberFormat ss:Format="0.00_ "/>
		</Style>
	</Styles>
	<Worksheet ss:Name="结算单">
		<Table x:FullColumns="1" x:FullRows="1" ss:StyleID="Default" ss:DefaultColumnWidth="60" ss:DefaultRowHeight="22.5">
			<Row ss:AutoFitHeight="0" ss:Height="27">
				<Cell ss:StyleID="bill-number">
					<Data ss:Type="String">${model.code!}</Data>
				</Cell>
				<Cell ss:StyleID="bill-number" />
				<Cell ss:StyleID="bill-number" />
			</Row>
			<Row ss:Index="3" ss:AutoFitHeight="0" ss:Height="62.25">
				<Cell ss:StyleID="bill-customer">
					<Data ss:Type="String">客户：</Data>
				</Cell>
				<Cell ss:StyleID="bill-customer">
					<Data ss:Type="String">${model.target.name!}</Data>
				</Cell>
				<Cell ss:Index="5" ss:MergeAcross="3" ss:StyleID="bill-title">
					<Data ss:Type="String">费用确认单&#10;${accountMonth!}</Data>
				</Cell>
				<Cell ss:MergeAcross="4" ss:StyleID="bill-title-address">
					<Data ss:Type="String">杭州圣世特物流有限公司&#10;杭州市文二路391号西湖国际科技大厦B3-1310&#10;TEL：88473507     FAX：88473508</Data>
				</Cell>
			</Row>
			
			<#assign currentType="其他" />
			<#assign count = bookingNoteData.size() />
			<#if (count > 0)>
			<#assign groupTotal = emptyTotalCharge>	<#-- 获取空的分组统计 -->
			<#assign charges = chargeData.values().toArray() />
			<#assign chargeHeaderArray = chargeHeader.keySet().toArray() />
			<#list 0..(count-1) as i>
			<#assign bnData = bookingNoteData.get(i) />
			
			<#-- 开始新的类型分组 -->
			<#if bnData[0] != currentType>
			<#-- 分组统计 -->
			<#if currentType != "其他">
			<Row>
				<Cell ss:MergeAcross="${bookingNoteHeader.size() - 1}" ss:StyleID="td-total"><Data ss:Type="String">TOTAL：</Data></Cell>
				<#list chargeHeader.values() as chargeTypes>
					<#list chargeTypes as type>
						<Cell ss:StyleID="td"><Data ss:Type="Number">${groupTotal.get( type )?string("0.00")}</Data></Cell>
					</#list>					
				</#list>
			</Row>
			<Row/> <#-- 空行 -->
			<#assign groupTotal = emptyTotalCharge>	<#-- 获取空的分组统计 -->
			</#if>
			<#assign currentType=bnData[0] />
			<Row>
				<Cell><Data ss:Type="String">${bnData[0]!}</Data></Cell><#-- 海运出口/海运进口/空运出口/空运进口/国内运输 -->
			</Row>
			<Row>
			<#list bookingNoteHeader as bnHeader>
				<Cell ss:MergeDown="1" ss:StyleID="th"><Data ss:Type="String">${bnHeader}</Data></Cell>
			</#list>
			<#list chargeHeaderArray as currencyName>
				<Cell ss:MergeAcross="${chargeHeader.get( currencyName ).size() - 1}" ss:StyleID="th"><Data ss:Type="String">${currencyName}</Data></Cell>
			</#list>
			</Row>
			<Row>
			<#assign writeIndex = true />	<#-- 单元格合并后 第二行起始的单元格要指定起始列编号 -->
			<#list chargeHeader.values() as chargeTypes>
				<#list chargeTypes as type>
					<Cell ss:StyleID="th"<#if writeIndex> ss:Index="${bookingNoteHeader.size() + 1}"</#if>><Data ss:Type="String">${type.substring(type.indexOf("-") + 1)}</Data></Cell>
					<#assign writeIndex = false />
				</#list>
			</#list>
			</Row>
			</#if>
				
			<#-- 具体托单及费用数据 -->
			<Row>
				<#list bnData as label>
				<#if label_index != 0><Cell ss:StyleID="td"><Data ss:Type="String">${label!}</Data></Cell></#if>
				</#list>
				<#list chargeHeader.values() as chargeTypes>
					<#list chargeTypes as type>
						<#if charges[i].get( type )??>
				<Cell ss:StyleID="td"><Data ss:Type="Number">${charges[i].get( type )?string("0.00")}</Data></Cell>
							<#-- 计算分组统计 -->
							<#assign subtotal = charges[i].get( type ) + groupTotal.get( type ) />${groupTotal.put( type, subtotal )!}
						<#else>
				<Cell ss:StyleID="td"><Data ss:Type="String">/</Data></Cell>
						</#if>
					</#list>					
				</#list>
			</Row>			  
			</#list>
			
			<#-- 分组统计 -->
			<Row>
				<Cell ss:MergeAcross="${bookingNoteHeader.size() - 1}" ss:StyleID="td-total"><Data ss:Type="String">TOTAL：</Data></Cell>
				<#list chargeHeader.values() as chargeTypes>
					<#list chargeTypes as type>
						<Cell ss:StyleID="td"><Data ss:Type="Number">${groupTotal.get( type )?string("0.00")}</Data></Cell>
					</#list>					
				</#list>
			</Row>
			<Row/> <#-- 空行 -->
			
			<#-- 整体汇总 -->
			<#list chargeHeaderArray as currencyName>
			<Row>
				<#if currencyName_index == 0>
				<Cell ss:StyleID="td-summary" ss:MergeDown="${chargeHeader.keySet().size() - 1}"><Data ss:Type="String">合计：</Data></Cell>
				</#if>
				<Cell ss:StyleID="td-summary"<#if ( currencyName_index > 0 )> ss:Index="2"</#if>><Data ss:Type="String">${currencyName}：</Data></Cell>
				<Cell ss:StyleID="td-summary"><Data ss:Type="Number">${charges[count].get( currencyName + "-小计" )?string("0.00")}</Data></Cell>
				<Cell ss:StyleID="td-summary-rate"><#if currencyName == "人民币"><Data ss:Type="String">汇率</Data><#else><Data ss:Type="Number">${chargeRate.get( currencyName )?string("0.0000")}</Data></#if></Cell>
			</Row>
			</#list>
			<Row>
				<Cell><Data ss:Type="String">折合人民币总计：</Data></Cell>
				<Cell ss:StyleID="td-total-sum"><Data ss:Type="Number">${total?string("0.00")}</Data></Cell>
			</Row>
			</#if>
		</Table>
		<WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
			<PageSetup>
				<Layout x:Orientation="Landscape" />
			</PageSetup>
			<Unsynced />
			<FitToPage>True</FitToPage>
			<Print />
			<Selected />
			<Panes />
			<ProtectObjects>False</ProtectObjects>
			<ProtectScenarios>False</ProtectScenarios>
		</WorksheetOptions>
	</Worksheet>
</Workbook>