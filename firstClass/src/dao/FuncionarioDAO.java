package dao;

import entity.Funcionario;

import java.util.List;

public interface FuncionarioDAO {
	void adicionar(Funcionario f);
	List<Funcionario> pesquisarPorNome(String nome);
	void excluir(int id);
	void alterar(Funcionario f);
}