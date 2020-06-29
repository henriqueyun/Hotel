package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Quarto;

public class QuartoDAOImpl implements QuartoDAO{
	
	private static final String URL = "jdbc:mariadb://localhost/hotel?allowMultiQueries=true";
	private static final String USER = System.getProperty("user.name");
	private static final String PASS = "123";

	public QuartoDAOImpl() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void adicionar(Quarto quarto) {
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "INSERT INTO quarto (numeroQuarto, TipoQuarto, valorDiaria, qtdCama, disponivel) "
					+ "VALUES  (0, ?, ?, ?, ?)";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, quarto.getTipo());
			stm.setDouble(2, quarto.getValordia());
			stm.setInt(3, quarto.getQtdcama());
			stm.setBoolean(4, quarto.isDisponivel());
			stm.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Quarto> pesquisarPorTipo(String tipo) {
		List<Quarto> lista = new ArrayList<>();
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "SELECT * FROM Quarto as q INNER JOIN TipoQuarto as t ON t.codigo = q.tipoDeQuarto And t.tipo like ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, "%" + tipo + "%");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Quarto quarto = new Quarto();
				quarto.setId(rs.getInt("numeroQuarto"));
				quarto.setTipo(rs.getInt("tipoDeQuarto"));
				quarto.setValordia(rs.getDouble("valorDiaria"));
				quarto.setQtdcama(rs.getInt("qtdCama"));
				quarto.setDisponivel(rs.getBoolean("disponivel"));
				lista.add(quarto);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public void excluir(int id) {
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "DELETE FROM quarto WHERE numeroQuarto = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);
			stm.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void alterar(Quarto quarto) {
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "UPDATE quarto SET TipoQuarto = ?, valorDiaria = ?, qtdCama = ?, disponivel = ? WHERE id = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, quarto.getTipo());
			stm.setDouble(2, quarto.getValordia());
			stm.setInt(3, quarto.getQtdcama());
			stm.setBoolean(4, quarto.isDisponivel());
			stm.setInt(5, quarto.getId());
			stm.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
