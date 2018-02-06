/*==============================================================*/
/* Table: T_DICT_APP_LIST                                       */
/*==============================================================*/
create table T_DICT_APP_LIST
(
   id                   bigint not null auto_increment,
   appCode              varchar(20) not null,
   appName              varchar(50) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_DICT_APP_LIST comment 'App列表';

/*==============================================================*/
/* Index: index_app_code                                        */
/*==============================================================*/
create unique index index_app_code on T_DICT_APP_LIST
(
   appCode
);

/*==============================================================*/
/* Table: T_DICT_APP_PUBLISH                                    */
/*==============================================================*/
create table T_DICT_APP_PUBLISH
(
   id                   bigint not null auto_increment,
   app_code             varchar(20) not null,
   version_name         varchar(10) not null,
   version_code         int not null,
   file_code            varchar(50) not null,
   create_time          datetime not null,
   primary key (id)
)
engine = InnoDB;

alter table T_DICT_APP_PUBLISH comment 'APP发布版本列表';