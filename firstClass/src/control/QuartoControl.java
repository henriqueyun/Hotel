package control;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import dao.QuartoDAO;
import dao.QuartoDAOImpl;
import entity.Quarto;
import entity.TipoQuarto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class QuartoControl {
	private QuartoDAO quartoDAO = new QuartoDAOImpl();
	private ObservableList<Quarto> lista = FXCollections.observableArrayList();
	private ObservableList<TipoQuarto> tipos;
	private Validator validator;
	
	public QuartoControl() { 
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		carregaTipos();
	}
	
	public void carregaTipos() {
		TipoQuartoControl tpQuarto = new TipoQuartoControl();
		tipos = FXCollections.observableArrayList(tpQuarto.retornaTipos());
	}

	private void alert(AlertType tipo, String title, String header, String content) {
		Alert alert = new Alert(tipo);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public void adicionar(Quarto quarto) { 
		Set<ConstraintViolation<Quarto>> erros = validator.validate(quarto);
		if (erros.isEmpty()) { 
			quartoDAO.adicionar(quarto);
			alert(AlertType.INFORMATION, "Hotel Mananger", null, 
					"Quarto " + quarto.getTipo() + " cadastrado com sucesso");
			atualizaTabela();
		} else { 
			String msgErros = "Erros: \n";
			for (ConstraintViolation<Quarto> erro : erros ) { 
				msgErros += erro.getPropertyPath() + " - " + erro.getMessage() + "\n";
			}
			alert(AlertType.ERROR, "Hotel Mananger", "ERRO: N?o foi possivel cadastrar o Quarto", msgErros);
		}
	}
	
	public void excluir(Quarto quarto) {
		ButtonType btnOk = new ButtonType("ok", ButtonData.OK_DONE);
		ButtonType btnNo = new ButtonType("no", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.CONFIRMATION,
		        "Voc? realmente deseja deletar o quarto " +  quarto.getTipo() + "?",
		        btnOk,
		        btnNo);

		alert.setTitle("Hotel Mananger");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.orElse(btnNo) == btnOk) {
			quartoDAO.excluir(quarto.getId());
	    	atualizaTabela();
		}
	}
	
	public boolean alterar(Quarto quarto) {
		boolean alterou = false;
		ButtonType btnOk = new ButtonType("ok", ButtonData.OK_DONE);
		ButtonType btnNo = new ButtonType("no", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.CONFIRMATION,
		        "Deseja realmente alterar esses dados?",
		        btnOk,
		        btnNo);

		alert.setTitle("Hotel Mananger");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.orElse(btnNo) == btnOk) {
			Set<ConstraintViolation<Quarto>> erros = validator.validate(quarto);
			if (erros.isEmpty()) { 
				quartoDAO.alterar(quarto);
		    	atualizaTabela();
		    	alterou = true;;
			} else { 
				String msgErros = "Erros: \n";
				for (ConstraintViolation<Quarto> erro : erros ) { 
					msgErros += erro.getPropertyPath() + " - " + erro.getMessage() + "\n";
				}
				alert(AlertType.ERROR, "Hotel Mananger", "ERRO: N?o foi possivel alterar os dados do Quarto", msgErros);
			}
		}
		return alterou;
	}

	public Quarto pesquisarPorTipo(String tipo) { 
		lista.clear();
		List<Quarto> quartos = quartoDAO.pesquisarPorTipo(tipo);
		if( quartos.isEmpty()) {
			alert(AlertType.ERROR, "Error na busca", null, "N?o foi encontrado nenhum nenhum desse tipo.");
			return null;
		}else {
			lista.addAll(quartos);
		}
		return lista.get(0);
	}
	
	public List<Quarto> retornaQuartos() {
		List<Quarto> quartos = quartoDAO.pesquisarPorTipo("");
		if (quartos.isEmpty()) {
			return Collections.emptyList();
		} else {
			lista.addAll(quartos);
		}
		return quartos;
	}
	
	
	public void atualizaTabela() {
		lista.clear();
		List<Quarto> quartos = quartoDAO.pesquisarPorTipo("");
		lista.addAll(quartos);
	}
	
	public ObservableList<Quarto> getLista() {
		return lista;
	}

	public ObservableList<TipoQuarto> getTipos() {
		return tipos;
	}
}
