package dao;

import java.util.List;
import entity.TipoQuarto;

public interface TipoQuartoDAO {
	void adicionar(TipoQuarto tpQuarto);
	List<TipoQuarto> pesquisarPorTipo(String tipo);
	void excluir(int id);
	void alterar(TipoQuarto tpQuarto);
}
