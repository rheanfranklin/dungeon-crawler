package game; /* M2 initial game screen requirements:
 * Display starting money (starting money should vary based on the difficulty chosen)
 * Display exists (up to four exits)
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class GameScreen extends Application {

    protected Player player;
    protected Stage stage;
    protected Floor floor;
    protected Room room;
    protected Monster monster;

    protected ImageView characterIcon;
    protected ImageView monsterIcon;
    protected ImageView weaponIcon;
    protected Rectangle merchantIcon;

    protected static Player staticPlayer;

    protected static final int EXPERIENCE_PER_LEVEL = 100;

    protected StackPane stack;
    protected Pane movePane;
    protected Text playerMoney;
    protected Text monsterHP;
    protected Text minusPlayerHP;
    protected Text minusMonsterHP;
    protected Text monsterName;
    protected Text plusExperience;
    protected Text playerExperience;
    protected Text secondsCountDown;
    protected VBox holdStats;
    protected VBox holdExperience;
    protected BorderPane screenSetUp;
    protected BorderPane wallPane;
    protected Rectangle background;
    protected Rectangle playerHPBar;
    protected Rectangle playerBorderHPBar;
    protected Rectangle playerManaBar;
    protected Rectangle playerBorderManaBar;
    protected Rectangle playerStaminaBar;
    protected Rectangle playerBorderStaminaBar;
    protected Rectangle experienceBar;
    protected Rectangle experienceBorderBar;
    protected Rectangle monsterHPBar;
    protected Rectangle monsterBorderHPBar;
    protected Rectangle wizard;

    protected boolean summonedGone = true;

    protected boolean storyMode;

    private static final ArrayList<Image> STORY_FRAME = new ArrayList<Image>(
            Arrays.asList(
                    new Image("resources/Story/Page1.png"),
                    new Image("resources/Story/Page2.png"),
                    new Image("resources/Story/Page3.png"),
                    new Image("resources/Story/Page4.png"),
                    new Image("resources/Story/Page5.png"),
                    new Image("resources/Story/Page7.png"),
                    new Image("resources/Story/Page8.png"),
                    new Image("resources/Story/Page9.png"),
                    new Image("resources/Story/Page10.png"),
                    new Image("resources/Story/Page11.png"),
                    new Image("resources/Story/Page12.png"),
                    new Image("resources/Story/Page13.png"),
                    new Image("resources/Story/Page14.png")

            )
    );



    public static final Font STANDARD_FONT =
            Font.font("OCR A Extended", FontWeight.EXTRA_BOLD, 15);


    public GameScreen(Player player, Floor floor, Room room, boolean storyMode) {
        this.player = player;
        this.floor = floor;
        this.room = room;
        this.storyMode = storyMode;
    }

    public GameScreen() {

    }

    /**
     * Creating background
     * @param stage window of the game
     */
    @SuppressWarnings("checkstyle:NoWhitespaceBefore")
    @Override
    public void start(Stage stage) {
        this.stage = stage;


        AtomicInteger j = new AtomicInteger();
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(650);
        rectangle.setWidth(900);
        StackPane storyStack = new StackPane();
        storyStack.getChildren().add(rectangle);
        Scene storyScene = new Scene(storyStack);
        VBox holdNextButtons = new VBox();
        stage.setScene(storyScene);
        stage.setHeight(650);
        stage.setWidth(900);
        stage.show();
        Button next = new Button("Next");
        Button skip = new Button("Skip");
        storyStack.setAlignment(Pos.CENTER);
        holdNextButtons.setAlignment(Pos.CENTER_RIGHT);
        holdNextButtons.getChildren().add(next);
        holdNextButtons.getChildren().add(skip);
        holdNextButtons.setSpacing(275);
        storyStack.getChildren().add(holdNextButtons);
        rectangle.setFill(new ImagePattern(STORY_FRAME.get(j.intValue())));
        next.setOnAction(e -> {
            j.incrementAndGet();
            rectangle.setFill(new ImagePattern(STORY_FRAME.get(j.intValue())));
            if (j.intValue() >= STORY_FRAME.size()) {
                storyMode = false;
                stage.close();
                start2(stage);
            }
        });

        skip.setOnAction(e -> {
            storyMode = false;
            stage.close();
            start2(stage);
        });
        if (!storyMode) {
            start2(stage);
        }
    }


    public void start2(Stage stage) {
        setUpStage(stage);

        //Creating stack pane to place movement pane on top of borderpane
        this.stack = new StackPane();
        setUpStack(stack);

        //creating black border
        Rectangle border = new Rectangle();
        setUpBorder(border);

        //creating background
        background = new Rectangle();
        setUpBackground(background);

        //creating border pane to hold walls & exits
        wallPane = new BorderPane();
        setUpWallPane(wallPane);

        //setting up screen set up to hold walls & exits
        screenSetUp = new BorderPane();
        setUpScreenSetUp(screenSetUp);

        //adding grid to stack
        movePane = new Pane();
        setUpMovePane(movePane);

        //Setting up exits
        //Creating exit buttons
        Button topExit = new Button("EXIT");
        Button bottomExit = new Button("EXIT");
        Button leftExit = new Button("EXIT");
        Button rightExit = new Button("EXIT");

        //creating boxes to hold buttons & money display
        HBox topBox = new HBox();
        HBox bottomBox = new HBox();
        VBox leftBox = new VBox();
        VBox rightBox = new VBox();
        VBox leftWallBox = new VBox();
        VBox rightWallBox = new VBox();

        //setting box alignment

        rightBox.setAlignment(Pos.CENTER);
        leftBox.setAlignment(Pos.CENTER);
        leftWallBox.setAlignment(Pos.CENTER);
        rightWallBox.setAlignment(Pos.CENTER);

        setUpTopExit(background, topBox, topExit);
        setUpBottomExit(background, bottomBox, bottomExit);
        setUpLeftExit(background, leftWallBox, leftBox, leftExit);
        setUpRightExit(background, rightWallBox, rightBox, rightExit);

        //adding boxes & image to BorderPanes
        screenSetUp.setTop(topBox);
        screenSetUp.setBottom(bottomBox);
        screenSetUp.setRight(rightBox);
        screenSetUp.setLeft(leftBox);
        wallPane.setRight(rightWallBox);
        wallPane.setLeft(leftWallBox);


        instantiateGameSequence();

        //setting up scene and displaying stage
        Scene gameScene = new Scene(stack);
        stage.setScene(gameScene);
        stage.show();


        if (room.equals(floor.getWizardRoom())) {
            wizard = new Rectangle();
            wizard.setFill(new ImagePattern(
                    new Image("resources/Wizard.png")
            ));
            wizard.setHeight(125);
            wizard.setWidth(125);
            movePane.getChildren().add(wizard);
            wizard.setX(550);
            wizard.setY(600);
        }

        Controller controller = new Controller(stage, player, floor, room);

        topExit.setOnAction((event) -> controller.clickedTopExit(summonedGone));
        bottomExit.setOnAction((event) -> controller.clickedBottomExit(summonedGone));
        leftExit.setOnAction((event) -> controller.clickedLeftExit(summonedGone));
        rightExit.setOnAction((event) -> controller.clickedRightExit(summonedGone));

        if (room.hasMonster() && !room.getMonster().isDead()) {

            monster = room.getMonster();
            monster.getSequenceMonster().setGameScreen(this);


            if (room.getName().equals(floor.getChallengeRoomOne().getName())
                    || room.getName().equals(floor.getChallengeRoomTwo().getName())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("You have reached a challenge room!");
                alert.setHeaderText("If you can defeat the challenge, "
                        + "you will be rewarded greatly");
                alert.setContentText("What would you like to do?");

                ButtonType buttonTypeOne = new ButtonType("Go Back");
                ButtonType buttonTypeTwo = new ButtonType("Fight!",
                        ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
                System.out.println(floor.getLastRoomVisited().getName());
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == buttonTypeOne){
                    Room last = floor.getLastRoomVisited();
                    last.setVisited();
                    if (last == room.getLeftRoom()) {
                        controller.clickedLeftExit(summonedGone);
                    } else if (last == room.getRightRoom()) {
                        controller.clickedRightExit(summonedGone);
                    } else if (last == room.getTopRoom()) {
                        controller.clickedTopExit(summonedGone);
                    } else {
                        controller.clickedBottomExit(summonedGone);
                    }
                } else {
                    alert.close();
                    instantiateAttackSequence();
                }
            } else {
                instantiateAttackSequence();
            }
        }


        //For testing purposes
        System.out.println("EndRoom: " + this.getFloor().getEndRoom().toString());
        System.out.println("Current Room: " + this.getRoom().getName());
        System.out.println("Challenge One: " + floor.getChallengeRoomOne().getName());
        System.out.println("Challenge Two: " + floor.getChallengeRoomTwo().getName());



    }

    public void setUpStage(Stage setStage) {
        setStage.setTitle("The game has begun!  Good luck");
        setStage.setHeight(900);
        setStage.setWidth(900);

        //config so can resize window
        setStage.setResizable(true);
    }

    public void setUpStack(StackPane setStack) {
        setStack.setMinHeight(setStack.getHeight());
        setStack.setMinWidth(setStack.getWidth());
    }

    public void setUpBorder(Rectangle border) {
        border.setFill(Color.BLACK);
        border.setHeight(900);
        border.setWidth(900);
        this.stack.getChildren().add(border);
        this.stack.setAlignment(Pos.CENTER);
    }

    public void setUpBackground(Rectangle background) {
        Image backgroundImage = new Image("resources/Background.jpg");
        background.setFill(new ImagePattern(backgroundImage));
        background.setHeight(room.getHorizontalLength());
        background.setWidth(room.getVerticalLength());
        this.stack.getChildren().add(background);
        this.stack.setAlignment(Pos.CENTER);
        holdExperience = new VBox();
        this.stack.getChildren().add(holdExperience);
    }


    public void setUpWallPane(BorderPane setWallPane) {
        setWallPane.setMinHeight(room.getVerticalLength());
        setWallPane.setMaxHeight(room.getVerticalLength());
        setWallPane.setMinWidth(room.getHorizontalLength());
        setWallPane.setMaxWidth(room.getHorizontalLength());
        this.stack.getChildren().add(setWallPane);
    }

    public void setUpScreenSetUp(BorderPane setScreenSetUp) {
        setScreenSetUp.setMinHeight(room.getVerticalLength());
        setScreenSetUp.setMaxHeight(room.getVerticalLength());
        setScreenSetUp.setMinWidth(room.getHorizontalLength());
        setScreenSetUp.setMaxWidth(room.getHorizontalLength());
        this.stack.getChildren().add(setScreenSetUp);

    }

    public void setUpMovePane(Pane setMovePane) {
        setMovePane.setMinHeight(room.getVerticalLength());
        setMovePane.setMinWidth(room.getHorizontalLength());
        this.stack.getChildren().add(setMovePane);
        setMovePane.setMouseTransparent(true);
    }

    public void setUpTopExit(Rectangle background, HBox topBox, Button topExit) {
        if (this.room.hasTopExit()) {
            ImageView topWall = new ImageView(new Image("resources/Layout/TopHalf.png"));
            ImageView bottomWall = new ImageView(new Image("resources/Layout/TopHalf.png"));
            topWall.setFitWidth((background.getWidth() - 125) / 2);
            topWall.setPreserveRatio(true);
            bottomWall.setFitWidth((background.getWidth() - 125) / 2);
            bottomWall.setPreserveRatio(true);
            topBox.getChildren().add(topWall);
            topBox.getChildren().add(topExit);
            topBox.getChildren().add(bottomWall);
            topBox.setSpacing(50);
        }
        topBox.setAlignment(Pos.CENTER);
    }

    public void setUpBottomExit(Rectangle background, HBox bottomBox, Button bottomExit) {
        if (this.room.hasBottomExit()) {
            ImageView topWall = new ImageView(new Image("resources/Layout/BottomHalfWall.png"));
            ImageView bottomWall = new ImageView(new Image("resources/Layout/BottomHalfWall.png"));
            topWall.setFitWidth((background.getWidth() - 125) / 2);
            topWall.setPreserveRatio(true);
            bottomWall.setFitWidth((background.getWidth() - 125) / 2);
            bottomWall.setPreserveRatio(true);
            bottomBox.getChildren().add(topWall);
            bottomBox.getChildren().add(bottomExit);
            bottomBox.getChildren().add(bottomWall);
            bottomBox.setSpacing(50);
        }
        bottomBox.setAlignment(Pos.CENTER);
    }

    public void setUpLeftExit(
            Rectangle background, VBox leftWallBox, VBox leftBox, Button leftExit) {
        if (room.hasLeftExit()) {
            ImageView topWall = new ImageView(new Image("resources/Layout/LeftHalfWall.png"));
            ImageView bottomWall = new ImageView(new Image("resources/Layout/LeftHalfWall.png"));
            topWall.setFitHeight((background.getWidth() - 125) / 2);
            topWall.setPreserveRatio(true);
            bottomWall.setFitHeight((background.getWidth() - 125) / 2);
            bottomWall.setPreserveRatio(true);
            leftWallBox.getChildren().add(topWall);
            leftBox.getChildren().add(leftExit);
            leftWallBox.getChildren().add(bottomWall);
            leftWallBox.setSpacing(125);
        }
    }

    public void setUpRightExit(Rectangle background,
                               VBox rightWallBox, VBox rightBox, Button rightExit) {
        if (room.hasRightExit()) {
            ImageView topWall = new ImageView(new Image("resources/Layout/RightHalfWall.png"));
            ImageView bottomWall = new ImageView(new Image("resources/Layout/RightHalfWall.png"));
            topWall.setFitHeight((background.getWidth() - 125) / 2);
            topWall.setPreserveRatio(true);
            bottomWall.setFitHeight((background.getWidth() - 125) / 2);
            bottomWall.setPreserveRatio(true);
            rightWallBox.getChildren().add(topWall);
            rightBox.getChildren().add(rightExit);
            rightWallBox.getChildren().add(bottomWall);
            rightWallBox.setSpacing(125);
        }
    }



    public void instantiateGameSequence() {
        //setting up texts of stats display
        Text playerHP = new Text(" HP:");
        Text playerStamina = new Text(" ST:");
        Text playerMana = new Text("  M:");
        playerMoney = new Text("  $ " + player.getMoney());
        minusMonsterHP = new Text("");
        plusExperience = new Text("");
        playerExperience = new Text("Level " + player.getLevel());
        secondsCountDown = new Text("");

        playerHP.setFont(STANDARD_FONT);
        playerHP.setFill(Color.WHITE);
        playerMana.setFont(STANDARD_FONT);
        playerMana.setFill(Color.WHITE);
        playerStamina.setFont(STANDARD_FONT);
        playerStamina.setFill(Color.WHITE);
        secondsCountDown.setFont(STANDARD_FONT);
        secondsCountDown.setFill(Color.RED);
        playerMoney.setFont(STANDARD_FONT);
        playerMoney.setFill(Color.WHITE);
        minusMonsterHP.setFont(STANDARD_FONT);
        plusExperience.setFont(STANDARD_FONT);
        plusExperience.setFill(Color.DEEPSKYBLUE);
        playerExperience.setFont(STANDARD_FONT);
        playerExperience.setFill(Color.WHITE);

        //creating HBoxes to hold stat text + bar
        HBox holdHPBar = new HBox();
        HBox holdStaminaBar = new HBox();
        HBox holdManaBar = new HBox();

        holdHPBar.getChildren().add(playerHP);
        playerHPBar = new Rectangle((((double) player.getHealth()
                / player.getMaxHealth()) * 110), 15);
        playerBorderHPBar = new Rectangle(110, 15);
        holdHPBar.getChildren().add(
                setUpStatBar(playerHPBar, playerBorderHPBar,
                        Color.MAROON, Color.CRIMSON));

        holdStaminaBar.getChildren().add(playerStamina);
        playerStaminaBar = new Rectangle(((player.getStamina()
                / player.getMaxStamina()) * 110), 15);
        playerBorderStaminaBar = new Rectangle(110, 15);
        holdStaminaBar.getChildren().add(
                setUpStatBar(playerStaminaBar, playerBorderStaminaBar,
                        Color.FORESTGREEN, Color.LIMEGREEN));

        holdManaBar.getChildren().add(playerMana);
        playerManaBar = new Rectangle(((player.getMana()
                / player.getMaxMana()) * 110), 15);
        playerBorderManaBar = new Rectangle(110, 15);
        holdManaBar.getChildren().add(
                setUpStatBar(playerManaBar, playerBorderManaBar,
                        Color.MIDNIGHTBLUE, Color.DODGERBLUE));
        holdExperience.getChildren().add(playerExperience);
        holdExperience.setAlignment(Pos.TOP_CENTER);

        holdExperience.setSpacing(5);

        experienceBar = new Rectangle((((double) player.getExperience()
                - ((double) player.getLevel() * EXPERIENCE_PER_LEVEL))
                / EXPERIENCE_PER_LEVEL) * 425, 15);
        experienceBorderBar = new Rectangle(425, 15);
        holdExperience.getChildren().add(
                setUpExperienceBar(experienceBar, experienceBorderBar,
                        Color.INDIGO, Color.DARKORCHID));
        experienceBar.setX(experienceBorderBar.getX());


        //adding HP & money display to screen
        holdStats = new VBox();
        holdStats.getChildren().add(holdHPBar);
        holdStats.getChildren().add(holdStaminaBar);
        holdStats.getChildren().add(holdManaBar);
        holdStats.getChildren().add(playerMoney);
        holdStats.getChildren().add(secondsCountDown);
        holdStats.setAlignment(Pos.TOP_LEFT);
        holdStats.setSpacing(2);
        screenSetUp.setCenter(holdStats);


        if (room.hasMerchant()) {
            Image merchantImage = new Image("resources/Merchant.png");
            merchantIcon = new Rectangle(100, 100);
            merchantIcon.setFill(new ImagePattern(merchantImage));
            movePane.getChildren().add(merchantIcon);
            merchantIcon.setX(550);
            merchantIcon.setY(125);
        }

        //setting up character icon
        characterIcon = setUpIcon(player.getImage(), 150, 150, 390, 240);
        movePane.getChildren().add(characterIcon);
        movePane.getChildren().add(minusMonsterHP);
        movePane.getChildren().add(plusExperience);

        //setting up weapon icon
        if (player.getEquippedWeapon() != null) {
            weaponIcon = new ImageView(player.getEquippedWeapon().getItemIcon());
        } else {
            weaponIcon = new ImageView();
            weaponIcon.setImage(null);
        }

        weaponIcon.setFitWidth(100);
        weaponIcon.setFitHeight(100);
        movePane.getChildren().add(weaponIcon);

        weaponIcon.setRotationAxis(Rotate.Y_AXIS);

        player.getSequencePlayer().setGameScreen(this);
        player.getSequencePlayer().startGameSequence();

    }

    public void instantiateAttackSequence() {
        if (room.hasMonster() && !room.getMonster().isDead()) {

            //setting up text displays
            monsterHP = new Text("HP: " + monster.getHealth());
            minusPlayerHP = new Text("-" + monster.getAttackDamage());
            monsterName = new Text(monster.getName());

            monsterHP.setFont(STANDARD_FONT);
            minusPlayerHP.setFont(STANDARD_FONT);
            monsterName.setFont(STANDARD_FONT);

            minusPlayerHP.setFill(Color.RED);
            monsterName.setFill(Color.WHITE);

            //setting up gradient
            Stop[] stops = new Stop[] {
                new Stop(0, Color.FORESTGREEN), new Stop(1,  Color.LIMEGREEN)
            };
            LinearGradient linGrad =
                    new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops);


            //creating border of monster HP bar
            monsterBorderHPBar = new Rectangle(80, 10);
            monsterBorderHPBar.setFill(Color.TRANSPARENT);
            monsterBorderHPBar.setStroke(Color.BLACK);
            monsterBorderHPBar.setArcHeight(10);
            monsterBorderHPBar.setArcWidth(10);

            //setting up HP bar
            monsterHPBar = new Rectangle(80, 10);
            monsterHPBar.setFill(linGrad);
            monsterHPBar.setArcHeight(10);
            monsterHPBar.setArcWidth(10);


            movePane.getChildren().add(monsterHPBar);
            movePane.getChildren().add(monsterBorderHPBar);

            //setting x & y coordinates of HPBar
            monsterBorderHPBar.setX(monsterHPBar.getX());
            monsterBorderHPBar.setY(monsterHPBar.getY());

            //setting up monster Icon & adding to move pane
            double monsterIconHeight = monster.getSequenceMonster().getImageView().getFitHeight();
            double monsterIconWidth = monster.getSequenceMonster().getImageView().getFitWidth();
            monsterIcon = setUpIcon(monster.getSequenceMonster().getImage(),
                    monsterIconWidth, monsterIconHeight, 500, 240);

            movePane.getChildren().add(monsterIcon);
            movePane.getChildren().add(minusPlayerHP);
            movePane.getChildren().add(monsterName);
            movePane.getChildren().add(monsterHP);

            monster.getSequenceMonster().startAttackSequence();
        }
    }
    public StackPane setUpStatBar(Rectangle statBar, Rectangle borderBar,
                                  Color statBarColor1, Color statBarColor2) {
        StackPane stackPane = new StackPane();

        //setting up gradient
        Stop[] stops = new Stop[] {
            new Stop(0, statBarColor1), new Stop(1, statBarColor2)
        };
        LinearGradient linGrad = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops);

        //creating border of HP bar
        borderBar.setFill(Color.TRANSPARENT);
        borderBar.setStroke(Color.BLACK);
        borderBar.setArcHeight(10);
        borderBar.setArcWidth(10);

        //setting up HP bar
        statBar.setFill(linGrad);
        statBar.setArcHeight(10);
        statBar.setArcWidth(10);

        //adding border & HP bar to stack pane
        stackPane.getChildren().add(statBar);
        stackPane.setAlignment(Pos.CENTER_LEFT);
        stackPane.getChildren().add(borderBar);
        stackPane.setAlignment(Pos.CENTER_LEFT);

        //setting x & y coordinates of HPBar
        borderBar.setX(statBar.getX());
        borderBar.setY(statBar.getY());

        return stackPane;
    }

    public StackPane setUpExperienceBar(Rectangle statBar, Rectangle borderBar,
                                        Color statBarColor1, Color statBarColor2) {
        StackPane stackPane = new StackPane();

        //setting up gradient
        Stop[] stops = new Stop[] {
            new Stop(0, statBarColor1), new Stop(1, statBarColor2)
        };
        LinearGradient linGrad = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops);

        //creating border of HP bar
        borderBar.setFill(Color.TRANSPARENT);
        borderBar.setStroke(Color.WHITE);
        borderBar.setArcHeight(10);
        borderBar.setArcWidth(10);

        //setting up HP bar
        statBar.setFill(linGrad);
        statBar.setArcHeight(10);
        statBar.setArcWidth(10);

        //adding border & HP bar to stack pane
        stackPane.getChildren().add(statBar);
        stackPane.setAlignment(Pos.BOTTOM_LEFT);
        stackPane.getChildren().add(borderBar);
        stackPane.setAlignment(Pos.BOTTOM_CENTER);

        //setting x & y coordinates of HPBar
        borderBar.setX(statBar.getX());
        borderBar.setY(statBar.getY());

        return stackPane;
    }

    public static ImageView setUpIcon(Image icon, double width, double height, int x, int y) {
        ImageView characterIcon = new ImageView();
        characterIcon.setImage(icon);
        characterIcon.setFitWidth(width);
        characterIcon.setFitHeight(height);
        characterIcon.setPreserveRatio(true);
        characterIcon.setSmooth(true);
        characterIcon.setX(x);
        characterIcon.setY(y);
        return characterIcon;
    }

    public Player getPlayer() {
        return player;
    }

    public Room getRoom() {
        return room;
    }

    public Floor getFloor() {
        return floor;
    }

    public Monster getMonster() {
        return monster;
    }

    public static void setStaticPlayer(Player player) {
        staticPlayer = player;
    }

    public void setSummonedGone(boolean summonedGone) { this.summonedGone = summonedGone; }
}