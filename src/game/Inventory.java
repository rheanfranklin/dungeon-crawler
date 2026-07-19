package game;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.*;

public class Inventory {
    private static final int WEAPONS_CAPACITY = 10000;
    private static final int SPELLS_CAPACITY = 10000;
    private static final int CONSUMABLE_CAPACITY = 10000;
    private static final int WEARABLE_CAPACITY = 10000;
    private static final int MISC_CAPACITY = 10000;
    private HBox row1;
    private HBox row2;
    private HBox row3;
    private HBox row4;
    private HBox row5;


    public enum ItemType {
        WEAPON,
        SPELL,
        CONSUMABLE,
        WEARABLE,
        MISC
    }

    private final HashMap<ItemType, ArrayList<Item>> itemList;

    public Inventory() {
        itemList = new HashMap<>();
        itemList.put(ItemType.WEAPON, new ArrayList<>());
        itemList.put(ItemType.SPELL, new ArrayList<>());
        itemList.put(ItemType.CONSUMABLE, new ArrayList<>());
        itemList.put(ItemType.WEARABLE, new ArrayList<>());
        itemList.put(ItemType.MISC, new ArrayList<>());
    }

    public void setUpInventoryGrid(StackPane stackPane, Controller controller) {
        VBox holdRows = new VBox();
        row1 = new HBox();
        row2 = new HBox();
        row3 = new HBox();
        row4 = new HBox();
        row5 = new HBox();

        LinkedList<Item> allItems = new LinkedList<>();
        allItems.addAll(itemList.get(ItemType.WEAPON));
        allItems.addAll(itemList.get(ItemType.SPELL));
        allItems.addAll(itemList.get(ItemType.CONSUMABLE));
        allItems.addAll(itemList.get(ItemType.WEARABLE));
        allItems.addAll(itemList.get(ItemType.MISC));

        int i = 0;

        stackPane.getChildren().removeAll();

        while (i < 10 && allItems.size() > 0) {
            setUpRow(row1, allItems, controller);
            ++i;
        }

        //allItems = new LinkedList<>();
        int j = 0;
        while (j < 10 && allItems.size() > 0) {
            setUpRow(row2, allItems, controller);
            ++j;
        }
        int k = 0;
        while (k < 10 && allItems.size() > 0) {
            setUpRow(row3, allItems, controller);
            ++k;
        }
        int l = 0;
        while (l < 10 && allItems.size() > 0) {
            setUpRow(row4, allItems, controller);
            ++l;
        }
        int m = 0;
        while (m < 10 && allItems.size() > 0) {
            setUpRow(row5, allItems, controller);
            ++m;
        }

        if (i < 10) {
            while (i < 10) {
                row1.getChildren().add(createTile());
                row1.setSpacing(2);
                ++i;
            }
        }

        if (j < 10) {
            while (j < 10) {
                row2.getChildren().add(createTile());
                row2.setSpacing(2);
                ++j;
            }
        }

        if (k < 10) {
            while (k < 10) {
                row3.getChildren().add(createTile());
                ++k;
                row3.setSpacing(2);
            }
        }

        if (l < 10) {
            while (l < 10) {
                row4.getChildren().add(createTile());
                row4.setSpacing(2);
                ++l;
            }
        }

        if (m < 10) {
            while (m < 10) {
                row5.getChildren().add(createTile());
                row5.setSpacing(2);
                ++m;
            }
        }

        row1.setSpacing(2);
        row2.setSpacing(2);
        row3.setSpacing(2);
        row4.setSpacing(2);
        row5.setSpacing(2);

        row1.setAlignment(Pos.CENTER);
        row2.setAlignment(Pos.CENTER);
        row3.setAlignment(Pos.CENTER);
        row4.setAlignment(Pos.CENTER);
        row5.setAlignment(Pos.CENTER);

        holdRows.getChildren().add(row1);
        holdRows.getChildren().add(row2);
        holdRows.getChildren().add(row3);
        holdRows.getChildren().add(row4);
        holdRows.getChildren().add(row5);

        holdRows.setSpacing(2);
        holdRows.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(holdRows);
        stackPane.setAlignment(Pos.CENTER);

    }

    public void setUpMerchantIcons() {

    }

    public Rectangle createTile() {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.DIMGRAY);
        rectangle.setStroke(Color.WHITE);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        rectangle.setWidth(50);
        rectangle.setHeight(50);

        return rectangle;
    }

    private void setUpRow(HBox row, LinkedList<Item> allItems, Controller controller) {
        StackPane iconStack = new StackPane();
        allItems.getFirst().setIconStack(iconStack);
        Text quantityText = new Text("");

        Rectangle iconBox = allItems.getFirst().getIconBox();
        quantityText.setFont(GameScreen.STANDARD_FONT);
        quantityText.setFill(Color.WHITE);
        if (allItems.getFirst().getMaxQuantity() > 1) {
            quantityText.setText("" + allItems.getFirst().getQuantity());
        }
        iconStack.getChildren().add(quantityText);
        iconStack.setAlignment(Pos.BOTTOM_RIGHT);
        iconStack.getChildren().add(iconBox);

        quantityText.setX(quantityText.getX() + 40);
        quantityText.setY(quantityText.getY() + 35);
        row.getChildren().add(iconStack);
        row.setSpacing(2);
        allItems.getFirst().setController(controller);
        allItems.getFirst().stopInventoryLoop();
        allItems.getFirst().startInventoryLoop();
        allItems.removeFirst();
    }

    public void add(Item item) {
        if (item.getItemType().equals(ItemType.WEAPON)) {
            addWeapon(item);
        } else if (item.getItemType().equals(ItemType.MISC)) {
            addMisc(item);
        } else if (item.getItemType().equals(ItemType.CONSUMABLE)) {
            addConsumable(item);
        } else if (item.getItemType().equals(ItemType.WEARABLE)) {
            addWearable(item);
        }
    }

    public void addWeapon(Item item) {
        if (itemList.get(ItemType.WEAPON).size() < WEAPONS_CAPACITY) {
            itemList.get(ItemType.WEAPON).add(item);
        }
    }

    private void addMisc(Item item) {
        if (itemList.get(ItemType.MISC).size() < MISC_CAPACITY) {
            itemList.get(ItemType.MISC).add(item);
        }
    }

    public void addConsumable(Item item) {
        if (itemList.get(ItemType.CONSUMABLE).size() < CONSUMABLE_CAPACITY) {
            boolean itemFound = false;
            int indexFound = 0;
            int j = 0;
            for (Item i : itemList.get(ItemType.CONSUMABLE)) {
                if (item.getItemName().equals(i.getItemName())) {
                    itemFound = true;
                    indexFound = j;
                }
                ++j;
            }
            if (itemFound) {
                int currQuantity = itemList.get(ItemType.CONSUMABLE).get(indexFound).getQuantity();
                int maxQuantity =
                        itemList.get(ItemType.CONSUMABLE).get(indexFound).getMaxQuantity();
                if (currQuantity + 1 <= maxQuantity) {
                    itemList.get(ItemType.CONSUMABLE)
                            .get(indexFound).changeQuantity(currQuantity + 1);
                }
            } else {
                itemList.get(ItemType.CONSUMABLE).add(item);
            }
        }
    }

    public void addWearable(Item item) {
        if (itemList.get(ItemType.WEARABLE).size() < WEARABLE_CAPACITY) {
            itemList.get(ItemType.WEARABLE).add(item);
        }
    }

    public void addSpell(Item item) {
        if (itemList.get(ItemType.SPELL).size() < SPELLS_CAPACITY) {
            itemList.get(ItemType.SPELL).add(item);
        }
    }

    public void removeItem(Item item) {
        boolean found = false;
        ItemType itemType = item.getItemType();
        int foundIndex = 0;
        for (int i = 0; i < itemList.get(itemType).size(); ++i) {
            if (itemList.get(itemType).get(i).getItemName().equals(item.getItemName())) {
                foundIndex = i;
            }
        }
        if (item.getQuantity() > 1) {
            item.changeQuantity(item.getQuantity() - 1);
        } else {
            itemList.get(itemType).remove(foundIndex);
            if (row1.getChildren().contains(item.getIconStack())) {
                row1.getChildren().remove(item.getIconStack());
                row1.getChildren().add(createTile());
                row1.setSpacing(2);
            }
            if (row2.getChildren().contains(item.getIconStack())) {
                row2.getChildren().remove(item.getIconStack());
                row2.getChildren().add(createTile());
                row2.setSpacing(2);
            }
            if (row3.getChildren().contains(item.getIconStack())) {
                row3.getChildren().remove(item.getIconStack());
                row3.getChildren().add(createTile());
                row3.setSpacing(2);
            }
            if (row4.getChildren().contains(item.getIconStack())) {
                row4.getChildren().remove(item.getIconStack());
                row4.getChildren().add(createTile());
                row4.setSpacing(2);
            }
            if (row5.getChildren().contains(item.getIconStack())) {
                row5.getChildren().remove(item.getIconStack());
                row5.getChildren().add(createTile());
                row5.setSpacing(2);
            }
            item.setRemoved();
        }
    }

    public void removeFromMerchant(Item item, Controller controller) {
        ItemType itemType = item.getItemType();


        if (item.getQuantity() > 1) {
            item.changeQuantity(item.getQuantity() - 1);
        } else {
            int foundIndex = 0;
            for (int i = 0; i < itemList.get(itemType).size(); ++i) {
                if (itemList.get(itemType).get(i).getItemName().equals(item.getItemName())) {
                    foundIndex = i;
                }
            }
            itemList.get(itemType).remove(foundIndex);
            controller.holdBackgroundIcons.getChildren().remove(item.holdMerchantItem);
        }
        controller.selectedItem = null;

    }

    public int numOfItems() {
        ArrayList<Item> listOfItems = new ArrayList<>();
        listOfItems.addAll(itemList.get(ItemType.WEAPON));
        listOfItems.addAll(itemList.get(ItemType.CONSUMABLE));
        listOfItems.addAll(itemList.get(ItemType.SPELL));
        listOfItems.addAll(itemList.get(ItemType.MISC));
        listOfItems.addAll(itemList.get(ItemType.WEARABLE));

        return listOfItems.size();
    }

    public HashMap<ItemType, ArrayList<Item>> getItemHashMap() {
        return itemList;
    }

    public ArrayList<Item> getListOfItems() {
        ArrayList<Item> listOfItems = new ArrayList<>();
        listOfItems.addAll(itemList.get(ItemType.WEAPON));
        listOfItems.addAll(itemList.get(ItemType.CONSUMABLE));
        listOfItems.addAll(itemList.get(ItemType.SPELL));
        listOfItems.addAll(itemList.get(ItemType.MISC));
        listOfItems.addAll(itemList.get(ItemType.WEARABLE));
        return listOfItems;
    }

    public void add(ArrayList<Item> addList) {
        for (Item i : addList) {
            add(i);
        }
    }


}
