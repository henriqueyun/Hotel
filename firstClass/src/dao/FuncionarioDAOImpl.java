package dao;

import entity.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FuncionarioDAOImpl implements FuncionarioDAO {
	private static final String URL = "jdbc:mariadb://localhost/hotel?allowMultiQueries=true";
	private static final String USER = System.getProperty("user.name");
	private static final String PASS = "123";
	
	public FuncionarioDAOImpl() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void adicionar(Funcionario f) {
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "INSERT INTO Usuario (codigo, usuario, senha) "
					+ "VALUES  (0, ?, ?)";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, f.getUsuario());
			stm.setString(2, f.getSenha());
			stm.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Funcionario> pesquisarPorNome(String nome) {
		List<Funcionario> lista = new ArrayList<>();
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "SELECT * FROM Usuario WHERE usuario like ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, "%" + nome + "%");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) { 
				Funcionario f = new Funcionario();
				f.setCodigo(rs.getInt("codigo"));
				f.setUsuario(rs.getString("usuario"));
				f.setSenha(rs.getString("senha"));
				lista.add(f);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public void excluir(int codigo) {
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "DELETE FROM Usuario WHERE codigo = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setLong(1, codigo);
			stm.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void alterar(Funcionario f) {
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "UPDATE Usuario SET usuario = ?, senha = ? WHERE codigo = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, f.getUsuario());
			stm.setString(2, f.getSenha());
			stm.setString(3, String.valueOf(f.getCodigo()));
			stm.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Funcionario realizaLogin(String usuario, String senha) {
		Funcionario f = new Funcionario();
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "SELECT * FROM Usuario WHERE usuario = ? AND senha = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, usuario);
			stm.setString(2, senha);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) { 
				f.setCodigo(rs.getInt("codigo"));
				f.setUsuario(rs.getString("usuario"));
				f.setSenha(rs.getString("senha"));
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return f;
	}
}