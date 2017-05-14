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
    static int equipIndex;
    // contains consumables and equips: array list of items
    static ArrayList<Item> items = new ArrayList<Item>();
    // index in array list?
    public static void init() {
    }
    public static void render(Graphics g) {
        // temporary! Will add GUI & fix up later
        g.drawString("INVENTORY", 20, 70);
        for (int i = 0; i<items.size(); i++){
            g.drawString(items.get(i).getName() + " x " + items.get(i).getQuantity(), 20, 70+(i+1)*15);
        }
    }
    public static void update(GameContainer container, int delta) {
        Input input = container.getInput();
        // check if keys pressed and move buttons around
        // also, check for using/equipping
        if (input.isKeyPressed(Input.KEY_ENTER)) {
            // use the item
        }
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
            items.get(index).addQuantity(1);
        }
        else{
            items.add(new Item(itemName, 0, 1));
        }
    }
    public static void removeItem(String itemName, int quantity) {
        int index = containsItem(itemName);
        if (index >= 0){
            items.get(index).removeQuantity(1);
        }
    }
    public static int getQuantity(String itemName) {
        return 0;
    }
}