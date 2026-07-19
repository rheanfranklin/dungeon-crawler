package game;


public class Merchant {

    private Inventory inventory;
    private int onHandMoney;

    public Merchant() {
        inventory = new Inventory();
        generateInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    private void generateInventory() {
        ListOfItemsGenerate listOfItemsGenerate = new ListOfItemsGenerate();
        listOfItemsGenerate.generateMerchantInventory(inventory);
    }
}
