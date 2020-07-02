package control;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


import dao.ReservaDAO;
import dao.ReservaDAOImpl;
import entity.Hospede;
import entity.Quarto;
import entity.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class ReservaControl {
	
	private ReservaDAO reservaDAO = new ReservaDAOImpl();
	private ObservableList<Reserva> lista = FXCollections.observableArrayList();
	private ObservableList<Quarto> quartos;
	private ObservableList<Hospede> hospedes = FXCollections.observableArrayList();
	private Validator validator;
	
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public ReservaControl() { 
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		carregaTipos();
	}
	
	public void carregaTipos() {
		QuartoControl quartoControl = new QuartoControl();
		HospedeControl hospedeControl = new HospedeControl();
		quartos = FXCollections.observableArrayList(quartoControl.retornaQuartos());
		hospedes = FXCollections.observableArrayList(hospedeControl.retornaHospede());
	}

	private void alert(AlertType tipo, String title, String header, String content) {
		Alert alert = new Alert(tipo);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public void adicionar(Reserva reserva) { 
		Set<ConstraintViolation<Reserva>> erros = validator.validate(reserva);
		Reserva dispReserva = new Reserva();
		dispReserva = verificaDisponibilidade(reserva);
		
		if (dispReserva.getDtReserva() == null) {
			if (erros.isEmpty()) { 
				reservaDAO.reservar(reserva);
				alert(AlertType.INFORMATION, "Hotel Mananger", null, 
						"Reserva de nº " + reserva.getId() + " feita com sucesso");
				atualizaTabela();
			} else { 
				String msgErros = "Erros: \n";
				for (ConstraintViolation<Reserva> erro : erros ) { 
					msgErros += erro.getPropertyPath() + " - " + erro.getMessage() + "\n";
				}
				alert(AlertType.ERROR, "Hotel Mananger", "ERRO: N?o foi possivel realizar a Reserva", msgErros);
			}
		}else {
			alert(AlertType.ERROR, "Hotel Mananger", "Reserva já existente", "O quarto de Nº"+dispReserva.getQuarto()+
					" já esta reservado para o hospede de codigo " +dispReserva.getHospede()+ " do dia " +dtf.format(dispReserva.getDtReserva())+" até o dia " +dtf.format(dispReserva.getDtReservaSaida()));
		}
	}
	
	public void excluir(Reserva reserva) {
		ButtonType btnOk = new ButtonType("ok", ButtonData.OK_DONE);
		ButtonType btnNo = new ButtonType("no", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.CONFIRMATION,
		        "Você realmente deseja excluir a reserva nº " +  reserva.getId() + "?",
		        btnOk,
		        btnNo);

		alert.setTitle("Hotel Mananger");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.orElse(btnNo) == btnOk) {
			reservaDAO.excluir(reserva.getId());
	    	atualizaTabela();
		}
	}
	
	public boolean alterar(Reserva reserva) {
		boolean alterou = false;
		ButtonType btnOk = new ButtonType("ok", ButtonData.OK_DONE);
		ButtonType btnNo = new ButtonType("no", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.CONFIRMATION,
		        "Deseja realmente alterar os dados da reserva?",
		        btnOk,
		        btnNo);

		alert.setTitle("Hotel Mananger");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.orElse(btnNo) == btnOk) {
			Set<ConstraintViolation<Reserva>> erros = validator.validate(reserva);
			if (erros.isEmpty()) { 
				reservaDAO.alterar(reserva);
		    	atualizaTabela();
		    	alterou = true;;
			} else { 
				String msgErros = "Erros: \n";
				for (ConstraintViolation<Reserva> erro : erros ) { 
					msgErros += erro.getPropertyPath() + " - " + erro.getMessage() + "\n";
				}
				alert(AlertType.ERROR, "Hotel Mananger", "ERRO: Não foi possivel alterar os dados da reserva", msgErros);
			}
		}
		return alterou;
	}

	public Reserva pesquisarPorHospede(String hospede) { 
		lista.clear();
		List<Reserva> reservas = reservaDAO.pesquisarPorHospede(hospede);
		if( quartos.isEmpty()) {
			alert(AlertType.ERROR, "Error na busca", null, "Não foi encontrado nenhuma reserva desse hospede.");
			return null;
		}else {
			lista.addAll(reservas);
		}
		return lista.get(0);
	}
	
	public Reserva verificaDisponibilidade(Reserva reservaInc) {
		return reservaDAO.verificaDisponibilidade(reservaInc);
	}
	
	public void atualizaTabela() {
		lista.clear();
		List<Reserva> reservas = reservaDAO.pesquisarPorHospede("");
		lista.addAll(reservas);
	}
	
	public ObservableList<Reserva> getLista() {
		return lista;
	}

	public ObservableList<Quarto> getQuartos() {
		return quartos;
	}

	public ObservableList<Hospede> getHospedes() {
		return hospedes;
	}
}
