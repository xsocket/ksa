<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/shiro-tags" prefix="shiro" %><%-- 自定义扩展后的 Shiro 标签 --%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner"><%-- 通用主导航菜单 - 顶部菜单栏 --%>
		<div class="container-fluid">
			<a class="brand" href="#" style="padding:5px 0;margin-right:5px;"><img src="<s:url value="/rs/images/logo-banner.png" />" alt="杭州凯思爱物流管理系统"></a><%-- 系统标题 --%>
			<div class="nav-collapse">
				<ul class="nav">
					<li id="_menu_index"><a href="/index.jsp"><i class="icon-home"></i> 首页</a></li>
					<shiro:hasAnyPermissions name="bookingnote:edit:view,bookingnote:viewall">
					<li id="_menu_logistics" class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-file"></i> 托单管理<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="<s:url namespace="/ui/logistics" action="default" />">托单管理</a></li>
							<li class="divider"></li>
							<li><a href="<s:url namespace="/ui/logistics" action="return-notify" />">退单管理</a></li>
						</ul>
					</li>
					</shiro:hasAnyPermissions>
					<shiro:hasAnyPermissions name="finance:charge,finance:invoice,finance:account1">
					<li id="_menu_income" class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">收入管理<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<shiro:hasPermission name="finance:charge">
							<li class="nav-header">费用管理</li>
							<li><a href="<s:url value="/ui/finance/charge/default.action" />">费用输入/查询</a></li>
							<li><a href="<s:url value="/ui/finance/charge/checking.action" />">费用审核</a></li>
							<li class="divider"></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="finance:account1">
							<li class="nav-header">结算单管理</li>
							<li><a href="<s:url value="/ui/finance/account/default.action" />">结算单管理</a></li>
							<li><a href="<s:url value="/ui/finance/account/default-doinvoice.action?params.ACCOUNT_STATE=1" />">账单审核</a></li>
							<li><a href="<s:url value="/ui/finance/account/default-doinvoice.action?params.ACCOUNT_STATE=2" />">账单开票</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="finance:invoice">
							<li class="divider"></li>
							<li class="nav-header">发票管理</li>
							<li><a href="<s:url value="/ui/finance/invoice/default.action?direction=-1" />">发票管理</a></li>
							</shiro:hasPermission>
						</ul>
					</li>
					</shiro:hasAnyPermissions>
					<shiro:hasAnyPermissions name="finance:charge,finance:invoice,finance:account-1">
					<li id="_menu_expense" class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">支出管理<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<shiro:hasPermission name="finance:charge">
							<li class="nav-header">费用管理</li>
							<li><a href="<s:url value="/ui/finance/charge/default.action?nature=1" />">费用输入/查询</a></li>
							<li><a href="<s:url value="/ui/finance/charge/checking.action?nature=1" />">费用审核</a></li>
							<li class="divider"></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="finance:account-1">
							<li class="nav-header">对账单管理</li>
							<li><a href="<s:url value="/ui/finance/account/default.action?direction=-1" />">对账单管理</a></li>
							<li><a href="<s:url value="/ui/finance/account/default-doinvoice.action?direction=-1&params.ACCOUNT_STATE=2" />">对账单审核</a></li>
							<li><a href="<s:url value="/ui/finance/account/default-doinvoice.action?direction=-1&params.ACCOUNT_STATE=8" />">账单付款</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="finance:invoice">
							<li class="divider"></li>
							<li class="nav-header">发票管理</li>
							<li><a href="<s:url value="/ui/finance/invoice/default.action?direction=1" />">发票管理</a></li>
							</shiro:hasPermission>
						</ul>
					</li>
					</shiro:hasAnyPermissions>
					<%-- 境外费用与境内费用合并管理
					<shiro:hasAnyPermissions name="finance:charge,finance:invoice,finance:account1,finance:account-1">
					<li id="_menu_nature" class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">境外费用管理<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<shiro:hasPermission name="finance:charge">
							<li class="nav-header">费用管理</li>
							<li><a href="<s:url value="/ui/finance/charge/default.action?nature=-1" />">费用输入/查询</a></li>
							<li><a href="<s:url value="/ui/finance/charge/checking.action?nature=-1" />">费用审核</a></li>
							<li class="divider"></li>
							</shiro:hasPermission>
							<shiro:hasAnyPermissions name="finance:account1,finance:account-1">
							<li class="nav-header">账单管理</li>
							<li><a href="<s:url value="/ui/finance/account/default.action?nature=-1" />">账单管理</a></li>
							<li><a href="<s:url value="/ui/finance/account/default.action?nature=-1&params.ACCOUNT_STATE=2" />">账单审核</a></li>
							<li><a href="<s:url value="/ui/finance/account/default.action?nature=-1&params.ACCOUNT_STATE=8" />">账单付款</a></li>
							</shiro:hasAnyPermissions>
							<!-- 境外费用暂时关闭发票管理
							<shiro:hasPermission name="finance:invoice">
							<li class="divider"></li>
							<li class="nav-header">发票管理</li>
							<li><a href="<s:url namespace="/ui/finance/invoice" action="default" />">发票管理</a></li>
							</shiro:hasPermission>
							 -->
						</ul>
					</li>
					</shiro:hasAnyPermissions>
					 --%>
					<shiro:hasAnyPermissions name="statistics:profit,statistics:cargo">
					<li id="_menu_profit" class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-th"></i> 业务统计<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<shiro:hasPermission name="statistics:profit">
							<li><a href="<s:url namespace="/ui/statistics/profit" action="default" />">利润统计</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="statistics:cargo">
							<li><a href="<s:url namespace="/ui/statistics/cargo" action="default" />">货物/箱量统计</a></li>
							</shiro:hasPermission>
						</ul>
					</li>
					</shiro:hasAnyPermissions>
					<shiro:hasAnyPermissions name="bd:partner,bd:data,bd:currency">
					<li id="_menu_bd" class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-book"></i> 信息管理<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<shiro:hasPermission name="bd:partner">
							<li><a href="<s:url namespace="/ui/bd/partner" action="default" />">合作伙伴管理</a></li>
							<li class="divider"></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="bd:data">
							<li id="_menu_bd_data"><a href="<s:url namespace="/ui/bd/data" action="default" />"> 基本代码管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="bd:currency">
							<li id="_menu_bd_currency"><a href="<s:url namespace="/ui/bd/currency" action="default" />"> 货币汇率管理</a></li>
							</shiro:hasPermission>
						</ul>
					</li>			
					</shiro:hasAnyPermissions>
					<shiro:hasAnyPermissions name="security:user,security:role,system:backup">
					<li id="_menu_system" class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-cog"></i> 系统配置<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<shiro:hasAnyPermissions name="system:backup">
							<li class="nav-header">数据管理</li>
							</shiro:hasAnyPermissions>
							<shiro:hasPermission name="system:backup">
							<li id="_menu_system_backup"><a href="<s:url namespace="/ui/system/backup" action="default" />"> 数据备份/还原</a></li>
							</shiro:hasPermission>
							<shiro:hasAnyPermissions name="security:user,security:role">
							<li class="nav-header">安全配置</li>
							</shiro:hasAnyPermissions>
							<shiro:hasPermission name="security:user">
							<li id="_menu_system_user"><a href="<s:url namespace="/ui/security/user" action="default" />"> 用户管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="security:role">
							<li id="_menu_system_role"><a href="<s:url namespace="/ui/security/role" action="default" />"> 角色管理</a></li>
							</shiro:hasPermission>
						</ul>
					</li>
					</shiro:hasAnyPermissions>
				</ul>
				
				<script type="text/javascript">
					function CHANGE_PASSWORD() {
					    var cp = window.APPLICATION_CONTEXTPATH || "";
					    top.$.open( {
			                src : cp + "/dialog/security/user/edit-password.action",
			                title : "修改密码", width:500, height:300
			            } );
					}
				</script>
				<ul class="nav pull-right" style="margin-right:-10px;">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-user"></i> 
							<%= com.ksa.service.security.util.SecurityUtils.getCurrentUser().getName() %><b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a alt="修改密码" href="javascript:void(0);" onclick="CHANGE_PASSWORD();"><i class="icon-tag"></i> 修改密码</a></li>
							<li class="divider"></li>
							<li><a alt="退出系统" href="<s:url value="/logout.jsp" />"><i class="icon-off"></i> 退出系统</a></li>
						</ul>
					</li>
				</ul>
			</div> <%-- /.nav-collapse --%>
		</div> <%-- /.container --%>
	</div> <%-- /.navbar-inner --%>
</div> <%-- /.navbar --%>