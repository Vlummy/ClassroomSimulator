package SimpleAnimator;

import Window.Router;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Animation that fades the node out
 * Author: Øyvind Johannessen
 * Version: 0.1
 */
public class FadeOut implements Animatable {
        private FadeTransition ft = new FadeTransition();

        public FadeOut(double seconds) {
            ft.setDuration(Duration.millis(Duration.seconds(seconds).toMillis()));
        }

        @Override
        public Node fire(Node node) {
            ft.setNode(node);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setCycleCount(1);
            ft.setInterpolator(Interpolator.SPLINE(0.0, 0.2, 0.0, 1.0));
            ft.setAutoReverse(false);
            ft.setOnFinished(event -> node.setOpacity(1.0));
            ft.play();

            return ft.getNode();
        }

    @Override
    public void fire(Node node, String key, Animatable animatable) {
        ft.setNode(node);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.setInterpolator(Interpolator.SPLINE(0.0, 0.2, 0.0, 1.0));
        ft.setOnFinished(event -> {
            Router.switchTo(key, animatable);
            node.setOpacity(1.0);
        });

        ft.play();
    }
}
