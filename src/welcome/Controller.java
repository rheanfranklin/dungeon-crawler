package welcome;

import config.CharacterScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller {

    @FXML private Button newGame;
    private static Stage primaryStage;

    public Button getButton() {
        return newGame;
    }
    public void newGameClicked(MouseEvent mouseEvent) {
        Stage primaryStage = (Stage) newGame.getScene().getWindow();
        try {

            CharacterScreen charScreen = new CharacterScreen();
            charScreen.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }
}
