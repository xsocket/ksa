<#--
/*
 * 兼容 bootstrap 样式的 action message 输出。
 */
-->
<#if (actionMessages?? && actionMessages?size > 0 && !parameters.isEmptyList)>
<div<#rt/>
<#if parameters.cssClass??>
 class="alert alert-success ${parameters.cssClass?html}"<#rt/>
<#else>
 class="alert alert-success actionMessage"<#rt/>
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
 class="actionMessage"<#rt/>
</#if>
<#if parameters.cssStyle??>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
>
		<#list actionMessages as message>
            <#if message?if_exists != "">
                <li><span>${message!}</span></li>
            </#if>
		</#list>
	</ul>
</div>
</#if>