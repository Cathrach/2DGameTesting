/**
 * Created by amy on 5/27/17.
 */
import org.newdawn.slick.*;
import java.util.ArrayList;
import org.newdawn.slick.state.*;

public class Shop extends BasicGameState {
    private int id;
    private final static int[] weaponIDs = {0, 1, 2, 3, 4, 5};
    private final static int[] defenseIDs = {8, 9, 10, 11, 12, 13, 14, 15};
    private final static int[] consumableIDs = {17, 18, 19, 20, 21, 22};
    static int[] items;
    static boolean isShop;
    static int highlightedItemID;
    static int mode;
    static int previousMode;
    final static int BUYING = 0;
    final static int SELLING = 1;
    final static int POPUP = 2;
    static String message;
    static ShopKeyboard listener;

    public Shop (int id) {
        this.id = id;
        highlightedItemID = 0;
    }

    @Override
    public int getID() { return id; }

    public static void initListener(GameContainer container) {
        listener = new ShopKeyboard();
        container.getInput().addKeyListener(listener);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) {
        highlightedItemID = 0;
    }

    public static void enter(String shopType) {
        highlightedItemID = 0;
        message = "";
        isShop = true;
        if (shopType.equals("shopConsumables")) {
            items = consumableIDs;
        } else if (shopType.equals("shopWeapons")) {
            items = weaponIDs;
        } else if (shopType.equals("shopDefense")) {
            items = defenseIDs;
        } else {
            System.out.print("Error: shop type not specified\n");
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawString("Press BACKSPACE to leave", 20, 450);
        g.drawString("SHOP ITEMS", 20, 35);
        g.drawString("YOUR ITEMS (sold for 75%)", 320, 35);
        g.drawString("MONEY: " + String.valueOf(Resources.money) + " munchkins", 20,380);

        // draw list of store items
        for (int i = 0; i<items.length; i++){
            Resources.item_db[items[i]].render(30, 55 + i*40, true, g);
        }

        // draw list of your inventory items (so you can sell them). Doesn't include current equips.
        for (int i = 0; i<Inventory.items.size(); i++){
            Inventory.items.get(i).render(330, 55 + i*40, false, g);
            g.drawString(String.valueOf("x " + Inventory.items.get(i).getQuantity()), 570, 55 + i*40);
        }

        // draw box around highlighted item, display item info
        if (highlightedItemID < items.length) {
            if (mode == BUYING) {
                Item item = Resources.item_db[items[highlightedItemID]];
                g.drawString(item.getName() + ": " + item.getDescription(), 20, 400);
                g.drawString(item.getInfo(), 20, 420);
                g.drawRect(25, 55+(highlightedItemID)*40, 280, 40);
            } else if (mode == SELLING) {
                Item item = Inventory.items.get(highlightedItemID);
                g.drawString(item.getName() + ": " + item.getDescription(), 20, 400);
                g.drawString(item.getInfo(), 20, 420);
                g.drawRect(325, 55+(highlightedItemID)*40, 280, 40);
            }
        }

        // pop-up to let you know when you've purchased or sold an item
        if (!message.equals("")) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(120, 100, 400, 180);
            g.setColor(Color.white);
            g.drawString(message, 130, 130);
            g.drawString("OK", 315, 253);
            g.drawRect(120, 100, 400, 180); // pop-up box outline
            g.drawRect(125,250,390,25);     // OK 'button' outline
        }
    }
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (!isShop) {
            game.enterState(TestingGame.MAP);
        }
    }
}

class ShopKeyboard implements KeyListener {
    public void keyPressed(int key, char c) {

    }

    public void keyReleased(int key, char c) {
        if (key == Input.KEY_DOWN) {
            if (Shop.mode == Shop.BUYING) {
                if (Shop.highlightedItemID < Shop.items.length-1) {
                    Shop.highlightedItemID++;
                }
            } else if (Shop.mode == Shop.SELLING) {
                if (Shop.highlightedItemID < Inventory.items.size()-1) {
                    Shop.highlightedItemID++;
                }
            }
        } else if (key == Input.KEY_UP) {
            if (Shop.highlightedItemID > 0) {
                Shop.highlightedItemID--;
            }
        } else if (key == Input.KEY_LEFT) {
            if (Shop.mode == Shop.SELLING) {
                Shop.mode = Shop.BUYING;
                Shop.highlightedItemID = 0;
            }
        } else if (key == Input.KEY_RIGHT) {
            if (Shop.mode == Shop.BUYING) {
                Shop.mode = Shop.SELLING;
                Shop.highlightedItemID = 0;
            }
        } else if (key == Input.KEY_ENTER) {
            Shop.previousMode = Shop.mode;
            if (Shop.mode == Shop.BUYING) {
                Shop.mode = Shop.POPUP;
                Item item = Resources.item_db[Shop.items[Shop.highlightedItemID]];
                if (item.getValue() <= Resources.money) {
                    Shop.message = "Purchased " + item.getName() + " x 1";
                    Resources.money -= item.getValue();
                    Inventory.addItem(item.getName(), 1);
                } else {
                    Shop.message = "Not enough munchkins to purchase this item";
                }
            } else if (Shop.mode == Shop.SELLING) {
                Shop.mode = Shop.POPUP;
                Item item = Inventory.items.get(Shop.highlightedItemID);
                Shop.message = "Sold " + item.getName() + " x 1";
                Resources.money += (int) (item.getValue() * 0.75);
                Inventory.removeItem(item.getName(), 1);
            } else if (Shop.mode == Shop.POPUP) {
                Shop.mode = Shop.previousMode;
                Shop.message = "";
            }
        } else if (key == Input.KEY_BACK) {
            Shop.isShop = false;
        }
    }

    public void setInput(Input input) {

    }

    public boolean isAcceptingInput() {
        return Shop.isShop;
    }

    public void inputEnded() {

    }

    public void inputStarted() {

    }
}