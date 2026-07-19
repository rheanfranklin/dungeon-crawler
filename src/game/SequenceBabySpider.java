package game;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.Arrays;

public class SequenceBabySpider extends SequenceMonster {
    /**
     * An array list that holds all the frames
     * of the baby spider's attack animation
     */
    private static final ArrayList<Image> ATTACK_SEQUENCE = new ArrayList<Image>(
            Arrays.asList(
                    new Image("resources/Monsters/BabySpider/BabySpiderAttackF1.png"),
                    new Image("resources/Monsters/BabySpider/BabySpiderAttackF2.png")
            )
    );

    /**
     * Counts the number of frames the Animation Timer
     * has iterated through to determine
     * which frame of the baby spider to display on the game screen
     */
    private static int frameCounter = 0;

    /**
     * Constructor of the baby spider
     */
    public SequenceBabySpider() {
        super(generateImageView("resources/Monsters/BabySpider/BabySpiderStatic.png"));
    }

    /**
     * Create an animation of the baby spider attacking
     * and chasing the player
     */
    public void startAttackSequence() {
        gameScreen.minusPlayerHP.setText("");
        AnimationTimer attackLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (gameScreen.monster != null && !gameScreen.monster.isDead()) {
                    //calculating distance between monster & player
                    double deltaX = gameScreen.monsterIcon.getX() - gameScreen.characterIcon.getX();
                    double deltaY = gameScreen.monsterIcon.getY() - gameScreen.characterIcon.getY();

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

                    //reset Ogre icon to the static image (as in, no animation)
                    //if the monster is in the middle of a fight sequence,
                    //then the image will be set later on
                    gameScreen.monsterIcon.setImage(stillMonsterImage.getImage());

                    //setting up HP & name display
                    gameScreen.monsterHPBar.setWidth(
                            gameScreen.monsterBorderHPBar.getWidth()
                                    * ((double) gameScreen.monster.getHealth()
                                    / gameScreen.monster.getMaxHealth())
                    );

                    //if the ogre is in attack range then stay still
                    //else, move toward player
                    if (deltaX < MONSTER_ATTACK_DISTANCE && deltaX > -MONSTER_ATTACK_DISTANCE) {
                        gameScreen.monsterIcon.setX(gameScreen.monsterIcon.getX());
                    } else if (deltaX < 0) {
                        gameScreen.monsterIcon.setX(gameScreen.monsterIcon.getX() + MONSTER_SPEED);
                    } else {
                        gameScreen.monsterIcon.setX(gameScreen.monsterIcon.getX() - MONSTER_SPEED);
                    }

                    if (deltaY < MONSTER_ATTACK_DISTANCE && deltaY > -MONSTER_ATTACK_DISTANCE) {
                        gameScreen.monsterIcon.setY(gameScreen.monsterIcon.getY());
                    } else if (deltaY < 0) {
                        gameScreen.monsterIcon.setY(gameScreen.monsterIcon.getY() + MONSTER_SPEED);
                    } else {
                        gameScreen.monsterIcon.setY(gameScreen.monsterIcon.getY() - MONSTER_SPEED);
                    }

                    //setting name coordinates based on monster's location
                    gameScreen.monsterName.setX(
                            gameScreen.monsterIcon.getX()
                                    + (gameScreen.monsterIcon.getFitWidth() / 2) - 30);
                    gameScreen.monsterName.setY(gameScreen.monsterIcon.getY() + 5);
                    gameScreen.monsterHPBar.setX(gameScreen.monsterName.getX() + 5);
                    gameScreen.monsterHPBar.setY(gameScreen.monsterName.getY() + 10);
                    gameScreen.monsterBorderHPBar.setX(gameScreen.monsterHPBar.getX());
                    gameScreen.monsterBorderHPBar.setY(gameScreen.monsterHPBar.getY());
                    gameScreen.minusPlayerHP.setX(gameScreen.monsterIcon.getX());
                    gameScreen.minusPlayerHP.setY(gameScreen.monsterIcon.getY());

                    //if ogre is within attacking distance, then initiate attack sequence
                    if (deltaY < MONSTER_ATTACK_DISTANCE
                            && deltaY > -MONSTER_ATTACK_DISTANCE
                            && deltaX < MONSTER_ATTACK_DISTANCE
                            && deltaX > -MONSTER_ATTACK_DISTANCE) {

                        //The cycle of an attack sequence is 120 frames
                        //so reset frame counter once it reaches
                        if (frameCounter >= 120) {
                            frameCounter = 0;
                        } else {
                            ++frameCounter;
                        }

                        //if frame counter is between 50 & 100
                        //then display first frame of attack sequence
                        if (frameCounter > 50 && frameCounter <= 100) {
                            gameScreen.monsterIcon.setImage(ATTACK_SEQUENCE.get(0));

                            //rotates attack frame 1 depending on which way the monster is facing
                            if (!facingLeft) {
                                gameScreen.monsterIcon.setRotate(180);
                            } else {
                                gameScreen.monsterIcon.setRotate(0);
                            }
                        }
                        //if frame counter is between 100 & 120
                        //then display second frame of attack sequence
                        if (frameCounter > 100 && frameCounter < 120) {
                            gameScreen.monsterIcon.setImage(ATTACK_SEQUENCE.get(1));

                            //rotates attack frame 2 depending on which way the monster is facing
                            if (!facingLeft) {
                                gameScreen.monsterIcon.setRotate(180);
                            } else {
                                gameScreen.monsterIcon.setRotate(0);
                            }
                        }
                        //attacks player as soon as it displays the second attack frame
                        if (frameCounter == 101) {
                            gameScreen.monster.attack(gameScreen.player);
                            gameScreen.player.getSequencePlayer().setPoisonCounter();
                            gameScreen.playerHPBar.setWidth(
                                    gameScreen.playerBorderHPBar.getWidth()
                                            * ((double) gameScreen.player.getHealth()
                                            / gameScreen.player.getMaxHealth())
                            );
                        }
                        if (frameCounter >= 101 && frameCounter < 115) {
                            gameScreen.minusPlayerHP.setText("-"
                                    + gameScreen.monster.getAttackDamage());
                        } else {
                            gameScreen.minusPlayerHP.setText("");
                        }
                    } else {
                        //sets frame counter to 0 if the monster is not in range of player
                        frameCounter = 0;
                    }
                } else {
                    //removes monster name & HP display once it's killed
                    gameScreen.monsterName.setText("");
                    gameScreen.movePane.getChildren().remove(gameScreen.monsterBorderHPBar);
                    gameScreen.movePane.getChildren().remove(gameScreen.monsterHPBar);
                    gameScreen.minusPlayerHP.setText("");
                }
            }
        };
        attackLoop.start();
        if (killed) {
            attackLoop.stop();
        }
    }
}