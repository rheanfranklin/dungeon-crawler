package tests;

import game.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ItemTests {

    private ItemWeapon weapon;

    @Before
    public void setup() {
        weapon = new ItemWeapon(null, ItemWeapon.WeaponType.AXE, "testWeapon", "testing");
    }


    @Test
    public void itemWeaponConstructed() {
        assertEquals(10, weapon.getDamage());
        assertEquals(Inventory.ItemType.WEAPON, weapon.getItemType());
        assertEquals("testWeapon", weapon.getItemName());
        assertEquals("testing", weapon.getItemDescription());
    }

    @Test
    public void testWeaponConsumeStamina() {
        Player player = new Player();
        weapon.equipWeapon(player);
        assertEquals(10, player.getConsumeStamina());
    }

    @Test
    public void testWeaponAddsAttackDamage() {
        Player player = new Player();
        weapon.equipWeapon(player);
        double expectedDamage = weapon.getDamage();
        assertEquals(expectedDamage, player.getAttackDamage());
    }

    @Test
    public void testCantStackWeapon() {
        try {
            weapon.changeQuantity(2);
            Assert.assertTrue("Weapon should not be stackable", false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testUnequipWeapon() {
        Player player = new Player();
        int regularDamage = player.getAttackDamage();
        double regularConsumeStamina = player.getConsumeStamina();
        weapon.equipWeapon(player);
        weapon.unEquipWeapon(player);

        assertEquals(regularDamage, player.getAttackDamage());
        assertEquals(regularConsumeStamina, player.getConsumeStamina());
        assertFalse(weapon.getEquipped());
        assertNull(player.getEquippedWeapon());
    }

    @Test
    public void testPotionRestore() {
        Player player = new Player();
        player.setStamina(10);
        ItemPotionRestore restore = new ItemPotionRestore();
        restore.consume(player);
        assertEquals(20, player.getStamina());
    }

    @Test
    public void testPotionReplenish() {
        Player player = new Player();
        player.setStamina(10);
        ItemPotionReplenish replenish = new ItemPotionReplenish();
        replenish.consume(player);
        assertEquals(20, player.getStamina());
    }

    @Test
    public void testPotionHealth() {
        Player player = new Player();
        player.setHealth(10);
        ItemPotionHealth health = new ItemPotionHealth();
        health.consume(player);
        assertEquals(20, player.getHealth());
    }

    @Test
    public void testPotionAttack() {
        Player player = new Player();
        player.setAttackDamage(10);
        ItemPotionAttack attack = new ItemPotionAttack();
        attack.consume(player);
        assertEquals(20, player.getAttackDamage());
    }

}
