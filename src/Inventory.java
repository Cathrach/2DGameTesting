/**
 * Created by serinahu on 5/4/17.
 */
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import java.util.ArrayList;
public class Inventory {
    static InventoryKeyboard listener;
    // currently equipped item's index in the inventory
    static int highlightedItemID;
    static Item selectedItem;
    static boolean isSelectingTarget;
    static int highlightedUnitID;
    static Ally selectedUnit;
    // contains consumables and equips: array list of items
    static ArrayList<Item> items = new ArrayList<>();

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
        g.drawString("Press ESC to return to main menu", 20, 450);
        g.drawString("INVENTORY", 20, 35);
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

        // render targets (for using/equipping the selected item)
        for (int i=0; i<Resources.party.size(); i++) {
            Entity entity = Resources.party.get(i);
            if (entity != null) {
                g.drawString(entity.battleEntity.name, 325, 75 + 85*i);
                g.drawString("Equips:", 420, 75 + 85*i);
                entity.getSprite().getSubImage(0, 0, 32, 45).draw(325, 95 + 85*i);
                for (int j = 0; j < entity.battleEntity.equips.size(); j++) {
                    Equipment equipment = entity.battleEntity.equips.get(j);
                    g.drawString(equipment.getName(), 420, 90 + 85*i + 15*j);
                }
            }
        }
        if (isSelectingTarget) {
            g.drawString("Member to use/equip this item:", 325, 35);
            g.drawString("(Press BACKSPACE to cancel)", 325, 55);
            g.drawRect(320, 75 + 85*highlightedUnitID, 300, 85);
        } else {
            g.drawString("Press [ENTER] to select item", 325, 35);
            g.drawString("Press [S] for Stats Menu", 325, 55);
        }
    }
    public static void update(GameContainer container, int delta) {
        // nothing to do here
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
}

class InventoryKeyboard implements KeyListener {
    public void keyPressed(int key, char c) {

    }

    public void keyReleased(int key, char c) {
        if (Inventory.isSelectingTarget) {
            if (key == Input.KEY_DOWN) {
                if (Inventory.highlightedUnitID < Resources.party.size()-1 && Resources.party.get(Inventory.highlightedUnitID + 1) != null) {
                    Inventory.highlightedUnitID++;
                }
            } else if (key == Input.KEY_UP) {
                if (Inventory.highlightedUnitID > 0) {
                    Inventory.highlightedUnitID--;
                }
            } else if (key == Input.KEY_DELETE || key == Input.KEY_BACK) {
                Inventory.isSelectingTarget = false;
            } else if (key == Input.KEY_ENTER) {
                Inventory.selectedUnit = Resources.party.get(Inventory.highlightedUnitID).battleEntity;
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
                    Inventory.removeItem(Inventory.selectedItem.getName(), 1);
                } else {
                    Inventory.selectedUnit.equip((Equipment) Inventory.selectedItem);
                }
                Inventory.isSelectingTarget = false;
            }
        } else {
            if (key == Input.KEY_DOWN) {
                if (Inventory.highlightedItemID < Inventory.items.size() - 1) {
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
        return PauseMenu.inMenu == PauseMenu.INVENTORY;
    }

    public void inputEnded() {

    }

    public void inputStarted() {

    }
}