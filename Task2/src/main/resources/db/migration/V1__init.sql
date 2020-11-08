create sequence news_sequence
start with 1 increment by  50;

create sequence user_sequence
start with 1 increment by  50;

create table news_table (
    news_id number(19,0) not null,
    news_brief varchar2(500 char),
    news_content long, news_date timestamp,
    news_title varchar2(100 char),
    primary key (news_id)
);

create table user_table (
    user_id number(19,0) not null,
    user_password varchar2(255 char),
    user_role varchar2(255 char),
    user_username varchar2(40 char),
    primary key (user_id)
);