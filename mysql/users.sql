INSERT INTO Usuario (usuario, senha)
VALUES ("admin", "admin");

INSERT INTO TipoQuarto (tipo)
VALUES ("Quarto"),
       ("Suíte");

INSERT INTO Quarto (tipoDeQuarto, valorDiaria, qtdCama, disponivel)
VALUES
(1, 50, 1, true),
(2, 90, 2, true);

INSERT INTO Hospede (nome, cpf, dataNascimento)
VALUES
("José da Silva", 12345678910, '27-10-1988' ),
("Maria de Souza", 109876543421, '27-10-1970' );

INSERT INTO Reserva (dataReserva, dataReservaSaida, status, codigoUsuario, codigoHospede, numeroQuarto)
VALUES
('2020-06-30', '2020-07-04', 'checado', 1, 1,1),
('2020-06-25', '2020-07-02', 'Reservado', 1, 2,2);

INSERT INTO Estadia (dataCheckin, dataCheckout, status, codigoReserva)
VALUES
('2020-06-30', '', 'checado', 1);
