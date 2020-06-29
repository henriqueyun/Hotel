package entity;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;

public class Hospede implements Comparable<Hospede> {

    private int codigo;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

    public Hospede() {}

    public Hospede(String nome) {
        this.nome = nome;
    }

    @Override
    public int compareTo(Hospede f) {
        return getNome().compareTo(f.getNome());
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}