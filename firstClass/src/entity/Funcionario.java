package entity;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;

public class Funcionario implements Comparable<Funcionario> {

	private int codigo;
	private String usuario = "";
	private String senha = "";

	public Funcionario() {}
	public Funcionario(String nome) {
		this.usuario = nome;
	}

	@Override
	public int compareTo(Funcionario f) {
		return getUsuario().compareTo(f.getUsuario());
	}

	public String getUsuario() {
		return usuario;
	}
	public String getSenha() {
		return usuario;
	}

	@Override
	public String toString() { 
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}