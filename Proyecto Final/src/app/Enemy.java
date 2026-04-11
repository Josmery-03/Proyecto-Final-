package app;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy {

    private Rectangle view;

    public Enemy(double x, double y) {
        view = new Rectangle(30, 20, Color.RED);
        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    public Rectangle getView() {
        return view;
    }
}
