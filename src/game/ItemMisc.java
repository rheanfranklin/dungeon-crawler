package game;

import javafx.scene.image.Image;

public abstract class ItemMisc extends Item {
    /*
    Added this to satisfy compiler when making ItemMisc extend Item; may need to be changed
     */
    public ItemMisc(Inventory.ItemType itemType, Image itemIcon,
                    double value, String itemName, String itemDescription, int maxQuantity) {
        super(itemType, itemIcon, value, itemName, itemDescription, maxQuantity);
    }
}
