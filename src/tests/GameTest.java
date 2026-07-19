package tests;


import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import welcome.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.After;

import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class GameTest extends ApplicationTest {
    private Button topExit;
    private Button bottomExit;
    private Button rightExit;
    private Button leftExit;

    private Text displayMoney;

    private boolean difficulty1 = true;
    private boolean difficulty2 = true;
    private boolean difficulty3 =  true;

    private Stage mainstage;

    @After
    public void cleanup() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void testLabel() {
        FxRobot bot = new FxRobot();
        if (difficulty1) {
            assertEquals("Current money: $500", displayMoney);
        }
        if (difficulty2) {
            assertEquals("Current money: $100", displayMoney);
        }
        if (difficulty3) {
            assertEquals("Current money: 0", displayMoney);
        }
    }

    @Test
    public void testExitDetails() {
        FxAssert.verifyThat(topExit, hasText("EXIT"));
        FxAssert.verifyThat(leftExit, hasText("EXIT"));
        FxAssert.verifyThat(rightExit, hasText("EXIT"));
        FxAssert.verifyThat(bottomExit, hasText("EXIT"));
    }



    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("/game/Game.fxml"));
        Scene scene = new Scene(mainNode);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        mainstage = stage;
        topExit = (Button) scene.lookup("#topExit");
        rightExit = (Button) scene.lookup("#rightExit");
        leftExit = (Button) scene.lookup("#leftExit");
        bottomExit = (Button) scene.lookup("#bottomExit");
        displayMoney = (Text) scene.lookup("#displayMoney");
    }

    @Test
    public void testMusicResources() {
        try {
            String musicFile1 =
                    "DungeonCrawler/src/resources/Music/218_Sleeping_Ogre.mp3";
            String musicFile2 =
                    "DungeonCrawler/src/resources/Music/230_All_Hallows_Eve.mp3";
            String musicFile3 =
                    "DungeonCrawler/src/resources/Music/236_Defiled_Temple.mp3";
            String musicFile4 =
                    "DungeonCrawler/src/resources/Music/238_Mind_Flayer_Chamber.mp3";
            String musicFile5 =
                    "DungeonCrawler/src/resources/Music/242_Spiders_Den.mp3";
            String musicFile6 =
                    "DungeonCrawler/src/resources/Music/251_Candledeep.mp3";
            String musicFile7 =
                    "DungeonCrawler/src/resources/Music/252_Vault_of_Terror.mp3";
            String musicFile8 =
                    "DungeonCrawler/src/resources/Music/254_Desert_Planet_Souk.mp3";
            String musicFile9 =
                    "DungeonCrawler/src/resources/Music/Justin Bieber - Baby.mp3";

            Media sound1 = new Media(new File(musicFile1).toURI().toString());
            Media sound2 = new Media(new File(musicFile2).toURI().toString());
            Media sound3 = new Media(new File(musicFile3).toURI().toString());
            Media sound4 = new Media(new File(musicFile4).toURI().toString());
            Media sound5 = new Media(new File(musicFile5).toURI().toString());
            Media sound6 = new Media(new File(musicFile6).toURI().toString());
            Media sound7 = new Media(new File(musicFile7).toURI().toString());
            Media sound8 = new Media(new File(musicFile8).toURI().toString());
            Media sound9 = new Media(new File(musicFile9).toURI().toString());

            MediaPlayer mediaPlayer1 = new  MediaPlayer(sound1);
            MediaPlayer mediaPlayer2 = new  MediaPlayer(sound2);
            MediaPlayer mediaPlayer3 = new  MediaPlayer(sound3);
            MediaPlayer mediaPlayer4 = new  MediaPlayer(sound4);
            MediaPlayer mediaPlayer5 = new  MediaPlayer(sound5);
            MediaPlayer mediaPlayer6 = new  MediaPlayer(sound6);
            MediaPlayer mediaPlayer7 = new  MediaPlayer(sound7);
            MediaPlayer mediaPlayer8 = new  MediaPlayer(sound8);
            MediaPlayer mediaPlayer9 = new  MediaPlayer(sound9);
        } catch (Exception e) {
            assertFalse("One of the music resources failed to load", true);
        }
    }

    @Test
    public void testGraphicalResources() {
        try {
            Path dir = Paths.get("/resources/Items");
            Files.walk(dir).forEach(path -> {
                try {
                    graphicsHelper(path.toFile());
                } catch (Exception e) {
                    assertFalse("One of the graphical resources failed to load", true);
                }
            });

            dir = Paths.get("/resources/Layout");
            Files.walk(dir).forEach(path -> {
                try {
                    graphicsHelper(path.toFile());
                } catch (Exception e) {
                    assertFalse("One of the graphical resources failed to load", true);
                }
            });

            dir = Paths.get("/resources/Monsters");
            Files.walk(dir).forEach(path -> {
                try {
                    graphicsHelper(path.toFile());
                } catch (Exception e) {
                    assertFalse("One of the graphical resources failed to load", true);
                }
            });
        } catch (Exception e) {
            assertFalse("A resource directory wasn't found", true);
        }
    }

    public void graphicsHelper(File file) throws Exception {
        if (!file.isDirectory()) {
            Image image = new Image(file.getAbsolutePath());
        }
    }

}
