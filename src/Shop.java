/**
 * Created by amy on 5/27/17.
 */
import org.newdawn.slick.*;
import java.util.ArrayList;
public class Shop {
    static ShopKeyboard listener;
    // currently equipped item's index in the inventory
    static int highlightedItemID;
    static Item selectedItem;
    // arraylist of purchasable items
    static ArrayList<Item> items = new ArrayList<>();

    public static void initListener(GameContainer container) {
        listener = new ShopKeyboard();
        container.getInput().addKeyListener(listener);
    }

    public static void init() {
        highlightedItemID = 0;
    }
    public static void render(Graphics g) {
        g.drawString("Press ESC to leave", 20, 450);
        g.drawString("ITEMS", 20, 35);
        g.drawString("MONEY: " + String.valueOf(Resources.money) + " munchkins", 20,380);

        // draw list of items
        for (int i = 0; i<items.size(); i++){
            items.get(i).render(30, 55 + i*40, g);
            g.drawString(String.valueOf("x " + items.get(i).getQuantity()), 270, 55 + i*40);
        }

        // draw box around highlighted item, display item info
        if (highlightedItemID < items.size()) {
            g.drawString(items.get(highlightedItemID).getName() + ": " + items.get(highlightedItemID).getDescription(), 20, 400);
            g.drawString(items.get(highlightedItemID).getInfo(), 20, 420);
            g.drawRect(25, 55+(highlightedItemID)*40, 280, 40);
        }
    }
    public static void update(GameContainer container, int delta) {
        // nothing to do here
    }
}

class ShopKeyboard implements KeyListener {
    public void keyPressed(int key, char c) {

    }

    public void keyReleased(int key, char c) {
        if (key == Input.KEY_DOWN) {
            if (Shop.highlightedItemID < Shop.items.size() - 1) {
                Shop.highlightedItemID++;
            }
        } else if (key == Input.KEY_UP) {
            if (Shop.highlightedItemID > 0) {
                Shop.highlightedItemID--;
            }
        } else if (key == Input.KEY_ENTER) {
            Shop.selectedItem = Shop.items.get(Shop.highlightedItemID);
            if (Resources.money >= Shop.selectedItem.getValue()) {
                Inventory.addItem(Shop.selectedItem.getName(), 1);
                Resources.money -= Shop.selectedItem.getValue();
            } else {

            }
        }
    }

    public void setInput(Input input) {

    }

    public boolean isAcceptingInput() {
        return Cutscene.isShop;
    }

    public void inputEnded() {

    }

    public void inputStarted() {

    }
}