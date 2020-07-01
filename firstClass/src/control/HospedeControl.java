package control;
import dao.HospedeDAO;
import dao.HospedeDAOImpl;
import entity.Hospede;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class HospedeControl {
    private HospedeDAO hospedeDAO = new HospedeDAOImpl();
    private ObservableList<Hospede> lista = FXCollections.observableArrayList();

    private Validator validator;

    public HospedeControl() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private void alert(AlertType tipo, String title, String header, String content) {
        Alert alert = new Alert(tipo);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void adicionar(Hospede h) {
        Set<ConstraintViolation<Hospede>> erros = validator.validate(h);
        if (erros.isEmpty()) {
            hospedeDAO.adicionar(h);
            alert(AlertType.INFORMATION, "Hotel", null,
                    "Hospede " + h.getNome() + " cadastrado com sucesso");
            atualizaTabela();
        } else {
            String msgErros = "Erros: \n";
            for (ConstraintViolation<Hospede> erro : erros ) {
                msgErros += erro.getPropertyPath() + " - " + erro.getMessage() + "\n";
            }
            alert(AlertType.ERROR, "Hotel", "ERRO: Não foi possivel cadastrar o funcionario", msgErros);
        }
    }

    public void excluir(Hospede h) {
        ButtonType btnOk = new ButtonType("ok", ButtonData.OK_DONE);
        ButtonType btnNo = new ButtonType("no", ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Você realmente deseja deletar o(a) " + h.getNome() + "?",
                btnOk,
                btnNo);

        alert.setTitle("Hotel");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(btnNo) == btnOk) {
            hospedeDAO.excluir(h.getCodigo());
            atualizaTabela();
        }
    }

    public boolean alterar(Hospede h) {
        boolean alterou = false;
        ButtonType btnOk = new ButtonType("ok", ButtonData.OK_DONE);
        ButtonType btnNo = new ButtonType("no", ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Deseja realmente alterar esses dados?",
                btnOk,
                btnNo);

        alert.setTitle("Hospedes");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(btnNo) == btnOk) {
            Set<ConstraintViolation<Hospede>> erros = validator.validate(h);
            if (erros.isEmpty()) {
                hospedeDAO.alterar(h);
                atualizaTabela();
                alterou = true;;
            } else {
                String msgErros = "Erros: \n";
                for (ConstraintViolation<Hospede> erro : erros ) {
                    msgErros += erro.getPropertyPath() + " - " + erro.getMessage() + "\n";
                }
                alert(AlertType.ERROR, "Hospede", "ERRO: Não foi possível alterar o funcionário", msgErros);
            }
        }

        return alterou;
    }

    public Hospede pesquisarPorNome(String nome) {
        lista.clear();
        List<Hospede> hospedes = hospedeDAO.pesquisarPorNome(nome);
        if(hospedes.isEmpty()) {
            alert(AlertType.ERROR, "Error na busca", null, "Não foi encontrado nenhum Pet com esse nome.");
            return null;
        }else {
            lista.addAll(hospedes);
        }
        return lista.get(0);
    }


    public void atualizaTabela() {
        lista.clear();
        List<Hospede> hospedes = hospedeDAO.pesquisarPorNome("");
        lista.addAll(hospedes);
    }

    public List<Hospede> retornaHospede() {
        List<Hospede> hospedes = hospedeDAO.pesquisarPorNome("");
        if (hospedes.isEmpty()) {
            return Collections.emptyList();
        } else {
            lista.addAll(hospedes);
        }
        return lista;
    }

    public ObservableList<Hospede> getLista() {
        return lista;
    }

}