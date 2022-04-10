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


# categorize
drop table if exists `category`;
create table `category` (
                            `id` bigint not null comment 'id',
                            `parent` bigint not null default 0 comment 'parent id',
                            `name` varchar(50) not null comment 'name',
                            `sort` int comment 'sort',
                            primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='categorize';

insert into `category` (id, parent, name, sort) values (100, 000, 'Front end development', 100);
insert into `category` (id, parent, name, sort) values (101, 100, 'Vue', 101);
insert into `category` (id, parent, name, sort) values (102, 100, 'HTML & CSS', 102);
insert into `category` (id, parent, name, sort) values (200, 000, 'Java', 200);
insert into `category` (id, parent, name, sort) values (201, 200, 'Basics', 201);
insert into `category` (id, parent, name, sort) values (202, 200, 'Frameworks', 202);
insert into `category` (id, parent, name, sort) values (300, 000, 'Python', 300);
insert into `category` (id, parent, name, sort) values (301, 300, 'Basic application', 301);
insert into `category` (id, parent, name, sort) values (302, 300, 'Advanced application', 302);
insert into `category` (id, parent, name, sort) values (400, 000, 'Database', 400);
insert into `category` (id, parent, name, sort) values (401, 400, 'MySQL', 401);
insert into `category` (id, parent, name, sort) values (500, 000, 'Others', 500);
insert into `category` (id, parent, name, sort) values (501, 500, 'Servers', 501);
insert into `category` (id, parent, name, sort) values (502, 500, 'Tools', 502);
insert into `category` (id, parent, name, sort) values (503, 500, 'Languages', 503);

-- document table
drop table if exists `doc`;
create table `doc` (
                       `id` bigint not null comment 'id',
                       `ebook_id` bigint not null default 0 comment 'ebook id',
                       `parent` bigint not null default 0 comment 'parent id',
                       `name` varchar(50) not null comment 'name',
                       `sort` int comment 'sort',
                       `view_count` int default 0 comment 'view count',
                       `vote_count` int default 0 comment 'like count',
                       primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='document';

insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (1, 1, 0, 'document1', 1, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (2, 1, 1, 'document1.1', 1, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (3, 1, 0, 'document2', 2, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (4, 1, 3, 'document2.1', 1, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (5, 1, 3, 'document2.2', 2, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (6, 1, 5, 'document2.2.1', 1, 0, 0);

-- doc content
drop table if exists `content`;
create table `content` (
                           `id` bigint not null comment 'id',
                           `content` mediumtext not null comment 'content',
                           primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='doc content';

drop table if exists `user`;
create table `user` (
                        `id` bigint not null comment 'ID',
                        `login_name` varchar(50) not null comment 'login_name',
                        `name` varchar(50) comment 'name',
                        `password` char(32) not null comment 'password',
                        primary key (`id`),
                        unique key `login_name_unique` (`login_name`)
) engine=innodb default charset=utf8mb4 comment='user';

insert into `user` (id, `login_name`, `name`, `password`) values (1, 'test1', 'test1', '7354a1d413535a6c0dc5c209e198d799');