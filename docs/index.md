# Documentação do Sistema de Hotelaria
## Descrição
O Sistema de Hotaleria é um software que soluciona problemas e auxília a administração de hoteis oferecendo desde funcionalidades que permitem acompanhar os hóspedes do check-in ao check-out -- registrando toda sua trilha de consumo -- às opções que possibilitam manejar reservas, gerir os espaços e controlar o trafégo de visitantes.

## Requisitos do Sistema
| ID do requisito | Titulo do requisito | Descrição do requisito |
|:------:|:-------------------:| ---------------------------------------
| 1. | Manter dados a respeito dos hóspedes | Registrar informações respeito dos hóspedes para que seja possível manter o histórico e resgatar essas informações quando necessário. |
| 2. | Realizar reservas | Deve ser possível reservar previamente uma hospedagem e as preferências de estada. Tem que haver um meio para confirmar e/ou cancelar reservas. |
| 3. | Tratar hospedagens e modalidades de hospedagem | Deve ser possível obter os _direitos_ (como massagens, áreas esportivas, cassinos etc.) aos quais os hospedes tem direito, a depender do _plano de hospedagem_. |
| 4. | Manter informações a respeito das estalagens  | É preciso manter dados a respeito das estalagens do hotel. |
| 5. | Controlar consumo dos hóspedes | O sistema deve registrar as informações de consumo das estadias dos hóspedes, para centralizar o pagamento de todas as suas despesas no check-out. |
| 6. | Permissionamento de usuários | Devido a natureza hierárquica que há na administração de hotéis, deve haver algum modo de controlar as permissões, de acesso às funções, dos usuários do sistema. |
| 7. | Controlar trafego de visitantes | Deve haver algum meio de controlar a entrada e saída de visitantes no hotel. |
| 8. | Notificar seguranças | Deve haver algum meio de comunicar a necessidade da presença seguranças. |
| 9. | Manter dados a respeito do hotel | Deve ser possível obter informações básicas a respeito do hotel a a partir do sistema. |
| X. | Renovar reserva | O sistema deve fornecer um meio de renovar as reservas com novos dados alterá-los. |
## Artefatos

### Diagrama de Caso de Uso

[<img src="https://raw.githubusercontent.com/henriqueyun/Hotel/master/docs/diagrams/Sistema%20de%20Hotelaria%20-%20Caso%20de%20Uso.png" href="(https://www.draw.io/#G1zjI4JMo-89LENNCCYuo0XHVBPLCT6b-I)"></img>](https://www.draw.io/#G1zjI4JMo-89LENNCCYuo0XHVBPLCT6b-I)
### Caso de Uso Textual

##### Relizar Reserva

O cliente pode realizar uma hospedagem. Para isso, precisa fornecer seus dados e preencher as informações a respeito da reserva.

##### Manter consumo

Os usuários do sistema conseguem registrar o consumo, que compreende a compra de produtos e serviços, de hóspedes através do sistema e manter esses registros.

#### Manter reserva

Tanto os clientes quanto as recepcionistas do hotel precisam registrar solicitações de reservas e também as confirmar, renovar e cancelar.

#### Manter hóspede

As recepcionistas conseguem cadastrar, atualizar, consultar e excluir registros de dados pessoais de hóspedes.

#### Manter produtos e serviços

Os grentes pode adicionar, atualizar, consultar e excluir dados a respeito de produtos e serviços oferecidos no hotel. Além de controlar a disponibilidade destes.

#### Manter usuário

Somente um gerente pode criar novos usuários do sistema para o Hotel, mudar as permissões de um usuário e desabilitar usuários.

#### Manter hotel

Os gerentes tem as permissões para atualizar as informações do hotel.

#### Manter quarto

Gerentes podem cadastrar e atualizar dados a respeito de quartos. Além de controlar sua disponibilidade.



### Diagrama de Classe
[<img src="https://raw.githubusercontent.com/henriqueyun/Hotel/master/docs/diagrams/Sistema%20de%20Hotelaria%20-%20Diagrama%20de%20Classe.png" href="(https://www.draw.io/#G1zjI4JMo-89LENNCCYuo0XHVBPLCT6b-I)"></img>](https://www.draw.io/#G19kg-91FQE2fpeHoDGOvHq9RuWNleIbQP)

### Visão das Classes Participativas
### Diagrama de Objetos
### Diagrama de Sequência
### Diagrama de Entidade-relacionamento

## Termos
Definições adotadas de palavras utilizadas no domínio de hotelaria.<br/>

* Hospedagem

  Acolhimento de pessoas, com ou sem remuneração; abrigo, hospitalidade

* Estadia

  Permânencia, estada por tempo limitado.

* Estada

  Ato de estar, permanecer em algum lugar.

* Hóspede

  Quem se abriga.

* Check-in

  É o registro de entrada de um hóspede em um hotel.

* Check-out

  É o registro de saída de um hóspede de um hotelaria.

* Espaços

  Os lugares que o hotel dispõe.

* Visitantes

  Pessoas que não são hóspedes ou funcionários mas precisam adentrar ao hotel.
