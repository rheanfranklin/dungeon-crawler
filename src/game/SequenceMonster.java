package game;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class SequenceMonster extends GameScreen {

    protected static final double MONSTER_ATTACK_DISTANCE = 50;

    protected static final double MONSTER_SPEED = 0.50;

    protected static final double MONSTER_COOL_DOWN = 50;

    protected static final double DEFAULT_POISON_TIME_LENGTH = 100;

    protected int frameCounter = 0;

    protected int lastAttack = 0;

    protected ImageView stillMonsterImage;

    protected GameScreen gameScreen;

    protected int poisonCounter = 0;

    protected int poisonTimeLength;

    protected boolean killed;

    protected enum State {
        NORMAL,
        POISONED,
        BLIND,
        FROZEN
    }

    protected State state;

    public SequenceMonster(ImageView monsterImage) {
        stillMonsterImage = monsterImage;
        state = State.NORMAL;
        killed = false;
    }

    public SequenceMonster() {
        state = State.NORMAL;
        killed = false;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void startAttackSequence() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameScreen.monster != null && !gameScreen.monster.isDead()) {
                    if (state.equals(State.NORMAL) || state.equals(State.POISONED)) {
                        //calculating distance between monster & player
                        double deltaX = gameScreen.monsterIcon.getX()
                                - gameScreen.characterIcon.getX();
                        double deltaY = gameScreen.monsterIcon.getY()
                                - gameScreen.characterIcon.getY();

                        //setting up Monster HP & name display
                        gameScreen.monsterHPBar.setWidth(
                                gameScreen.monsterBorderHPBar.getWidth()
                                        * ((double) gameScreen.monster.getHealth()
                                        / gameScreen.monster.getMaxHealth())
                        );
                        gameScreen.monsterName.setText(gameScreen.monster.getName());

                        //used to determine which way the monster is facing (towards player)
                        boolean facingLeft;
                        facingLeft = deltaX > 0;

                        //setting rotation axis for mirroring monster image
                        //for whenever it changes left/right directions
                        gameScreen.monsterIcon.setRotationAxis(Rotate.Y_AXIS);

                        //rotates the monster image 180 degrees
                        //depending on which direction it is facing
                        if (!facingLeft) {
                            gameScreen.monsterIcon.setRotate(180);
                        } else {
                            gameScreen.monsterIcon.setRotate(0);
                        }

                        //Telling monster to move toward player if it is outside of
                        //its MONSTER_ATTACK_DISTANCE range
                        //else stay in same place
                        if (deltaX < MONSTER_ATTACK_DISTANCE && deltaX > -MONSTER_ATTACK_DISTANCE) {
                            gameScreen.monsterIcon.setX(gameScreen.monsterIcon.getX());
                        } else if (deltaX < 0) {
                            gameScreen.monsterIcon.setX(
                                    gameScreen.monsterIcon.getX() + MONSTER_SPEED);
                        } else {
                            gameScreen.monsterIcon.setX(
                                    gameScreen.monsterIcon.getX() - MONSTER_SPEED);
                        }

                        if (deltaY < MONSTER_ATTACK_DISTANCE && deltaY > -MONSTER_ATTACK_DISTANCE) {
                            gameScreen.monsterIcon.setY(gameScreen.monsterIcon.getY());
                        } else if (deltaY < 0) {
                            gameScreen.monsterIcon.setY(
                                    gameScreen.monsterIcon.getY() + MONSTER_SPEED);
                        } else {
                            gameScreen.monsterIcon.setY(
                                    gameScreen.monsterIcon.getY() - MONSTER_SPEED);
                        }

                        //if monster is in range of the player to attack
                        //then initiate attack sequence
                        if (deltaY < MONSTER_ATTACK_DISTANCE
                                && deltaY > -MONSTER_ATTACK_DISTANCE
                                && deltaX < MONSTER_ATTACK_DISTANCE
                                && deltaX > -MONSTER_ATTACK_DISTANCE) {
                            //increment frame counter to time the frame shifting and attack
                            ++frameCounter;
                            if (frameCounter - lastAttack > MONSTER_COOL_DOWN) {
                                gameScreen.monster.attack(gameScreen.player);
                                lastAttack = frameCounter;
                                gameScreen.playerHPBar.setWidth(
                                        gameScreen.playerBorderHPBar.getWidth()
                                                * ((double) gameScreen.player.getHealth()
                                                / gameScreen.player.getMaxHealth())
                                );
                                gameScreen.minusPlayerHP.setText(
                                        "-" + gameScreen.monster.getAttackDamage());
                                gameScreen.minusPlayerHP.setX(gameScreen.monsterIcon.getX());
                                gameScreen.minusPlayerHP.setY(gameScreen.monsterIcon.getY());
                            }
                            if (frameCounter - lastAttack < 10) {
                                if (facingLeft) {
                                    gameScreen.monsterIcon.setRotate(180);
                                } else {
                                    gameScreen.monsterIcon.setRotate(0);
                                }
                            }
                            if (frameCounter - lastAttack > MONSTER_COOL_DOWN - 20) {
                                gameScreen.minusPlayerHP.setText("");
                            }
                        }

                        //setting the x & y coordinates of monster name & HP display
                        gameScreen.monsterName.setX(
                                gameScreen.monsterIcon.getX()
                                        + (gameScreen.monsterIcon.getFitWidth() / 2) - 10);
                        gameScreen.monsterName.setY(gameScreen.monsterIcon.getY() - 10);
                        gameScreen.monsterHPBar.setX(gameScreen.monsterIcon.getX()
                                + (gameScreen.monsterIcon.getFitWidth() / 2) - 30);
                        gameScreen.monsterHPBar.setY(gameScreen.monsterName.getY() + 10);
                        gameScreen.monsterBorderHPBar.setX(gameScreen.monsterHPBar.getX());
                        gameScreen.monsterBorderHPBar.setY(gameScreen.monsterHPBar.getY());

                    }
                } else {
                    //if monster is dead, then remove hp & name display
                    gameScreen.movePane.getChildren().remove(gameScreen.monsterBorderHPBar);
                    gameScreen.movePane.getChildren().remove(gameScreen.monsterHPBar);
                    gameScreen.movePane.getChildren().remove(gameScreen.monsterIcon);
                    gameScreen.monsterName.setText("");
                    gameScreen.minusPlayerHP.setText("");
                    gameScreen.minusMonsterHP.setText("");
                }
            }
        };
        gameLoop.start();

        if (killed) {
            gameLoop.stop();
        }
    }

    public State getState() {
        return state;
    }

    public void setPoison(StatePoison statePoison) {
        this.state = State.POISONED;
    }

    public Image getImage() {
        return stillMonsterImage.getImage();
    }

    /**
     * Generates an image view to give to the super constructor
     *
     * @param string the string location of the image
     * @return image view made with string location
     */
    protected static ImageView generateImageView(String string) {
        ImageView imageView = new ImageView(new Image(string));
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        return imageView;
    }

    protected static ImageView generateImageView(String string, double height, double width) {
        ImageView imageView = new ImageView(new Image(string));
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }

    public ImageView getImageView() {
        return stillMonsterImage;
    }


    public void setKilled() {
        killed = true;
    }

    public boolean getKilled() {
        return killed;
    }


}