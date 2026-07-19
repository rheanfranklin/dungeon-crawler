package game;

import javafx.scene.image.Image;

public class ItemPotionReplenish extends ItemPotion {
    private static final Image REPLENISH_POTION_ICON =
            new Image("resources/Items/Consumable/PotionGreen.png");

    private static final String NAME = "Replenishing Potion";

    private static final int DEFAULT_STAMINA_FACTOR = 10;

    private int staminaFactor;

    private Level level;


    public ItemPotionReplenish() {
        this (DEFAULT_STAMINA_FACTOR, Level.LEVEL1, DEFAULT_VALUE);
    }

    public ItemPotionReplenish(int staminaFactor, Level level, double value) {
        super(REPLENISH_POTION_ICON, level,  value, NAME,
                generateDescription(staminaFactor));
        this.staminaFactor = staminaFactor;
        this.level = level;
    }

    public static String generateDescription(int staminaFactor) {
        String description = "Restores stamina \nby " + staminaFactor + " points";
        return description;
    }

    public void consume(Player player) {
        player.setStamina(Math.min(player.getStamina() + staminaFactor, player.getMaxStamina()));
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
