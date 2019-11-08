import Simulator.components.classroom.Classroom;
import Window.Router;
import Window.Window;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setFullScreen(true);
        Router.connect(new Window(primaryStage)
                .bind("classroom", new Classroom())
                .setView("classroom")
                .setTitle("Classroom Simulator")
                .addBorderRadius(12.0)
                .addCSSFile("styles/mainStyle.css")
                .show()
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
