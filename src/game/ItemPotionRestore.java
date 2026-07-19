package game;

import javafx.scene.image.Image;

public class ItemPotionRestore extends ItemPotion {

    private static final Image RESTORE_POTION_ICON =
            new Image("resources/Items/Consumable/PotionBlue.png");

    private static final String NAME = "Restoring Potion";

    private static final int DEFAULT_MANA_FACTOR = 10;

    private final int manaFactor;

    private final Level level;

    public ItemPotionRestore() {
        this (DEFAULT_MANA_FACTOR, Level.LEVEL1, DEFAULT_VALUE);
    }

    public ItemPotionRestore(int manaFactor, Level level, double value) {
        super(RESTORE_POTION_ICON, level,  value, NAME,
                generateDescription(manaFactor));
        this.manaFactor = manaFactor;
        this.level = level;
    }

    public static String generateDescription(int manaFactor) {
        return "Restores mana\nby " + manaFactor + " points";
    }

    public void consume(Player player) {
        player.setStamina(Math.min(player.getMana() + manaFactor, player.getMaxMana()));
        player.getInventory().removeItem(this);
    }

    public void mouseClickedButton() {
        this.consume(controller.player);
        if (this.quantity <= 0) {
            this.itemRemoved = true;
        }
        controller.button.setText("Pressed");
    }
}
