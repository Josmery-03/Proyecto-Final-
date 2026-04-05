package app;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class GameApp {

    private Pane root;

    public GameApp(Stage stage) {
        root = new Pane();
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Space Invaders - Juego");
        stage.setScene(scene);
        stage.show();

        iniciar();
    }

    private void iniciar() {
    Label texto = new Label("Aquí va el juego");
    texto.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
    texto.setLayoutX(200);
    texto.setLayoutY(180);

    root.getChildren().add(texto);
    }
}