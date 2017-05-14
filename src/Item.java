/**
 * Created by serinahu on 5/4/17.
 */
import org.newdawn.slick.*;
public class Item {
    private String name;
    private Image item;
    //private int imgIndex;
    private int value;
    private int quantity;
    public Item(String n, int v, int q){
        name = n;
        value = v;
        quantity = q;
    }
    public void init() {
    }
    public void render(float xPos, float yPos, Graphics g) {
        item.draw(xPos, yPos);
        g.drawString(name, xPos + item.getWidth() + 10, yPos);
        g.drawString(value + "G", xPos + item.getWidth() + 10, yPos + 10);
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
    public void addQuantity(int quantity) {
        this.quantity = Math.min(99, this.quantity + quantity);
    }
    public void removeQuantity(int quantity) {
        this.quantity = Math.max(0, this.quantity - quantity);
    }
}