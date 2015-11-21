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

-- 创建表 - 角色表
create table KSA_SECURITY_ROLE (
	ID						varchar(36)		not null		comment  '角色标识' ,
    NAME				varchar(200)		not null		comment  '角色名称' ,
    DESCRIPTION	varchar(2000)	not null		comment  '角色描述' ,
    primary key ( ID )
);	

		insert into KSA_SECURITY_ROLE ( ID, NAME, DESCRIPTION ) values ( 'test-role-1', '系统管理员', '系统管理员描述' );
		insert into KSA_SECURITY_ROLE ( ID, NAME, DESCRIPTION ) values ( 'test-role-2', '操作员', '操作员描述' );

-- 创建表 - 权限表
create table KSA_SECURITY_PERMISSION (
	ID						varchar(200)		not null		comment  '权限标识',
    NAME				varchar(200)		not null		comment  '权限名称',
    DESCRIPTION	varchar(2000)	not null		comment  '权限描述',
    primary key ( ID )
);	

		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bookingnote:edit:view', 	'托单查看', '可以查看所有的业务托单，但是并没有编辑的权限。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bookingnote:print', 		'托单打印', '可以打印提单、订舱通知等业务相关的文档。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bookingnote:edit', 		'托单编辑', '可以新增和修改所有的业务托单，但是没有删除的权限。' );
		insert into KSA_SECURITY_PERMISSION ( ID, NAME, DESCRIPTION ) values ( 'bookingnote:delete', 		'托单删除', '可以删除托单（并非真正删除，而是将托单置于已删除状态）。' );
		
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

		insert into KSA_SECURITY_USERROLE ( USER_ID, ROLE_ID ) values ( 'test-user-1', 'test-role-1' );
		insert into KSA_SECURITY_USERROLE ( USER_ID, ROLE_ID ) values ( 'test-user-1', 'test-role-2' );
		insert into KSA_SECURITY_USERROLE ( USER_ID, ROLE_ID ) values ( 'test-user-2', 'test-role-2' );


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

		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'test-role-1', 'bookingnote:edit:view' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'test-role-1', 'bookingnote:print' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'test-role-1', 'bookingnote:edit' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'test-role-1', 'bookingnote:delete' );
		insert into KSA_SECURITY_ROLEPERMISSION ( ROLE_ID, PERMISSION_ID ) values ( 'test-role-2', 'bookingnote:edit:view' );