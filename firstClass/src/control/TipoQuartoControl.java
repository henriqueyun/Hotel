package control;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import dao.TipoQuartoDAO;
import dao.TipoQuartoDAOImpl;
import entity.TipoQuarto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class TipoQuartoControl {
	private TipoQuartoDAO tpQuartoDAO = new TipoQuartoDAOImpl();
	private ObservableList<TipoQuarto> lista = FXCollections.observableArrayList();
	private Validator validator;

	public TipoQuartoControl() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private void alert(AlertType tipo, String title, String header, String content) {
		Alert alert = new Alert(tipo);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public void adicionar(TipoQuarto tpQuarto) {
		Set<ConstraintViolation<TipoQuarto>> erros = validator.validate(tpQuarto);
		if (erros.isEmpty()) {
			tpQuartoDAO.adicionar(tpQuarto);
			alert(AlertType.INFORMATION, "Hotel Mananger", null,
					"Tipo de quarto " + tpQuarto.getTipo() + " cadastrado com sucesso");
			atualizaTabela();
		} else {
			String msgErros = "Erros: \n";
			for (ConstraintViolation<TipoQuarto> erro : erros) {
				msgErros += erro.getPropertyPath() + " - " + erro.getMessage() + "\n";
			}
			alert(AlertType.ERROR, "Hotel Mananger", "ERRO: N?o foi possivel cadastrar o tipo de quarto", msgErros);
		}
	}

	public void excluir(TipoQuarto tpQuarto) {
		ButtonType btnOk = new ButtonType("ok", ButtonData.OK_DONE);
		ButtonType btnNo = new ButtonType("no", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Voc? realmente deseja deletar tipo de quarto " + tpQuarto.getTipo() + "?", btnOk, btnNo);

		alert.setTitle("Hotel Mananger");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.orElse(btnNo) == btnOk) {
			tpQuartoDAO.excluir(tpQuarto.getId());
			atualizaTabela();
		}
	}

	public boolean alterar(TipoQuarto tpQuarto) {
		boolean alterou = false;
		ButtonType btnOk = new ButtonType("ok", ButtonData.OK_DONE);
		ButtonType btnNo = new ButtonType("no", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente alterar esses dados?", btnOk, btnNo);

		alert.setTitle("Hotel Mananger");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.orElse(btnNo) == btnOk) {
			Set<ConstraintViolation<TipoQuarto>> erros = validator.validate(tpQuarto);
			if (erros.isEmpty()) {
				tpQuartoDAO.alterar(tpQuarto);
				atualizaTabela();
				alterou = true;
				;
			} else {
				String msgErros = "Erros: \n";
				for (ConstraintViolation<TipoQuarto> erro : erros) {
					msgErros += erro.getPropertyPath() + " - " + erro.getMessage() + "\n";
				}
				alert(AlertType.ERROR, "Hotel Mananger", "ERRO: N?o foi possivel alterar o tipo de quarto!", msgErros);
			}
		}

		return alterou;
	}

	public TipoQuarto pesquisarPorTipo(String tipo) {
		lista.clear();
		List<TipoQuarto> tpQuartos = tpQuartoDAO.pesquisarPorTipo(tipo);
		if (tpQuartos.isEmpty()) {
			alert(AlertType.ERROR, "Error na busca", null, "N?o foi encontrado nenhum tipo de quarto.");
			return null;
		} else {
			lista.addAll(tpQuartos);
		}
		return lista.get(0);
	}

	
	public List<TipoQuarto> retornaTipos() {
		List<TipoQuarto> tpQuartos = tpQuartoDAO.pesquisarPorTipo("");
		if (tpQuartos.isEmpty()) {
			return Collections.emptyList();
		} else {
			lista.addAll(tpQuartos);
		}
		return tpQuartos;
	}
	
	
	public void atualizaTabela() {
		lista.clear();
		List<TipoQuarto> tpQuartos = tpQuartoDAO.pesquisarPorTipo("");
		lista.addAll(tpQuartos);
	}

	public ObservableList<TipoQuarto> getLista() {
		return lista;
	}
}
