package game;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class SequenceSimp extends SequenceMonster {

    private final GameScreen gameScreen;

    protected static final double MONSTER_ATTACK_DISTANCE = 50;

    protected static final double MONSTER_SPEED = 0.50;

    protected static final double MONSTER_COOL_DOWN = 50;

    protected int frameCounter;
    protected int lastAttack;

    protected ImageView simpIcon;

    protected Monster simp;

    private SequenceSuccubus succubus;

    private Text minusMonsterHP;

    public SequenceSimp(GameScreen gameScreen, ImageView simpIcon, SequenceSuccubus succubus) {
        super();
        this.gameScreen = gameScreen;
        this.simpIcon = simpIcon;
        this.succubus = succubus;
        frameCounter = 0;
        lastAttack = 0;
    }

    public void setSimp(Monster simp) {
        this.simp = simp;
    }

    public void startAttackSequence() {
        //setting up Monster HP & name display
        Text simpHP = new Text("HP: "
                + Math.max(simp.getHealth(), 0));
        gameScreen.movePane.getChildren().add(simpHP);

        Text simpName = new Text("Simp");
        simpName.setFont(GameScreen.STANDARD_FONT);
        gameScreen.movePane.getChildren().add(simpName);

        Text minusPlayerHP = new Text("");
        simpName.setFont(GameScreen.STANDARD_FONT);
        gameScreen.movePane.getChildren().add(minusPlayerHP);
        minusPlayerHP.setFill(Color.RED);

        minusMonsterHP = new Text("");
        simpName.setFont(GameScreen.STANDARD_FONT);
        gameScreen.movePane.getChildren().add(minusMonsterHP);
        minusMonsterHP.setFill(Color.LIMEGREEN);

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (simp != null && !simp.isDead() && !gameScreen.monster.isDead()) {
                    //calculating distance between monster & player
                    double deltaX = simpIcon.getX() - gameScreen.characterIcon.getX();
                    double deltaY = simpIcon.getY() - gameScreen.characterIcon.getY();

                    //used to determine which way the monster is facing (towards player)
                    boolean facingLeft;
                    facingLeft = deltaX < 0;

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

                    //Telling monster to move toward player if it is outside of
                    //its MONSTER_ATTACK_DISTANCE range
                    //else stay in same place
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

                    //if monster is in range of the player to attack
                    //then initiate attack sequence
                    if (deltaY < MONSTER_ATTACK_DISTANCE
                            && deltaY > -MONSTER_ATTACK_DISTANCE
                            && deltaX < MONSTER_ATTACK_DISTANCE
                            && deltaX > -MONSTER_ATTACK_DISTANCE) {
                        //increment frame counter to time the frame shifting and attack
                        ++frameCounter;
                        if (frameCounter - lastAttack > MONSTER_COOL_DOWN) {
                            simp.attack(gameScreen.player);
                            lastAttack = frameCounter;
                            gameScreen.playerHPBar.setWidth(
                                    gameScreen.playerBorderHPBar.getWidth()
                                            * ((double) gameScreen.player.getHealth()
                                            / gameScreen.player.getMaxHealth())
                            );
                            minusPlayerHP.setText(
                                    "-" + simp.getAttackDamage());
                            minusPlayerHP.setX(simpIcon.getX());
                            minusPlayerHP.setY(simpIcon.getY());
                        }
                        if (frameCounter - lastAttack > MONSTER_COOL_DOWN - 20) {
                            minusPlayerHP.setText("");
                        }
                    }

                    //setting the x & y coordinates of monster name & HP display
                    simpName.setX(
                            simpIcon.getX()
                                    + (simpIcon.getFitWidth() / 2) - 10);
                    simpName.setY(simpIcon.getY());
                    simpHP.setX(simpName.getX());
                    simpHP.setY(simpName.getY() - 15);
                } else {
                    //if monster is dead, then remove hp & name display
                    simpHP.setText("");
                    simpName.setText("");
                    minusPlayerHP.setText("");
                    minusMonsterHP.setText("");
                }

                gameScreen.stack.setOnMouseClicked(e -> {
                    setOnMouseClicked();
                });
            }
        };

        gameLoop.start();
        if (gameScreen.monster.getSequenceMonster().getKilled()) {
            gameScreen.movePane.getChildren().remove(simpIcon);
            gameLoop.stop();
        }
    }

    //TODO add minus stamina
    public void setOnMouseClicked() {
        double simpDeltaX = gameScreen.characterIcon.getX() - simpIcon.getX();
        double simpDeltaY = gameScreen.characterIcon.getY() - simpIcon.getY();
        double succubusDeltaX = gameScreen.characterIcon.getX() - gameScreen.monsterIcon.getX();
        double succubusDeltaY = gameScreen.characterIcon.getY() - gameScreen.monsterIcon.getY();
        SequencePlayer sequencePlayer = gameScreen.getPlayer().getSequencePlayer();
        if (simpDeltaY < 100 && simpDeltaY > -100 && simpDeltaX < 100 && simpDeltaX > -100) {
            simp.takeDamage(gameScreen.player);
            lastAttack = 0;
            minusMonsterHP.setText("-" + gameScreen.player.getAttackDamage());
            minusMonsterHP.setFont(GameScreen.STANDARD_FONT);
            minusMonsterHP.setFill(Color.LIMEGREEN);
            minusMonsterHP.setX(simpIcon.getX() - 50);
            minusMonsterHP.setY(simpIcon.getY() + 20);

            if (simp.isDead() && !simp.getGaveExperience()) {
                gameScreen.player.setMoney(gameScreen.player.getMoney() + 50);
                gameScreen.plusExperience.setX(gameScreen.characterIcon.getX());
                gameScreen.plusExperience.setY(gameScreen.characterIcon.getY());
                sequencePlayer.setGiveExperienceCounter(0);
                gameScreen.player.setExperience(gameScreen.player.getExperience()
                        + simp.getReturnExperience());
                simp.giveExperience(gameScreen.player);
                simp.getSequenceMonster().setKilled();
                gameScreen.movePane.getChildren().remove(simpIcon);

            }
        }

        if (succubusDeltaY < 100 && succubusDeltaY > -100
                && succubusDeltaX < 100 && succubusDeltaX > -100) {

            sequencePlayer.setOnMouseClicked();
        }
    }
}