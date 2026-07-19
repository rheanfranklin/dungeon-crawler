package game;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ItemConsumable extends Item {
    protected int increaseHealth;

    private AnimationTimer inventoryLoop;
    private AnimationTimer merchantLoop;
    private ItemConsumable consumable;
    private HBox holdName;
    private HBox holdDescription;
    private HBox holdValue;

    public ItemConsumable(Image itemIcon, double value,
                          String name, String description, int increaseHealth) {
        super(Inventory.ItemType.CONSUMABLE, itemIcon,
                value, name, description, DEFAULT_MAX_QUANTITY);
        this.increaseHealth = increaseHealth;
        this.consumable = this;
    }



    public void startInventoryLoop() {

        Text namePrompt = setText("Name: ");
        Text descriptionPrompt = setText("Description: ");
        Text valuePrompt = setText("Value: ");

        Text consumableName = setText(itemName);
        Text consumableDescription = setText(itemDescription);
        Text consumableValue = setText("$" + value);

        consumableDescription.setFill(Color.LIMEGREEN);
        consumableValue.setFill(Color.CYAN);

        Text potionQuantity = setText("" + quantity);
        this.inventoryLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {

                consumable.iconBox.setOnMouseClicked(e -> {
                    controller.selectedItem = consumable;


                    controller.bigIconDisplay.setFill(new ImagePattern(consumable.getItemIcon()));


                    controller.holdIconStats.setAlignment(Pos.CENTER);

                    controller.button.setText("Consume");
                });


                if (controller.selectedItem == null || !controller.selectedItem.equals(consumable)) {
                    controller.rightStatsPrompt.getChildren().remove(namePrompt);
                    controller.rightStatsPrompt.getChildren().remove(descriptionPrompt);
                    controller.rightStatsPrompt.getChildren().remove(valuePrompt);

                    controller.leftStatsDescription.getChildren().remove(consumableName);
                    controller.leftStatsDescription.getChildren().remove(consumableDescription);
                    controller.leftStatsDescription.getChildren().remove(consumableValue);
                    potionQuantity.setText("");
                } else if (controller.selectedItem.equals(consumable)) {
                    if (!controller.rightStatsPrompt.getChildren().contains(namePrompt)) {
                        controller.rightStatsPrompt.getChildren().add(namePrompt);
                    }
                    if (!controller.inventoryStack.getChildren().contains(potionQuantity)) {
                        controller.inventoryStack.getChildren().add(potionQuantity);
                        potionQuantity.setX(iconBox.getX());
                        potionQuantity.setY(iconBox.getY());
                    }
                    if (!controller.rightStatsPrompt.getChildren().contains(descriptionPrompt)) {
                        controller.rightStatsPrompt.getChildren().add(descriptionPrompt);
                    }
                    if (!controller.rightStatsPrompt.getChildren().contains(valuePrompt)) {
                        controller.rightStatsPrompt.getChildren().add(valuePrompt);
                    }

                    if (!controller.leftStatsDescription.getChildren().contains(consumableName)) {
                        controller.leftStatsDescription.getChildren().add(consumableName);
                    }
                    if (!controller.leftStatsDescription.getChildren()
                            .contains(consumableDescription)) {
                        controller.leftStatsDescription.getChildren().add(consumableDescription);
                    }
                    if (!controller.leftStatsDescription.getChildren().contains(consumableValue)) {
                        controller.leftStatsDescription.getChildren().add(consumableValue);
                    }
                    potionQuantity.setText("" + quantity);

                }

                if (itemRemoved) {
                    controller.rightStatsPrompt.getChildren().remove(namePrompt);
                    controller.rightStatsPrompt.getChildren().remove(descriptionPrompt);
                    controller.rightStatsPrompt.getChildren().remove(valuePrompt);


                    controller.leftStatsDescription.getChildren().remove(consumableName);
                    controller.leftStatsDescription.getChildren().remove(consumableDescription);
                    controller.leftStatsDescription.getChildren().remove(consumableValue);

                    controller.inventoryStack.getChildren().remove(consumable.iconBox);
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
        this.consume(controller.player);
        if (this.quantity <= 0) {
            this.itemRemoved = true;
        }
        controller.inventoryStack.getChildren().remove(iconBox);
    }

    public void startMerchantLoop() {

        Text namePrompt = setText("Name: ");
        Text descriptionPrompt = setText("Description: ");
        Text valuePrompt = setText("Value: ");

        Text consumableName = setText(itemName);
        Text consumableDescription = setText(itemDescription);
        Text consumableValue = setText("$" + value);



        holdName = new HBox();
        holdDescription = new HBox();
        holdValue = new HBox();

        holdName.getChildren().add(namePrompt);
        holdName.getChildren().add(consumableName);
        holdDescription.getChildren().add(descriptionPrompt);
        holdDescription.getChildren().add(consumableDescription);
        holdValue.getChildren().add(valuePrompt);
        holdValue.getChildren().add(consumableValue);

        consumableDescription.setFill(Color.LIMEGREEN);
        consumableValue.setFill(Color.CYAN);

        this.merchantLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                consumable.holdMerchantItem.setOnMouseClicked(e -> {
                    controller.merchantItemInfo.getChildren().remove(holdName);
                    controller.merchantItemInfo.getChildren().remove(holdDescription);
                    controller.merchantItemInfo.getChildren().remove(holdValue);
                    controller.merchantItemInfo.getChildren().removeAll();
                    controller.selectedItem = consumable;
                    controller.merchantBigIcon.setFill(new ImagePattern(consumable.getItemIcon()));
                    controller.merchantItemInfo.getChildren().add(holdName);
                    controller.merchantItemInfo.getChildren().add(holdDescription);
                    controller.merchantItemInfo.getChildren().add(holdValue);
                });
                if (controller.selectedItem == null || !controller.selectedItem.equals(consumable)) {
                    controller.merchantItemInfo.getChildren().remove(holdName);
                    controller.merchantItemInfo.getChildren().remove(holdDescription);
                    controller.merchantItemInfo.getChildren().remove(holdValue);

                } else if (controller.selectedItem.equals(consumable)) {
                    if (!controller.merchantItemInfo.getChildren().contains(holdName)) {
                        controller.merchantItemInfo.getChildren().add(holdName);
                    }
                    if (!controller.merchantItemInfo.getChildren().contains(holdDescription)) {
                        controller.merchantItemInfo.getChildren().add(holdDescription);
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
            controller.merchantItemInfo.getChildren().remove(holdName);
            controller.merchantItemInfo.getChildren().remove(holdDescription);
            controller.merchantItemInfo.getChildren().remove(holdValue);
            merchantLoop.stop();
        }
    }

    public boolean checkMerchantLoopStarted() {
        return merchantLoop != null;
    }


    public void consume(Player player) {
        player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + increaseHealth));
        player.getInventory().removeItem(this);
    }

    public Rectangle getIconBox() {
        return iconBox;
    }

    public Text setText(String string) {
        Text text = new Text(string);
        text.setFont(GameScreen.STANDARD_FONT);
        text.setFill(Color.WHITE);
        return text;
    }



}
