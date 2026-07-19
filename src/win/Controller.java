package win;

import config.CharacterScreen;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import welcome.Main;


public class Controller {

    @FXML
    private Text timeLabel;
    @FXML
    private Text moneyLabel;
    @FXML
    private Text mainText;
    @FXML
    private Text subText;
    @FXML
    private Text expLabel;

    private boolean built;


    public void buildController() {
        if (!built) {
            if (Winscreen.getIsDeathScreen()) {
                mainText.setText("You Lost!");
                mainText.setTextAlignment(TextAlignment.CENTER);
                subText.setText("Better luck next time!");
            }

            long elapsedTime = System.currentTimeMillis() - Winscreen.getStartTime();
            long elapsedTimeSeconds = elapsedTime / 1000;

            long elapsedTimeMinutes = elapsedTimeSeconds / 60;
            long remainder = elapsedTimeSeconds % 60;
            timeLabel.setText(elapsedTimeMinutes + ":" + String.format("%02d", remainder));

            expLabel.setText(Winscreen.getExperience() + " points");

            moneyLabel.setText("$" + Winscreen.getMoney());
        }
        built = true;
    }

    public void returnButtonClicked() throws Exception {
        Stage primaryStage = Winscreen.getMainStage();
        Main mainScreen = new Main();

        // reset all variables
        CharacterScreen.setMainStage(null);

        mainScreen.start(primaryStage);
    }

    public void exitButtonClicked() {
        System.exit(0);
    }

    public Text getTimeLabel() {
        return timeLabel;
    }
    public Text getMoneyLabel() {
        return moneyLabel;
    }
    public Text getMainText() {
        return mainText;
    }
    public Text getSubText() {
        return subText;
    }
    public Text getExpLabel() {
        return expLabel;
    }
}