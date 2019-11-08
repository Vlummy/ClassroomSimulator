package SimpleAnimator;

import javafx.scene.Node;

/**
 * This interface is used by animation classes to share common method signatures.
 * Router class can easily fire of any animations by these methods.
 *
 * Usually one implement the interface to a Animation class, instantiates a new
 * Animation like ScaleTransition or TranslateTransition as a field.
 * the constructor sets the duration of the Animation.
 * The parameters like setFromX and setToX is set in the fire() methods.
 * Author: Øyvind Joahnnessen
 * Version: 0.1
 */
public interface Animatable {
    /**
     * Method that fires of the animation setting that is specified.
     *
     * @param node The node to be animated
     * @return node return the input Node
     */
    Node fire(Node node);

    /**
     * This method is used when to animations are to be played on two different nodes, like when switching between scenes.
     * One scene is animated away, and another is animated in to the view.
     * @param node The node to be animated
     * @param key The key to the second Node that is to be animated. The key is used by Router.switchTo(key, animatable).
     *            This is set in the .setOnFinished() method by en animation.
     * @param animatable The second animation that is passed to this method. Just pass it along in Router.switchTo(key, animatable)
     *                   Then the correct fire() method will be called depending on with animation that was instantiated.
     */
    void fire(Node node, String key, Animatable animatable);
}
