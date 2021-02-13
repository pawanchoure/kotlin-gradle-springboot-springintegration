CREATE TABLE IF NOT EXISTS TRADE (
    id               INTEGER PRIMARY KEY AUTO_INCREMENT,
    trade_id         VARCHAR(100) NOT NULL,
    type             VARCHAR(100) NOT NULL,
    sub_type         VARCHAR(100) NOT NULL,
    pnl_spn          INTEGER NOT NULL,
    spn              INTEGER NOT NULL,
    trade_date       DATETIME,
    price            DOUBLE NOT NULL,
    quantity         INTEGER(100) NOT NULL
);