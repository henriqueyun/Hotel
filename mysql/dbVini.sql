CREATE DATABASE hotel; 
USE hotel;

CREATE TABLE Hospede (
	codigo INT NOT NULL,
	nome VARCHAR(45),
       	cpf VARCHAR (20),
	dataNascimento DATE,
	PRIMARY KEY (codigo)
);

CREATE TABLE Usuario (
	codigo INT NOT NULL,
	usuario VARCHAR(20),
	senha VARCHAR(20),
	PRIMARY KEY (codigo)
);


CREATE TABLE TipoQuarto(
	codigo int primary key auto_increment,
	tipo varchar(40) not null 
);

CREATE TABLE Quarto (
	numeroQuarto INT NOT NULL,
	tipoDeQuarto INT,
	valorDiaria FLOAT,
	qtdCama TINYINT,
	disponivel BOOLEAN,
	PRIMARY KEY (numeroQuarto)
);
CREATE TABLE Reserva (
	codigo INT NOT NULL,
	dataReserva DATE,
	dataReservaSaida DATE,
	status TINYINT,
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


