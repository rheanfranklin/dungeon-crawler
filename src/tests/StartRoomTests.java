package tests;

import game.Floor;
import game.GameScreen;
import game.Player;
import game.Room;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import welcome.Main;

import static org.junit.Assert.assertEquals;

public class StartRoomTests extends ApplicationTest {
    private Stage mainStage;
    private Room testRoom;
    private Floor testFloor;
    private Button leftExit;
    private Button rightExit;
    private GameScreen thisScreen;
    private Player ourPlayer;
    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("/game/Game.fxml"));
        Scene scene = new Scene(mainNode);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        mainStage = stage;
        thisScreen = FXMLLoader.load(Main.class.getResource("/game/GameScreen/fxml"));
        testRoom = thisScreen.getRoom();
        //FXMLLoader.load(Main.class.getResource("/game/Room/fxml"));
        testFloor = thisScreen.getFloor();
        //FXMLLoader.load(Main.class.getResource("/game/Floor/fxml"));
        leftExit = (Button) scene.lookup("#leftExit");
        rightExit = (Button) scene.lookup("#rightExit");
        ourPlayer = thisScreen.getPlayer();

    }

    @Before
    public void createRoom() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(GameScreen.class);
        testFloor.createStartRoom();
        testRoom = testFloor.getStartRoom();
        //wooohooo!
    }

    @After
    public void cleanup() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    //If exit is present, room is non null
    @Test
    public void checkForNullRoomExits() {
        boolean check = true;
        if (testRoom.hasBottomExit() && testRoom.getBottomRoom() == null) {
            check = false;
        }
        if (testRoom.hasRightExit() && testRoom.getRightRoom() == null) {
            check = false;
        }
        if (testRoom.hasTopExit() && testRoom.getTopRoom() == null) {
            check = false;
        }
        if (testRoom.hasLeftExit() && testRoom.getLeftRoom() == null) {
            check = false;
        }
        assertEquals(check, true); //if exit is present with a null room, that's bad desu
    }
    //at least one exit should be present des
    @Test
    public void ensureExitPresent() {
        boolean check = false;
        if (testRoom.hasBottomExit()) {
            check = true;
        }
        if (testRoom.hasRightExit()) {
            check = true;
        }
        if (testRoom.hasTopExit()) {
            check = true;
        }
        if (testRoom.hasLeftExit()) {
            check = true;
        }
        assertEquals(check, true);
    }

    @Test
    public void startRoomNotEndRoom() {
        Floor floor = new Floor();

        assertEquals(floor.getStartRoom().isEndRoom(floor), false);
    }

    @Test
    public void roomRemains() {
        FxRobot botty = new FxRobot();
        Room hopeRoom = testRoom.getLeftRoom();
        botty.clickOn(leftExit);
        //Room currentRoom = thisScreen.getPlayer().getCurrentRoom();
        //assertEquals(hopeRoom, currentRoom);
    }

    @Test //for later, making sure that when we have shops money won't just reset
    public void ensurePlayerMoneyMaintains() {
        int dollarBills = ourPlayer.getMoney();
        int hopeBills = dollarBills - 10;
        ourPlayer.setMoney(dollarBills - 10);
        FxRobot botty = new FxRobot();
        Room hopeRoom = testRoom.getLeftRoom();
        botty.clickOn(leftExit);
        assertEquals(hopeBills, dollarBills);
    }

    @Test
    public void roomRemains2() {
        FxRobot botty = new FxRobot();
        Room hopeRoom = testRoom.getLeftRoom();
        botty.clickOn(leftExit);
        assertEquals(hopeRoom.hasRightExit(), true); //return exit exists
    }

    @Test
    public void roomRemains3() {
        FxRobot botty = new FxRobot();
        //Room currentRoom = thisScreen.getPlayer().getCurrentRoom();
        Room hopeRoom = testRoom.getLeftRoom();
        botty.clickOn(leftExit);
        botty.clickOn(rightExit);
        //return
        //assertEquals(thisScreen.getPlayer().getCurrentRoom(), currentRoom);
    }

}
