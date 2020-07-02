package dao;

import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import entity.Reserva;

public class ReservaDAOImpl implements ReservaDAO {
	
	private static final String URL = "jdbc:mariadb://localhost/hotel?allowMultiQueries=true";
	private static final String USER = System.getProperty("user.name");
	private static final String PASS = "123";

	public ReservaDAOImpl() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reservar(Reserva reserva) {
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			//todo tirar usuario chumbado
			String sql = "INSERT INTO Reserva (codigo, dataReserva, dataReservaSaida, status, codigoUsuario, codigoHospede, numeroQuarto) "
					+ "VALUES  (0, ?, ?, ?, 1, ?, ?)";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setDate(1,  java.sql.Date.valueOf(reserva.getDtReserva()));
			stm.setDate(2, java.sql.Date.valueOf(reserva.getDtReservaSaida()));
			stm.setString(3, reserva.getStatus());
			//stm.setInt(4, reserva.getUsuario());
			stm.setInt(4, reserva.getHospede());
			stm.setInt(5, reserva.getQuarto());
			stm.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Reserva> pesquisarPorHospede(String hospede) {
		List<Reserva> lista = new ArrayList<>();
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "SELECT * FROM Reserva as r INNER JOIN Hospede as h ON r.codigoHospede = h.codigo And h.nome like ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, "%" + hospede + "%");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Reserva reserva = new Reserva();
				reserva.setId(rs.getInt("codigo"));
				reserva.setDtReserva(rs.getDate("dataReserva").toLocalDate());
				reserva.setDtReservaSaida(rs.getDate("dataReservaSaida").toLocalDate()); 
				reserva.setStatus(rs.getString("status"));
				reserva.setUsuario(rs.getInt("codigoUsuario"));
				reserva.setQuarto(rs.getInt("numeroQuarto"));
				reserva.setHospede(rs.getInt("codigoHospede"));
				lista.add(reserva);
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
			String sql = "DELETE FROM Reserva WHERE codigo = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);
			stm.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void alterar(Reserva reserva) {
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			 //todo arrumar usuario chumbado
			String sql = "UPDATE Reserva SET dataReserva = ?, dataReservaSaida = ?, status = ?, codigoUsuario = 1, codigoHospede = ?, numeroQuarto = ? WHERE codigo = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setDate(1,  java.sql.Date.valueOf(reserva.getDtReserva()));
			stm.setDate(2,  java.sql.Date.valueOf(reserva.getDtReservaSaida()));
			stm.setString(3, reserva.getStatus());
			//stm.setInt(4, reserva.getUsuario());
			stm.setInt(4, reserva.getQuarto());
			stm.setInt(5, reserva.getHospede());
			stm.setInt(6, reserva.getId());
			stm.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Reserva verificaDisponibilidade(Reserva reserva) {
		Reserva Retreserva = new Reserva();
		try {
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			String sql = "SELECT * FROM Reserva WHERE numeroQuarto = ? AND (dataReserva BETWEEN ? AND ? OR dataReservaSaida BETWEEN ? AND ?)";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1,  reserva.getQuarto());
			stm.setDate(2,  java.sql.Date.valueOf(reserva.getDtReserva()));
			stm.setDate(3,  java.sql.Date.valueOf(reserva.getDtReservaSaida()));
			stm.setDate(4,  java.sql.Date.valueOf(reserva.getDtReserva()));
			stm.setDate(5,  java.sql.Date.valueOf(reserva.getDtReservaSaida()));
			ResultSet rs = stm.executeQuery();
			if (rs.first()) {
				Retreserva.setId(rs.getInt("codigo"));
				Retreserva.setDtReserva(rs.getDate("dataReserva").toLocalDate());
				Retreserva.setDtReservaSaida(rs.getDate("dataReservaSaida").toLocalDate()); 
				Retreserva.setStatus(rs.getString("status"));
				Retreserva.setUsuario(rs.getInt("codigoUsuario"));
				Retreserva.setQuarto(rs.getInt("numeroQuarto"));
				Retreserva.setHospede(rs.getInt("codigoHospede"));
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Retreserva;
	}

}
