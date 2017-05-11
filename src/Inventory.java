/**
 * Created by serinahu on 5/4/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

import java.util.ArrayList;

public class Inventory {

    // a button for highlighting
    Rectangle button;
    // button location (x, y)
    int buttonXPos;
    int buttonYPos;
    // contains consumables and equips: array list of items
    ArrayList<Item> items;
    // index in array list?

    public Inventory() {

    }

    public void init() {

    }

    public void render(Graphics g) {
        // show a table of items: render each item
        // by default first item is highlighted by the button (should probably handle this in init)
    }

    public void update(GameContainer container, int delta) {
        Input input = container.getInput();
        // check if keys pressed and move buttons around
        // also, check for using/equipping
        if (input.isKeyPressed(Input.KEY_ENTER)) {
            // use the item

        }

    }

    // increase the amount of item by some quantity; should also check if quantity goes over 99
    public void addItem(String itemName, int quantity) {
        for (Item item : items) {

        }
    }

    public void removeItem(String itemName, int quantity) {

    }

    public int getQuantity(String itemName) {
        return 0;
    }
}
