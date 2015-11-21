<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Created>2006-09-16T00:00:00Z</Created>
  <LastSaved>2013-03-13T09:12:24Z</LastSaved>
  <Version>14.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
  <RemovePersonalInformation/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>8010</WindowHeight>
  <WindowWidth>14805</WindowWidth>
  <WindowTopX>240</WindowTopX>
  <WindowTopY>105</WindowTopY>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s62">
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="12" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s64">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="22" ss:Color="#000000"
    ss:Bold="1"/>
  </Style>
  <Style ss:ID="s65">
   <Alignment ss:Horizontal="Right" ss:Vertical="Top"/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="12" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s67">
   <Alignment ss:Horizontal="Left" ss:Vertical="Top"/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="12" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s68">
   <Alignment ss:Vertical="Top"/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="12" ss:Color="#000000"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="进仓通知单">
  <Table ss:ExpandedColumnCount="10" ss:ExpandedRowCount="30" x:FullColumns="1"
   x:FullRows="1" ss:StyleID="s62" ss:DefaultColumnWidth="54"
   ss:DefaultRowHeight="14.25">
   <Column ss:StyleID="s62" ss:AutoFitWidth="0" ss:Width="32.25"/>
   <Column ss:StyleID="s62" ss:AutoFitWidth="0" ss:Width="60"/>
   <Column ss:Index="5" ss:StyleID="s62" ss:AutoFitWidth="0" ss:Width="45"/>
   <Column ss:StyleID="s62" ss:AutoFitWidth="0" ss:Width="60"/>
   <Column ss:Index="8" ss:StyleID="s62" ss:AutoFitWidth="0" ss:Width="45"/>
   <Row ss:AutoFitHeight="0" ss:Height="22.5" ss:Span="1"/>
   <Row ss:Index="3" ss:AutoFitHeight="0" ss:Height="18.75">
    <Cell><Data ss:Type="String">代理名称：凯思爱(杭州)物流有限公司</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="18.75">
    <Cell><Data ss:Type="String">地址：杭州市文二路391号西湖国际科技大厦B4-915</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="18.75">
    <Cell><Data ss:Type="String">TEL：${model.salerTel!"88473507"}</Data></Cell>
    <Cell ss:Index="4"><Data ss:Type="String">FAX：${model.salerFax!"88473508"}</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="18.75">
    <Cell><Data ss:Type="String">EMAIL：${model.salerEmail!"hangzhou@ksa.co.jp"}</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="18.75">
    <Cell><Data ss:Type="String">客户服务代表：</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="18.75">
    <Cell ss:Index="2"><Data ss:Type="String">${model.saler!}</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="60">
    <Cell ss:MergeAcross="9" ss:StyleID="s64"><Data ss:Type="String">进仓通知单</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="18.75">
    <Cell ss:StyleID="s65"><Data ss:Type="String">TO：</Data></Cell>
    <Cell ss:MergeAcross="7" ss:MergeDown="2" ss:StyleID="s67"><Data
      ss:Type="String">${model.to!}</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="18.75" ss:Span="2"/>
   <Row ss:Index="14" ss:AutoFitHeight="0" ss:Height="22.5">
    <Cell ss:Index="2"><Data ss:Type="String">进仓编号：</Data></Cell>
    <Cell><Data ss:Type="String">${model.code!}</Data></Cell>
    <Cell ss:Index="6"><Data ss:Type="String">通知时间：</Data></Cell>
    <Cell><Data ss:Type="String">${model.createdDate!}</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="22.5">
    <Cell ss:Index="2"><Data ss:Type="String">品名：</Data></Cell>
    <Cell><Data ss:Type="String">${model.cargoName!}</Data></Cell>
    <Cell ss:Index="6"><Data ss:Type="String">委托件数：</Data></Cell>
    <Cell><Data ss:Type="String">${model.cargoQuantity!}</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="22.5">
    <Cell ss:Index="2"><Data ss:Type="String">委托毛重：</Data></Cell>
    <Cell><Data ss:Type="String">${model.cargoWeight!}</Data></Cell>
    <Cell ss:Index="6"><Data ss:Type="String">委托体积：</Data></Cell>
    <Cell><Data ss:Type="String">${model.cargoVolumn!}</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="22.5">
    <Cell ss:Index="2"><Data ss:Type="String">客户名：</Data></Cell>
    <Cell><Data ss:Type="String">${model.customer!}</Data></Cell>
    <Cell ss:Index="6"><Data ss:Type="String">起运港：</Data></Cell>
    <Cell><Data ss:Type="String">${model.departurePort!}</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="22.5">
    <Cell ss:Index="2"><Data ss:Type="String">卸货港：</Data></Cell>
    <Cell><Data ss:Type="String">${model.dischargePort!}</Data></Cell>
    <Cell ss:Index="6"><Data ss:Type="String">目的地：</Data></Cell>
    <Cell><Data ss:Type="String">${model.destination!}</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="22.5">
    <Cell ss:Index="2"><Data ss:Type="String">船名航次：</Data></Cell>
    <Cell><Data ss:Type="String">${model.vesselVoyage!}</Data></Cell>
    <Cell ss:Index="6"><Data ss:Type="String">开航日：</Data></Cell>
    <Cell><Data ss:Type="String">${model.departureDate!}</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="22.5">
    <Cell ss:Index="2"><Data ss:Type="String">提单号：</Data></Cell>
    <Cell><Data ss:Type="String">${model.mawb!}</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="22.5"/>
   <Row ss:AutoFitHeight="0" ss:Height="22.5">
    <Cell ss:Index="2"><Data ss:Type="String">贵司以上委托之货物请最晚于</Data></Cell>
    <Cell ss:Index="5"><Data ss:Type="String">${model.entryDate!}</Data></Cell>
    <Cell ss:Index="7"><Data ss:Type="String">前进仓！</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="22.5">
    <Cell ss:Index="2"><Data ss:Type="String">　若委托我司报关，请最晚于</Data></Cell>
    <Cell ss:Index="5"><Data ss:Type="String">${model.informDate!}</Data></Cell>
    <Cell ss:Index="7"><Data ss:Type="String">前将报送单证送抵我司！</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="30"/>
   <Row ss:AutoFitHeight="0" ss:Height="22.5">
    <Cell ss:Index="2" ss:StyleID="s68"><Data ss:Type="String">进仓地址：</Data></Cell>
    <Cell ss:MergeAcross="6" ss:MergeDown="2" ss:StyleID="s67"><Data
      ss:Type="String">${model.address!}</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="22.5" ss:Span="2"/>
   <Row ss:Index="29" ss:AutoFitHeight="0" ss:Height="22.5">
    <Cell ss:Index="2"><Data ss:Type="String">联系人：</Data></Cell>
    <Cell><Data ss:Type="String">${model.contact!}</Data></Cell>
    <Cell ss:Index="5"><Data ss:Type="String">电话：</Data></Cell>
    <Cell><Data ss:Type="String">${model.telephone!}</Data></Cell>
    <Cell ss:Index="8"><Data ss:Type="String">传真：</Data></Cell>
    <Cell><Data ss:Type="String">${model.fax!}</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:Height="18.75"/>
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <Unsynced/>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>1200</HorizontalResolution>
    <VerticalResolution>1200</VerticalResolution>
   </Print>
   <Selected/>
   <TopRowVisible>6</TopRowVisible>
   <Panes>
    <Pane>
     <Number>3</Number>
     <ActiveRow>7</ActiveRow>
     <ActiveCol>3</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
