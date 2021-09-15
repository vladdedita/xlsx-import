create table if not exists opportunity (
    id serial NOT NULL,
    opportunity_id uuid not null,
    customer_name varchar,
    booking_date timestamp,
    booking_type varchar,
    account_executive varchar,
    sales_organization varchar,
    team varchar,
    product varchar,
    total float8,
    renewable boolean,
    PRIMARY KEY (id),
    UNIQUE (opportunity_id)
)