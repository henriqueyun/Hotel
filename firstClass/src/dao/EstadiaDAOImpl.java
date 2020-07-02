package dao;

import entity.Estadia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadiaDAOImpl implements EstadiaDAO {

	private static final String URL = "jdbc:mariadb://localhost/hotel?allowMultiQueries=true";
	private static final String USER = "henriqueyun";
	private static final String PASS = "123";

	public EstadiaDAOImpl() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void checkin (Estadia f) {
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "INSERT INTO Estadia (codigo, dataCheckin, dataCheckout, status, codigoReserva) "
					+ "VALUES  (0, ?, ?, ?, ?); UPDATE Reserva SET status = 'Checado' WHERE codigo = ?  ";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setDate(1, java.sql.Date.valueOf(f.getDataCheckin()));
			stm.setNull(2, Types.DATE);
			stm.setString(3, "Checado");
			stm.setInt(4, f.getReserva());
			stm.setInt(5, f.getReserva());
			stm.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Estadia> pesquisar() {
		List<Estadia> lista = new ArrayList<>();
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "SELECT * FROM Estadia e INNER JOIN Reserva r ON e.codigoReserva = r.codigo WHERE e.status = 'Checado'";
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) { 
				Estadia f = new Estadia();
				f.setCodigo(rs.getInt("codigo"));
				f.setDataCheckin(rs.getDate("dataCheckin").toLocalDate());
				//f.setDataCheckout(rs.getDate("dataCheckout").toLocalDate());
				f.setStatus(rs.getString("status"));
				f.setReserva(rs.getInt("codigoReserva"));
				lista.add(f);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public void checkout(Estadia e) {
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "UPDATE Estadia SET status = 'Finalizada' WHERE codigo = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, String.valueOf(e.getCodigo()));
			stm.executeUpdate();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	
	
	
}