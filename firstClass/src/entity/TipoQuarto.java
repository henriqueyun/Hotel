package entity;

import javax.validation.constraints.NotBlank;

public class TipoQuarto {
	private int id;
	private String tipo;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@NotBlank
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String toString() { 
		return tipo;
	}

}
