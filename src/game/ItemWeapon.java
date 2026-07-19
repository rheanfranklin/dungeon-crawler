package game;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;

public class ItemWeapon extends Item {
    private static final int DEFAULT_DAMAGE = 10;

    private static final int DEFAULT_CONSUMED_STAMINA = 10;

    private HBox holdName;
    private HBox holdConsume;
    private HBox holdDamage;
    private HBox holdValue;
    private HBox holdLevel;

    public enum WeaponType {
        AXE,
        SWORD,
        FISTS,
        HAMMER
    }

    private final WeaponType weaponType;

    private int consumedStamina;

    private int damage;

    private boolean equipped;

    private int level;

    /**
     * The X coordinates of
     * the weapon icon relative to the player
     * depending on whether the player is facing
     * left or right
     */
    private double rightXCoord;

    private double leftXCoord;

    /**
     * The Y coordinates of
     * the weapon icon relative to the player
     * depending on whether the player icon
     * is in the first frame of the spire motion
     * or the second frame
     */
    private double firstYCoord;

    private double secondYCoord;

    private AnimationTimer inventoryLoop;

    private AnimationTimer merchantLoop;

    /**
     *
     * @param weaponIcon image of the weapon
     * @param weaponType weapon type of the weapon
     * @param name name of the weapon
     * @param description description of the weapon
     */
    public ItemWeapon(Image weaponIcon, WeaponType weaponType, String name, String description) {
        this(weaponIcon, weaponType, DEFAULT_VALUE,
                DEFAULT_DAMAGE, DEFAULT_CONSUMED_STAMINA, name, description, 1);
    }

    /**
     *
     * @param weaponIcon image of the weapon
     * @param weaponType weapon type of the weapon
     * @param value money value of the weapon
     * @param damage damage of the weapon
     * @param consumedStamina how much stamina the weapon consumes
     * @param name the name of the weapon
     * @param description description of the weapon
     */
    public ItemWeapon(Image weaponIcon, WeaponType weaponType,
                      double value, int damage, int consumedStamina,
                      String name, String description, int level) {
        super(Inventory.ItemType.WEAPON, weaponIcon, value, name, description, 1);
        this.weaponType = weaponType;
        this.damage = damage;
        this.consumedStamina = consumedStamina;
        this.level = level;
    }

    /**
     * Starts the animation timer whenever
     * player opens the inventory screen
     * so player can interact with the weapon icon
     */
    public void startInventoryLoop() {
        Text namePrompt = setText("Name: ");
        Text damagePrompt = setText("Damage: ");
        Text consumePrompt = setText("Consumes: ");
        Text valuePrompt = setText("Value: ");
        Text levelPrompt = setText("Level: ");

        Text weaponName = setText(itemName);
        Text weaponDamage = setText("-" + damage + " HP");
        Text weaponConsume = setText("-" + consumedStamina + " ST");
        Text weaponValue = setText("$" + value);
        Text weaponLevel = setText("" + level);

        ItemWeapon weapon = this;

        this.inventoryLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                weapon.iconBox.setOnMouseClicked(e -> {
                    controller.selectedItem = weapon;

                    weaponDamage.setFill(Color.LIMEGREEN);
                    weaponConsume.setFill(Color.CRIMSON);
                    weaponValue.setFill(Color.CYAN);
                    weaponLevel.setFill(Color.MAGENTA);

                    controller.bigIconDisplay.setFill(new ImagePattern(weapon.getItemIcon()));

                    if (!controller.holdIconStats.getChildren().contains(controller.button)) {
                        controller.holdIconStats.getChildren().add(controller.button);
                    }
                    controller.holdIconStats.setAlignment(Pos.CENTER);

                    if (weapon.getEquipped()) {
                        controller.button.setText("Un-Equip");
                    } else {
                        controller.button.setText("Equip");
                    }

                });

                if (controller.selectedItem == null || !controller.selectedItem.equals(weapon)) {
                    controller.rightStatsPrompt.getChildren().remove(namePrompt);
                    controller.rightStatsPrompt.getChildren().remove(damagePrompt);
                    controller.rightStatsPrompt.getChildren().remove(consumePrompt);
                    controller.rightStatsPrompt.getChildren().remove(valuePrompt);
                    controller.rightStatsPrompt.getChildren().remove(levelPrompt);
                    controller.leftStatsDescription.getChildren().remove(weaponName);
                    controller.leftStatsDescription.getChildren().remove(weaponDamage);
                    controller.leftStatsDescription.getChildren().remove(weaponConsume);
                    controller.leftStatsDescription.getChildren().remove(weaponValue);
                    controller.leftStatsDescription.getChildren().remove(weaponLevel);
                } else if (controller.selectedItem != null
                        && controller.selectedItem.equals(weapon)) {
                    if (!controller.rightStatsPrompt.getChildren().contains(namePrompt)) {
                        controller.rightStatsPrompt.getChildren().add(namePrompt);
                    }
                    if (!controller.rightStatsPrompt.getChildren().contains(damagePrompt)) {
                        controller.rightStatsPrompt.getChildren().add(damagePrompt);
                    }
                    if (!controller.rightStatsPrompt.getChildren().contains(consumePrompt)) {
                        controller.rightStatsPrompt.getChildren().add(consumePrompt);
                    }
                    if (!controller.rightStatsPrompt.getChildren().contains(valuePrompt)) {
                        controller.rightStatsPrompt.getChildren().add(valuePrompt);
                    }
                    if (!controller.rightStatsPrompt.getChildren().contains(levelPrompt)) {
                        controller.rightStatsPrompt.getChildren().add(levelPrompt);
                    }


                    if (!controller.leftStatsDescription.getChildren().contains(weaponName)) {
                        controller.leftStatsDescription.getChildren().add(weaponName);
                    }
                    if (!controller.leftStatsDescription.getChildren().contains(weaponDamage)) {
                        controller.leftStatsDescription.getChildren().add(weaponDamage);
                    }
                    if (!controller.leftStatsDescription.getChildren().contains(weaponConsume)) {
                        controller.leftStatsDescription.getChildren().add(weaponConsume);
                    }
                    if (!controller.leftStatsDescription.getChildren().contains(weaponValue)) {
                        controller.leftStatsDescription.getChildren().add(weaponValue);
                    }

                    if (!controller.leftStatsDescription.getChildren().contains(weaponLevel)) {
                        controller.leftStatsDescription.getChildren().add(weaponLevel);
                    }

                }
            }

        };
        inventoryLoop.start();


    }

    public Text setText(String string) {
        Text text = new Text(string);
        text.setFont(GameScreen.STANDARD_FONT);
        text.setFill(Color.WHITE);
        return text;
    }

    public void mouseClickedButton() {
        if (equipped) {
            unEquipWeapon(controller.player);
            controller.button.setText("Equip");
        } else {
            equipWeapon(controller.player);
            controller.button.setText("Un-Equip");
        }
    }

    /**
     * Stops the animation time whenever
     * player closes the inventory screen
     */
    public void stopInventoryLoop() {
        if (inventoryLoop != null) {
            inventoryLoop.stop();
        }
    }

    public void setXAndYCoord(double rightX, double leftX, double firstY, double secondY) {
        this.rightXCoord = rightX;
        this.leftXCoord = leftX;
        this.firstYCoord = firstY;
        this.secondYCoord = secondY;
    }

    public double getRightXCoord() {
        return rightXCoord;
    }

    public double getLeftXCoord() {
        return leftXCoord;
    }

    public double getFirstYCoord() {
        return firstYCoord;
    }

    public double getSecondYCoord() {
        return secondYCoord;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void equipWeapon(Player player) {
        if (player.getLevel() < level) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not high enough level!");
            alert.setHeaderText(null);
            alert.setContentText("You don't have a high enough level to equip this weapon!");

            alert.showAndWait();
        } else {
            player.setAttackDamage(damage);
            player.setConsumeStamina(this.consumedStamina);
            player.setEquippedWeapon(this);
            equipped = true;
        }
    }

    public void unEquipWeapon(Player player) {
        if (player.getAttackDamage() < damage || !player.getEquippedWeapon().equals(this)) {
            throw new IllegalArgumentException("Player does not have weapon equipped.");
        }
        player.setAttackDamage(player.getAttackDamage() - damage);
        player.setConsumeStamina(player.getConsumeStamina() - consumedStamina);
        player.setEquippedWeapon(null);
        equipped = false;
    }

    public boolean getEquipped() {
        return equipped;
    }

    public int getConsumedStamina() {
        return consumedStamina;
    }

    public void startMerchantLoop() {
        Text namePrompt = setText("Name: ");
        Text damagePrompt = setText("Damage: ");
        Text consumePrompt = setText("Consumes: ");
        Text valuePrompt = setText("Value: ");
        Text levelPromp = setText("Level: ");

        Text weaponName = setText(itemName);
        Text weaponDamage = setText("-" + damage + " HP");
        Text weaponConsume = setText("-" + consumedStamina + " ST");
        Text weaponValue = setText("$" + value);
        Text weaponLevel = setText("" + level);

        holdName = new HBox();
        holdDamage = new HBox();
        holdConsume = new HBox();
        holdValue = new HBox();
        holdLevel = new HBox();

        holdName.getChildren().add(namePrompt);
        holdName.getChildren().add(weaponName);
        holdDamage.getChildren().add(damagePrompt);
        holdDamage.getChildren().add(weaponDamage);
        holdConsume.getChildren().add(consumePrompt);
        holdConsume.getChildren().add(weaponConsume);
        holdValue.getChildren().add(valuePrompt);
        holdValue.getChildren().add(weaponValue);
        holdLevel.getChildren().add(levelPromp);
        holdLevel.getChildren().add(weaponLevel);

        weaponDamage.setFill(Color.LIMEGREEN);
        weaponConsume.setFill(Color.CRIMSON);
        weaponValue.setFill(Color.CYAN);
        weaponLevel.setFill(Color.MAGENTA);

        ItemWeapon weapon = this;

        this.merchantLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                weapon.holdMerchantItem.setOnMouseClicked(e -> {
                    controller.merchantItemInfo.getChildren().removeAll();
                    controller.selectedItem = weapon;
                    controller.merchantBigIcon.setFill(new ImagePattern(weapon.getItemIcon()));
                    controller.merchantItemInfo.getChildren().add(holdName);
                    controller.merchantItemInfo.getChildren().add(holdDamage);
                    controller.merchantItemInfo.getChildren().add(holdConsume);
                    controller.merchantItemInfo.getChildren().add(holdValue);
                    controller.merchantItemInfo.getChildren().add(holdLevel);
                });
                if (controller.selectedItem == null || !controller.selectedItem.equals(weapon)) {
                    controller.merchantItemInfo.getChildren().remove(holdName);
                    controller.merchantItemInfo.getChildren().remove(holdDamage);
                    controller.merchantItemInfo.getChildren().remove(holdConsume);
                    controller.merchantItemInfo.getChildren().remove(holdValue);
                    controller.merchantItemInfo.getChildren().remove(holdLevel);

                } else if (controller.selectedItem.equals(weapon)) {
                    if (!controller.merchantItemInfo.getChildren().contains(holdName)) {
                        controller.merchantItemInfo.getChildren().add(holdName);
                    }
                    if (!controller.merchantItemInfo.getChildren().contains(holdDamage)) {
                        controller.merchantItemInfo.getChildren().add(holdDamage);
                    }
                    if (!controller.merchantItemInfo.getChildren().contains(holdConsume)) {
                        controller.merchantItemInfo.getChildren().add(holdConsume);
                    }
                    if (!controller.merchantItemInfo.getChildren().contains(holdValue)) {
                        controller.merchantItemInfo.getChildren().add(holdValue);
                    }
                    if (!controller.merchantItemInfo.getChildren().contains(holdLevel)) {
                        controller.merchantItemInfo.getChildren().add(holdLevel);
                    }
                }
            }
        };
        merchantLoop.start();
    }


    public void stopMerchantLoop() {
        if (merchantLoop != null) {
            controller.merchantItemInfo.getChildren().remove(holdName);
            controller.merchantItemInfo.getChildren().remove(holdDamage);
            controller.merchantItemInfo.getChildren().remove(holdConsume);
            controller.merchantItemInfo.getChildren().remove(holdValue);
            controller.merchantItemInfo.getChildren().remove(holdLevel);
            merchantLoop.stop();
        }
    }

    public int getLevel(){ return level; }

    public boolean checkMerchantLoopStarted() {
        return merchantLoop != null;
    }
}