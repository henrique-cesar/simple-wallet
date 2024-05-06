-- Inserir usuário
INSERT INTO simple_wallet.account (id, username, email) VALUES (UUID(), 'usuario_exemplo', 'usuario@example.com');

-- Inserir balanço associado ao usuário
INSERT INTO simple_wallet.balance (id, account_id, amount, locked)
VALUES (UUID(), (SELECT id FROM simple_wallet.account LIMIT 1), 1000.00, false);

-- Inserir 30 transações com valores aleatórios
INSERT INTO simple_wallet.transaction (id, balance_id, value, description, transaction_type, created_at, updated_at)
VALUES
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Recarga', 'Recarga', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Transferencia', 'Transferencia', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Compra', 'Compra', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Estorno', 'Estorno', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Cancelamento', 'Cancelamento', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Recarga', 'Recarga', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Transferencia', 'Transferencia', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Compra', 'Compra', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Estorno', 'Estorno', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Cancelamento', 'Cancelamento', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Recarga', 'Recarga', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Transferencia', 'Transferencia', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Compra', 'Compra', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Estorno', 'Estorno', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Cancelamento', 'Cancelamento', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Recarga', 'Recarga', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Transferencia', 'Transferencia', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Compra', 'Compra', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Estorno', 'Estorno', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Cancelamento', 'Cancelamento', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Recarga', 'Recarga', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Transferencia', 'Transferencia', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Compra', 'Compra', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Estorno', 'Estorno', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Cancelamento', 'Cancelamento', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Recarga', 'Recarga', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Transferencia', 'Transferencia', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Compra', 'Compra', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Estorno', 'Estorno', NOW(), NOW()),
(UUID(), (SELECT id FROM simple_wallet.balance LIMIT 1), ROUND(RAND() * 100, 2), 'Cancelamento', 'Cancelamento', NOW(), NOW());
