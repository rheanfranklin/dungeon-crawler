package game;

import javafx.scene.image.Image;

public class ItemPotionHealth extends ItemPotion {
    private static final Image HEALTH_POTION_ICON =
            new Image("resources/Items/Consumable/PotionRed.png");

    private static final int DEFAULT_HEALTH_FACTOR = 10;

    private static final String NAME = "Healing Potion";

    private final int healthFactor;

    public ItemPotionHealth() {
        this (DEFAULT_HEALTH_FACTOR, Level.LEVEL1, DEFAULT_VALUE);
    }

    public ItemPotionHealth(int healthFactor, Level level, double value) {
        super(HEALTH_POTION_ICON, level,  value, NAME,
                generateDescription(healthFactor));
        this.healthFactor = healthFactor;
        this.level = level;
    }

    public static String generateDescription(int healthFactor) {
        return "Restores health\nby " + healthFactor + " points";
    }

    public void consume(Player player) {
        player.setHealth(Math.min(player.getHealth() + healthFactor, player.getMaxHealth()));
        player.getInventory().removeItem(this);
    }

    public int getHealthFactor() {
        return healthFactor;
    }

    public Level getLevel() {
        return level;
    }

    public void mouseClickedButton() {
        this.consume(controller.player);
        if (this.quantity <= 0) {
            this.itemRemoved = true;
        }
        controller.button.setText("Pressed");
        controller.inventoryStack.getChildren().remove(iconBox);
    }

}
