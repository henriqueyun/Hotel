package boundary;

import control.HospedeControl;
import entity.Hospede;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HospedeBoundary extends Application implements EventHandler<ActionEvent> {

	private HospedeControl control = new HospedeControl();

	// componentes de tela
	private TextField txtCodigo = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtCPF = new TextField();
	private DatePicker txtDataNascimento = new DatePicker();
	private TextField txtPesquisa = new TextField();
	
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnExcluir   = new Button("Excluir");
	private Button btnAlterar   = new Button("Alterar");
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnVoltar 	= new Button("<");
	
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private TableView<Hospede> tableView = new TableView<>(control.getLista());
	
	public void generateTable() {

		TableColumn<Hospede, String> colCodigo = new TableColumn<>("Codigo");
		colCodigo.setCellValueFactory(new PropertyValueFactory<Hospede, String>("codigo"));

		TableColumn<Hospede, String> colNome = new TableColumn<>("Nome");
		colNome.setCellValueFactory(new PropertyValueFactory<Hospede, String>("nome"));

		TableColumn<Hospede, String> colCPF = new TableColumn<>("CPF");
		colCPF.setCellValueFactory(new PropertyValueFactory<Hospede, String>("cpf"));

		TableColumn<Hospede, String> colDataNascimento = new TableColumn<>("Nascimento");
		colDataNascimento.setCellValueFactory(item ->
				new ReadOnlyStringWrapper(dtf.format(item.getValue().getDataNascimento()))
		);

		tableView.getColumns().addAll(colCodigo, colNome, colCPF, colDataNascimento);

		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Hospede>() {
			@Override
			public void changed(ObservableValue<? extends Hospede> f, Hospede antigo, Hospede novo) {
				entityToBoundary(novo);
			}
		});
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane panPrincipal = new BorderPane();
		Scene scn = new Scene(panPrincipal, 700, 600);
	
		
		GridPane panCampos = new GridPane();
		generateTable();
		
		txtPesquisa.setPromptText("Nome do funcionário");
		txtPesquisa.setMinWidth(250);

		panCampos.add(new Label("Código: "), 0, 0);
		panCampos.add(txtCodigo, 1, 0);
		panCampos.add(new Label("Nome: "), 0, 1);
		panCampos.add(txtNome, 1, 1);
		panCampos.add(new Label("Dt. de nasc.: "), 0, 2);
		panCampos.add(txtDataNascimento, 1, 2);
		panCampos.add(new Label("CPF: "), 0, 3);
		panCampos.add(txtCPF, 1, 3);
		panCampos.add(new Label("Pesquisar: "), 2, 0);
		panCampos.add(txtPesquisa, 3, 0);
		panCampos.setPadding(new Insets(10));
		panCampos.setVgap(15);
		panCampos.setHgap(40);
		
		btnAdicionar.setStyle("-fx-background-color: #008080; -fx-text-fill: #ffffff; ");
		btnExcluir.setStyle("-fx-background-color: #A52A2A; -fx-text-fill: #ffffff; ");
		btnAlterar.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #ffffff; ");
		btnPesquisar.setStyle("-fx-background-color: #4682B4; -fx-text-fill: #ffffff; ");
	
		btnAdicionar.setMinWidth(150);
		btnExcluir.setMinWidth(150);
		btnAlterar.setMinWidth(150);
		btnPesquisar.setMinWidth(150);
		
		btnAdicionar.setMinHeight(35);
		btnExcluir.setMinHeight(35);
		btnAlterar.setMinHeight(35);
		btnPesquisar.setMinHeight(35);

		btnVoltar.setOnAction((a) -> {
			HomeBoundary t = new HomeBoundary();
			try {
				t.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		HBox hboxButtons = new HBox(8);
		hboxButtons.getChildren().addAll(btnAdicionar,btnExcluir, btnAlterar,btnPesquisar);
		hboxButtons.setPadding(new Insets(0, 0, 0, 40));
		
		HBox hboxTitle = new HBox(8);
		hboxTitle.getChildren().addAll(btnVoltar);
		
		VBox vbox = new VBox(8);
		vbox.getChildren().add(hboxTitle);
		vbox.getChildren().add(panCampos);
		vbox.getChildren().add(hboxButtons);
		vbox.setPadding(new Insets(5, 0, 10, 10));
		
		btnAdicionar.setOnAction(this);
		btnPesquisar.setOnAction(this);
		btnExcluir.setOnAction(this);
		btnAlterar.setOnAction(this);
		
		panPrincipal.setTop(vbox);
		panPrincipal.setCenter(tableView);
		tableView.requestFocus();
		
		stage.setResizable(false);
		stage.setScene(scn);
		stage.setTitle("Gestão de Hóspedes");
		control.atualizaTabela();
		bloquearCampos();
		stage.show();
	}
	
	public void handle(ActionEvent e) { 
		if (e.getTarget() == btnAdicionar) { 
			if (btnAdicionar.getText().equals("Confirmar") && !btnAlterar.getText().equals("Alterando")) {
				Hospede h = boundaryToEntity();
				control.adicionar(h);
				entityToBoundary(new Hospede());
				alterarEdicao();
				zeraCampos();
				bloquearCampos();
			}else if (btnAdicionar.getText().equals("Confirmar") && btnAlterar.getText().equals("Alterando")) {
				Hospede h = boundaryToEntity();
				if (control.alterar(h)) {
					alterarEdicao();
					zeraCampos();
					bloquearCampos();
					btnAlterar.setText("Alterar");
				}
			}else {
				zeraCampos();
				liberarCampos();
				alterarEdicao();
			}
			
		} else if (e.getTarget() == btnPesquisar){ 
			String nome = txtPesquisa.getText();
			Hospede f = control.pesquisarPorNome(nome);
			if(f != null) {
				entityToBoundary(f);
			}else {
				zeraCampos();
			}
		}else if (e.getTarget() == btnExcluir){ 
			if (btnExcluir.getText().equals("Excluir")) {
				Hospede f = boundaryToEntity();
				control.excluir(f);
			}else {
				btnAlterar.setText("Alterar");
				alterarEdicao();
				zeraCampos();
				bloquearCampos();
			}
		}else if (e.getTarget() == btnAlterar){ 
			if (!btnAdicionar.getText().equals("Confirmar")) {
				if (!txtNome.getText().isEmpty()) {
					btnAlterar.setText("Alterando");
					liberarCampos();
					alterarEdicao();
				}
			}
		}
	}
	
	public Hospede boundaryToEntity() {
		Hospede h = new Hospede();
		try {
			if(!(btnAdicionar.getText().equals("Confirmar") && !btnAlterar.getText().equals("Alterando"))) {
				h.setCodigo(Integer.parseInt(txtCodigo.getText()));
			}
			h.setNome(txtNome.getText());
			h.setCpf(txtCPF.getText());
			h.setDataNascimento(txtDataNascimento.getValue());
		} catch (Exception ex) {
			System.err.println("Erro ao computar os dados: " + ex);
		}
		return h;
	}
	
	public void entityToBoundary(Hospede h) {
		if (h != null) {
			txtCodigo.setText(String.valueOf(h.getCodigo()));
			txtNome.setText(String.valueOf(h.getNome()) );
			txtCPF.setText(h.getCpf());
			txtDataNascimento.setValue(h.getDataNascimento());
		}
	}
	
	public void bloquearCampos() {
		txtCodigo.setDisable(true);
		txtNome.setDisable(true);
		txtCPF.setDisable(true);
		txtDataNascimento.setDisable(true);
		tableView.setDisable(false);
		txtPesquisa.setDisable(false);
		btnVoltar.setDisable(false);
	}
	
	
	public void liberarCampos() {
		txtNome.setDisable(false);
		txtCPF.setDisable(false);
		txtDataNascimento.setDisable(false);
		tableView.setDisable(true);
		txtPesquisa.setDisable(true);
		btnVoltar.setDisable(true);
	}

	
	public void zeraCampos() {
		txtCodigo.setText("");
		txtNome.setText("");
		txtCPF.setText("");
		txtDataNascimento.setValue(LocalDate.now());
	}

	public void alterarEdicao() {
		if (btnAdicionar.getText().equals("Adicionar")) {
			btnAdicionar.setText("Confirmar");
			btnExcluir.setText("Cancelar");
			btnAlterar.setVisible(false);
			btnPesquisar.setVisible(false);
		}else {
			btnAdicionar.setText("Adicionar");
			btnExcluir.setText("Excluir");
			btnAlterar.setVisible(true);
			btnPesquisar.setVisible(true);
		}
	}
	
	public static void main(String[] args) {
		Application.launch(HospedeBoundary.class, args);
	}
}