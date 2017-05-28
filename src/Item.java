/**
 * Created by serinahu on 5/4/17.
 */
import org.newdawn.slick.*;
public class Item {
    private String name;
    private Image item;
    private int value;
    private int quantity;
    private String description;
    public String info;

    public Item(String n, int v, int q, String d) throws SlickException {
        name = n;
        value = v;
        quantity = q;
        description = d;
        item = new Image("images/items/" + name + ".png");
    }
    public void init() {
    }
    public void render(float xPos, float yPos, Graphics g) {
        item.draw(xPos, yPos);
        g.drawString(name, xPos + item.getWidth() + 10, yPos);
        g.drawString("Value: " + value + "munchkins", xPos + item.getWidth() + 10, yPos + 15);
    }
    public Image getImage(){
        return item;
    }
    public void update(int delta) {
    }
    public String getName(){
        return name;
    }
    public int getQuantity(){
        return quantity;
    }
    public String getDescription() {
        return description;
    }
    public int getValue() { return value; }
    public String getInfo() { return info; }

    public void addQuantity(int quantity) {
        this.quantity = Math.min(99, this.quantity + quantity);
    }
    public void removeQuantity(int quantity) {
        this.quantity = Math.max(0, this.quantity - quantity);
    }
}