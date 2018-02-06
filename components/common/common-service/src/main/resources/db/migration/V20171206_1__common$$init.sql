/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/12/15 21:19:08                          */
/*==============================================================*/


/*==============================================================*/
/* Table: T_DICT_OPTION                                         */
/*==============================================================*/
create table T_DICT_OPTION
(
   id                   bigint not null auto_increment,
   DIC_CODE             varchar(20) not null,
   DIC_LABEL            varchar(30) not null,
   DIC_DESC             varchar(100),
   DIC_CLASS_NAME       varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_DICT_OPTION comment '所有的选项字典表';

/*==============================================================*/
/* Index: Index_dict_classname                                  */
/*==============================================================*/
create index Index_dict_classname on T_DICT_OPTION
(
   DIC_CLASS_NAME
);

/*==============================================================*/
/* Table: T_LOG_T_TRACER                                        */
/*==============================================================*/
create table T_LOG_T_TRACER
(
   id                   bigint not null auto_increment,
   sub_module           varchar(30) not null,
   business_id          bigint not null,
   created_time         datetime not null,
   action               varchar(10) not null,
   created_user         varchar(20) not null,
   content              text not null,
   extra_id             bigint comment '用来存贮扩展数据，可用作数据关联',
   module               varchar(30) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_LOG_T_TRACER comment '用户操作日志记录表';

/*==============================================================*/
/* Index: Index_log_user_code                                   */
/*==============================================================*/
create index Index_log_user_code on T_LOG_T_TRACER
(
   created_user
);

/*==============================================================*/
/* Index: Index_log_module_code                                 */
/*==============================================================*/
create index Index_log_module_code on T_LOG_T_TRACER
(
   module
);

