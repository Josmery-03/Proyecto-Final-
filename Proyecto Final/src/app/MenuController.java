package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Node;

public class MenuController {

    @FXML
    private void jugar(ActionEvent event) {
        // Obtenemos el stage actual a partir del evento
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        System.out.println("Cargando motores... ¡Despegue!");

        // Iniciar la aplicacion del juego pasando el stage
        new GameApp(stage);
    }

    @FXML
    private void salir(ActionEvent event) {
        System.out.println("Cerrando sistemas de navegación...");
        Platform.exit();
        System.exit(0);
    }
}