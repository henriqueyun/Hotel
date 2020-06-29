package boundary;


import control.TipoQuartoControl;
import entity.TipoQuarto;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TipoQuartoBoundary extends Application implements EventHandler<ActionEvent>{
	private TipoQuartoControl control = new TipoQuartoControl();
	private TextField txtId = new TextField();
	private TextField txtTipo = new TextField();
	private TextField txtPesquisa = new TextField();
	
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnExcluir   = new Button("Excluir");
	private Button btnAlterar   = new Button("Alterar");
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnVoltar 	= new Button("<");
	
	private TableView<TipoQuarto> tableView = new TableView<>(control.getLista());
	
	public void generateTable() { 
		TableColumn<TipoQuarto, String> colId = new TableColumn<>("Codigo");
		colId.setCellValueFactory(new PropertyValueFactory<TipoQuarto, String>("codigo"));
		
		TableColumn<TipoQuarto, String> colTipo = new TableColumn<>("Tipo");
		colTipo.setCellValueFactory(new PropertyValueFactory<TipoQuarto, String>("tipo"));
			
		tableView.getColumns().addAll(colId, colTipo);
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TipoQuarto>() {
			@Override
			public void changed(ObservableValue<? extends TipoQuarto> p, TipoQuarto antigo, TipoQuarto novo) {
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
		
		txtPesquisa.setPromptText("Tipo");
		txtPesquisa.setMinWidth(250);
		
		panCampos.add(new Label("Id: "), 0, 0);
		panCampos.add(txtId, 1, 0);
		panCampos.add(new Label("Pesquisar: "), 2, 0);
		panCampos.add(txtPesquisa, 3, 0);
		panCampos.add(new Label("Tipo: "), 0, 1);
		panCampos.add(txtTipo, 1, 1);
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
			QuartoBoundary t = new QuartoBoundary();
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
		stage.setTitle("Gest?o de Tipos de Quarto");
		control.atualizaTabela();
		bloquearCampos();
		stage.show();
	}
	
	public void handle(ActionEvent e) { 
		if (e.getTarget() == btnAdicionar) { 
			if (btnAdicionar.getText().equals("Confirmar") && !btnAlterar.getText().equals("Alterando")) {
				TipoQuarto tpQuarto = boundaryToEntity();
				control.adicionar(tpQuarto);
				entityToBoundary(new TipoQuarto());
				alterarEdicao();
				zeraCampos();
				bloquearCampos();
			}else if (btnAdicionar.getText().equals("Confirmar") && btnAlterar.getText().equals("Alterando")) {
				TipoQuarto tpQuarto = boundaryToEntity();
				if (control.alterar(tpQuarto)) {
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
			String tipo = txtPesquisa.getText();
			TipoQuarto tpQuarto = control.pesquisarPorTipo(tipo);
			if(tpQuarto != null) {
				entityToBoundary(tpQuarto);
			}else {
				zeraCampos();
			}
		}else if (e.getTarget() == btnExcluir){ 
			if (btnExcluir.getText().equals("Excluir")) {
				TipoQuarto tpQuarto = boundaryToEntity();
				control.excluir(tpQuarto);
			}else {
				btnAlterar.setText("Alterar");
				alterarEdicao();
				zeraCampos();
				bloquearCampos();
			}
		}else if (e.getTarget() == btnAlterar){ 
			if (!btnAdicionar.getText().equals("Confirmar")) {
				if (!txtId.getText().isEmpty()) {
					btnAlterar.setText("Alterando");
					liberarCampos();
					alterarEdicao();
				}
			}
		}
	}
	
	public TipoQuarto boundaryToEntity() { 
		TipoQuarto tpQuarto = new TipoQuarto();
		try { 
			if(!(btnAdicionar.getText().equals("Confirmar") && !btnAlterar.getText().equals("Alterando"))) {
				tpQuarto.setId( Integer.parseInt((txtId.getText()) ));
			}
			tpQuarto.setTipo( txtTipo.getText() );
		} catch (Exception ex) { 
			System.out.println("Erro ao computar os dados");
		}
		return tpQuarto;
	}
	
	public void entityToBoundary(TipoQuarto tpQuarto) { 
		if (tpQuarto != null) {
			txtId.setText( String.valueOf(tpQuarto.getId()) );
			txtTipo.setText( tpQuarto.getTipo() );
		}
	}
	
	public void bloquearCampos() {
		txtId.setDisable(true);
		txtTipo.setDisable(true);
		tableView.setDisable(false);
		txtPesquisa.setDisable(false);
		btnVoltar.setDisable(false);
	}
	
	
	public void liberarCampos() {
		txtTipo.setDisable(false);
		tableView.setDisable(true);
		txtPesquisa.setDisable(true);
		btnVoltar.setDisable(true);
	}
	
	
	public void zeraCampos() {
		txtId.setText("");
		txtTipo.setText("");
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
		launch();
	}

}
