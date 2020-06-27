import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

public class Home extends Application {
    private TextField txtNome = new TextField();
    private TextField txtIdade = new TextField();


    private Button btnMostrar = new Button("Mostrar");

    public static void main(String[] args) {
        System.out.println("why r you running?");
        Application.launch(Home.class, args);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));

        // botão com reservas
        Image imageTest = new Image(Config.image("book.png"),50,50,true,false);
        Button btnReservas = new Button("Reserva", new ImageView(imageTest));
        gridPane.add(btnReservas, 0, 0);

        Scene scene = new Scene(gridPane, 700, 300);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }
}
