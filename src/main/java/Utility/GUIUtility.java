package Utility;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;

/**
 * Contains different common Utility methods that can be applied on the Graphical User Interface
 * Author: Øyvind Johannessen
 * Version: 0.1
 */
public class GUIUtility {
    /**
     * Used for adjusting the border radius on a node
     */
    public static void applyBorderRadius(Node node, Double value) {
        Rectangle rectangle = new Rectangle();
        rectangle.setArcHeight(value);
        rectangle.setArcWidth(value);
        node.layoutBoundsProperty().addListener(((observable, oldValue, newValue) -> {
            rectangle.setWidth(newValue.getWidth());
            rectangle.setHeight(newValue.getHeight());
        }));
        node.setClip(rectangle);
    }
}
