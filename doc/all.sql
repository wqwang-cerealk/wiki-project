drop table if exists `test`;
create table `test` (
    `id` bigint not null comment 'id',
    `name` varchar(50) comment 'name',
    `password` varchar(50) comment 'password',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='test';

insert into `test` (id, name, password) values (1, 'alex', 'password');

drop table if exists `demo`;
create table `demo` (
    `id` bigint not null comment 'id',
    `name` varchar(50) comment 'name',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='demo';

insert into `demo` (id, name) values (1, 'alex');

drop table if exists `ebook`;
create table `ebook` (
    `id` bigint not null comment 'id',
    `name` varchar(50) comment 'name',
    `category1_id` bigint comment 'category1',
    `category2_id` bigint comment 'category2',
    `description` varchar(200) comment 'description',
    `cover` varchar(200) comment 'cover page',
    `doc_count` int comment 'count of doc',
    `view_count` int comment 'count of view',
    `vote_count` int comment 'count of vote',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='ebook';

insert into `ebook` (id, name, description) values (1, 'SpringBoot Tutorial', 'Introduce how to create a SpringBoot project');
insert into `ebook` (id, name, description) values (2, 'Vue Tutorial', 'Introduce how to build front end with Vue');
insert into `ebook` (id, name, description) values (3, 'Python Tutorial', 'Introduction to programming with Python');
insert into `ebook` (id, name, description) values (4, 'MySQL Tutorial', 'Introduction to mySQL database');
insert into `ebook` (id, name, description) values (5, 'Oracle Tutorial', 'Introduction to Oracle');