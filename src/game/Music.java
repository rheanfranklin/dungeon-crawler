package game;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Music {
    /**
     * Strings of all the music files
     */
    private static final String musicFile1 =
            "DungeonCrawler/src/resources/Music/218_Sleeping_Ogre.mp3";
    private static final String musicFile2 =
            "DungeonCrawler/src/resources/Music/230_All_Hallows_Eve.mp3";
    private static final String musicFile3 =
            "DungeonCrawler/src/resources/Music/236_Defiled_Temple.mp3";
    private static final String musicFile4 =
            "DungeonCrawler/src/resources/Music/238_Mind_Flayer_Chamber.mp3";
    private static final String musicFile5 =
            "DungeonCrawler/src/resources/Music/242_Spiders_Den.mp3";
    private static final String musicFile6 =
            "DungeonCrawler/src/resources/Music/251_Candledeep.mp3";
    private static final String musicFile7 =
            "DungeonCrawler/src/resources/Music/252_Vault_of_Terror.mp3";
    private static final String musicFile8 =
            "DungeonCrawler/src/resources/Music/254_Desert_Planet_Souk.mp3";
    private static final String musicFile9 =
            "DungeonCrawler/src/resources/Music/Justin Bieber - Baby.mp3";

    private static final Media sound1 = new Media(new File(musicFile1).toURI().toString());
    private static final Media sound2 = new Media(new File(musicFile2).toURI().toString());
    private static final Media sound3 = new Media(new File(musicFile3).toURI().toString());
    private static final Media sound4 = new Media(new File(musicFile4).toURI().toString());
    private static final Media sound5 = new Media(new File(musicFile5).toURI().toString());
    private static final Media sound6 = new Media(new File(musicFile6).toURI().toString());
    private static final Media sound7 = new Media(new File(musicFile7).toURI().toString());
    private static final Media sound8 = new Media(new File(musicFile8).toURI().toString());
    private static final Media sound9 = new Media(new File(musicFile9).toURI().toString());

    private static final MediaPlayer mediaPlayer1 = new  MediaPlayer(sound1);
    private static final MediaPlayer mediaPlayer2 = new  MediaPlayer(sound2);
    private static final MediaPlayer mediaPlayer3 = new  MediaPlayer(sound3);
    private static final MediaPlayer mediaPlayer4 = new  MediaPlayer(sound4);
    private static final MediaPlayer mediaPlayer5 = new  MediaPlayer(sound5);
    private static final MediaPlayer mediaPlayer6 = new  MediaPlayer(sound6);
    private static final MediaPlayer mediaPlayer7 = new  MediaPlayer(sound7);
    private static final MediaPlayer mediaPlayer8 = new  MediaPlayer(sound8);
    private static final MediaPlayer mediaPlayer9 = new  MediaPlayer(sound9);



    private static final ArrayList<MediaPlayer> randMusicFileList =  new ArrayList<MediaPlayer>(
            Arrays.asList(
                    mediaPlayer1,
                    mediaPlayer2,
                    mediaPlayer3,
                    mediaPlayer5,
                    mediaPlayer6,
                    mediaPlayer8
            )
    );

    private static final ArrayList<MediaPlayer> musicFileList =  new ArrayList<MediaPlayer>(
            Arrays.asList(
                    mediaPlayer1,
                    mediaPlayer2,
                    mediaPlayer3,
                    mediaPlayer5,
                    mediaPlayer6,
                    mediaPlayer8,
                    mediaPlayer7,
                    mediaPlayer4,
                    mediaPlayer9

            )
    );

    private static int setInx;

    private static boolean succubus = false;

    private static boolean justin = false;

    public static void initiateMediaPlayer() {
        mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer2.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer3.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer4.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer5.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer6.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer7.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer8.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer9.setCycleCount(MediaPlayer.INDEFINITE);

        musicFileList.get(setInx).pause();
        Random random = new Random(System.currentTimeMillis());
        int randInx = random.nextInt(randMusicFileList.size() - 1);
        setInx = randInx;
        randMusicFileList.get(2).seek(Duration.ZERO);
        randMusicFileList.get(2).play();
        succubus = false;
        justin = false;

    }

    public static void setRandomSound() {
        musicFileList.get(setInx).pause();
        Random random = new Random(System.currentTimeMillis());
        int randInx = random.nextInt(randMusicFileList.size() - 1);
        setInx = randInx;
        randMusicFileList.get(2).seek(Duration.ZERO);
        randMusicFileList.get(2).play();
        succubus = false;
        justin = false;
    }

    public static void setSuccubusMusic() {
        musicFileList.get(setInx).pause();
        setInx = 7;
        musicFileList.get(setInx).seek(Duration.ZERO);
        musicFileList.get(setInx).play();
        succubus = true;
        justin = false;
    }

    public static void playJustin() {
        musicFileList.get(setInx).pause();
        setInx = 8;
        musicFileList.get(setInx).seek(Duration.ZERO);
        musicFileList.get(setInx).play();
        succubus = false;
        justin = true;
    }

    public static boolean getSuccubus() { return succubus; }

    public static boolean getJustin() { return justin; }
}
