/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/1/13 21:09:46                           */
/*==============================================================*/


/*==============================================================*/
/* Table: T_U_AUTH                                              */
/*==============================================================*/
create table T_U_AUTH
(
   id                   bigint not null auto_increment,
   auth_name            varchar(30) not null,
   auth_desc            varchar(200),
   auth_type            bit not null comment '1为部门权限，主要用于工作流
            0为全局权限，主要用于菜单控制',
   auth_code            varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

/*==============================================================*/
/* Index: AUTH_UNIQUE_CODE                                      */
/*==============================================================*/
create unique index AUTH_UNIQUE_CODE on T_U_AUTH
(
   auth_code
);

/*==============================================================*/
/* Table: T_U_CAPTCHA                                           */
/*==============================================================*/
create table T_U_CAPTCHA
(
   id                   bigint not null auto_increment,
   mobile_phone         varchar(15) not null,
   captcha              varchar(10) not null,
   create_time          datetime not null,
   primary key (id)
)
engine = InnoDB;

alter table T_U_CAPTCHA comment '验证码';

/*==============================================================*/
/* Table: T_U_DEPT                                              */
/*==============================================================*/
create table T_U_DEPT
(
   id                   bigint not null auto_increment,
   dept_name            varchar(30) not null,
   parent_dept_code     varchar(20),
   dept_desc            varchar(200),
   dept_code            varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

/*==============================================================*/
/* Index: DEPT_UNIQE_CODE                                       */
/*==============================================================*/
create unique index DEPT_UNIQE_CODE on T_U_DEPT
(
   dept_code
);

/*==============================================================*/
/* Table: T_U_ORG                                               */
/*==============================================================*/
create table T_U_ORG
(
   id                   bigint not null auto_increment,
   org_name             varchar(50) not null,
   short_name           varchar(20) not null,
   org_desc             varchar(200),
   org_code             varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_U_ORG comment '组织表，一般代表一个公司';

/*==============================================================*/
/* Index: ORG_UNIQE_CODE                                        */
/*==============================================================*/
create unique index ORG_UNIQE_CODE on T_U_ORG
(
   org_code
);

/*==============================================================*/
/* Table: T_U_ROLE                                              */
/*==============================================================*/
create table T_U_ROLE
(
   id                   bigint not null auto_increment,
   role_name            varchar(30),
   status               char(1) comment 'N：正常使用状态
            S：状态为禁用状态，需要管理员恢复',
   role_desc            varchar(200),
   role_code            varchar(20),
   primary key (id)
)
engine = InnoDB;

/*==============================================================*/
/* Index: ROLE_NAME_INDEX                                       */
/*==============================================================*/
create unique index ROLE_NAME_INDEX on T_U_ROLE
(
   role_code
);

/*==============================================================*/
/* Table: T_U_ROLE_AUTH                                         */
/*==============================================================*/
create table T_U_ROLE_AUTH
(
   id                   bigint not null auto_increment,
   role_code            varchar(20) not null,
   create_time          datetime not null,
   auth_code            varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_U_ROLE_AUTH comment '角色和权限的关联表';

/*==============================================================*/
/* Index: Index_role_auth_code                                  */
/*==============================================================*/
create unique index Index_role_auth_code on T_U_ROLE_AUTH
(
   role_code,
   auth_code
);

/*==============================================================*/
/* Table: T_U_USER                                              */
/*==============================================================*/
create table T_U_USER
(
   id                   bigint not null auto_increment,
   last_name            varchar(30) not null,
   login_name           varchar(30) not null,
   password             varchar(32) not null,
   status               char(1) not null comment 'N：正常使用状态
            S：状态为禁用状态，需要管理员恢复
            T：未认证状态，用户需通过页面认证
            L：被锁定状态，可通过页面或管理员解锁
            ',
   create_time          timestamp not null,
   user_desc            varchar(200),
   dept_code            varchar(20),
   email                varchar(50),
   first_name           varchar(30),
   access_code          varchar(64) not null,
   user_code            varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_U_USER comment '用户表，只包含基础的数据';

/*==============================================================*/
/* Index: USER_LOGIN_UNIQ                                       */
/*==============================================================*/
create unique index USER_LOGIN_UNIQ on T_U_USER
(
   login_name
);

/*==============================================================*/
/* Index: USER_CODE_UNIQ                                        */
/*==============================================================*/
create unique index USER_CODE_UNIQ on T_U_USER
(
   user_code
);

/*==============================================================*/
/* Index: USER_ACCESS_CODE_UNIQ                                 */
/*==============================================================*/
create unique index USER_ACCESS_CODE_UNIQ on T_U_USER
(
   access_code
);

/*==============================================================*/
/* Table: T_U_USER_AUTH                                         */
/*==============================================================*/
create table T_U_USER_AUTH
(
   id                   bigint not null auto_increment,
   auth_code            varchar(20) not null,
   create_time          datetime,
   user_code            varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

/*==============================================================*/
/* Index: Index_user_auth_code                                  */
/*==============================================================*/
create unique index Index_user_auth_code on T_U_USER_AUTH
(
   auth_code,
   user_code
);

/*==============================================================*/
/* Table: T_U_USER_ROLE                                         */
/*==============================================================*/
create table T_U_USER_ROLE
(
   id                   bigint not null auto_increment,
   role_code            varchar(20) not null,
   create_time          datetime,
   user_code            varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

/*==============================================================*/
/* Index: Index_user_role_code                                  */
/*==============================================================*/
create unique index Index_user_role_code on T_U_USER_ROLE
(
   role_code,
   user_code
);

