INSERT INTO permissao (codigo, descricao) values (9, 'ROLE_REMOVER_CATEGORIA');
-- admin
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 9);