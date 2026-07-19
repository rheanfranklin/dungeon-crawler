package tests;

import game.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class InventoryTests {
    private Inventory inv;
    private HashMap<Inventory.ItemType, ArrayList<Item>> list;

    @Before
    public void setup() {
        inv = new Inventory();
        list = inv.getItemHashMap();
    }
    @Test
    public void testCategories() {
        assertNotNull(list.get(Inventory.ItemType.WEAPON));
        assertNotNull(list.get(Inventory.ItemType.SPELL));
        assertNotNull(list.get(Inventory.ItemType.CONSUMABLE));
        assertNotNull(list.get(Inventory.ItemType.WEARABLE));
        assertNotNull(list.get(Inventory.ItemType.MISC));
    }

    @Test
    public void testAddItem() {
        ItemWeapon weapon = new ItemWeapon(
                null, ItemWeapon.WeaponType.AXE, "testWeapon", "testing");
        inv.add(weapon);
        ArrayList<Item> weaponList = list.get(Inventory.ItemType.WEAPON);
        double size = weaponList.size();
        assertEquals(1, size);

    }

    @Test
    public void testRemoveItem() {
        ItemWeapon weapon = new ItemWeapon(
                null, ItemWeapon.WeaponType.AXE, "testWeapon", "testing");
        inv.add(weapon);
        ArrayList<Item> weaponList = list.get(Inventory.ItemType.WEAPON);
        inv.removeItem(weapon);
        double size = weaponList.size();
        assertEquals(0, size);

    }

    @Test
    public void testNumberOfItemsAdded() {
        Inventory inventory = new Inventory();
        ListOfItems listOfItems = new ListOfItems();
        Item item = new ItemPotionHealth();
        inventory.add(item);
        assertEquals(1, inventory.numOfItems());
    }

    @Test
    public void testAddSpecificItem() {
        ItemWeapon weapon = new ItemWeapon(
                null, ItemWeapon.WeaponType.AXE, "testWeapon", "testing");
        inv.addWeapon(weapon);
        assertEquals(1, list.get(Inventory.ItemType.WEAPON).size());
        ItemConsumable consumable = new ItemConsumable(null, 0, "testName", "testDescription", 0);
        inv.addConsumable(consumable);
        assertEquals(1, list.get(Inventory.ItemType.CONSUMABLE).size());
    }

    @Test
    public void testAddCapacity() {
        list.get(Inventory.ItemType.WEAPON).clear();
        for (int i = 0; i < 10; i++) {
            ItemWeapon weapon = new ItemWeapon(
                    null, ItemWeapon.WeaponType.AXE, "weapon: " + i, "desc: " + i);
            inv.add(weapon);
        }
        assertEquals(10, list.get(Inventory.ItemType.WEAPON).size());

        ItemWeapon weapon = new ItemWeapon(
                null, ItemWeapon.WeaponType.AXE, "weapon: 11", "desc: 11");
        inv.add(weapon);
        assertEquals(10, list.get(Inventory.ItemType.WEAPON).size());
    }
}
