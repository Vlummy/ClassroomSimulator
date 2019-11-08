package Window;

import SceneSwitcher.SceneSwitcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Utility.GUIUtility;

import java.io.IOException;
import java.util.Objects;

/**
 * Window class represents a more customizable and fresh window for javafx application. It has the same features as
 * the Stage. This class uses a window.fxml file that mimics the original Stage of javafx, but uses a BorderPane node
 * and a Scene with an transparent Stage.
 * Author: Øyvind Johannessen
 * Version: 0.2
 * Thanks to Alexander Berg for providing a window resize helper class (ResizeHelper)
 */
public class Window {
    private BorderPane window;
    private SceneSwitcher ss = SceneSwitcher.getInstance();

    /**
     * Constructor for this Custom window class.
     * There are some identifications that are important to know about.
     * This window is automatically binned to the key "window". So you can get the window from with this key.
     * The close, minimize, expand and title labels of the Window class has id's "close", "minimize", "expand" and "title".
     *
     * @param stage
     */
    public Window(Stage stage) {
        OpenCloseAction.setStage(stage);
        try {
            window = FXMLLoader.load(Objects.requireNonNull(this.getClass().getClassLoader().getResource("fxml/window.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(window));
        stage.initStyle(StageStyle.TRANSPARENT);

        window.getStylesheets().add(this.getClass().getClassLoader().getResource("styles/windowStyle.css").toString());

        ResizeHelper.addResizeListener(stage);

        this.bind("window", this.window);

        this.getWindow().setId("window");
    }

    public Window bindDirectory(String directoryName, String[] keys) {
        ss.bindDirectory(directoryName, keys);
        return this;
    }

    public Window bindListOfViews(Parent[] views, String[] keys) {
        ss.bindListOfViews(views, keys);
        return this;
    }

    public Window bind(String key, String fxmlDocName) {
        ss.bind(key, fxmlDocName);
        return this;
    }

    public Window bind(String key, Parent view) {
        ss.bind(key, view);
        return this;
    }

    /**
     * Get the view of this window
     * @return
     */
    public BorderPane getWindow() {
        return this.window;
    }

    /**
     * Set the view of this window
     * @param view The layout view that is to be shown
     */
    public Window setView(Parent view) {
        window.setCenter(view);
        return this;
    }

    /**
     * Set the view of this window
     * @param key The layout view that is to be shown
     */
    public Window setView(String key) {
        window.setCenter(ss.getView(key));
        return this;
    }

    /**
     * Set the title of the window
     * @param title
     * @return
     */
    public Window setTitle(String title) {
        Label titleLabel = (Label) window.getScene().lookup("#title");
        titleLabel.setText(title);
        return this;
    }

    /**
     * Get the title label
     * @return
     */
    public Label getTitle() {
        return (Label) window.getScene().lookup("#title");
    }

    /**
     * Get the close label
     * @return
     */
    public Label getCloseLabel() {
        return (Label) window.getScene().lookup("#close");
    }

    /**
     * Get the minimize label
     * @return
     */
    public Label getMinimizeLabel() {
        return (Label) window.getScene().lookup("#minimize");
    }

    /**
     * Get the expand label
     * @return
     */
    public Label getExpandLabel() {
        return (Label) window.getScene().lookup("#expand");
    }

    public Window setHeaderStyle(String style) {
        window.getTop().setStyle(style);
        return this;
    }

    public Window setWindowStyle(String style) {
        window.setStyle(style);
        return this;
    }

    /**
     * Add a custom .css file for the application
     * @param path
     * @return Window this window
     */
    public Window addCSSFile(String path) {
        this.getWindow().getScene().getStylesheets().add(getClass().getClassLoader().getResource(path).toExternalForm());
        return this;
    }

    /**
     * This method can be used to make the corners of the window rounder
     * The method used for this is also available to be used on child nodes. It can be used from the
     * GUIUtility class located in Utility package.
     * @return Window this window
     */
    public Window addBorderRadius(Double value) {
        GUIUtility.applyBorderRadius(this.window, value);
        this.getWindow().getScene().setFill(Color.TRANSPARENT);
        return this;
    }

    /**
     * Display the window
     * @return
     */
    public Window show() {
        OpenCloseAction.open();
        return this;
    }
}
