drop table if exists market_spread_classify;

/*==============================================================*/
/* Table: market_spread_classify                                */
/*==============================================================*/
create table market_spread_classify
(
   id                   bigint not null comment 'ID',
   name                 varchar(50) comment '名称',
   status               int comment '状态(1:启用,0:禁用)',
   create_time          datetime comment '创建时间',
   create_by            bigint comment '创建者',
   update_time          datetime comment '更新时间',
   update_by            bigint comment '更新者',
   primary key (id)
);

alter table market_spread_classify comment '营销推广分类';




if exists(select 1 from sys.sysforeignkey where role='FK_MARKET_S_SPREAD_CL_MARKET_S') then
    alter table market_spread_manager
       delete foreign key FK_MARKET_S_SPREAD_CL_MARKET_S
end if;

if exists(
   select 1 from sys.systable 
   where table_name='market_spread_manager'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table market_spread_manager
end if;

/*==============================================================*/
/* Table: market_spread_manager                                 */
/*==============================================================*/
create table market_spread_manager 
(
   id                   bigint                         not null,
   name                 varchar(50)                    null,
   classify_id          bigint                         null,
   create_by            bigint                         null,
   update_time          datetime                       null,
   update_by            bigint                         null,
   read_num             bigint                         null,
   author               varchar(20)                    null,
   spread_content_type  int                            null,
   content_type         int                            null,
   content              text                           null,
   good_id              bigint                         null,
   share_num            bigint                         null,
   collect_num          bigint                         null,
   constraint PK_MARKET_SPREAD_MANAGER primary key (id)
);

comment on table market_spread_manager is 
'营销推广分类';

comment on column market_spread_manager.id is 
'ID';

comment on column market_spread_manager.name is 
'名称';

comment on column market_spread_manager.classify_id is 
'创建时间';

comment on column market_spread_manager.create_by is 
'创建者';

comment on column market_spread_manager.update_time is 
'更新时间';

comment on column market_spread_manager.update_by is 
'更新者';

alter table market_spread_manager
   add constraint FK_MARKET_S_SPREAD_CL_MARKET_S foreign key (classify_id)
      references market_spread_classify (id)
      on update restrict
      on delete restrict;
	  
	  
/*==============================================================*/
/* 2016-7-19                              */
/*==============================================================*/	  
alter table indent add column ship_notice int(2) COMMENT '发货提醒';