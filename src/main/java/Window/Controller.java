package Window;
import SceneSwitcher.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller {
    private SceneSwitcher ss = SceneSwitcher.getInstance();

    @FXML private Label title;
    @FXML private Label minimize;
    @FXML private Label expand;
    @FXML private Label close;

    public void initialize() {

        this.close.setOnMouseClicked(event -> {
            OpenCloseAction.close();
        });

        this.expand.setOnMouseClicked(event -> {
            Stage stage = ((Stage)((Node) event.getSource()).getScene().getWindow());
            if(stage.isFullScreen())
                stage.setFullScreen(false);
            else
                stage.setFullScreen(true);
        });

        this.minimize.setOnMouseClicked(event -> ((Stage)((Node) event.getSource()).getScene().getWindow()).setIconified(true));
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
