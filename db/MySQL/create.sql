use
Katchau;

insert into Usuario(codigo, email, senha, nome, papel)
values ('000.000.000-00', 'ok@ok.com', 'ok', 'ok', 1);

insert into Usuario(codigo, email, senha, nome, papel)
values ('000.000.000/0001-01', 'loja@loja.com', 'loja', 'loja', 2);

insert into Usuario(codigo, email, senha, nome, papel)
values ('000.000.000/0001-02', 'loja2@loja.com', 'loja2', 'loja2', 2);

insert into Loja(cnpj, descricao) values ('000.000.000/0001-01', 'loja 1');

insert into Loja(cnpj, descricao) values ('000.000.000/0001-02', 'loja 2');

insert into Usuario(codigo, email, senha, nome, papel)
values ('000.000.000-00', 'ok@ok.com', 'ok', 'ok', 1);

insert into Usuario(codigo, email, senha, nome, papel)
values ('000.000.001', 'cliente@cliente.com', 'cliente', 'cliente', 3);

insert into Usuario(codigo, email, senha, nome, papel)
values ('000.000.002', 'cliente2@cliente.com', 'cliente2', 'cliente2', 3);

insert into Cliente(cpf, telefone, sexo, nascimento) VALUES ('000.000.001', '999999999', 'm','2001-03-01');
insert into Cliente(cpf, telefone, sexo, nascimento) VALUES ('000.000.002', '992999999', 'f','2002-03-01');

insert into Carro(cnpj, placa, modelo, chassi, ano, km, descricao, valor) values ('000.000.000/0001-01','00001','carroa','none',1080,0,'carro antigo',2);
insert into Carro(cnpj, placa, modelo, chassi, ano, km, descricao, valor) values ('000.000.000/0001-02','00002','carrob','none',1081,1,'carro antigo 2',3);
insert into Carro(cnpj, placa, modelo, chassi, ano, km, descricao, valor) values ('000.000.000/0001-02','00003','fusca','aço inox',1082,0,'fusca',4);

insert into Proposta(status, data, valor, condPag, cnpj, cpf, carro_id) VALUES (1, '2001-02-22', 3, 'horrivel', '000.000.000/0001-02','000.000.001',2);
insert into Proposta(status, data, valor, condPag, cnpj, cpf, carro_id) VALUES (0, '2001-02-22', 3, 'condicao boa', '000.000.000/0001-02','000.000.001',2);

insert into Proposta(status, data, valor, condPag, cnpj, cpf, carro_id) VALUES (1, '2001-02-22', 30, 'fusca', '000.000.000/0001-02','000.000.001',3);
insert into Proposta(status, data, valor, condPag, cnpj, cpf, carro_id) VALUES (0, '2001-02-22', 30, 'eu quero o fusca, 30 no dinheiro', '000.000.000/0001-02','000.000.001',3);