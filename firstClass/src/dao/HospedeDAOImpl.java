package dao;

import entity.Hospede;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HospedeDAOImpl implements HospedeDAO {
    private static final String URL = "jdbc:mariadb://localhost/hotel?allowMultiQueries=true";
    private static final String USER = "henriqueyun";
    private static final String PASS = "123";

    public HospedeDAOImpl() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void adicionar(Hospede f) {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            String sql = "INSERT INTO Hospede (nome, cpf, dataNascimento) "
                    + "VALUES  (?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, f.getNome());
            stm.setString(2, f.getCpf());
            stm.setString(3, String.valueOf(f.getDataNascimento()));
            stm.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Hospede> pesquisarPorNome(String nome) {
        List<Hospede> lista = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            String sql = "SELECT * FROM Hospede WHERE nome like ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Hospede h = new Hospede();
                h.setCodigo(rs.getInt("codigo"));
                h.setNome(rs.getString("nome"));
                h.setCpf(rs.getString("cpf"));
                h.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                lista.add(h);
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
            String sql = "DELETE FROM Hospede WHERE codigo = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setLong(1, codigo);
            stm.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void alterar(Hospede f) {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            String sql = "UPDATE Hospede SET nome = ?, cpf = ?, dataNascimento = ? WHERE codigo = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, f.getNome());
            stm.setString(2, f.getCpf());
            stm.setDate(3, java.sql.Date.valueOf(f.getDataNascimento()));
            stm.setInt(4, f.getCodigo());
            stm.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}