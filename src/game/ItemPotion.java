package game;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;

public abstract class ItemPotion extends Item {

    protected Level level;

    private AnimationTimer inventoryLoop;

    private AnimationTimer merchantLoop;

    private HBox holdName;
    private HBox holdDescription;
    private HBox holdValue;

    public ItemPotion(Image potionIcon, Level level, double value,
                      String name, String description) {
        super(Inventory.ItemType.CONSUMABLE, potionIcon, value, name,
                description, DEFAULT_MAX_QUANTITY);
    }

    public abstract void consume(Player player);

    @Override
    public void startInventoryLoop() {

        Text namePrompt = setText("Name: ");
        Text descriptionPrompt = setText("Description: ");
        Text valuePrompt = setText("Value: ");

        Text potionName = setText(itemName);
        Text potionDescription = setText(itemDescription);
        Text potionValue = setText("$" + value);

        Text potionQuantity = setText("" + quantity);

        ItemPotion potion = this;

        this.inventoryLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {

                potion.iconBox.setOnMouseClicked(e -> {
                    controller.selectedItem = potion;
                    potionDescription.setFill(Color.LIMEGREEN);
                    potionValue.setFill(Color.CYAN);

                    controller.bigIconDisplay.setFill(new ImagePattern(potion.getItemIcon()));

                    controller.holdIconStats.setAlignment(Pos.CENTER);

                    controller.button.setText("Consume");
                });


                if (controller.selectedItem == null || !controller.selectedItem.equals(potion)) {
                    controller.rightStatsPrompt.getChildren().remove(namePrompt);
                    controller.rightStatsPrompt.getChildren().remove(descriptionPrompt);
                    controller.rightStatsPrompt.getChildren().remove(valuePrompt);

                    controller.leftStatsDescription.getChildren().remove(potionName);
                    controller.leftStatsDescription.getChildren().remove(potionDescription);
                    controller.leftStatsDescription.getChildren().remove(potionValue);

                    potionQuantity.setText("");
                } else if (controller.selectedItem.equals(potion)) {
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

                    if (!controller.leftStatsDescription.getChildren().contains(potionName)) {
                        controller.leftStatsDescription.getChildren().add(potionName);
                    }
                    if (!controller.leftStatsDescription.getChildren()
                            .contains(potionDescription)) {
                        controller.leftStatsDescription.getChildren().add(potionDescription);
                    }
                    if (!controller.leftStatsDescription.getChildren().contains(potionValue)) {
                        controller.leftStatsDescription.getChildren().add(potionValue);
                    }
                    potionQuantity.setText("" + quantity);

                }

                if (itemRemoved) {
                    controller.rightStatsPrompt.getChildren().remove(namePrompt);
                    controller.rightStatsPrompt.getChildren().remove(descriptionPrompt);
                    controller.rightStatsPrompt.getChildren().remove(valuePrompt);

                    controller.leftStatsDescription.getChildren().remove(potionName);
                    controller.leftStatsDescription.getChildren().remove(potionDescription);
                    controller.leftStatsDescription.getChildren().remove(potionValue);

                    controller.inventoryStack.getChildren().remove(potion.iconBox);
                }
            }

        };
        inventoryLoop.start();
    }

    @Override
    public void stopInventoryLoop() {
        if (inventoryLoop != null) {
            inventoryLoop.stop();
        }
    }

    public Text setText(String string) {
        Text text = new Text(string);
        text.setFont(GameScreen.STANDARD_FONT);
        text.setFill(Color.WHITE);
        return text;
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

        ItemPotion potion = this;

        this.merchantLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                potion.holdMerchantItem.setOnMouseClicked(e -> {
                    controller.merchantItemInfo.getChildren().remove(holdName);
                    controller.merchantItemInfo.getChildren().remove(holdDescription);
                    controller.merchantItemInfo.getChildren().remove(holdValue);
                    controller.merchantItemInfo.getChildren().removeAll();
                    controller.selectedItem = potion;
                    controller.merchantBigIcon.setFill(new ImagePattern(potion.getItemIcon()));
                    controller.merchantItemInfo.getChildren().add(holdName);
                    controller.merchantItemInfo.getChildren().add(holdDescription);
                    controller.merchantItemInfo.getChildren().add(holdValue);
                });
                if (controller.selectedItem == null || !controller.selectedItem.equals(potion)) {
                    controller.merchantItemInfo.getChildren().remove(holdName);
                    controller.merchantItemInfo.getChildren().remove(holdDescription);
                    controller.merchantItemInfo.getChildren().remove(holdValue);

                } else if (controller.selectedItem.equals(potion)) {
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


}
