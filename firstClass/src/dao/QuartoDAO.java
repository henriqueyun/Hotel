package dao;

import java.util.List;

import entity.Quarto;

public interface QuartoDAO {
	void adicionar(Quarto quarto);
	List<Quarto> pesquisarPorTipo(String tipo);
	void excluir(int id);
	void alterar(Quarto quarto);

}
