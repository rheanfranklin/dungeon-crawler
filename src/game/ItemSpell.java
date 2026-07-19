package game;

import javafx.scene.image.Image;

public abstract class ItemSpell extends Item {
    protected enum Level {
        LEVEL1,
        LEVEL2,
        LEVEL3
    }

    protected Level level;

    public ItemSpell(Image spellIcon, String itemName, String spellDescription, Level level) {
        super(Inventory.ItemType.SPELL, spellIcon, DEFAULT_VALUE, itemName, spellDescription, 1);
        this.itemName = itemName;
        this.level = level;
    }

    public abstract void cast(Player player);

    public String getName() {
        return itemName;
    }

    public void startMerchantLoop() {

    }


    public void stopMerchantLoop() {

    }

    public void mouseClickedMerchantButton() {

    }
    public boolean checkMerchantLoopStarted() {
        return false;
    }
}
