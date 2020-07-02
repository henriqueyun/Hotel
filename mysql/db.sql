DROP DATABASE IF EXISTS hotel;
CREATE DATABASE IF NOT EXISTS hotel; 
USE hotel;

CREATE TABLE Hospede (
	codigo INT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(45),
       	cpf VARCHAR (20),
	dataNascimento DATE,
	PRIMARY KEY (codigo)
);

CREATE TABLE Usuario (
	codigo INT NOT NULL AUTO_INCREMENT,
	usuario VARCHAR(20),
	senha VARCHAR(20),
	PRIMARY KEY (codigo)
);

CREATE TABLE TipoQuarto(
        codigo int primary key auto_increment,
        tipo varchar(40) not null
);

CREATE TABLE Quarto (
	numeroQuarto INT NOT NULL AUTO_INCREMENT,
	tipoDeQuarto INT,
	valorDiaria FLOAT,
	qtdCama TINYINT,
	disponivel BOOLEAN,
	PRIMARY KEY (numeroQuarto),
	CONSTRAINT FK_codigoTipoQuarto FOREIGN KEY (tipoDeQuarto)
		REFERENCES TipoQuarto(codigo)
);

CREATE TABLE Reserva (
	codigo INT NOT NULL AUTO_INCREMENT,
	dataReserva DATE,
	dataReservaSaida DATE,
	status VARCHAR(20),
       	codigoUsuario INT,
	codigoHospede INT,
	numeroQuarto INT,
	PRIMARY KEY (codigo),
	CONSTRAINT FK_codigoUsuario FOREIGN KEY (codigoUsuario)
		REFERENCES Usuario(codigo),
        CONSTRAINT FK_codigoHospede FOREIGN KEY (codigoHospede)
                REFERENCES Hospede(codigo),
        CONSTRAINT FK_numeroQuarto FOREIGN KEY (numeroQuarto)
                REFERENCES Quarto(numeroQuarto)

);

CREATE Table Estadia (
	codigo INT NOT NULL AUTO_INCREMENT,
	dataCheckin DATE,
	dataCheckout DATE,
	status VARCHAR(20),
	codigoReserva INT,
	PRIMARY KEY (codigo),
	CONSTRAINT FK_codigoReserva FOREIGN KEY (codigoReserva)
		REFERENCES Reserva(codigo)
	)

