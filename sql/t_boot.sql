drop table if exists t_boot_area;

create table if not exists t_boot_area(
 id int not null auto_increment comment '主键',
 pid int not null comment 'parent id',
 name varchar(128) comment '地区名称',
 sort int comment '排序字段',
 primary key(id)
);


create table if not exists t_boot_house(
 id int not null auto_increment comment '主键',
 area_name varchar(128) not null comment '所在地区名称',
 rent_mode varchar(1) not null comment '租赁方式 1:整租,2:合租',
 house_type varchar(1) not null comment '户型',
 orientation varchar(1) not null comment '朝向',
rental int not null comment '租金',
province int not null comment '省id',
city int not null comment '市id',
area int not null comment '区县id',
address varchar(256) not null comment '地址',
pic varchar(128) not null comment '图片地址',
publish_time datetime not null comment '发布时间',
primary key(id)

);
