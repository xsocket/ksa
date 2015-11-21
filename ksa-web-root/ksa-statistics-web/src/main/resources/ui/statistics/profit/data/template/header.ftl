<chart <#if model.title??>caption='${model.title!''}'</#if> palette='2' useRoundEdges='1'<#rt/> 
 decimals='2' numberScaleValue='10000,10000' numberScaleUnit='万,亿'<#rt/>
 baseFontSize='10' outCnvBaseFontSize='12' outCnvBaseFont='微软雅黑,Arial'
 yAxisName='PROFIT ( RMB )' <#if model.labelX??>xAxisName='${model.labelX!''}'</#if>><#rt/>