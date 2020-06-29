package dao;

import entity.Hospede;

import java.sql.*;
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
            String sql = "INSERT INTO pet (codigo, usuario, senha) "
                    + "VALUES  (0, ?, ?=)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, String.valueOf(f.getCodigo()));
//            stm.setString(2, f.getUsuario());
//            stm.setString(3, f.getSenha());
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
            String sql = "SELECT * FROM Usuario WHERE usuario like ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Hospede f = new Hospede();
//                f.setUsuario(rs.getString("usuario"));
//                f.setSenha(rs.getString("senha"));
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
            String sql = "DELETE FROM pet WHERE id = ?";
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
            String sql = "UPDATE Usuario SET usuario = ?, senha = ? WHERE id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
//            stm.setString(1, f.getUsuario());
//            stm.setString(2, f.getSenha());
            stm.setString(3, String.valueOf(f.getCodigo()));
            stm.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}