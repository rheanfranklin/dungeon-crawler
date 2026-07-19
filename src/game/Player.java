package game;

import javafx.scene.image.Image;

/**
 * Creates an instance of a game
 * that holds all the attributes specific to
 * an individual player
 */
public class Player {

    /**
     * The name the player entered at the beginning of the game
     */
    private String name;

    /**
     * The image of the character that the player selected
     */
    private Image image;

    /**
     * The current & max health of the player
     * Dependent on difficulty
     */
    private int currentHealth;
    private int maxHealth;

    /**
     * the current & max stamina of the player
     * Will be used to attack a monster using a weapon
     * Dependent on difficulty
     */
    private double currentStamina;
    private double maxStamina;
    private double consumeStamina;

    /**
     * The current & max mana of the player
     * Will be used to cast spells
     * Dependent on difficulty
     */
    private double currentMana;
    private double maxMana;

    /**
     * Defense of the player
     * A higher defense will lower
     * the attack damage done by monsters
     */
    private int defense;

    /**
     * How much damage the player does when
     * they attack a monster
     */
    private int attackDamage;

    /**
     * How much money the player has
     * Dependent on difficulty
     */
    private int money;

    /**
     * How much experience the player has
     */
    private int experience;

    private int level;

    /**
     * Speed at which the player's stamina replenishes itself
     */
    private double staminaReplenishSpeed;

    private double manaReplenishSpeed;


    /**
     * The animation sequence associated with the player;
     */
    private SequencePlayer sequencePlayer;

    /**
     * The inventory of the player
     */
    private Inventory inventory;

    private int increasePoints;

    /**
     * Corresponds to the difficulty that the player selected
     */
    public enum Difficulty {
        INSANE,
        NORMAL,
        EASY
    }

    private Difficulty difficulty;

    /**
     * Factor that will determine the attack damage
     * and health of the monsters the player encounters
     * based on the difficulty they selected
     */
    private double difficultyFactor;

    private State state;

    private ItemWeapon equippedWeapon;

    private ItemWearable equippedNecklace;

    private ItemWearable equippedRing;

    private ItemWearable equippedHelmet;

    private ItemWearable equippedChest;

    private ItemWearable equippedPants;


    /**
     * Player constructor
     *
     * @param name       name of the player
     * @param image      character image associated with player
     * @param difficulty difficulty the player selected
     */
    public Player(String name, Image image, Difficulty difficulty) {
        this.name = name;
        this.image = image;
        this.difficulty = difficulty;
        this.experience = 0;
        this.defense = 0;
        this.sequencePlayer = new SequencePlayer();
        this.inventory = new Inventory();
        this.state = State.NORMAL;
        this.consumeStamina = 10;
        this.increasePoints = 0;
        this.level = level;
        //sets equipped weapon to be fists
        //this.equippedWeapon = new ItemWeapon(null,
        // ItemWeapon.WeaponType.FISTS,0, 0, 10, "Fists", "fists");
        this.staminaReplenishSpeed = 0.1;
        this.manaReplenishSpeed = 0.1;

        //setting up player stats based on difficulty
        if (difficulty.equals(Difficulty.INSANE)) {
            this.money = 0;

            this.currentHealth = 75;
            this.maxHealth = 75;

            this.currentStamina = 40;
            this.maxStamina = 40;

            this.currentMana = 75;
            this.maxMana = 75;

            this.attackDamage = 10;
        } else if (difficulty.equals(Difficulty.NORMAL)) {
            this.money = 250;

            this.currentHealth = 100;
            this.maxHealth = 100;

            this.currentStamina = 50;
            this.maxStamina = 50;

            this.currentMana = 100;
            this.maxMana = 100;

            this.attackDamage = 15;
        } else if (difficulty.equals(Difficulty.EASY)) {
            this.money = 500;

            this.currentHealth = 150;
            this.maxHealth = 150;

            this.currentStamina = 60;
            this.maxStamina = 60;

            this.currentMana = 150;
            this.maxMana = 150;

            this.attackDamage = 20;
        }
    }

    /**
     * Creates instance of player
     * Uses constructor chaining
     * Default difficulty is easy
     */
    public Player() {
        this("", null, Difficulty.EASY);
    }

    /**
     * Invoked when the player attacks a monster
     *
     * @param monster that the player attacks
     */
    public void attack(Monster monster) {
        monster.takeDamage(this);
    }

    /**
     * Invoked when monster attacks the player
     *
     * @param monster that attacks the player
     */
    public void takeDamage(Monster monster) {
        currentHealth = currentHealth - monster.getAttackDamage() + defense;
    }

    /*
     * All getters & setters for the player's variables
     */
    public int getHealth() {
        return currentHealth;
    }

    public void setHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getStamina() {
        return currentStamina;
    }

    public void setStamina(double stamina) {
        currentStamina = stamina;
    }

    public double getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(double maxStamina) {
        this.maxStamina = maxStamina;
    }

    public double getConsumeStamina() {
        return consumeStamina;
    }

    public void setConsumeStamina(double consumeStamina) {
        this.consumeStamina = consumeStamina;
    }

    public double getMana() {
        return this.currentMana;
    }

    public void setMana(double mana) {
        this.currentMana = mana;
    }

    public double getMaxMana() {
        return this.maxMana;
    }

    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setLevel(int level) { this.level = level; }

    public int getLevel() { return level; }

    public double getStaminaReplenishSpeed() {
        return this.staminaReplenishSpeed;
    }

    public void setStaminaReplenishSpeed(double staminaReplenishSpeed) {
        this.staminaReplenishSpeed = staminaReplenishSpeed;
    }

    public int getIncreasePoints() { return increasePoints; }

    public void setIncreasePoints(int increasePoints) { this.increasePoints = increasePoints; }

    public double getManaReplenishSpeed() {
        return this.manaReplenishSpeed;
    }

    public void setManaReplenishSpeed(double manaReplenishSpeed) {
        this.manaReplenishSpeed = manaReplenishSpeed;
    }

    public ItemWeapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(ItemWeapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public ItemWearable getEquippedNecklace() { return equippedNecklace; }

    public void setEquippedNecklace(ItemWearable equippedNecklace) {
        this.equippedNecklace = equippedNecklace;
    }

    public ItemWearable getEquippedRing() { return equippedRing; }

    public void setEquippedRing(ItemWearable equippedRing) {
        this.equippedRing = equippedRing;
    }

    public ItemWearable getEquippedHelmet() { return equippedHelmet; }

    public void setEquippedHelmet(ItemWearable equippedHelmet) {
        this.equippedHelmet = equippedHelmet;
    }

    public ItemWearable getEquippedChest() { return equippedChest; }

    public void setEquippedChest(ItemWearable equippedChest) {
        this.equippedChest = equippedChest;
    }

    public ItemWearable getEquippedPants() { return equippedPants; }

    public void setEquippedPants(ItemWearable equippedPants) {
        this.equippedPants = equippedPants;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int newMoney) {
        this.money = newMoney;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public double getDifficultyFactor() {
        return difficultyFactor;
    }

    /**
     * @return image of the player
     */
    public Image getImage() {
        return this.image;
    }

    public SequencePlayer getSequencePlayer() {
        return sequencePlayer; }

    public Inventory getInventory() {
        return inventory; }

    /**
     * An alternate constructor for testing purposes that circumvents initializing graphics
     * @param diff the difficulty selected
     */
    public Player(Difficulty diff) {
        this.experience = 0;
        if (diff.equals(Difficulty.INSANE)) {
            this.money = 0;
            this.currentHealth = 50;
            this.currentMana = 50;
            this.attackDamage = 10;
        } else if (diff.equals(Difficulty.NORMAL)) {
            this.money = 100;
            this.currentHealth = 100;
            this.currentMana = 100;
            this.attackDamage = 15;
        } else if (diff.equals(Difficulty.EASY)) {
            this.money = 500;
            this.currentHealth = 150;
            this.currentMana = 150;
            this.attackDamage = 20;
        }
    }
}
