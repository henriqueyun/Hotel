package dao;

import java.util.List;

import entity.Reserva;

public interface ReservaDAO {
	void reservar(Reserva reserva);
	List<Reserva> pesquisarPorHospede(String hospede);
	void excluir(int id);
	void alterar(Reserva reserva);

}
