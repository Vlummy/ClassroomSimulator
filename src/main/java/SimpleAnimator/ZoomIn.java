package SimpleAnimator;

import Window.Router;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Animation that zooms a node in from point zero
 * Author: Øyvind Johannessen
 * Version: 0.1
 */
public class ZoomIn implements Animatable {
    ScaleTransition st = new ScaleTransition();

    public ZoomIn(double seconds) {
        st.setDuration(Duration.millis(Duration.seconds(seconds).toMillis()));
    }

    @Override
    public Node fire(Node node) {
        node.setScaleZ(0.0);
        node.setScaleX(0.0);
        node.setScaleY(0.0);
        st.setNode(node);
        st.setFromZ(0.0);
        st.setFromX(0.0);
        st.setFromY(0.0);
        st.setToZ(1.0);
        st.setToX(1.0);
        st.setToY(1.0);
        st.setCycleCount(1);
        st.setAutoReverse(false);
        st.setInterpolator(Interpolator.SPLINE(0.0, 0.0, 0.0, 1.0));
        st.setOnFinished(event -> {
            node.setScaleZ(1.0);
            node.setScaleX(1.0);
            node.setScaleY(1.0);
        });
        st.play();

        return st.getNode();
    }

    @Override
    public void fire(Node node, String key, Animatable animatable) {
        node.setScaleZ(0.0);
        node.setScaleX(0.0);
        node.setScaleY(0.0);
        st.setNode(node);
        st.setFromZ(0.0);
        st.setFromX(0.0);
        st.setFromY(0.0);
        st.setToZ(1.0);
        st.setToX(1.0);
        st.setToY(1.0);
        st.setCycleCount(1);
        st.setAutoReverse(false);
        st.setInterpolator(Interpolator.SPLINE(0.0, 0.0, 0.0, 1.0));
        st.setOnFinished(event -> {
            Router.switchTo(key, animatable);
            node.setScaleZ(1.0);
            node.setScaleX(1.0);
            node.setScaleY(1.0);
        });

        st.play();
    }
}
