
create table if not exists b_transaction_0 (
    id bigint not null primary key unique,
    amount numeric(10, 2) not null default 0,
    account_from_ref_id uuid not null,
    signature varchar(64) not null unique,
    currency_id_ref int not null,
    account_to_ref_id uuid not null,
    transaction_type int,
    datetime timestamp
);

create table if not exists b_transaction_1 (
    id bigint not null primary key unique,
    amount numeric(10, 2) not null default 0,
    account_from_ref_id uuid not null,
    signature varchar(64) not null unique,
    currency_id_ref int not null,
    account_to_ref_id uuid not null,
    transaction_type int,
    datetime timestamp
);

create table if not exists b_transaction_2 (
    id bigint not null primary key unique,
    amount numeric(10, 2) not null default 0,
    account_from_ref_id bigint not null,
    signature varchar(64) not null unique,
    currency_id_ref int not null,
    account_to_ref_id bigint not null,
    transaction_type int,
    datetime timestamp
);