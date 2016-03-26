-- ---------------------- 测试相关表及数据 ----------------------------
create table KSA_LOGISTICS_BOOKINGNOTE (
	ID					varchar(36)		not null		comment  '标识' 
);
	insert into KSA_LOGISTICS_BOOKINGNOTE ( ID ) values ( 'test-bookingnote-1' );

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

		insert into KSA_SECURITY_USER ( ID, NAME, PASSWORD, EMAIL, TELEPHONE ) values ( 'test-user-1', '麻文强', 'fEqNCco3Yq9h5ZUglD3CZJT4lBs', 'a@a.a', '123456');
		insert into KSA_SECURITY_USER ( ID, NAME, PASSWORD, EMAIL, TELEPHONE ) values ( 'test-user-2', '闫寅卓', 'fEqNCco3Yq9h5ZUglD3CZJT4lBs', 'a@a.a', '123456' );

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
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '00-currency-HKD', 'HKD', 	'港币', 		'', '', 	'0.882', 	'00-currency', 3 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '00-currency-JPY', 	 'JPY', 	'日元', 		'', '', 	'0.075', 	'00-currency', 4 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '00-currency-EUR',  'EUR', 	'欧元', 		'', '', 	'10.000', 	'00-currency', 5 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '00-currency-TWD', 'TWD', '台币', 		'', '', 	'0.200', 	'00-currency', 6 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '00-currency-KRW', 'KRW', 	'韩元', 		'', '', 	'0.005', 	'00-currency', 7 );
	-- 费用类型
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-001', 'ABF', 	'安保费', '', '', '', '10-charge', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-002', 'AFC',	'Air/Ocean Freight Charge', '', '', '', '10-charge', 2 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-003', 'AWC',	'Air Waybill Charge(AWC)', '', '', '', '10-charge', 3 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '10-charge-004', 'BAF',	'BAF', '', '', '', '10-charge', 4 );	
	-- 来往单位类型
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-bgh', 	'BGH','报关行', 		'', '', '', '20-department', 1 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-dls', 		'DLS','代理商', 		'', '', '', '20-department', 2 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-gys', 		'GYS','供应商', 		'', '', '', '20-department', 3 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-cd', 		'CD','船代', 			'', '', '', '20-department', 4 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-cyr', 		'CYR','承运人', 		'', '', '', '20-department', 5 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-chedui',	'CHEDUI','车队', 	'', '', '', '20-department', 6 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-gwdl', 	'GWDL','国外代理',	'', '', '', '20-department', 7 );
	insert into KSA_BD_DATA ( ID, CODE, NAME, ALIAS, NOTE, EXTRA, TYPE_ID, RANK ) values ( '20-department-hkgs', 	'HKGS','航空公司', 	'', '', '', '20-department', 8 );
	
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

	insert into KSA_BD_PARTNER ( ID, CODE, NAME, ALIAS, ADDRESS, PP, NOTE, IMPORTANT, RANK, SALER_ID ) values ( 'test-partner-1', 'test-partner-1', '测试合作伙伴1', 'Alias1-1', '', 30, '', 1, 1, 'test-user-1');
	insert into KSA_BD_PARTNER ( ID, CODE, NAME, ALIAS, ADDRESS, PP, NOTE, IMPORTANT, RANK, SALER_ID ) values ( 'test-partner-2', 'test-partner-2', '测试合作伙伴2', 'Alias2-1', '', 30, '', 0, 1001, '' );
	

-- ---------------------- 正式表与数据 ----------------------------

-- 创建表 - 结算对账单
create table KSA_FINANCE_ACCOUNT (
	ID 						varchar(36)		not null		comment  '标识',
	CODE					varchar(200)		not null		comment  '结算对账单编号',
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

	insert into KSA_FINANCE_ACCOUNT ( ID, CODE, TARGET_ID, CREATOR_ID, CREATED_DATE, NOTE, STATE, DIRECTION ) values ( 'test-account-1', 'test-account-1', 'test-partner-1', 'test-user-1', '2012-12-11', '', 0, 1);
	insert into KSA_FINANCE_ACCOUNT ( ID, CODE, TARGET_ID, CREATOR_ID, CREATED_DATE, NOTE, STATE, DIRECTION ) values ( 'test-account-2', 'test-account-2', 'test-partner-1', 'test-user-1', '2012-12-11', '', 0, -1);


-- 创建表 - 费用
create table KSA_FINANCE_CHARGE (
	ID							varchar(36)		not null		comment  '标识',
	TARGET_ID			varchar(36)						comment  '费用结算对象标识',
	TYPE						varchar(200)		not null		comment  '费用类型',
  	CURRENCY_ID		varchar(36)						comment  '货币类型标识',
  	PRICE					numeric(20,5)					comment  '单价',
  	QUANTITY			int(10)								comment  '数量',
  	AMOUNT				numeric(20,5)	not null		comment  '金额',
  	CREATED_DATE	date					not null		comment  '创建日期',
  	NOTE 					varchar(2000)	not null		comment  '备注',
  	CREATOR_ID 		varchar(36)						comment  '创建人标识',
  	ACCOUNT_ID		varchar(36)						comment  '所属对账单标识',
  	BOOKINGNOTE_ID	varchar(36)	not null		comment  '所属托单标识',
  	DIRECTION			int(1) default 1 not null		comment  '收支方向:1表示收入,-1表示支出',
  	NATURE				int(1) default 1 not null		comment  '国内/境外:1表示国内,-1表示境外',
    RANK             int(3) default 0 not null       comment  '费用录入排序编号',
  	primary key ( ID ),
  	
  	-- 外键关联合作伙伴
  	constraint FK_KSA_FINANCE_CHARGE_PARTNER
    	foreign key ( TARGET_ID )
    	references KSA_BD_PARTNER ( ID )
	    on delete set null
	    on update cascade,
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
	INVOICE_NUMBER varchar(200)	not null		comment  '发票号码',
	TAX_NUMBER		varchar(200)		not null		comment  '发票税号',
	TYPE						varchar(200)		not null		comment  '票据类型',
	TARGET_ID			varchar(36)						comment  '票据清算对象',
  	CURRENCY_ID		varchar(36)						comment  '货币类型标识',
  	AMOUNT				numeric(20,5)	not null		comment  '金额',
  	CREATED_DATE	date					not null		comment  '创建日期',
  	NOTE 					varchar(2000)	not null		comment  '备注',
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
