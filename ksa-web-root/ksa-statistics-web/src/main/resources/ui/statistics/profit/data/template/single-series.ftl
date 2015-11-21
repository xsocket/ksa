<#if model.datasets?? && ( model.datasets?size > 0 ) && model.categories?? && ( model.categories?size > 0 )>
<#list model.datasets as dataset>
<#if dataset.label == "人民币"><#-- 单系列的图仅仅统计人民币 -->
<#list model.categories as category>
	<#if dataset.get( category.label )??>
		<set label='${category.label!}'  value='${dataset.get( category.label ).value}' />
	</#if>
</#list>
</#if>
</#list>
</#if>