package game;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import win.Winscreen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Handler;

public class Controller {

    private Stage stage;
    protected Player player;
    private Floor floor;
    private Room currentRoom;
    protected Merchant merchant;

    public static final int ICON_HEIGHT = 50;
    public static final int ICON_ARC = 10;

    protected StackPane inventoryStack;
    protected StackPane merchantStack;
    protected Text name;
    protected Text description;
    protected double itemValue;
    protected Rarity itemRarity;
    protected Button button;
    protected Button buyButton;
    protected Button sellButton;
    protected Button buySellButton;
    protected Button inventoryButton;
    protected Button playerStatsButton;
    protected VBox holdIconStats;
    protected Rectangle bigIconDisplay;
    protected Rectangle merchantBigIcon;
    protected VBox rightStatsPrompt;
    protected VBox leftStatsDescription;
    protected VBox merchantItemInfo;
    protected VBox holdBackgroundIcons;
    protected HBox leftBox;
    protected Item selectedItem;
    protected VBox setUp;
    protected HBox holdInventoryButton;


    protected boolean onMerchantBuyScreen;
    protected boolean onInventoryScreen;


    public Controller(Stage stage, Player player, Floor floor, Room currentRoom) {
        this.stage = stage;
        this.player = player;
        this.floor = floor;
        this.currentRoom = currentRoom;
    }

    public Controller() {

    }

    public void clickedTopExit(boolean summonedGone) {
        floor.setLastRoomVisited(currentRoom);
        Room topRoom = currentRoom.getTopRoom();



        if (!floor.getStartRoom().equals(topRoom) && !topRoom.hasMonster()) {
            topRoom.addMonster(floor);
        }

        //creating new instance of GameScreen
        GameScreen game = new GameScreen(player, floor, topRoom, false);

        //if room top room is end room, call win screen

        if (currentRoom.hasMonster() && (!summonedGone
                    || ((currentRoom.getMonster().getName().equals("Succubus")
                    || currentRoom.getMonster().getName().equals("Big Spider"))
                    && !currentRoom.getMonster().isDead()))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can't Escape!");
            alert.setHeaderText(null);
            alert.setContentText("The monster won't let you escape through that exit!");

            alert.showAndWait();

        } else if (currentRoom.hasMonster() && !currentRoom.getMonster().isDead()) {
            if (topRoom.getVisited()) {
                game.start(stage);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Can't Escape!");
                alert.setHeaderText(null);
                alert.setContentText("The monster won't let you escape through that exit!");

                alert.showAndWait();
            }
        } else {
            game.start(stage);
        }

        currentRoom.setVisited();
    }

    public void clickedBottomExit(boolean summonedGone) {
        GameScreen.setStaticPlayer(player);
        floor.setLastRoomVisited(currentRoom);
        Room bottomRoom = currentRoom.getBottomRoom();

        if (!floor.getStartRoom().equals(bottomRoom) && !bottomRoom.hasMonster()) {
            bottomRoom.addMonster(floor);
        }

        //creating instance of game screen
        GameScreen game = new GameScreen(player, floor, bottomRoom, false);


        if (currentRoom.hasMonster() && (!summonedGone
                    || ((currentRoom.getMonster().getName().equals("Succubus")
                    || currentRoom.getMonster().getName().equals("Big Spider"))
                    && !currentRoom.getMonster().isDead()))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can't Escape!");
            alert.setHeaderText(null);
            alert.setContentText("The monster won't let you escape through that exit!");

            alert.showAndWait();

        } else if (currentRoom.hasMonster() && !currentRoom.getMonster().isDead()) {
            if (bottomRoom.getVisited()) {
                game.start(stage);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Can't Escape!");
                alert.setHeaderText(null);
                alert.setContentText("The monster won't let you escape through that exit!");

                alert.showAndWait();
            }
        } else {
            game.start(stage);
        }
        currentRoom.setVisited();

    }

    public void clickedLeftExit(boolean summonedGone) {
        GameScreen.setStaticPlayer(player);
        floor.setLastRoomVisited(currentRoom);
        Room leftRoom = currentRoom.getLeftRoom();

        //creating instance of game screen
        GameScreen game = new GameScreen(player, floor, leftRoom, false);

        if (!floor.getStartRoom().equals(leftRoom) && !leftRoom.hasMonster()) {
            leftRoom.addMonster(floor);
        }

        if (currentRoom.hasMonster() && (!summonedGone
                    || ((currentRoom.getMonster().getName().equals("Succubus")
                    || currentRoom.getMonster().getName().equals("Big Spider"))
                    && !currentRoom.getMonster().isDead()))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can't Escape!");
            alert.setHeaderText(null);
            alert.setContentText("The monster won't let you escape through that exit!");

            alert.showAndWait();

        } else if (currentRoom.hasMonster() && !currentRoom.getMonster().isDead()) {
            if (leftRoom.getVisited()) {
                game.start(stage);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Can't Escape!");
                alert.setHeaderText(null);
                alert.setContentText("The monster won't let you escape through that exit!");

                alert.showAndWait();
            }
        } else {
            game.start(stage);

        }
        currentRoom.setVisited();

    }

    public void clickedRightExit(boolean summonedGone) {
        GameScreen.setStaticPlayer(player);
        floor.setLastRoomVisited(currentRoom);
        Room rightRoom = currentRoom.getRightRoom();

        if (!floor.getStartRoom().equals(rightRoom) && !rightRoom.hasMonster()) {
            rightRoom.addMonster(floor);
        }

        //creating instance of game screen
        GameScreen game = new GameScreen(player, floor, rightRoom, false);


        //if  right room == end room, then populate win screen
        //else if current room has a monster
        if (currentRoom.hasMonster() &&(!summonedGone
                    || ((currentRoom.getMonster().getName().equals("Succubus")
                    || currentRoom.getMonster().getName().equals("Big Spider"))
                    && !currentRoom.getMonster().isDead()))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can't Escape!");
            alert.setHeaderText(null);
            alert.setContentText("The monster won't let you escape through that exit!");

            alert.showAndWait();

        } else if (currentRoom.hasMonster() && !currentRoom.getMonster().isDead()) {
            if (rightRoom.getVisited()) {
                game.start(stage);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Can't Escape!");
                alert.setHeaderText(null);
                alert.setContentText("The monster won't let you escape through that exit!");

                alert.showAndWait();
            }
        } else {
            game.start(stage);

        }
        currentRoom.setVisited();
    }
    public void pressedUpKey(GameScreen gameScreen) {
        double currY = gameScreen.characterIcon.getY();
        gameScreen.characterIcon.setY(currY - 10);
    }

    public void pressedDownKey(GameScreen gameScreen) {
        double currY = gameScreen.characterIcon.getY();
        gameScreen.characterIcon.setY(currY + 10);
    }

    public void pressedLeftKey(GameScreen gameScreen) {
        double currX = gameScreen.characterIcon.getX();
        gameScreen.characterIcon.setX(currX - 10);
        gameScreen.characterIcon.setRotate(180);
    }

    public void pressedRightKey(GameScreen gameScreen) {
        double currX = gameScreen.characterIcon.getX();
        gameScreen.characterIcon.setX(currX + 10);
        gameScreen.characterIcon.setRotate(0);
    }

    public void openInventoryWindow(GameScreen gameScreen) {
        //Display window with items
        //Needs to be square window with 4 spots WEAPON/MISC/SPELL/CONSUMABLE

        inventoryStack = new StackPane();
        inventoryStack = setUpInventoryScreenBackground();
        inventoryStack.setId("inventory");
        gameScreen.stack.getChildren().add(inventoryStack);
        setUp = new VBox();
        inventoryButton = new Button("Inventory");
        playerStatsButton = new Button("Player Stats");
        holdInventoryButton = new HBox();
        holdInventoryButton.getChildren().add(inventoryButton);
        holdInventoryButton.getChildren().add(playerStatsButton);
        holdInventoryButton.setAlignment(Pos.CENTER);
        holdInventoryButton.setSpacing(200);
        inventoryStack.getChildren().add(setUp);

        setUpInventoryWindow(gameScreen);

    }
    public void setUpInventoryWindow(GameScreen gameScreen) {
        onInventoryScreen = true;
        Inventory inventory = gameScreen.player.getInventory();

        HBox iconDisplay = new HBox();

        //display rectangle
        VBox holdDisplayBox = new VBox();
        Rectangle display = new Rectangle();
        display.setWidth(223);
        display.setHeight(223);
        display.setArcHeight(20);
        display.setArcWidth(20);
        display.setStroke(Color.DARKGREY);
        display.setFill(Color.ALICEBLUE);
        StackPane bigIconStack = new StackPane();
        bigIconStack.getChildren().add(display);
        //display rectangle
        bigIconDisplay = new Rectangle();
        bigIconDisplay.setWidth(223);
        bigIconDisplay.setHeight(223);
        bigIconDisplay.setArcHeight(20);
        bigIconDisplay.setArcWidth(20);
        bigIconDisplay.setStroke(Color.DARKGREY);
        bigIconDisplay.setFill(Color.ALICEBLUE);
        bigIconStack.getChildren().add(bigIconDisplay);
        holdDisplayBox.getChildren().add(bigIconStack);
        holdDisplayBox.setAlignment(Pos.CENTER_LEFT);

        iconDisplay.getChildren().add(holdDisplayBox);

        HBox iconStats = new HBox();
        rightStatsPrompt = new VBox();
        leftStatsDescription = new VBox();

        rightStatsPrompt.setSpacing(20.0);
        rightStatsPrompt.setAlignment(Pos.TOP_RIGHT);

        leftStatsDescription.setSpacing(20);
        leftStatsDescription.setAlignment(Pos.TOP_LEFT);

        iconStats.getChildren().add(rightStatsPrompt);
        iconStats.getChildren().add(leftStatsDescription);
        iconStats.setSpacing(5);

        holdIconStats = new VBox();
        holdIconStats.getChildren().add(iconStats);
        holdIconStats.setAlignment(Pos.CENTER);
        holdIconStats.setSpacing(20);

        button = new Button();

        //Image image = new Image("resources/Items/Weapons&Spells/SpellBlue.png");
        //ItemConsumable itemConsumable =
        // new ItemConsumable(image, 10, "Spell", "Is a spell.", 100);

        iconDisplay.getChildren().add(holdIconStats);
        iconDisplay.setAlignment(Pos.CENTER);
        iconDisplay.setSpacing(20);


        setUp.getChildren().add(iconDisplay);
        setUp.setAlignment(Pos.CENTER);

        VBox grid = new VBox();
        StackPane gridStack = new StackPane();
        createGrid(grid);
        gridStack.getChildren().add(grid);

        setUp.setSpacing(10);

        setUp.getChildren().add(gridStack);
        setUp.getChildren().add(holdInventoryButton);

        Rectangle itemIcon = new Rectangle();
        itemIcon.setStroke(Color.WHITE);
        itemIcon.setArcHeight(ICON_ARC);
        itemIcon.setArcWidth(ICON_ARC);
        itemIcon.setHeight(ICON_HEIGHT);
        itemIcon.setWidth(ICON_HEIGHT);

        itemIcon.setFill(new ImagePattern(
                new Image("resources/Items/Weapons&Spells/SpellBlue.png")));
        //itemConsumable.setController(this);
        inventory.setUpInventoryGrid(gridStack, this);

        //itemConsumable.startInventoryLoop();

        //inventoryStack.getChildren().add(itemConsumable.getIconBox());
        inventoryStack.setAlignment(Pos.CENTER);
        holdIconStats.getChildren().add(button);

        button.setOnAction(e -> {
            if (selectedItem != null) {
                selectedItem.mouseClickedButton();
            }
        });

        playerStatsButton.setOnAction(f -> {
            if (onInventoryScreen) {
                setUp.getChildren().remove(iconDisplay);
                setUp.getChildren().remove(gridStack);
                setUp.getChildren().remove(holdInventoryButton);
                setUpPlayerStats(gameScreen);
            }
        });
    }

    public void setUpPlayerStats(GameScreen gameScreen) {
        onInventoryScreen = false;

        //top half of the player stats screen
        HBox topHalf = new HBox();
        //holds the stats that will be displayed on the left side of the top half screen
        VBox leftSideStats = new VBox();
        //holds the stats that will be displayed on the right side of the top half screen
        VBox rightSideStats = new VBox();
        //aligns spacing
        HBox blank = new HBox();
        //holds the player's available points
        HBox pointStats = new HBox();
        //holds the player's health stats
        HBox healthStats = new HBox();
        //holds the player's stamina stats
        HBox staminaStats = new HBox();
        //holds the player's attack damage stats
        HBox attackStats = new HBox();
        //holds the player's defense stats
        HBox defenseStats = new HBox();
        //adding buttons to increase stats
        Button increaseHealth = new Button("+");
        Button increaseStamina = new Button("+");
        Button increaseDamage = new Button("+");
        Button increaseDefense = new Button("+");


        //point prompt
        Text blankText = new Text("boop");
        Text pointPrompt = new Text("Points:");
        Text points = new Text("" + gameScreen.player.getIncreasePoints());
        Text healthPrompt = new Text("Max Health:");
        Text health = new Text("" + gameScreen.player.getMaxHealth() + " HP");
        Text staminaPrompt = new Text("Max Stamina:");
        Text stamina = new Text("" + gameScreen.player.getMaxStamina() + " ST");
        Text attackPrompt = new Text("Attack Damage:");
        Text attack = new Text("" + gameScreen.player.getAttackDamage() + " PT");
        Text defensePrompt = new Text("Defense:");
        Text defense = new Text("" + player.getDefense() + " PT");

        setUpDisplayText2(pointPrompt);
        setUpDisplayText2(points);
        setUpDisplayText2(healthPrompt);
        setUpDisplayText2(health);
        health.setFill(Color.CYAN);
        setUpDisplayText2(staminaPrompt);
        setUpDisplayText2(stamina);
        stamina.setFill(Color.LIMEGREEN);
        setUpDisplayText2(attackPrompt);
        setUpDisplayText2(attack);
        attack.setFill(Color.RED);
        setUpDisplayText2(defensePrompt);
        setUpDisplayText2(defense);
        defense.setFill(Color.MAGENTA);


        pointStats.getChildren().add(pointPrompt);
        pointStats.getChildren().add(points);
        pointStats.setSpacing(10);
        pointStats.setAlignment(Pos.CENTER_LEFT);
        healthStats.getChildren().add(healthPrompt);
        healthStats.getChildren().add(health);
        healthStats.getChildren().add(increaseHealth);
        healthStats.setSpacing(10);
        healthStats.setAlignment(Pos.CENTER_LEFT);
        staminaStats.getChildren().add(staminaPrompt);
        staminaStats.getChildren().add(stamina);
        staminaStats.getChildren().add(increaseStamina);
        staminaStats.setSpacing(10);
        staminaStats.setAlignment(Pos.CENTER_LEFT);
        blank.getChildren().add(blankText);
        attackStats.getChildren().add(attackPrompt);
        attackStats.getChildren().add(attack);
        attackStats.getChildren().add(increaseDamage);
        attackStats.setSpacing(10);
        attackStats.setAlignment(Pos.CENTER_LEFT);
        defenseStats.getChildren().add(defensePrompt);
        defenseStats.getChildren().add(defense);
        defenseStats.getChildren().add(increaseDefense);
        defenseStats.setSpacing(10);
        defenseStats.setAlignment(Pos.CENTER_LEFT);

        leftSideStats.getChildren().add(pointStats);
        leftSideStats.getChildren().add(healthStats);
        leftSideStats.getChildren().add(staminaStats);
        leftSideStats.setAlignment(Pos.CENTER);
        leftSideStats.setSpacing(20);

        rightSideStats.getChildren().add(blank);
        rightSideStats.getChildren().add(attackStats);
        rightSideStats.getChildren().add(defenseStats);
        rightSideStats.setAlignment(Pos.CENTER);
        rightSideStats.setSpacing(20);

        topHalf.getChildren().add(leftSideStats);
        topHalf.getChildren().add(rightSideStats);
        topHalf.setAlignment(Pos.CENTER);
        topHalf.setSpacing(30);

        //bottom half of the player stats screen
        VBox bottomHalf = new VBox();
        //hold equipped text
        HBox holdEquippedText = new HBox();
        //Will hold the first row of the "equipped" display
        HBox equippedRow1 = new HBox();
        //Will hold the second row of the "equipped" display
        HBox equippedRow2 = new HBox();
        //text that says "equipped"
        Text equippedText = new Text("Equipped: ");
        //pushed equipped text to the left
        Rectangle spacingRectangle = new Rectangle();
        //stack panes to hold display items
        StackPane stackPane1 = new StackPane();
        StackPane stackPane2 = new StackPane();
        StackPane stackPane3 = new StackPane();
        StackPane stackPane4 = new StackPane();
        StackPane stackPane5 = new StackPane();
        StackPane stackPane6 = new StackPane();
        //text that will be displayed when player does not have something equipped
        Text display1Text = new Text("Weapon");
        Text display2Text = new Text("Necklace");
        Text display3Text = new Text("Ring");
        Text display4Text = new Text("Helmet");
        Text display5Text = new Text("Chest\nPlate");
        Text display6Text = new Text("Pants");
        //rectangles that will display the equipped items
        Rectangle equippedWeapon = new Rectangle();
        Rectangle equippedNecklace = new Rectangle();
        Rectangle equippedRing = new Rectangle();
        Rectangle equippedHelmet = new Rectangle();
        Rectangle equippedChestPlate = new Rectangle();
        Rectangle equippedPants = new Rectangle();

        setUpBackgroundDisplayRectangle(stackPane1);
        setUpBackgroundDisplayRectangle(stackPane2);
        setUpBackgroundDisplayRectangle(stackPane3);
        setUpBackgroundDisplayRectangle(stackPane4);
        setUpBackgroundDisplayRectangle(stackPane5);
        setUpBackgroundDisplayRectangle(stackPane6);

        setUpDisplayText(equippedText);

        setUpDisplayText(display1Text);
        setUpDisplayText(display2Text);
        setUpDisplayText(display3Text);
        setUpDisplayText(display4Text);
        setUpDisplayText(display5Text);
        setUpDisplayText(display6Text);

        setUpDisplayRectangle(equippedWeapon);
        setUpDisplayRectangle(equippedNecklace);
        setUpDisplayRectangle(equippedRing);
        setUpDisplayRectangle(equippedHelmet);
        setUpDisplayRectangle(equippedChestPlate);
        setUpDisplayRectangle(equippedPants);

        if (gameScreen.player.getEquippedWeapon() != null) {
            equippedWeapon.setFill(
                    new ImagePattern(gameScreen.player.getEquippedWeapon().getItemIcon()));
            display1Text.setText("");
        }
        if (gameScreen.player.getEquippedNecklace() != null) {
            equippedNecklace.setFill(
                    new ImagePattern(gameScreen.player.getEquippedNecklace().getItemIcon()));
            display2Text.setText("");
        }
        if (gameScreen.player.getEquippedRing() != null) {
            equippedRing.setFill(
                    new ImagePattern(gameScreen.player.getEquippedRing().getItemIcon()));
            display3Text.setText("");
        }
        if (gameScreen.player.getEquippedHelmet() != null) {
            equippedHelmet.setFill(
                    new ImagePattern(gameScreen.player.getEquippedHelmet().getItemIcon()));
            display4Text.setText("");
        }
        if (gameScreen.player.getEquippedChest() != null) {
            equippedChestPlate.setFill(
                    new ImagePattern(gameScreen.player.getEquippedChest().getItemIcon()));
            display5Text.setText("");
        }
        if (gameScreen.player.getEquippedPants() != null) {
            equippedPants.setFill(
                    new ImagePattern(gameScreen.player.getEquippedPants().getItemIcon()));
            display6Text.setText("");
        }

        stackPane1.getChildren().add(equippedWeapon);
        stackPane2.getChildren().add(equippedNecklace);
        stackPane3.getChildren().add(equippedRing);
        stackPane4.getChildren().add(equippedHelmet);
        stackPane5.getChildren().add(equippedChestPlate);
        stackPane6.getChildren().add(equippedPants);

        stackPane1.getChildren().add(display1Text);
        stackPane2.getChildren().add(display2Text);
        stackPane3.getChildren().add(display3Text);
        stackPane4.getChildren().add(display4Text);
        stackPane5.getChildren().add(display5Text);
        stackPane6.getChildren().add(display6Text);

        stackPane1.setAlignment(Pos.CENTER);
        stackPane2.setAlignment(Pos.CENTER);
        stackPane3.setAlignment(Pos.CENTER);
        stackPane4.setAlignment(Pos.CENTER);
        stackPane5.setAlignment(Pos.CENTER);
        stackPane6.setAlignment(Pos.CENTER);

        equippedRow1.getChildren().add(stackPane1);
        equippedRow1.getChildren().add(stackPane2);
        equippedRow1.getChildren().add(stackPane3);

        equippedRow2.getChildren().add(stackPane4);
        equippedRow2.getChildren().add(stackPane5);
        equippedRow2.getChildren().add(stackPane6);

        equippedRow1.setAlignment(Pos.CENTER);
        equippedRow2.setAlignment(Pos.CENTER);

        equippedRow1.setSpacing(50);
        equippedRow2.setSpacing(50);

        spacingRectangle.setWidth(150);
        spacingRectangle.setHeight(2);
        spacingRectangle.setFill(Color.TRANSPARENT);

        holdEquippedText.getChildren().add(equippedText);
        holdEquippedText.getChildren().add(spacingRectangle);
        holdEquippedText.setSpacing(150);
        holdEquippedText.setAlignment(Pos.CENTER);

        bottomHalf.getChildren().add(holdEquippedText);
        bottomHalf.getChildren().add(equippedRow1);
        bottomHalf.getChildren().add(equippedRow2);
        bottomHalf.setAlignment(Pos.CENTER);
        bottomHalf.setSpacing(20);

        setUp.getChildren().add(topHalf);
        setUp.getChildren().add(bottomHalf);
        setUp.setSpacing(50);
        setUp.getChildren().add(holdInventoryButton);

        inventoryButton.setOnAction(e -> {
            if (!onInventoryScreen) {
                setUp.getChildren().remove(topHalf);
                setUp.getChildren().remove(bottomHalf);
                setUp.getChildren().remove(holdInventoryButton);
                setUpInventoryWindow(gameScreen);
            }
        });

        increaseDefense.setOnAction(e -> {
            if (gameScreen.player.getIncreasePoints() > 1) {
                gameScreen.player.setDefense(gameScreen.player.getDefense() + 10);
                gameScreen.player.setIncreasePoints(gameScreen.player.getIncreasePoints() - 1);
                defense.setText("" + gameScreen.player.getDefense() + " PT");
                points.setText("" + gameScreen.player.getIncreasePoints());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not enough points!");
                alert.setHeaderText(null);
                alert.setContentText("You don't have enough points to increase your defense!");

                alert.showAndWait();
            }
        });

        increaseHealth.setOnAction(e -> {
            if (gameScreen.player.getIncreasePoints() > 1) {
                gameScreen.player.setMaxHealth(gameScreen.player.getMaxHealth() + 10);
                gameScreen.player.setIncreasePoints(gameScreen.player.getIncreasePoints() - 1);
                health.setText("" + gameScreen.player.getMaxHealth() + " HP");
                points.setText("" + gameScreen.player.getIncreasePoints());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not enough points!");
                alert.setHeaderText(null);
                alert.setContentText("You don't have enough points to increase your health!");

                alert.showAndWait();
            }
        });

        increaseDamage.setOnAction(e -> {
            if (gameScreen.player.getIncreasePoints() > 1) {
                gameScreen.player.setAttackDamage(gameScreen.player.getAttackDamage() + 10);
                gameScreen.player.setIncreasePoints(gameScreen.player.getIncreasePoints() - 1);
                attack.setText("" + gameScreen.player.getAttackDamage() + " PT");
                points.setText("" + gameScreen.player.getIncreasePoints());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not enough !");
                alert.setHeaderText(null);
                alert.setContentText("You don't have enough points to increase your attack damage!");

                alert.showAndWait();
            }
        });


        increaseStamina.setOnAction(e -> {
            if (gameScreen.player.getIncreasePoints() > 1) {
                gameScreen.player.setMaxStamina(gameScreen.player.getMaxStamina() + 10);
                gameScreen.player.setIncreasePoints(gameScreen.player.getIncreasePoints() - 1);
                stamina.setText("" + gameScreen.player.getMaxStamina() + " ST");
                points.setText("" + gameScreen.player.getIncreasePoints());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not enough points!");
                alert.setHeaderText(null);
                alert.setContentText("You don't have enough points to increase your stamina!");

                alert.showAndWait();
            }
        });

    }

    public void setUpEquippedBox() {

    }

    private void setUpDisplayText(Text text) {
        text.setFont(GameScreen.STANDARD_FONT);
        text.setFill(Color.LIMEGREEN);
    }

    private void setUpDisplayText2(Text text) {
        text.setFont(GameScreen.STANDARD_FONT);
        text.setFill(Color.WHITE);
    }

    public void setUpDisplayRectangle(Rectangle rectangle) {
        rectangle.setWidth(100);
        rectangle.setHeight(100);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        rectangle.setStroke(Color.WHITE);
        rectangle.setFill(Color.TRANSPARENT);
    }

    private void setUpBackgroundDisplayRectangle(StackPane stackPane) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(100);
        rectangle.setHeight(100);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        rectangle.setStroke(Color.WHITE);
        rectangle.setFill(Color.DARKSLATEGREY);
        stackPane.getChildren().add(rectangle);

    }

    public StackPane setUpInventoryScreenBackground() {
        StackPane inventoryStack = new StackPane();

        Rectangle backgroundInventory = new Rectangle();
        backgroundInventory.setFill(Color.DARKGREY);
        backgroundInventory.setWidth(600);
        backgroundInventory.setHeight(600);
        backgroundInventory.setStroke(Color.BLACK);
        backgroundInventory.setArcWidth(20);
        backgroundInventory.setArcHeight(20);

        Rectangle layerInventory = new Rectangle();
        layerInventory.setFill(Color.BLACK);
        layerInventory.setWidth(550);
        layerInventory.setHeight(550);
        layerInventory.setStroke(Color.BLACK);
        layerInventory.setArcWidth(20);
        layerInventory.setArcHeight(20);

        inventoryStack.getChildren().add(backgroundInventory);
        inventoryStack.setAlignment(Pos.CENTER);
        inventoryStack.getChildren().add(layerInventory);
        inventoryStack.setAlignment(Pos.CENTER);

        return inventoryStack;
    }

    public void createGrid(VBox grid) {
        HBox row1 = new HBox();
        createRows(row1);

        HBox row2 = new HBox();
        createRows(row2);

        HBox row3 = new HBox();
        createRows(row3);

        HBox row4 = new HBox();
        createRows(row4);

        HBox row5 = new HBox();
        createRows(row5);

        grid.getChildren().add(row1);
        grid.getChildren().add(row2);
        grid.getChildren().add(row3);
        grid.getChildren().add(row4);
        grid.getChildren().add(row5);

        grid.setAlignment(Pos.CENTER);

        grid.setSpacing(2);
    }

    public void createRows(HBox row) {
        row.getChildren().add(createTile());
        row.getChildren().add(createTile());
        row.getChildren().add(createTile());
        row.getChildren().add(createTile());
        row.getChildren().add(createTile());
        row.getChildren().add(createTile());
        row.getChildren().add(createTile());
        row.getChildren().add(createTile());
        row.getChildren().add(createTile());
        row.getChildren().add(createTile());
        row.setAlignment(Pos.CENTER);
        row.setSpacing(2);
    }

    public Rectangle createTile() {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.DIMGRAY);
        rectangle.setStroke(Color.WHITE);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        rectangle.setWidth(50);
        rectangle.setHeight(50);

        return rectangle;
    }

    public void closeInventoryWindow(GameScreen gameScreen) {
        Node inventory  = gameScreen.stack.lookup("#inventory");
        gameScreen.stack.getChildren().remove(inventory);
        selectedItem = null;
    }

    public void openMerchantWindow(GameScreen gameScreen) {
        merchantStack = new StackPane();

        merchantStack = setUpInventoryScreenBackground();
        gameScreen.stack.getChildren().add(merchantStack);
        merchantStack.setId("merchantWindow");
        onMerchantBuyScreen = true;
        merchant = gameScreen.getRoom().getMerchant();
        setUpBuyWindow(gameScreen);


    }

    public void setUpBuyWindow(GameScreen gameScreen) {
        //holds the entire merchant pane
        VBox merchantScreenBox = new VBox();
        //h box that will hold the buy/sell buttons
        HBox holdButtons = new HBox();
        //button player will press to go to the buy screen
        buyButton = new Button("Buy Screen");
        //button player will press to go to the sell screen
        sellButton = new Button("Sell Screen");
        //inventory that will be displayed on the screen
        AtomicReference<Inventory> inventory
                = new AtomicReference<>(gameScreen.getRoom().getMerchant().getInventory());


        //below are all the items on the left side of the merchant screen
        //scroll bar so you can scroll through the merchant's items
        ScrollBar scrollBar = new ScrollBar();
        //vbox that we will use to hold the item icons;
        holdBackgroundIcons = new VBox();
        //item icons are generated by the inventory class
        VBox holdItemIcons = new VBox();
        //the box that holds the left side of the merchant screen
        leftBox = new HBox();

        //below are all the items on the right side of the merchant screen
        //
        Text playerMoney = new Text("$ " + gameScreen.player.getMoney());
        //background rectangle of the icon display
        Rectangle backgroundIconDisplay = new Rectangle();
        //holds the icon in the big icon display
        merchantBigIcon = new Rectangle();
        //stack pane that will hold the background rectangle & the item icon
        StackPane bigIconStack = new StackPane();
        //box that will display the item info
        merchantItemInfo = new VBox();
        //sets up the button that will allow the player to buy/sell items
        buySellButton = new Button("Buy");
        //box that will hold everything in the right side of the merchant screen
        VBox rightBox = new VBox();

        //holds the left and right side of the merchant screen
        HBox holdLeftAndRightBoxes = new HBox();

        //adding holdLeftAndRightBoxes to merchant stack
        merchantStack.getChildren().add(merchantScreenBox);

        //setting up the merchant screen box
        merchantScreenBox.getChildren().add(holdLeftAndRightBoxes);
        merchantScreenBox.getChildren().add(holdButtons);
        holdButtons.getChildren().add(buyButton);
        holdButtons.getChildren().add(sellButton);
        merchantScreenBox.setAlignment(Pos.CENTER);
        merchantScreenBox.setSpacing(10);
        holdButtons.setAlignment(Pos.CENTER);
        holdButtons.setSpacing(200);


        //adding left and right sides of the merchant screen to holdLeftAndRightBoxes
        holdLeftAndRightBoxes.getChildren().add(leftBox);
        holdLeftAndRightBoxes.getChildren().add(rightBox);
        holdLeftAndRightBoxes.setAlignment(Pos.CENTER);
        holdLeftAndRightBoxes.setSpacing(20);

        //setting up left side of merchant screen
        leftBox.getChildren().add(scrollBar);
        leftBox.getChildren().add(holdBackgroundIcons);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setSpacing(5);

        //setting up scroll bar
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setMaxHeight(500);

        holdItemIcons.setLayoutX(5);

        final int[] start = {0};
        final int[] end = {9};


        setUpMerchantIconBackground(
                inventory.get().getListOfItems());


        //setting up right side of merchant screen
        rightBox.getChildren().add(playerMoney);
        rightBox.getChildren().add(bigIconStack);
        rightBox.getChildren().add(merchantItemInfo);
        rightBox.getChildren().add(buySellButton);
        rightBox.setAlignment(Pos.TOP_CENTER);
        rightBox.setSpacing(10);

        playerMoney.setFont(GameScreen.STANDARD_FONT);
        playerMoney.setFill(Color.WHITE);

        //set up big icon display
        backgroundIconDisplay.setWidth(223);
        backgroundIconDisplay.setHeight(223);
        backgroundIconDisplay.setArcHeight(20);
        backgroundIconDisplay.setArcWidth(20);
        backgroundIconDisplay.setStroke(Color.DARKGREY);
        backgroundIconDisplay.setFill(Color.ALICEBLUE);

        merchantBigIcon.setWidth(223);
        merchantBigIcon.setHeight(223);
        merchantBigIcon.setArcHeight(20);
        merchantBigIcon.setArcWidth(20);
        merchantBigIcon.setStroke(Color.DARKGREY);
        merchantBigIcon.setFill(Color.ALICEBLUE);

        bigIconStack.getChildren().add(backgroundIconDisplay);
        bigIconStack.getChildren().add(merchantBigIcon);
        bigIconStack.setAlignment(Pos.CENTER);

        merchantItemInfo.setAlignment(Pos.CENTER);
        merchantItemInfo.setSpacing(5);

        //setting up what happens whenever you move the scroll bar
        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue,
                                Number number, Number t1) {
                if (t1.intValue() > number.intValue()) {
                    if (end[0] < inventory.get().getListOfItems().size()) {
                        ++start[0];
                        ++end[0];
                        scrollDown(inventory.get().getListOfItems(), holdBackgroundIcons, end[0] - 1);
                        startMerchantSellLoop(inventory.get().getListOfItems());
                    }
                } else {
                    if (start[0] - 1 >= 0) {
                        --start[0];
                        --end[0];
                        scrollUp(inventory.get().getListOfItems(), holdBackgroundIcons, start[0]);
                        startMerchantSellLoop(inventory.get().getListOfItems());
                    }

                }
            }
        });

        startMerchantSellLoop(inventory.get().getListOfItems());

        sellButton.setOnAction(e -> {
            onMerchantBuyScreen = false;
            inventory.set(gameScreen.getPlayer().getInventory());
            setUpMerchantIconBackground(
                    inventory.get().getListOfItems());
            startMerchantSellLoop(inventory.get().getListOfItems());
            buySellButton.setText("Sell");
        });

        buyButton.setOnAction(e -> {
            onMerchantBuyScreen = true;
            inventory.set(gameScreen.getRoom().getMerchant().getInventory());
            setUpMerchantIconBackground(
                    inventory.get().getListOfItems());
            startMerchantSellLoop(inventory.get().getListOfItems());
            buySellButton.setText("Buy");
        });

        buySellButton.setOnAction(e -> {
            if (selectedItem != null) {
                selectedItem.mouseClickedMerchantButton();
                setUpMerchantIconBackground(
                        inventory.get().getListOfItems());
                playerMoney.setText("$ " + gameScreen.player.getMoney());
            }
        });
    }


    public void setUpMerchantIconBackground(
            ArrayList<Item> merchantItemList) {
        leftBox.getChildren().remove(1);
        holdBackgroundIcons = new VBox();
        leftBox.getChildren().add(holdBackgroundIcons);
        int j = Math.min(9, merchantItemList.size());
        for (int i = 0; i < j; ++i) {
            HBox holdIcons = new HBox();
            StackPane iconStack = new StackPane();

            iconStack.getChildren().add(createTile());
            iconStack.getChildren().add(merchantItemList.get(i).getMerchantIconBox());
            holdIcons.getChildren().add(iconStack);

            merchantItemList.get(i).setHoldMerchantItem(holdIcons);

            Text itemName = new Text(merchantItemList.get(i).getItemName());
            itemName.setFont(GameScreen.STANDARD_FONT);
            itemName.setFill(Color.WHITE);
            holdIcons.getChildren().add(itemName);

            holdIcons.setAlignment(Pos.CENTER_LEFT);
            holdIcons.setSpacing(5);
            iconStack.setAlignment(Pos.CENTER_LEFT);

            holdBackgroundIcons.getChildren().add(holdIcons);
        }
        holdBackgroundIcons.setSpacing(5);
        holdBackgroundIcons.setAlignment(Pos.CENTER_LEFT);
    }

    public void startMerchantSellLoop(ArrayList<Item> merchantItemList) {
        for (Item i : merchantItemList) {
            i.setController(this);
            i.stopMerchantLoop();
            i.startMerchantLoop();
        }
    }

    public void scrollDown(ArrayList<Item> merchantItemList, VBox holdBackgroundIcons, int index) {
        holdBackgroundIcons.getChildren().remove(0);
        merchantItemInfo.getChildren().removeAll();
        HBox holdIcons = new HBox();
        StackPane iconStack = new StackPane();
        iconStack.getChildren().add(createTile());
        iconStack.getChildren().add(merchantItemList.get(index).getMerchantIconBox());
        holdIcons.getChildren().add(iconStack);
        Text itemName = new Text(merchantItemList.get(index).getItemName());
        merchantItemList.get(index).setHoldMerchantItem(holdIcons);
        itemName.setFont(GameScreen.STANDARD_FONT);
        itemName.setFill(Color.WHITE);
        holdIcons.getChildren().add(itemName);

        holdIcons.setAlignment(Pos.CENTER_LEFT);
        holdIcons.setSpacing(5);
        iconStack.setAlignment(Pos.CENTER_LEFT);




        if (!merchantItemList.get(index).checkMerchantLoopStarted()) {
            merchantItemList.get(index).setController(this);
            merchantItemList.get(index).startMerchantLoop();
        }
        holdBackgroundIcons.getChildren().add(holdIcons);

    }
    public void scrollUp(ArrayList<Item> merchantItemList, VBox holdBackgroundIcons, int index) {

        HBox holdIcons = new HBox();
        holdBackgroundIcons.getChildren().remove(8);
        merchantItemInfo.getChildren().removeAll();
        StackPane iconStack = new StackPane();
        iconStack.getChildren().add(createTile());
        iconStack.getChildren().add(merchantItemList.get(index).getMerchantIconBox());
        holdIcons.getChildren().add(iconStack);
        Text itemName = new Text(merchantItemList.get(index).getItemName());
        merchantItemList.get(index).setHoldMerchantItem(holdIcons);
        itemName.setFont(GameScreen.STANDARD_FONT);
        itemName.setFill(Color.WHITE);
        holdIcons.getChildren().add(itemName);

        holdIcons.setAlignment(Pos.CENTER_LEFT);
        holdIcons.setSpacing(5);
        iconStack.setAlignment(Pos.CENTER_LEFT);

        merchantItemList.get(index).setController(this);

        if (!merchantItemList.get(index).checkMerchantLoopStarted()) {
            merchantItemList.get(index).startMerchantLoop();
        }

        holdBackgroundIcons.getChildren().add(0, holdIcons);

    }


    public void closeMerchantWindow(GameScreen gameScreen) {
        Node merchantWindow  = gameScreen.stack.lookup("#merchantWindow");
        gameScreen.stack.getChildren().remove(merchantWindow);
        selectedItem = null;
    }

}