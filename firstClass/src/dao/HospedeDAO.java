package dao;

import entity.Hospede;

import java.util.List;

public interface HospedeDAO {
    void adicionar(Hospede f);
    List<Hospede> pesquisarPorNome(String nome);
    void excluir(int id);
    void alterar(Hospede f);
}