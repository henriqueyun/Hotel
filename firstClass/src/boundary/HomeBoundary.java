package boundary;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Config;

public class HomeBoundary extends Application {

	private Button btnCheck;
	private Button btnQuartos;
	private Button btnReservas;
	private Button btnFuncionarios;
	private Button btnHospedes;
	private Button btnSobre;

	public HomeBoundary() {

		// botões principais
		btnCheck = new Button("", Config.homeIcon("sineta.png"));
		btnCheck.setStyle("-fx-background-color: #008080; -fx-text-fill: #ffffff; ");

		btnQuartos = new Button("", Config.homeIcon("cama.png"));
		btnQuartos.setStyle("-fx-background-color: #A52A2A; -fx-text-fill: #ffffff; ");

		btnReservas = new Button("", Config.homeIcon("reserva.png"));
		btnReservas.setStyle("-fx-background-color: #6959CD; -fx-text-fill: #ffffff; ");

		btnFuncionarios = new Button("", Config.homeIcon("empregado.png"));
		btnFuncionarios.setStyle("-fx-background-color: #A0522D; -fx-text-fill: #ffffff; ");

		btnHospedes = new Button("", Config.homeIcon("Hospedes.png"));
		btnHospedes.setStyle("-fx-background-color: #4169E1; -fx-text-fill: #ffffff; ");

		btnSobre = new Button("", Config.homeIcon("about.png"));
		btnSobre.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #ffffff; ");

		btnCheck.setMinWidth(250);
		btnCheck.setMinHeight(200);

		btnQuartos.setMinWidth(250);
		btnQuartos.setMinHeight(200);

		btnReservas.setMinWidth(250);
		btnReservas.setMinHeight(200);

		btnFuncionarios.setMinWidth(250);
		btnFuncionarios.setMinHeight(200);

		btnHospedes.setMinWidth(250);
		btnHospedes.setMinHeight(200);

		btnSobre.setMinWidth(250);
		btnSobre.setMinHeight(200);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox panPrincipal = new VBox(8);
		Scene scn = new Scene(panPrincipal, 900, 600);

		btnQuartos.setOnAction((a) -> {
			QuartoBoundary tpScreen = new QuartoBoundary();
			try {
				tpScreen.start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		btnReservas.setOnAction((a) -> {
			ReservasBoundary rsvScreen = new ReservasBoundary();
			try {
				rsvScreen.start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		btnFuncionarios.setOnAction((a) -> {
			FuncionarioBoundary hspScreen = new FuncionarioBoundary();
			try {
				hspScreen.start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			};
		});

		btnHospedes.setOnAction((a) -> {
			HospedeBoundary hspScreen = new HospedeBoundary();
			try {
				hspScreen.start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			};
		});
		
		Label check = new Label("Check");
		Label Quartos = new Label("Quartos");
		Label Reservas = new Label("Reservas");
		
		Label funcionarios = new Label("Funcionarios");
		Label hospedes = new Label("Hospedes");
		Label sobre = new Label("About");
		
		check.setMinWidth(240);
		Quartos.setMinWidth(250);
		Reservas.setMinWidth(200);
		
		funcionarios.setMinWidth(260);
		hospedes.setMinWidth(255);
		sobre.setMinWidth(200);

		HBox hboxt = new HBox(8);
		hboxt.getChildren().setAll(new Label("Titulo", new Label("Empregada")));
		hboxt.setPadding(new Insets(10, 0, 50, 0));

		HBox hbox1 = new HBox(8);
		hbox1.getChildren().setAll(btnCheck, btnQuartos, btnReservas);
		
		HBox hbox4 = new HBox(8);
		hbox4.getChildren().setAll(check, Quartos, Reservas);
		hbox4.setPadding(new Insets(0, 0, 0, 110));

		HBox hbox2 = new HBox(8);
		hbox2.getChildren().setAll(btnFuncionarios, btnHospedes, btnSobre);
		
		HBox hbox5 = new HBox(8);
		hbox5.getChildren().setAll(funcionarios, hospedes, sobre);
		hbox5.setPadding(new Insets(0, 0, 0, 90));

		panPrincipal.getChildren().addAll(hboxt, hbox1, hbox4, hbox2, hbox5);
		panPrincipal.setPadding(new Insets(0, 0, 0, 73));
		panPrincipal.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #ffffff; ");

		primaryStage.setResizable(false);
		primaryStage.setScene(scn);
		primaryStage.setTitle("HotelManager");
		primaryStage.show();

	}

	public static void main(String[] args) {
		Application.launch(HomeBoundary.class, args);
	}

}
