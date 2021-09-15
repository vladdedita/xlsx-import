create table if not exists file_metadata
(
    id          uuid      NOT NULL,
    name        varchar   not null,
    upload_date timestamp not null,
    size        int8      not null,
    range       varchar   not null,
    PRIMARY KEY (id)
)