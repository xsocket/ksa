# 杭州凯思爱物流管理系统


## 变更日志：

### v3.8.5
- 修复版本v3.8.4托单数据过滤未过滤“退单管理”页面的问题；
- 另外加入五种业务类型：KB-捆包业务、RH-内联行、CC-仓储业务、BC-搬场业务、TL-公铁联运

### v3.8.4
- 修复版本v3.8.0托单查看的bug，基本的托单查看权限只允许查看自己创建或负责销售的托单；

### v3.8.3
- 改进费用信息表格：默认显示【备注】列；
 
### v3.8.2
- 解决了导出`面单`中费用明细最多显示20条的限制；

### v3.8.1
- 更新了`结算单`中单位的**名称**和**联系地址**；

### v3.8.0
- 改进了托单查看的权限控制，新增了`仅查看个人业务`的权限；
- 涉及到了相应数据库的变更：
 
```
-- 增加新的权限数据
insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bookingnote:viewall', 	'托单查看-全部', '可以查看所有的业务托单，但是并没有编辑的权限。' );
-- 更新原权限的名称和说明
update KSA_SECURITY_PERMISSION set NAME	= '托单查看-个人', DESCRIPTION = '仅可以查看个人创建的业务托单，其他业务托单无权查看。' where ID = 'bookingnote:edit:view';

-- 将新的权限赋予相应的角色: 操作主管、财务、财务主管、经理、系统管理员
insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'bookingnote:viewall' );
insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant', 'bookingnote:viewall' );
insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'bookingnote:viewall' );
insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'bookingnote:viewall' );
insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'bookingnote:viewall' );
```

### v3.7.9
- 更新了利润统计图的展示方式；