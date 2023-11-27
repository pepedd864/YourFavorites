# 创建数据库
create database if not exists your_favorites default character set utf8mb4 collate utf8mb4_unicode_ci;
use your_favorites;

# 创建表
create table sys_user
(
    id          int auto_increment comment '用户id'
        primary key,
    username    varchar(20)  not null comment '用户名',
    nickname    varchar(30)  not null comment '昵称',
    password    varchar(100) not null comment 'BCrypt加密',
    phone       varchar(11)  not null comment '国内手机号',
    email       varchar(30)  not null comment '邮箱',
    create_time datetime     not null comment '创建时间'
);

create table tb_favorite
(
    id          int auto_increment comment '书签id'
        primary key,
    user_id     int          not null comment '用户id',
    tag_id      int          not null comment '标签id',
    title       varchar(50)  not null comment '书签名',
    `desc`      varchar(100) null comment '描述',
    level       int          not null comment '层级',
    `index`     int          not null comment '所在层级的id',
    `key`       varchar(255) not null comment '书签的层级路径(n-n-n-n)',
    create_time datetime     not null comment '创建时间',
    update_time datetime     null comment '更新时间',
    remark      varchar(255) null comment '备注'
);

create table tb_tag
(
    id          int auto_increment comment '标签id'
        primary key,
    user_id     int          not null comment '用户id',
    title       varchar(50)  not null comment '标签名',
    `desc`      varchar(100) null comment '描述',
    level       int          not null comment '层级',
    `index`     int          not null comment '标签的层级id',
    parent_id   int          not null comment '上一级的id',
    `key`       varchar(255) not null comment '标签的层级路径(n-n-n-n)',
    create_time datetime     not null comment '创建时间',
    update_time datetime     null comment '更新时间',
    remark      varchar(255) null comment '备注'
);

