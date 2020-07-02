package entity;

import java.time.LocalDate;

public class Reserva {
	private int id;
	private LocalDate dtReserva;
	private LocalDate dtReservaSaida;
	private String status;
	private int quarto;
	private int usuario;
	private int hospede;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDtReserva() {
		return dtReserva;
	}
	public void setDtReserva(LocalDate dtReserva) {
		this.dtReserva = dtReserva;
	}
	public LocalDate getDtReservaSaida() {
		return dtReservaSaida;
	}
	public void setDtReservaSaida(LocalDate dtReservaSaida) {
		this.dtReservaSaida = dtReservaSaida;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getQuarto() {
		return quarto;
	}
	public void setQuarto(int quarto) {
		this.quarto = quarto;
	}
	public int getHospede() {
		return hospede;
	}
	public void setHospede(int hospede) {
		this.hospede = hospede;
	}
	public int getUsuario() {
		return usuario;
	}
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	public String toString(){
		return id + "(" + hospede + ")";
	};
}
