/*==============================================================*/
/* 添加管理员角色，设定admin账为管理员                                          */
/*==============================================================*/
INSERT INTO t_u_role (role_name,STATUS,role_desc,role_code) VALUES ('admin','N', '系统管理员','R123456789012345678');
INSERT INTO t_u_user_role (role_code,user_code,create_time) VALUES ('R123456789012345678','USadmin1234321232',NOW());
