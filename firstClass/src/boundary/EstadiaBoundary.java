package boundary;

import control.EstadiaControl;
import entity.Estadia;
import entity.Reserva;
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

public class EstadiaBoundary extends Application implements EventHandler<ActionEvent> {

	private EstadiaControl control = new EstadiaControl();

	// componentes de tela
	private ComboBox<Reserva> txtReserva = new ComboBox<>(control.getReservas());
	private TextField txtCodigo = new TextField();
	private DatePicker txtCheckin = new DatePicker();
	private DatePicker txtCheckout = new DatePicker();
	private TextField txtStatus = new TextField();


	private Button btnCheckin = new Button("Check-in");
	private Button btnCheckout = new Button("Check-out");
	private Button btnVoltar 	= new Button("<");
	
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private TableView<Estadia> tableView = new TableView<>(control.getEstadias());
	
	public void generateTable() {

		TableColumn<Estadia, Integer> colCodigo = new TableColumn<>("Codigo");
		colCodigo.setCellValueFactory(new PropertyValueFactory<Estadia, Integer>("codigo"));

		TableColumn<Estadia, String> colCheckin = new TableColumn<>("Checkin");
		colCheckin.setCellValueFactory(item ->
				new ReadOnlyStringWrapper(dtf.format(item.getValue().getDataCheckin()))
		);

		TableColumn<Estadia, String> colCheckout = new TableColumn<>("Checkout");
		colCheckout.setCellValueFactory(item ->
				new ReadOnlyStringWrapper(dtf.format(item.getValue().getDataCheckout()))
		);

		TableColumn<Estadia, String> colStatus = new TableColumn<>("Status");
		colStatus.setCellValueFactory(new PropertyValueFactory<Estadia, String>("status"));

		tableView.getColumns().addAll(colCodigo, colCheckin, colCheckout, colStatus);
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Estadia>() {
			@Override
			public void changed(ObservableValue<? extends Estadia> f, Estadia antigo, Estadia novo) {
				liberarCampos();
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
		
		txtReserva.setPromptText("");
		txtReserva.setMinWidth(100);
		txtReserva.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Reserva>() {
			@Override
			public void changed(ObservableValue<? extends Reserva> observableValue, Reserva reserva, Reserva t1) {
				zeraCampos();
				suporValores();
				bloquearCampos();
			}
		});
		panCampos.add(new Label("Reserva: "), 0, 0);
		panCampos.add(txtReserva, 1, 0);
		panCampos.add(new Label("Código: "), 0, 1);
		panCampos.add(txtCodigo, 1, 1);
		panCampos.add(new Label("Dt. Check-in: "), 0, 2);
		panCampos.add(txtCheckin, 1, 2);
		panCampos.add(new Label("Dt. Check-out: "), 0, 3);
		panCampos.add(txtCheckout, 1, 3);
		panCampos.add(new Label("Status: "), 0, 4);
		panCampos.add(txtStatus, 1, 4);
		panCampos.setPadding(new Insets(10));
		panCampos.setVgap(15);
		panCampos.setHgap(40);
		
		btnCheckout.setStyle("-fx-background-color: #008080; -fx-text-fill: #ffffff; ");
		btnCheckin.setStyle("-fx-background-color: #4682B4; -fx-text-fill: #ffffff; ");
	
		btnCheckout.setMinWidth(150);
		btnCheckin.setMinWidth(150);
		
		btnCheckout.setMinHeight(35);
		btnCheckin.setMinHeight(35);

		btnVoltar.setOnAction((a) -> {
			HomeBoundary t = new HomeBoundary();
			try {
				t.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		HBox hboxButtons = new HBox(8);
		hboxButtons.getChildren().addAll(btnCheckin, btnCheckout);
		hboxButtons.setPadding(new Insets(0, 0, 0, 40));
		
		HBox hboxTitle = new HBox(8);
		hboxTitle.getChildren().addAll(btnVoltar);
		
		VBox vbox = new VBox(8);
		vbox.getChildren().add(hboxTitle);
		vbox.getChildren().add(panCampos);
		vbox.getChildren().add(hboxButtons);
		vbox.setPadding(new Insets(5, 0, 10, 10));

		btnCheckout.setOnAction(this);
		btnCheckin.setOnAction(this);
		
		panPrincipal.setTop(vbox);
		panPrincipal.setCenter(tableView);
		tableView.requestFocus();
		
		stage.setResizable(false);
		stage.setScene(scn);
		stage.setTitle("Gestão de Estadias");
		control.pesquisar();
		bloquearCampos();
		stage.show();
	}

	public void handle(ActionEvent event) {
		if (event.getTarget() == btnCheckin) {
			Estadia e = boundaryToEntity();
			control.checkin(e);
			entityToBoundary(new Estadia());
			zeraCampos();
			bloquearCampos();
		} else if (event.getTarget() == btnCheckout) {
			Estadia e = boundaryToEntity();
			control.checkin(e);
			zeraCampos();
			bloquearCampos();
		}
	}
	public Estadia boundaryToEntity() {
		Estadia e = new Estadia();
		try {
				System.out.print("XD");
				e.setReserva(txtReserva.getSelectionModel().getSelectedItem().getId());
				if (!txtCodigo.getText().equals("")) {
					e.setCodigo(Integer.parseInt(txtCodigo.getText()));
				}
				e.setDataCheckin(txtCheckin.getValue());
				e.setDataCheckout(txtCheckout.getValue());
				e.setStatus(txtStatus.getText());
		} catch (Exception ex) {
			System.out.println("Erro ao computar os dados" + ex);
		}
		return e;
	}
	
	public void entityToBoundary(Estadia e) {
		if (e != null) {
			txtCodigo.setText(String.valueOf(e.getCodigo()));
			txtCheckin.setValue(e.getDataCheckin());
			txtCheckout.setValue(e.getDataCheckout());
			txtStatus.setText( e.getStatus() );
		}
	}
	
	public void bloquearCampos() {
		txtCodigo.setDisable(true);
		txtCheckin.setDisable(true);
		txtCheckout.setDisable(true);
		txtStatus.setDisable(true);

		tableView.setDisable(false);

		btnCheckin.setDisable(false);
		btnCheckout.setDisable(true);
	}
	
	
	public void liberarCampos() {
		btnCheckin.setDisable(true);
		btnCheckout.setDisable(false);
	}
	
	
	public void zeraCampos() {
		txtCodigo.setText("");
		txtStatus.setText("");
	}
	
	public void suporValores() {
		txtCheckin.setValue(LocalDate.now());
		txtCheckout.setValue(LocalDate.now());
		txtStatus.setText("Checar");
	}

	public static void main(String[] args) {
		Application.launch(EstadiaBoundary.class, args);
	}
}