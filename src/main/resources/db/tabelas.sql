-- Tabela Clientes
CREATE TABLE cliente (
                         idcliente SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         cpf CHAR(11) UNIQUE NOT NULL,
                         fone VARCHAR(15)
);

-- Tabela Produto
CREATE TABLE produto (
                         idproduto SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         precocusto DECIMAL(10, 2) NOT NULL,
                         precovenda DECIMAL(10, 2) NOT NULL,
                         estoque INT NOT NULL
);

-- Tabela Venda
CREATE TABLE venda (
                       idvenda SERIAL PRIMARY KEY,
                       data DATE NOT NULL,
                       idcliente INT,
                       FOREIGN KEY (idcliente) REFERENCES cliente(idcliente)
);

-- Tabela DetalheVenda
CREATE TABLE detalhevenda (
                              iddetalhe SERIAL PRIMARY KEY,
                              idvenda INT,
                              idproduto INT,
                              quantidade INT NOT NULL,
                              FOREIGN KEY (idvenda) REFERENCES venda(idvenda) ON DELETE CASCADE,
                              FOREIGN KEY (idproduto) REFERENCES produto(idproduto)
);
