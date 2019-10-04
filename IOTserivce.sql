CREATE DATABASE IOT_DATABASE;

USE IOT_DATABASE;

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
    FUNCTION_DATATYPE      tinytext null,
    FUNCTION_TYPE          bit      not null
);

create table definitions_many_to_many
(
    M_FUNCTION_DEFINITION_ID bigint not null,
    M_DEVICE_DEFINITION_ID   bigint not null,
    M2M_ID                   bigint auto_increment,
    constraint definitions_many_to_many_M2M_ID_uindex
        unique (M2M_ID),
    constraint M_DEVICE_DEFINITION_ID
        foreign key (M_DEVICE_DEFINITION_ID) references device_definitions (DEVICE_DEFINITION_ID),
    constraint M_FUNCTION_DEFINITION_ID
        foreign key (M_FUNCTION_DEFINITION_ID) references function_definitions (FUNCTION_DEFINITION_ID)
);

alter table definitions_many_to_many
    add primary key (M2M_ID);

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
    constraint DEVICE_DEFINITION_TO_DEVICE
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
    FUNCTION_DEFINITION_ID bigint not null,
    FUNCTION_DEVICE_ID     bigint not null,
    constraint FUNCTION_DEFINITION_ID
        foreign key (FUNCTION_DEFINITION_ID) references function_definitions (FUNCTION_DEFINITION_ID),
    constraint FUNCTION_DEVICE_ID
        foreign key (FUNCTION_DEVICE_ID) references device (DEVICE_ID)
);

create table language
(
    LANGUAGE_ID     bigint auto_increment
        primary key,
    LANGUAGE_NAME   tinytext null,
    LANGUAGE_LOCALE tinytext null
);

create table user
(
    USER_ID       bigint auto_increment
        primary key,
    USER_LOGIN    varchar(40)  not null,
    USER_PASSWORD varchar(100) not null,
    USER_ROLE     int          not null,
    USER_BLOCKED  bit          null
);

create table user_to_home
(
    USER_ID        bigint not null,
    HOME_ID        bigint not null,
    USER_HOME_ROLE int    not null,
    primary key (USER_ID, HOME_ID),
    constraint HOME_ID
        foreign key (HOME_ID) references home (HOME_ID),
    constraint USER_ID
        foreign key (USER_ID) references user (USER_ID)
);

INSERT INTO iot_database.device_definitions (DEVICE_DEFINITION_NAME) VALUES ('key.doorSensor');
INSERT INTO iot_database.device_definitions (DEVICE_DEFINITION_NAME) VALUES ('key.tempSensor');
INSERT INTO iot_database.device_definitions (DEVICE_DEFINITION_NAME) VALUES ('key.heater');
INSERT INTO iot_database.device_definitions (DEVICE_DEFINITION_NAME) VALUES ('key.conditioner');
INSERT INTO iot_database.device_definitions (DEVICE_DEFINITION_NAME) VALUES ('key.light');
INSERT INTO iot_database.device_definitions (DEVICE_DEFINITION_NAME) VALUES ('key.soundBuffer');
INSERT INTO iot_database.device_definitions (DEVICE_DEFINITION_NAME) VALUES ('key.doorBell');
INSERT INTO iot_database.device_definitions (DEVICE_DEFINITION_NAME) VALUES ('key.motionSensor');
INSERT INTO iot_database.device_definitions (DEVICE_DEFINITION_NAME) VALUES ('key.door');
INSERT INTO iot_database.device_definitions (DEVICE_DEFINITION_NAME) VALUES ('key.safetyMode');

INSERT INTO iot_database.function_definitions (FUNCTION_NAME, FUNCTION_DATATYPE, FUNCTION_TYPE) VALUES ('key.functionOpened', 'BOOL', false);
INSERT INTO iot_database.function_definitions (FUNCTION_NAME, FUNCTION_DATATYPE, FUNCTION_TYPE) VALUES ('key.functionInfo', 'INTEGER', false);
INSERT INTO iot_database.function_definitions (FUNCTION_NAME, FUNCTION_DATATYPE, FUNCTION_TYPE) VALUES ('key.functiononOrOff', 'BOOL', true);
INSERT INTO iot_database.function_definitions (FUNCTION_NAME, FUNCTION_DATATYPE, FUNCTION_TYPE) VALUES ('key.functionTrackName', 'TEXT', false);
INSERT INTO iot_database.function_definitions (FUNCTION_NAME, FUNCTION_DATATYPE, FUNCTION_TYPE) VALUES ('key.functionControl', 'INTEGER', true);
INSERT INTO iot_database.function_definitions (FUNCTION_NAME, FUNCTION_DATATYPE, FUNCTION_TYPE) VALUES ('key.functionMotion', 'BOOL', false);
INSERT INTO iot_database.function_definitions (FUNCTION_NAME, FUNCTION_DATATYPE, FUNCTION_TYPE) VALUES ('key.functionOpenOrClose', 'BOOL', true);
INSERT INTO iot_database.function_definitions (FUNCTION_NAME, FUNCTION_DATATYPE, FUNCTION_TYPE) VALUES ('key.functiononOrOff', 'BOOL', false);

INSERT INTO iot_database.definitions_many_to_many (M_FUNCTION_DEFINITION_ID, M_DEVICE_DEFINITION_ID) VALUES (1, 1);
INSERT INTO iot_database.definitions_many_to_many (M_FUNCTION_DEFINITION_ID, M_DEVICE_DEFINITION_ID) VALUES (2, 2);
INSERT INTO iot_database.definitions_many_to_many (M_FUNCTION_DEFINITION_ID, M_DEVICE_DEFINITION_ID) VALUES (3, 3);
INSERT INTO iot_database.definitions_many_to_many (M_FUNCTION_DEFINITION_ID, M_DEVICE_DEFINITION_ID) VALUES (3, 4);
INSERT INTO iot_database.definitions_many_to_many (M_FUNCTION_DEFINITION_ID, M_DEVICE_DEFINITION_ID) VALUES (3, 6);
INSERT INTO iot_database.definitions_many_to_many (M_FUNCTION_DEFINITION_ID, M_DEVICE_DEFINITION_ID) VALUES (4, 6);
INSERT INTO iot_database.definitions_many_to_many (M_FUNCTION_DEFINITION_ID, M_DEVICE_DEFINITION_ID) VALUES (8, 7);
INSERT INTO iot_database.definitions_many_to_many (M_FUNCTION_DEFINITION_ID, M_DEVICE_DEFINITION_ID) VALUES (6, 8);
INSERT INTO iot_database.definitions_many_to_many (M_FUNCTION_DEFINITION_ID, M_DEVICE_DEFINITION_ID) VALUES (7, 9);
INSERT INTO iot_database.definitions_many_to_many (M_FUNCTION_DEFINITION_ID, M_DEVICE_DEFINITION_ID) VALUES (3, 10);
INSERT INTO iot_database.definitions_many_to_many (M_FUNCTION_DEFINITION_ID, M_DEVICE_DEFINITION_ID) VALUES (3, 5);

INSERT INTO iot_database.home (HOME_NAME, HOME_ADDRESS) VALUES ('Дом', '22 мкр. 11 дом, 39 кв.');
INSERT INTO iot_database.home (HOME_NAME, HOME_ADDRESS) VALUES ('dahca', 'lokomotivnaya 132');
INSERT INTO iot_database.home (HOME_NAME, HOME_ADDRESS) VALUES ('Дом', 'Социалистическая, 12');
INSERT INTO iot_database.home (HOME_NAME, HOME_ADDRESS) VALUES ('Работа', 'Комунальная, 12');
INSERT INTO iot_database.home (HOME_NAME, HOME_ADDRESS) VALUES ('work', '32334 asdasd, 332');
INSERT INTO iot_database.home (HOME_NAME, HOME_ADDRESS) VALUES ('zikkurat', 'Azerot, 22');
INSERT INTO iot_database.home (HOME_NAME, HOME_ADDRESS) VALUES ('garage', 'asidjsdi, 2929');

INSERT INTO iot_database.device (DEVICE_DEFINITION_TO_DEVICE, HOME_PLACED_ID, DEVICE_NAME) VALUES (4, 1, 'Комната 1');
INSERT INTO iot_database.device (DEVICE_DEFINITION_TO_DEVICE, HOME_PLACED_ID, DEVICE_NAME) VALUES (8, 1, 'Есть тут кто?');
INSERT INTO iot_database.device (DEVICE_DEFINITION_TO_DEVICE, HOME_PLACED_ID, DEVICE_NAME) VALUES (6, 1, 'Слушаем музыку и чилим');
INSERT INTO iot_database.device (DEVICE_DEFINITION_TO_DEVICE, HOME_PLACED_ID, DEVICE_NAME) VALUES (2, 1, 'Прохладненько');
INSERT INTO iot_database.device (DEVICE_DEFINITION_TO_DEVICE, HOME_PLACED_ID, DEVICE_NAME) VALUES (5, 3, 'Да будет свет!');
INSERT INTO iot_database.device (DEVICE_DEFINITION_TO_DEVICE, HOME_PLACED_ID, DEVICE_NAME) VALUES (9, 3, 'Входная');

INSERT INTO iot_database.functions (F_BOOL, F_INT, F_STRING, FUNCTION_DEFINITION_ID, FUNCTION_DEVICE_ID) VALUES (false, 0, '', 3, 1);
INSERT INTO iot_database.functions (F_BOOL, F_INT, F_STRING, FUNCTION_DEFINITION_ID, FUNCTION_DEVICE_ID) VALUES (false, 0, '', 6, 2);
INSERT INTO iot_database.functions (F_BOOL, F_INT, F_STRING, FUNCTION_DEFINITION_ID, FUNCTION_DEVICE_ID) VALUES (false, 0, '', 3, 3);
INSERT INTO iot_database.functions (F_BOOL, F_INT, F_STRING, FUNCTION_DEFINITION_ID, FUNCTION_DEVICE_ID) VALUES (false, 0, '', 4, 3);
INSERT INTO iot_database.functions (F_BOOL, F_INT, F_STRING, FUNCTION_DEFINITION_ID, FUNCTION_DEVICE_ID) VALUES (false, 0, '', 2, 4);
INSERT INTO iot_database.functions (F_BOOL, F_INT, F_STRING, FUNCTION_DEFINITION_ID, FUNCTION_DEVICE_ID) VALUES (false, 0, '', 3, 5);
INSERT INTO iot_database.functions (F_BOOL, F_INT, F_STRING, FUNCTION_DEFINITION_ID, FUNCTION_DEVICE_ID) VALUES (false, 0, '', 7, 6);

INSERT INTO iot_database.language (LANGUAGE_NAME, LANGUAGE_LOCALE) VALUES ('ENGLISH', 'EN_locale');
INSERT INTO iot_database.language (LANGUAGE_NAME, LANGUAGE_LOCALE) VALUES ('РУССКИЙ', 'RU_locale');

INSERT INTO iot_database.user (USER_LOGIN, USER_PASSWORD, USER_ROLE, USER_BLOCKED) VALUES ('warkazz', '$2a$12$LwlK8a.a4sApABrQjAjnDe1M4JLXYqfhvAPdEuqVIjZl7S4SFu1A2', 1, false);
INSERT INTO iot_database.user (USER_LOGIN, USER_PASSWORD, USER_ROLE, USER_BLOCKED) VALUES ('anastasiya', '$2a$12$LwlK8a.a4sApABrQjAjnDe1M4JLXYqfhvAPdEuqVIjZl7S4SFu1A2', 2, false);
INSERT INTO iot_database.user (USER_LOGIN, USER_PASSWORD, USER_ROLE, USER_BLOCKED) VALUES ('vladlen', '$2a$12$LwlK8a.a4sApABrQjAjnDe1M4JLXYqfhvAPdEuqVIjZl7S4SFu1A2', 2, false);
INSERT INTO iot_database.user (USER_LOGIN, USER_PASSWORD, USER_ROLE, USER_BLOCKED) VALUES ('tony777', '$2a$12$LwlK8a.a4sApABrQjAjnDe1M4JLXYqfhvAPdEuqVIjZl7S4SFu1A2', 2, false);
INSERT INTO iot_database.user (USER_LOGIN, USER_PASSWORD, USER_ROLE, USER_BLOCKED) VALUES ('lordofthering', '$2a$12$LwlK8a.a4sApABrQjAjnDe1M4JLXYqfhvAPdEuqVIjZl7S4SFu1A2', 2, false);
INSERT INTO iot_database.user (USER_LOGIN, USER_PASSWORD, USER_ROLE, USER_BLOCKED) VALUES ('guest', '$2a$12$x.lsqJ4HTvRmiNOSyF9wveeuSTh9lISsQqFai9vobE0Z147khBRr2', 3, false);

INSERT INTO iot_database.user_to_home (USER_ID, HOME_ID, USER_HOME_ROLE) VALUES (1, 1, 1);
INSERT INTO iot_database.user_to_home (USER_ID, HOME_ID, USER_HOME_ROLE) VALUES (1, 2, 2);
INSERT INTO iot_database.user_to_home (USER_ID, HOME_ID, USER_HOME_ROLE) VALUES (1, 3, 1);
INSERT INTO iot_database.user_to_home (USER_ID, HOME_ID, USER_HOME_ROLE) VALUES (1, 4, 1);
INSERT INTO iot_database.user_to_home (USER_ID, HOME_ID, USER_HOME_ROLE) VALUES (2, 1, 2);
INSERT INTO iot_database.user_to_home (USER_ID, HOME_ID, USER_HOME_ROLE) VALUES (2, 2, 1);
INSERT INTO iot_database.user_to_home (USER_ID, HOME_ID, USER_HOME_ROLE) VALUES (2, 3, 2);
INSERT INTO iot_database.user_to_home (USER_ID, HOME_ID, USER_HOME_ROLE) VALUES (2, 4, 2);