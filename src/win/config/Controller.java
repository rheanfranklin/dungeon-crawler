package config;

import game.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import win.Winscreen;

import java.io.IOException;
public class Controller {

    @FXML
    private RadioButton diffEasy;
    @FXML
    private RadioButton diffNormal;
    @FXML
    private RadioButton diffInsane;
    @FXML
    private ComboBox weaponSelection;
    @FXML
    private Button back;
    @FXML
    private Button next;
    @FXML
    private Text text;
    @FXML
    private TextField nameBox;
    @FXML
    private Text warningText;

    private ToggleGroup group;
    private boolean built;
    private String playerName;
    private Stage primaryStage;
    private Player player;
    private Floor floor;
    private Room room;
    private ItemWeapon startingWeapon;


    public Controller() throws IOException {
    }

    public void buildController() {
        if (!built) {
            group = new ToggleGroup();
            diffEasy.setToggleGroup(group);
            diffNormal.setToggleGroup(group);
            diffInsane.setToggleGroup(group);
            group.selectToggle(diffEasy);
            warningText.setText("");
            built = true;
            weaponSelection.getItems().addAll("Axe", "Sword", "Magic");


        }

    }

    public void backButtonClicked() throws IOException {
        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("/welcome/Welcome.fxml"));
        primaryStage.setTitle("Untitled Dungeon Crawler");
        primaryStage.getScene().setRoot(newRoot);
    }

    public void nextButtonClicked() throws IOException {
        Image image = new Image("/resources/Char1Image.png");
        Player player;
        if (diffEasy.isSelected()) {
            player = new Player(playerName, image, Player.Difficulty.EASY);
        } else if (diffNormal.isSelected()) {
            player = new Player(playerName, image, Player.Difficulty.NORMAL);
        } else {
            player = new Player(playerName, image, Player.Difficulty.INSANE);
        }


        if (nameBox.getText().equals("")) {
            warningText.setText("You can't travel without a name!");
        } else if (weaponSelection.getSelectionModel().isEmpty()) {
            warningText.setText("You can't travel without a weapon!");
        } else {
            primaryStage = CharacterScreen.getMainStage();

            String weapon = (String) weaponSelection.getValue();
            if (weapon.equals("Axe")) {
                Image weaponImage = new Image("resources/Items/Weapons&Spells/WeaponAxe1.png");
                startingWeapon = new ItemWeapon(weaponImage, ItemWeapon.WeaponType.AXE,
                        "Chopper", "Chops enemies with\nextreme precision");
            } else if (weapon.equals("Sword")) {
                Image weaponImage = new Image("resources/Items/Weapons&Spells/WeaponSword1.png");
                startingWeapon = new ItemWeapon(weaponImage, ItemWeapon.WeaponType.SWORD,
                        "Slicer", "Slices enemies with\nextreme precision");
            } else {
                Image weaponImage = new Image("resources/Items/Weapons&Spells/SpellRed.png");
                startingWeapon = new ItemWeapon(weaponImage, ItemWeapon.WeaponType.AXE,
                        "Fire Ball", "Throws fire balls\nat enemies");
            }

            Winscreen winScreen = new Winscreen(player);
            Floor floor = new Floor();
            floor.createStartRoom();
            this.floor = floor;
            GameScreen game = new GameScreen(player, floor, floor.getStartRoom(), true);

            ListOfItems listOfItems = new ListOfItems();

            player.getInventory().add(startingWeapon);
            player.setEquippedWeapon(startingWeapon);
            player.setLevel(10);
            player.setExperience(1000);

            winScreen.setStartTime(System.currentTimeMillis());
            game.start(primaryStage);
        }
    }

    public void difficultySelectEasy() {
        diffNormal.setSelected(false);
        diffInsane.setSelected(false);
    }

    public void difficultySelectNormal() {
        diffEasy.setSelected(false);
        diffInsane.setSelected(false);
    }

    public void difficultySelectInsane() {
        diffNormal.setSelected(false);
        diffEasy.setSelected(false);

    }

    public void weaponSelect() {
        warningText.setText("");

    }

    public void nameEntered() {
        playerName = nameBox.getText();
    }
}
