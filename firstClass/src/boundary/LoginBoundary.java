package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import control.LoginControl;
import entity.Funcionario;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.Config;

public class LoginBoundary extends Application {
	private Button btnLogin;
	private Button btnSair;
	private Button btnImg;
	private TextField txtLogin;
	private PasswordField txtSenha;
	private LoginControl control = new LoginControl();

	public LoginBoundary() {
		btnLogin = new Button("Login", Config.btnIcon("empregado.png"));
		btnLogin.setStyle("-fx-background-color: #4169E1; -fx-text-fill: #ffffff; ");
		btnLogin.setMinWidth(250);
		btnLogin.setMinHeight(30);

		btnSair = new Button("Sair", Config.btnIcon("checkout.png"));
		btnSair.setStyle("-fx-background-color: #A52A2A; -fx-text-fill: #ffffff; ");
		btnSair.setMinWidth(250);
		btnSair.setMinHeight(30);

		btnImg = new Button("Sair", Config.homeIcon("Hospedes.png"));
		btnImg.setStyle("-fx-background-color: #4169E1; -fx-text-fill: #ffffff; ");
		btnImg.setMinWidth(250);
		btnImg.setMinHeight(250);

		txtLogin = new TextField();
		txtSenha = new PasswordField();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		HBox panPrincipal = new HBox(8);
		Scene scn = new Scene(panPrincipal, 600, 300);

		Label login = new Label("Login");
		login.setTextFill(Color.web("#0076a3"));
		login.setFont(Font.font("Arial", 28));

		VBox cboxt = new VBox(8);
		cboxt.getChildren().setAll(btnLogin, btnSair);
		cboxt.setPadding(new Insets(10, 0, 0, 0));

		VBox hboxt = new VBox(8);
		hboxt.getChildren().setAll(login, new Label("Usuario"), txtLogin, new Label("Senha"), txtSenha, cboxt);
		hboxt.setPadding(new Insets(10, 10, 10, 10));

		try {
			FileInputStream input;
			input = new FileInputStream("firstClass/resources/images/hotel.png");
			Image image = new Image(input, 280, 280, false, false);
			ImageView imageView = new ImageView(image);

			panPrincipal.getChildren().addAll(hboxt, imageView);
			panPrincipal.setPadding(new Insets(15));
			panPrincipal.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #ffffff; ");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		btnLogin.setOnAction((a) -> {
			
			Funcionario func = control.validaUsuario(txtLogin.getText(), txtSenha.getText());
			
			if (!func.getUsuario().equals("")) {
				HomeBoundary t = new HomeBoundary(func);
				try {
					t.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Hotel Mananger");
				alert.setContentText("Usuario n�o encontrado! Tente novamente");
				alert.showAndWait();
				txtLogin.setText("");
				txtSenha.setText("");
			}
		});

		btnSair.setOnAction((a) -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Hotel Mananger");
			alert.setContentText("Sistema encerrado");
			alert.showAndWait();
			txtLogin.setText("");
			txtSenha.setText("");
			System.exit(0);
		});

		primaryStage.setResizable(false);
		primaryStage.setScene(scn);
		primaryStage.setTitle("HotelManager");
		primaryStage.show();

	}

	public static void main(String[] args) {
		Application.launch(LoginBoundary.class, args);
	}
}
