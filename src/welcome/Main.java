
package welcome;

import game.Music;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;



 public class Main extends Application {



    @FXML
    private GridPane pane;

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/welcome/Welcome.fxml"));
        primaryStage.setScene(new Scene(root, 300, 275));

        primaryStage.setHeight(450);
        primaryStage.setWidth(650);
        primaryStage.setResizable(false);

        welcome.Controller.setStage(primaryStage);
        primaryStage.show();
        Music.initiateMediaPlayer();
    }

    public static void main(String[] args) {
        launch(args);
    }
}