create table device_definitions
(
    DEVICE_DEFINITION_ID   bigint auto_increment
        primary key,
    DEVICE_DEFINITION_NAME tinytext not null
);

create table function_definitions
(
    FUNCTION_DEFINITION_ID bigint auto_increment
        primary key,
    FUNCTION_NAME          tinytext not null,
    FUNCTION_TYPE          bit      not null,
    DEVICE_DEFINITION_ID   bigint   not null,
    constraint DEVICE_DEFINITION_ID
        foreign key (DEVICE_DEFINITION_ID) references device_definitions (DEVICE_DEFINITION_ID)
);

create table home
(
    HOME_ID      bigint auto_increment
        primary key,
    HOME_NAME    text       null,
    HOME_ADDRESS mediumtext null
);

create table device
(
    DEVICE_ID                   bigint auto_increment
        primary key,
    DEVICE_DEFINITION_TO_DEVICE bigint   null,
    HOME_PLACED_ID              bigint   null,
    DEVICE_NAME                 tinytext null,
    constraint DEVICE_DEFINITIONS_TO_DEVICE
        foreign key (DEVICE_DEFINITION_TO_DEVICE) references device_definitions (DEVICE_DEFINITION_ID),
    constraint HOME_PLACED_ID
        foreign key (HOME_PLACED_ID) references home (HOME_ID)
);

create table functions
(
    FUNCTION_ID            bigint auto_increment
        primary key,
    F_BOOL                 bit    null,
    F_INT                  int    null,
    F_STRING               text   null,
    FUNCTION_DEFINITION_ID bigint null,
    FUNCTION_DEVICE_ID     bigint null,
    constraint FUNCTION_DEFINITION_ID
        foreign key (FUNCTION_DEFINITION_ID) references function_definitions (FUNCTION_DEFINITION_ID),
    constraint FUNCTION_DEVICE_ID
        foreign key (FUNCTION_DEVICE_ID) references device (DEVICE_ID)
);

create table user
(
    USER_ID         bigint auto_increment
        primary key,
    USER_LOGIN      varchar(40)  not null,
    USER_PASSWORD   varchar(100) not null,
    USER_ROLE       int          not null,
    USER_FIRST_NAME tinytext     null,
    USER_LAST_NAME  tinytext     null,
    USER_IMAGE_URL  text         null
);

create table user_to_home
(
    USER_ID        bigint not null,
    HOME_ID        bigint not null,
    USER_HOME_ROLE int    not null,
    primary key (USER_ID, HOME_ID),
    constraint HOME_ID
        foreign key (HOME_ID) references home (HOME_ID)
            on update cascade on delete cascade,
    constraint USER_ID
        foreign key (USER_ID) references user (USER_ID)
            on update cascade on delete cascade
);

