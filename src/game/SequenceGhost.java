package game;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.Arrays;

public class SequenceGhost extends SequenceMonster {

    private static final int GHOST_ATTACK_DISTANCE = 50;

    /**
     * An array list that holds all the frames
     * of the ghost's attack animation
     */
    private static final ArrayList<Image> ATTACK_SEQUENCE = new ArrayList<Image>(
            Arrays.asList(
                    new Image("resources/Monsters/Ghost/GhostAttackF1.png"),
                    new Image("resources/Monsters/Ghost/GhostAttackF2.png")
            )
    );

    /**
     * Counts the number of frames the Animation Timer
     * has iterated through to determine
     * which frame of the ghost to display on the game screen
     */
    private int frameCounter = 0;

    /**
     * Constructor of the ghost
     */
    public SequenceGhost() {
        super(generateImageView("resources/Monsters/Ghost/GhostStatic.png"));
    }

    /**
     * Create an animation of the ghost attacking
     * and chasing the player
     */
    public void startAttackSequence() {
        AnimationTimer attackLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //initiate attack sequence so long as monster is not dead
                if (gameScreen.monster != null && !gameScreen.monster.isDead()) {
                    //calculating distance between monster & player
                    double deltaX = gameScreen.monsterIcon.getX() - gameScreen.characterIcon.getX();
                    double deltaY = gameScreen.monsterIcon.getY() - gameScreen.characterIcon.getY();
                    //determining which way the monster is facing
                    boolean facingLeft = deltaX > 0;
                    //setting axis of rotation
                    gameScreen.monsterIcon.setRotationAxis(Rotate.Y_AXIS);
                    //setting monster icon to still image
                    gameScreen.monsterIcon.setImage(stillMonsterImage.getImage());
                    //rotate monster based on which way it is facing
                    if (!facingLeft) {
                        gameScreen.monsterIcon.setRotate(180);
                    } else {
                        gameScreen.monsterIcon.setRotate(0);
                    }
                    //setting up font of -HP
                    //minus player hp displays the HP that was subtracted from player
                    gameScreen.minusPlayerHP.setFont(GameScreen.STANDARD_FONT);
                    gameScreen.minusPlayerHP.setFill(Color.RED);
                    //if monster is within attack distance then sit still//else move toward player
                    if (deltaX < GHOST_ATTACK_DISTANCE && deltaX > -GHOST_ATTACK_DISTANCE) {
                        gameScreen.monsterIcon.setX(gameScreen.monsterIcon.getX());
                    } else if (deltaX < 0) {
                        gameScreen.monsterIcon.setX(gameScreen.monsterIcon.getX() + MONSTER_SPEED);
                    } else {
                        gameScreen.monsterIcon.setX(gameScreen.monsterIcon.getX() - MONSTER_SPEED);
                    }
                    if (deltaY < GHOST_ATTACK_DISTANCE && deltaY > -GHOST_ATTACK_DISTANCE) {
                        gameScreen.monsterIcon.setY(gameScreen.monsterIcon.getY());
                    } else if (deltaY < 0) {
                        gameScreen.monsterIcon.setY(gameScreen.monsterIcon.getY() + MONSTER_SPEED);
                    } else {
                        gameScreen.monsterIcon.setY(gameScreen.monsterIcon.getY() - MONSTER_SPEED);
                    }
                    //setting coordinates of monster name & minus HP display
                    gameScreen.monsterName.setX(gameScreen.monsterIcon.getX()
                                    + (gameScreen.monsterIcon.getFitWidth() / 2) - 10);
                    gameScreen.monsterName.setY(gameScreen.monsterIcon.getY() - 10);
                    gameScreen.monsterHPBar.setWidth(gameScreen.monsterBorderHPBar.getWidth()
                                    * ((double) gameScreen.monster.getHealth()
                                    / gameScreen.monster.getMaxHealth()));
                    gameScreen.monsterHPBar.setY(gameScreen.monsterName.getY() - 15);
                    //if monster is within attacking range, then initiate attacking sequence
                    if (deltaY < GHOST_ATTACK_DISTANCE && deltaY > -GHOST_ATTACK_DISTANCE
                            && deltaX < GHOST_ATTACK_DISTANCE && deltaX > -GHOST_ATTACK_DISTANCE) {

                        //the cycle of an attack sequence is 150,
                        //so reset frame counter once it reaches that point
                        if (frameCounter >= 150) {
                            frameCounter = 0;
                        } else {
                            ++frameCounter;
                        }
                        //determines which attack frame to display depending on//the frame counter
                        if (frameCounter > 50 && frameCounter <= 55) {
                            gameScreen.monsterIcon.setImage(ATTACK_SEQUENCE.get(0));
                            if (!facingLeft) {
                                gameScreen.monsterIcon.setRotate(180);
                            } else {
                                gameScreen.monsterIcon.setRotate(0);
                            }
                        }
                        if (frameCounter > 55 && frameCounter <= 60) {
                            gameScreen.monsterIcon.setImage(ATTACK_SEQUENCE.get(1));
                            if (!facingLeft) {
                                gameScreen.monsterIcon.setRotate(180);
                            } else {
                                gameScreen.monsterIcon.setRotate(0);
                            }
                        }
                        if (frameCounter > 60 && frameCounter <= 65) {
                            gameScreen.monsterIcon.setImage(ATTACK_SEQUENCE.get(0));
                            if (!facingLeft) {
                                gameScreen.monsterIcon.setRotate(180);
                            } else {
                                gameScreen.monsterIcon.setRotate(0);
                            }
                        }
                        if (frameCounter > 65 && frameCounter <= 70) {
                            gameScreen.monsterIcon.setImage(ATTACK_SEQUENCE.get(1));
                            if (!facingLeft) {
                                gameScreen.monsterIcon.setRotate(180);
                            } else {
                                gameScreen.monsterIcon.setRotate(0);
                            }
                        }
                        if (frameCounter > 70 && frameCounter <= 75) {
                            gameScreen.monsterIcon.setImage(ATTACK_SEQUENCE.get(0));
                            if (!facingLeft) {
                                gameScreen.monsterIcon.setRotate(180);
                            } else {
                                gameScreen.monsterIcon.setRotate(0);
                            }
                        }
                        if (frameCounter > 75 && frameCounter <= 80) {
                            gameScreen.monsterIcon.setImage(ATTACK_SEQUENCE.get(1));
                            if (!facingLeft) {
                                gameScreen.monsterIcon.setRotate(180);
                            } else {
                                gameScreen.monsterIcon.setRotate(0);
                            }
                        }
                        //tells the monster to attack at every point the attack frame is changed
                        if (frameCounter == 75 || frameCounter == 70 || frameCounter == 65
                                || frameCounter == 60 || frameCounter == 55 || frameCounter == 50) {
                            gameScreen.monster.attack(gameScreen.player);
                            gameScreen.playerHPBar.setWidth(gameScreen.playerBorderHPBar.getWidth()
                                    * ((double) gameScreen.player.getHealth()
                                            / gameScreen.player.getMaxHealth()));
                        }
                        //display minus HP while monster is attacking
                        if (frameCounter >= 50 && frameCounter <= 75) {
                            gameScreen.minusPlayerHP.setText(
                                    "-" + gameScreen.monster.getAttackDamage());
                        } else {
                            gameScreen.minusPlayerHP.setText("");
                        }
                    } else {
                        //if monster is not in attacking range, set frame counter to 0
                        frameCounter = 0;
                    }
                    gameScreen.monsterHPBar.setWidth(
                            gameScreen.monsterBorderHPBar.getWidth() * ((double)
                                    gameScreen.monster.getHealth()
                                    / gameScreen.monster.getMaxHealth()));
                    gameScreen.monsterHPBar.setX(gameScreen.monsterIcon.getX()
                            + (gameScreen.monsterIcon.getFitWidth() / 2) - 30);
                    gameScreen.monsterHPBar.setY(gameScreen.monsterName.getY() + 10);
                    gameScreen.monsterBorderHPBar.setX(gameScreen.monsterHPBar.getX());
                    gameScreen.monsterBorderHPBar.setY(gameScreen.monsterHPBar.getY());
                } else {
                    //once monster dies, remove name & minus player HP text
                    gameScreen.monsterName.setText("");
                    gameScreen.minusPlayerHP.setText("");
                    gameScreen.movePane.getChildren().remove(gameScreen.monsterBorderHPBar);
                    gameScreen.movePane.getChildren().remove(gameScreen.monsterHPBar);
                }
            }
        };
        attackLoop.start();
        if (gameScreen.monster.isDead()) {
            attackLoop.stop();
        }
    }
}