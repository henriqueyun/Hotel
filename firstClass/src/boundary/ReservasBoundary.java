package boundary;

import java.time.format.DateTimeFormatter;

import control.ReservaControl;
import entity.Hospede;
import entity.Quarto;
import entity.Reserva;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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

public class ReservasBoundary extends Application implements EventHandler<ActionEvent> {
	private ReservaControl control = new ReservaControl();
	private TextField txtId = new TextField();
	private ComboBox<Quarto> txtQuartos = new ComboBox<>(control.getQuartos());
	private ComboBox<Hospede> txtHospedes = new ComboBox<>(control.getHospedes());
	private DatePicker txtDtReserva = new DatePicker();
	private DatePicker txtDtSaida = new DatePicker();
	private TextField txtStatus = new TextField();
	private TextField txtPesquisa = new TextField();
	
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnExcluir   = new Button("Excluir");
	private Button btnAlterar   = new Button("Alterar");
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnVoltar 	= new Button("<");
	
	private TableView<Reserva> tableView = new TableView<>(control.getLista());
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	@SuppressWarnings("unchecked")
	public void generateTable() { 
		TableColumn<Reserva, String> colDtReserva = new TableColumn<>("Data Inicio");
		colDtReserva.setCellValueFactory(item -> 
			new ReadOnlyStringWrapper(dtf.format(item.getValue().getDtReserva()))
		);
		
		TableColumn<Reserva, String> colDtReservaSaida = new TableColumn<>("Dt Termino");
		colDtReservaSaida.setCellValueFactory(item -> 
			new ReadOnlyStringWrapper(dtf.format(item.getValue().getDtReservaSaida()))
		);
		
		TableColumn<Reserva, String> colStatus = new TableColumn<>("Status");
		colStatus.setCellValueFactory(new PropertyValueFactory<Reserva, String>("status"));
		
		TableColumn<Reserva, Integer> colQuarto = new TableColumn<>("Quarto");
		colQuarto.setCellValueFactory(new PropertyValueFactory<Reserva, Integer>("quarto"));
		
		TableColumn<Reserva, Integer> colHospede = new TableColumn<>("Hospede");
		colHospede.setCellValueFactory(new PropertyValueFactory<Reserva, Integer>("hospede"));
				
		tableView.getColumns().addAll(colDtReserva, colDtReservaSaida, colStatus, colQuarto, colHospede  );
		
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Reserva>() {
			@Override
			public void changed(ObservableValue<? extends Reserva> q, Reserva antigo, Reserva novo) {
				entityToBoundary(novo);
			}
		});
	}
	
	public void start(Stage stage) throws Exception {
		BorderPane panPrincipal = new BorderPane();
		Scene scn = new Scene(panPrincipal, 700, 600);
	
		
		GridPane panCampos = new GridPane();
		generateTable();
		
		txtPesquisa.setPromptText("Nome do Hospede");
		txtPesquisa.setMinWidth(250);
		
		panCampos.add(new Label("Id: "), 0, 0);
		panCampos.add(txtId, 1, 0);
		panCampos.add(new Label("Pesquisar: "), 2, 0);
		panCampos.add(txtPesquisa, 3, 0);
		panCampos.add(new Label("Dt Reserva: "), 0, 1);
		panCampos.add(txtDtReserva, 1, 1);
		panCampos.add(new Label("Dt Saida: "), 0, 2);
		panCampos.add(txtDtSaida, 1, 2);
		panCampos.add(new Label("Status: "), 0, 3);
		panCampos.add(txtStatus, 1, 3);
		panCampos.add(new Label("Quarto: "), 0, 4);
		panCampos.add(txtQuartos, 1, 4);
		panCampos.add(new Label("Hospedes: "), 0, 5);
		panCampos.add(txtHospedes, 1, 5);
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
		stage.setTitle("Gestão de Reservas");
		control.atualizaTabela();
		bloquearCampos();
		stage.show();
	}
	
	public void handle(ActionEvent e) { 
		if (e.getTarget() == btnAdicionar) { 
			if (btnAdicionar.getText().equals("Confirmar") && !btnAlterar.getText().equals("Alterando")) {
				Reserva reserva = boundaryToEntity();
				control.adicionar(reserva);
				entityToBoundary(new Reserva());
				alterarEdicao();
				zeraCampos();
				bloquearCampos();
			}else if (btnAdicionar.getText().equals("Confirmar") && btnAlterar.getText().equals("Alterando")) {
				Reserva reserva = boundaryToEntity();
				if (control.alterar(reserva)) {
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
			String hospede = txtPesquisa.getText();
			Reserva reserva = control.pesquisarPorHospede(hospede);
			if(reserva != null) {
				entityToBoundary(reserva);
			}else {
				zeraCampos();
			}
		}else if (e.getTarget() == btnExcluir){ 
			if (btnExcluir.getText().equals("Excluir")) {
				Reserva reserva = boundaryToEntity();
				control.excluir(reserva);
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
	
	public Reserva boundaryToEntity() { 
		Reserva reserva = new Reserva();
		try { 
			if(isThisUpdate()) {
				reserva.setId( Integer.parseInt(txtId.getText()) );
			}
			reserva.setDtReserva(txtDtReserva.getValue());
			reserva.setDtReservaSaida(txtDtSaida.getValue());
			reserva.setStatus(txtStatus.getText());
			reserva.setQuarto(txtQuartos.getSelectionModel().getSelectedItem().getId());
			reserva.setHospede(txtHospedes.getSelectionModel().getSelectedItem().getCodigo());
			reserva.setUsuario(HomeBoundary.funcionario.getCodigo());
		} catch (Exception ex) { 
			System.out.println("Erro ao computar os dados");
		}
		return reserva;
	}
	
	public void entityToBoundary(Reserva reserva) { 
		if (reserva != null) {
			txtId.setText( String.valueOf(reserva.getId()) );
			txtDtReserva.setValue(reserva.getDtReserva());
			txtDtSaida.setValue(reserva.getDtReservaSaida());
			txtStatus.setText( reserva.getStatus() );
			for (Quarto quart : control.getQuartos()) {
				if(reserva.getQuarto() == quart.getId()) {
					txtQuartos.getSelectionModel().select( quart );
				}
			}
			for (Hospede hosped : control.getHospedes()) {
				if(reserva.getHospede() == hosped.getCodigo()) {
					txtHospedes.getSelectionModel().select( hosped );
				}
			}
		}
	}
	
	public void bloquearCampos() {
		txtId.setDisable(true);
		txtDtReserva.setDisable(true);
		txtDtSaida.setDisable(true);
		txtStatus.setDisable(true);
		txtQuartos.setDisable(true);
		txtHospedes.setDisable(true);
		tableView.setDisable(false);
		txtPesquisa.setDisable(false);
		btnVoltar.setDisable(false);
	}
	
	
	public void liberarCampos() {
		txtDtReserva.setDisable(false);
		txtDtSaida.setDisable(false);
		txtQuartos.setDisable(false);
		txtHospedes.setDisable(false);
		tableView.setDisable(true);
		txtPesquisa.setDisable(true);
		btnVoltar.setDisable(true);
	}
	
	
	public void zeraCampos() {
		txtId.setText("");
		txtStatus.setText("Reservado");
		txtDtReserva.setValue(null);
		txtDtSaida.setValue(null);
		txtQuartos.getSelectionModel().select(null);
		txtHospedes.getSelectionModel().select(null);
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

	// verifica a operação é uma inclusão ou edição
	public boolean isThisUpdate() {
		return !(btnAdicionar.getText().equals("Confirmar") && !btnAlterar.getText().equals("Alterando"));
	}

	public static void main(String[] args) {
		launch();
	}
}
