drop table if exists event_online_study_type;

/*==============================================================*/
/* Table: event_online_study_type                               */
/*==============================================================*/
create table event_online_study_type
(
   id                   bigint not null comment 'ID',
   grade                bigint comment '级数',
   parent_id            bigint comment '父类别ID',
   code                 varchar(50) comment '编码',
   name                 varchar(50) comment '名称',
   status               int comment '状态',
   create_time          datetime comment '创建时间',
   create_by            bigint comment '创建者',
   update_time          datetime comment '更新时间',
   update_by            bigint comment '更新者',
   primary key (id)
);

alter table event_online_study_type comment '在线学习分类';

alter table event_online_study_type add constraint fk_online_study_parent_id foreign key (parent_id)
      references event_online_study_type (id) on delete restrict on update restrict;
