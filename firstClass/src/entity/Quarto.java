package entity;

import java.util.List;
import javax.validation.constraints.DecimalMin;
import control.TipoQuartoControl;


public class Quarto {
	private int id;
	private int tipo;
	private double valordia;
	private int qtdcama;
	private boolean disponivel;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	@DecimalMin(value="10.0")
	public double getValordia() {
		return valordia;
	}
	public void setValordia(double valordia) {
		this.valordia = valordia;
	}
	
	public int getQtdcama() {
		return qtdcama;
	}
	public void setQtdcama(int qtdcama) {
		this.qtdcama = qtdcama;
	}
	
	public boolean isDisponivel() {
		return disponivel;
	}
	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}
	
	public String toString() { 
		String ret = "";
		TipoQuartoControl quartoDao = new TipoQuartoControl();
		List<TipoQuarto> tpQuarto = quartoDao.retornaTipos();
		for(TipoQuarto tp: tpQuarto) {
			if(tp.getId() == this.getId()) {
				ret = tp.getTipo();
			}
		}
		return ret;
	}
}
