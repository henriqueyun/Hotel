import java.util.Date;

public class Hospede {

    private String cpf;
    private String nome;
    private Date dataNascimento;

    public Hospede(String nome, String cpf, Date dataNascimento){
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }
}
