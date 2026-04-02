package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;

public class MenuController {

    @FXML
    private void jugar(ActionEvent event) {
        System.out.println("Iniciando juego...");
        
    }

    @FXML
    private void salir(ActionEvent event) {
        System.out.println("Saliendo...");
        Platform.exit(); // Cierra la aplicación
    }
}