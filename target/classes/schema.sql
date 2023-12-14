CREATE TABLE IF NOT EXISTS def_currency (
  currency_code  VARCHAR(3) NOT NULL,
  description VARCHAR(20),
  CONSTRAINT def_currency PRIMARY KEY (currency_code)
);
