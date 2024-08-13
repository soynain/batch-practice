/*this is automatically executed when starting the spring boot project, something similar
to laravel*/
DROP TABLE IF EXISTS batch_table_migrate;

create table batch_table_migrate(
    int primary key auto_increment not null,
    sej_uuid binary(36) null default null,
    description varchar(200) default null,
    code int null default null,
    parent_sad_uuid varchar(100) null default null
);