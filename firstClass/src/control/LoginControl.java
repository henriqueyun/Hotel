package control;

import dao.FuncionarioDAO;
import dao.FuncionarioDAOImpl;
import entity.Funcionario;
import javafx.stage.Stage;

public class LoginControl {
	private FuncionarioDAO funcDao = new FuncionarioDAOImpl();
	
	
	public Funcionario validaUsuario(String usuario, String senha) {
		return funcDao.realizaLogin(usuario, senha);
	}
}
