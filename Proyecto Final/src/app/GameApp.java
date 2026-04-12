package app;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameApp {

    private Pane root;
    private Player player;
    private boolean moverIzquierda = false;
    private boolean moverDerecha = false;
    private boolean disparar = false;

    private List<Bullet> balas = new ArrayList<>();
    private List<Enemy> enemigos = new ArrayList<>();
    private List<Bullet> balasEnemigas = new ArrayList<>();

    private boolean moverDerechaEnemigos = true;
    
    private int puntaje;
    private int vidas;
    private int nivel;

    private Text textoPuntaje;
    private Text textoVidas;
    private Text textoNivel;

    private boolean juegoTerminado;
    private Stage stage;
    private GameLoop loop;

    private boolean juegoIniciado = false;

    // Constructor principal del juego, recibe el stage para configurar la escena

    public GameApp(Stage stage) {
        this.stage = stage;
        this.nivel = 1;
        this.puntaje = 0;
        this.vidas = 3;
        this.juegoTerminado = false;
        
        this.balas.clear();
        this.enemigos.clear();
        this.balasEnemigas.clear();

        root = new Pane();
        root.setStyle("-fx-background-color: #000000;");

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Space Invaders - UCNE");
        stage.setScene(scene);
        stage.setMaximized(true);

        Platform.runLater(() -> {
            crearEstrellas(); 
            crearUI();
            crearEnemigos();
        });

        crearJugador();
        controles(scene);

        loop = new GameLoop(this::update);
        loop.start();
    }

    // Método para crear un fondo de estrellas

    private void crearEstrellas() {
        for (int i = 0; i < 200; i++) {
            double size = Math.random() * 3;
            Rectangle estrella = new Rectangle(size, size, Color.WHITE);
            estrella.setOpacity(Math.random());
            estrella.setLayoutX(Math.random() * root.getWidth());
            estrella.setLayoutY(Math.random() * root.getHeight());
            root.getChildren().add(0, estrella);
        }
    }

    // Método para crear el jugador

    private void crearJugador() {
        player = new Player(400, 550);
        root.getChildren().add(player.getView());
    }

    // Método para crear los enemigos en formación

    private void crearEnemigos() {
        int filas = 4 + this.nivel; 
        int columnas = 10;
        double espacioX = 75; 
        double espacioY = 55;
        double inicioX = (root.getWidth() - (columnas * espacioX)) / 2;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Enemy enemigo = new Enemy(inicioX + j * espacioX, 80 + i * espacioY, i);
                enemigos.add(enemigo);
                root.getChildren().add(enemigo.getView());
            }
        }
        juegoIniciado = true;
    }
    
    // Método para crear la interfaz de usuario (puntaje, nivel, vidas)

    private void crearUI() {
        HBox topBar = new HBox(60);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPrefHeight(60);
        topBar.prefWidthProperty().bind(root.widthProperty());
        topBar.setStyle("-fx-background-color: rgba(0, 255, 255, 0.05); -fx-border-color: #00fbff; -fx-border-width: 0 0 2 0;");

        textoPuntaje = new Text("PUNTAJE: " + puntaje);
        textoPuntaje.setFill(Color.CYAN);
        textoPuntaje.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        textoNivel = new Text("NIVEL: " + nivel);
        textoNivel.setFill(Color.LIGHTGREEN);
        textoNivel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        textoVidas = new Text("VIDAS: " + vidas);
        textoVidas.setFill(Color.ORANGE);
        textoVidas.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        topBar.getChildren().addAll(textoPuntaje, textoNivel, textoVidas);
        root.getChildren().add(topBar);
    }

    // Método para configurar los controles del juego

    private void controles(Scene scene) {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT -> moverIzquierda = true;
                case RIGHT -> moverDerecha = true;
                case SPACE -> disparar = true;
            }
        });
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT -> moverIzquierda = false;
                case RIGHT -> moverDerecha = false;
                case SPACE -> disparar = false;
            }
        });
    }

    // Método principal de actualización del juego, llamado por el GameLoop

    private void update() {
        if (juegoTerminado) return;

        if (moverIzquierda && player.getView().getLayoutX() > 0) player.moverIzquierda();
        if (moverDerecha && player.getView().getLayoutX() < root.getWidth() - 65) player.moverDerecha();
        
        if (disparar) {
            crearBalaJugador();
            disparar = false;
        }

        actualizarLogica();
        chequearColisiones();

        if (vidas <= 0 && !juegoTerminado) {
            juegoTerminado = true;
            mostrarGameOver();
        }
    }

    // Método para actualizar la lógica del juego: movimiento de balas, enemigos y generación de disparos enemigos

    private void actualizarLogica() {
        if (Math.random() < 0.02 * nivel) disparoEnemigo();

        balas.removeIf(b -> {
            b.moverArriba();
            if (b.getView().getLayoutY() < 0) { root.getChildren().remove(b.getView()); return true; }
            return false;
        });

        balasEnemigas.removeIf(b -> {
            b.moverAbajo(4 + nivel);
            if (b.getView().getLayoutY() > root.getHeight()) { root.getChildren().remove(b.getView()); return true; }
            return false;
        });

        moverEjercitoEnemigo();
    }

    // Método para mover el ejército enemigo

    private void moverEjercitoEnemigo() {
        boolean tocarBorde = false;
        for (Enemy e : enemigos) {
            if (e.getView().getLayoutX() <= 0 || e.getView().getLayoutX() >= root.getWidth() - 55) {
                tocarBorde = true; break;
            }
        }

        if (tocarBorde) {
            moverDerechaEnemigos = !moverDerechaEnemigos;
            enemigos.forEach(e -> e.getView().setLayoutY(e.getView().getLayoutY() + 20));
        }

        double velocidad = 2 + nivel;
        enemigos.forEach(e -> e.getView().setLayoutX(e.getView().getLayoutX() + (moverDerechaEnemigos ? velocidad : -velocidad)));
        
        if (enemigos.isEmpty() && juegoIniciado) {
            nivel++;
            if (nivel > 5) { 
                juegoTerminado = true;
                textoPuntaje.setText("¡MISIÓN CUMPLIDA!");
            } else {
                textoNivel.setText("NIVEL: " + nivel);
                crearEnemigos();
            }
        }
    }

    // Método para chequear colisiones entre balas, enemigos y el jugador

    private void chequearColisiones() {
        for (int i = 0; i < balasEnemigas.size(); i++) {
            Bullet b = balasEnemigas.get(i);
            if (b.getView().getBoundsInParent().intersects(player.getView().getBoundsInParent())) {
                vidas--;
                textoVidas.setText("VIDAS: " + vidas);
                root.getChildren().remove(b.getView());
                balasEnemigas.remove(i--);
            }
        }

        for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            for (int j = 0; j < enemigos.size(); j++) {
                Enemy e = enemigos.get(j);
                if (b.getView().getBoundsInParent().intersects(e.getView().getBoundsInParent())) {
                    root.getChildren().remove(e.getView());
                    enemigos.remove(j);
                    root.getChildren().remove(b.getView());
                    balas.remove(i--);
                    puntaje += 10;
                    textoPuntaje.setText("PUNTAJE: " + puntaje);
                    break;
                }
            }
        }
    }

    // Método para mostrar el panel de Game Over

    private void mostrarGameOver() {
        VBox panelGameOver = new VBox(30);
        panelGameOver.setAlignment(Pos.CENTER);
        panelGameOver.setPrefSize(500, 400);
        panelGameOver.setLayoutX((root.getWidth() - 500) / 2);
        panelGameOver.setLayoutY((root.getHeight() - 400) / 2);
        
        // Estilo del contenedor principal
        panelGameOver.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9); " +
                               "-fx-border-color: #ff004c; " +
                               "-fx-border-width: 4; " +
                               "-fx-background-radius: 30; " +
                               "-fx-border-radius: 30; " +
                               "-fx-effect: dropshadow(three-pass-box, rgba(255, 0, 76, 0.5), 20, 0, 0, 0);");

        Text textoTitulo = new Text("MISIÓN FALLIDA");
        textoTitulo.setFill(Color.web("#ff004c"));
        textoTitulo.setStyle("-fx-font-size: 45px; -fx-font-weight: bold; -fx-font-family: 'Arial Black';");

        // Botón para reiniciar el juego
        Button btnReiniciar = new Button("REINTENTAR");
        String estiloVerde = "-fx-background-color: #00ff88; -fx-text-fill: black; -fx-font-size: 20px; " +
                             "-fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 12 50; -fx-cursor: hand;";
        btnReiniciar.setStyle(estiloVerde);
        
        // Efecto hover para reintentar
        btnReiniciar.setOnMouseEntered(e -> btnReiniciar.setStyle(estiloVerde + "-fx-background-color: #00cc6e; -fx-scale-x: 1.1; -fx-scale-y: 1.1;"));
        btnReiniciar.setOnMouseExited(e -> btnReiniciar.setStyle(estiloVerde));
        
        btnReiniciar.setOnAction(e -> {
            loop.stop();
            new GameApp(stage);
        });

        // Botón para volver al menú principal
        Button btnMenu = new Button("VOLVER AL MENÚ");
        String estiloRojo = "-fx-background-color: transparent; -fx-text-fill: #ff004c; -fx-font-size: 18px; " +
                            "-fx-border-color: #ff004c; -fx-border-width: 2; -fx-border-radius: 15; -fx-padding: 10 40; -fx-cursor: hand;";
        btnMenu.setStyle(estiloRojo);
        
        // Efecto hover para menú
        btnMenu.setOnMouseEntered(e -> btnMenu.setStyle(estiloRojo + "-fx-background-color: rgba(255, 0, 76, 0.1); -fx-scale-x: 1.05; -fx-scale-y: 1.05;"));
        btnMenu.setOnMouseExited(e -> btnMenu.setStyle(estiloRojo));
        
        btnMenu.setOnAction(e -> volverAlMenu());

        panelGameOver.getChildren().addAll(textoTitulo, btnReiniciar, btnMenu);
        root.getChildren().add(panelGameOver);
    }

    // Método para volver al menú principal

    private void volverAlMenu() {
        try {
            if (loop != null) loop.stop();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/menu.fxml"));
            Parent rootMenu = loader.load();
            stage.setScene(new Scene(rootMenu));
            stage.setMaximized(false);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para crear una bala disparada por el jugador

    private void crearBalaJugador() {
        Bullet bala = new Bullet(player.getView().getLayoutX() + 28, player.getView().getLayoutY(), Color.YELLOW);
        balas.add(bala);
        root.getChildren().add(bala.getView());
    }

    // Método para que los enemigos disparen

    private void disparoEnemigo() {
        if (enemigos.isEmpty()) return;
        Enemy e = enemigos.get((int) (Math.random() * enemigos.size()));
        Bullet bala = new Bullet(e.getView().getLayoutX() + 23, e.getView().getLayoutY() + 35, Color.WHITE);
        balasEnemigas.add(bala);
        root.getChildren().add(bala.getView());
    }
}