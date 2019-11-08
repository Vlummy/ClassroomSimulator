package Window;

import SceneSwitcher.SceneSwitcher;
import SimpleAnimator.Animatable;
import SimpleAnimator.FadeIn;
import SimpleAnimator.FadeOut;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class Router {
    private static Router router = new Router();
    private SceneSwitcher sceneSwitcher = SceneSwitcher.getInstance();
    private BorderPane window;

    /**
     * Private empty Constructor
     */
    private Router() {
    }

    /**
     * Switch to view binned to input key
     * @param key
     */
    public static void switchTo(String key) {
        getInstance().setView(key);
    }

    /**
     * Switch method that lets you apply animations transition from old view and to new view.
     * @param currentKey The key for the view that is currently open
     * @param requestedKey The key for the requested view
     * @param current animation to be applied on current view
     * @param requested animation to be applied to the requesten view
     */
    public static void switchTo(String currentKey, String requestedKey, Animatable current, Animatable requested) {
        getInstance().setView(currentKey, requestedKey, current, requested);
    }

    /**
     * Switch method that lets you apply animations transition from old view and to new view.
     * @param currentKey The key for the view that is currently open
     * @param requestedKey The key for the requested view
     * @param current animation to be applied on current view
     * @param requested animation to be applied to the requesten view
     */
    private void setView(String currentKey, String requestedKey, Animatable current, Animatable requested) {
        current.fire(
                sceneSwitcher.getView(currentKey),
                requestedKey,
                requested
        );
    }

    /**
     * Switch to view binned to input key and apply animation
     * @param key
     * @param animatable Any animation class that is animatable
     */
    public static void switchTo(String key, Animatable animatable) {
        getInstance().setView(key, animatable);
    }

    /**
     * Switch to view binned to input key and apply animation
     * @param key
     * @param animatable Any animation class that is animatable
     */
    private void setView(String key, Animatable animatable) {
        Node node = sceneSwitcher.getView(key);
        this.window.setCenter(animatable.fire(node));
    }

    /**
     * Private method for setting view of window
     * @param key
     */
    private void setView(String key) {
        this.window.setCenter(this.sceneSwitcher.getView(key));
    }

    /**
     * Returns the router instance
     * @return Router instance
     */
    private static Router getInstance() {
        return router;
    }

    /**
     * Connects a root window to router
     * @param window
     * @return
     */
    public static void connect(Window window) {
        getInstance().set(window);
    }

    /**
     * Set window stage
     * @param window
     * @return Router instance
     */
    private void set(Window window) {
        this.window = window.getWindow();
    }
}
