create table person
(
    name      char(50) null,
    surname   char(50) null,
    phone     char(50) null,
    person_id int auto_increment
        primary key
);

create table post
(
    post_name char(50) not null,
    cost      double   null,
    post_id   int auto_increment
        primary key
);

create table property
(
    name        char(50) not null,
    value       char(50) null,
    property_id int auto_increment
        primary key
);

create table rack
(
    name   char(50)                                                           not null,
    place  enum ('STORAGE', 'HALL')                                           not null,
    color  enum ('red', 'blue', 'green', 'yellow', 'black', 'grey', 'purple') null,
    coordX double                                                             null,
    coordY double                                                             null,
    primary key (name, place)
);

create table shell
(
    rack_name   char(50)                 not null,
    shell_id    char(50)                 not null
        primary key,
    place       enum ('STORAGE', 'HALL') not null,
    shell_index int default 0            not null,
    capacity    double                   null,
    constraint FK_shell_rack
        foreign key (rack_name) references rack (name)
);

create table product
(
    property_id   int      null,
    cost          int      null,
    code          int      not null
        primary key,
    shell_id      char(50) null,
    name          char(50) null,
    number        double   null,
    nominal_price double   null,
    weight        double   null,
    constraint FK_product_property
        foreign key (property_id) references property (property_id),
    constraint FK_product_shell
        foreign key (shell_id) references shell (shell_id)
            on update cascade on delete set null
);

create table sales
(
    profit   double null,
    expences double null,
    amount   int    null,
    sales_id int auto_increment
        primary key,
    date     date   null,
    code     int    null,
    constraint FK_sales_product
        foreign key (code) references product (code)
);

create table user
(
    login     char(50) null,
    password  blob     null,
    user_id   int auto_increment
        primary key,
    person_id int      null,
    post_id   int      null,
    minutes   int      null,
    constraint FK_user_person
        foreign key (person_id) references person (person_id)
            on update cascade on delete cascade,
    constraint FK_user_post
        foreign key (post_id) references post (post_id)
            on delete cascade
);

create table salaries
(
    salary    double null,
    salary_id int auto_increment
        primary key,
    date      date   null,
    user_id   int    null,
    constraint FK_salaries_user
        foreign key (user_id) references user (user_id)
);

create table shop
(
    address   char(50)                          null,
    name      char(50)                          not null
        primary key,
    sales_id  int                               null,
    currency  enum ('RUB', 'BYR', 'EUR', 'USD') null,
    salary_id int                               null,
    constraint FK_shop_salaries
        foreign key (salary_id) references salaries (salary_id),
    constraint FK_shop_sales
        foreign key (sales_id) references sales (sales_id)
);

create table work
(
    type    enum ('ADDSHELL', 'EDITSHELL', 'DELETESHELL', 'SHOWSHELL', 'ADDRACK', 'EDITRACK', 'DELETERACK', 'SHOWRACK', 'ADDPRODUCT', 'EDITPRODUCT', 'DELETEPRODUCT', 'SHOWPRODUCT', 'ADDPRODUCTPLACE', 'ADDPROPERTY', 'EDITPROPERTY', 'DELETEPROPERTY', 'SHOWPROPERTY') null,
    work_id int auto_increment
        primary key,
    post_id int                                                                                                                                                                                                                                                          null,
    constraint FK_work_post
        foreign key (post_id) references post (post_id)
            on delete set null
);



