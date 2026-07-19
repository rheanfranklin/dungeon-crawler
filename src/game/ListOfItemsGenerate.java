package game;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class ListOfItemsGenerate {
    private HashMap<Rarity, HashMap<Inventory.ItemType, List<Item>>> itemsList;

    public ListOfItemsGenerate() {
        itemsList = new HashMap<>();
        itemsList.put(Rarity.COMMON, new HashMap<>());
        itemsList.get(Rarity.COMMON).put(Inventory.ItemType.WEAPON, new ArrayList<>());
        itemsList.get(Rarity.COMMON).put(Inventory.ItemType.SPELL, new ArrayList<>());
        itemsList.get(Rarity.COMMON).put(Inventory.ItemType.WEARABLE, new ArrayList<>());
        itemsList.get(Rarity.COMMON).put(Inventory.ItemType.CONSUMABLE, new ArrayList<>());
        itemsList.get(Rarity.COMMON).put(Inventory.ItemType.MISC, new ArrayList<>());

        itemsList.put(Rarity.UNCOMMON, new HashMap<>());
        itemsList.get(Rarity.UNCOMMON).put(Inventory.ItemType.WEAPON, new ArrayList<>());
        itemsList.get(Rarity.UNCOMMON).put(Inventory.ItemType.SPELL, new ArrayList<>());
        itemsList.get(Rarity.UNCOMMON).put(Inventory.ItemType.WEARABLE, new ArrayList<>());
        itemsList.get(Rarity.UNCOMMON).put(Inventory.ItemType.CONSUMABLE, new ArrayList<>());
        itemsList.get(Rarity.UNCOMMON).put(Inventory.ItemType.MISC, new ArrayList<>());

        itemsList.put(Rarity.RARE, new HashMap<>());
        itemsList.get(Rarity.RARE).put(Inventory.ItemType.WEAPON, new ArrayList<>());
        itemsList.get(Rarity.RARE).put(Inventory.ItemType.SPELL, new ArrayList<>());
        itemsList.get(Rarity.RARE).put(Inventory.ItemType.WEARABLE, new ArrayList<>());
        itemsList.get(Rarity.RARE).put(Inventory.ItemType.CONSUMABLE, new ArrayList<>());
        itemsList.get(Rarity.RARE).put(Inventory.ItemType.MISC, new ArrayList<>());

        itemsList.put(Rarity.SUPER_RARE, new HashMap<>());
        itemsList.get(Rarity.SUPER_RARE).put(Inventory.ItemType.WEAPON, new ArrayList<>());
        itemsList.get(Rarity.SUPER_RARE).put(Inventory.ItemType.SPELL, new ArrayList<>());
        itemsList.get(Rarity.SUPER_RARE).put(Inventory.ItemType.WEARABLE, new ArrayList<>());
        itemsList.get(Rarity.SUPER_RARE).put(Inventory.ItemType.CONSUMABLE, new ArrayList<>());
        itemsList.get(Rarity.SUPER_RARE).put(Inventory.ItemType.MISC, new ArrayList<>());

    }

    /**
     * Generates a random common consumable item
     * Used whenever a monster drops an item
     *
     * @return random common consumable that was generated
     */
    public Item returnCommonConsumable() {
        generateCommonConsumable();
        Random rand = new Random(System.currentTimeMillis());
        int randInt = rand.nextInt(itemsList.get(Rarity.COMMON)
                .get(Inventory.ItemType.CONSUMABLE).size() - 1);
        return itemsList.get(Rarity.COMMON).get(Inventory.ItemType.CONSUMABLE).get(randInt);

    }

    /**
     * Generates a random common weapon item
     * Used whenever a monster drops an item
     *
     * @return random common weapon that was generated
     */
    public Item returnCommonWeapon() {
        generateCommonWeapon();
        Random rand = new Random(System.currentTimeMillis());
        int randInt = rand.nextInt(itemsList.get(Rarity.COMMON)
                .get(Inventory.ItemType.WEAPON).size() - 1);
        return itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEAPON).get(randInt);

    }

    /**
     * Generates a random item for monster to drop
     * when killed depending on the monster's item drop type
     *
     * @param itemDropType the item drop type of the monster that was killed
     * @return item to be dropped for player to pick up
     */
    public Item generateItem(Monster.ItemDropType itemDropType) {
        Item returnItem;
        Random rand = new Random(System.currentTimeMillis());
        int randInt;

        if (itemDropType.equals(Monster.ItemDropType.COMMON_CONSUMABLE)) {
            generateCommonConsumable();
            randInt = rand.nextInt(itemsList.get(Rarity.COMMON)
                    .get(Inventory.ItemType.CONSUMABLE).size() - 1);
            returnItem = itemsList.get(Rarity.COMMON)
                    .get(Inventory.ItemType.CONSUMABLE).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.COMMON_WEAPON)) {
            generateCommonWeapon();
            randInt = rand.nextInt(itemsList.get(Rarity.COMMON)
                    .get(Inventory.ItemType.WEAPON).size() - 1);
            returnItem = itemsList.get(Rarity.COMMON)
                    .get(Inventory.ItemType.WEAPON).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.COMMON_MISC)) {
            generateCommonMisc();
            randInt = rand.nextInt(itemsList.get(Rarity.COMMON)
                    .get(Inventory.ItemType.MISC).size() - 1);
            returnItem = itemsList.get(Rarity.COMMON)
                    .get(Inventory.ItemType.MISC).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.COMMON_SPELL)) {
            generateCommonSpell();
            randInt = rand.nextInt(itemsList.get(Rarity.COMMON)
                    .get(Inventory.ItemType.SPELL).size() - 1);
            returnItem = itemsList.get(Rarity.COMMON)
                    .get(Inventory.ItemType.SPELL).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.COMMON_WEARABLE)) {
            generateCommonWearable();
            randInt = rand.nextInt(itemsList.get(Rarity.COMMON)
                    .get(Inventory.ItemType.WEARABLE).size() - 1);
            returnItem = itemsList.get(Rarity.COMMON)
                    .get(Inventory.ItemType.WEARABLE).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.UNCOMMON_CONSUMABLE)) {
            generateUncommonConsumable();
            randInt = rand.nextInt(itemsList.get(Rarity.UNCOMMON)
                    .get(Inventory.ItemType.CONSUMABLE).size() - 1);
            returnItem = itemsList.get(Rarity.UNCOMMON)
                    .get(Inventory.ItemType.CONSUMABLE).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.UNCOMMON_WEAPON)) {
            generateUncommonWeapon();
            randInt = rand.nextInt(itemsList.get(Rarity.UNCOMMON)
                    .get(Inventory.ItemType.WEAPON).size() - 1);
            returnItem = itemsList.get(Rarity.UNCOMMON)
                    .get(Inventory.ItemType.WEAPON).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.UNCOMMON_MISC)) {
            generateUncommonMisc();
            randInt = rand.nextInt(itemsList.get(Rarity.UNCOMMON)
                    .get(Inventory.ItemType.MISC).size() - 1);
            returnItem = itemsList.get(Rarity.UNCOMMON)
                    .get(Inventory.ItemType.MISC).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.UNCOMMON_SPELL)) {
            generateUncommonSpell();
            randInt = rand.nextInt(itemsList.get(Rarity.UNCOMMON)
                    .get(Inventory.ItemType.SPELL).size() - 1);
            returnItem = itemsList.get(Rarity.UNCOMMON)
                    .get(Inventory.ItemType.SPELL).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.UNCOMMON_WEARABLE)) {
            generateUncommonWearable();
            randInt = rand.nextInt(itemsList.get(Rarity.UNCOMMON)
                    .get(Inventory.ItemType.WEARABLE).size() - 1);
            returnItem = itemsList.get(Rarity.UNCOMMON)
                    .get(Inventory.ItemType.WEARABLE).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.RARE_CONSUMABLE)) {
            generateRareConsumable();
            randInt = rand.nextInt(itemsList.get(Rarity.RARE)
                    .get(Inventory.ItemType.CONSUMABLE).size() - 1);
            returnItem = itemsList.get(Rarity.RARE)
                    .get(Inventory.ItemType.CONSUMABLE).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.RARE_WEAPON)) {
            generateRareWeapon();
            randInt = rand.nextInt(itemsList.get(Rarity.RARE)
                    .get(Inventory.ItemType.WEAPON).size() - 1);
            returnItem = itemsList.get(Rarity.RARE)
                    .get(Inventory.ItemType.WEAPON).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.RARE_MISC)) {
            generateRareMisc();
            randInt = rand.nextInt(itemsList.get(Rarity.RARE)
                    .get(Inventory.ItemType.MISC).size() - 1);
            returnItem = itemsList.get(Rarity.RARE)
                    .get(Inventory.ItemType.MISC).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.RARE_SPELL)) {
            generateRareSpell();
            randInt = rand.nextInt(itemsList.get(Rarity.RARE)
                    .get(Inventory.ItemType.SPELL).size() - 1);
            returnItem = itemsList.get(Rarity.RARE)
                    .get(Inventory.ItemType.SPELL).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.RARE_WEARABLE)) {
            generateRareWearable();
            randInt = rand.nextInt(itemsList.get(Rarity.RARE)
                    .get(Inventory.ItemType.WEARABLE).size() - 1);
            returnItem = itemsList.get(Rarity.RARE)
                    .get(Inventory.ItemType.WEARABLE).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.SUPER_RARE_CONSUMABLE)) {
            generateSuperRareConsumable();
            randInt = rand.nextInt(itemsList.get(Rarity.SUPER_RARE)
                    .get(Inventory.ItemType.CONSUMABLE).size());
            returnItem = itemsList.get(Rarity.SUPER_RARE)
                    .get(Inventory.ItemType.CONSUMABLE).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.SUPER_RARE_WEAPON)) {
            generateSuperRareWeapon();
            randInt = rand.nextInt(itemsList.get(Rarity.SUPER_RARE)
                    .get(Inventory.ItemType.WEAPON).size() - 1);
            returnItem = itemsList.get(Rarity.SUPER_RARE)
                    .get(Inventory.ItemType.WEAPON).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.SUPER_RARE_MISC)) {
            generateSuperRareMisc();
            randInt = rand.nextInt(itemsList.get(Rarity.SUPER_RARE)
                    .get(Inventory.ItemType.MISC).size() - 1);
            returnItem = itemsList.get(Rarity.SUPER_RARE)
                    .get(Inventory.ItemType.MISC).get(randInt);

        } else if (itemDropType.equals(Monster.ItemDropType.SUPER_RARE_SPELL)) {
            generateSuperRareSpell();
            randInt = rand.nextInt(itemsList.get(Rarity.SUPER_RARE)
                    .get(Inventory.ItemType.SPELL).size() - 1);
            returnItem = itemsList.get(Rarity.SUPER_RARE)
                    .get(Inventory.ItemType.SPELL).get(randInt);

        } else {
            generateSuperRareWearable();
            randInt = rand.nextInt(itemsList.get(Rarity.SUPER_RARE)
                    .get(Inventory.ItemType.WEARABLE).size() - 1);
            returnItem = itemsList.get(Rarity.SUPER_RARE)
                    .get(Inventory.ItemType.WEARABLE).get(randInt);

        }

        return returnItem;
    }

    /*
     * TODO implement this method
     */
    public void generateMerchantInventory(Inventory inventory) {
        generateCommonConsumable();
        generateCommonWearable();
        generateCommonWeapon();
        generateUncommonWeapon();
        generateRareWeapon();
        generateSuperRareWeapon();

        inventory.add((ArrayList<Item>) itemsList.get(Rarity.COMMON).get(Inventory.ItemType.CONSUMABLE));
        inventory.add((ArrayList<Item>) itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEAPON));
        inventory.add((ArrayList<Item>) itemsList.get(Rarity.UNCOMMON).get(Inventory.ItemType.WEAPON));
        inventory.add((ArrayList<Item>) itemsList.get(Rarity.RARE).get(Inventory.ItemType.WEAPON));
        inventory.add((ArrayList<Item>) itemsList.get(Rarity.SUPER_RARE).get(Inventory.ItemType.WEAPON));
        inventory.add((ArrayList<Item>) itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE));


    }

    private void generateCommonConsumable() {
        ItemPotionAttack attackPotion = new ItemPotionAttack();
        ItemPotionHealth healthPotion = new ItemPotionHealth();
        ItemPotionReplenish replenishingPotion = new ItemPotionReplenish();
        ItemPotionRestore restoringPotion = new ItemPotionRestore();

        Image foodImage1 = new Image("resources/Items/Consumable/Apple.png");
        Image foodImage2 = new Image("resources/Items/Consumable/Bread.png");
        Image foodImage3 = new Image("resources/Items/Consumable/Cake.png");
        Image foodImage4 = new Image("resources/Items/Consumable/ChickenNuggets.png");
        Image foodImage5 = new Image("resources/Items/Consumable/Cookie.png");
        Image foodImage6 = new Image("resources/Items/Consumable/Potato.png");

        ItemConsumable food1 = new ItemConsumable(
                foodImage1, 10, "Apple", "Restores health\nby 10 points", 10);
        ItemConsumable food2 = new ItemConsumable(
                foodImage2, 15, "Bread", "Restores health\nby 10 points", 15);
        ItemConsumable food3 = new ItemConsumable(
                foodImage3, 50, "Cake", "Restores health\nby 50 points", 50);
        ItemConsumable food4 = new ItemConsumable(
                foodImage4, 30, "Chicken Nuggets", "Restores health\nby 30 points", 30);
        ItemConsumable food5 = new ItemConsumable(
                foodImage5, 20, "Cookie", "Restores health\nby 20 points", 20);
        ItemConsumable food6 = new ItemConsumable(
                foodImage6, 40, "Potato", "Restores health\nby 40 points", 40);

        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.CONSUMABLE).add(attackPotion);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.CONSUMABLE).add(healthPotion);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.CONSUMABLE).add(replenishingPotion);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.CONSUMABLE).add(restoringPotion);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.CONSUMABLE).add(food1);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.CONSUMABLE).add(food2);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.CONSUMABLE).add(food3);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.CONSUMABLE).add(food4);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.CONSUMABLE).add(food5);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.CONSUMABLE).add(food6);


    }

    private void generateCommonWeapon() {
        //Image weaponIcon, WeaponType weaponType,
        //                      double value, int damage, int consumedStamina,
        //                      String name, String description

        Image weapon2Image = new Image("resources/Items/Weapons&Spells/WeaponSword2.png");
        Image weapon6Image = new Image("resources/Items/Weapons&Spells/WeaponSword6.png");
        Image weapon12Image = new Image("resources/Items/Weapons&Spells/WeaponAxe2.png");


        ItemWeapon weapon2 =  new ItemWeapon(weapon2Image, ItemWeapon.WeaponType.SWORD,
                20, 10, 10, "Boomer", "Not really in with the times", 0);
        ItemWeapon weapon6 = new ItemWeapon(weapon6Image, ItemWeapon.WeaponType.SWORD,
                20, 10, 10, "Steel Sword", "Made of only the finest steel", 0);
        ItemWeapon weapon12 = new ItemWeapon(weapon12Image, ItemWeapon.WeaponType.AXE,
                20, 20, 20, "Simple Axe",
                "A simple tool for a simple man", 0);

        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEAPON).add(weapon2);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEAPON).add(weapon6);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEAPON).add(weapon12);


    }

    private void generateCommonMisc() {

    }

    private void generateCommonSpell() {

    }

    private void generateCommonWearable() {
        Image wearableImage1 = new Image("resources/Items/Wearable/ArmorHelmetSteel.png");
        Image wearableImage2 = new Image("resources/Items/Wearable/ArmorChestSteel.png");
        Image wearableImage3 = new Image("resources/Items/Wearable/ArmorPantsSteel.png");
        Image wearableImage5 = new Image("resources/Items/Wearable/ArmorHelmetGold.png");
        Image wearableImage6 = new Image("resources/Items/Wearable/ArmorChestGold.png");
        Image wearableImage7 = new Image("resources/Items/Wearable/ArmorPantsGold.png");
        Image wearableImage8 = new Image("resources/Items/Wearable/ArmorHelmetTitanium.png");
        Image wearableImage9 = new Image("resources/Items/Wearable/ArmorChestTitanium.png");
        Image wearableImage10 = new Image("resources/Items/Wearable/ArmorPantsTitanium.png");
        Image wearableImage11 = new Image("resources/Items/Wearable/ArmorHelmetMagic.png");
        Image wearableImage12 = new Image("resources/Items/Wearable/ArmorPantsMagic.png");
        Image wearableImage13 = new Image("resources/Items/Wearable/ArmorChestMagic.png");


        Image wearableImage14 = new Image("resources/Items/Wearable/JewelryNecklace4.png");
        Image wearableImage15 = new Image("resources/Items/Wearable/JewelryNecklace5.png");
        Image wearableImage16 = new Image("resources/Items/Wearable/JewelryNecklace6.png");
        Image wearableImage17 = new Image("resources/Items/Wearable/WearableNecklace1.png");
        Image wearableImage18 = new Image("resources/Items/Wearable/WearableNecklace.png");
        Image wearableImage19 = new Image("resources/Items/Wearable/WearableNecklace3.png");

        Image wearableImage20 = new Image("resources/Items/Wearable/JewelryRing1.png");
        Image wearableImage21 = new Image("resources/Items/Wearable/JewelryRing2.png");
        Image wearableImage22 = new Image("resources/Items/Wearable/JewelryRing3.png");
        Image wearableImage23 = new Image("resources/Items/Wearable/JewelryRing4.png");
        Image wearableImage24 = new Image("resources/Items/Wearable/JewelryRing6.png");
        Image wearableImage25 = new Image("resources/Items/Wearable/JewelryRing7.png");
        Image wearableImage26 = new Image("resources/Items/Wearable/JewelryRing9.png");
        Image wearableImage27 = new Image("resources/Items/Wearable/JewelryRing11.png");
        Image wearableImage28 = new Image("resources/Items/Wearable/JewelryRing14.png");
        Image wearableImage29 = new Image("resources/Items/Wearable/JewelryRing15.png");

        ItemWearable itemWearable1 = new ItemWearable(wearableImage1, 10, ItemWearable.WearableType.HELMET,
        20, "Steel Helmet", "Steel Helmet");
        ItemWearable itemWearable2 = new ItemWearable(wearableImage2, 10, ItemWearable.WearableType.CHEST,
                20, "Steel Chest", "Steel Helmet");
        ItemWearable itemWearable3 = new ItemWearable(wearableImage3, 10, ItemWearable.WearableType.PANTS,
                20, "Steel Pants", "Steel Helmet");
        ItemWearable itemWearable4 = new ItemWearable(wearableImage5, 15, ItemWearable.WearableType.HELMET,
                50, "Gold Helmet", "Steel Helmet");
        ItemWearable itemWearable5 = new ItemWearable(wearableImage6, 15, ItemWearable.WearableType.CHEST,
                50, "Gold Chest", "Steel Helmet");
        ItemWearable itemWearable6 = new ItemWearable(wearableImage7, 15, ItemWearable.WearableType.PANTS,
                50, "Gold Pants", "Steel Helmet");
        ItemWearable itemWearable7 = new ItemWearable(wearableImage8, 20, ItemWearable.WearableType.HELMET,
                75, "Titanium Helmet", "Steel Helmet");
        ItemWearable itemWearable8 = new ItemWearable(wearableImage9, 20, ItemWearable.WearableType.CHEST,
                75, "Titanium Chest", "Steel Helmet");
        ItemWearable itemWearable9 = new ItemWearable(wearableImage10, 20, ItemWearable.WearableType.PANTS,
                75, "Titanium Pants", "Steel Helmet");
        ItemWearable itemWearable10 = new ItemWearable(wearableImage11, 30, ItemWearable.WearableType.HELMET,
                125, "Magic Helmet", "Steel Helmet");
        ItemWearable itemWearable11 = new ItemWearable(wearableImage12, 30, ItemWearable.WearableType.CHEST,
                125, "Magic Chest", "Steel Helmet");
        ItemWearable itemWearable12 = new ItemWearable(wearableImage13, 30, ItemWearable.WearableType.PANTS,
                125, "Magic Pants", "Steel Helmet");

        ItemWearable itemWearable13 = new ItemWearable(wearableImage14, 10, ItemWearable.WearableType.NECKLACE,
                10, "Necklace 1", "Necklace");
        ItemWearable itemWearable14 = new ItemWearable(wearableImage15, 10, ItemWearable.WearableType.NECKLACE,
                10, "Necklace 2", "Necklace");
        ItemWearable itemWearable15 = new ItemWearable(wearableImage16, 10, ItemWearable.WearableType.NECKLACE,
                10, "Necklace 3", "Necklace");
        ItemWearable itemWearable16 = new ItemWearable(wearableImage17, 10, ItemWearable.WearableType.NECKLACE,
                10, "Necklace 4", "Necklace");
        ItemWearable itemWearable17 = new ItemWearable(wearableImage18, 10, ItemWearable.WearableType.NECKLACE,
                10, "Necklace 5", "Necklace");
        ItemWearable itemWearable18 = new ItemWearable(wearableImage19, 10, ItemWearable.WearableType.NECKLACE,
                10, "Necklace 6", "Necklace");

        ItemWearable itemWearable19 = new ItemWearable(wearableImage20, 10, ItemWearable.WearableType.RING,
                10, "Ring 1", "Necklace");
        ItemWearable itemWearable20 = new ItemWearable(wearableImage21, 10, ItemWearable.WearableType.RING,
                10, "Ring 2", "Necklace");
        ItemWearable itemWearable21 = new ItemWearable(wearableImage22, 10, ItemWearable.WearableType.RING,
                10, "Ring 3", "Necklace");
        ItemWearable itemWearable22 = new ItemWearable(wearableImage23, 10, ItemWearable.WearableType.RING,
                10, "Ring 4", "Necklace");
        ItemWearable itemWearable23 = new ItemWearable(wearableImage24, 10, ItemWearable.WearableType.RING,
                10, "Ring 5", "Necklace");
        ItemWearable itemWearable24 = new ItemWearable(wearableImage25, 10, ItemWearable.WearableType.RING,
                10, "Ring 6", "Necklace");
        ItemWearable itemWearable25 = new ItemWearable(wearableImage26, 10, ItemWearable.WearableType.RING,
                10, "Ring 7", "Necklace");
        ItemWearable itemWearable26 = new ItemWearable(wearableImage27, 10, ItemWearable.WearableType.RING,
                10, "Ring 8", "Necklace");
        ItemWearable itemWearable27 = new ItemWearable(wearableImage28, 10, ItemWearable.WearableType.RING,
                10, "Ring 9", "Necklace");
        ItemWearable itemWearable28 = new ItemWearable(wearableImage29, 10, ItemWearable.WearableType.RING,
                10, "Ring 10", "Necklace");


        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable1);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable2);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable3);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable4);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable5);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable6);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable7);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable8);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable9);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable10);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable11);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable12);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable13);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable14);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable15);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable16);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable17);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable18);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable19);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable20);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable21);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable22);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable23);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable24);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable25);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable26);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable27);
        itemsList.get(Rarity.COMMON).get(Inventory.ItemType.WEARABLE).add(itemWearable28);




    }

    /*
     * Generating uncommon item lists
     */
    private void generateUncommonConsumable() {

    }

    private void generateUncommonWeapon() {
        Image weapon1Image = new Image("resources/Items/Weapons&Spells/WeaponSword9.png");
        Image weapon3Image = new Image("resources/Items/Weapons&Spells/WeaponSword3.png");
        Image weapon5Image = new Image("resources/Items/Weapons&Spells/WeaponSword5.png");
        Image weapon13Image = new Image("resources/Items/Weapons&Spells/WeaponAxe3.png");



        ItemWeapon weapon3 =  new ItemWeapon(weapon3Image, ItemWeapon.WeaponType.SWORD,
                50, 25, 15, "Satan's Sword", "Used by the devil himself", 2);
        ItemWeapon weapon5 = new ItemWeapon(weapon5Image, ItemWeapon.WeaponType.SWORD,
                30, 20, 10, "Golden Sword", "Made of only the finest gold", 2);
        ItemWeapon weapon1 =  new ItemWeapon(weapon1Image, ItemWeapon.WeaponType.SWORD,
                45, 20, 10, "Swisher", "Swishes real fast", 2);
        ItemWeapon weapon13 =  new ItemWeapon(weapon13Image, ItemWeapon.WeaponType.AXE,
                45, 30, 30, "Oreo", "Not for human consumption", 2);


        itemsList.get(Rarity.UNCOMMON).get(Inventory.ItemType.WEAPON).add(weapon3);
        itemsList.get(Rarity.UNCOMMON).get(Inventory.ItemType.WEAPON).add(weapon5);
        itemsList.get(Rarity.UNCOMMON).get(Inventory.ItemType.WEAPON).add(weapon1);
        itemsList.get(Rarity.UNCOMMON).get(Inventory.ItemType.WEAPON).add(weapon13);

    }

    private void generateUncommonMisc() {

    }

    private void generateUncommonSpell() {

    }

    private void generateUncommonWearable() {

    }

    /*
     * Generating rare item lists
     */
    private void generateRareConsumable() {

    }

    private void generateRareWeapon() {

        Image weapon7Image = new Image("resources/Items/Weapons&Spells/WeaponSword7.png");
        Image weapon8Image = new Image("resources/Items/Weapons&Spells/WeaponSword8.png");
        Image weapon4Image = new Image("resources/Items/Weapons&Spells/WeaponSword4.png");
        Image weapon16Image = new Image("resources/Items/Weapons&Spells/WeaponAxe6.png");
        Image weapon15Image = new Image("resources/Items/Weapons&Spells/WeaponAxe4.png");


        ItemWeapon weapon7 = new ItemWeapon(weapon7Image, ItemWeapon.WeaponType.SWORD,
                75, 40, 20, "Nightmare", "Will give your enemies the hibby gibbies", 5);
        ItemWeapon weapon8 = new ItemWeapon(weapon8Image, ItemWeapon.WeaponType.SWORD,
                60, 30, 15, "Thicc Boy", "Yeah, he real thicc", 5);
        ItemWeapon weapon4 = new ItemWeapon(weapon4Image, ItemWeapon.WeaponType.SWORD,
                50, 25, 15, "Angel Sword", "As beautiful as an angel", 5);
        ItemWeapon weapon5 = new ItemWeapon(weapon16Image, ItemWeapon.WeaponType.SWORD,
                50, 25, 15, "Blood Demon", "Craves the yummy yummy taste of blood", 5);


        itemsList.get(Rarity.RARE).get(Inventory.ItemType.WEAPON).add(weapon4);
        itemsList.get(Rarity.RARE).get(Inventory.ItemType.WEAPON).add(weapon7);
        itemsList.get(Rarity.RARE).get(Inventory.ItemType.WEAPON).add(weapon8);
    }

    private void generateRareMisc() {

    }

    private void generateRareSpell() {

    }

    private void generateRareWearable() {

    }

    /*
     * Generate super rare item lists
     */
    private void generateSuperRareConsumable() {
        Image itemImage = new Image("resources/Items/Consumable/PotionPurple.png");
        ItemConsumable itemConsumable = new ItemConsumable(itemImage, 100000, "Venom",
                "Completely\nrestores HP", 100);
        itemsList.get(Rarity.SUPER_RARE).get(Inventory.ItemType.CONSUMABLE).add(itemConsumable);
    }

    private void generateSuperRareWeapon() {

        Image weapon9Image = new Image("resources/Items/Weapons&Spells/WeaponSword10.png");
        Image weapon10Image = new Image("resources/Items/Weapons&Spells/WeaponSword11.png");
        Image weapon11Image = new Image("resources/Items/Weapons&Spells/WeaponSword12.png");
        Image weapon14Image = new Image("resources/Items/Weapons&Spells/WeaponAxe5.png");


        ItemWeapon weapon9 = new ItemWeapon(weapon9Image, ItemWeapon.WeaponType.SWORD,
                100, 40, 10, "Bad Barbie", "The prettiest sword out there", 10);
        ItemWeapon weapon10 = new ItemWeapon(weapon10Image, ItemWeapon.WeaponType.SWORD,
                100, 50, 20, "Light in the Dark", "When you need both a glow stick and a sword", 10);
        ItemWeapon weapon11 = new ItemWeapon(weapon11Image, ItemWeapon.WeaponType.SWORD,
                150, 60, 30, "Purple Thunder",
                "The little sword in the middle is useless, why is it there", 10);
        ItemWeapon weapon12 = new ItemWeapon(weapon14Image, ItemWeapon.WeaponType.AXE,
                150, 70, 5, "Flash",
                "You wont' even see it coming", 10);


        itemsList.get(Rarity.SUPER_RARE).get(Inventory.ItemType.WEAPON).add(weapon9);
        itemsList.get(Rarity.SUPER_RARE).get(Inventory.ItemType.WEAPON).add(weapon10);
        itemsList.get(Rarity.SUPER_RARE).get(Inventory.ItemType.WEAPON).add(weapon11);
        itemsList.get(Rarity.SUPER_RARE).get(Inventory.ItemType.WEAPON).add(weapon12);

    }

    private void generateSuperRareMisc() {

    }

    private void generateSuperRareSpell() {

    }

    private void generateSuperRareWearable() {

    }



}
