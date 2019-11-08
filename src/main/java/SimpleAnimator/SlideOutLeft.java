package SimpleAnimator;

import Window.OpenCloseAction;
import Window.Router;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Animation that slides the node out to the left
 * Author: Øyvind Johannessen
 * Version: 0.1
 */
public class SlideOutLeft implements Animatable {
    private TranslateTransition tt = new TranslateTransition();

    public SlideOutLeft(double seconds) {
        tt.setDuration(Duration.millis(Duration.seconds(seconds).toMillis()));
    }

    @Override
    public Node fire(Node node) {
        Stage stage = OpenCloseAction.getStage();
        tt.setNode(node);
        tt.setFromX(0.0);
        tt.setToX(-stage.getWidth());
        tt.setCycleCount(1);
        tt.setAutoReverse(false);
        tt.setInterpolator(Interpolator.SPLINE(0.0,0.0,1.0,0.0));
        tt.setOnFinished(event -> node.setTranslateX(0.0));

        tt.play();

        return tt.getNode();
    }

    @Override
    public void fire(Node node, String key, Animatable animatable) {
        Stage stage = OpenCloseAction.getStage();
        tt.setNode(node);
        tt.setFromX(0.0);
        tt.setToX(-stage.getWidth());
        tt.setCycleCount(1);
        tt.setAutoReverse(false);
        tt.setInterpolator(Interpolator.SPLINE(0.0,0.0,1.0,0.0));

        tt.setOnFinished(event -> {
            Router.switchTo(key, animatable);
            node.setTranslateX(0.0);
        });

        tt.play();
    }
}
