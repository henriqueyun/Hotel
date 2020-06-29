package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.TipoQuarto;

public class TipoQuartoDAOImpl implements TipoQuartoDAO {

	private static final String URL = "jdbc:mariadb://localhost/hotel?allowMultiQueries=true";
	private static final String USER = System.getProperty("user.name");
	private static final String PASS = "123";

	public TipoQuartoDAOImpl() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void adicionar(TipoQuarto tpQuarto) {
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "INSERT INTO TipoQuarto (codigo, tipo) " + "VALUES  (0, ?)";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, tpQuarto.getTipo());
			stm.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<TipoQuarto> pesquisarPorTipo(String tipo) {
		List<TipoQuarto> lista = new ArrayList<>();
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "SELECT * FROM TipoQuarto WHERE tipo like ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, "%" + tipo + "%");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				TipoQuarto tpQuarto = new TipoQuarto();
				tpQuarto.setId(rs.getInt("codigo"));
				tpQuarto.setTipo(rs.getString("tipo"));
				lista.add(tpQuarto);
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
			String sql = "DELETE FROM TipoQuarto WHERE codigo = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);
			stm.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void alterar(TipoQuarto tpQuarto) {
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "UPDATE TipoQuarto SET tipo = ? WHERE codigo = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, tpQuarto.getTipo());
			stm.setInt(2, tpQuarto.getId());
			stm.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
