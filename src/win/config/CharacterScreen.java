package config;

import game.Music;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class CharacterScreen extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //FXMLLoader loader = new FXMLLoader();
        Parent root = FXMLLoader.load(getClass().getResource("/config/Character-Screen.fxml"));
        primaryStage.setTitle("Character Creation");
        primaryStage.getScene().setRoot(root);
        primaryStage.show();
        mainStage = primaryStage;


    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
