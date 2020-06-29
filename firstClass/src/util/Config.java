package util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Config {
    public static ImageView homeIcon(String iconName) {
        Image img = new Image("file:firstClass/resources/images/" + iconName, 150, 150, false, false);
        return new ImageView(img);
    }
    public static ImageView btnIcon(String iconName) {
        Image img = new Image("file:firstClass/resources/images/" + iconName, 20, 20, false, false);
        return new ImageView(img);
    }
}
