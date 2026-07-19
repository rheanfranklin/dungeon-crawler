package game;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class SequenceJustin extends SequenceMonster {
    private static final ImageView JUSTIN_ICON = new ImageView(
            new Image("resources/Monsters/JustinBeiber.png")
    );


    public SequenceJustin() {
        super(generateImageView("resources/Monsters/JustinBeiber.png", 150, 150));
    }

    private int frameCounter = 0;

    public void startAttackSequence() {
        gameScreen.minusPlayerHP.setText("");
        AnimationTimer attackLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (gameScreen.monster != null && !gameScreen.monster.isDead()) {
                    ++frameCounter;
                    if (frameCounter > 100) {
                        frameCounter = 0;
                    }

                    //setting rotation axis for mirroring monster image
                    //for whenever it changes left/right directions
                    gameScreen.monsterIcon.setRotationAxis(Rotate.Y_AXIS);
                    gameScreen.monsterIcon.setRotate(180);
                    //setting up HP & name display
                    gameScreen.monsterHPBar.setWidth(
                            gameScreen.monsterBorderHPBar.getWidth()
                                    * ((double) gameScreen.monster.getHealth()
                                    / gameScreen.monster.getMaxHealth())
                    );

                    //setting name coordinates based on monster's location
                    gameScreen.monsterName.setX(
                            gameScreen.monsterIcon.getX()
                                    + (gameScreen.monsterIcon.getFitWidth() / 2) - 10);
                    gameScreen.monsterName.setY(gameScreen.monsterIcon.getY() - 10);
                    gameScreen.monsterHPBar.setX(gameScreen.monsterName.getX() - 22);
                    gameScreen.monsterHPBar.setY(gameScreen.monsterName.getY() + 10);
                    gameScreen.monsterBorderHPBar.setX(gameScreen.monsterHPBar.getX());
                    gameScreen.monsterBorderHPBar.setY(gameScreen.monsterHPBar.getY());
                    gameScreen.minusPlayerHP.setX(gameScreen.characterIcon.getX());
                    gameScreen.minusPlayerHP.setY(gameScreen.characterIcon.getY());

                    if (frameCounter == 80) {
                        gameScreen.monster.attack(gameScreen.player);
                        gameScreen.playerHPBar.setWidth(
                                gameScreen.playerBorderHPBar.getWidth()
                                        * ((double) gameScreen.player.getHealth()
                                        / gameScreen.player.getMaxHealth())
                        );
                    }

                    if (frameCounter > 80 && frameCounter < 100) {
                        gameScreen.minusPlayerHP.setText("-"
                                + gameScreen.monster.getAttackDamage());
                    } else {
                        gameScreen.minusPlayerHP.setText("");
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