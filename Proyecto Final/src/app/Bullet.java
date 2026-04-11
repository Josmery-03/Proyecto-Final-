package app;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet {

    private Rectangle view;

    public Bullet(double x, double y, Color color) {
        view = new Rectangle(5, 10, color);
        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    public Rectangle getView() {
        return view;
    }

    public void moverArriba() {
        view.setLayoutY(view.getLayoutY() - 5);
    }

    public void moverAbajo(double velocidad) {
        view.setLayoutY(view.getLayoutY() + velocidad);
    }
}