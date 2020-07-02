package control;

import dao.EstadiaDAO;
import dao.EstadiaDAOImpl;
import entity.Estadia;
import entity.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EstadiaControl {
	private EstadiaDAO estadiaDAO = new EstadiaDAOImpl();
	private ObservableList<Estadia> lista = FXCollections.observableArrayList();
	private ObservableList<Reserva> reservas = FXCollections.observableArrayList();;
	private Validator validator;

	public EstadiaControl() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		carregaReservas();
	}

	private void carregaReservas() {
		ReservaControl reservaControl = new ReservaControl();
		reservas = FXCollections.observableArrayList(reservaControl.retornaReservas());
	}

	private void alert(AlertType tipo, String title, String header, String content) {
		Alert alert = new Alert(tipo);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public void checkin(Estadia e) {
		Set<ConstraintViolation<Estadia>> erros = validator.validate(e);
		if (erros.isEmpty()) {
			estadiaDAO.checkin(e);
			alert(AlertType.INFORMATION, "Hotel", null,
					"Check-in da reserva " + e.getDataCheckin() + " realizado com sucesso");
			pesquisar();
		} else { 
			String msgErros = "Erros: \n";
			for (ConstraintViolation<Estadia> erro : erros ) {
				msgErros += erro.getPropertyPath() + " - " + erro.getMessage() + "\n";
			}
			alert(AlertType.ERROR, "Hotel", "ERRO: Não foi possivel realizar o check-in ", msgErros);
		}
	}
	
	public void excluir(Estadia e) {
		ButtonType btnOk = new ButtonType("ok", ButtonData.OK_DONE);
		ButtonType btnNo = new ButtonType("no", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.CONFIRMATION,
		        "Você realmente deseja deletar a estadia " + (e.getCodigo()) + "?",
		        btnOk,
		        btnNo);

		alert.setTitle("Hotel");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.orElse(btnNo) == btnOk) {
			estadiaDAO.excluir(e.getCodigo());
	    	pesquisar();
		}
	}
	
	public boolean alterar(Estadia e) {
		boolean alterou = false;
		ButtonType btnOk = new ButtonType("ok", ButtonData.OK_DONE);
		ButtonType btnNo = new ButtonType("no", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.CONFIRMATION,
		        "Deseja realmente alterar esses dados?",
		        btnOk,
		        btnNo);

		alert.setTitle("Estadia");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.orElse(btnNo) == btnOk) {
			Set<ConstraintViolation<Estadia>> erros = validator.validate(e);
			if (erros.isEmpty()) { 
				estadiaDAO.alterar(e);
		    	pesquisar();
		    	alterou = true;;
			} else {
				String msgErros = "Erros: \n";
				for (ConstraintViolation<Estadia> erro : erros ) {
					msgErros += erro.getPropertyPath() + " - " + erro.getMessage() + "\n";
				}
				alert(AlertType.ERROR, "Estadia", "ERRO: Não foi possível alterar o estadia", msgErros);
			}
		}
		
		return alterou;
	}

	public void pesquisar () {
		lista.clear();
		List<Estadia> estadias = estadiaDAO.pesquisar();
		if(estadias.isEmpty()) {
			alert(AlertType.ERROR, "Erro na busca: ", null, "Não foi encontrada nenhuma estadia.");
		}else {
			lista.addAll(estadias);
		}
	}
	
	public ObservableList<Estadia> getEstadias() {
		return lista;
	}

    public ObservableList<Reserva> getReservas() {
		return reservas;
    }
}