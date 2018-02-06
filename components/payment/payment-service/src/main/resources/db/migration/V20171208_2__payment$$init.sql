/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/1/10 10:31:10                           */
/*==============================================================*/


/*==============================================================*/
/* Table: T_PAY_BALANCE                                         */
/*==============================================================*/
create table T_PAY_BALANCE
(
   id                   bigint not null auto_increment,
   balance_amount       decimal(10,2) not null,
   last_modify_date     datetime,
   user_code            varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_PAY_BALANCE comment '资金余额表';

/*==============================================================*/
/* Index: Index_balance_user_code                               */
/*==============================================================*/
create unique index Index_balance_user_code on T_PAY_BALANCE
(
   user_code
);

/*==============================================================*/
/* Table: T_PAY_CONFIG                                          */
/*==============================================================*/
create table T_PAY_CONFIG
(
   id                   bigint not null auto_increment,
   value                varchar(100) not null,
   conf_desc            varchar(200),
   code                 varchar(10) not null,
   primary key (id)
);

alter table T_PAY_CONFIG comment '支付配置表';

/*==============================================================*/
/* Index: index_conf_code                                       */
/*==============================================================*/
create unique index index_conf_code on T_PAY_CONFIG
(
   code
);

/*==============================================================*/
/* Table: T_PAY_MONEY_FLOW                                      */
/*==============================================================*/
create table T_PAY_MONEY_FLOW
(
   id                   bigint not null auto_increment,
   amount               decimal(10,2) not null,
   unit                 varchar(10),
   record_time          datetime not null,
   order_record_code    varchar(20) not null,
   busi_type            varchar(10) not null comment 'BZJ:保证金交易
            RC：业务交易
            TH：退货交易
            TX：提现交易',
   busi_user_code       varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_PAY_MONEY_FLOW comment '资金流水表';

/*==============================================================*/
/* Index: Index_money_flow_user_code                            */
/*==============================================================*/
create index Index_money_flow_user_code on T_PAY_MONEY_FLOW
(
   busi_user_code
);

/*==============================================================*/
/* Table: T_PAY_ORDER                                           */
/*==============================================================*/
create table T_PAY_ORDER
(
   id                   bigint not null auto_increment,
   from_user_code       varchar(20) not null,
   to_user_code         varchar(20),
   pay_id               varchar(20) not null,
   amount               decimal(10,2) not null,
   unit                 varchar(10),
   record_time          datetime not null,
   busi_type            varchar(10) not null comment 'BZJ:保证金交易
            RC：业务交易
            TH：退货交易
            TX：提现交易',
   create_time          datetime not null,
   order_status         varchar(10) not null comment 'SUCC：成功完成
            NEW:新建
            FAIL:交易失败
            FIN:交易完成',
   busi_code            varchar(20) not null,
   order_record_code    varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_PAY_ORDER comment '订单交易记录';

/*==============================================================*/
/* Index: order_record_code_index                               */
/*==============================================================*/
create unique index order_record_code_index on T_PAY_ORDER
(
   order_record_code
);

/*==============================================================*/
/* Index: Index_order_require_code                              */
/*==============================================================*/
create index Index_order_require_code on T_PAY_ORDER
(
   busi_code
);

/*==============================================================*/
/* Index: Index_order_from_user_code                            */
/*==============================================================*/
create index Index_order_from_user_code on T_PAY_ORDER
(
   from_user_code
);

/*==============================================================*/
/* Index: Index_order_to_user_code                              */
/*==============================================================*/
create index Index_order_to_user_code on T_PAY_ORDER
(
   to_user_code
);

/*==============================================================*/
/* Table: T_PAY_PAYMENT                                         */
/*==============================================================*/
create table T_PAY_PAYMENT
(
   id                   bigint not null auto_increment,
   busi_code            varchar(20) not null comment '支付相关的业务主键',
   pay_user_code        varchar(20) not null comment '支付相关的业务用户主键',
   pay_status           varchar(10) not null comment 'SUCC：成功完成
            NEW:新建
            FAIL:交易失败
            FIN:交易完成',
   pay_way              varchar(5) not null comment '微信：WX
            支付宝：ALI',
   pay_amount           decimal(10,2) not null,
   pay_unit             varchar(10),
   payee_user_id        varchar(32) comment '支付平台返回的用户ID',
   payee_user_name      varchar(50),
   payee_id             varchar(32),
   payee_account_no     varchar(100),
   pay_init_time        datetime not null,
   payee_result_time    datetime,
   payee_error_code     varchar(20),
   pay_id               varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_PAY_PAYMENT comment '支付记录表';

/*==============================================================*/
/* Index: index_user                                            */
/*==============================================================*/
create index index_user on T_PAY_PAYMENT
(
   pay_user_code,
   pay_init_time
);

/*==============================================================*/
/* Index: index_busi                                            */
/*==============================================================*/
create index index_busi on T_PAY_PAYMENT
(
   busi_code
);

/*==============================================================*/
/* Index: Index_payment_code                                    */
/*==============================================================*/
create unique index Index_payment_code on T_PAY_PAYMENT
(
   pay_id
);

/*==============================================================*/
/* Table: T_PAY_USER_BANK                                       */
/*==============================================================*/
create table T_PAY_USER_BANK
(
   id                   bigint not null auto_increment,
   user_code            varchar(20) not null,
   account_name         varchar(30) not null,
   bank                 varchar(50) not null,
   account_no           varchar(30) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_PAY_USER_BANK comment '用户银行信息表';

/*==============================================================*/
/* Table: T_PAY_WITHDRAW                                        */
/*==============================================================*/
create table T_PAY_WITHDRAW
(
   id                   bigint not null auto_increment,
   wd_account_name      varchar(30) not null,
   wd_bank              varchar(50) not null,
   wd_account_no        varchar(100) not null,
   wd_amount            decimal(10,2) not null,
   wd_unit              varchar(10),
   wd_init_time         datetime not null,
   wd_status            varchar(10) not null,
   wd_result_time       datetime,
   wd_error_code        varchar(20),
   wd_way               varchar(10),
   pay_bank_id          varchar(30),
   busi_user_code       varchar(20) not null,
   wd_code              varchar(20) not null,
   primary key (id)
)
engine = InnoDB;

alter table T_PAY_WITHDRAW comment '提现记录表';

/*==============================================================*/
/* Index: Index_withdraw_user_code                              */
/*==============================================================*/
create index Index_withdraw_user_code on T_PAY_WITHDRAW
(
   busi_user_code
);

/*==============================================================*/
/* Index: index_withdraw_code                                   */
/*==============================================================*/
create unique index index_withdraw_code on T_PAY_WITHDRAW
(
   wd_code
);
