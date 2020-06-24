import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Home extends Application {
    private TextField txtNome = new TextField();
    private TextField txtIdade = new TextField();

    private Button btnMostrar = new Button("Mostrar");

    public static void main(String[] args) {
        System.out.println("why r you running?");
        Application.launch(Home.class, args);
    }

    @Override
    public void start(Stage stage) {
        BorderPane panelPrincipal = new BorderPane();
        Scene scn = new Scene(panelPrincipal, 400, 600);

        GridPane panelCampos = new GridPane();

        panelCampos.add(new Label("Nome: "), 0 , 0);
        panelCampos.add(txtNome, 1, 0);
        panelCampos.add(new Label("Idade: "), 0 ,1);
        panelCampos.add(txtIdade, 1, 1);
        panelCampos.add(btnMostrar, 0, 3);

    }
}
