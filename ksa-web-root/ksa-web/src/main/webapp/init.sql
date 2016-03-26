-- ----------------------- SECURITY -------------------------

-- 创建表 - 用户表
create table KSA_SECURITY_USER (
    ID					varchar(36)		not null		comment  '用户标识' ,
    NAME			varchar(200)		not null		comment  '用户姓名' ,
    PASSWORD	varchar(200)		not null		comment  '登录密码' ,
    EMAIL			varchar(200)		not null		comment  '用户邮箱' ,
    TELEPHONE	varchar(200)		not null		comment  '用户电话' ,
    IS_LOCKED	int(1) default 0	not null		comment  '是否锁定' ,
    primary key ( ID )
);

		insert into KSA_SECURITY_USER ( ID, NAME, PASSWORD, EMAIL, TELEPHONE ) values ( 'administrator', '系统管理员', 'fEqNCco3Yq9h5ZUglD3CZJT4lBs=', '', '');

-- 创建表 - 角色表
create table KSA_SECURITY_ROLE (
	ID						varchar(36)		not null		comment  '角色标识' ,
    NAME				varchar(200)		not null		comment  '角色名称' ,
    DESCRIPTION	varchar(2000)	not null		comment  '角色描述' ,
    primary key ( ID )
);	

		insert into KSA_SECURITY_ROLE ( ID, NAME, DESCRIPTION ) values ( 'administrator', '系统管理员', '负责系统的整体运行与维护。' );
		insert into KSA_SECURITY_ROLE ( ID, NAME, DESCRIPTION ) values ( 'manager', '总经理', '负责全体业务的管理。' );
		insert into KSA_SECURITY_ROLE ( ID, NAME, DESCRIPTION ) values ( 'operator', '操作员', '负责基本业务数据的管理。' );
		insert into KSA_SECURITY_ROLE ( ID, NAME, DESCRIPTION ) values ( 'operator-supervisor', '操作主管', '负责全体业务数据的管理及基本费用数据的录入。' );
		insert into KSA_SECURITY_ROLE ( ID, NAME, DESCRIPTION ) values ( 'accountant', '财务', '负责基本财务数据的管理。' );
		insert into KSA_SECURITY_ROLE ( ID, NAME, DESCRIPTION ) values ( 'accountant-supervisor', '财务主管', '负责全体财务数据的管理。' );

-- 创建表 - 权限表
create table KSA_SECURITY_PERMISSION (
	ID						varchar(200)		not null		comment  '权限标识' ,
    NAME				varchar(200)		not null		comment  '权限名称' ,
    DESCRIPTION	varchar(2000)	not null		comment  '权限描述' ,
    primary key ( ID )
);	

    insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bookingnote:share:gz', '托单共享-广州', '将广州事务所的托单共享出来，供有权限的人员查看和编辑。' );
    insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bookingnote:viewshare:gz', '托单查看-广州共享', '查看广州事务所共享出来的托单。' );
    insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bookingnote:viewall',   '托单查看-全部', '可以查看所有的业务托单，但是并没有编辑的权限。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bookingnote:edit:view', 	'托单查看-个人', '可以查看所有的业务托单，但是并没有编辑的权限。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bookingnote:print', 		'托单打印', '可以打印提单、订舱通知等业务相关的文档。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bookingnote:edit', 		'托单编辑', '可以新增和修改所有的业务托单，但是没有删除的权限。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bookingnote:delete', 		'托单删除', '可以删除托单（并非真正删除，而是将托单置于已删除状态）。' );		
		
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'finance:charge-check', 	'费用审核', '可以对提交的业务费用进行审核操作。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'finance:charge', 			'费用管理', '可以使用【财务管理】功能的【费用管理】子功能。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'finance:invoice',				'发票管理', '可以使用【财务管理】功能的【发票管理】子功能。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'finance:account1',			'结算单管理', '可以使用【财务管理】功能的【结算单管理】子功能。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'finance:account-1',		'对账单管理', '可以使用【财务管理】功能的【对账单管理】子功能。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'finance:account-check','结算/对账单审核', '可以对提交的结算/对账单进行审核操作，审核通过后单据进入收款/付款状态。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'finance:account-settle',	'结算/对账单结算', '可以对提交的结算/对账单进行结算完毕操作，结算完毕的结算单不能进行任何修改' );
		
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'statistics:profit', 			'统计利润', '可以使用【业务统计】功能的【利润统计】子功能。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'statistics:cargo', 			'统计箱量', '可以使用【业务统计】功能的【箱量统计】子功能。' );
		
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bd:partner', 					'合作伙伴管理', '可以使用【信息管理】功能的【合作伙伴管理】子功能。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bd:data', 						'基本代码管理', '可以使用【信息配置】功能的【基本代码管理】子功能。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bd:currency',					'货币汇率管理', '可以使用【信息配置】功能的【货币汇率管理】子功能。' );

		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'security:user', 				'用户管理', '可以使用【系统配置】功能的【用户管理】子功能。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'security:role', 				'角色管理', '可以使用【系统配置】功能的【角色管理】子功能。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'security:permission', 		'权限管理', '可以使用【系统配置】功能的【权限管理】子功能。' );
		
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'system:backup', 			'数据备份管理', '可以使用【系统配置】功能的【数据备份管理】子功能。' );

		
-- 创建表 - 用户角色关联表
create table KSA_SECURITY_USERROLE (
  USER_ID varchar(36) not null	 comment '用户标识:外键关联 KSA_SECURITY_USER 表中的 ID 字段' ,
  ROLE_ID varchar(36) not null	 comment '角色标识:外键关联 KSA_SECURITY_ROLE 表中的 ID 字段' ,
  
  primary key (USER_ID, ROLE_ID) ,
  
  constraint FK_KSA_SECURITY_USERROLE
    foreign key ( USER_ID )
    references KSA_SECURITY_USER ( ID )
    on delete cascade
    on update cascade ,
    
  constraint FK_KSA_SECURITY_ROLEUSER
    foreign key ( ROLE_ID )
    references KSA_SECURITY_ROLE ( ID )
    on delete cascade
    on update cascade
);

		insert into KSA_SECURITY_USERROLE ( USER_ID, ROLE_ID ) values ( 'administrator', 'administrator' );


-- 创建表 - 角色权限关联表
CREATE TABLE KSA_SECURITY_ROLEPERMISSION (
  ROLE_ID 			varchar(36) not null	 comment '角色标识:外键关联 KSA_SECURITY_ROLE 表中的 ID 字段' ,
  PERMISSION_ID	varchar(36) not null	 comment '权限标识:外键关联 KSA_SECURITY_PERMISSION 表中的 ID 字段' ,
  
  primary key ( ROLE_ID, PERMISSION_ID ) ,
  
  constraint FK_KSA_SECURITY_ROLEPERMISSION
    foreign key ( ROLE_ID )
    references KSA_SECURITY_ROLE ( ID )
    on delete cascade
    on update cascade ,
    
  constraint FK_KSA_SECURITY_PERMISSIONROLE
    foreign key ( PERMISSION_ID )
    references KSA_SECURITY_PERMISSION ( ID )
    on delete cascade
    on update cascade
);
		-- administrator 权限
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'bookingnote:edit:view' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'bookingnote:print' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'bookingnote:edit' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'bookingnote:delete' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'finance:charge-check' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'finance:charge' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'finance:invoice' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'finance:account1' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'finance:account-1' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'finance:account-check' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'finance:account-settle' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'statistics:profit' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'statistics:cargo' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'bd:partner' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'bd:data' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'bd:currency' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'security:user' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'security:role' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'security:permission' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'system:backup' );
		
		-- manager 权限
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'bookingnote:edit:view' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'bookingnote:print' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'bookingnote:edit' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'bookingnote:delete' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'finance:charge-check' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'finance:charge' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'finance:invoice' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'finance:account1' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'finance:account-1' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'finance:account-check' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'finance:account-settle' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'statistics:profit' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'statistics:cargo' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'bd:partner' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'bd:data' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'bd:currency' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'security:user' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'security:role' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'security:permission' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'system:backup' );
		
		-- operator 权限
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator', 'bookingnote:edit:view' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator', 'bookingnote:print' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator', 'bookingnote:edit' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator', 'bd:partner' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator', 'bd:data' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator', 'bd:currency' );
		
		-- operator-supervisor 权限
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'bookingnote:edit:view' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'bookingnote:print' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'bookingnote:edit' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'bookingnote:delete' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'finance:charge' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'finance:invoice' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'finance:account1' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'finance:account-1' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'statistics:cargo' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'bd:partner' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'bd:data' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'bd:currency' );
		
		-- accountant 权限
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant', 'bookingnote:edit:view' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant', 'bookingnote:print' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant', 'bookingnote:edit' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant', 'finance:charge' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant', 'finance:invoice' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant', 'finance:account1' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant', 'finance:account-1' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant', 'bd:partner' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant', 'bd:data' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant', 'bd:currency' );
		
		-- accountant-supervisor 权限
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'bookingnote:edit:view' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'bookingnote:print' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'bookingnote:edit' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'finance:charge-check' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'finance:charge' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'finance:invoice' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'finance:account1' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'finance:account-1' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'finance:account-check' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'finance:account-settle' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'statistics:profit' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'bd:partner' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'bd:data' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'bd:currency' );

    -- view-all 权限
    insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'operator-supervisor', 'bookingnote:viewall' );
    insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant', 'bookingnote:viewall' );
    insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'accountant-supervisor', 'bookingnote:viewall' );
    insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'manager', 'bookingnote:viewall' );
    insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'administrator', 'bookingnote:viewall' );
		
		
-- ----------------------- BD -------------------------		
		
-- 创建表 - 基础数据类型
create table KSA_BD_TYPE (
	ID			varchar(36)		not null		comment  '数据类型标识',
  	NAME	varchar(200)		not null		comment  '数据类型名称',
  	primary key ( ID )
);

	insert into KSA_BD_TYPE ( ID, NAME ) values ( '00-currency', 				'结算货币' );
	insert into KSA_BD_TYPE ( ID, NAME ) values ( '01-units', 						'数量单位' );
	insert into KSA_BD_TYPE ( ID, NAME ) values ( '08-vehicle', 					'车辆类型' );
	--	insert into KSA_BD_TYPE ( ID, NAME ) values ( '09-custom', 					'报关类型' );
	insert into KSA_BD_TYPE ( ID, NAME ) values ( '10-charge', 					'费用类型' );
	-- insert into KSA_BD_TYPE ( ID, NAME ) values ( '11-trade-clause',			'贸易条款' );
	-- insert into KSA_BD_TYPE ( ID, NAME ) values ( '12-freight-clause',		'运费条款' );
	-- insert into KSA_BD_TYPE ( ID, NAME ) values ( '13-surcharge-clause', 	'附加费条款' );
	insert into KSA_BD_TYPE ( ID, NAME ) values ( '20-department', 			'单位类型' );
	insert into KSA_BD_TYPE ( ID, NAME ) values ( '30-state', 						'国家地区' );
	insert into KSA_BD_TYPE ( ID, NAME ) values ( '31-port-sea', 				'海运港口' );
	insert into KSA_BD_TYPE ( ID, NAME ) values ( '32-port-air', 				'空运港口' );
	insert into KSA_BD_TYPE ( ID, NAME ) values ( '33-route-sea',				'海运航线' );
	insert into KSA_BD_TYPE ( ID, NAME ) values ( '34-route-air',				'空运航线' );


-- 创建表 - 基础数据
create table KSA_BD_DATA (
	ID				varchar(36)		not null		comment  '标识',
	CODE		varchar(200)		not null		comment  '编码',
  	NAME		varchar(200)		not null		comment  '名称',
  	ALIAS		varchar(200)		not null		comment  '别名',
  	NOTE 		varchar(2000)	not null		comment  '备注',
  	EXTRA		varchar(200)		not null		comment  '附加属性',
  	TYPE_ID 	varchar(36)		not null		comment  '类型标识',
  	RANK		int					not null		comment  '排序',
  	primary key ( ID ) 
);

	-- 币种
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '00-currency-RMB', 'RMB', 	'人民币', 	'', '', 	'1.000', 	'00-currency', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '00-currency-USD', 'USD', 	'美元', 		'', '', 	'6.800', 	'00-currency', 2 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '00-currency-JPY', 	 'JPY', 	'日元', 		'', '', 	'0.075', 	'00-currency', 3 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '00-currency-HKD', 'HKD', 	'港币', 		'', '', 	'0.882', 	'00-currency', 4 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '00-currency-EUR',  'EUR', 	'欧元', 		'', '', 	'10.000', 	'00-currency', 5 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '00-currency-TWD', 'TWD', '台币', 		'', '', 	'0.200', 	'00-currency', 6 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '00-currency-KRW', 'KRW', 	'韩元', 		'', '', 	'0.005', 	'00-currency', 7 );
	-- 数量单位
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '01-units-0', 'CASE', 	'箱', 		'', '', '', '01-units', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '01-units-1', 'CTNS', 	'纸箱', 	'', '', '', '01-units', 2 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '01-units-2', 'PLTS', 	'托盘', 	'', '', '', '01-units', 3 );
	-- 报关
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '09-custom-0', 'QG', 		'清关', 		'', '', '', '09-custom', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '09-custom-1', 'ZG', 		'转关', 		'', '', '', '09-custom', 2 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '09-custom-2', 'WGQ', 	'外高桥', 	'', '', '', '09-custom', 3 );
	-- 费用类型
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-001', 'ABF', 	'安保费', '', '', '', '10-charge', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-002', 'AFC',	'Air/Ocean Freight Charge', '', '', '', '10-charge', 2 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-003', 'AWC',	'Air Waybill Charge(AWC)', '', '', '', '10-charge', 3 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-004', 'BAF',	'BAF', '', '', '', '10-charge', 4 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-005', 'BDF',	'并单费', '', '', '', '10-charge', 5 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-006', 'BGF1',	'包干费', '', '', '', '10-charge', 6 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-007', 'BGF2',	'报关费', '', '', '', '10-charge', 7 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-008', 'BHF',	'驳货费', '', '', '', '10-charge', 8 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-009', 'BSCC',	'保税仓储费', '', '', '', '10-charge', 9 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-010', 'BXF',	'保险费', '', '', '', '10-charge', 10 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-011', 'BZF',	'包装费', '', '', '', '10-charge', 11 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-012', 'BZF1',	'办证费', '', '', '', '10-charge', 12 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-013', 'CCF',	'仓储费', '', '', '', '10-charge', 13 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-014', 'CCF1',	'铲车费', '', '', '', '10-charge', 14 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-015', 'CCFGW','车船费(国外)', '', '', '', '10-charge', 15 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-016', 'CCFWN','车船费(国内)', '', '', '', '10-charge', 16 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-017', 'CDF',	'仓单费', '', '', '', '10-charge', 17 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-018', 'CFS',	'CFS Charge', '', '', '', '10-charge', 18 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-019', 'CGF',	'冲关费', '', '', '', '10-charge', 19 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-020', 'CJF',	'磁检费', '', '', '', '10-charge', 20 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-021', 'CKBGF','出口报关费', '', '', '', '10-charge', 21 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-022', 'CKCZ',	'仓库操作费', '', '', '', '10-charge', 22 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-023', 'CKXGYWDLF','出口相关业务代理费', '', '', '', '10-charge', 23 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-024', 'CKXKZSQF','出口许可证申请费', '', '', '', '10-charge', 24 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-025', 'CKZF',	'出口杂费', '', '', '', '10-charge', 25 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-026', 'CLF',	'材料费', '', '', '', '10-charge', 26 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-027', 'CXF',	'查询费', '', '', '', '10-charge', 27 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-028', 'CXF1',	'拆箱费', '', '', '', '10-charge', 28 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-029', 'CYF',	'查验费', '', '', '', '10-charge', 29 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-030', 'CYF1',	'检验费(植物)', '', '', '', '10-charge', 30 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-031', 'CYF2',	'检验费(动物)', '', '', '', '10-charge', 31 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-032', 'DBF',	'短驳费', '', '', '', '10-charge', 32 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-033', 'DBF1',	'代办费', '', '', '', '10-charge', 33 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-034', 'DCF1','订仓费', '', '', '', '10-charge', 34 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-035', 'DCF2','堆场费', '', '', '', '10-charge', 35 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-036', 'DDC','DDC', '', '', '', '10-charge', 36 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-037', 'DDF','打单费', '', '', '', '10-charge', 37 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-038', 'DDSXF','代垫手续费', '', '', '', '10-charge', 38 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-039', 'DFF','电放费', '', '', '', '10-charge', 39 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-040', 'DJF','吊机费', '', '', '', '10-charge', 40 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-041', 'DJO','动检场地费', '', '', '', '10-charge', 41 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-042', 'DLF','代理费', '', '', '', '10-charge', 42 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-043', 'DLT','电脑套件', '', '', '', '10-charge', 43 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-044', 'DOC','DOC FEE', '', '', '', '10-charge', 44 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-045', 'DSF','待时费', '', '', '', '10-charge', 45 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-046', 'DTF','打托费', '', '', '', '10-charge', 46 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-047', 'DXF','倒箱费', '', '', '', '10-charge', 47 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-048', 'DZCLF','单证处理费', '', '', '', '10-charge', 48 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-049', 'DZJ','动植检', '', '', '', '10-charge', 49 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-050', 'EDI','EDI付税', '', '', '', '10-charge', 50 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-051', 'FBF','分拨费', '', '', '', '10-charge', 51 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-052', 'FDF','放单费', '', '', '', '10-charge', 52 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-053', 'FDF1','分单费', '', '', '', '10-charge', 53 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-054', 'FJF','附加费', '', '', '', '10-charge', 54 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-055', 'FJF1','返还附加费', '', '', '', '10-charge', 55 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-056', 'FKF','放空费', '', '', '', '10-charge', 56 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-057', 'FWF','服务费', '', '', '', '10-charge', 57 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-058', 'GDF','改单费', '', '', '', '10-charge', 58 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-059', 'GGF','更改费', '', '', '', '10-charge', 59 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-060', 'GGF1','公估费', '', '', '', '10-charge', 60 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-061', 'GJF','港建费', '', '', '', '10-charge', 61 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-062', 'GJTX','国际通信费', '', '', '', '10-charge', 62 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-063', 'GKBA','港口保安费', '', '', '', '10-charge', 63 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-064', 'GKCZ','港口操作费', '', '', '', '10-charge', 64 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-065', 'GS','关税', '', '', '', '10-charge', 65 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-066', 'GZF','港杂费', '', '', '', '10-charge', 66 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-067', 'H/C','Handling Charge(H/C)', '', '', '', '10-charge', 67 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-068', 'HANDLING','HANGLING CHARGE', '', '', '', '10-charge', 68 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-069', 'HCF','核查费', '', '', '', '10-charge', 69 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-070', 'HDF','换单费', '', '', '', '10-charge', 70 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-071', 'HGCY','海关查验交通费', '', '', '', '10-charge', 71 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-072', 'HGJB','海关加班费', '', '', '', '10-charge', 72 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-073', 'HGSBF','海关申报费', '', '', '', '10-charge', 73 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-074', 'HWBYF','货物搬运费', '', '', '', '10-charge', 74 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-075', 'HYF','海运费', '', '', '', '10-charge', 75 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-076', 'JDF','寄单费', '', '', '', '10-charge',76 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-077', 'JGCYF','监管车运费', '', '', '', '10-charge', 77 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-078', 'JGF','监管费', '', '', '', '10-charge', 78 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-079', 'JJB','加急报关费', '', '', '', '10-charge', 79 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-080', 'JJBG','紧急报关费', '', '', '', '10-charge', 80 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-081', 'JJF1','加急费', '', '', '', '10-charge', 81 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-082', 'JJF2','交际费', '', '', '', '10-charge', 82 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-083', 'JKBG','进口报关费', '', '', '', '10-charge', 83 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-084', 'JKF','集卡费', '', '', '', '10-charge', 84 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-085', 'JKGS','进口关税', '', '', '', '10-charge', 85 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-086', 'JKXGYWDLF','进口相关业务代理费', '', '', '', '10-charge', 86 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-087', 'JPFGN','机票费(国内)', '', '', '', '10-charge', 87 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-088', 'JPFGW','机票费(国外)', '', '', '', '10-charge', 88 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-089', 'JWG','进外高桥费', '', '', '', '10-charge', 89 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-090', 'JXF','机械费', '', '', '', '10-charge', 90 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-091', 'JXF1','监箱费', '', '', '', '10-charge', 91 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-092', 'JYF','检疫费', '', '', '', '10-charge', 92 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-093', 'JYJYF','检验检疫费', '', '', '', '10-charge', 93 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-094', 'JZXBY','集装箱办运费', '', '', '', '10-charge', 94 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-095', 'JZXHXF','集装箱换箱费', '', '', '', '10-charge', 95 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-096', 'JZXYF','集装箱运费', '', '', '', '10-charge', 96 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-097', 'K/B','K/B', '', '', '', '10-charge', 97 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-098', 'KCF','亏舱费', '', '', '', '10-charge', 98 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-099', 'KCYF','卡车运费', '', '', '', '10-charge', 99 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-100', 'KDF','快递费', '', '', '', '10-charge', 100 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-101', 'KJF','快件费', '', '', '', '10-charge', 101 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-102', 'KPF','改匹费', '', '', '', '10-charge', 102 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-103', 'KXCL','空箱处理费', '', '', '', '10-charge', 103 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-104', 'KYF','空运费', '', '', '', '10-charge', 104 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-105', 'LDF','拉单费', '', '', '', '10-charge', 105 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-106', 'LDF1','联单费', '', '', '', '10-charge', 106 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-107', 'LHF','理货费', '', '', '', '10-charge', 107 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-108', 'LPF','礼品费', '', '', '', '10-charge', 108 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-109', 'LWF','劳务费', '', '', '', '10-charge', 109 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-110', 'LXF','落箱费', '', '', '', '10-charge', 110 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-111', 'NTF','内托费', '', '', '', '10-charge', 111 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-112', 'NZXF','内装箱费', '', '', '', '10-charge', 112 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-113', 'PCYF','拼车运费', '', '', '', '10-charge', 113 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-114', 'PDF','跑单费', '', '', '', '10-charge', 114 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-115', 'PUC','Pick Up Charge(PUC)', '', '', '', '10-charge', 115 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-116', 'QDF','汽代费', '', '', '', '10-charge', 116 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-117', 'QGF','清关费', '', '', '', '10-charge', 117 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-118', 'QT','其他', '', '', '', '10-charge', 118 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-119', 'QTBGF','其他报关费', '', '', '', '10-charge', 119 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-120', 'QTCC','其他仓储费', '', '', '', '10-charge', 120 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-121', 'QTCY','其他查验费', '', '', '', '10-charge', 121 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-122', 'RBFY','日本费用', '', '', '', '10-charge', 122 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-123', 'RBFYSJ','日本费用税金', '', '', '', '10-charge', 123 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-124', 'SCT','SCT', '', '', '', '10-charge', 124 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-125', 'SDF','输单费', '', '', '', '10-charge', 125 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-126', 'SDF1','删单费', '', '', '', '10-charge', 126 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-127', 'SGF','疏港费', '', '', '', '10-charge', 127 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-128', 'SJ','税金', '', '', '', '10-charge', 128 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-129', 'SJCYF','商检查验费', '', '', '', '10-charge', 129 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-130', 'SJF1','三检费', '', '', '', '10-charge', 130 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-131', 'SJF2','商检费', '', '', '', '10-charge', 131 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-132', 'SJH','商检换单费', '', '', '', '10-charge', 132 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-133', 'SOC','Teminal Charge(SOC)', '', '', '', '10-charge', 133 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-134', 'SQF','速遣费', '', '', '', '10-charge', 134 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-135', 'SXF','上下车费', '', '', '', '10-charge', 135 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-136', 'SXF1','手续费', '', '', '', '10-charge', 136 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-137', 'T/F','Trucking Fee', '', '', '', '10-charge', 137 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-138', 'TBZK','特别折扣', '', '', '', '10-charge', 138 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-139', 'TGF','退关费', '', '', '', '10-charge', 139 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-140', 'THC','THC', '', '', '', '10-charge', 140 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-141', 'THF','提货费', '', '', '', '10-charge', 141 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-142', 'TM','贴唛', '', '', '', '10-charge', 142 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-143', 'TXCY','掏箱查验费', '', '', '', '10-charge', 143 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-144', 'TXF1','提箱费', '', '', '', '10-charge', 144 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-145', 'TXF2','掏箱费', '', '', '', '10-charge', 145 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-146', 'TXF3','拖箱费', '', '', '', '10-charge', 146 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-147', 'TY','退佣', '', '', '', '10-charge', 147 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-148', 'WJF','危检费', '', '', '', '10-charge', 148 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-149', 'WJF1','卫检费', '', '', '', '10-charge', 149 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-150', 'WJF2','文件费', '', '', '', '10-charge', 150 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-151', 'WLGLF','物流管理费', '', '', '', '10-charge', 151 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-152', 'WXPBZ','危险品标志', '', '', '', '10-charge', 152 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-153', 'WXPSBF','危险品申报费', '', '', '', '10-charge', 153 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-154', 'XDF','消毒费', '', '', '', '10-charge', 154 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-155', 'XFS','消费税', '', '', '', '10-charge', 155 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-156', 'XK','箱扣', '', '', '', '10-charge', 156 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-157', 'XTSY','系统使用费', '', '', '', '10-charge', 157 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-158', 'XXDCF','信息调查费', '', '', '', '10-charge', 158 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-159', 'XXF','保险费', '', '', '', '10-charge', 159 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-160', 'XXF1','洗箱费', '', '', '', '10-charge', 160 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-161', 'XZF','熏蒸费', '', '', '', '10-charge', 161 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-162', 'YJ','佣金', '', '', '', '10-charge', 162 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-163', 'YJF','邮寄费', '', '', '', '10-charge', 163 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-164', 'YLF','预录费', '', '', '', '10-charge', 164 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-165', 'YSF','运输费', '', '', '', '10-charge', 165 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-166', 'YWXZF','业务协助费', '', '', '', '10-charge', 166 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-167', 'YXF','移箱费', '', '', '', '10-charge', 167 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-168', 'YZF1','预支出车费', '', '', '', '10-charge', 168 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-169', 'YZF2','运杂费', '', '', '', '10-charge', 169 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-170', 'ZBJ','滞报金', '', '', '', '10-charge', 170 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-171', 'ZCF','装船费', '', '', '', '10-charge', 171 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-172', 'ZDF','制单费', '', '', '', '10-charge', 172 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-173', 'ZGBG','转关报关费', '', '', '', '10-charge', 173 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-174', 'ZSF','住宿费', '', '', '', '10-charge', 174 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-175', 'ZXF','装箱费', '', '', '', '10-charge', 175 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-176', 'ZXF1','装卸费', '', '', '', '10-charge', 176 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-177', 'ZXF2','注销费', '', '', '', '10-charge', 177 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-178', 'ZXF3','咨询费', '', '', '', '10-charge', 178 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-179', 'ZXF4','滞箱费', '', '', '', '10-charge', 179 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-180', 'ZXJ','重箱进港费', '', '', '', '10-charge', 180 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-181', 'ZZF','滞箱费', '', '', '', '10-charge', 181 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-182', 'ZZS','增值税', '', '', '', '10-charge', 182 );
	-- 贸易条款
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '11-trade-clause-01', 'GCJH',			'工厂交货', 				'', '', '', '11-trade-clause', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '11-trade-clause-02', 'HJCYR',			'货交承运人', 				'', '', '', '11-trade-clause', 2 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '11-trade-clause-03', 'CBJH',			'船边交货', 				'', '', '', '11-trade-clause', 3 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '11-trade-clause-04', 'CSJH',			'船上交货', 				'', '', '', '11-trade-clause', 4 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '11-trade-clause-05', 'CBJYF',			'成本加运费', 				'', '', '', '11-trade-clause', 5 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '11-trade-clause-06', 'BXFJYF',		'成本、保险费加运费',	'', '', '', '11-trade-clause', 6 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '11-trade-clause-07', 'YFFZ',			'运费付至', 				'', '', '', '11-trade-clause', 7 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '11-trade-clause-08', 'BXFFZ',			'运费及保险费付至', 	'', '', '', '11-trade-clause', 8 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '11-trade-clause-09', 'BJJH',			'边境交货', 				'', '', '', '11-trade-clause', 9 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '11-trade-clause-10', 'MDGCSJH',	'目的港船上交货', 		'', '', '', '11-trade-clause', 10 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '11-trade-clause-11', 'MDGMTJH',	'目的港码头交货',		'', '', '', '11-trade-clause', 11 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '11-trade-clause-12', 'WWSJH',		'未完税交货', 				'', '', '', '11-trade-clause', 12 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '11-trade-clause-13', 'WSHJH',		'完税后交货', 				'', '', '', '11-trade-clause', 13 );
	-- 运费条款
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '12-freight-clause-1', 'PP','预付', '', '', '', '12-freight-clause', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '12-freight-clause-2', 'RP','到付', '', '', '', '12-freight-clause', 2 );
	-- 附加费条款
	-- 来往单位类型
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-bgh', 	'BGH','报关行', 		'', '', '', '20-department', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-dls', 		'DLS','代理商', 		'', '', '', '20-department', 2 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-gys', 		'GYS','供应商', 		'', '', '', '20-department', 3 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-cd', 		'CD','船代', 			'', '', '', '20-department', 4 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-cyr', 		'CYR','承运人', 		'', '', '', '20-department', 5 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-chedui',	'CHEDUI','车队', 	'', '', '', '20-department', 6 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-gwdl', 	'GWDL','国外代理',	'', '', '', '20-department', 7 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-hkgs', 	'HKGS','航空公司', 	'', '', '', '20-department', 8 );
	-- 国家地区
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-01', 'CN','中国', 'China', '', '', '30-state', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-02', 'JP','日本', 'Japan', '', '', '30-state', 2 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-03', 'HK','香港', 'HongKong', '', '', '30-state', 3 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-04', 'TW','台湾', 'TaiWan', '', '', '30-state', 4 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-05', 'US','美国', 'American', '', '', '30-state', 5 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-06', 'UK','英国', 'Britain', '', '', '30-state', 6 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-07', 'AUS','澳大利亚', '', '', '', '30-state', 7 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-08', 'CANADA','加拿大', '', '', '', '30-state', 8 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-09', 'FINLAND','芬兰', '', '', '', '30-state', 9 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-10', 'GERMANY','德国', '', '', '', '30-state', 10 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-11', 'INDIA','印度', '', '', '', '30-state', 11 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-12', 'INDONESIA','印度尼西亚本', '', '', '', '30-state', 12 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-13', 'MALAYSIA','马来西亚', '', '', '', '30-state', 13 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-14', 'PORTUGAL','葡萄牙', '', '', '', '30-state', 14 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-15', 'SINGAPORE','新加坡', '', '', '', '30-state', 15 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-16', 'SOUTH AFRICA','南非', '', '', '', '30-state', 16 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-17', 'SWEDEN','瑞典', '', '', '', '30-state', 17 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-18', 'TG','泰国', 'TAILAND', '', '', '30-state', 18 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-19', 'ZIMBABWE','津巴布韦', '', '', '', '30-state', 19 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-20', 'SYDNEY','悉尼', '', '', '', '30-state', 20 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-21', 'OSAKA','大阪', '', '', '', '30-state', 21 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-22', 'GZ','广州', '', '', '', '30-state', 22 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-23', 'NB','宁波', '', '', '', '30-state', 23 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '30-state-24', 'SH','上海', '', '', '', '30-state', 24 );
	-- 海运港口
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-01', 'HangZhou','杭州', '', '', '', '31-port-sea', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-02', 'ShangHai','上海', '', '', '', '31-port-sea', 2 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-03', 'GuangZhou','广州', '', '', '', '31-port-sea', 3 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-04', 'XiaMen','厦门', '', '', '', '31-port-sea', 4 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-05', 'XiaoShan','萧山', '', '', '', '31-port-sea', 5 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-06', 'HongKong','香港', '', '', '', '31-port-sea', 6 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-07', 'Ningbo','宁波', '', '', '', '31-port-sea', 7 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-08', 'Brisbane','布里斯班', '', '', '', '31-port-sea', 8 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-09', 'Buenos Aires','布宜诺斯艾利斯', '', '', '', '31-port-sea', 9 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-10', 'ChenNai','钦奈', '', '', '', '31-port-sea', 10 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-11', 'FelixStowe','弗里克斯托', '', '', '', '31-port-sea', 11 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-12', 'GothenBurg','歌德堡', '', '', '', '31-port-sea', 12 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-13', 'Hakata','博多', '', '', '', '31-port-sea', 13 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-14', 'Hambure','汉堡', '', '', '', '31-port-sea', 14 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-15', 'Harare','哈拉雷', '', '', '', '31-port-sea', 15 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-16', 'Hiroshima','广岛', '', '', '', '31-port-sea', 16 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-17', 'Imabari','今治', '', '', '', '31-port-sea', 17 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-18', 'Inchon','仁川', '', '', '', '31-port-sea', 18 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-19', 'JY','江阴', '', '', '', '31-port-sea', 19 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-20', 'Kanazawa','金泽', '', '', '', '31-port-sea', 20 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-21', 'Kobe','神户', '', '', '', '31-port-sea', 21 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-22', 'Kotka','Kotka', '', '', '', '31-port-sea', 22 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-23', 'Kumamoto','熊本', '', '', '', '31-port-sea', 23 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-24', 'Leixoes','莱特索斯', '', '', '', '31-port-sea', 24 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-25', 'Lisbon','里斯本', '', '', '', '31-port-sea', 25 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-26', 'Long Beach','长滩', '', '', '', '31-port-sea', 26 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-27', 'Los Angle','洛杉矶', '', '', '', '31-port-sea', 27 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-28', 'MG','曼谷', '', '', '', '32-port-air', 28 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-29', 'Mizushima','水岛', '', '', '', '32-port-air', 29 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-30', 'Moji','门司', '', '', '', '31-port-sea', 30 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-31', 'Montreal','蒙特利尔', '', '', '', '31-port-sea', 31 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-32', 'Mumbai','孟买', '', '', '', '31-port-sea', 32 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-33', 'Nagoya','名古屋', '', '', '', '31-port-sea', 33 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-34', 'Nagoya','Nagoya', '', '', '', '31-port-sea', 34 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-35', 'Nashville','纳什维尔', '', '', '', '31-port-sea', 35 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-36', 'New York','纽约', '', '', '', '31-port-sea', 36 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-37', 'Nhava Sheva','那瓦什瓦', '', '', '', '31-port-sea', 37 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-38', 'Niigata','新泻', '', '', '', '32-port-air', 38 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-39', 'Osaka','大阪', '', '', '', '32-port-air', 39 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-40', 'QingDao','青岛', '', '', '', '31-port-sea', 40 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-41', 'Rotterdam','鹿特丹', '', '', '', '31-port-sea', 41 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-42', 'ShenZhen','深圳', '', '', '', '31-port-sea', 42 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-43', 'Shimizu','清水', '', '', '', '31-port-sea', 43 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-44', 'SuZhou','苏州', '', '', '', '31-port-sea', 44 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-45', 'Singapore','新加坡', '', '', '', '31-port-sea', 45 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-46', 'Stockholm','斯德哥尔摩', '', '', '', '31-port-sea', 46 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-47', 'Sydney','悉尼', '', '', '', '31-port-sea', 47 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-48', 'Tokyo','东京', '', '', '', '32-port-air', 48 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-49', 'Toyama','富山', '', '', '', '32-port-air', 49 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-50', 'Tunis','突尼斯', '', '', '', '31-port-sea', 50 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-51', 'Vancouver','温哥华', '', '', '', '31-port-sea', 51 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-52', 'Zurich','苏黎世', '', '', '', '31-port-sea', 52 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-53', 'Xias','下沙', '', '', '', '31-port-sea', 53 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-54', 'TianJin','天津', '', '', '', '31-port-sea', 54 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-55', 'YanTai','烟台', '', '', '', '31-port-sea', 55 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-56', 'YanTian','盐田', '', '', '', '31-port-sea', 56 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-57', 'Yokkaichi','四日市', '', '', '', '31-port-sea', 57 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '31-port-sea-58', 'Yokohama','横滨', '', '', '', '32-port-air', 58 );
	-- 空运港口
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-01', 'BeiJing','北京', '', '', '', '32-port-air', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-02', 'ShangHai','上海', '', '', '', '32-port-air', 2 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-03', 'GuangZhou','广州', '', '', '', '32-port-air', 3 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-04', 'HangZhou','杭州', '', '', '', '32-port-air', 4 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-05', 'HET','呼和浩特', '', '', '', '32-port-air', 5 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-06', 'HongKong','香港', '', '', '', '32-port-air', 6 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-07', 'ChangChun','长春', '', '', '', '32-port-air', 7 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-08', 'BKK','曼谷', '', '', '', '32-port-air', 8 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-09', 'BAKU','巴库', '', '', '', '32-port-air', 9 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-10', 'INCHEON','仁川', '', '', '', '32-port-air', 10 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-11', 'FUKUOKA','福冈', '', '', '', '32-port-air', 11 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-12', 'Kuala Lumpur','吉隆坡', '', '', '', '32-port-air', 12 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-13', 'LA','洛杉矶', '', '', '', '32-port-air', 13 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-14', 'London','伦敦', '', '', '', '32-port-air', 14 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-15', 'Madrid','马德里', '', '', '', '32-port-air', 15 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-16', 'Mumbai','孟买', '', '', '', '32-port-air', 16 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-17', 'Nagoya','名古屋', '', '', '', '32-port-air', 17 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-18', 'NY','纽约', '', '', '', '32-port-air', 18 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-19', 'Niigata','新泻', '', '', '', '32-port-air', 19 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-20', 'Osaka','大阪', '', '', '', '32-port-air', 20 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-21', 'Paris','巴黎', '', '', '', '32-port-air', 21 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-22', 'Pusan','釜山', '', '', '', '32-port-air', 22 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-23', 'Singapore','新加坡', '', '', '', '32-port-air', 23 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-24', 'Stockholm','斯德哥尔摩', '', '', '', '32-port-air', 24 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-25', 'Tokyo','东京', '', '', '', '32-port-air', 25 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-26', 'XiaMen','厦门', '', '', '', '32-port-air', 26 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-27', 'American','美国', '', '', '', '32-port-air', 27 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '32-port-air-28', 'MAA','CHENAAI', '', '', '', '32-port-air', 28 );
	-- 海运航线
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-01', 'SH2NB','上海 —— 宁波', '', '沿海航线', '', '33-route-sea', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-02', 'NB2SH','宁波 —— 上海', '', '沿海航线', '', '33-route-sea', 2 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-03', 'SH2GZ','上海 —— 广州', '', '沿海航线', '', '33-route-sea', 3 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-04', 'GZ2SH','广州 —— 上海', '', '沿海航线', '', '33-route-sea', 4 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-05', 'CN2JP','中国 —— 日本', '', '近洋航线', '', '33-route-sea', 5 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-06', 'JP2CN','日本 —— 中国', '', '近洋航线', '', '33-route-sea', 6 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-07', 'JPHX','日本航线', '', '近洋航线', '', '33-route-sea', 7 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-08', 'CN2TG','中国 —— 泰国', '', '', '', '33-route-sea', 8 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-09', 'TG2CN','泰国 —— 中国', '', '', '', '33-route-sea', 9 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-10', 'FLBHX','菲律宾', '', '沿海航线', '', '33-route-sea', 10 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-11', 'GNYS','国内运输', '', '', '', '33-route-sea', 11 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-12', 'US2JP','美国 —— 日本', '', '远洋航线', '', '33-route-sea', 12 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-13', 'JP2US','日本 —— 美国', '', '远洋航线', '', '33-route-sea', 13 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-14', 'US2CN','美国 —— 中国', '', '远洋航线', '', '33-route-sea', 14 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '33-route-sea-15', 'CN2US','中国 —— 美国', '', '远洋航线', '', '33-route-sea', 15 );
	-- 空运航线
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '34-route-air-1', 'AUS2CN','澳大利亚 —— 中国',	'', '', '', '34-route-air', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '34-route-air-2', 'CN2AUS','中国 —— 澳大利亚',	'', '', '', '34-route-air', 2 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '34-route-air-3', 'JPN2CN','日本 —— 中国', 		'', '', '', '34-route-air', 3 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '34-route-air-4', 'CN2JPN','中国 —— 日本', 		'', '', '', '34-route-air', 4 );

-- 创建表 - 合作伙伴数据
create table KSA_BD_PARTNER (
	ID					varchar(36)		not null		comment  '标识' ,
	CODE			varchar(200)		not null		comment  '编码' ,
  	NAME			varchar(2000)	not null		comment  '名称' ,
  	ALIAS			varchar(2000)	not null		comment  '别名 - 主要显示在提单信息中' ,
  	ADDRESS		varchar(2000)	not null		comment  '地址' ,
  	PP 				int					not null		comment  '付款周期 ( 天 )' ,
  	NOTE			varchar(2000)	not null		comment  '备注' ,
  	IMPORTANT int(1) default 0	not null		comment  '是否为重要伙伴' ,
  	RANK			int					not null		comment  '排序' ,
  	SALER_ID		varchar(36)		not null		comment  '销售担当标识',
    primary key ( ID ),
    unique ( CODE )
);	

	insert into KSA_BD_PARTNER ( ID, CODE, NAME, ALIAS, ADDRESS, PP, NOTE, IMPORTANT, RANK, SALER_ID ) values ( 'test-partner', 'test-partner', '测试-客户', '提单中所需用户信息', '', 30, '', 1, 0, 'test-operator');
	insert into KSA_BD_PARTNER ( ID, CODE, NAME, ALIAS, ADDRESS, PP, NOTE, IMPORTANT, RANK, SALER_ID ) values ( 'test-customs', 'test-customs', '测试-报关行', '提单中所需用户信息', '', 30, '', 1, 1, 'test-operator');
	insert into KSA_BD_PARTNER ( ID, CODE, NAME, ALIAS, ADDRESS, PP, NOTE, IMPORTANT, RANK, SALER_ID ) values ( 'test-agent', 'test-agent', '测试-代理商', '提单中所需用户信息', '', 30, '', 1, 2, 'test-operator' );
	insert into KSA_BD_PARTNER ( ID, CODE, NAME, ALIAS, ADDRESS, PP, NOTE, IMPORTANT, RANK, SALER_ID ) values ( 'test-supplier', 'test-supplier', '测试-供应商', '提单中所需用户信息', '', 30, '', 1, 3, 'test-operator');
	insert into KSA_BD_PARTNER ( ID, CODE, NAME, ALIAS, ADDRESS, PP, NOTE, IMPORTANT, RANK, SALER_ID ) values ( 'test-shipping', 'test-shipping', '测试-船代', '提单中所需用户信息', '', 30, '', 1, 4, 'test-operator' );
	insert into KSA_BD_PARTNER ( ID, CODE, NAME, ALIAS, ADDRESS, PP, NOTE, IMPORTANT, RANK, SALER_ID ) values ( 'test-carrier', 'test-carrier', '测试-承运人', '提单中所需用户信息', '', 30, '', 1, 5, 'test-operator');
	insert into KSA_BD_PARTNER ( ID, CODE, NAME, ALIAS, ADDRESS, PP, NOTE, IMPORTANT, RANK, SALER_ID ) values ( 'test-team', 'test-team', '测试-车队', '提单中所需用户信息', '', 30, '', 1, 6, 'test-operator' );

-- 创建表 - 合作伙伴附加提单信息
create table KSA_BD_PARTNER_EXTRA (
	PARTNER_ID	varchar(36)		not null		comment  '合作伙伴标识' ,
  	EXTRA			varchar(2000)	not null		comment  '附加提单信息' ,
  	-- PARTNER_ID 外键关联合作伙伴表
  	constraint FK_KSA_BD_PARTNER_EXTRA
    	foreign key ( PARTNER_ID )
    	references KSA_BD_PARTNER ( ID )
	    on delete cascade
	    on update cascade
);

	insert into KSA_BD_PARTNER_EXTRA ( PARTNER_ID, EXTRA ) values ( 'test-customs', '提单所需用户信息的其他格式');
	insert into KSA_BD_PARTNER_EXTRA ( PARTNER_ID, EXTRA ) values ( 'test-agent', '提单所需用户信息的其他格式');
	insert into KSA_BD_PARTNER_EXTRA ( PARTNER_ID, EXTRA ) values ( 'test-supplier', '提单所需用户信息的其他格式');
	insert into KSA_BD_PARTNER_EXTRA ( PARTNER_ID, EXTRA ) values ( 'test-shipping', '提单所需用户信息的其他格式');
	insert into KSA_BD_PARTNER_EXTRA ( PARTNER_ID, EXTRA ) values ( 'test-carrier', '提单所需用户信息的其他格式');
	insert into KSA_BD_PARTNER_EXTRA ( PARTNER_ID, EXTRA ) values ( 'test-team', '提单所需用户信息的其他格式');

-- 创建表 - 合作伙伴单位类型信息
create table KSA_BD_PARTNER_TYPE (
	PARTNER_ID	varchar(36)		not null		comment  '合作伙伴标识' ,
  	TYPE_ID		varchar(36)		not null		comment  '单位类型标识' ,
  	primary key (PARTNER_ID, TYPE_ID) ,
  	
  	-- PARTNER_ID 外键关联合作伙伴表
  	constraint FK_KSA_BD_PARTNER_TYPE1
    	foreign key ( PARTNER_ID )
    	references KSA_BD_PARTNER ( ID )
	    on delete cascade
	    on update cascade,
	    
	constraint FK_KSA_BD_PARTNER_TYPE2
    	foreign key ( TYPE_ID )
    	references KSA_BD_DATA ( ID )
	    on delete cascade
	    on update cascade
);	

	insert into KSA_BD_PARTNER_TYPE ( PARTNER_ID, TYPE_ID ) values ( 'test-customs', '20-department-bgh');
	insert into KSA_BD_PARTNER_TYPE ( PARTNER_ID, TYPE_ID ) values ( 'test-agent', '20-department-dls');
	insert into KSA_BD_PARTNER_TYPE ( PARTNER_ID, TYPE_ID ) values ( 'test-supplier', '20-department-gys');
	insert into KSA_BD_PARTNER_TYPE ( PARTNER_ID, TYPE_ID ) values ( 'test-shipping', '20-department-cd');
	insert into KSA_BD_PARTNER_TYPE ( PARTNER_ID, TYPE_ID ) values ( 'test-carrier', '20-department-cyr');
	insert into KSA_BD_PARTNER_TYPE ( PARTNER_ID, TYPE_ID ) values ( 'test-team', '20-department-chedui');

-- 创建表 - 汇率表 : 按日期记录
create table KSA_BD_CURRENCY_RATE_BYDATE (
	ID						varchar(36)		not null		comment  '汇率标识',
	CURRENCY_ID	varchar(36)		not null		comment  '关联货币的标识',
	MONTH			date					not null		comment  '记录汇率的日期',				
	RATE  				numeric(10,5)	not null		comment  '汇率值',
  	primary key ( ID ),
  	-- 外键关联汇率表
  	constraint FK_KSA_BD_CURRENCY_RATE_BYDATE
    	foreign key ( CURRENCY_ID )
    	references KSA_BD_DATA ( ID )
	    on delete cascade
	    on update cascade
);

-- 创建表 - 汇率表 : 按客户记录
create table KSA_BD_CURRENCY_RATE_BYPARTNER (
	ID						varchar(36)		not null		comment  '汇率标识',
	CURRENCY_ID	varchar(36)		not null		comment  '关联货币的标识',
	PARTNER_ID	varchar(36)		not null		comment  '关联客户的标识',				
	RATE  				numeric(10,5)	not null		comment  '汇率值',
  	primary key ( ID ),
  	-- 外键关联汇率表
  	constraint FK_KSA_BD_CURRENCY_RATE_BYPARTNER
    	foreign key ( CURRENCY_ID )
    	references KSA_BD_DATA ( ID )
	    on delete cascade
	    on update cascade,
    -- 外键关联客户表
  	constraint FK_KSA_BD_CURRENCY_RATE_BYPARTNER2
	    foreign key ( PARTNER_ID )
	    references KSA_BD_PARTNER ( ID )
	    on delete cascade
	    on update cascade
);	



-- ----------------------- LOGISTICS -------------------------
-- 创建表 - 托单表
create table KSA_LOGISTICS_BOOKINGNOTE (
	ID								varchar(36)		not null		comment  '标识',
	CODE						varchar(200)		not null		comment  '编号',
  	TYPE							varchar(200)		not null		comment  '托单类型',
  	TYPE_SUB					varchar(200)						comment  '托单子类型(FCL/LCL)',
  	SERIAL_NUMBER		int(10)				not null		comment  '流水号',
  	CUSTOMER_ID			varchar(36)		not null		comment  '客户标识',
  	INVOICE_NUMBER	varchar(200)				 		comment  '发票号',
  	CREATED_DATE		date					not null		comment  '接单日期',
  	CHARGE_DATE			date									comment  '记账月份',
  	
  	SALER_ID					varchar(36)						comment  '销售人员标识',
  	CREATOR_ID				varchar(36)						comment  '创建人标识',
  	
  	CARRIER_ID				varchar(200)						comment  '承运人标识 - 不强制外键关联',
  	SHIPPING_AGENT_ID varchar(200)					comment  '船代标识 - 不强制外键关联',
  	AGENT_ID				varchar(200)						comment  '代理标识 - 不强制外键关联',
  	
  	CARGO_NAME			varchar(200)						comment  '品名',
  	CARGO_NOTE			varchar(2000)					comment  '货物备注',
  	CARGO_DESCRIPTION varchar(2000) 				comment  '箱号封号',
  	CARGO_CONTAINER varchar(2000)					comment  '箱类箱型箱量描述',
  	SHIPPING_MARK		varchar(2000)					comment  '唛头',
  	VOLUMN					numeric(10,3)					comment  '体积',
  	WEIGHT					numeric(10,3)					comment  '毛重',
  	QUANTITY				int(10)								comment  '数量',
  	UNIT						varchar(200)						comment  '数量单位',
  	QUANTITY_DESCRIPTION varchar(2000)		 	comment  '英文数量描述',
  	
  	TITLE						varchar(200)						comment  '开票抬头',
  	MAWB						varchar(200)						comment  '主提单号',
  	HAWB						varchar(200)						comment  '副提单号',
  	SHIPPER_ID				varchar(36)						comment  '发货人标识',
  	CONSIGNEE_ID		varchar(36)						comment  '收货人标识',
  	NOTIFY_ID				varchar(36)						comment  '通知人标识',
  	
  	DEPARTURE				varchar(200)						comment  '出发地',
  	DEPARTURE_PORT	varchar(200)						comment  '出发港',
  	DEPARTURE_DATE	date									comment  '出发日期',
  	DESTINATION			varchar(200)						comment  '目的地',
  	DESTINATION_PORT varchar(200)					comment  '目的港',
  	DESTINATION_DATE date									comment  '到达日期',
  	LOADING_PORT		varchar(200)						comment  '装货港/中转港口',
  	DISCHARGE_PORT	varchar(200)						comment  '卸货港/中转港口',
  	DELIVER_DATE			date									comment  '接收/派送日期',
  	
  	ROUTE						varchar(200)						comment  '航线',
  	ROUTE_NAME			varchar(200)						comment  '船名',
  	ROUTE_CODE			varchar(200)						comment  '航次',

  	CUSTOMS_BROKER_ID	varchar(200)					comment  '代理标识 - 不强制外键关联',
  	CUSTOMS_CODE		varchar(200)						comment  '报关单号',
  	CUSTOMS_DATE 		date									comment  '报关日期',
  	RETURN_CODE			varchar(200)						comment  '退单单号',
  	RETURN_DATE	 		date									comment  '退单日期',
  	RETURN_DATE2 		date									comment  '退单日期2',
  	TAX_CODE				varchar(200)						comment  '税单号',
  	TAX_DATE1				date									comment  '收税单日期',
  	TAX_DATE2		 		date									comment  '发税单日期',
  	EXPRESS_CODE		varchar(200)						comment  '快递单号',
  	
  	VEHICLE_TEAM_ID	varchar(200)						comment  '车队标识',
  	VEHICLE_TYPE			varchar(200)						comment  '车型',
  	VEHICLE_NUMBER	varchar(200)						comment  '车号',
  	
  	STATE						int(8)				not null		comment  '托单状态',
  	primary key ( ID )
);

-- 创建表 - 托单货物表
create table KSA_LOGISTICS_BOOKINGNOTE_CARGO (
	ID								varchar(36)				comment  '货物标识',
	NAME						varchar(200)				comment  '货物名称',
  	CATEGORY				varchar(200)				comment  '箱类',
  	TYPE							varchar(200)				comment  '箱型',
  	AMOUNT					int(10)						comment  '箱量',
  	BOOKINGNOTE_ID	varchar(36) not null	comment  '所属托单标识',
  	primary key ( ID ),
  	constraint FK_KSA_LOGISTICS_BOOKINGNOTE_CARGO
	    foreign key ( BOOKINGNOTE_ID )
	    references KSA_LOGISTICS_BOOKINGNOTE ( ID )
	    on delete cascade
	    on update cascade
);

-- 创建表 - 提单确认书
create table KSA_LOGISTICS_BILLOFLADING (
	ID								varchar(36)		comment  '货物标识',
	TARGET					varchar(200)		comment  '提单对象',
	PUBLISH_DATE			varchar(200)		comment  '发布日期',
	SHIPPER					varchar(200)		comment  '发货人名称',
	CONSIGNEE				varchar(200)		comment  '收货人名称',
	NOTIFY					varchar(200)		comment  '通知人名称',
	CODE						varchar(200)		comment  '提单编号',
	DELIVER_TYPE			varchar(200)		comment  '发送方式: 电放or正本',
	CUSTOMER_CODE	varchar(200)		comment  '客户编号',
	SELF_CODE				varchar(200)		comment  '我司编号',
	CREATOR					varchar(200)		comment  '发件人',
	BILL_TYPE					varchar(200)		comment  '提单类型',
	NOTE						varchar(2000)	comment  '备注',
	AGENT						varchar(200)		comment  '海外代理',
	VESSEL_VOYAGE		varchar(200)		comment  '船名航次',
	LOADING_PORT		varchar(200)		comment  '装货港',
	DISCHARGE_PORT	varchar(200)		comment  '卸货港',
	DESTINATION_PORT varchar(200)	comment  '目的港',
	CARGO_MARK			varchar(2000)	comment  '货物标记',
	CARGO_QUANTITY	varchar(200)		comment  '货物数量',
	CARGO_NAME			varchar(2000)	comment  '货物名称',
	CARGO_WEIGHT		varchar(200)		comment  '货物毛重',
	CARGO_VOLUMN	varchar(200)		comment  '货物体积',
	CARGO_DESCRIPTION	varchar(2000)	comment  '箱号封号',
	CARGO_QUANTITY_DESCRIPTION varchar(2000) comment '货物英文数量描述',
	PAY_MODE				varchar(50)		comment '付款方式',
  	BOOKINGNOTE_ID	varchar(36) not null comment  '所属托单标识',
  	primary key ( ID ),
  	constraint KSA_LOGISTICS_BILLOFLADING
	    foreign key ( BOOKINGNOTE_ID )
	    references KSA_LOGISTICS_BOOKINGNOTE ( ID )
	    on delete cascade
	    on update cascade
);

-- 创建表 - 到货通知单
create table KSA_LOGISTICS_ARRIVALNOTE (
	ID								varchar(36)		comment  '单据标识',
	ARRIVAL_DATE			varchar(200)		comment  '到货日期',
	CODE						varchar(200)		comment  '编号',
	SHIPPER					varchar(200)		comment  '发货人名称',
	CONSIGNEE				varchar(200)		comment  '收货人名称',

	VESSEL						varchar(200)		comment  '船名',
	VOYAGE					varchar(200)		comment  '航次',
	MAWB						varchar(200)		comment  '主单号',
	HAWB						varchar(200)		comment  '副单号',
	CONTAINER				varchar(2000)	comment  '箱号封号',
	SEAL							varchar(200)		comment  '封印',
	ETA							varchar(200)		comment  '到货日',
	
	CY							varchar(200)		comment  'cy or cfs',
	LOADING_PORT		varchar(200)		comment  '装货港',
	DISCHARGE_PORT	varchar(200)		comment  '卸货港',
	DELIVER_PLACE		varchar(2000)	comment  '送货地',
	
	CARGO_MARK			varchar(2000)	comment  '货物标记',
	CARGO_WEIGHT		varchar(200)		comment  '货物毛重',
	CARGO_VOLUMN	varchar(200)		comment  '货物体积',
	CARGO_DESCRIPTION	varchar(2000)	comment  '货物描述',
	CARGO						varchar(2000)	comment  '货物数量',
	CARGO_PKG				varchar(200)		comment  'pkg',
	CARGO_COUNT		varchar(200)		comment  'count',
	
	FREIGHT					varchar(200)		comment  'FREIGHT',
	CHARGE					varchar(200)		comment  'CHARGE',
	RATE							varchar(200)		comment  'RATE',
	
  	BOOKINGNOTE_ID	varchar(36) not null comment  '所属托单标识',
  	primary key ( ID ),
  	constraint KSA_LOGISTICS_ARRIVALNOTE
	    foreign key ( BOOKINGNOTE_ID )
	    references KSA_LOGISTICS_BOOKINGNOTE ( ID )
	    on delete cascade
	    on update cascade
);

-- 创建表 - 进仓通知单
create table KSA_LOGISTICS_WAREHOUSENOTING (
	ID								varchar(36)		comment  '单据标识',
	SALER						varchar(200)		comment  '销售担当',
	TARGET					varchar(200)		comment  '提单对象',
	CODE						varchar(200)		comment  '提单编号',
	CREATED_DATE		varchar(200)		comment  '进仓时间',
	CARGO_NAME			varchar(2000)	comment  '货物名称',
	CARGO_WEIGHT		varchar(200)		comment  '货物毛重',
	CARGO_VOLUMN	varchar(200)		comment  '货物体积',
	CARGO_QUANTITY	varchar(200)		comment  '货物数量',
	CUSTOMER				varchar(200)		comment  '委托客户',
	LOADING_PORT		varchar(200)		comment  '装货港',
	DISCHARGE_PORT	varchar(200)		comment  '卸货港',
	VESSEL_VOYAGE		varchar(200)		comment  '船名航次',
	DESTINATION			varchar(200)		comment  '目的地',
	DEPARTURE_DATE	varchar(200)		comment  '出航日',
	MAWB						varchar(200)		comment  '提单号',
	ENTRY_DATE			varchar(200)		comment  '最晚入仓时间',
	INFORM_DATE			varchar(200)		comment  '通知时间',
	ADDRESS					varchar(2000)	comment  '进仓地址',
	CONTACT					varchar(200)		comment  '联系人',
	TELEPHONE				varchar(200)		comment  '电话',
	FAX							varchar(200)		comment  '传真',
  	BOOKINGNOTE_ID	varchar(36) not null comment  '所属托单标识',
  	SALER_TEL				varchar(200) 	comment  '销售电话',
  	SALER_FAX				varchar(200) 	comment  '销售传真',
  	SALER_EMAIL			varchar(200) 	comment  '销售邮件',
  	primary key ( ID ),
  	constraint KSA_LOGISTICS_WAREHOUSENOTING
	    foreign key ( BOOKINGNOTE_ID )
	    references KSA_LOGISTICS_BOOKINGNOTE ( ID )
	    on delete cascade
	    on update cascade
);

-- 创建表 - 订舱通知单
create table KSA_LOGISTICS_WAREHOUSEBOOKING (
	ID								varchar(36)		comment  '单据标识',
	SALER						varchar(200)		comment  '销售担当',
	SHIPPER					varchar(200)		comment  '发货人名称',
	CONSIGNEE				varchar(200)		comment  '收货人名称',
	NOTIFY					varchar(200)		comment  '通知人名称',
	CODE						varchar(200)		comment  '提单编号',
	CREATED_DATE		varchar(200)		comment  '进仓时间',
	DEPARTURE_PORT	varchar(200)		comment  '起运港',
	DESTINATION_PORT varchar(200)	comment  '目的港',
	
	SWITCH_SHIP			varchar(200)		comment  '转船',
	GROUPING				varchar(200)		comment  '分批',
	TRANSPORT_MODE	varchar(200)		comment  '运输方式',
	PAYMENT_MODE 	varchar(200)		comment  '付款方式',
	FREIGHT_CHARGE 	varchar(200)		comment  '运费',
	
	CARGO_CONTAINER varchar(200)	comment  '箱量',
	SHIPPING_MARK		varchar(2000)	comment  '唛头',
	CARGO_NAME			varchar(2000)	comment  '货物名称',
	CARGO_WEIGHT		varchar(200)		comment  '货物毛重',
	CARGO_VOLUMN	varchar(200)		comment  '货物体积',
	CARGO_QUANTITY	varchar(200)		comment  '货物数量',
	TOTAL_WEIGHT		varchar(200)		comment  '汇总毛重',
	TOTAL_VOLUMN		varchar(200)		comment  '汇总体积',
	TOTAL_QUANTITY	varchar(200)		comment  '汇总数量',
	NOTE						varchar(2000)	comment  '注意事项',
  	BOOKINGNOTE_ID	varchar(36) not null comment  '所属托单标识',
  	SALER_TEL				varchar(200) 	comment  '销售电话',
  	SALER_FAX				varchar(200) 	comment  '销售传真',
  	SALER_EMAIL			varchar(200) 	comment  '销售邮件',
  	primary key ( ID ),
  	constraint KSA_LOGISTICS_WAREHOUSEBOOKING
	    foreign key ( BOOKINGNOTE_ID )
	    references KSA_LOGISTICS_BOOKINGNOTE ( ID )
	    on delete cascade
	    on update cascade
);

-- 创建表 - MANIFEST
create table KSA_LOGISTICS_MANIFEST (
	ID								varchar(36)		comment  '单据标识',
	SALER						varchar(200)		comment  '销售担当',
	CODE						varchar(200)		comment  '提单编号',
	LOADING_PORT		varchar(200)		comment  '装货港',
	DESTINATION_PORT varchar(200)	comment  '目的港',
	FLIGHT_DATE			varchar(200)		comment  '航班和时间',
	AGENT						varchar(200)		comment  '代理商',	
	
	HAWB					 	varchar(200)		comment  'hawb',
	CARGO_NAME			varchar(2000)	comment  '货物名称',
	CARGO_WEIGHT		varchar(200)		comment  '货物毛重',
	FINAL_DESTINATION varchar(200)	comment  '最终目的地',
	SHIPPER					varchar(200)		comment  '发货人名称',
	CONSIGNEE				varchar(200)		comment  '收货人名称',
	RE								varchar(2000)	comment  're',
	
	TOTAL_HAWB			varchar(200)		comment  'total-hawb',
	TOTAL_PACKAGES	varchar(200)		comment  'total-packages',
  	BOOKINGNOTE_ID	varchar(36) not null comment  '所属托单标识',
  	SALER_TEL				varchar(200) 	comment  '销售电话',
  	SALER_FAX				varchar(200) 	comment  '销售传真',
  	SALER_EMAIL			varchar(200) 	comment  '销售邮件',
  	primary key ( ID ),
  	constraint KSA_LOGISTICS_MANIFEST
	    foreign key ( BOOKINGNOTE_ID )
	    references KSA_LOGISTICS_BOOKINGNOTE ( ID )
	    on delete cascade
	    on update cascade
);

-- ----------------------- FINANCE -------------------------
-- 创建表 -结算对账单
create table KSA_FINANCE_ACCOUNT (
	ID 						varchar(36)		not null		comment  '标识',
	CODE					varchar(200)		not null		comment  '结算单号',
	TARGET_ID			varchar(36)						comment  '结算对象标识',
	CREATOR_ID			varchar(36)		not null		comment  '创建人标识',
	CREATED_DATE	date									comment  '创建日期',
	DEADLINE			date									comment  '付款截止日期',
	PAYMENT_DATE	date									comment  '结清日期',
	NOTE					varchar(2000)	not null		comment  '备注',	
	STATE					int(5) default 0 not null		comment  '状态',
	DIRECTION			int(1) default 1 not null		comment  '收支方向:1表示收入,-1表示支出',
	NATURE				int(1) default 1 not null		comment  '国内/境外:1表示国内,-1表示境外',
	primary key ( ID ),
	
	-- 外键关联合作伙伴表
  	constraint FK_KSA_FINANCE_ACCOUNT_PARTNER
	    foreign key ( TARGET_ID )
	    references KSA_BD_PARTNER ( ID )
	    on delete set null
	    on update cascade
);

-- 创建表 - 费用
create table KSA_FINANCE_CHARGE (
	ID							varchar(36)		not null		comment  '标识',
	TARGET_ID			varchar(36)						comment  '费用结算对象标识',
	TYPE						varchar(200)		not null		comment  '费用类型',
  	CURRENCY_ID		varchar(36)						comment  '货币类型标识',
  	PRICE					numeric(20,5)					comment  '单价',
  	QUANTITY			int(10)								comment  '数量',
  	AMOUNT				numeric(20,5)	not null		comment  '金额',
  	CREATED_DATE	datetime				not null		comment  '创建日期',
  	NOTE 					varchar(2000)	not null		comment  '备注',
  	CREATOR_ID 		varchar(36)						comment  '创建人标识',
  	ACCOUNT_ID		varchar(36)						comment  '所属对账单标识',
  	BOOKINGNOTE_ID	varchar(36)	not null		comment  '所属托单标识',
  	DIRECTION			int(1) default 1 not null		comment  '收支方向:1表示收入,-1表示支出',
  	NATURE				int(1) default 1 not null		comment  '国内/境外:1表示国内,-1表示境外',
  	RANK               int(3) default 0 not null       comment  '费用录入排序编号',
  	primary key ( ID ),
  	
  	-- 外键关联货币类型
  	constraint FK_KSA_FINANCE_CHARGE_CURRENCY
    	foreign key ( CURRENCY_ID )
    	references KSA_BD_DATA ( ID )
	    on delete set null
	    on update cascade,
    -- 外键关联用户表
  	constraint FK_KSA_FINANCE_CHARGE_USER
	    foreign key ( CREATOR_ID )
	    references KSA_SECURITY_USER ( ID )
	    on delete set null
	    on update cascade,
	-- 外键关联收入结算对账单表
  	constraint FK_KSA_FINANCE_ACCOUNT
	    foreign key ( ACCOUNT_ID )
	    references KSA_FINANCE_ACCOUNT ( ID )
	    on delete set null
	    on update cascade,
    -- 外键关联托单表
  	constraint FK_KSA_FINANCE_BOOKINGNOTE
	    foreign key ( BOOKINGNOTE_ID )
	    references KSA_LOGISTICS_BOOKINGNOTE ( ID )
	    on delete cascade
	    on update cascade
);

-- 创建表 - 发票单据:自己开出的发票(合作伙伴收到的发票 对应自己的收入费用)
create table KSA_FINANCE_INVOICE (
	ID							varchar(36)		not null		comment  '标识',
	CODE					varchar(200)		not null		comment  '发票代码',
	INVOICE_NUMBER varchar(200)					comment  '发票号码',
	TAX_NUMBER		varchar(200)						comment  '发票税号',
	TYPE						varchar(200)						comment  '票据类型',
	TARGET_ID			varchar(36)						comment  '票据清算对象',
  	CURRENCY_ID		varchar(36)						comment  '货币类型标识',
  	AMOUNT				numeric(20,5)	not null		comment  '金额',
  	CREATED_DATE	date					not null		comment  '创建日期',
  	NOTE 					varchar(2000)					comment  '备注',
  	CREATOR_ID 		varchar(36)						comment  '创建人标识',
  	ACCOUNT_ID		varchar(36)						comment  '所属对账单标识应用于销账',
  	DIRECTION			int(1) default 1 not null		comment  '收支方向:1表示收入,-1表示支出',
  	NATURE				int(1) default 1 not null		comment  '国内/境外:1表示国内,-1表示境外',
  	primary key ( ID ),
  	
  	-- 外键关联货币类型
  	constraint FK_KSA_FINANCE_INVOICE_CURRENCY
    	foreign key ( CURRENCY_ID )
    	references KSA_BD_DATA ( ID )
	    on delete set null
	    on update cascade,
	-- 外键关联收入结算对账单表(开出的发票与收入进行销账)
  	constraint FK_KSA_FINANCE_INVOICE_ACCOUNT
	    foreign key ( ACCOUNT_ID )
	    references KSA_FINANCE_ACCOUNT ( ID )
	    on delete set null
	    on update cascade,
    -- 外键关联用户表
  	constraint FK_KSA_FINANCE_INVOICE_USER
	    foreign key ( CREATOR_ID )
	    references KSA_SECURITY_USER ( ID )
	    on delete set null
	    on update cascade,
    -- 外键关联合作伙伴表
  	constraint FK_KSA_FINANCE_INVOICE_PARTNER
	    foreign key ( TARGET_ID )
	    references KSA_BD_PARTNER ( ID )
	    on delete set null
	    on update cascade
);
	
-- 创建表 - 汇率表 : 按结算单
create table KSA_FINANCE_CURRENCY_RATE_BYACCOUNT (
	ID						varchar(36)		not null		comment  '汇率标识',
	CURRENCY_ID	varchar(36)		not null		comment  '关联货币的标识',
	ACCOUNT_ID	varchar(36)		not null		comment  '关联结算单的标识',				
	RATE  				numeric(10,5)	not null		comment  '汇率值',
  	primary key ( ID ),
  	-- 外键关联汇率表
  	constraint FK_KSA_FINANCE_CURRENCY_RATE_BYACCOUNT
    	foreign key ( CURRENCY_ID )
    	references KSA_BD_DATA ( ID )
	    on delete cascade
	    on update cascade,
    -- 外键关联结算单表
  	constraint FK_KSA_FINANCE_CURRENCY_RATE_BYACCOUNT2
	    foreign key ( ACCOUNT_ID )
	    references KSA_FINANCE_ACCOUNT ( ID )
	    on delete cascade
	    on update cascade
);	
