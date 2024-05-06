CREATE SCHEMA IF NOT EXISTS simple_wallet;

-- Tabela AccountEntity
CREATE TABLE IF NOT EXISTS simple_wallet.account (
    id VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_username UNIQUE (username),
    CONSTRAINT uk_email UNIQUE (email)
);

-- Tabela BalanceEntity
CREATE TABLE IF NOT EXISTS simple_wallet.balance (
    id VARCHAR(255) NOT NULL,
    account_id VARCHAR(255) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    locked BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES simple_wallet.account (id) ON DELETE CASCADE
);

-- Tabela TransactionEntity
CREATE TABLE IF NOT EXISTS simple_wallet.transaction (
    id VARCHAR(255) NOT NULL,
    balance_id VARCHAR(255) NOT NULL,
    value DECIMAL(19, 2) NOT NULL,
    description VARCHAR(255),
    transaction_type VARCHAR(50),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    complementar JSON,
    PRIMARY KEY (id),
    FOREIGN KEY (balance_id) REFERENCES simple_wallet.balance (id) ON DELETE CASCADE
);
