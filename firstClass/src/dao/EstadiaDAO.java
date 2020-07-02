package dao;

import entity.Estadia;

import java.util.List;

public interface EstadiaDAO {
	void checkin(Estadia f);
	void checkout(Estadia e);
	List<Estadia> pesquisar();
}