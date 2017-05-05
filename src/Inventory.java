/**
 * Created by serinahu on 5/4/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

import java.util.Hashtable;

public class Inventory {

    // a button for highlighting
    Rectangle button;
    // button location (x, y)
    int buttonXPos;
    int buttonYPos;
    // contains consumables and equips: hash table of items and quantities? make a class "InventoryItem"?


    public Inventory() {

    }

    public void init() {

    }

    public void render(Graphics g) {
        // show a table of items: render each item
        // by default first item is highlighted by the button (should probably handle this in init)
    }

    public void update(int delta) {
        // check if keys pressed and move buttons around
        // also, check for using/equipping
    }

    // increase the amount of item by some quantity; should also check if quantity goes over 99
    public void addItem(Item item, int quantity) {

    }

    public void removeItem(Item item, int quantity) {

    }

    public int getQuantity(Item item) {
        return 0;
    }
}
