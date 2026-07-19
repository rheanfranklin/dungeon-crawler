package game;

import win.Winscreen;

/**
 * Class to hold all instances
 * of monsters
 */
public class Monster {
    /**
     * Default health of monster used
     * when no health argument is given
     * to monster constructor
     */
    protected static final int DEFAULT_HEALTH = 100;

    /**
     * Default damage of monster used
     * when no attackDamage argument is given
     * to monster constructor
     */
    protected static final int DEFAULT_ATTACK_DAMAGE = 10;

    /**
     * Default experience returned to player
     * when no returnExperience argument is given
     * to monster constructor
     */
    protected static final int DEFAULT_RETURN_EXPERIENCE = 10;

    /**
     * Name of monster
     */
    private final String name;

    /**
     * Current health of monster
     */
    private int health;

    /**
     * Max health of the monster
     */
    private final int maxHealth;

    /**
     * Experience monster gives to Player
     * whenever killed
     */
    private final int returnExperience;

    /**
     * Damage monster does to player
     * whenever attack() method is invoked
     */
    private final int attackDamage;

    /**
     * Animation sequence associated with given monster
     */
    private final SequenceMonster sequenceMonster;

    /**
     * Boolean for whether the monster has
     * already given player experience
     */
    private boolean gaveExperience;

    private State state;

    public enum ItemDropType {
        COMMON_CONSUMABLE,
        COMMON_WEAPON,
        COMMON_SPELL,
        COMMON_MISC,
        COMMON_WEARABLE,

        UNCOMMON_CONSUMABLE,
        UNCOMMON_WEAPON,
        UNCOMMON_SPELL,
        UNCOMMON_MISC,
        UNCOMMON_WEARABLE,

        RARE_CONSUMABLE,
        RARE_WEAPON,
        RARE_SPELL,
        RARE_MISC,
        RARE_WEARABLE,

        SUPER_RARE_CONSUMABLE,
        SUPER_RARE_WEAPON,
        SUPER_RARE_SPELL,
        SUPER_RARE_MISC,
        SUPER_RARE_WEARABLE,
    };

    private ItemDropType itemDropType;

    /**
     * Monster constructor
     *
     * @param name name of monster to be displayed on screen
     * @param health health of monster
     * @param attackDamage damage monster does to player when it attacks
     * @param returnExperience experience returned when monster is killed
     * @param sequenceMonster the sequence associated with the given monster
     */
    public Monster(String name, int health, int attackDamage,
                   int returnExperience, SequenceMonster sequenceMonster) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attackDamage = attackDamage;
        this.returnExperience = returnExperience;
        this.sequenceMonster = sequenceMonster;
        this.gaveExperience = false;
        this.state = State.NORMAL;
    }

    /**
     * Monster constructor
     * Uses constructor chaining
     */
    public Monster() {
        this("Monster", DEFAULT_HEALTH, DEFAULT_ATTACK_DAMAGE, DEFAULT_RETURN_EXPERIENCE, null);
    }

    /**
     * Invoked whenever a monster attacks a player
     * @param player the player the monster attacks
     */
    public void attack(Player player) {
        player.takeDamage(this);
    }

    /**
     * Invoked whenever a monster is attacked by player
     * Damage done depends on player's strength
     * @param player the player that attacks the monster
     */
    public void takeDamage(Player player) {
        health = health - player.getAttackDamage();
    }

    public void takeDamage(int damage) {
        health = health - damage;
    }

    public void kill() {
        health = 0;
    }


    /**
     * Gives experience to player when invoked
     *
     * @param player the player to give experience to
     */
    public void giveExperience(Player player) {
        if (health > 0) {
            throw new IllegalArgumentException("The monster's not dead yet!"
                    + "It can't give experience.");
        } else if (gaveExperience) {
            return;
        }
        player.setExperience(player.getExperience() + returnExperience);
        gaveExperience = true;
        Winscreen.setExperience(player.getExperience());
    }
    /**
     * @return boolean of whether the monster is dead
     */
    public boolean isDead() {
        return health <= 0;
    }

    /**
     * @return the attack damage of the monster
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * @return name of Monster
     */
    public String getName() {
        return name;
    }

    /**
     * @return health of monster
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return max health of monster
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * @return monster icon
     */
    public SequenceMonster getSequenceMonster() {
        return sequenceMonster;
    }

    /**
     * @return return experience
     */
    public int getReturnExperience() {
        gaveExperience = true;
        return returnExperience;
    }

    public boolean getGaveExperience() {
        return gaveExperience;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setItemDropType(ItemDropType itemDropType) {
        this.itemDropType = itemDropType;
    }

    public Item generateDropItem() {
        if (itemDropType != null) {
            ListOfItemsGenerate listOfItemsGenerate = new ListOfItemsGenerate();
            return listOfItemsGenerate.generateItem(itemDropType);
        } else {
            return null;
        }
    }


}
