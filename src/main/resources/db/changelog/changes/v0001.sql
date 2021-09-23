create table "sales_transaction" (
  id BIGSERIAL not null,
  account_id bigint,
  account_amount_millis bigint not null,
  cash_amount_millis bigint,
  transaction_date_time TIMESTAMP,
  primary key (id)
);
