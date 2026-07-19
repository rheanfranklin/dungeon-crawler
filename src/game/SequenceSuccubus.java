package game;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class SequenceSuccubus extends SequenceMonster {
    /**
     * The range that the succubus must be in
     * in order to attack the player
     */
    private static final double SUCCUBUS_ATTACK_DISTANCE = 100;

    /**
     * Attack frame of the succubus
     */
    private static final Image ATTACK_FRAME =
            new Image("resources/Monsters/Succubus/SuccubusAttackF.png");

    /**
     * Counts the number of frames the Animation Timer
     * has iterated through to determine
     * which frame of the Succubus to display on the game screen
     */
    private int frameCounter;

    private int lastAttack;

    private static int summonCounter = 290;

    private int simpCount = 0;

    private SequenceSuccubus succubus;

    /**
     * Constructor of the ogre
     */
    public SequenceSuccubus() {
        super(generateImageView("resources/Monsters/Succubus/SuccubusStatic.png", 300, 300));
        succubus = this;
        frameCounter = 0;
        lastAttack = 0;
    }

    /**
     * Create an animation of the ogre attacking
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

                    gameScreen.monsterHPBar.setWidth(gameScreen.monsterBorderHPBar.getWidth()
                            * ((double) gameScreen.monster.getHealth()
                            / gameScreen.monster.getMaxHealth())
                    );

                    //reset Ogre icon to the static image (as in, no animation)
                    //if the monster is in the middle of a fight sequence,
                    //then the image will be set later on
                    gameScreen.monsterIcon.setImage(stillMonsterImage.getImage());


                    //setting text display coordinates based on monster's location
                    gameScreen.monsterName.setX(
                            gameScreen.monsterIcon.getX()
                                    + (gameScreen.monsterIcon.getFitWidth() / 2) - 10);
                    gameScreen.monsterName.setY(gameScreen.monsterIcon.getY());
                    gameScreen.monsterHPBar.setX(gameScreen.monsterIcon.getX()
                            + (gameScreen.monsterIcon.getFitWidth() / 2) - 30);
                    gameScreen.monsterHPBar.setY(gameScreen.monsterName.getY() + 10);
                    gameScreen.monsterBorderHPBar.setX(gameScreen.monsterHPBar.getX());
                    gameScreen.monsterBorderHPBar.setY(gameScreen.monsterHPBar.getY());
                    gameScreen.minusPlayerHP.setX(gameScreen.monsterIcon.getX());
                    gameScreen.minusPlayerHP.setY(gameScreen.monsterIcon.getY());

                    //if ogre is within attacking distance, then initiate attack sequence
                    if (deltaY < SUCCUBUS_ATTACK_DISTANCE
                            && deltaY > -SUCCUBUS_ATTACK_DISTANCE
                            && deltaX < SUCCUBUS_ATTACK_DISTANCE
                            && deltaX > -SUCCUBUS_ATTACK_DISTANCE) {
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
                        if (frameCounter - lastAttack > MONSTER_COOL_DOWN - 20) {
                            gameScreen.minusPlayerHP.setText("");
                        }
                    }
                    if (summonCounter > 300) {
                        summonCounter = 0;
                    } else {
                        ++summonCounter;
                    }

                    if (summonCounter == 0 && simpCount < 6) {
                        ImageView simpImage =
                                new ImageView(new Image("resources/Monsters/Simp.png"));
                        simpImage.setFitHeight(100);
                        simpImage.setFitWidth(100);
                        SequenceSimp sequenceSimp =
                                new SequenceSimp(gameScreen, simpImage, succubus);
                        Monster simp = new Monster("Simp", Monster.DEFAULT_HEALTH,
                                Monster.DEFAULT_ATTACK_DAMAGE, Monster.DEFAULT_RETURN_EXPERIENCE,
                                sequenceSimp);
                        sequenceSimp.setSimp(simp);
                        gameScreen.movePane.getChildren().add(simpImage);
                        simpImage.setX(300);
                        simpImage.setY(240);
                        simp.getSequenceMonster().startAttackSequence();
                        ++simpCount;
                    }
                } else {
                    gameScreen.movePane.getChildren().remove(gameScreen.monsterIcon);
                    gameScreen.minusPlayerHP.setText("");
                    gameScreen.minusMonsterHP.setText("");
                    gameScreen.movePane.getChildren().remove(gameScreen.monsterHPBar);
                    gameScreen.movePane.getChildren().remove(gameScreen.monsterBorderHPBar);

                }
            }
        };
        attackLoop.start();

        if (killed) {

            attackLoop.stop();

        }
    }

    public void minusSimp() {
        --simpCount;
    }
}