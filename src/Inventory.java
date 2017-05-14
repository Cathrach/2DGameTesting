/**
 * Created by serinahu on 5/4/17.
 */
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import java.util.ArrayList;
public class Inventory {
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
    public static void init() {
        // init items: read from a file and put them in
        highlightedItemID = 0;
        isSelectingTarget = false;
        highlightedUnitID = 0;
    }
    public static void render(Graphics g) {
        // temporary! Will add GUI & fix up later
        g.drawString("INVENTORY", 20, 70);
        for (int i = 0; i<items.size(); i++){
            g.drawString(items.get(i).getName() + " x " + items.get(i).getQuantity(), 20, 70+(i+1)*15);
        }
        if (isSelectingTarget) {
            // render targets somehow?
        }
    }
    public static void update(GameContainer container, int delta) {
        Input input = container.getInput();
        if (isSelectingTarget) {
            if (input.isKeyPressed(Input.KEY_DOWN)) {
                if (highlightedUnitID < Resources.party.length && Resources.party[highlightedUnitID + 1] != null) {
                    highlightedUnitID++;
                }
            } else if (input.isKeyPressed(Input.KEY_UP)) {
                if (highlightedUnitID > 0) {
                    highlightedUnitID--;
                }
            } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
                isSelectingTarget = false;
            } else if (input.isKeyPressed(Input.KEY_ENTER)) {
                selectedUnit = Resources.party[highlightedUnitID].battleEntity;
                if (selectedItem instanceof Consumable) {
                    // handle target types here :(
                    ((Consumable) selectedItem).use(selectedUnit);
                } else {
                    selectedUnit.equip((Equipment) selectedItem);
                }
                isSelectingTarget = false;
            }
        } else {
            if (input.isKeyPressed(Input.KEY_DOWN)) {
                if (highlightedItemID < items.size()) {
                    highlightedItemID++;
                }
            } else if (input.isKeyPressed(Input.KEY_UP)) {
                if (highlightedItemID > 0) {
                    highlightedItemID--;
                }
            } else if (input.isKeyPressed(Input.KEY_ENTER)) {
                selectedItem = items.get(highlightedItemID);
                isSelectingTarget = true;
            }
        }
        // handle leaving the inventory somehow?
    }
    // increase the amount of item by some quantity; should also check if quantity goes over 99
    public static int containsItem(String itemName){
        for (int i=0; i<items.size(); i++){
            if (items.get(i).getName().equals(itemName)){
                return i;
            }
        }
        return -1;
    }
    public static void addItem(String itemName, int quantity) {
        int index = containsItem(itemName);
        if (index >= 0){
            items.get(index).addQuantity(quantity);
        }
        else{
            items.add(new Item(itemName, 0, quantity));
        }
    }
    public static void removeItem(String itemName, int quantity) {
        int index = containsItem(itemName);
        if (index >= 0){
            items.get(index).removeQuantity(quantity);
        }
    }
    public static int getQuantity(String itemName) {
        return 0;
    }
}