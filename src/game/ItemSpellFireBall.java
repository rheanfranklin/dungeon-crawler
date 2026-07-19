package game;

import javafx.scene.image.Image;

public class ItemSpellFireBall extends ItemSpell {

    private static final Image FIRE_BALL_ICON =
            new Image("resources/Items/Weapons&Spells/SpellRed.png");

    private int damage;

    public ItemSpellFireBall(Image spellIcon, String itemName,
                             String spellDescription, Level level) {
        super(spellIcon, itemName, spellDescription, level);
    }

    public void cast(Player player) {

    }

    public void startInventoryLoop() {

    }

    public void stopInventoryLoop() {

    }

    public void mouseClickedButton() {

    }
}
