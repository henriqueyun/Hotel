package control;

import dao.FuncionarioDAO;
import dao.FuncionarioDAOImpl;
import entity.Funcionario;
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

public class FuncionarioControl {
	private FuncionarioDAO funcionarioDAO = new FuncionarioDAOImpl();
	private ObservableList<Funcionario> lista = FXCollections.observableArrayList();

	private Validator validator;

	public FuncionarioControl() {
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

	public void adicionar(Funcionario f) {
		Set<ConstraintViolation<Funcionario>> erros = validator.validate(f);
		if (erros.isEmpty()) { 
			funcionarioDAO.adicionar(f);
			alert(AlertType.INFORMATION, "Hotel", null,
					"Funcionario " + f.getUsuario() + " cadastrado com sucesso");
			atualizaTabela();
		} else { 
			String msgErros = "Erros: \n";
			for (ConstraintViolation<Funcionario> erro : erros ) {
				msgErros += erro.getPropertyPath() + " - " + erro.getMessage() + "\n";
			}
			alert(AlertType.ERROR, "Hotel", "ERRO: Não foi possivel cadastrar o funcionario", msgErros);
		}
	}
	
	public void excluir(Funcionario f) {
		ButtonType btnOk = new ButtonType("ok", ButtonData.OK_DONE);
		ButtonType btnNo = new ButtonType("no", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.CONFIRMATION,
		        "Você realmente deseja deletar o(a) " + f.getUsuario() + "?",
		        btnOk,
		        btnNo);

		alert.setTitle("Hotel");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.orElse(btnNo) == btnOk) {
			funcionarioDAO.excluir(f.getCodigo());
	    	atualizaTabela();
		}
	}
	
	public boolean alterar(Funcionario f) {
		boolean alterou = false;
		ButtonType btnOk = new ButtonType("ok", ButtonData.OK_DONE);
		ButtonType btnNo = new ButtonType("no", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.CONFIRMATION,
		        "Deseja realmente alterar esses dados?",
		        btnOk,
		        btnNo);

		alert.setTitle("Funcionario");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.orElse(btnNo) == btnOk) {
			Set<ConstraintViolation<Funcionario>> erros = validator.validate(f);
			if (erros.isEmpty()) { 
				funcionarioDAO.alterar(f);
		    	atualizaTabela();
		    	alterou = true;;
			} else {
				String msgErros = "Erros: \n";
				for (ConstraintViolation<Funcionario> erro : erros ) {
					msgErros += erro.getPropertyPath() + " - " + erro.getMessage() + "\n";
				}
				alert(AlertType.ERROR, "Funcionario", "ERRO: Não foi possível alterar o funcionário", msgErros);
			}
		}
		
		return alterou;
	}

	public Funcionario pesquisarPorNome(String nome) {
		lista.clear();
		List<Funcionario> funcionarios = funcionarioDAO.pesquisarPorNome(nome);
		if( funcionarios.isEmpty()) {
			alert(AlertType.ERROR, "Error na busca", null, "Não foi encontrado nenhum Pet com esse nome.");
			return null;
		}else {
			lista.addAll(funcionarios);
		}
		return lista.get(0);
	}
	
	
	public void atualizaTabela() {
		lista.clear();
		List<Funcionario> funcionarios = funcionarioDAO.pesquisarPorNome("");
		lista.addAll(funcionarios);
	}
	
	public ObservableList<Funcionario> getLista() {
		return lista;
	}

}