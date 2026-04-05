package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Node;

public class MenuController {

    @FXML
    private void jugar(ActionEvent event) {
        System.out.println("Iniciando juego...");

        // Obtener la ventana actual
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Cambiar a la pantalla del juego
        new GameApp(stage);
    }

    @FXML
    private void salir(ActionEvent event) {
        System.out.println("Saliendo...");
        Platform.exit();
    }
}