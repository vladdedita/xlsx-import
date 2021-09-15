create table if not exists opportunity
(
    id                 serial    NOT NULL,
    opportunity_id     uuid      not null,
    customer_name      varchar   not null,
    booking_date       timestamp not null,
    booking_type       varchar   not null,
    account_executive  varchar   not null,
    sales_organization varchar   not null,
    team               varchar   not null,
    product            varchar   not null,
    total              float8    not null,
    renewable          boolean   not null,
    PRIMARY KEY (id),
    UNIQUE (opportunity_id)
)