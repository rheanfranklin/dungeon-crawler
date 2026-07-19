package tests;

import config.CharacterScreen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;

import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import javafx.scene.text.Text;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.testfx.matcher.control.LabeledMatchers.hasText;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConfigTests extends ApplicationTest {

    private Stage mainStage;
    private Button back;
    private Button next;
    private RadioButton diffEasy;
    private RadioButton diffNormal;
    private RadioButton diffInsane;
    private Text text;
    private ComboBox box;


    @Before
    public void setup() throws Exception {
        FxToolkit.registerPrimaryStage();

        FxToolkit.setupApplication(welcome.Main.class);
        FxToolkit.setupApplication(CharacterScreen.class);
    }

    @After
    public void cleanup() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }


    @Test
    public void testBackClicked() {
        FxRobot bot = new FxRobot();

        bot.moveTo(390, 334);
        bot.press(MouseButton.PRIMARY);
        bot.release(MouseButton.PRIMARY);


        bot.moveTo(350, 520);
        bot.press(MouseButton.PRIMARY);
        bot.release(MouseButton.PRIMARY);

        assertEquals("Untitled Dungeon Crawler", mainStage.getTitle());
    }

    @Test
    public void testRadio() {


        long time = System.currentTimeMillis();
        boolean easy;
        boolean normal;
        boolean insane;
        boolean[] otherDiffs = new boolean[2];
        boolean[] expected = new boolean[2];
        if (diffEasy.isSelected()) {
            normal = diffNormal.isSelected();
            insane = diffInsane.isSelected();
            otherDiffs[0] = normal;
            otherDiffs[1] = insane;
            expected[0] = false;
            expected[1] = false;
            assertArrayEquals(expected, otherDiffs);
            //diffNormal.setSelected(true);
        }
        if (diffNormal.isSelected()) {
            easy = diffEasy.isSelected();
            insane = diffInsane.isSelected();
            otherDiffs[0] = easy;
            otherDiffs[1] = insane;
            expected[0] = false;
            expected[1] = false;
            assertArrayEquals(expected, otherDiffs);
        }
        if (diffInsane.isSelected()) {
            easy = diffEasy.isSelected();
            normal = diffNormal.isSelected();
            otherDiffs[0] = easy;
            otherDiffs[1] = normal;
            expected[0] = false;
            expected[1] = false;
            assertArrayEquals(expected, otherDiffs);
        }
    }

    @Test
    public void testBackLabel() {
        FxAssert.verifyThat(back, hasText("Back"));
    }

    @Test
    public void testNextButtonLabel() {
        FxAssert.verifyThat(next, hasText("Next"));
    }

    @Test
    public void testText() {
        assertEquals("Create Your Traveller", text.getText());
    }

    @Test
    public void testNextBtn() {

        FxRobot bot = new FxRobot();

        bot.moveTo(next.getLayoutX() + mainStage.getX() + 2,
                next.getLayoutY() + mainStage.getY() + 2);
        bot.press(MouseButton.PRIMARY);
        bot.release(MouseButton.PRIMARY);

        assertEquals("The game has begun! Good luck", mainStage.getTitle());


    }

    @Test
    public void testButtonLabel() {
        FxAssert.verifyThat(next, hasText("Next"));
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(CharacterScreen.class.getResource(""
                + "/config/Character-Screen.fxml"));
        Scene scene = new Scene(mainNode);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        mainStage = stage;
        next = (Button) scene.lookup("#next");
        back = (Button) scene.lookup("#back");
        diffEasy = (RadioButton) scene.lookup("#diffEasy");
        diffNormal = (RadioButton) scene.lookup("#diffNormal");
        diffInsane = (RadioButton) scene.lookup("#diffInsane");
        text = (Text) scene.lookup("#text");
        box = (ComboBox) scene.lookup("#weaponSelection");
    }

}
