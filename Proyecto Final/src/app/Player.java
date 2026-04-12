package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    private ImageView view;

    public Player(double x, double y) {
        Image img = new Image(getClass().getResourceAsStream("/app/nave_jugador.png"));
        view = new ImageView(img);
        
        // Tamaño
        view.setFitWidth(60); 
        view.setFitHeight(45);
        view.setSmooth(false); 
        
        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    public ImageView getView() { return view; }

    public void moverIzquierda() { view.setLayoutX(view.getLayoutX() - 6); }
    public void moverDerecha() { view.setLayoutX(view.getLayoutX() + 6); }
}