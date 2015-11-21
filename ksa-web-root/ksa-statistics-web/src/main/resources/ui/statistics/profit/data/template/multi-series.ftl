<#if model.categories?? && ( model.categories?size > 0 )>
<categories>
	<#list model.categories as category>
	<category label='${category.label!''}'/>
	</#list>
</categories>
<#if model.datasets?? && ( model.datasets?size > 0 )>
<#list model.datasets as dataset>
<dataset seriesName='${dataset.label!''}'>
	<#list model.categories as category>
	<set value='<#if dataset.get( category.label )??>${dataset.get( category.label ).value}<#else>0</#if>' />
	</#list>
</dataset>
</#list>
</#if>
</#if>