/**
 * Created by serinahu on 5/4/17.
 */

import org.newdawn.slick.*;

public class Item {
    private String name;
    private Image item;
    private int value;
    private int quantity;

    public void init() {

    }

    public void render(float xPos, float yPos, Graphics g) {
        item.draw(xPos, yPos);
        g.drawString(name, xPos + item.getWidth() + 10, yPos);
        g.drawString(value + "G", xPos + item.getWidth() + 10, yPos + 10);
    }

    public void update(int delta) {

    }

    public void addQuantity(int quantity) {

    }

    public void removeQuantity(int quantity) {

    }
}
