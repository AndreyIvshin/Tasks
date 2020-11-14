create sequence category_seq start 1 increment 1;

create sequence order_seq start 1 increment 1;

create sequence product_seq start 1 increment 1;

create sequence user_seq start 1 increment 1;

create sequence vendor_seq start 1 increment 1;


create table category_products_table (
    category_fk int8 not null,
    product_fk int8 not null
);

create table category_table (
    category_id int8 not null,
    category_name varchar(255),
    primary key (category_id)
);

create table order_product_table (
    order_fk int8 not null,
    product_fk int8 not null
);

create table order_table (
    order_id int8 not null,
    order_complete boolean,
    order_created_at timestamp,
    order_ship_date timestamp,
    order_status varchar(255),
    primary key (order_id)
);

create table product_table (
    product_id int8 not null,
    product_name varchar(255),
    product_price int4,
    product_quantity int4,
    primary key (product_id)
);

create table user_order_table (
    user_fk int8 not null,
    order_fk int8 not null
);

create table user_table (
    user_id int8 not null,
    user_email varchar(255),
    user_first_name varchar(255),
    user_last_name varchar(255),
    user_password varchar(255),
    user_role varchar(255),
    user_username varchar(255),
    primary key (user_id)
);

create table vendor_product_table (
    vendor_fk int8 not null,
    product_fk int8 not null
);

create table vendor_table (
    vendor_id int8 not null,
    vendor_name varchar(255),
    primary key (vendor_id)
);


alter table if exists category_products_table add constraint UK_fhaayod6x4fwi7lxym224a9s1 unique (product_fk);

alter table if exists order_product_table add constraint UK_snkvf1wkidh3bg8eitsdmslc9 unique (product_fk);

alter table if exists user_order_table add constraint UK_9jremyww0vs14k6q9bh1l0t6v unique (order_fk);

alter table if exists vendor_product_table add constraint UK_oqq1erkhmqxv2bx3fn603o4l5 unique (product_fk);

alter table if exists category_products_table add constraint FKb9m83bhwxjk2xtecylvpdfs2t foreign key (product_fk) references product_table;

alter table if exists category_products_table add constraint FK98ccmk0kg12f4f2jcpibqn4eg foreign key (category_fk) references category_table;

alter table if exists order_product_table add constraint FKlfqbk8y4d9yv56mho9sjkhji5 foreign key (product_fk) references product_table;

alter table if exists order_product_table add constraint FKk6chxnnq9bovrrrx307ngc1qp foreign key (order_fk) references order_table;

alter table if exists user_order_table add constraint FKiamkf3i7o7ol4mas7tmnjpdwc foreign key (order_fk) references order_table;

alter table if exists user_order_table add constraint FKkqrp92eb66kdqvdqa7b17gy4b foreign key (user_fk) references user_table;

alter table if exists vendor_product_table add constraint FKtgdft1pmhc9oob2smkbjnbvsx foreign key (product_fk) references product_table;

alter table if exists vendor_product_table add constraint FK6sc7kc1aiwq5aq6oxkd7bnibv foreign key (vendor_fk) references vendor_table;