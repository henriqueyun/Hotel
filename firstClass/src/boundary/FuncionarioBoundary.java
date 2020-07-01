package boundary;

import control.FuncionarioControl;
import entity.Funcionario;
import javafx.application.Application;
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
import java.time.format.DateTimeFormatter;

public class FuncionarioBoundary extends Application implements EventHandler<ActionEvent> {

	private FuncionarioControl control = new FuncionarioControl();

	// componentes de tela
	private TextField txtUsuario = new TextField();
	private TextField txtSenha = new TextField();
	private TextField txtCodigo = new TextField();
	private TextField txtPesquisa = new TextField();

	
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnExcluir   = new Button("Excluir");
	private Button btnAlterar   = new Button("Alterar");
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnVoltar 	= new Button("<");
	
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private TableView<Funcionario> tableView = new TableView<>(control.getLista());
	
	public void generateTable() {

		TableColumn<Funcionario, Integer> colCodigo = new TableColumn<>("Codigo");
		colCodigo.setCellValueFactory(new PropertyValueFactory<Funcionario, Integer>("codigo"));

		TableColumn<Funcionario, String> colUsuario = new TableColumn<>("Usuario");
		colUsuario.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("usuario"));
		
		TableColumn<Funcionario, String> colSenha = new TableColumn<>("Senha");
		colSenha.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("senha"));

		tableView.getColumns().addAll(colCodigo, colUsuario, colSenha);
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Funcionario>() {
			@Override
			public void changed(ObservableValue<? extends Funcionario> f, Funcionario antigo, Funcionario novo) {
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
		panCampos.add(new Label("Usuário: "), 0, 1);
		panCampos.add(txtUsuario, 1, 1);
		panCampos.add(new Label("Senha: "), 0, 2);
		panCampos.add(txtSenha, 1, 2);
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
		stage.setTitle("Gestão de Funcionários");
		control.atualizaTabela();
		bloquearCampos();
		stage.show();
	}
	
	public void handle(ActionEvent e) { 
		if (e.getTarget() == btnAdicionar) { 
			if (btnAdicionar.getText().equals("Confirmar") && !btnAlterar.getText().equals("Alterando")) {
				Funcionario f = boundaryToEntity();
				control.adicionar(f);
				entityToBoundary(new Funcionario());
				alterarEdicao();
				zeraCampos();
				bloquearCampos();
			}else if (btnAdicionar.getText().equals("Confirmar") && btnAlterar.getText().equals("Alterando")) {
				Funcionario f = boundaryToEntity();
				if (control.alterar(f)) {
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
			Funcionario f = control.pesquisarPorNome(nome);
			if(f != null) {
				entityToBoundary(f);
			}else {
				zeraCampos();
			}
		}else if (e.getTarget() == btnExcluir){ 
			if (btnExcluir.getText().equals("Excluir")) {
				Funcionario f = boundaryToEntity();
				control.excluir(f);
			}else {
				btnAlterar.setText("Alterar");
				alterarEdicao();
				zeraCampos();
				bloquearCampos();
			}
		}else if (e.getTarget() == btnAlterar){ 
			if (!btnAdicionar.getText().equals("Confirmar")) {
				if (!txtUsuario.getText().isEmpty()) {
					btnAlterar.setText("Alterando");
					liberarCampos();
					alterarEdicao();
				}
			}
		}
	}
	
	public Funcionario boundaryToEntity() {
		Funcionario f = new Funcionario();
		try { 
			if(isThisUpdate()) {
				System.out.print("XD");
				f.setCodigo(Integer.parseInt(txtCodigo.getText()));
			}
				f.setUsuario(txtUsuario.getText());
				f.setSenha(txtUsuario.getText());
		} catch (Exception ex) {
			System.out.println("Erro ao computar os dados" + ex);
		}
		return f;
	}
	
	public void entityToBoundary(Funcionario f) {
		if (f != null) {
			txtCodigo.setText(String.valueOf(f.getCodigo()));
			txtUsuario.setText( String.valueOf(f.getUsuario()) );
			txtSenha.setText( f.getSenha() );
		}
	}
	
	public void bloquearCampos() {
		txtCodigo.setDisable(true);
		txtUsuario.setDisable(true);
		txtSenha.setDisable(true);
		tableView.setDisable(false);
		txtPesquisa.setDisable(false);
		btnVoltar.setDisable(false);
	}
	
	
	public void liberarCampos() {
		txtUsuario.setDisable(false);
		txtSenha.setDisable(false);
		tableView.setDisable(true);
		txtPesquisa.setDisable(true);
		btnVoltar.setDisable(true);
	}
	
	
	public void zeraCampos() {
		txtUsuario.setText("");
		txtSenha.setText("");
	}
	
	public void alterarEdicao() {
		if (btnAdicionar.getText().equals("Adicionar")) {
			btnAdicionar.setText("Confirmar");
			btnExcluir.setText("Cancelar");
			btnAlterar.setVisible(false);
			btnPesquisar.setVisible(false);
		} else {
			btnAdicionar.setText("Adicionar");
			btnExcluir.setText("Excluir");
			btnAlterar.setVisible(true);
			btnPesquisar.setVisible(true);
		}
	}

	// verifica a operação é uma inclusão ou edição
	public boolean isThisUpdate() {
		return !(btnAdicionar.getText().equals("Confirmar") && !btnAlterar.getText().equals("Alterando"));
	}

	public static void main(String[] args) {
		Application.launch(FuncionarioBoundary.class, args);
	}
}