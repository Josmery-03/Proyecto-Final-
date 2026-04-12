package app;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet {
    private Rectangle view;

    // Costructor
    public Bullet(double x, double y, Color color) {
       
        view = new Rectangle(4, 12, color);
        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    // Metodos para obtener la vista de la bala y moverla
    public Rectangle getView() {
        return view;
    }

    public void moverArriba() {
        view.setLayoutY(view.getLayoutY() - 7);
    }

    public void moverAbajo(double velocidad) {
        view.setLayoutY(view.getLayoutY() + velocidad);
    }
}