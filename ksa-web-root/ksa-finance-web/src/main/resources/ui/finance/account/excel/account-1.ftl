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
		</Style>
	</Styles>
	<Worksheet ss:Name="对账单">
		<Table x:FullColumns="1" x:FullRows="1" ss:StyleID="Default" ss:DefaultColumnWidth="60" ss:DefaultRowHeight="22.5">
				<Row>
					<Cell><Data ss:Type="String">供应名称：</Data></Cell>
					<Cell ss:MergeAcross="2"><Data ss:Type="String">${model.target.name!}</Data></Cell>
					<Cell><Data ss:Type="String">账期：</Data></Cell>
					<Cell ss:MergeAcross="1"><Data ss:Type="String">${accountBeginDate!}</Data></Cell>
					<Cell><Data ss:Type="String">至</Data></Cell>
					<Cell ss:MergeAcross="1"><Data ss:Type="String">${accountEndDate!}</Data></Cell>
				</Row>
				<Row />	<#-- 空行 -->
				<Row>
				<#list bookingNoteHeader as bnHeader>
					<Cell ss:MergeDown="1" ss:StyleID="th"><Data ss:Type="String">${bnHeader}</Data></Cell>
				</#list>
				<#assign chargeHeaderArray = chargeHeader.keySet().toArray() />
				<#list chargeHeaderArray as currencyName>
					<Cell ss:MergeAcross="${chargeHeader.get( currencyName ).size() - 1}" ss:StyleID="th"><Data ss:Type="String">${currencyName}</Data></Cell>
				</#list>
				</Row>
				<Row>
				<#assign writeIndex = true />	<#-- 单元格合并后 第二行其实的单元格要指定起始列编号 -->
				<#list chargeHeader.values() as chargeTypes>
					<#list chargeTypes as type>
						<Cell ss:StyleID="th"<#if writeIndex> ss:Index="${bookingNoteHeader.size() + 1}"</#if>><Data ss:Type="String">${type.substring(type.indexOf("-") + 1)}</Data></Cell>
						<#assign writeIndex = false />
					</#list>
				</#list>
				</Row>
				<#assign count = bookingNoteData.size() />
				<#if (count > 0)>
				<#assign charges = chargeData.values().toArray() />
				<#list 0..(count-1) as i>
				<Row>
					<#assign bnData = bookingNoteData.get(i) />
					<#list bnData as label>
					<Cell ss:StyleID="td"><Data ss:Type="String">${label}</Data></Cell>
					</#list>
					<#list chargeHeader.values() as chargeTypes>
						<#list chargeTypes as type>
							<#if charges[i].get( type )??>
					<Cell ss:StyleID="td"><Data ss:Type="Number">${charges[i].get( type )?string("0.00")}</Data></Cell>
							<#else>
					<Cell ss:StyleID="td"><Data ss:Type="String">/</Data></Cell>
							</#if>
						</#list>					
					</#list>
				</Row>				  
				</#list>
				<Row>
					<Cell ss:MergeAcross="${bookingNoteHeader.size() - 1}" ss:StyleID="td-total"><Data ss:Type="String">TOTAL：</Data></Cell>
					<#assign chargeData = charges[count].values() />
					<#list chargeData as charge>
					<Cell ss:StyleID="td"><Data ss:Type="Number">${charge?string("0.00")}</Data></Cell>
					</#list>
				</Row>
				<Row />	<#-- 空行 -->
				<#list chargeHeaderArray as currencyName>
				<Row>
					<#if currencyName_index == 0>
					<Cell ss:StyleID="td-summary" ss:MergeDown="${chargeHeader.keySet().size() - 1}"><Data ss:Type="String">合计：</Data></Cell>
					</#if>
					<Cell ss:StyleID="td-summary"<#if ( currencyName_index > 0 )> ss:Index="2"</#if>><Data ss:Type="String">${currencyName}：</Data></Cell>
					<Cell ss:StyleID="td-summary"><Data ss:Type="Number">${charges[count].get( currencyName + "-小计" )?string("0.00")}</Data></Cell>
				</Row>
				</#list>
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