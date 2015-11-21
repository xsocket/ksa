-- ---------------------- 测试相关表及数据 ----------------------------	
-- 创建表 - 用户表
create table KSA_SECURITY_USER (
	ID					varchar(36) primary key,
  	NAME			varchar(256),
  	PASSWORD varchar(256),
  	EMAIL 			varchar(256),
  	TELEPHONE varchar(256)
);

	insert into KSA_SECURITY_USER ( ID, NAME, PASSWORD, EMAIL, TELEPHONE ) values ( 'test-user-1', '麻文强', 'fEqNCco3Yq9h5ZUglD3CZJT4lBs', 'a@a.a', '123456' );
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

-- 创建表 - 托单表
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
  	SHIPPING_MARK		varchar(200)						comment  '唛头',
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
  	RETURN_DATE2	 		date								comment  '退单日期2',
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

	insert into KSA_LOGISTICS_BOOKINGNOTE ( ID, CODE, TYPE, SERIAL_NUMBER, CUSTOMER_ID, INVOICE_NUMBER, CREATED_DATE, SALER_ID, CREATOR_ID, CARRIER_ID, SHIPPING_AGENT_ID, AGENT_ID, CARGO_NAME, CARGO_NOTE, CARGO_CONTAINER, CARGO_DESCRIPTION, SHIPPING_MARK, VOLUMN, WEIGHT, QUANTITY, UNIT,QUANTITY_DESCRIPTION, TITLE, MAWB, HAWB, SHIPPER_ID, CONSIGNEE_ID, NOTIFY_ID, DEPARTURE, DEPARTURE_PORT, DEPARTURE_DATE, DESTINATION, DESTINATION_PORT, DESTINATION_DATE, LOADING_PORT, DISCHARGE_PORT, DELIVER_DATE, ROUTE, ROUTE_NAME, ROUTE_CODE, CUSTOMS_BROKER_ID, CUSTOMS_CODE, CUSTOMS_DATE, RETURN_CODE, RETURN_DATE, RETURN_DATE2, TAX_CODE, TAX_DATE1, EXPRESS_CODE, TAX_DATE2, VEHICLE_TEAM_ID, VEHICLE_TYPE, VEHICLE_NUMBER, STATE ) values ( 'test-bookingnote-1', 'KHSE1', 'SE', 1, 'test-partner-1', '', '2012-10-1', 'test-user-1', 'test-user-2', '', '', '', '品名', '', '20GP*1+40GP*2', '', '', 123.0, 321.0, 222, '箱', '', '开票抬头', 'mawb', 'hawb', 'test-partner-1', 'test-partner-2', 'test-partner-2', '', '', '2012-1-1', '', '', '2012-2-2', '', '', '2012-3-3', '', '', '', 'test-partner-1', '', '2012-4-4', '', '2012-5-5', '2012-5-5', '', '2012-6-6', '', '2012-7-7', 'test-partner-2', '', '', 1 ); 
	insert into KSA_LOGISTICS_BOOKINGNOTE ( ID, CODE, TYPE, SERIAL_NUMBER, CUSTOMER_ID, INVOICE_NUMBER, CREATED_DATE, SALER_ID, CREATOR_ID, CARRIER_ID, SHIPPING_AGENT_ID, AGENT_ID, CARGO_NAME, CARGO_NOTE, CARGO_CONTAINER, CARGO_DESCRIPTION, SHIPPING_MARK, VOLUMN, WEIGHT, QUANTITY, UNIT,QUANTITY_DESCRIPTION, TITLE, MAWB, HAWB, SHIPPER_ID, CONSIGNEE_ID, NOTIFY_ID, DEPARTURE, DEPARTURE_PORT, DEPARTURE_DATE, DESTINATION, DESTINATION_PORT, DESTINATION_DATE, LOADING_PORT, DISCHARGE_PORT, DELIVER_DATE, ROUTE, ROUTE_NAME, ROUTE_CODE, CUSTOMS_BROKER_ID, CUSTOMS_CODE, CUSTOMS_DATE, RETURN_CODE, RETURN_DATE, RETURN_DATE2, TAX_CODE, TAX_DATE1, EXPRESS_CODE, TAX_DATE2, VEHICLE_TEAM_ID, VEHICLE_TYPE, VEHICLE_NUMBER, STATE ) values ( 'test-bookingnote-2', 'KHSI2', 'SI', 2, 'test-partner-1', '', '2012-10-1', 'test-user-1', 'test-user-2', '', '', '', '品名', '', '20GP*1+40GP*2', '', '', 123.0, 321.0, 222, '箱', '', '开票抬头', 'mawb', 'hawb', 'test-partner-1', 'test-partner-2', 'test-partner-2', '', '', '2012-1-1', '', '', '2012-2-2', '', '', '2012-3-3', '', '', '', 'test-partner-1', '', '2012-4-4', '', '2012-5-5', '2012-5-5', '', '2012-6-6', '', '2012-7-7', 'test-partner-2', '', '', 0 );
	insert into KSA_LOGISTICS_BOOKINGNOTE ( ID, CODE, TYPE, SERIAL_NUMBER, CUSTOMER_ID, INVOICE_NUMBER, CREATED_DATE, SALER_ID, CREATOR_ID, CARRIER_ID, SHIPPING_AGENT_ID, AGENT_ID, CARGO_NAME, CARGO_NOTE, CARGO_CONTAINER, CARGO_DESCRIPTION, SHIPPING_MARK, VOLUMN, WEIGHT, QUANTITY, UNIT,QUANTITY_DESCRIPTION, TITLE, MAWB, HAWB, SHIPPER_ID, CONSIGNEE_ID, NOTIFY_ID, DEPARTURE, DEPARTURE_PORT, DEPARTURE_DATE, DESTINATION, DESTINATION_PORT, DESTINATION_DATE, LOADING_PORT, DISCHARGE_PORT, DELIVER_DATE, ROUTE, ROUTE_NAME, ROUTE_CODE, CUSTOMS_BROKER_ID, CUSTOMS_CODE, CUSTOMS_DATE, RETURN_CODE, RETURN_DATE, RETURN_DATE2, TAX_CODE, TAX_DATE1, EXPRESS_CODE, TAX_DATE2, VEHICLE_TEAM_ID, VEHICLE_TYPE, VEHICLE_NUMBER, STATE ) values ( 'test-bookingnote-3', 'KHAE3', 'AE', 3, 'test-partner-1', '', '2012-10-1', 'test-user-1', 'test-user-2', '', '', '', '品名', '', '20GP*1+40GP*2', '', '', 123.0, 321.0, 222, '箱', '', '开票抬头', 'mawb', 'hawb', 'test-partner-1', 'test-partner-2', 'test-partner-2', '', '', '2012-1-1', '', '', '2012-2-2', '', '', '2012-3-3', '', '', '', 'test-partner-1', '', '2012-4-4', '', '2012-5-5', '2012-5-5', '', '2012-6-6', '', '2012-7-7', 'test-partner-2', '', '', 2 ); 
	insert into KSA_LOGISTICS_BOOKINGNOTE ( ID, CODE, TYPE, SERIAL_NUMBER, CUSTOMER_ID, INVOICE_NUMBER, CREATED_DATE, SALER_ID, CREATOR_ID, CARRIER_ID, SHIPPING_AGENT_ID, AGENT_ID, CARGO_NAME, CARGO_NOTE, CARGO_CONTAINER, CARGO_DESCRIPTION, SHIPPING_MARK, VOLUMN, WEIGHT, QUANTITY, UNIT,QUANTITY_DESCRIPTION, TITLE, MAWB, HAWB, SHIPPER_ID, CONSIGNEE_ID, NOTIFY_ID, DEPARTURE, DEPARTURE_PORT, DEPARTURE_DATE, DESTINATION, DESTINATION_PORT, DESTINATION_DATE, LOADING_PORT, DISCHARGE_PORT, DELIVER_DATE, ROUTE, ROUTE_NAME, ROUTE_CODE, CUSTOMS_BROKER_ID, CUSTOMS_CODE, CUSTOMS_DATE, RETURN_CODE, RETURN_DATE, RETURN_DATE2, TAX_CODE, TAX_DATE1, EXPRESS_CODE, TAX_DATE2, VEHICLE_TEAM_ID, VEHICLE_TYPE, VEHICLE_NUMBER, STATE ) values ( 'test-bookingnote-4', 'KHAI4', 'AI', 4, 'test-partner-1', '', '2012-10-1', 'test-user-1', 'test-user-2', '', '', '', '品名', '', '20GP*1+40GP*2', '', '', 123.0, 321.0, 222, '箱', '', '开票抬头', 'mawb', 'hawb', 'test-partner-1', 'test-partner-2', 'test-partner-2', '', '', '2012-1-1', '', '', '2012-2-2', '', '', '2012-3-3', '', '', '', 'test-partner-1', '', '2012-4-4', '', '2012-5-5', '2012-5-5', '', '2012-6-6', '', '2012-7-7', 'test-partner-2', '', '', 8 );
	insert into KSA_LOGISTICS_BOOKINGNOTE ( ID, CODE, TYPE, SERIAL_NUMBER, CUSTOMER_ID, INVOICE_NUMBER, CREATED_DATE, SALER_ID, CREATOR_ID, CARRIER_ID, SHIPPING_AGENT_ID, AGENT_ID, CARGO_NAME, CARGO_NOTE, CARGO_CONTAINER, CARGO_DESCRIPTION, SHIPPING_MARK, VOLUMN, WEIGHT, QUANTITY, UNIT,QUANTITY_DESCRIPTION, TITLE, MAWB, HAWB, SHIPPER_ID, CONSIGNEE_ID, NOTIFY_ID, DEPARTURE, DEPARTURE_PORT, DEPARTURE_DATE, DESTINATION, DESTINATION_PORT, DESTINATION_DATE, LOADING_PORT, DISCHARGE_PORT, DELIVER_DATE, ROUTE, ROUTE_NAME, ROUTE_CODE, CUSTOMS_BROKER_ID, CUSTOMS_CODE, CUSTOMS_DATE, RETURN_CODE, RETURN_DATE, RETURN_DATE2, TAX_CODE, TAX_DATE1, EXPRESS_CODE, TAX_DATE2, VEHICLE_TEAM_ID, VEHICLE_TYPE, VEHICLE_NUMBER, STATE ) values ( 'test-bookingnote-5', 'KHSI5', 'SI', 5, 'test-partner-1', '', '2012-10-1', 'test-user-1', 'test-user-2', '', '', '', '品名', '', '20GP*1+40GP*2', '', '', 123.0, 321.0, 222, '箱', '', '开票抬头', 'mawb', 'hawb', 'test-partner-1', 'test-partner-2', 'test-partner-2', '', '', '2012-1-1', '', '', '2012-2-2', '', '', '2012-3-3', '', '', '', 'test-partner-1', '', '2012-4-4', '', '2012-5-5', '2012-5-5', '', '2012-6-6', '', '2012-7-7', 'test-partner-2', '', '', -1 );
	


-- 创建表 - 托单货物表
create table KSA_LOGISTICS_BOOKINGNOTE_CARGO (
	ID								varchar(36)		not null		comment  '货物标识',
	NAME						varchar(200)		not null		comment  '货物名称',
  	CATEGORY				varchar(200)		not null		comment  '箱类',
  	TYPE							varchar(200)		not null		comment  '箱型',
  	AMOUNT					int(10)				not null		comment  '箱量',
  	BOOKINGNOTE_ID	varchar(36)		not null		comment  '所属托单标识',
  	primary key ( ID ),
  	constraint FK_KSA_LOGISTICS_BOOKINGNOTE_CARGO
	    foreign key ( BOOKINGNOTE_ID )
	    references KSA_LOGISTICS_BOOKINGNOTE ( ID )
	    on delete cascade
	    on update cascade
);


-- 创建表 - 提单确认书
create table KSA_LOGISTICS_BILLOFLADING (
	ID								varchar(36)		comment  '单据标识',
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
	NOTE						varchar(200)		comment  '备注',
	AGENT						varchar(200)		comment  '海外代理',
	VESSEL_VOYAGE		varchar(200)		comment  '船名航次',
	LOADING_PORT		varchar(200)		comment  '装货港',
	DISCHARGE_PORT	varchar(200)		comment  '卸货港',
	DESTINATION_PORT varchar(200)	comment  '目的港',
	CARGO_MARK			varchar(200)		comment  '货物标记',
	CARGO_QUANTITY	varchar(200)		comment  '货物数量',
	CARGO_NAME			varchar(200)		comment  '货物名称',
	CARGO_WEIGHT		varchar(200)		comment  '货物毛重',
	CARGO_VOLUMN	varchar(200)		comment  '货物体积',
	CARGO_DESCRIPTION	varchar(200)	comment  '箱号封号',
	CARGO_QUANTITY_DESCRIPTION varchar(200) comment '货物英文数量描述',
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
	CONTAINER				varchar(200)		comment  '箱号封号',
	SEAL							varchar(200)		comment  '封印',
	ETA							varchar(200)		comment  '到货日',
	
	CY							varchar(200)		comment  'cy or cfs',
	LOADING_PORT		varchar(200)		comment  '装货港',
	DISCHARGE_PORT	varchar(200)		comment  '卸货港',
	DELIVER_PLACE		varchar(200)		comment  '送货地',
	
	CARGO_MARK			varchar(200)		comment  '货物标记',
	CARGO_WEIGHT		varchar(200)		comment  '货物毛重',
	CARGO_VOLUMN	varchar(200)		comment  '货物体积',
	CARGO_DESCRIPTION	varchar(200)	comment  '货物描述',
	CARGO						varchar(200)		comment  '货物数量',
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
	CARGO_NAME			varchar(200)		comment  '货物名称',
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
	ADDRESS					varchar(200)		comment  '进仓地址',
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
	SHIPPING_MARK		varchar(200)		comment  '唛头',
	CARGO_NAME			varchar(200)		comment  '货物名称',
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
	CARGO_NAME			varchar(200)		comment  '货物名称',
	CARGO_WEIGHT		varchar(200)		comment  '货物毛重',
	FINAL_DESTINATION varchar(200)	comment  '最终目的地',
	SHIPPER					varchar(200)		comment  '发货人名称',
	CONSIGNEE				varchar(200)		comment  '收货人名称',
	RE								varchar(200)		comment  're',
	
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