package game;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.Arrays;

public class SequenceBabySpiderSummon extends SequenceMonster {

    private final GameScreen gameScreen;

    protected static final double MONSTER_ATTACK_DISTANCE = 50;

    protected static final double MONSTER_SPEED = 0.50;

    protected static final double MONSTER_COOL_DOWN = 50;

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

    protected int frameCounter;
    protected int lastAttack;

    protected ImageView simpIcon;

    protected Monster babySpider;

    private SequenceBigSpider sequenceBigSpider;

    private Text minusMonsterHP;

    private Room room;

    public SequenceBabySpiderSummon(GameScreen gameScreen, ImageView simpIcon, SequenceBigSpider sequenceBigSpider) {
        super();
        this.gameScreen = gameScreen;
        this.simpIcon = simpIcon;
        this.sequenceBigSpider = sequenceBigSpider;
        this.stillMonsterImage = new ImageView(new Image("resources/Monsters/BabySpider/BabySpiderStatic.png"));
        frameCounter = 0;
        lastAttack = 0;
    }

    public void setBabySpider(Monster babySpider) {
        this.babySpider = babySpider;
    }

    public void startAttackSequence() {
        //setting up Monster HP & name display
        Text simpHP = new Text("HP: "
                + Math.max(babySpider.getHealth(), 0));
        gameScreen.movePane.getChildren().add(simpHP);
        simpHP.setFont(GameScreen.STANDARD_FONT);
        simpHP.setFill(Color.WHITE);

        Text simpName = new Text("Baby Spider");
        simpName.setFont(GameScreen.STANDARD_FONT);
        simpName.setFill(Color.WHITE);
        gameScreen.movePane.getChildren().add(simpName);

        Text minusPlayerHP = new Text("");
        minusPlayerHP.setFont(GameScreen.STANDARD_FONT);
        gameScreen.movePane.getChildren().add(minusPlayerHP);
        minusPlayerHP.setFill(Color.RED);

        minusMonsterHP = new Text("");
        gameScreen.movePane.getChildren().add(minusMonsterHP);
        minusMonsterHP.setFill(Color.LIMEGREEN);

        room = gameScreen.room;

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (babySpider != null && !babySpider.isDead() && gameScreen.getRoom().equals(room)) {
                    //calculating distance between monster & player
                    double deltaX = simpIcon.getX() - gameScreen.characterIcon.getX();
                    double deltaY = simpIcon.getY() - gameScreen.characterIcon.getY();

                    //used to determine which way the monster is facing (towards player)
                    boolean facingLeft;
                    facingLeft = deltaX > 0;

                    simpHP.setText("HP: " + babySpider.getHealth());

                    //setting rotation axis for mirroring monster image
                    //for whenever it changes left/right directions
                    simpIcon.setRotationAxis(Rotate.Y_AXIS);

                    //rotates the monster image 180 degrees
                    //depending on which direction it is facing
                    if (!facingLeft) {
                        simpIcon.setRotate(180);
                    } else {
                        simpIcon.setRotate(0);
                    }

                    //reset Ogre icon to the static image (as in, no animation)
                    //if the monster is in the middle of a fight sequence,
                    //then the image will be set later on
                    simpIcon.setImage(stillMonsterImage.getImage());


                    //setting the x & y coordinates of monster name & HP display
                    simpName.setX(
                            simpIcon.getX()
                                    + (simpIcon.getFitWidth() / 2) - 10);
                    simpName.setY(simpIcon.getY());
                    simpHP.setX(simpName.getX());
                    simpHP.setY(simpName.getY() - 15);

                    //if the ogre is in attack range then stay still
                    //else, move toward player
                    if (deltaX < MONSTER_ATTACK_DISTANCE && deltaX > -MONSTER_ATTACK_DISTANCE) {
                        simpIcon.setX(simpIcon.getX());
                    } else if (deltaX < 0) {
                        simpIcon.setX(simpIcon.getX() + MONSTER_SPEED);
                    } else {
                        simpIcon.setX(simpIcon.getX() - MONSTER_SPEED);
                    }

                    if (deltaY < MONSTER_ATTACK_DISTANCE && deltaY > -MONSTER_ATTACK_DISTANCE) {
                        simpIcon.setY(simpIcon.getY());
                    } else if (deltaY < 0) {
                        simpIcon.setY(simpIcon.getY() + MONSTER_SPEED);
                    } else {
                        simpIcon.setY(simpIcon.getY() - MONSTER_SPEED);
                    }

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
                            simpIcon.setImage(ATTACK_SEQUENCE.get(0));

                            //rotates attack frame 1 depending on which way the monster is facing
                            if (!facingLeft) {
                                simpIcon.setRotate(180);
                            } else {
                                simpIcon.setRotate(0);
                            }
                        }
                        //if frame counter is between 100 & 120
                        //then display second frame of attack sequence
                        if (frameCounter > 100 && frameCounter < 120) {
                            simpIcon.setImage(ATTACK_SEQUENCE.get(1));

                            //rotates attack frame 2 depending on which way the monster is facing
                            if (!facingLeft) {
                                simpIcon.setRotate(180);
                            } else {
                                simpIcon.setRotate(0);
                            }
                        }
                        //attacks player as soon as it displays the second attack frame
                        if (frameCounter == 101) {
                            babySpider.attack(gameScreen.player);
                            gameScreen.player.getSequencePlayer().setPoisonCounter();

                            gameScreen.playerHPBar.setWidth(
                                    gameScreen.playerBorderHPBar.getWidth()
                                            * ((double) gameScreen.player.getHealth()
                                            / gameScreen.player.getMaxHealth())
                            );
                        }
                        if (frameCounter >= 101 && frameCounter < 115) {
                            gameScreen.minusPlayerHP.setText("-"
                                    + babySpider.getAttackDamage());
                        } else {
                            gameScreen.minusPlayerHP.setText("");
                        }
                    } else {
                        //sets frame counter to 0 if the monster is not in range of player
                        frameCounter = 0;
                    }
                } else {
                    //if monster is dead, then remove hp & name display
                    simpHP.setText("");
                    simpName.setText("");
                    minusPlayerHP.setText("");
                    minusMonsterHP.setText("");
                }

                if (!babySpider.isDead()) {
                    gameScreen.stack.setOnMouseClicked(e -> {
                        setOnMouseClicked();
                    });
                    gameScreen.summonedGone = false;
                }
            }
        };

        gameLoop.start();
        if (!room.equals(gameScreen.getRoom()) || babySpider.isDead()) {
            gameScreen.movePane.getChildren().remove(simpIcon);
            gameLoop.stop();
        }
    }

    public void setOnMouseClicked() {
        double simpDeltaX = gameScreen.characterIcon.getX() - simpIcon.getX();
        double simpDeltaY = gameScreen.characterIcon.getY() - simpIcon.getY();
        double succubusDeltaX = gameScreen.characterIcon.getX() - gameScreen.monsterIcon.getX();
        double succubusDeltaY = gameScreen.characterIcon.getY() - gameScreen.monsterIcon.getY();
        SequencePlayer sequencePlayer = gameScreen.getPlayer().getSequencePlayer();
        if (simpDeltaY < 100 && simpDeltaY > -100 && simpDeltaX < 100 && simpDeltaX > -100) {
            babySpider.takeDamage(gameScreen.player);
            lastAttack = 0;
            minusMonsterHP.setText("-" + gameScreen.player.getAttackDamage());
            minusMonsterHP.setFont(GameScreen.STANDARD_FONT);
            minusMonsterHP.setFill(Color.LIMEGREEN);
            minusMonsterHP.setX(simpIcon.getX() - 50);
            minusMonsterHP.setY(simpIcon.getY() + 20);

            if (babySpider.isDead() && !babySpider.getGaveExperience()) {
                sequenceBigSpider.minusSimp();
                gameScreen.player.setMoney(gameScreen.player.getMoney() + 50);
                summonedGone = true;

                gameScreen.plusExperience.setX(gameScreen.characterIcon.getX());
                gameScreen.plusExperience.setY(gameScreen.characterIcon.getY());
                sequencePlayer.setGiveExperienceCounter(0);
                gameScreen.player.setExperience(gameScreen.player.getExperience()
                        + babySpider.getReturnExperience());
                babySpider.giveExperience(gameScreen.player);
                babySpider.getSequenceMonster().setKilled();
                gameScreen.movePane.getChildren().remove(simpIcon);

            }
        }

        if (succubusDeltaY < 100 && succubusDeltaY > -100
                && succubusDeltaX < 100 && succubusDeltaX > -100) {

            sequencePlayer.setOnMouseClicked();
        }
    }
}
