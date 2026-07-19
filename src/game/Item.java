package game;


import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public abstract class Item {
    protected static final Inventory.ItemType DEFAULT_ITEM_TYPE = Inventory.ItemType.MISC;

    protected static final double DEFAULT_VALUE = 10.00;

    protected static final int DEFAULT_MAX_QUANTITY = 16;

    protected final Inventory.ItemType itemType;

    protected final Image itemIcon;

    protected double value;

    protected int quantity;

    protected final int maxQuantity;

    protected String itemName;

    protected String itemDescription;

    protected Controller controller;

    protected Player player;

    protected Rectangle iconBox;

    protected StackPane iconStack;

    protected boolean itemRemoved;

    protected Rectangle merchantIconBox;

    protected HBox holdMerchantItem;

    public Item(Inventory.ItemType itemType, Image itemIcon,
                double value, String itemName, String itemDescription, int maxQuantity) {
        this.itemType = itemType;
        this.itemIcon = itemIcon;
        this.value = value;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.maxQuantity = maxQuantity;
        this.quantity = 1;
        itemRemoved = false;
        iconBox = new Rectangle();
        iconBox.setHeight(Controller.ICON_HEIGHT);
        iconBox.setWidth(Controller.ICON_HEIGHT);
        iconBox.setArcWidth(Controller.ICON_ARC);
        iconBox.setArcHeight(Controller.ICON_ARC);
        iconBox.setFill(new ImagePattern(itemIcon));

        merchantIconBox = new Rectangle();
        merchantIconBox.setHeight(Controller.ICON_HEIGHT);
        merchantIconBox.setWidth(Controller.ICON_HEIGHT);
        merchantIconBox.setArcWidth(Controller.ICON_ARC);
        merchantIconBox.setArcWidth(Controller.ICON_ARC);
        merchantIconBox.setFill(new ImagePattern(itemIcon));

        holdMerchantItem = new HBox();
    }

    public Item(Image itemIcon, String name, String description) {
        this(DEFAULT_ITEM_TYPE, itemIcon, DEFAULT_VALUE, name, description, DEFAULT_MAX_QUANTITY);
    }



    public Inventory.ItemType getItemType() {
        return itemType;
    }

    public Image getItemIcon() {
        return itemIcon;
    }

    public double getValue() {
        return value;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public Rectangle getIconBox() {
        return iconBox;
    }

    public Rectangle getMerchantIconBox() { return merchantIconBox; }

    public abstract void startInventoryLoop();

    public abstract void stopInventoryLoop();

    public abstract void mouseClickedButton();

    public abstract void startMerchantLoop();

    public abstract void stopMerchantLoop();

    public abstract boolean checkMerchantLoopStarted();

    public void mouseClickedMerchantButton() {
        if (controller.onMerchantBuyScreen) {
            this.buy(controller.player, controller.merchant);
        } else {
            this.sell(controller.player, controller.merchant);
        }
    }

    public void buy(Player player, Merchant merchant) {
        if (player.getMoney() >= (int) value) {
            player.getInventory().add(this);
            merchant.getInventory().removeFromMerchant(this, controller);
            player.setMoney(player.getMoney() - (int) value);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Money!");
            alert.setHeaderText(null);
            alert.setContentText("You don't have enough money for that!");

            alert.showAndWait();
        }
    }

    public  void sell(Player player, Merchant merchant) {
        merchant.getInventory().add(this);
        player.getInventory().removeFromMerchant(this, controller);
        player.setMoney(player.getMoney() + (int) value);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setIconStack(StackPane iconStack) { this.iconStack = iconStack; }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public StackPane getIconStack() { return iconStack; }

    public void setRemoved() {
        itemRemoved = true;
    }

    public void setHoldMerchantItem(HBox holdMerchantItem) {
        this.holdMerchantItem = holdMerchantItem;
    }
}