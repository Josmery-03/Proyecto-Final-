package app;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameApp {

    private Pane root;
    private Rectangle player;

    private boolean moverIzquierda = false;
    private boolean moverDerecha = false;

    public GameApp(Stage stage) {

        root = new Pane();
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Space Invaders - Juego");
        stage.setScene(scene);
        stage.show();

        crearJugador();
        controles(scene);
        gameLoop();
    }

    private void crearJugador() {
        player = new Rectangle(40, 20, Color.GREEN);

        player.setLayoutX(280);
        player.setLayoutY(350);

        root.getChildren().add(player);
    }

    private void controles(Scene scene) {

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT -> moverIzquierda = true;
                case RIGHT -> moverDerecha = true;
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT -> moverIzquierda = false;
                case RIGHT -> moverDerecha = false;
            }
        });
    }

    private void gameLoop() {

        AnimationTimer timer = new AnimationTimer() {
            @Override
        public void handle(long now) {

    if (moverIzquierda && player.getLayoutX() > 0) {
        player.setLayoutX(player.getLayoutX() - 5);
    }

    if (moverDerecha && player.getLayoutX() < 560) {
        player.setLayoutX(player.getLayoutX() + 5);
    }
}
        };

        timer.start();
    }
}

