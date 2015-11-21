<#-- 对账单 Excel 2003 模板 -->
<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet" xmlns:o="urn:schemas-microsoft-com:office:office"
	xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:html="http://www.w3.org/TR/REC-html40">
	<DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
		<LastPrinted />
		<#-- 对账单创建时间 -->
		<Created><#if model.customsDate??>${model.customsDate?string("yyyy-MM-ddTHH:mm:ssZ")}</#if></Created>
		<LastSaved />
		<Version>11.6568</Version><#-- Excel2003 版本 -->
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
	</Styles>
	<Worksheet ss:Name="对账单">
		<Table ss:ExpandedColumnCount="12" ss:ExpandedRowCount="30" x:FullColumns="1" x:FullRows="1" ss:StyleID="Default" ss:DefaultColumnWidth="75" ss:DefaultRowHeight="15">
			<#if rows?? && rows?size > 0>
				<#list rows as row>
				<Row<#if row.styleId??> ss:StyleID="${row.styleId}"</#if><#if row.autoFitHeight??> ss:AutoFitHeight="${row.autoFitHeight}"</#if><#if row.height??> ss:Height="${row.height}"</#if>>
					<#if row.cells?? && row.cells?size > 0>
						<#list row.cells as cell>
						<Cell<#if cell.styleId??> ss:StyleID="${cell.styleId}"</#if><#if cell.mergeDown??> ss:MergeDown="${cell.mergeDown}"</#if><#if cell.mergeAcross??> ss:MergeAcross="${cell.mergeAcross}"</#if>>
							<Data<#if cell.type??> ss:Type="${row.type}"</#if>>${cell.value!}</Data>
						</Cell>
						</#list>
					</#if>
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
