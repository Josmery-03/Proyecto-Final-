package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy {
    private ImageView view;

    public Enemy(double x, double y, int fila) {
        // Elegir la imagen del enemigo según la fila (alternando entre dos tipos)
        String ruta = (fila % 2 == 0) ? "/app/enemigo_cangrejo.png" : "/app/enemigo_calamar.png";
        
        Image img = new Image(getClass().getResourceAsStream(ruta));
        view = new ImageView(img);
        
        // Tamaño
        view.setFitWidth(50);
        view.setFitHeight(35);
        view.setSmooth(false);
        
        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    public ImageView getView() { return view; }
}