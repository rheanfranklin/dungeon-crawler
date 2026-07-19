package tests;

import config.CharacterScreen;
import game.Floor;
import game.GameScreen;
import game.Player;
import game.Room;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import welcome.Main;
import win.Controller;
import win.Winscreen;

import static org.junit.Assert.*;

public class WinTests extends ApplicationTest {
    private Floor testFloor;
    private GameScreen thisScreen;
    private Room endRoom;
    private Winscreen winscreen;
    private Stage mainStage;

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

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("/game/GameScreen.fxml"));
        thisScreen = FXMLLoader.load(Main.class.getResource("/game/GameScreen/fxml"));
        testFloor = thisScreen.getFloor();
        endRoom = thisScreen.getFloor().getEndRoom();
        winscreen = FXMLLoader.load(Main.class.getResource("/win/Winscreen/fxml"));
        Scene scene = new Scene(mainNode);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        mainStage = stage;
    }
    @Test
    public void verifyBoolean() {
        Floor floor = new Floor();
        assertEquals(floor.getEndRoom().isEndRoom(floor), true);
    }


    @Test
    public void endScreenProperlyMet() {
        assertEquals(endRoom.hasRightExit(), false);
        assertEquals(endRoom.hasLeftExit(), false);
        assertEquals(endRoom.hasTopExit(), false);
        assertEquals(endRoom.hasBottomExit(), false);
    }

    @Test
    public void testWinOrLose() {
        Player player = new Player();
        player.setHealth(0);
        Winscreen lose = new Winscreen(player);
        assertTrue(lose.getIsDeathScreen());

        Player winner = new Player();
        winner.setHealth(100);
        Winscreen win = new Winscreen(winner);
        assertFalse(win.getIsDeathScreen());
    }

    @Test
    public void testWinLoseText() {
        Player player = new Player();
        player.setHealth(0);
        Winscreen lose = new Winscreen(player);

        win.Controller ctrl = new win.Controller();
        ctrl.buildController();
        Text mainText = ctrl.getMainText();
        Text subText = ctrl.getSubText();

        assertEquals("You Lost!", mainText.getText());
        assertEquals("Better luck next time!", subText.getText());

        Player winner = new Player();
        player.setHealth(100);
        Winscreen win = new Winscreen(winner);

        ctrl = new win.Controller();
        ctrl.buildController();

        mainText = ctrl.getMainText();
        subText = ctrl.getSubText();
        assertEquals("Congratulations!", mainText);
        assertEquals("You escaped successfully!", subText);
    }

    @Test
    public void testExperienceText() {
        Player player = new Player();
        player.setExperience(2000);
        Winscreen win = new Winscreen(player);
        win.Controller ctrl = new win.Controller();
        ctrl.buildController();
        Text expLabel = ctrl.getExpLabel();

        assertEquals("2000 points", expLabel.getText());
    }

    @Test
    public void testMoneyText() {
        Player player = new Player();
        player.setMoney(150);
        Winscreen win = new Winscreen(player);
        win.Controller ctrl = new win.Controller();
        ctrl.buildController();
        Text moneyLabel = ctrl.getMoneyLabel();

        assertEquals("$150", moneyLabel.getText());
    }
    @Test
    public void testTimer() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Winscreen.setStartTime(startTime);
        Thread.sleep(1000);
        long elapsedTime = startTime + 1000;
        win.Controller ctrl = new win.Controller();
        ctrl.buildController();
        long elapsedTimeSeconds = elapsedTime / 1000;
        long elapsedTimeMinutes = elapsedTimeSeconds / 60;
        long remainder = elapsedTimeSeconds % 60;

        Text timeLabel = ctrl.getTimeLabel();

        assertEquals("0:01", timeLabel.getText());
    }

    @Test
    public void testTryAgainButton() throws Exception {
        win.Controller ctrl = new Controller();
        Player player = new Player();
        Winscreen win = new Winscreen(player);
        Stage primaryStage = new Stage();
        win.start(primaryStage);
        ctrl.returnButtonClicked();
        assertNull(CharacterScreen.getMainStage());
        assertEquals(primaryStage, Winscreen.getMainStage());
    }

    @Test
    public void testAlignmentAdjusted() {
        Player player = new Player();
        player.setHealth(0);
        Winscreen win = new Winscreen(player);
        win.Controller ctrl = new Controller();
        ctrl.buildController();
        Text mainText = ctrl.getMainText();
        Text subText = ctrl.getSubText();
        assertEquals(TextAlignment.CENTER, mainText.getTextAlignment());
        assertEquals(TextAlignment.LEFT, subText.getTextAlignment());
    }
}
