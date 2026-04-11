package app;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player {

    private Rectangle view;

    public Player(double x, double y) {
        view = new Rectangle(40, 20, Color.GREEN);
        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    public Rectangle getView() {
        return view;
    }

    public void moverIzquierda() {
        view.setLayoutX(view.getLayoutX() - 5);
    }

    public void moverDerecha() {
        view.setLayoutX(view.getLayoutX() + 5);
    }
}