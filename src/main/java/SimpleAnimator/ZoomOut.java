package SimpleAnimator;

import Window.Router;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Animation that zooms a node out to point zero
 * Author: Øyvind Johannessen
 * Version: 0.1
 */
public class ZoomOut implements Animatable {
    ScaleTransition st = new ScaleTransition();

    public ZoomOut(double seconds) {
        st.setDuration(Duration.millis(Duration.seconds(seconds).toMillis()));
    }

    @Override
    public Node fire(Node node) {
        st.setNode(node);
        st.setFromZ(1.0);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToZ(0.0);
        st.setToX(0.0);
        st.setToY(0.0);
        st.setCycleCount(1);
        st.setAutoReverse(false);
        st.setInterpolator(Interpolator.SPLINE(0.0, 0.0, 0.6, 0.0));
        st.setOnFinished(event -> {
            node.setScaleY(1.0);
            node.setScaleX(1.0);
            node.setScaleZ(1.0);
        });

        st.play();

        return st.getNode();
    }

    @Override
    public void fire(Node node, String key, Animatable animatable) {
        st.setNode(node);
        st.setFromZ(1.0);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToZ(0.0);
        st.setToX(0.0);
        st.setToY(0.0);
        st.setCycleCount(1);
        st.setAutoReverse(false);
        st.setInterpolator(Interpolator.SPLINE(0.0, 0.0, 0.6, 0.0));
        st.setOnFinished(event -> {
            Router.switchTo(key, animatable);
            node.setScaleY(1.0);
            node.setScaleX(1.0);
            node.setScaleZ(1.0);

        });

        st.play();
    }
}
