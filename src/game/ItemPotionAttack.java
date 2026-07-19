package game;

import javafx.scene.image.Image;

public class ItemPotionAttack extends ItemPotion {
    private static final Image ATTACK_POTION_ICON =
            new Image("resources/Items/Consumable/PotionOrange.png");

    private static final String NAME = "Attack Potion";

    private static final int DEFAULT_ATTACK_FACTOR = 10;

    private static final int DEFAULT_TIMER = 3600;

    private final int attackFactor;

    private final int timer;

    public ItemPotionAttack() {
        this(DEFAULT_ATTACK_FACTOR, DEFAULT_TIMER, Level.LEVEL1, DEFAULT_VALUE);

    }

    public ItemPotionAttack(int attackFactor, int timer, Level level, double value) {
        super(ATTACK_POTION_ICON, level, value, NAME,
                generateDescription(attackFactor, timer));
        this.attackFactor = attackFactor;
        this.timer = timer;
    }

    public static String generateDescription(int attackFactor, int timer) {
        return "Increases attack\ndamage by " + attackFactor + "\nfor " + (timer / 60) + " seconds.";
    }

    public void consume(Player player) {
        player.getSequencePlayer().setAttackCounter(timer, attackFactor);
        player.getInventory().removeItem(this);
    }

    public void mouseClickedButton() {
        this.consume(controller.player);
        if (this.quantity <= 0) {
            this.itemRemoved = true;
        }
        controller.inventoryStack.getChildren().remove(this.iconBox);
        controller.button.setText("Pressed");
    }

}
