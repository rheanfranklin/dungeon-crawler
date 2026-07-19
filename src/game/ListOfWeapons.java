package game;

import javafx.scene.image.Image;

public class ListOfWeapons {

    public ItemWeapon createStartingSword() {
        Image weaponImage = new Image("resources/Items/Weapons&Spells/WeaponSword1.png");
        ItemWeapon weapon = new ItemWeapon(weaponImage, ItemWeapon.WeaponType.SWORD,
                "Slicer", "Slices enemies with\nextreme precision");
        return weapon;
    }

    public void createStartingSpell() {

    }
}
