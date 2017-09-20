CREATE TABLE titulo (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(200) NULL,
    valor DECIMAL(19,2) NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO titulo (descricao, valor) values ('pagamento Live TIM', 90.00);
INSERT INTO titulo (descricao, valor) values ('pagamento Live TIM', 90.00);