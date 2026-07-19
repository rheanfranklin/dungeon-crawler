package game;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import win.Winscreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SequencePlayer extends GameScreen {

    private GameScreen gameScreen;

    private int lastAttack;

    private static final ArrayList<Image> STATIONARY_FRAMES = new ArrayList<Image>(
            Arrays.asList(
                    new Image("resources/Char1Image.png"),
                    new Image("resources/Char1Image2.png")
            )
    );

    public static final ArrayList<Image> ATTACK_SEQUENCES = new ArrayList<Image>(
            Arrays.asList(
                    new Image("resources/Char1AttackF1.png"),
                    new Image("resources/Char1AttactF2.png")
            )
    );

    private enum KeyPress {
        LEFT,
        RIGHT
    }

    private KeyPress lastKeyPressed = KeyPress.LEFT;

    private boolean inventoryStatus = false;

    private boolean merchantStatus = false;

    private int frameCounter = 0;

    private int lowStaminaCounter = 200;

    private int giveExperienceCounter = 200;

    private Text lowStaminaText;


    private int increaseAttackCounter = 0;

    private int increaseAttack = 0;

    private boolean increasedAttack = false;

    private int seconds = 0;


    private Rectangle dropItemRectangle;

    private Rectangle characterRectangle;

    private Item dropItem;

    private boolean pickedUpItem = false;

    private boolean itemDropped;

    private boolean poisoned;

    private int poisonedSeconds;

    private int poisonCounter;

    private Text poisonText;

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        lastAttack = 0;
        itemDropped = false;
    }

    public void startGameSequence() {
        Controller controller = new Controller(
                gameScreen.stage, gameScreen.player, gameScreen.floor, gameScreen.room);
        gameScreen.characterIcon.setRotationAxis(Rotate.Y_AXIS);

        characterRectangle = new Rectangle(gameScreen.characterIcon.getFitWidth(),
                gameScreen.characterIcon.getFitWidth());
        characterRectangle.setFill(Color.TRANSPARENT);
        gameScreen.movePane.getChildren().add(characterRectangle);
        characterRectangle.setX(gameScreen.characterIcon.getX());
        characterRectangle.setY(gameScreen.characterIcon.getY());

        //enabling player to move around screen
        gameScreen.stack.setOnKeyPressed(keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (code.equals(KeyCode.P) && keyEvent.isShiftDown()) {
                gameScreen.movePane.getChildren().remove(gameScreen.monsterIcon);
                gameScreen.monster.kill();
            } else if (code.equals(KeyCode.E) && !inventoryStatus) {
                controller.openInventoryWindow(gameScreen);
                inventoryStatus = true;
            } else if (code.equals(KeyCode.E) && inventoryStatus) {
                controller.closeInventoryWindow(gameScreen);
                inventoryStatus = false;
            } else if (code.equals(KeyCode.Q) && !merchantStatus && gameScreen.room.hasMerchant()
                    && characterRectangle.getBoundsInParent().intersects(gameScreen.merchantIcon.getBoundsInParent())) {
                controller.openMerchantWindow(gameScreen);
                merchantStatus = true;
            } else if (code.equals(KeyCode.Q) && merchantStatus) {
                controller.closeMerchantWindow(gameScreen);
                merchantStatus = false;
            } else if (code.isArrowKey() || code.isLetterKey()) {
                if (code == KeyCode.UP || code == KeyCode.W) {
                    controller.pressedUpKey(gameScreen);
                } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                    controller.pressedDownKey(gameScreen);
                } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                    controller.pressedLeftKey(gameScreen);
                    lastKeyPressed = KeyPress.LEFT;
                } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                    controller.pressedRightKey(gameScreen);
                    lastKeyPressed = KeyPress.RIGHT;
                }
            }
        });

        lowStaminaText = new Text("");
        lowStaminaText.setFont(GameScreen.STANDARD_FONT);
        lowStaminaText.setFill(Color.RED);
        gameScreen.movePane.getChildren().add(lowStaminaText);

        poisonText = new Text("");
        poisonText.setFont(GameScreen.STANDARD_FONT);
        poisonText.setFill(Color.RED);
        gameScreen.movePane.getChildren().add(poisonText);

        gameLoop.start();
    }

    private final AnimationTimer gameLoop = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (gameScreen.wizard != null && characterRectangle.getBoundsInParent().intersects(
                    gameScreen.wizard.getBoundsInParent())) {
                initiateWinScreen();
                this.stop();
            }
            if (frameCounter > 60) {
                frameCounter = 0;
            } else {
                ++frameCounter;
            }
            ++lastAttack;
            ++lowStaminaCounter;
            ++giveExperienceCounter;

            if (gameScreen.player.getExperience()
                    - (gameScreen.player.getLevel() * gameScreen.EXPERIENCE_PER_LEVEL) >= 100) {
                gameScreen.player.setLevel(gameScreen.player.getLevel() + 1);
                gameScreen.player.setIncreasePoints(gameScreen.player.getIncreasePoints() + 4);
            }

            gameScreen.playerExperience.setText("Level " + gameScreen.player.getLevel());


            if (poisonCounter > 0) {
                --poisonCounter;
                if (poisonCounter % 50  == 0) {
                    gameScreen.player.setHealth(gameScreen.player.getHealth() - 3);
                }
                if (poisonCounter % 60 == 0) {
                    --poisonedSeconds;
                }
                poisonText.setText("Poisoned: " + poisonedSeconds);
            } else {
                poisonText.setText("");
            }

            if (seconds > 0) {
                --increaseAttackCounter;
                if (!increasedAttack) {
                    gameScreen.player.setAttackDamage(
                            gameScreen.player.getAttackDamage() + increaseAttack);
                    increasedAttack = true;
                }
                if (increaseAttackCounter % 60 == 0) {
                    --seconds;
                }
                gameScreen.secondsCountDown.setText("    " + seconds);

            } else {
                if (increasedAttack) {
                    gameScreen.player.setAttackDamage(
                            gameScreen.player.getAttackDamage() - increaseAttack);
                    increasedAttack = false;
                }
                gameScreen.secondsCountDown.setText("");
                increaseAttack = 0;
            }


            if (gameScreen.getPlayer().getStamina() < gameScreen.getPlayer().getMaxStamina()) {
                gameScreen.getPlayer().setStamina(gameScreen.player.getStamina()
                        + gameScreen.getPlayer().getStaminaReplenishSpeed());
            }
            gameScreen.playerStaminaBar.setWidth(
                    gameScreen.playerBorderStaminaBar.getWidth()
                            * (gameScreen.player.getStamina()
                            / gameScreen.player.getMaxStamina())
            );

            gameScreen.playerHPBar.setWidth(
                    ((double) gameScreen.player.getHealth()
                            / gameScreen.player.getMaxHealth())
                            * gameScreen.playerBorderHPBar.getWidth()
            );

            gameScreen.experienceBar.setWidth(((double) gameScreen.player.getExperience()
                    - ((double) gameScreen.player.getLevel() * EXPERIENCE_PER_LEVEL))
                    / EXPERIENCE_PER_LEVEL
                            * gameScreen.experienceBorderBar.getWidth()
            );

            if (itemDropped && !pickedUpItem) {
                if (characterRectangle.getBoundsInParent().intersects(
                        dropItemRectangle.getBoundsInParent())) {
                    gameScreen.movePane.getChildren().remove(dropItemRectangle);
                    pickedUpItem = true;
                    if (dropItem != null) {
                        gameScreen.player.getInventory().add(dropItem);
                    }
                }
            }
            gameScreen.playerMoney.setText("  $ " + gameScreen.player.getMoney());
            lowStaminaText.setX(gameScreen.characterIcon.getX());
            lowStaminaText.setY(gameScreen.characterIcon.getY() - 10);
            characterRectangle.setX(gameScreen.characterIcon.getX());
            characterRectangle.setY(gameScreen.characterIcon.getY());
            poisonText.setX(gameScreen.characterIcon.getX());
            poisonText.setY(gameScreen.characterIcon.getY());
            if (gameScreen.player.getEquippedWeapon() != null) {
                gameScreen.weaponIcon.setImage(gameScreen.player.getEquippedWeapon().getItemIcon());
                if (frameCounter < 30) {
                    gameScreen.characterIcon.setImage(STATIONARY_FRAMES.get(0));
                    gameScreen.weaponIcon.setImage(
                            gameScreen.player.getEquippedWeapon().getItemIcon());
                    gameScreen.weaponIcon.setY(gameScreen.characterIcon.getY() + 15);
                } else {
                    gameScreen.characterIcon.setImage(STATIONARY_FRAMES.get(1));
                    gameScreen.weaponIcon.setY(gameScreen.characterIcon.getY() + 13);
                }
                if (lastKeyPressed.equals(KeyPress.LEFT)) {
                    gameScreen.characterIcon.setRotate(180);
                    gameScreen.weaponIcon.setRotate(180);
                } else {
                    gameScreen.characterIcon.setRotate(0);
                    gameScreen.weaponIcon.setRotate(0);
                }
            } else {
                gameScreen.weaponIcon.setImage(null);
            }

            gameScreen.stack.setOnMouseClicked(e -> {
                if (gameScreen.room.hasMonster()
                        && !gameScreen.room.getMonster().isDead()) {
                    setOnMouseClicked();
                }
            });
            if (lastAttack > 20) {
                gameScreen.minusMonsterHP.setText("");
            } else {
                gameScreen.minusMonsterHP.setText("-"
                        + gameScreen.player.getAttackDamage());
            }

            if (lowStaminaCounter < 30) {
                lowStaminaText.setText("You don't have enough stamina!");
            } else {
                lowStaminaText.setText("");
            }

            if (giveExperienceCounter < 50) {
                gameScreen.plusExperience.setText("+ "
                        + gameScreen.monster.getReturnExperience() + " experience!");
            } else {
                gameScreen.plusExperience.setText("");
            }
            if (gameScreen.player.getEquippedWeapon() != null) {
                if (lastKeyPressed.equals(KeyPress.LEFT)) {
                    gameScreen.weaponIcon.setX(gameScreen.characterIcon.getX() - 32);
                } else {
                    gameScreen.weaponIcon.setX(gameScreen.characterIcon.getX() + 80);
                }
            }
            if (gameScreen.player.getHealth() <= 0) {
                initiateLoseScreen();
                this.stop();
            }

            if (gameScreen.room.hasMonster() && !gameScreen.room.getMonster().isDead()) {
                if (gameScreen.monster.getName().equals("Succubus") && !Music.getSuccubus()) {
                    Music.setSuccubusMusic();
                } else if (gameScreen.monster.getName().equals("Justin Bieber") && !Music.getJustin()) {
                    Music.playJustin();
                } else if ((Music.getSuccubus() && !gameScreen.monster.getName().equals("Succubus"))
                        || (!gameScreen.monster.getName().equals("Justin Bieber") && Music.getJustin())) {
                    Music.setRandomSound();
                }
            }

            if(gameScreen.room.hasMonster() && gameScreen.room.getMonster().isDead()
            && gameScreen.room.getMonster().getName().equals("Justin Bieber")
            && Music.getJustin()) {
                Music.setRandomSound();
            }
        }
    };

    public void setOnMouseClicked() {
        double deltaX = gameScreen.characterIcon.getX() - gameScreen.monsterIcon.getX();
        double deltaY = gameScreen.characterIcon.getY() - gameScreen.monsterIcon.getY();
        if (deltaY < 100 && deltaY > -100 && deltaX < 100 && deltaX > -100) {
            if (gameScreen.player.getStamina()
                    - gameScreen.player.getConsumeStamina()
                    > 0) {
                gameScreen.monster.takeDamage(gameScreen.player);
                lastAttack = 0;
                gameScreen.minusMonsterHP.setText("-" + gameScreen.player.getAttackDamage());
                gameScreen.minusMonsterHP.setFont(GameScreen.STANDARD_FONT);
                gameScreen.minusMonsterHP.setFill(Color.LIMEGREEN);
                gameScreen.minusMonsterHP.setX(gameScreen.monsterIcon.getX() - 50);
                gameScreen.minusMonsterHP.setY(gameScreen.monsterIcon.getY() + 20);
                gameScreen.player.setStamina(
                        gameScreen.player.getStamina() - gameScreen.player.getConsumeStamina());
                lastAttack = 0;
            } else {
                lowStaminaCounter = 0;
            }

            if (gameScreen.monster.isDead() && !gameScreen.monster.getGaveExperience()) {
                gameScreen.plusExperience.setX(gameScreen.characterIcon.getX());
                gameScreen.plusExperience.setY(gameScreen.characterIcon.getY());
                giveExperienceCounter =  0;
                gameScreen.player.setExperience(gameScreen.player.getExperience()
                        + gameScreen.monster.getReturnExperience());
                gameScreen.monster.giveExperience(gameScreen.player);
                gameScreen.monster.getSequenceMonster().setKilled();
                gameScreen.movePane.getChildren().remove(gameScreen.monsterName);

                dropItem = gameScreen.monster.generateDropItem();

                dropItemRectangle = new Rectangle();
                dropItemRectangle.setWidth(50);
                dropItemRectangle.setHeight(50);
                dropItemRectangle.setFill(new ImagePattern(dropItem.getItemIcon()));
                gameScreen.movePane.getChildren().add(dropItemRectangle);
                dropItemRectangle.setX(gameScreen.characterIcon.getX());
                dropItemRectangle.setY(gameScreen.characterIcon.getY()
                        + gameScreen.characterIcon.getFitHeight() + 20);

                itemDropped = true;

                pickedUpItem = false;

                gameScreen.movePane.getChildren().remove(gameScreen.monsterIcon);
            }
        }
    }

    public void initiateLoseScreen() {
        Winscreen winscreen = new Winscreen(gameScreen.getPlayer());
        try {
            winscreen.start(gameScreen.stage);
        } catch (Exception e) {

        }
    }

    public void setAttackCounter(int timer, int increaseAttack) {
        increaseAttackCounter = timer;
        seconds = increaseAttackCounter / 60;
        this.increaseAttack = increaseAttack;
    }

    public void initiateWinScreen() {
        Winscreen win = new Winscreen(gameScreen.player);
        try {
            win.start(gameScreen.stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGiveExperienceCounter(int giveExperienceCounter) {
        this.giveExperienceCounter = giveExperienceCounter;
    }

    public void setPoisonCounter() {
        poisonCounter = 600;
        poisonedSeconds = 10;
    }


}