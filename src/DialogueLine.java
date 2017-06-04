import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.*;

/**
 * Created by serinahu on 5/12/17.
 */
public class DialogueLine {
    String[] lineSplit;
    Image speaker;
    boolean isLast;
    String triggerChange;
    int mapID;

    public DialogueLine(String[] data) throws SlickException {
        // if the text file for the scene is empty, assign default text
        try { this.lineSplit = data[0].split("//"); }
        catch (ArrayIndexOutOfBoundsException e) { this.lineSplit = new String[]{"...?"}; }

        // if there is no specified image, assign default image
        try { this.speaker = new Image(data[1]); }
        catch (RuntimeException e) { this.speaker = new Image("images/backgrounds/blank.png"); }

        // if it is not specified whether this line is the last line, assign default value
        try { this.isLast = Boolean.valueOf(data[2]); }
        catch (ArrayIndexOutOfBoundsException e) { this.isLast = false; }

        // if no map is specified, assign default (-1 means map does not change)
        try { this.mapID = Integer.parseInt(data[3]); }
        catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) { this.mapID = -1; }

        // if no trigger is specified, assign default
        try { this.triggerChange = data[4]; }
        catch (ArrayIndexOutOfBoundsException e) { this.triggerChange = ""; }
    }

    public void render(Graphics g) {
        // render speaker (or, in this case, the cutscene image)
        this.speaker.draw(0, 0);
        // render line in a rectangle
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 400, 640, 80);
        g.setColor(Color.white);
        g.drawRect(0,400,639,80);
        for (int i=0; i<lineSplit.length; i++) {
            g.drawString(lineSplit[i], 20, 420+i*15);
        }
        g.drawString("<SPACE>", 560, 460);
    }
}
