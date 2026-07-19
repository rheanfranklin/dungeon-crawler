package tests;

import welcome.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.assertEquals;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class WelcomeTests extends ApplicationTest {
    private Button newGame;
    private Stage mainStage;

    @Before
    public void setup() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Main.class);
    }

    @After
    public void cleanup() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void testNewGameClicked() {

        FxRobot bot = new FxRobot();

        bot.moveTo(newGame.getLayoutX() + mainStage.getX() + 2, newGame.getLayoutY()
                + mainStage.getY() + 2);
        bot.press(MouseButton.PRIMARY);
        bot.release(MouseButton.PRIMARY);

        assertEquals("Character Creation", mainStage.getTitle());


    }

    @Test
    public void testButtonLabel() {
        FxAssert.verifyThat(newGame, hasText("New game"));
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(welcome.Main.class.getResource("/welcome/Welcome.fxml"));
        Scene scene = new Scene(mainNode);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        mainStage = stage;
        newGame = (Button) scene.lookup("#newGame");
    }
}