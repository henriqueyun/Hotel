package dao;

import entity.Estadia;

import java.util.List;

public interface EstadiaDAO {
	void checkin(Estadia f);
	List<Estadia> pesquisar();
	void excluir(int id);
	void alterar(Estadia f);
}