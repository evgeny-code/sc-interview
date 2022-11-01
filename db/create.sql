create table IF NOT EXISTS clan
(
    id   bigint auto_increment primary key,
    name varchar,
    gold int
);

create table IF NOT EXISTS task
(
    id     bigint auto_increment primary key,
    name   varchar,
    reward int
);

create table IF NOT EXISTS user_profile
(
    id   bigint auto_increment primary key,
    name varchar,
    gold int
);


create table IF NOT EXISTS clan_history
(
    id        bigint auto_increment primary key,
    date_time datetime,
    action    varchar,

    clan_id   bigint,
    user_id   bigint,
    task_id   bigint,

    gold_from int,
    gold_to   int
);
