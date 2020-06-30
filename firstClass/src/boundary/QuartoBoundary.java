package boundary;

import control.QuartoControl;
import entity.Quarto;
import entity.TipoQuarto;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import util.Config;

public class QuartoBoundary  extends Application implements EventHandler<ActionEvent> {
	private QuartoControl control = new QuartoControl();
	private TextField txtId = new TextField();
	private ComboBox<TipoQuarto> txtTipos = new ComboBox<>(control.getTipos());
	private TextField txtVlDia = new TextField();
	private TextField txtQtdCama = new TextField();
	private TextField txtPesquisa = new TextField();
	private TextField txtDisponivel = new TextField();
	
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnExcluir   = new Button("Excluir");
	private Button btnAlterar   = new Button("Alterar");
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnVoltar 	= new Button("<");
	private Button btnTipos;
	
	private TableView<Quarto> tableView = new TableView<>(control.getLista());
	
	public QuartoBoundary() {
		btnTipos = new Button("", Config.btnIcon("dormir.png"));
		btnTipos.setStyle("-fx-background-color: #6959CD; -fx-text-fill: #ffffff; ");
		
		btnTipos.setMinWidth(170);
		btnTipos.setMinHeight(25);
	}
	
	@SuppressWarnings("unchecked")
	public void generateTable() { 
		TableColumn<Quarto, Integer> colTipo = new TableColumn<>("Tipo Quarto");
		colTipo.setCellValueFactory(new PropertyValueFactory<Quarto, Integer>("tipo"));
		
		TableColumn<Quarto, Double> colVlDia = new TableColumn<>("Vl Diaria");
		colVlDia.setCellValueFactory(new PropertyValueFactory<Quarto, Double>("valordia"));
		
		TableColumn<Quarto, Integer> colQtdCama = new TableColumn<>("Qtd Camas");
		colQtdCama.setCellValueFactory(new PropertyValueFactory<Quarto, Integer>("qtdcama"));
		
		TableColumn<Quarto, Boolean> colDisponivel = new TableColumn<>("Disponivel");
		colDisponivel.setCellValueFactory(new PropertyValueFactory<Quarto, Boolean>("disponivel"));
				
		tableView.getColumns().addAll(colTipo, colVlDia, colQtdCama, colDisponivel);
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Quarto>() {
			@Override
			public void changed(ObservableValue<? extends Quarto> q, Quarto antigo, Quarto novo) {
				entityToBoundary(novo);
			}
		});
	}
	
	public void start(Stage stage) throws Exception {
		BorderPane panPrincipal = new BorderPane();
		Scene scn = new Scene(panPrincipal, 700, 600);
	
		
		GridPane panCampos = new GridPane();
		generateTable();
		
		btnTipos.setOnAction((a) -> {
			TipoQuartoBoundary qtScreen = new TipoQuartoBoundary();
			try {
				qtScreen.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		txtPesquisa.setPromptText("Tipo do Quarto");
		txtPesquisa.setMinWidth(250);
		
		panCampos.add(new Label("Id: "), 0, 0);
		panCampos.add(txtId, 1, 0);
		panCampos.add(new Label("Pesquisar: "), 2, 0);
		panCampos.add(txtPesquisa, 3, 0);
		panCampos.add(new Label("Tps de Quarto: "), 2, 1);
		panCampos.add(btnTipos, 3, 1);
		panCampos.add(new Label("Tipo: "), 0, 1);
		panCampos.add(txtTipos, 1, 1);
		panCampos.add(new Label("Vl Diaria: "), 0, 2);
		panCampos.add(txtVlDia, 1, 2);
		panCampos.add(new Label("Qtd Cama: "), 0, 3);
		panCampos.add(txtQtdCama, 1, 3);
		panCampos.add(new Label("Disponivel: "), 0, 4);
		panCampos.add(txtDisponivel, 1, 4);;
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
		stage.setTitle("Gestão de Quartos");
		control.atualizaTabela();
		bloquearCampos();
		stage.show();
	}
	
	public void handle(ActionEvent e) { 
		if (e.getTarget() == btnAdicionar) { 
			if (btnAdicionar.getText().equals("Confirmar") && !btnAlterar.getText().equals("Alterando")) {
				Quarto quarto = boundaryToEntity();
				control.adicionar(quarto);
				entityToBoundary(new Quarto());
				alterarEdicao();
				zeraCampos();
				bloquearCampos();
			}else if (btnAdicionar.getText().equals("Confirmar") && btnAlterar.getText().equals("Alterando")) {
				Quarto quarto = boundaryToEntity();
				if (control.alterar(quarto)) {
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
			Quarto quarto = control.pesquisarPorTipo(tipo);
			if(quarto != null) {
				entityToBoundary(quarto);
			}else {
				zeraCampos();
			}
		}else if (e.getTarget() == btnExcluir){ 
			if (btnExcluir.getText().equals("Excluir")) {
				Quarto quarto = boundaryToEntity();
				control.excluir(quarto);
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
	
	public Quarto boundaryToEntity() { 
		Quarto quarto = new Quarto();
		try { 
			if(!(btnAdicionar.getText().equals("Confirmar") && !btnAlterar.getText().equals("Alterando"))) {
				quarto.setId( Integer.parseInt(txtId.getText()) );
			}
			quarto.setTipo(  txtTipos.getSelectionModel().getSelectedItem().getId() );
			quarto.setValordia(Double.parseDouble(txtVlDia.getText()) );
			quarto.setQtdcama(Integer.parseInt(txtQtdCama.getText()) );
			quarto.setDisponivel(Boolean.parseBoolean(txtDisponivel.getText()) );
			
		} catch (Exception ex) { 
			System.out.println("Erro ao computar os dados");
		}
		return quarto;
	}
	
	public void entityToBoundary(Quarto quarto) { 
		if (quarto != null) {
			txtId.setText( String.valueOf(quarto.getId()) );
			
			for (TipoQuarto tpQuart : control.getTipos()) {
				if(quarto.getTipo() == tpQuart.getId()) {
					txtTipos.getSelectionModel().select( tpQuart );
				}
			}
			
			txtVlDia.setText( String.valueOf(quarto.getValordia()) );
			txtQtdCama.setText( String.valueOf(quarto.getQtdcama()) );
			txtDisponivel.setText( String.valueOf(quarto.isDisponivel()) );
		}
	}
	
	public void bloquearCampos() {
		txtId.setDisable(true);
		txtTipos.setDisable(true);
		txtVlDia.setDisable(true);
		txtQtdCama.setDisable(true);
		txtDisponivel.setDisable(true);
		tableView.setDisable(false);
		txtPesquisa.setDisable(false);
		btnVoltar.setDisable(false);
	}
	
	
	public void liberarCampos() {
		txtTipos.setDisable(false);
		txtVlDia.setDisable(false);
		txtQtdCama.setDisable(false);
		tableView.setDisable(true);
		txtPesquisa.setDisable(true);
		btnVoltar.setDisable(true);
	}
	
	
	public void zeraCampos() {
		txtId.setText("");
		txtVlDia.setText("");
		txtTipos.getSelectionModel().select(null);
		txtQtdCama.setText("");
		txtDisponivel.setText("false");
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
