
alter table b_transaction_2 drop column account_from_ref_id;
alter table b_transaction_2 drop column account_to_ref_id;

alter table b_transaction_2 add column account_from_ref_id uuid not null;
alter table b_transaction_2 add column account_to_ref_id uuid not null;