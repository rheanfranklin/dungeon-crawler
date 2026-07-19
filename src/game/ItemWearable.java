package game;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;

public class ItemWearable extends Item {

    public enum WearableType {
        NECKLACE,
        RING,
        HELMET,
        CHEST,
        PANTS
    }

    private WearableType wearableType;

    private int increaseDefense;

    private AnimationTimer inventoryLoop;

    private AnimationTimer merchantLoop;

    private HBox holdName;
    private HBox holdDefense;
    private HBox holdValue;

    private boolean equipped;

    public ItemWearable(Image itemIcon, int increaseDefense, WearableType wearableType,
                double value, String itemName, String itemDescription) {
        super(Inventory.ItemType.WEARABLE, itemIcon, value,
                itemName, itemDescription, 1);
        this.increaseDefense = increaseDefense;
        this.wearableType = wearableType;
        equipped = false;
    }

    public void startInventoryLoop() {
        Text namePrompt = setText("Name: ");
        Text damagePrompt = setText("Increased Defense: ");
        Text valuePrompt = setText("Value: ");

        Text weaponName = setText(itemName);
        Text weaponDamage = setText("-" + increaseDefense + " HP");
        Text weaponValue = setText("$" + value);

        ItemWearable weapon = this;

        this.inventoryLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                weapon.iconBox.setOnMouseClicked(e -> {
                    controller.selectedItem = weapon;

                    weaponDamage.setFill(Color.LIMEGREEN);
                    weaponValue.setFill(Color.CYAN);

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
                    controller.rightStatsPrompt.getChildren().remove(valuePrompt);
                    controller.leftStatsDescription.getChildren().remove(weaponName);
                    controller.leftStatsDescription.getChildren().remove(weaponDamage);
                    controller.leftStatsDescription.getChildren().remove(weaponValue);
                } else if (controller.selectedItem != null
                        && controller.selectedItem.equals(weapon)) {
                    if (!controller.rightStatsPrompt.getChildren().contains(namePrompt)) {
                        controller.rightStatsPrompt.getChildren().add(namePrompt);
                    }
                    if (!controller.rightStatsPrompt.getChildren().contains(damagePrompt)) {
                        controller.rightStatsPrompt.getChildren().add(damagePrompt);
                    }
                    if (!controller.rightStatsPrompt.getChildren().contains(valuePrompt)) {
                        controller.rightStatsPrompt.getChildren().add(valuePrompt);
                    }


                    if (!controller.leftStatsDescription.getChildren().contains(weaponName)) {
                        controller.leftStatsDescription.getChildren().add(weaponName);
                    }
                    if (!controller.leftStatsDescription.getChildren().contains(weaponDamage)) {
                        controller.leftStatsDescription.getChildren().add(weaponDamage);
                    }
                    if (!controller.leftStatsDescription.getChildren().contains(weaponValue)) {
                        controller.leftStatsDescription.getChildren().add(weaponValue);
                    }

                }
            }

        };
        inventoryLoop.start();
    }

    public void stopInventoryLoop() {
        if (inventoryLoop != null) {
            inventoryLoop.stop();
        }
    }

    public void mouseClickedButton() {
        if (equipped) {
            unEquip(controller.player);
            controller.button.setText("Equip");
        } else {
            equip(controller.player);
            controller.button.setText("Un-Equip");
        }
    }

    public void startMerchantLoop() {
        Text namePrompt = setText("Name: ");
        Text defensePrompt = setText("Increase defense: ");
        Text valuePrompt = setText("Value: ");

        Text weaponName = setText(itemName);
        Text wearableDefense = setText("-" + increaseDefense + " HP");
        Text weaponValue = setText("$" + value);

        holdName = new HBox();
        holdDefense = new HBox();
        holdValue = new HBox();

        holdName.getChildren().add(namePrompt);
        holdName.getChildren().add(weaponName);
        holdDefense.getChildren().add(defensePrompt);
        holdDefense.getChildren().add(wearableDefense);
        holdValue.getChildren().add(valuePrompt);
        holdValue.getChildren().add(weaponValue);

        wearableDefense.setFill(Color.LIMEGREEN);
        weaponValue.setFill(Color.CYAN);

        ItemWearable wearable = this;

        this.merchantLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                wearable.holdMerchantItem.setOnMouseClicked(e -> {
                    controller.merchantItemInfo.getChildren().removeAll();
                    controller.selectedItem = wearable;
                    controller.merchantBigIcon.setFill(new ImagePattern(wearable.getItemIcon()));
                    controller.merchantItemInfo.getChildren().add(holdName);
                    controller.merchantItemInfo.getChildren().add(holdDefense);
                    controller.merchantItemInfo.getChildren().add(holdValue);
                });
                if (controller.selectedItem == null || !controller.selectedItem.equals(wearable)) {
                    controller.merchantItemInfo.getChildren().remove(holdName);
                    controller.merchantItemInfo.getChildren().remove(holdDefense);
                    controller.merchantItemInfo.getChildren().remove(holdValue);

                } else if (controller.selectedItem.equals(wearable)) {
                    if (!controller.merchantItemInfo.getChildren().contains(holdName)) {
                        controller.merchantItemInfo.getChildren().add(holdName);
                    }
                    if (!controller.merchantItemInfo.getChildren().contains(holdDefense)) {
                        controller.merchantItemInfo.getChildren().add(holdDefense);
                    }
                    if (!controller.merchantItemInfo.getChildren().contains(holdValue)) {
                        controller.merchantItemInfo.getChildren().add(holdValue);
                    }
                }
            }
        };
        merchantLoop.start();
    }

    public void stopMerchantLoop() {
        if (merchantLoop != null) {
            merchantLoop.stop();
        }
    }

    public boolean checkMerchantLoopStarted() {
        return merchantLoop != null;
    }

    public void equip(Player player) {
        if (wearableType.equals(WearableType.NECKLACE)) {
            player.setEquippedNecklace(this);
        }
        if (wearableType.equals(WearableType.RING)) {
            player.setEquippedRing(this);
        }
        if (wearableType.equals(WearableType.HELMET)) {
            player.setEquippedHelmet(this);
        }
        if (wearableType.equals(WearableType.CHEST)) {
            player.setEquippedChest(this);
        }
        if (wearableType.equals(WearableType.PANTS)) {
            player.setEquippedPants(this);
        }
        player.setDefense(player.getDefense() + increaseDefense);
        equipped = true;
    }

    public void unEquip(Player player) {
        boolean found = false;
        if (wearableType.equals(WearableType.NECKLACE)
                && player.getEquippedNecklace().equals(this)) {
            player.setEquippedNecklace(null);
            found = true;
        }
        if (wearableType.equals(WearableType.RING)
                && player.getEquippedRing().equals(this)) {
            player.setEquippedRing(null);
            found = true;
        }
        if (wearableType.equals(WearableType.HELMET)
                && player.getEquippedHelmet().equals(this)) {
            player.setEquippedHelmet(null);
            found = true;
        }
        if (wearableType.equals(WearableType.CHEST)
                && player.getEquippedChest().equals(this)) {
            player.setEquippedChest(null);
            found = true;
        }
        if (wearableType.equals(WearableType.PANTS)
                && player.getEquippedPants().equals(this)) {
            player.setEquippedPants(null);
            found = true;
        }
        if (found) {
            player.setDefense(player.getDefense() - increaseDefense);
        }
    }

    public Text setText(String string) {
        Text text = new Text(string);
        text.setFont(GameScreen.STANDARD_FONT);
        text.setFill(Color.WHITE);
        return text;
    }

    public boolean getEquipped() { return equipped; }

}
