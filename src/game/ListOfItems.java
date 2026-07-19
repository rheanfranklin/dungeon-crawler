package game;

import java.util.ArrayList;

/**
 * a collection of methods
 * that generate a given potions
 * to be returned to the caller
 */
public class ListOfItems {

    private ArrayList<Item> level1Items;
    private ArrayList<Item> level2Items;
    private ArrayList<Item> level3Items;

    public ListOfItems() {
        level1Items = new ArrayList<>();
        level2Items = new ArrayList<>();
        level3Items = new ArrayList<>();
    }

    /*
     * List of all potions
     */
    public ItemPotionHealth createHealthPotion(Level level) {
        ItemPotionHealth healthPotion;
        if (level.equals(Level.LEVEL1)) {
            healthPotion = new ItemPotionHealth();
        } else if (level.equals(Level.LEVEL2)) {
            healthPotion = new ItemPotionHealth(15, level, 20);
        } else {
            healthPotion = new ItemPotionHealth(20, level, 30);
        }
        return healthPotion;
    }

    public ItemPotionReplenish createReplenishPotion(Level level) {
        ItemPotionReplenish replenishPotion;
        if (level.equals(Level.LEVEL1)) {
            replenishPotion = new ItemPotionReplenish();
        } else if (level.equals(Level.LEVEL2)) {
            replenishPotion = new ItemPotionReplenish(15, level, 20);
        } else {
            replenishPotion = new ItemPotionReplenish(20, level, 30);
        }
        return replenishPotion;
    }

    public ItemPotionRestore createRestorePotion(Level level) {
        ItemPotionRestore restoringPotion;
        if (level.equals(Level.LEVEL1)) {
            restoringPotion = new ItemPotionRestore();
        } else if (level.equals(Level.LEVEL2)) {
            restoringPotion = new ItemPotionRestore(15, level, 20);
        } else {
            restoringPotion = new ItemPotionRestore(20, level, 30);
        }
        return restoringPotion;
    }

    public ItemPotionAttack createAttackPotion(Level level) {
        ItemPotionAttack attackPotion;
        if (level.equals(Level.LEVEL1)) {
            attackPotion = new ItemPotionAttack(5, 50,  level, 20);
        } else if (level.equals(Level.LEVEL2)) {
            attackPotion = new ItemPotionAttack(10, 100, level, 30);
        } else {
            attackPotion = new ItemPotionAttack(15, 200, level, 40);
        }
        return attackPotion;
    }

}
