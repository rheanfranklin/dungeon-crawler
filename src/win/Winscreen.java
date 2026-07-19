
package win;

import game.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class Winscreen extends Application {
    private static Stage mainStage;
    private static long startTime;
    private static boolean isDeathScreen;
    private static int money;
    private static int experience;

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/win/winscreen.fxml"));
        primaryStage.setTitle("Congratulations!");
        //if (GameScreen.getPlayer().getHealth() <= 0) {
        //    primaryStage.setTitle("Too bad!");
        //}

        primaryStage.getScene().setRoot(root);
        primaryStage.setHeight(450);
        primaryStage.setWidth(650);
        primaryStage.setResizable(false);
        primaryStage.show();
        mainStage = primaryStage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setStartTime(long time) {
        startTime = time;
    }

    public static long getStartTime() {
        return startTime;
    }

    public static void main(String[] args) {
        launch(args); }

    public Winscreen(Player player) {
        Winscreen.money = player.getMoney();
        Winscreen.experience = player.getExperience();
        if (player.getHealth() <= 0) {
            setIsDeathScreen(true);
        } else {
            setIsDeathScreen(false);
        }
    }

    public static boolean getIsDeathScreen() {
        return isDeathScreen;
    }

    public static int getMoney() {
        return money;
    }

    public static void setIsDeathScreen(Boolean bool) {
        isDeathScreen = bool;
    }

    public static void setExperience(int exp) {
        experience = exp;
    }

    public static int getExperience() {
        return experience;
    }
}