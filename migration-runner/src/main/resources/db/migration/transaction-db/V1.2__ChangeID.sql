alter table b_transaction_0 drop column id;
alter table b_transaction_1 drop column id;
alter table b_transaction_2 drop column id;

alter table b_transaction_0
    add column transaction_id bigserial primary key not null unique;

alter table b_transaction_1
    add column transaction_id bigserial primary key not null unique;

alter table b_transaction_2
    add column transaction_id bigserial primary key not null unique;