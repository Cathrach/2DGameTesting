/**
 * Created by amy on 5/27/17.
 */
import org.newdawn.slick.*;
import java.util.ArrayList;
import org.newdawn.slick.state.*;

public class Shop extends BasicGameState {
    private int id;
    static int highlightedItemID;
    static Item selectedItem;
    // arraylist of purchasable items
    static ArrayList<Item> items;

    public Shop (int id) {
        this.id = id;
        highlightedItemID = 0;
        items = new ArrayList<>();
    }

    @Override
    public int getID() { return id; }

    @Override
    public void init(GameContainer container, StateBasedGame game) {
        highlightedItemID = 0;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawString("Press ESC to leave", 20, 450);
        g.drawString("ITEMS", 20, 35);
        g.drawString("MONEY: " + String.valueOf(Resources.money) + " munchkins", 20,380);

        // draw list of store items
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

        // draw list of your inventory items (so you can sell them). Doesn't include current equips.
    }
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // TODO
        // use up & down arrows to look at different items
        // press ENTER to buy the item
        // display "are you sure?" pop-up
    }
}