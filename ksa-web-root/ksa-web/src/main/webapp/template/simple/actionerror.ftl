<#--
/*
 * 兼容 bootstrap 样式的 action error 输出。
 */
-->
<#if (actionErrors?? && actionErrors?size > 0)>
<div <#rt/>
<#if parameters.cssClass??>
 class="alert alert-error ${parameters.cssClass?html}"<#rt/>
<#else>
 class="alert alert-error errorMessage"<#rt/>
</#if>
>
	<button type="button" class="close" data-dismiss="alert">×</button>
	<ul<#rt/>
<#if parameters.id?if_exists != "">
 id="${parameters.id?html}"<#rt/>
</#if>            
<#if parameters.cssClass??>
 class="${parameters.cssClass?html}"<#rt/>
<#else>
 class="errorMessage"<#rt/>
</#if>
<#if parameters.cssStyle??>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
>
	<#list actionErrors as error>
		<#if error?if_exists != "">
            <li><span>${error!}</span><#rt/></li><#rt/>
        </#if>
	</#list>
	</ul>
</div>
</#if>