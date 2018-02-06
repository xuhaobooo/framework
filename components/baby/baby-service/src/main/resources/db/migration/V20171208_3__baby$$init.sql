/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/1/19 13:46:24                           */
/*==============================================================*/


/*==============================================================*/
/* Table: T_BABY_APPLY                                          */
/*==============================================================*/
create table T_BABY_APPLY
(
   id                   bigint not null auto_increment,
   user_code            varchar(20) not null comment '申请用户编号',
   create_time          datetime not null comment '申请时间',
   apply_status         varchar(5) not null comment 'PND:待确认
            SUCC：被选中
            FAIL：未被选中',
   require_code         varchar(20) not null comment '需求编号',
   primary key (id)
)
engine = InnoDB;

alter table T_BABY_APPLY comment '申请信息表，机构的申请记录';

/*==============================================================*/
/* Index: IndexIndex_require_require                            */
/*==============================================================*/
create index IndexIndex_require_require on T_BABY_APPLY
(
   require_code
);

/*==============================================================*/
/* Index: Index_apply_user                                      */
/*==============================================================*/
create index Index_apply_user on T_BABY_APPLY
(
   user_code
);

/*==============================================================*/
/* Table: T_BABY_EVALUATION                                     */
/*==============================================================*/
create table T_BABY_EVALUATION
(
   id                   bigint not null auto_increment,
   send_user_code       varchar(20) not null,
   receive_user_code    varchar(20) not null,
   level                varchar(10) not null,
   notes                text,
   create_time          datetime not null,
   require_code         varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_BABY_EVALUATION comment '评价表';

/*==============================================================*/
/* Index: Index_evaluation_require_code                         */
/*==============================================================*/
create index Index_evaluation_require_code on T_BABY_EVALUATION
(
   require_code
);

/*==============================================================*/
/* Index: Index_evaluation_from_user                            */
/*==============================================================*/
create index Index_evaluation_from_user on T_BABY_EVALUATION
(
   send_user_code
);

/*==============================================================*/
/* Index: Index_evaluation_receive_user                         */
/*==============================================================*/
create index Index_evaluation_receive_user on T_BABY_EVALUATION
(
   receive_user_code
);

/*==============================================================*/
/* Table: T_BABY_INFO                                           */
/*==============================================================*/
create table T_BABY_INFO
(
   id                   bigint not null auto_increment,
   baby_name            varchar(50) not null,
   baby_birthday        date not null,
   baby_sex             varchar(3) not null comment 'F为女，M为男',
   create_time          datetime not null,
   baby_code            varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_BABY_INFO comment '宝贝信息表';

/*==============================================================*/
/* Index: Index_baby_info_code                                  */
/*==============================================================*/
create unique index Index_baby_info_code on T_BABY_INFO
(
   baby_code
);

/*==============================================================*/
/* Table: T_BABY_INVITE                                         */
/*==============================================================*/
create table T_BABY_INVITE
(
   id                   bigint not null auto_increment,
   user_code            varchar(20) not null,
   invite_user_code     varchar(20) not null,
   create_time          datetime not null,
   enable               bit(1) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_BABY_INVITE comment '邀请信息';

/*==============================================================*/
/* Index: INDEX_INVITE_USER_CODE                                */
/*==============================================================*/
create unique index INDEX_INVITE_USER_CODE on T_BABY_INVITE
(
   user_code
);

/*==============================================================*/
/* Table: T_BABY_ITEM_LIST                                      */
/*==============================================================*/
create table T_BABY_ITEM_LIST
(
   id                   bigint not null auto_increment,
   item_name            varchar(50) not null,
   item_price           float not null,
   enable_flag          bit not null,
   item_code            varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_BABY_ITEM_LIST comment '需求可选项目列表';

/*==============================================================*/
/* Index: Index_require_item_dict                               */
/*==============================================================*/
create unique index Index_require_item_dict on T_BABY_ITEM_LIST
(
   item_code
);

/*==============================================================*/
/* Table: T_BABY_PAYMENT_RECORD                                 */
/*==============================================================*/
create table T_BABY_PAYMENT_RECORD
(
   id                   bigint not null auto_increment,
   payment_type         varchar(10) not null,
   amount               float not null,
   create_time          datetime not null,
   payment_status       varchar(10) not null comment 'NEW:创建，无反馈
            SUCC:支付成功
            FAIL:支付失败
            FIN:已完成',
   last_update_time     datetime,
   busi_payment_code1   varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_BABY_PAYMENT_RECORD comment '支付信息';

/*==============================================================*/
/* Table: T_BABY_REQUIRE                                        */
/*==============================================================*/
create table T_BABY_REQUIRE
(
   id                   bigint not null auto_increment,
   start_time           datetime not null,
   end_time             datetime not null,
   addr_name            varchar(50) not null,
   addr_pos_x           double,
   addr_pos_y           double,
   baby_name            varchar(30) not null,
   baby_age             int not null,
   baby_sex             varchar(3) not null comment 'F为女，M为男',
   create_time          datetime not null,
   require_code         varchar(20) not null,
   credit_code          varchar(10) not null,
   pay_more             float not null,
   fee_amount           float not null,
   require_status       varchar(10) not null comment 'NEW：订单已生成，尚未开始
            CONF：已确认
            ARRV：已到达
            PF：机构确认完成
            CF：用户确认完成
            AF: 订单完成',
   user_code            varchar(20) not null comment '关联T_U_USER中的用户编号',
   primary key (id)
)
engine = InnoDB;

alter table T_BABY_REQUIRE comment '用户需求表';

/*==============================================================*/
/* Index: Index_require_code                                    */
/*==============================================================*/
create unique index Index_require_code on T_BABY_REQUIRE
(
   require_code
);

/*==============================================================*/
/* Index: Index_require_status                                  */
/*==============================================================*/
create index Index_require_status on T_BABY_REQUIRE
(
   require_status
);

/*==============================================================*/
/* Index: Index_require_time                                    */
/*==============================================================*/
create index Index_require_time on T_BABY_REQUIRE
(
   start_time,
   end_time
);

/*==============================================================*/
/* Table: T_BABY_REQUIRE_ITEMS                                  */
/*==============================================================*/
create table T_BABY_REQUIRE_ITEMS
(
   id                   bigint not null auto_increment,
   item_code            varchar(20) not null,
   item_name            varchar(50) not null,
   item_price           float not null,
   require_code         varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_BABY_REQUIRE_ITEMS comment '用户需求表-项目';

/*==============================================================*/
/* Index: Index_require_item                                    */
/*==============================================================*/
create index Index_require_item on T_BABY_REQUIRE_ITEMS
(
   require_code
);

/*==============================================================*/
/* Table: T_BABY_TASKS                                          */
/*==============================================================*/
create table T_BABY_TASKS
(
   id                   bigint not null auto_increment,
   require_code         varchar(20) not null,
   send_user_code       varchar(20) not null,
   get_user_code        varchar(20) not null,
   task_status          varchar(5) not null comment 'CONF：已确认
            ARRV：已到达
            PAID：已存保证金
            PF：完成待确认
            CF：完成',
   start_time           datetime not null,
   end_time             datetime,
   create_time          datetime,
   task_code            varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_BABY_TASKS comment '任务记录表';

/*==============================================================*/
/* Index: Index_task_code                                       */
/*==============================================================*/
create unique index Index_task_code on T_BABY_TASKS
(
   task_code
);

/*==============================================================*/
/* Index: Index_task_require_code                               */
/*==============================================================*/
create unique index Index_task_require_code on T_BABY_TASKS
(
   require_code
);

/*==============================================================*/
/* Index: Index_task_receive_user_code                          */
/*==============================================================*/
create index Index_task_receive_user_code on T_BABY_TASKS
(
   get_user_code
);

/*==============================================================*/
/* Table: T_BABY_TASK_RECORD                                    */
/*==============================================================*/
create table T_BABY_TASK_RECORD
(
   id                   bigint not null auto_increment,
   step_content         varchar(50) not null,
   done_time            datetime not null,
   create_time          datetime,
   task_code            varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_BABY_TASK_RECORD comment '任务过程记录表';

/*==============================================================*/
/* Index: Index_task_step_code                                  */
/*==============================================================*/
create index Index_task_step_code on T_BABY_TASK_RECORD
(
   task_code
);

/*==============================================================*/
/* Table: T_BABY_USER_BABY                                      */
/*==============================================================*/
create table T_BABY_USER_BABY
(
   id                   bigint not null auto_increment,
   user_code            varchar(20) not null,
   baby_code            varchar(20) not null,
   create_time          datetime not null,
   primary key (id)
)
engine = InnoDB;

alter table T_BABY_USER_BABY comment '用户-宝贝关系表';

/*==============================================================*/
/* Index: Index_user_baby                                       */
/*==============================================================*/
create unique index Index_user_baby on T_BABY_USER_BABY
(
   user_code,
   baby_code
);

/*==============================================================*/
/* Table: T_BABY_USER_INFO                                      */
/*==============================================================*/
create table T_BABY_USER_INFO
(
   id                   bigint not null auto_increment,
   addr_name            varchar(50) not null,
   addr_pos_x           double,
   addr_pos_y           double,
   user_role            varchar(10) not null comment 'DD:爸爸
            MM:妈妈
            NN:奶奶
            YY:爷爷
            WG:外公
            WP:外婆',
   tel                  varchar(15) not null,
   note                 varchar(255),
   user_name            varchar(30) not null,
   user_code            varchar(20) not null comment '关联T_U_USER中的用户编号',
   primary key (id)
)
engine = InnoDB;

alter table T_BABY_USER_INFO comment '用户信息表';

/*==============================================================*/
/* Index: Index_user_info_code                                  */
/*==============================================================*/
create unique index Index_user_info_code on T_BABY_USER_INFO
(
   user_code
);

/*==============================================================*/
/* Index: index_user_info_tel                                   */
/*==============================================================*/
create unique index index_user_info_tel on T_BABY_USER_INFO
(
   tel
);

/*==============================================================*/
/* Table: t_baby_time_price                                     */
/*==============================================================*/
create table t_baby_time_price
(
   id                   bigint not null auto_increment,
   price                double not null,
   last_update_time     datetime,
   price_code           varchar(30) not null,
   primary key (id)
)
engine = InnoDB;

alter table t_baby_time_price comment '工时价格配置表';

/*==============================================================*/
/* Index: index_price_code                                      */
/*==============================================================*/
create unique index index_price_code on t_baby_time_price
(
   price_code
);

