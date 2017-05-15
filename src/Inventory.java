/**
 * Created by serinahu on 5/4/17.
 */
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import java.util.ArrayList;
public class Inventory {
    static InventoryKeyboard listener;
    // buttons for highlighting
    static Rectangle hoverButton;
    static Rectangle equippedItem;
    // button location (x, y)
    static int buttonXPos;
    static int buttonYPos;
    // currently equipped item's index in the inventory
    static int highlightedItemID;
    static Item selectedItem;
    static boolean isSelectingTarget;
    static int highlightedUnitID;
    static Ally selectedUnit;
    // contains consumables and equips: array list of items
    static ArrayList<Item> items = new ArrayList<Item>();
    // index in array list?

    public static void getFromSave() {
        // init items here
    }

    public static void initListener(GameContainer container) {
        listener = new InventoryKeyboard();
        container.getInput().addKeyListener(listener);
    }

    public static void init() {
        highlightedItemID = 0;
        isSelectingTarget = false;
        highlightedUnitID = 0;
    }
    public static void render(Graphics g) {
        // temporary! Will add GUI etc. later
        g.drawString("Press ESC to return to main menu", 20, 70);
        g.drawString("INVENTORY", 20, 100);
        for (int i = 0; i<items.size(); i++){
            g.drawString(items.get(i).getName() + " x " + items.get(i).getQuantity(), 30, 100+(i+1)*20);
        }

        g.drawString("EXAMINING: [highlighted item + description]git", 20, 380);
        g.drawString("EQUIPPED: [selectedItem.name]", 20, 410);

        if (isSelectingTarget) {
            // render targets somehow?
        }
    }
    public static void update(GameContainer container, int delta) {
    }
    // increase the amount of item by some quantity; should also check if quantity goes over 99
    public static int containsItem(String itemName){
        for (int i = 0; i < items.size(); i++){
            if (items.get(i).getName().equals(itemName)){
                return i;
            }
        }
        return -1;
    }
    public static void addItem(String itemName, int quantity) {
        if (!itemName.equals("empty")) {
            int index = containsItem(itemName);
            if (index >= 0) {
                items.get(index).addQuantity(quantity);
            } else {
                int itemID = Resources.getItemID(itemName);
                if (itemID >= 0) {
                    Resources.item_db[itemID].addQuantity(quantity);
                    items.add(Resources.item_db[itemID]);
                }
            }
        }
    }
    public static void removeItem(String itemName, int quantity) {
        int index = containsItem(itemName);
        if (index >= 0){
            items.get(index).removeQuantity(quantity);
        }
        if (items.get(index).getQuantity() == 0) {
            items.remove(index);
        }
    }
    public static int getQuantity(String itemName) {
        return 0;
    }
}

class InventoryKeyboard implements KeyListener {
    public void keyPressed(int key, char c) {

    }

    public void keyReleased(int key, char c) {
        if (Inventory.isSelectingTarget) {
            if (key == Input.KEY_DOWN) {
                if (Inventory.highlightedUnitID < Resources.party.length && Resources.party[Inventory.highlightedUnitID + 1] != null) {
                    Inventory.highlightedUnitID++;
                }
            } else if (key == Input.KEY_UP) {
                if (Inventory.highlightedUnitID > 0) {
                    Inventory.highlightedUnitID--;
                }
            } else if (key == Input.KEY_DELETE || key == Input.KEY_BACK) {
                Inventory.isSelectingTarget = false;
            } else if (key == Input.KEY_ENTER) {
                Inventory.selectedUnit = Resources.party[Inventory.highlightedUnitID].battleEntity;
                if (Inventory.selectedItem instanceof Consumable) {
                    // handle target types here :(
                    if (((Consumable) Inventory.selectedItem).getTargetType() == TargetType.ALL_ALLIES) {
                        for (Entity ally : Resources.party) {
                            if (ally != null) {
                                ((Consumable) Inventory.selectedItem).use(ally.battleEntity);
                            }
                        }
                    } else {
                        ((Consumable) Inventory.selectedItem).use(Inventory.selectedUnit);
                    }
                } else {
                    Inventory.selectedUnit.equip((Equipment) Inventory.selectedItem);
                }
                Inventory.isSelectingTarget = false;
            }
        } else {
            if (key == Input.KEY_DOWN) {
                if (Inventory.highlightedItemID < Inventory.items.size()) {
                    Inventory.highlightedItemID++;
                }
            } else if (key == Input.KEY_UP) {
                if (Inventory.highlightedItemID > 0) {
                    Inventory.highlightedItemID--;
                }
            } else if (key == Input.KEY_ENTER) {
                Inventory.selectedItem = Inventory.items.get(Inventory.highlightedItemID);
                Inventory.isSelectingTarget = true;
            }
        }
    }

    public void setInput(Input input) {

    }

    public boolean isAcceptingInput() {
        return PauseMenu.inMenu == PauseMenu.NONE;
    }

    public void inputEnded() {

    }

    public void inputStarted() {

    }
}